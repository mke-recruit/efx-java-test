package fx.price.publisher.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "pricing.config")
@Configuration
@Data
public class CurrencyConfigurationProperties {

  List<CurrencyPairConfiguration> currencyPairs;

  @Data
  public static class CurrencyPairConfiguration {
    String pairName;
    int scale;
  }

}
