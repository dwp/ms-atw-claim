package uk.gov.dwp.health.atw.msclaim.config;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.dwp.health.atw.msclaim.config.properties.EventCryptoConfigProperties;
import uk.gov.dwp.health.crypto.CryptoConfig;
import uk.gov.dwp.health.crypto.CryptoDataManager;
import uk.gov.dwp.health.crypto.exception.CryptoException;

@Slf4j
@Configuration
public class KmsConfig {

  private final EventCryptoConfigProperties eventCryptoConfigProperties;

  public KmsConfig(
      EventCryptoConfigProperties eventCryptoConfigProperties) {
    this.eventCryptoConfigProperties = eventCryptoConfigProperties;
  }

  @Bean
  public CryptoDataManager cryptoDataManager() {
    try {
      CryptoConfig config = this.eventCryptoConfigProperties.getCryptoConfig();

      // The library only checks for null.
      // If the value is empty string it tries to apply kms override
      if (config.getKmsEndpointOverride().isBlank()) {
        config.setKmsEndpointOverride(null);
      }

      return new CryptoDataManager(config);
    } catch (CryptoException
        | NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException e) {
      final String msg =
          String.format("Error config crypto data crypto manager for messaging %s", e.getMessage());
      log.error(msg);
      throw new IllegalStateException(msg);
    }
  }
}
