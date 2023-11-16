package fx.price.publisher.api.rest;

import fx.price.publisher.api.dto.PricingDto;
import fx.price.publisher.domain.interfaces.CurrentPriceManager;
import fx.price.publisher.domain.model.CurrencyPair;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrentPriceController {

  private final CurrentPriceManager priceManager;
  private final PricingMapper pricingMapper;

  @GetMapping("fxprices")
  ResponseEntity<List<PricingDto>> getCurrentPrices() {
    var responsePayload =  priceManager.getAllCurrentPrices().stream()
        .map(pricingMapper::toPricingDto)
        .toList();
    return responsePayload.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(responsePayload);
  }

  @GetMapping("fxprices/{currencyPair}")
  ResponseEntity<PricingDto> getCurrent(@PathVariable String currencyPair) {
    return priceManager.getPricing(CurrencyPair.ofShortName(currencyPair))
        .map(pricingMapper::toPricingDto)
        .map(price -> ResponseEntity.ok().body(price))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
