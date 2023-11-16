package fx.price.publisher.domain.interfaces;

import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.util.List;
import java.util.Map;

public interface LatestPricingExtractor {
  Map<CurrencyPair, Pricing> getLatestForEachPair(List<Pricing> pricingList);
}
