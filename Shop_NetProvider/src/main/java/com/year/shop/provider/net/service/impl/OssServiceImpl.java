package com.year.shop.provider.net.service.impl;

import com.fyy.mybabystudy.common.vo.R;
import com.fyy.mybabystudy.provider.net.core.AliOssUtil;
import com.fyy.mybabystudy.provider.net.core.FileUtil;
import com.fyy.mybabystudy.provider.net.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author fyy
 * @date 2020/1/2 0002 下午 19:48
 */

@Service
public class OssServiceImpl implements OssService {

    @Override
    public R save(MultipartFile file) throws IOException {
        String fn = FileUtil.renameFile(file.getOriginalFilename());
        // 上传 图片 到Oss
        String url = AliOssUtil.upload(fn, file.getBytes());
        if (url != null) {
            return R.ok(url);
        } else {
            return R.fail();
        }
    }
}
