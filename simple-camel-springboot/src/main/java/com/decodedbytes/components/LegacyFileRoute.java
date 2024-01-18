package com.decodedbytes.components;

import com.decodedbytes.processors.InboundMessageProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Osada
 * @Date 1/15/2024
 */
//@Component
public class LegacyFileRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(LegacyFileRoute.class);

    BeanIODataFormat beanIODataFormat = new BeanIODataFormat("inboundMessageBeanIOMapping.xml","inboundPersonFile");

    @Override
    public void configure() throws Exception {

        from("file:src/data/input?fileName=inputFile.csv")
                .routeId("legacyFileMoveId")
                .split(body().tokenize("\n",1,true))
                .unmarshal(beanIODataFormat)
                .log("Before converting to outbound : ${body}")
                .process(new InboundMessageProcessor())
                .log("After converting to outbound : ${body}")
                .convertBodyTo(String.class)
                .to("file:src/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();//split block ends

    }
}
