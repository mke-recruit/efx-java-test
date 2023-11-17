package fx.price.publisher.services;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fx.price.publisher.domain.interfaces.CurrentPriceManager;
import fx.price.publisher.domain.interfaces.FxPricingMessageParser;
import fx.price.publisher.domain.interfaces.LatestPricingExtractor;
import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PricingMessageListenerImplTest {
  @Mock
  FxPricingMessageParser messageParser;
  @Mock
  LatestPricingExtractor latestPricingExtractor;
  @Mock
  CurrentPriceManager priceManager;
  @InjectMocks
  PricingMessageListenerImpl underTest;

  @Test
  void shouldReceiveMessage() {
    // given
    var messagePayload = "Some dummy message";
    var pricingList = Collections.<Pricing>emptyList();
    var uniquePricingMap = Collections.<CurrencyPair, Pricing>emptyMap();
    given(messageParser.parse(messagePayload)).willReturn(pricingList);
    given(latestPricingExtractor.getLatestForEachPair(pricingList)).willReturn(uniquePricingMap);
    // when
    underTest.onMessage(messagePayload);
    // then
    verify(messageParser).parse(messagePayload);
    verify(latestPricingExtractor, times(1)).getLatestForEachPair(pricingList);
    verify(priceManager, times(1)).notifyPriceUpdates(uniquePricingMap);
  }
}
