package com.db.recon.reconservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication

@EnableCaching   //  Without it @Cacheable silently does nothing.
@EnableKafka
public class ReconServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReconServiceApplication.class, args);
    }

}
/*@EnableCaching tells Spring to create AOP proxies around any bean method annotated with @Cacheable.
 Without it, the annotation is present but completely ignored — no error, no warning, just no caching.
*/
