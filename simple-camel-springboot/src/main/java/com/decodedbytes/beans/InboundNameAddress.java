package com.decodedbytes.beans;


import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;


/**
 * @author Osada
 * @Date 1/16/2024
 */
@Entity
@Table(name = "NAME_ADDRESS")
@Data
@NamedQuery(name = "fetchAllRows",query = "select x from InboundNameAddress x")
public class InboundNameAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "house_number")
    private String houseNumber;

    private String city;

    private String province;

    @Column(name = "postal_code")
    private String postalCode;

}
