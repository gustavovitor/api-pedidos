package com.gustavovitor.apibase.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "apiconfig")
public class APIProperty {
    public Security getSecurity() {
        return security;
    }

    private final Security security = new Security();

    public static class Security{
        private boolean enableHttps;
        private String[] allowOrigin;

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }

        public void setAllowOrigin(String[] allowOrigin) {
            this.allowOrigin = allowOrigin;
        }

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public String[] getAllowOrigin() {
            return allowOrigin;
        }
    }
}
