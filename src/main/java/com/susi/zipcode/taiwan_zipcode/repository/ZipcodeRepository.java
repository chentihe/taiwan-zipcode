package com.susi.zipcode.taiwan_zipcode.repository;

import com.susi.zipcode.taiwan_zipcode.dto.AddressDTO;
import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZipcodeRepository extends JpaRepository<ZipcodeReference, Long> {

    @Query("SELECT DISTINCT z.city FROM ZipcodeReference z")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT z.area FROM ZipcodeReference z WHERE z.city = :city")
    List<String> findAreasByCity(String city);

    @Query("SELECT DISTINCT z.road FROM ZipcodeReference z WHERE z.city = :city AND z.area = :area")
    List<String> findRoadsByCityAndArea(String city, String area);

    List<ZipcodeReference> findByZipcodeStartingWith(String zipcode);

    @Query("SELECT DISTINCT new com.susi.zipcode.taiwan_zipcode.dto.AddressDTO(z.city, z.area, z.road, null, null) " +
    "FROM ZipcodeReference z WHERE z.zipcode = :zipcode")
    List<AddressDTO> findAddressByZipcode(String zipcode);

    List<ZipcodeReference> findByCityAndAreaAndRoad(String city, String area, String road);

    @Query("SELECT z FROM ZipcodeReference z " +
    "WHERE z.city = :city " +
    "AND z.area = :area " +
    "AND z.road = :road " +
    "AND :weight BETWEEN z.minNum AND z.maxNum " +
    "AND (z.scopeType = 'ALL' OR z.scopeType = :scopeType) " +
    "ORDER BY (z.maxNum - z.minNum) ASC")
    List<ZipcodeReference> findMatchingZipcodes(
            String city,
            String area,
            String road,
            Long weight,
            String scopeType
    );
}
