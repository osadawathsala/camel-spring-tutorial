package com.tutorial.camel.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Osada
 * @Date 1/14/2024
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "activemq")
@Configuration
//@PropertySource("classpath:activemq.properties")
public class ActiveMQConfig {
    private int maximumRedeliveries;
    private long redeliveryDelay;
    private double backOffMultiplier;
    private long maximumRedeliveryDelay;
}
