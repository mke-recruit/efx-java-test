package fx.price.publisher;

import static org.assertj.core.api.Assertions.assertThat;

import fx.price.publisher.domain.model.CurrencyPair;
import org.junit.jupiter.api.Test;

class DummyTest {

  @Test
  void shouldReturnPairOfSomeName() {
    // given
    var shorthandName = "USDJPY";
    // when
    var pair = CurrencyPair.ofShortName(shorthandName);
    // then
    assertThat(pair).isEqualTo(CurrencyPair.ofName("USD/JPY"));
  }

}
