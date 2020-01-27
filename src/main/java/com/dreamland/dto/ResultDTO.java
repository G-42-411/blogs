package com.dreamland.dto;

import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultDTO errorof(CustomizeErrorCode errorCode) {
        return new ResultDTO(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okof() {
        return new ResultDTO(200, "成功！");
    }

    public static ResultDTO errorof(CustomizeException e) {
        return new ResultDTO(e.getCode(), e.getMessage());
    }


}
