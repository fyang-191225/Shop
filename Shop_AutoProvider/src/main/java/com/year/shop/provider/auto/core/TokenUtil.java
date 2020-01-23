package com.year.shop.provider.auto.core;


import com.alibaba.fastjson.JSON;
import com.year.shop.common.dto.TokenDto;
import com.year.shop.common.exception.UserException;

/**
 * @program: EatJoke
 * @description:
 * @author: Feri
 * @create: 2019-12-25 14:37
 */
public class TokenUtil {
    //生成令牌
    public static String createToken(TokenDto tokenDto){
        return JwtUtil.createJWT(JSON.toJSONString(tokenDto));
    }
    //解析令牌
    public static TokenDto parseToken(String token) throws UserException {
        String str=JwtUtil.parseJWT(token);
        if(str!=null && str.length()>0) {
            try{
                return JSON.parseObject(str, TokenDto.class);
            }catch (Exception e){
                throw new UserException("令牌不合法");
            }
        }else {
            throw new UserException("令牌为空");
        }
    }
}
