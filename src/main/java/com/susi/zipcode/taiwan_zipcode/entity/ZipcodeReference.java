package com.susi.zipcode.taiwan_zipcode.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zipcode_references", indexes = {
        @Index(name = "idx_zipcode", columnList = "zipcode"),
        @Index(name = "idx_city_area", columnList = "city, area")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZipcodeReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String area;
    private String road;
    private String scope;
    private String zipcode;

    private String scopeType; // "SINGLE" (單), "DOUBLE" (雙), "ALL" (連、全)
    private Integer minNum;
    private Integer maxNum;
}
