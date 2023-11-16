package fx.price.publisher.services;

import fx.price.publisher.config.CurrencyConfigurationProperties;
import fx.price.publisher.config.CurrencyConfigurationProperties.CurrencyPairConfiguration;
import fx.price.publisher.domain.model.CurrencyPair;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConfigurationManager {

  private final Map<CurrencyPair, Integer> currencyPairConfigMap;

  public CurrencyConfigurationManager(CurrencyConfigurationProperties properties) {
    currencyPairConfigMap = properties.getCurrencyPairs().stream()
        .collect(Collectors.toMap(conf -> CurrencyPair.ofName(conf.getPairName()), CurrencyPairConfiguration::getScale));
  }

  public boolean isSupported(CurrencyPair currencyPair) {
    return currencyPairConfigMap.containsKey(currencyPair);
  }

  public int getScale(CurrencyPair currencyPair) {
    return currencyPairConfigMap.get(currencyPair);
  }

}
