package fx.price.publisher.services;

import fx.price.publisher.domain.interfaces.CurrencyConfigurationManager;
import fx.price.publisher.domain.model.Pricing;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fx.price.publisher.domain.interfaces.PriceAdjustmentService;

@Service
@RequiredArgsConstructor
public class PriceAdjustmentServiceImpl implements PriceAdjustmentService {

  private final CurrencyConfigurationManager configurationManager;
  private static final BigDecimal BID_COMMISSION_MULTIPLIER = new BigDecimal("1.001");
  private static final BigDecimal ASK_COMMISSION_MULTIPLIER = new BigDecimal("0.999");
  @Override public Pricing addCommission(Pricing originalPricing) {
    var targetScale = configurationManager.getScale(originalPricing.pair());
    return originalPricing.toBuilder()
        .askPrice(BID_COMMISSION_MULTIPLIER.multiply(originalPricing.askPrice()).setScale(targetScale, RoundingMode.HALF_EVEN))
        .bidPrice(ASK_COMMISSION_MULTIPLIER.multiply(originalPricing.bidPrice()).setScale(targetScale, RoundingMode.HALF_EVEN))
        .build();
  }
}
