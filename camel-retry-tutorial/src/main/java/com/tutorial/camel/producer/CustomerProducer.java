package com.tutorial.camel.producer;

import com.tutorial.camel.model.Company;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

/**
 * @author Osada
 * @Date 12/3/2024
 */
@Component
public class CustomerProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
       from("timer:dummy?period=10000")
               .setBody(this::createDummyEvent)
               .marshal().json(JsonLibrary.Jackson)
               .to("activemq:{{activemq.endpoint}}");
    }

    private Company createDummyEvent(Exchange exchange) {
        Company dummy = Company
                .builder()
                .id("cus_1978")
                .name("Osada")
                .email("osadasliit@gmail.com")
                .build();
        return dummy;
    }
}
