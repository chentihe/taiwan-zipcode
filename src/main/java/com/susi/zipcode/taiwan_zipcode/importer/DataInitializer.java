package com.susi.zipcode.taiwan_zipcode.importer;

import com.susi.zipcode.taiwan_zipcode.repository.ZipcodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final ZipcodeRepository repository;
    private final JsonImporter jsonImporter;

    @Override
    public void run(String ...args) throws Exception {
        long count = repository.count();

        if (count == 0) {
            log.info("database is empty, starting importing zipcode.json...");
            ClassPathResource resource = new ClassPathResource("zipcode.json");
            jsonImporter.importJson(resource.getInputStream());
            log.info("import successfully");
        } else {
            log.info("already has " + count + " data, skip initialization");
        }
    }
}
