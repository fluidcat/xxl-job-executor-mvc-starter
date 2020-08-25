package com.triumen.xxljob;

import com.triumen.xxljob.mvc.XxlJobApi;
import com.triumen.xxljob.mvc.XxlJobApiInterceptor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ConditionalOnClass({XxlJobSpringExecutor.class})
public class XxlJobMvcExecutorAutoConfiguration implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean("xxlJobExecutorProperties")
    @ConfigurationProperties(prefix = "xxl.job.executor")
    public XxlJobExecutorProperties xxlJobExecutorProperties() {
        return new XxlJobExecutorProperties();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xxlJobApiInterceptor()).addPathPatterns(xxlJobExecutorProperties().getUriRoot() + "/**");
    }

    @Bean(autowire = Autowire.BY_TYPE)
    public XxlJobApi xxlJobApi() {
        return new XxlJobApi();
    }

    @Bean
    @ConditionalOnMissingBean(XxlJobSpringExecutor.class)
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobExecutorProperties properties) {
        logger.info(">>>>>>>>>>> xxl-job executor config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(properties.getAppName());
        xxlJobSpringExecutor.setAddress(properties.getAddress());
        xxlJobSpringExecutor.setIp(properties.getIp());
        xxlJobSpringExecutor.setPort(properties.getPort());
        xxlJobSpringExecutor.setAccessToken(properties.getAdminAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(properties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }

    @Bean(autowire = Autowire.BY_TYPE)
    @ConditionalOnMissingBean(XxlJobApiInterceptor.class)
    public XxlJobApiInterceptor xxlJobApiInterceptor() {
        return new XxlJobApiInterceptor();
    }
}
