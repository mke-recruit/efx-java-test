package fx.price.publisher.api.rest;

import fx.price.publisher.domain.model.Pricing;
import org.springframework.stereotype.Service;
import fx.price.publisher.api.dto.PricingDto;

@Service
public class PricingMapper {

  PricingDto toPricingDto(Pricing pricing) {
    return new PricingDto(pricing.pair().toString(), pricing.bidPrice(), pricing.askPrice());
  }
}
