package com.year.shop.provider.net.service;

import com.fyy.mybabystudy.common.vo.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author fyy
 * @date 2020/1/2 0002 下午 19:47
 */
public interface OssService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    R save(MultipartFile file) throws IOException;
}
