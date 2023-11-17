package fx.price.publisher.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import fx.price.publisher.domain.interfaces.CurrencyConfigurationManager;
import fx.price.publisher.domain.model.CurrencyPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FxPricingMessageParserImplTest {

  @Mock
  CurrencyConfigurationManager configurationManagerMock;
   FxPricingMessageParserImpl underTest;

  @BeforeEach
   void setUp() {

    underTest = new FxPricingMessageParserImpl("dd-MM-yyyy HH:mm:ss:SSS", configurationManagerMock);
  }

  @Test
  void verifyIfMessageParsedCorrectly() {
    // given
    given(configurationManagerMock.isSupported(any(CurrencyPair.class))).willReturn(true);
    var csvMessagePayload = """
        106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
        107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002
        108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
        """;
    // when
    var parsingResult = underTest.parse(csvMessagePayload);
    // then
    assertThat(parsingResult).hasSize(3);
  }

  @Test
  void shouldReturnLinesParsedCorrectly() {
    // given
    given(configurationManagerMock.isSupported(any(CurrencyPair.class))).willReturn(true);
    var csvMessagePayload = """
        dummy unparsable text
        108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002
        another,not, parsable, line
        """;
    // when
    var parsingResult = underTest.parse(csvMessagePayload);
    // then
    assertThat(parsingResult).hasSize(1);
  }

  @Test
  void shouldFailOnIncorrectDateFormat() {
    var csvFaultyMessage = """
        200, EUR/JPY, 119.60,119.90,2021/11/11 18:15:02
        """;
    // when
    var parsingResult = underTest.parse(csvFaultyMessage);
    // then
    assertThat(parsingResult).isEmpty();
  }

  @Test
  void shouldFailOnUnparsableCurrencyPair() {
    var csvFaultyMessage = """
        200, dummy, 119.60,119.90,2021/11/11 18:15:02
        """;
    // when
    var parsingResult = underTest.parse(csvFaultyMessage);
    // then
    assertThat(parsingResult).isEmpty();
  }
}
