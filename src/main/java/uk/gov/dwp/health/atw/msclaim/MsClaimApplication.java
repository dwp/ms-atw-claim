package uk.gov.dwp.health.atw.msclaim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MsClaimApplication {
  public static void main(String[] args) {
    SpringApplication.run(MsClaimApplication.class, args);
  }
}

