package com.decodedbytes.components;

import com.decodedbytes.beans.InboundNameAddress;
import com.decodedbytes.processors.InboundMessageProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * @author Osada
 * @Date 1/17/2024
 */
//@Component
public class BatchJPAProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

       from("timer:readDB?period=10000")
               .routeId("readDBRouteId")
               .to("jpa:"+ InboundNameAddress.class.getName() +"?namedQuery=fetchAllRows")
               .split(body())
                   .process(new InboundMessageProcessor())
                   .log("After converting to outbound : ${body}")
                   .convertBodyTo(String.class)
                   .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
               .toD("jpa:"+InboundNameAddress.class.getName()+"?nativeQuery=DELETE FROM NAME_ADDRESS WHERE ID = ${header.consumedId}&useExecuteUpdate=true")
               .end();//split block ends

    }
}
