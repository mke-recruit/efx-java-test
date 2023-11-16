package fx.price.publisher.services;

import fx.price.publisher.domain.interfaces.CurrentPriceManager;
import fx.price.publisher.domain.interfaces.FxPricingMessageParser;
import fx.price.publisher.domain.interfaces.LatestPricingExtractor;
import fx.price.publisher.domain.interfaces.PricingMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingMessageListenerImpl implements PricingMessageListener {

  private final FxPricingMessageParser messageParser;
  private final LatestPricingExtractor latestPricingExtractor;
  private final CurrentPriceManager priceManager;

  @Override public void onMessage(String message) {
    priceManager.notifyPriceUpdates(
        latestPricingExtractor.getLatestForEachPair(
            messageParser.parse(message)));
  }
}
