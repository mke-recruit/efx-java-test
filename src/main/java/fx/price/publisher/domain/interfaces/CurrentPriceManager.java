package fx.price.publisher.domain.interfaces;

import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface CurrentPriceManager {
  void notifyPriceUpdates(Map<CurrencyPair, Pricing> newPrices);
  Collection<Pricing> getAllCurrentPrices();
  Optional<Pricing> getPricing(CurrencyPair currencyPair);
}
