package com.susi.zipcode.taiwan_zipcode.controller;

import com.susi.zipcode.taiwan_zipcode.dto.AddressDTO;
import com.susi.zipcode.taiwan_zipcode.service.ZipcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
@CrossOrigin(origins = "*")
public class AddressController {
    private final ZipcodeService service;

    @GetMapping("cities")
    public ResponseEntity<List<String>> getCities() {
        return ResponseEntity.ok(service.findDistinctCities());
    }

    @GetMapping("/areas")
    public ResponseEntity<List<String>> getAreas(@RequestParam String city) {
        return ResponseEntity.ok(service.findAreasByCity(city));
    }

    @GetMapping("/roads")
    public ResponseEntity<List<String>> searchRoads(
            @RequestParam String city,
            @RequestParam String area
    ) {
        return ResponseEntity.ok(service.findRoadsByCityAndArea(city, area));
    }

    @GetMapping("/by-zipcode")
    public ResponseEntity<List<AddressDTO>> getAddressByZipcode(@RequestParam String zipcode) {
        return ResponseEntity.ok(service.findAddressByZipcode(zipcode));
    }

    @GetMapping("/zipcode")
    public ResponseEntity<String> getZipcodeByAddress(
            @RequestParam String city,
            @RequestParam String area,
            @RequestParam String road,
            @RequestParam String scope
            ) {
        return ResponseEntity.ok(service.findZipcodeByAddress(city, area, road, scope));
    }
}
