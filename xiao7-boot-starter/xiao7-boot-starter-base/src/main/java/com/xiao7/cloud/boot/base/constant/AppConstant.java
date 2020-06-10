package com.xiao7.cloud.boot.base.constant;

/**
 * 系统变量
 *
 * @author cwh
 */
public interface AppConstant {

    String BASE_PACKAGES = "com.xiao7.cloud";

    /**
     * 开发环境
     */
    String ENV_DEV = "dev";

    /**
     * 生产环境
     */
    String ENV_PROD = "prod";

    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = "xiao7-";

    /**
     * 日志模块名称
     */
    String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";

    /**
     * 鉴权模块名称
     */
    String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";

    /**
     * 平台模块名称
     */
    String APPLICATION_PLATFORM_NAME = APPLICATION_NAME_PREFIX + "platform";

    /**
     * 网关服务名称
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";

}
