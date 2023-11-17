package fx.price.publisher.domain.interfaces;

import fx.price.publisher.domain.model.CurrencyPair;

public interface CurrencyConfigurationManager {

  boolean isSupported(CurrencyPair currencyPair);

  int getScale(CurrencyPair currencyPair);
}
