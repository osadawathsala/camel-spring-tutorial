package com.decodedbytes.processors;

import com.decodedbytes.beans.OutboundMessage;
import com.decodedbytes.beans.InboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Osada
 * @Date 1/16/2024
 */
public class InboundMessageProcessor implements Processor {
    Logger logger = LoggerFactory.getLogger(InboundMessageProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        InboundNameAddress fileData = exchange.getIn().getBody(InboundNameAddress.class);
        logger.info("{}",fileData.toString());
        exchange.getIn().setBody(new OutboundMessage(fileData.getName(),getOutboundAddress(fileData)));
        exchange.getIn().setHeader("consumedId",fileData.getId());

    }

    public String getOutboundAddress(InboundNameAddress fileData){
        StringBuilder sb = new StringBuilder();
        sb.append(fileData.getHouseNumber());
        sb.append(" " + fileData.getCity());
        sb.append(" " + fileData.getProvince());
        sb.append(" " + fileData.getCity());
        sb.append(" " + fileData.getPostalCode());
        return sb.toString();
    }
}
