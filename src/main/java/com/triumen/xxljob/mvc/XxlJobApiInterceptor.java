package com.triumen.xxljob.mvc;

import com.triumen.xxljob.XxlJobExecutorException;
import com.triumen.xxljob.XxlJobExecutorProperties;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;

public class XxlJobApiInterceptor implements HandlerInterceptor {
    private HashSet<String> includeUri = new HashSet<>(32);

    @Autowired
    private XxlJobExecutorProperties properties;

    // 初始化需要校验的接口
    @PostConstruct
    void init() {
        Method[] methods = ReflectionUtils.getDeclaredMethods(XxlJobApi.class);
        for (Method method : methods) {
            PostMapping mapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
            if (mapping != null) {
                String uri = mapping.value()[0];
                includeUri.add(properties.getUriRoot() + uri);
            }
        }
    }

    // xxl-job 接口校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!includeUri.contains(uri)) {
            throw new XxlJobExecutorException(new ReturnT<String>(ReturnT.FAIL_CODE, "invalid request, uri-mapping(" + uri + ") not found."));
        }
        String accessToken = properties.getAdminAccessToken();
        String accessTokenReq = request.getHeader(XxlJobRemotingUtil.XXL_JOB_ACCESS_TOKEN);
        if (accessToken != null
                && accessToken.trim().length() > 0
                && !accessToken.equals(accessTokenReq)) {
            throw new XxlJobExecutorException(new ReturnT<String>(ReturnT.FAIL_CODE, "The access token is wrong."));
        }
        return true;
    }

}
