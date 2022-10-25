package org.imartynov.quote.index.initializers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;


@ConfigurationProperties(prefix = "index")      // read properties with given prefix
@Validated              // validate with JSR-380. at startup.
@ConstructorBinding     // create with all-args constructor. immutable.
public class IndexProperties {

    @Positive
    private final int inputQueueNumber;

    public IndexProperties(@Positive int inputQueueNumber) {
        this.inputQueueNumber = inputQueueNumber;
    }

    public int getInputQueueNumber() {
        return inputQueueNumber;
    }
}
