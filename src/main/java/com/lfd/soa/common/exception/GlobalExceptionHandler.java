package com.lfd.soa.common.exception;

import com.lfd.soa.common.bean.resp.Resp;
import com.lfd.soa.common.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述: controller统一异常处理
 *
 * @author linfengda
 * @create 2018-11-20 10:36
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Resp<Integer> defaultErrorHandler(Exception e) {
        Resp<Integer> resp;
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            resp = new Resp<>(businessException.getCode(), businessException.getMsg());
        }else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) e;
            resp = new Resp<>(ErrorCode.PARAM_ERROR_CODE, argumentNotValidException.getMessage());
        }else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.warn("404找不到URL:未知请求与方法", e);
            resp = new Resp<>(404, "请求地址未找到");
        }else {
            log.error("error info:", e);
            resp = new Resp<>(ErrorCode.UNKNOWN_ERROR_CODE, "系统故障，请稍后再试！");
        }
        return resp;
    }
}
