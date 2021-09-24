package com.example.life_community.controller;

import com.example.life_community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    /**
     * 方式一：
     *  将文件放到固定目录里，使用 Nginx 或 Tomcat 映射一个地址，返回给用户。这样依赖于主机迁移，比如部署在国内的云上，后面部署到自己的机房时需要迁移各种文件。
     * 方式二：
     *  将图片资源转换二进制文件存储数据库，渲染页面时候序列化，这样成本很高，本身数据库是有索引，各种存储机制优化机制为了方便存储数据，当成文件图片存储成本高。
     * 方式三：
     *  云平台（对象存储）
     * @return
     */
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO uploadImg() {
        System.out.println("access uploadImg");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("");
        fileDTO.setUrl("/images/img1.jpg");
        return fileDTO;
    }

}
