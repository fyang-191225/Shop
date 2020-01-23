package com.year.shop.provider.net.controller;

import com.fyy.mybabystudy.common.vo.R;
import com.fyy.mybabystudy.provider.net.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 实现资源的操作：图片，文件，视频，借助于阿里云的oss
 * @author fyy
 * @date 2020/1/2 0002 下午 19:43
 */
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/provider/oss/fileupload")
    public R save(MultipartFile file) throws IOException {
        return ossService.save(file);
    }
}
