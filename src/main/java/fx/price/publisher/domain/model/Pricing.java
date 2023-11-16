package fx.price.publisher.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;

@Builder(toBuilder = true)
public record Pricing(CurrencyPair pair, BigDecimal bidPrice, BigDecimal askPrice, Instant pricingTime) {
}
