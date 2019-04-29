package xyz.dean.tutor_manager.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.dean.tutor_manager.common.ResponseCode;
import xyz.dean.tutor_manager.model.ResponseData;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseData defaultErrorHandler(HttpServletRequest req, Exception e) {
        return ResponseData
                .unknownError(e)
                .setMsg("在访问"+req.getRequestURL()+"时发生了错误:"+e.getMessage());
    }
}
