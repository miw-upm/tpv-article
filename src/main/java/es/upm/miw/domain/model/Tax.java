package es.upm.miw.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Tax {
    FREE("0"), SUPER_REDUCED("4"), REDUCED("10"), GENERAL("21");

    private final BigDecimal rate;

    Tax(String tax) {
        this.rate = new BigDecimal(tax);
    }

    @Override
    public String toString() {
        return this.name() + "(" + this.rate + ")";
    }
}
