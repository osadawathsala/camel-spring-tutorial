package com.decodedbytes.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Osada
 * @Date 1/14/2024
 */
public class InboundRestProcessorBean {
    Logger logger = LoggerFactory.getLogger(InboundRestProcessorBean.class);
    public void validate(Exchange exchange){

        InboundNameAddress nameAddress = exchange
                .getIn()
                .getBody(InboundNameAddress.class);
        exchange.getIn().setHeader("userCity",nameAddress.getCity());

        logger.info("setting up user city : {} ",nameAddress.getCity());

    }
}
