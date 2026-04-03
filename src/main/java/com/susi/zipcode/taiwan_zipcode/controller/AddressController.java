package com.susi.zipcode.taiwan_zipcode.controller;

import com.susi.zipcode.taiwan_zipcode.dto.AddressDTO;
import com.susi.zipcode.taiwan_zipcode.repository.ZipcodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin(origins = "*")
public class AddressController {
    private ZipcodeRepository repository;

    public AddressController(ZipcodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/areas")
    public ResponseEntity<List<String>> getAreas(@RequestParam String city) {
        String cityKey = city.replace("台", "臺");
        return ResponseEntity.ok(repository.findAreasByCity(cityKey));
    }

    @GetMapping("/roads")
    public ResponseEntity<List<AddressDTO>> searchRoads(
            @RequestParam String city,
            @RequestParam String area,
            @RequestParam String keyword
    ) {
        String searchKey = keyword.replace("台", "臺");

        List<AddressDTO> results = repository.findTop10ByCityAndAreaAndRoadContaining(city, area, searchKey)
                .stream()
                .map(z -> new AddressDTO(z.getCity(), z.getArea(), z.getRoad(), z.getZipcode(), z.getScope()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
}
