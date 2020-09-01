## xxl-job 执行器自动配置
spring boot web项目作为执行器时，使用官方demo时需要开放多一个执行器端口（默认:9999），在环境部署时需要开放额外的端口。该项目使用```#{server.port}```作为端口
环境部署时无需配置额外的端口

### 依赖版本
xxl-job-core：2.2.0  
spring boot ：2.3.3.RELEASE

### 使用
#### 安装
```shell
git clone https://github.com/fluidcat/xxl-job-executor-mvc-starter.git

cd xxl-job-executor-mvc-starter

mvn install
```  
#### 添加依赖
```xml
        <dependency>
            <groupId>io.github.fluidcat</groupId>
            <artifactId>xxl-job-executor-mvc-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```
#### 添加配置
```yaml
xxl:
  job:
    executor:
      admin-addresses: {xxl-job admin address}
      admin-accessToken: {accessToken}
      app-name: xxl-job-executor-simple
      # 这里后缀必须和uri-root相同
      address: http://10.8.1.36:${server.port}${xxl.job.executor.uri-root}
      log-path: /logs/xxl-job
      log-retention-days: 3
      # 建议为执行器的接口设置前缀，区分其他业务接口
      uri-root: /e/xxl-job/simple
server:
  port: 8080
```