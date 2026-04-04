package com.susi.zipcode.taiwan_zipcode.importer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;
import com.susi.zipcode.taiwan_zipcode.repository.ZipcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonImporter {
    private final ZipcodeRepository repository;
    private final ObjectMapper objectMapper;

    public void importJson(InputStream inputStream) throws IOException {
        JsonNode root = objectMapper.readTree(inputStream);
        ArrayList<ZipcodeReference> batchList = new ArrayList<>();

        root.properties().forEach(cityEntry -> {
            String cityName = cityEntry.getKey();
            JsonNode areas = cityEntry.getValue().get("areas");

            areas.properties().forEach(areaEntry -> {
                String areaName = areaEntry.getKey();
                JsonNode roads = areaEntry.getValue().get("roads");

                roads.properties().forEach(roadEntry -> {
                    String roadName = roadEntry.getKey();
                    JsonNode scopes = roadEntry.getValue().get("scopes");

                    for (JsonNode scopeNode : scopes) {
                        String scopeName = scopeNode.get("scope").asText();

                        ZipcodeReference ref = new ZipcodeReference();
                        ref.setCity(cityName);
                        ref.setArea(areaName);
                        ref.setRoad(roadName);
                        ref.setScope(scopeName);
                        ref.setZipcode(scopeNode.get("zipcode").asText());

                        if (scopeNode.has("department")) {
                            ref.setDepartment(scopeNode.get("department").asText());
                        }

                        ScopeParser.parseAndSet(scopeName, ref);

                        batchList.add(ref);
                        if (batchList.size() >= 1000) {
                            repository.saveAll(batchList);
                            batchList.clear();
                        }
                    }
                });
            });
        });

        if (!batchList.isEmpty()) {
            repository.saveAll(batchList);
        }
        log.info("zipcode data import successfully");
    }
}
