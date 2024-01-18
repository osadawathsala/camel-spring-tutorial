package com.decodedbytes.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Osada
 * @Date 1/14/2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutboundMessage {
    private String name;
    private String address;
}
