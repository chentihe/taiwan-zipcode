package com.susi.zipcode.taiwan_zipcode.service;

import com.susi.zipcode.taiwan_zipcode.dto.AddressDTO;
import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;
import com.susi.zipcode.taiwan_zipcode.repository.ZipcodeRepository;
import com.susi.zipcode.taiwan_zipcode.util.AddressFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ZipcodeService {
    private final ZipcodeRepository repository;

    public List<String> findDistinctCities() {
        return repository.findDistinctCities();
    }

    public List<String> findAreasByCity(String city) {
        String cityKey = city.replace("台", "臺");
        return repository.findAreasByCity(cityKey);
    }

    public List<String> findRoadsByCityAndArea(String city, String area) {
        String cityKey = city.replace("台", "臺");
        return repository.findRoadsByCityAndArea(city, area);
    }

    public List<AddressDTO> findAddressByZipcode(String zipcode) {
        return repository.findAddressByZipcode(zipcode);
    }

    public String findZipcodeByAddress(String city, String area, String road, String scope) {
        String cityKey = city.replace("台", "臺");

        AddressFeature feature = AddressFeature.parse(scope);
        log.info("Parsed Address: lane={}, alley={}, number={}, isOdd={}, weight={}",
                feature.getLane(), feature.getAlley(), feature.getNumber(), feature.isOdd(), feature.getWeight());
        Long weight = feature.getWeight();
        String scopeType = feature.isOdd() ? "SINGLE" : "DOUBLE";

        List<ZipcodeReference> results = repository.findMatchingZipcodes(
                cityKey,
                area,
                road,
                weight,
                scopeType
        );

        return results.isEmpty() ? null : results.get(0).getZipcode();
    }
}
