package com.decodedbytes.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Osada
 * @Date 1/17/2024
 */
//@Component
public class QueueReceiverRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("activemq:queue:nameaddressqueue")
                .routeId("queueReceiverRouteId")
                .log(LoggingLevel.INFO,"The message received from queue : ${body}");

    }
}
