package com.example.life_community.controller;

import com.example.life_community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
     *
     * https://www.cnblogs.com/summerday152/p/13969452.html#%E5%88%A9%E7%94%A8spirngboot%E5%AE%9E%E7%8E%B0%E6%96%87%E4%BB%B6%E4%B8%8A%E4%BC%A0%E5%8A%9F%E8%83%BD
     */
    @PostMapping("/file/upload")
    @ResponseBody
    public FileDTO uploadImg(MultipartFile file) {
        String fileName;
        FileDTO fileDTO = new FileDTO();
        System.out.println("access uploadImg");
        try {
            fileName = file.getOriginalFilename();
            File newFile = new File("E:/2021_idea_workspace/demo/life_community/src/main/resources/upload_file/" + fileName);
            if(!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }
            if(!newFile.exists()) {
                newFile.createNewFile();
            }

            file.transferTo(newFile);




            fileDTO.setSuccess(1);
            fileDTO.setMessage("SUCCESS");
            fileDTO.setUrl("E:/2021_idea_workspace/demo/life_community/src/main/resources/upload_file/" + fileName);
            return fileDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileDTO.setSuccess(101);
        fileDTO.setMessage("ERROR");
        fileDTO.setUrl(null);
        return fileDTO;
    }

}
