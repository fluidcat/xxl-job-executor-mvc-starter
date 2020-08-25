package com.triumen.xxljob.mvc;

import com.triumen.xxljob.XxlJobExecutorException;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {XxlJobApi.class})
public class XxlJobApiAdvice {
    @ExceptionHandler(XxlJobExecutorException.class)
    public ReturnT exception(XxlJobExecutorException e) {
        log.error(e.getRet().getMsg());
        return e.getRet();
    }

    @ExceptionHandler(Exception.class)
    public ReturnT elseException(Exception e) {
        log.error("XxlJobApi error.", e);
        return new ReturnT(ReturnT.FAIL_CODE, e.getMessage());
    }
}
