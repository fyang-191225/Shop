package com.year.shop.provider.auto.service.impl;

import com.alibaba.fastjson.JSON;
import com.year.shop.common.config.RedisConfig;
import com.year.shop.common.config.SystemConfig;
import com.year.shop.common.dto.TokenDto;
import com.year.shop.common.dto.user.UserLoginDto;
import com.year.shop.common.exception.UserException;
import com.year.shop.common.util.EncryptUtil;
import com.year.shop.common.util.JedisUtil;
import com.year.shop.common.util.StrUtil;
import com.year.shop.common.vo.R;
import com.year.shop.entity.user.User;
import com.year.shop.provider.auto.core.TokenUtil;
import com.year.shop.provider.auto.dao.UserDao;
import com.year.shop.provider.auto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fyy
 * @date 2020/1/20 0020 下午 18:29
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public R save(User user) {
        // 加锁   可重入锁特点：手动开启、手动关闭
        Lock lock = new ReentrantLock();
        try {
            lock.lock();// 开锁
            // 注册前的校验，校验该账号是否已被注册
            // 因为注册时phone和email是两个字段，所以调用selectUser() 方法
            if (userDao.selectUser(user) == null) { // 未注册
                // 密码加密 第一个参数 key ,第二个参数 值，即密码
                user.setPassword(EncryptUtil.aesenc(SystemConfig.PASS_KEY, user.getPassword()));
                if (userDao.insert(user) > 0) {
                    return R.ok();
                } else {
                    return R.fail("服务异常，注册失败");
                }
            } else {
                return R.fail("手机号或邮箱已被注册，请更换方式");
            }
        } finally {
            lock.unlock(); //释放锁
        }
    }

    @Override
    public R login(UserLoginDto userLoginDto) {
        // 校验账号是否正确 即是否存在数据库
        User user = userDao.selectByName(userLoginDto.getName());
        if (user != null) {
            // 该账号冻结
            if (jedisUtil.checkKey(RedisConfig.TOKEN_FROST + user.getId())) {
                return R.fail("账号已冻结，请稍后再来");
            } else {
                // 校验密码是否正确
                if (user.getPassword().equals(EncryptUtil.aesenc(SystemConfig.PASS_KEY, userLoginDto.getPass()))) {
                    // 账号密码正确
                    // 生成令牌
                    TokenDto tokenDto = new TokenDto(user.getId(), userLoginDto.getType(), user.getPhone(), user.getEmail()); //令牌对象
                    String token = TokenUtil.createToken(tokenDto);// 生成de令牌
                    // 限定3次
                    List<String> tks = jedisUtil.getList(RedisConfig.USER_TOKEN + user.getId());
                    if (tks.size() > 2) {
                        // 本次为第四次登录， 需要挤掉一个最早登录的令牌
                        String exitToken = jedisUtil.pop(RedisConfig.USER_TOKEN + user.getId()); // 被挤掉的用户 第一次登录的人
                        //删除令牌
                        jedisUtil.del(RedisConfig.USER_TOKEN+exitToken);
                        // 记录挤掉的信息
                        jedisUtil.addHash(RedisConfig.TOKEN_TKCK,exitToken, JSON.toJSONString(userLoginDto));
                    }
                    // 存储令牌  即存储最新登录的用户
                    jedisUtil.addStr(RedisConfig.TOKEN_KEY + token, user.getId() + "", RedisConfig.TOKEN_TIME);
                    // 记录用户id的令牌
                    jedisUtil.push(RedisConfig.USER_TOKEN + user.getId(), token);
                    return R.ok(token);
                } else {
                    // 密码错误 记录密码错误 ，先记录密码错误
                    jedisUtil.addStr(RedisConfig.LOGIN_ERROR+user.getId()+":"+System.currentTimeMillis(),"",600);
                    //  验证失败次数有没有达到3次
                    if (jedisUtil.key(RedisConfig.LOGIN_ERROR+user.getId()) >= 3) {
                        // 账号冻结
                        jedisUtil.addStr(RedisConfig.TOKEN_FROST+user.getId(),"",RedisConfig.TOKENFORST_TIME);
                        // 冻结账号，所有在线人必须下线
                        List<String> tks = jedisUtil.getList(RedisConfig.USER_TOKEN + user.getId());
                        for (String s:tks) {
                            //  干掉每个账户
                            jedisUtil.del(RedisConfig.USER_TOKEN+s);
                        }
                        // 记录用户id的令牌 删除
                        jedisUtil.del(RedisConfig.USER_TOKEN+user.getId());

                        return R.fail("登录10分钟之内连续错误，账户被冻结");
                    }else {
                        return R.fail("账号或密码不正确");
                    }
                }
            }
        }else{
            return R.fail("账号或密码不正确");
        }
    }

    /**
     * 校验 账户是否存在
     *
     * @param name
     * @return
     */
    @Override
    public R checkName(String name) {
        if (userDao.selectByName(name) != null) {
            return R.fail("手机号或邮箱已存在");
        } else {
            return R.ok();
        }
    }

    @Override
    public R changePwd(String token, String pass) {
        return null;
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param pass
     * @return
     */
    @Override
    public R forgetPwd(String phone, String pass) {
        // 判断 手机号和 密码是否为空
        if (!StrUtil.isEmpty(phone, pass)) { // 不为空
            if (userDao.update(phone, EncryptUtil.aesenc(SystemConfig.PASS_KEY, pass)) > 0) {// 修改加密后的密码
                //清理该账号一切相关的令牌
                return R.ok();
            } else {
                return R.fail("服务异常，账号找回失败");
            }
        } else { // 为空
            return R.fail("请输入合法的参数");
        }
    }

    @Override
    public R checkToken(String token) throws UserException {
        // 校验令牌是否存在
        if (jedisUtil.checkKey(RedisConfig.USER_TOKEN+token)) {
            // 令牌无效 原因 1.令牌失效 2.账号被冻结 3.找回密码 4.密码修改 5.被挤掉 6.Redis触发了被淘汰策略
            if (jedisUtil.checkHash(RedisConfig.TOKEN_TKCK,token)) {    //   被挤掉

                return R.fail(jedisUtil.getHash(RedisConfig.TOKEN_TKCK).get(token)); // 谁挤掉的信息

            } else if (jedisUtil.checkKey(RedisConfig.TOKEN_FROST+ TokenUtil.parseToken(token).getUid())){ // 被冻结
                return R.fail("亲，账号被冻结");
            } else {
                return R.fail("令牌无效");
            }
        } else {
            return R.ok();
        }
    }
}


