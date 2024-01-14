package com.tutorial.model;

import lombok.*;

/**
 * @author Osada
 * @Date 1/14/2024
 */
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer id;
    private String name;
    private String email;
}
