package com.decodedbytes.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Osada
 * @Date 1/15/2024
 */
//@Component
public class SimpleTimer extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:simpleTimer?period=2000")
                .routeId("SimpleRouteId")
                .setBody(constant("Hello World!"))
                .log(LoggingLevel.INFO,"${body}")
                .to("activemq:queue:helloqueue");
    }
}
