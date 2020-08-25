package com.triumen.xxljob;

import lombok.Data;

@Data
public class XxlJobExecutorProperties {
    private String adminAddresses;
    private String adminAccessToken;
    private String appName;
    private String address;
    private String ip;
    private int port;
    private String logPath;
    private int logRetentionDays;
    private String uriRoot;
}
