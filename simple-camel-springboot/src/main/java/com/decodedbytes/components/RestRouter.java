package com.decodedbytes.components;

import com.decodedbytes.beans.InboundNameAddress;
import com.decodedbytes.beans.InboundRestProcessorBean;
import jakarta.jms.JMSException;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

import org.apache.camel.LoggingLevel;

/**
 * @author Osada
 * @Date 1/16/2024
 */

@Component
public class RestRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        Predicate isCityAjax = header("userCity").isEqualTo("Ajax");

       restConfiguration()
               .component("jetty")
               .host("localhost")
               .port(8003).bindingMode(RestBindingMode.json)
               .enableCORS(true);

        onException(JMSException.class, ConnectException.class)
                .handled(true)
                .log(LoggingLevel.INFO, "JMS connection could not be established");

        rest().produces("application/json").post("nameAddress")
                .type(InboundNameAddress.class).outType(String.class)
                .routeId("restRouterId")
                .to("direct:processRequest");

               from("direct:processRequest")
                       .routeId("ProcessRequestId")
                       .bean(new InboundRestProcessorBean())
                       //.log(LoggingLevel.INFO, String.valueOf(simple("${body}")))
                      // .bean(new InboundRestProcessorBean(),"validateAnother") //calling different methods
                       //set up rule
                       //if city == AJAX then send request to MQ
                       //otherwise send to both
                           .choice()
                                .when(isCityAjax)
                                .to("seda:messageRoute")
                            .otherwise()
                                .to("direct:persistMessage")
                                .to("seda:messageRoute")
                           .end()
                       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)) // changing the Response code to 201 to validate in Postman
                       //.transform().simple("Message processed Successfully: ${body}")
                       .setBody(simple("Message processed Successfully: ${body}")) //This will convert the body to String as defined by the outType method in rest definition
                       .end();

               from("direct:persistMessage")
                       .routeId("persistMessageRouteId")
                       .log(LoggingLevel.INFO, "--> Saving to DB")
                       .to("jpa:"+InboundNameAddress.class.getName());

               from("seda:messageRoute")
                       .routeId("messageRouteId")
                       .to("activemq:queue:nameaddressqueue?exchangePattern=InOnly");
               
    }
}
