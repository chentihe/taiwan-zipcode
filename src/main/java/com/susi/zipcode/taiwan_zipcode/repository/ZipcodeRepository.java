package com.susi.zipcode.taiwan_zipcode.repository;

import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZipcodeRepository extends JpaRepository<ZipcodeReference, Long> {

    @Query("SELECT DISTINCT z.city FROM ZipcodeReference z")
    List<String> findDistinctCities();

    @Query("SELECT DISTINCT z.area FROM ZipcodeReference z WHERE z.city = :city")
    List<String> findAreasByCity(String city);

    List<ZipcodeReference> findTop10ByCityAndAreaAndRoadContaining(String city, String area, String road);

    List<ZipcodeReference> findByZipcodeStartingWith(String zipcode);
}
