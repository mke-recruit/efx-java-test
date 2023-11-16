package fx.price.publisher.domain.interfaces;

import fx.price.publisher.domain.model.Pricing;

public interface PriceAdjustmentService {

  Pricing addCommission(Pricing originalPricing);

}
