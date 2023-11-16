package fx.price.publisher.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FxPricingMessageParserImplTest {

  private FxPricingMessageParserImpl underTest = new FxPricingMessageParserImpl("dd-MM-yyyy HH:mm:ss:SSS");

  private static final String testMessage = """
      107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
      108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
      109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100
      """;

  @Test
  void verifyIfMessageParsedCorrectly() {
    // given textMessage

    // when
    var parsingResult = underTest.parse(testMessage);

    // then
    assertThat(parsingResult).hasSize(3);
  }

}
