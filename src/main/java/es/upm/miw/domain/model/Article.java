package es.upm.miw.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import es.upm.miw.domain.model.validations.PositiveBigDecimal;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private UUID id;
    @NotBlank
    private String barcode;
    @NotBlank
    private String description;
    @PositiveBigDecimal
    private BigDecimal retailPrice;
    private Integer stock;
    private Tax tax;
    private Boolean discontinued;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // pattern="dd/MM/yyyy hh:mm" o iso = DateTimeFormat.ISO.TIME
    private LocalDateTime registrationDate;
    private Provider provider;

    public static Article ofBarcodeDescriptionStock(Article article) {
        return Article.builder()
                .barcode(article.getBarcode())
                .description(article.getDescription())
                .stock(article.getStock())
                .build();
    }

    public static Article ofBarcode(Article article) {
        return Article.builder()
                .barcode(article.getBarcode())
                .build();
    }

    public void doDefault() {
        if (Objects.isNull(stock)) {
            this.stock = 10;
        }
        if (Objects.isNull(tax)) {
            this.tax = Tax.GENERAL;
        }
        if (Objects.isNull(discontinued)) {
            this.discontinued = false;
        }
        if (Objects.isNull(retailPrice)) {
            this.retailPrice = BigDecimal.ZERO;
        }
    }

    public BigDecimal taxBase() {
        return this.retailPrice
                .divide(BigDecimal.ONE.add(tax.getRate()
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax() {
        return this.retailPrice.subtract(taxBase());
    }
}