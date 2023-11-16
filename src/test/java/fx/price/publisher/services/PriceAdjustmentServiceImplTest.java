package fx.price.publisher.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceAdjustmentServiceImplTest {

  private static final CurrencyPair EUR_USD = CurrencyPair.ofName("EUR/USD");

  @Mock
  CurrencyConfigurationManager configurationManager;

  @InjectMocks
  PriceAdjustmentServiceImpl underTest;


  @Test
  void shouldReturnPricesWithCommissionCalculated() {
    // given
    var initialPricing = Pricing.builder()
        .pair(EUR_USD)
        .askPrice(new BigDecimal("1.3456"))
        .bidPrice(new BigDecimal("1.3269"))
        .build();
    given(configurationManager.getScale(EUR_USD)).willReturn(4);

    // when
    var pricingWithCommission = underTest.addCommission(initialPricing);

    // then
    assertThat(pricingWithCommission.askPrice()).isEqualByComparingTo("1.3469");
    assertThat(pricingWithCommission.bidPrice()).isEqualByComparingTo("1.3256");
  }

}
