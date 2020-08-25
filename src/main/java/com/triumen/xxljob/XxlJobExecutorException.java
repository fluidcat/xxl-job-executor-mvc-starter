package com.triumen.xxljob;

import com.xxl.job.core.biz.model.ReturnT;

public class XxlJobExecutorException extends RuntimeException {
    private ReturnT ret;

    public XxlJobExecutorException(ReturnT ret) {
        super(ret.getMsg());
        this.ret = ret;
    }

    public ReturnT getRet() {
        return ret;
    }
}
