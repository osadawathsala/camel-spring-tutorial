package com.tutorial.camel.model;

import lombok.*;

/**
 * @author Osada
 * @Date
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Company {
    private String id;
    private String name;
    private String email;
}
