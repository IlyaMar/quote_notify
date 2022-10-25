package org.imartynov.quote.stock.initializers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;


@ConfigurationProperties(prefix = "stockexchange")      // read properties with given prefix
@Validated              // validate with JSR-380. at startup.
@ConstructorBinding     // create with all-args constructor. immutable.
public class StockExchangeProperties {

    private final String stocks;          // stock list, comma-separated

    @Positive
    private final int quotePerSecond;     // output quote rate

    public StockExchangeProperties(String stocks, @Positive int quotePerSecond) {
        this.stocks = stocks;
        this.quotePerSecond = quotePerSecond;
    }

    public String getStocks() {
        return stocks;
    }

    public int getQuotePerSecond() {
        return quotePerSecond;
    }

    @Override
    public String toString() {
        return "StockExchangeProperties{" +
                "stocks='" + stocks + '\'' +
                ", quotePerSecond=" + quotePerSecond +
                '}';
    }
}
