package fx.price.publisher.services;


import static fx.price.publisher.services.TestDataFactory.ASK_PRICE_1;
import static fx.price.publisher.services.TestDataFactory.ASK_PRICE_2;
import static fx.price.publisher.services.TestDataFactory.ASK_PRICE_3;
import static fx.price.publisher.services.TestDataFactory.BID_PRICE_1;
import static fx.price.publisher.services.TestDataFactory.BID_PRICE_2;
import static fx.price.publisher.services.TestDataFactory.BID_PRICE_3;
import static fx.price.publisher.services.TestDataFactory.EUR_USD;
import static fx.price.publisher.services.TestDataFactory.PRICE_TIME_1;
import static fx.price.publisher.services.TestDataFactory.PRICE_TIME_2;
import static fx.price.publisher.services.TestDataFactory.PRICE_TIME_3;
import static fx.price.publisher.services.TestDataFactory.createPricing;
import static org.assertj.core.api.Assertions.assertThat;

import fx.price.publisher.domain.interfaces.PriceAdjustmentService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrentPriceManagerImplTest {

  private final PriceAdjustmentService noOpPriceAdjustmentService = x -> x;

  CurrentPriceManagerImpl underTest;


  @BeforeEach
  void setUp() {
    underTest = new CurrentPriceManagerImpl(noOpPriceAdjustmentService);
  }

  @Test
  void shouldReturnEmptyCollectionWhenNoPricing() {
    assertThat(underTest.getAllCurrentPrices()).isEmpty();
  }

  @Test
  void shouldRespondWithLatestPrice() {
    // given
    var pricing1 = createPricing(EUR_USD, BID_PRICE_1, ASK_PRICE_1, PRICE_TIME_1);
    var pricing2 = createPricing(EUR_USD, BID_PRICE_2, ASK_PRICE_2, PRICE_TIME_2);
    var pricing3 = createPricing(EUR_USD, BID_PRICE_3, ASK_PRICE_3, PRICE_TIME_3);
    underTest.notifyPriceUpdates(Map.of(EUR_USD, pricing1));
    underTest.notifyPriceUpdates(Map.of(EUR_USD, pricing3));
    underTest.notifyPriceUpdates(Map.of(EUR_USD, pricing2));
    // when
    var result = underTest.getPricing(EUR_USD);
    // then
    assertThat(result).contains(pricing3);
  }
}
