package fx.price.publisher.api.rest;

import fx.price.publisher.domain.interfaces.PricingMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DummyDataInputController {

  private final PricingMessageListener pricingMessageListener;

  @PostMapping("/test/publisher/prices")
  public void acceptNewPrices(@RequestBody String message) {
    log.debug("Received new message with prices:\n{}", message);
    pricingMessageListener.onMessage(message);
  }
}
