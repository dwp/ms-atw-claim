package uk.gov.dwp.health.atw.msclaim.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import uk.gov.dwp.health.crypto.CryptoConfig;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "uk.gov.dwp.health.event")
public class EventCryptoConfigProperties {
  CryptoConfig cryptoConfig;
}
