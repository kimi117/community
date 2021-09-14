package com.example.life_community.advice;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomDefaultErrorAttribute extends DefaultErrorAttributes {
// 处理 404 等返回的信息
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "找不到资源");
        return map;
    }
}
