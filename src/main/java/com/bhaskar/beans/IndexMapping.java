package com.bhaskar.beans;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@PropertySource("classpath:config/infectionIndexMapping.properties")
@ConfigurationProperties
public class IndexMapping {

    private Map<String, String> config = new HashMap<>();

    /**
     * @return the priorityMap
     */
    public Map<String, String> getConfig() {
        return config;
    }


}

