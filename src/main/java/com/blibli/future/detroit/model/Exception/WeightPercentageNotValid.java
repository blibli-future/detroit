package com.blibli.future.detroit.model.Exception;

public class WeightPercentageNotValid extends Exception {
    public WeightPercentageNotValid() {
    }

    public WeightPercentageNotValid(String message) {
        super(message);
    }

    public WeightPercentageNotValid(String message, Throwable cause) {
        super(message, cause);
    }

    public WeightPercentageNotValid(Throwable cause) {
        super(cause);
    }

    public WeightPercentageNotValid(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
