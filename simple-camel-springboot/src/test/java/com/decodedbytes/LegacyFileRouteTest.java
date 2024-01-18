package com.decodedbytes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Osada
 * @Date 1/15/2024
 */
@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
public class LegacyFileRouteTest {

    @Autowired
    CamelContext context;

    @Autowired
    ProducerTemplate producerTemplate;

    @EndpointInject("mock:result")
    MockEndpoint mockEndpoint;

    @Test
    public void fileMovedTest() throws Exception{

        //setting up Mock
        String expectedBody = "This is input file";
        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMinimumMessageCount(1);

        //tweak message definition
        AdviceWith.adviceWith(context,
                "legacyFileMoveId", routeBuilder -> {
                    routeBuilder
                            .weaveByToUri("file:*")
                            .replace()
                            .to(mockEndpoint);
                });
        //start context and validate test

        context.start();

        mockEndpoint.assertIsSatisfied();

    }
    @Test
    public void fileMovedByMockingFromEndpoint() throws Exception{

        String expectedBody = "OutboundMessage(name=Osada, address=Watarappala WP Watarappala Watarappala)";
        mockEndpoint.expectedBodiesReceived(expectedBody);

        AdviceWith.adviceWith(context,
                "legacyFileMoveId",routeBuilder ->{
                    routeBuilder.replaceFromWith("direct:mockStart");
                    routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
                });
        context.start();

        String inputTemplate = "name,house_number,city,province,country Osada,40,Watarappala,WP,Sri lanka";

        producerTemplate.sendBody("direct:mockStart",expectedBody);
        mockEndpoint.assertIsSatisfied();

    }
}
