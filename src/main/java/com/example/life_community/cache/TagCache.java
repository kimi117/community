package com.example.life_community.cache;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {

    public static List<TagDTO> get() {
        List<TagDTO> tagDTOList = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Js", "Php", "Css", "Html", "Java", "Node", "Python"));
        tagDTOList.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring", "SpringBoot"));
        tagDTOList.add(framework);

        TagDTO server =new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Tomcat"));
        tagDTOList.add(server);

        TagDTO db =new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("Mysql", "Oracle"));
        tagDTOList.add(db);

        TagDTO tool =new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("Git", "Svn"));
        tagDTOList.add(tool);

        return tagDTOList;
    }

    public static String filterInvalid(String tags) {
        String[] tagArray = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOList = get();
        System.out.println(JSON.toJSONString(tagDTOList));

        List<String> tagList = tagDTOList.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        System.out.println("tagList：" + tagList);

        String invalid = Arrays.stream(tagArray).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        System.out.println("invalid：" + invalid);
        return invalid;

    }

}
