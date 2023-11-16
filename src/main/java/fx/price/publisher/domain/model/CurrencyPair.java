package fx.price.publisher.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyPair {

  String base;
  String quote;

  public static CurrencyPair ofName(String currencyPair) {
    var currencies = currencyPair.split("/");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static CurrencyPair ofShortName(String currencyPairName) {
    return new CurrencyPair(currencyPairName.substring(0,3), currencyPairName.substring(3,6));
  }

  @Override public String toString() {
    return String.format("%s/%s", base, quote);
  }
}
