package fx.price.publisher.domain.interfaces;

import fx.price.publisher.domain.model.Pricing;
import java.util.List;

public interface FxPricingMessageParser {
  List<Pricing> parse(String message);
}
