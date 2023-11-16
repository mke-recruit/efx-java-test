package fx.price.publisher.services;

import fx.price.publisher.domain.interfaces.LatestPricingExtractor;
import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LatestPricingExtractorImpl implements LatestPricingExtractor {
  @Override public Map<CurrencyPair, Pricing> getLatestForEachPair(List<Pricing> pricingList) {
    return pricingList.stream()
        .collect(Collectors.groupingBy(Pricing::pair))
        .entrySet()
        .stream()
        .map(entry -> Map.entry(entry.getKey(),this.getLatest(entry.getValue())))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
  private Pricing getLatest(List<Pricing> samePairPricing) {
    return samePairPricing.stream()
        .sorted(Comparator.comparing(Pricing::pricingTime))
        .findFirst()
        .get();
  }
}
