package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.SyncFailedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error("发生500异常"+e.getMessage());
        return Result.fail(null);
    }
    @ExceptionHandler(SsyxException.class)
    @ResponseBody
    public Result error(SsyxException e) {
        log.error("发生自定义异常"+e.getMessage());
        return Result.fail(null);
    }


}
