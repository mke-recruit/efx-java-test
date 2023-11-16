package fx.price.publisher.api.dto;

import java.math.BigDecimal;

public record PricingDto (String currencyPair, BigDecimal bidPrice, BigDecimal askPrice) {
}
