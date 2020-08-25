package com.triumen.xxljob.mvc;


import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.impl.ExecutorBizImpl;
import com.xxl.job.core.biz.model.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("#{xxlJobExecutorProperties.uriRoot}")
public class XxlJobApi {

    private ExecutorBiz delegate = new ExecutorBizImpl();

    @PostMapping("/beat")
    public ReturnT<String> beat() {
        return delegate.beat();
    }

    @PostMapping("/idleBeat")
    public ReturnT<String> idleBeat(@RequestBody IdleBeatParam idleBeatParam) {
        return delegate.idleBeat(idleBeatParam);
    }

    @PostMapping("/run")
    public ReturnT<String> run(@RequestBody TriggerParam triggerParam) {
        return delegate.run(triggerParam);
    }

    @PostMapping("/kill")
    public ReturnT<String> kill(@RequestBody KillParam killParam) {
        return delegate.kill(killParam);
    }

    @PostMapping("/log")
    public ReturnT<LogResult> log(@RequestBody LogParam logParam) {
        return delegate.log(logParam);
    }
}
