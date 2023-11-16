package fx.price.publisher.services;

import fx.price.publisher.domain.interfaces.CurrentPriceManager;
import fx.price.publisher.domain.interfaces.PriceAdjustmentService;
import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentPriceManagerImpl implements CurrentPriceManager {

  private final PriceAdjustmentService priceAdjustmentService;
  private ConcurrentMap<CurrencyPair, Pricing> currentPricesMap = new ConcurrentHashMap<>();

  @Override public void notifyPriceUpdates(Map<CurrencyPair, Pricing> newPrices) {
    newPrices.values()
        .stream()
        .map(priceAdjustmentService::addCommission)
        .forEach(this::updatePrice);
  }

  private void updatePrice(Pricing pricing) {
    currentPricesMap.merge(pricing.pair(), pricing, this::getLatest);
  }

  private Pricing getLatest(Pricing actualPricing, Pricing newPricing) {
    return newPricing.pricingTime().isAfter(actualPricing.pricingTime()) ? newPricing : actualPricing;
  }

  @Override public Collection<Pricing> getAllCurrentPrices() {
    return currentPricesMap.values();
  }

  @Override public Optional<Pricing> getPricing(CurrencyPair currencyPair) {
    return Optional.ofNullable(currentPricesMap.get(currencyPair));
  }
}
