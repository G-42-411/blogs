package com.dreamland.advice;

import com.alibaba.fastjson.JSON;
import com.dreamland.dto.ResultDTO;
import com.dreamland.exception.CustomizeErrorCode;
import com.dreamland.exception.CustomizeException;
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
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            //返回json
            if (e instanceof CustomizeException) {
                resultDTO =  ResultDTO.errorof((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorof(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
        }
        return new ModelAndView("error");
    }
}
