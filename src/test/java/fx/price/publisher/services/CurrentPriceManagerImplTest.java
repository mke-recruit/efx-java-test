package fx.price.publisher.services;


import static org.assertj.core.api.Assertions.assertThat;

import fx.price.publisher.domain.interfaces.PriceAdjustmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CurrentPriceManagerImplTest {


  private final PriceAdjustmentService noOpPriceAdjustmentService =  x -> x;

  CurrentPriceManagerImpl underTest;


  @BeforeEach
  void setUp() {
    underTest = new CurrentPriceManagerImpl(noOpPriceAdjustmentService);
  }

  @Test
  void shouldReturnEmpty() {
    assertThat(underTest.getAllCurrentPrices()).isEmpty();
  }

}
