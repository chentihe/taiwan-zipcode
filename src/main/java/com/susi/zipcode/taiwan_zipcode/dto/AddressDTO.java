package com.susi.zipcode.taiwan_zipcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {
    private String city;
    private String area;
    private String road;
    private String zipcode;
    private String scope;
}
