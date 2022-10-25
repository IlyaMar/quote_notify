package org.imartynov.quote.input.initializers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;


@ConfigurationProperties(prefix = "input")      // read properties with given prefix
@Validated              // validate with JSR-380. at startup.
@ConstructorBinding     // create with all-args constructor. immutable.
public class InputProperties {

    @Positive
    private final int fanoutCount;

    public InputProperties(@Positive int fanoutcount) {
        this.fanoutCount = fanoutcount;
    }

    public int getFanoutCount() {
        return fanoutCount;
    }
}
