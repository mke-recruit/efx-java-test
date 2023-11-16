package fx.price.publisher.services;

import fx.price.publisher.domain.model.Pricing;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import fx.price.publisher.domain.interfaces.PriceAdjustmentService;

@Service
public class PriceAdjustmentServiceImpl implements PriceAdjustmentService {

  private static final BigDecimal BID_COMMISSION_MULTIPLIER = new BigDecimal("1.001");
  private static final BigDecimal ASK_COMMISSION_MULTIPLIER = new BigDecimal("0.999");
  @Override public Pricing addCommission(Pricing originalPricing) {
    return originalPricing.toBuilder()
        .askPrice(BID_COMMISSION_MULTIPLIER.multiply(originalPricing.askPrice()).setScale(4, RoundingMode.HALF_EVEN))
        .bidPrice(ASK_COMMISSION_MULTIPLIER.multiply(originalPricing.bidPrice()).setScale(4, RoundingMode.HALF_EVEN))
        .build();
  }
}
