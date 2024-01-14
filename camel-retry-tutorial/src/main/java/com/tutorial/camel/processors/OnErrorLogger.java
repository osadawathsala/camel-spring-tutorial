package com.tutorial.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Osada
 * @Date 12/3/2024
 */
public class OnErrorLogger implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String msg = "Something went wrong due to " + cause.getMessage();
    }
}
