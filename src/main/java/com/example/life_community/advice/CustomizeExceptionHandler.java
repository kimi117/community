package com.example.life_community.advice;

import com.alibaba.fastjson.JSON;
import com.example.life_community.dto.ResultDTO;
import com.example.life_community.exception.CustomizeException;
import com.example.life_community.exception.ECustomizeErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {

        String contentType = request.getContentType();
        System.out.println("contentType：" + contentType);
        if("application/json".equals(contentType)) {
            ResultDTO resultDTO = null;
            // 返回 JSON
            if(ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(ECustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            // 错误页面跳转
            if(ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", ECustomizeErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }

    }

}
