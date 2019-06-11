package org.dskim.nitrite;

import org.dizitart.no2.Nitrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NitriteApplication {
    static String dbFilePath = "/tmp/test.db";

    @Bean
    public Nitrite nitrite() {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath(dbFilePath)
                .openOrCreate()
                ;
        return db;
    }

    public static void main(String[] args) {
        SpringApplication.run(NitriteApplication.class, args);
    }
}
