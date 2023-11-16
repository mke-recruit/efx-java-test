package fx.price.publisher.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class CurrencyConfigurationProperties {

  List<CurrencyPairConfiguration> currencyPairs;

  @Data
  public static class CurrencyPairConfiguration {
    String pairName;
    int scale;
  }

}
