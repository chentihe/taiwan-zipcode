package com.susi.zipcode.taiwan_zipcode.importer;

import com.susi.zipcode.taiwan_zipcode.repository.ZipcodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private ZipcodeRepository repository;

    private JsonImporter jsonImporter;

    public DataInitializer(JsonImporter jsonImporter, ZipcodeRepository repository) {
        this.repository = repository;
        this.jsonImporter = jsonImporter;
    }

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
