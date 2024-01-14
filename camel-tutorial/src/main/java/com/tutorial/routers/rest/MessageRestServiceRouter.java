package com.tutorial.routers.rest;

import com.tutorial.model.Message;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

/**
 * @author Osada
 * @Date 1/14/2024
 */
@Component
public class MessageRestServiceRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);

        rest("/messages").description("Message REST service").consumes("application/json")
        .produces("application/json")
                .get().description("Find all messages").outType(Message[].class)
                .responseMessage().code(200).message("All Messages successfully returned").endResponseMessage()
                .to("bean:messageService?method=findMessages")

                .get("/{id}").description("Find user by ID")
                .outType(Message.class)
                .param().name("id").type(path).description("The ID of the message").dataType("integer").endParam()
                .responseMessage().code(200).message("Message successfully returned").endResponseMessage()
                .to("bean:messageService?method=findMessageById(${header.id})")

                .put("/{id}")
                .description("Update a Message")
                .type(Message.class)
                .param().name("id").type(path).description("The ID of the message to update").dataType("integer").endParam()
                .param().name("body").type(body).description("The message to update").endParam()
                .responseMessage().code(204).message("Message successfully updated").endResponseMessage()
                .to("direct:update-message");

                from("direct:update-message")
                .to("bean:messageService?method=updateMessage")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                .setBody(constant(""));

   //http://localhost:8002/api/activemq/public
   // send messages to queue
    rest().post("activemq/public").produces(MediaType.APPLICATION_JSON_VALUE).consumes(MediaType.APPLICATION_JSON_VALUE)
          .type(Message.class).param().name("body").type(body)
          .description("The message queue update").endParam()
          .to("activemq:queue:publicQueue?exchangePattern=InOnly")
          .responseMessage().code(204).message("Message queue successfully updated").endResponseMessage();

                      // consume messages from queue
    from("activemq:queue:publicQueue").log("Reading public message incoming - ${body}").end();

    }

}
