package com.example.life_community.dto;

import com.example.life_community.exception.CustomizeException;
import com.example.life_community.exception.ECustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(ECustomizeErrorCode noLogin) {

        return errorOf(noLogin.getCode(), noLogin.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("SUCCESS");
        return resultDTO;
    }

}
