package com.decodedbytes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Osada
 * @Date 1/15/2024
 */
//@CamelSpringBootTest
//@SpringBootTest
//@UseAdviceWith
public class SimpleTimerTest {
    @Autowired
    CamelContext context;

    @EndpointInject("mock:result")
    MockEndpoint mockEndpoint;

    @Test
    public void testSimpleTimer() throws Exception {

        String expectedBody = "Hello World!";

        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMessageCount(1);

        AdviceWith.adviceWith(context,"SimpleRouteId",
                routeBuilder -> {
                    routeBuilder.weaveAddLast().to(mockEndpoint);
        });

        context.start();

        mockEndpoint.assertIsSatisfied();
    }

}
