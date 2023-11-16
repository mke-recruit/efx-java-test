package fx.price.publisher.services;

import fx.price.publisher.domain.interfaces.FxPricingMessageParser;
import fx.price.publisher.domain.model.CurrencyPair;
import fx.price.publisher.domain.model.Pricing;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FxPricingMessageParserImpl implements FxPricingMessageParser {

  private static final String DEFAULT_EVENT_DATE_FORMAT = "";

  private final SimpleDateFormat pricingEventDateFormat;


  public FxPricingMessageParserImpl(@Value("${message.datetime.format:dd-MM-yyyy HH:mm:ss:SSS}") String dateFormat) {
    pricingEventDateFormat = new SimpleDateFormat(dateFormat);
  }

  @Override public List<Pricing> parse(String message) {
    return Arrays.stream(message.split(System.lineSeparator()))
        .map(this::parseSingleLine)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private Optional<Pricing> parseSingleLine(String line) {
    try {
      var lineItems = line.split(",");
      if (lineItems.length == 5) {
        var pricingTimestamp = pricingEventDateFormat.parse(lineItems[4].trim());
        return Optional.of(new Pricing(CurrencyPair.ofName(lineItems[1].trim()), new BigDecimal(lineItems[2].trim()),
            new BigDecimal(lineItems[3].trim()), pricingTimestamp.toInstant()));
      }
    } catch (Exception ex) {
      log.warn("Exception parsing message line [{}]", line);
      log.warn("Exception details :", ex);
    }
    return Optional.empty();
  }
}
