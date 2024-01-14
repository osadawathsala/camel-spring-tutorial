package com.tutorial.camel.subscriber;

import com.tutorial.camel.utils.DLQEndpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import com.tutorial.camel.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Osada
 * @Date 12/3/2024
 */
@Component
public class CustomerSubscriber extends RouteBuilder {
    Logger logger = LoggerFactory.getLogger(CustomerSubscriber.class);
    @Autowired
    DLQEndpoint dlqEndpoint;

    @Override
    public void configure() throws Exception {

          errorHandler(dlqEndpoint
                  .dlq("activemq:{{activemq.dlq.endpoint}}"));
//        errorHandler(
//                deadLetterChannel("activemq:customer.message.dead")
//                        .useOriginalMessage()
//                        .maximumRedeliveries(3)
//                        .redeliveryDelay(5000)
//                        .useExponentialBackOff()
//                        .backOffMultiplier(2)
//                        .maximumRedeliveryDelay(20000)
//                        .onExceptionOccurred(new OnErrorLogger())
//                        .onPrepareFailure(new PrepareProcessor()));

        from("activemq:{{activemq.endpoint}}")
                 .unmarshal().json(JsonLibrary.Jackson, Company.class)
                 .bean(this);
    }
    public void process(Company event){
        logger.info("message : {} " ,event.toString());
        sendWelcomeEmail(event.getEmail(),event.getName());
    }

    public void sendWelcomeEmail(String address, String name) {
        log.info("Attempting to send e-mail; address={}, name={}", address, name);
        throw new IllegalStateException("Service Unavailable; " + LocalDateTime.now());
    }
}
