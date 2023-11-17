package fx.price.publisher.services;

import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TestDataFactory {
  public static final Instant PRICE_TIME_1 = OffsetDateTime.of(2023, 11, 10, 12, 20, 0, 5, ZoneOffset.UTC).toInstant();
  public static final Instant PRICE_TIME_2 =
      OffsetDateTime.of(2023, 11, 10, 12, 20, 0, 15, ZoneOffset.UTC).toInstant();
  public static final Instant PRICE_TIME_3 =
      OffsetDateTime.of(2023, 11, 10, 12, 20, 0, 35, ZoneOffset.UTC).toInstant();

  public static final BigDecimal BID_PRICE_1 = new BigDecimal("1.000");
  public static final BigDecimal ASK_PRICE_1 = new BigDecimal("1.100");
  public static final BigDecimal BID_PRICE_2 = new BigDecimal("2.000");
  public static final BigDecimal ASK_PRICE_2 = new BigDecimal("2.100");
  public static final BigDecimal BID_PRICE_3 = new BigDecimal("3.000");
  public static final BigDecimal ASK_PRICE_3 = new BigDecimal("3.100");
  public static final CurrencyPair EUR_USD = CurrencyPair.ofName("EUR/USD");



  private TestDataFactory() {
  }

  public static Pricing createPricing(String pairName, BigDecimal bidPrice, BigDecimal askPrice, Instant pricingTime) {
    return createPricing(CurrencyPair.ofName(pairName), bidPrice, askPrice, pricingTime);
  }

  public static Pricing createPricing(CurrencyPair pair, BigDecimal bidPrice, BigDecimal askPrice,
      Instant pricingTime) {
    return Pricing.builder()
        .pair(pair)
        .askPrice(askPrice)
        .bidPrice(bidPrice)
        .pricingTime(pricingTime)
        .build();
  }
}
