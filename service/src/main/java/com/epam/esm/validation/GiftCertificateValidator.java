package com.epam.esm.validation;

import com.epam.esm.dto.GiftCertificateDto;

import java.math.BigDecimal;
import java.util.Objects;

public class GiftCertificateValidator {
    private static final String NAME_PATTERN = "^[0-9a-zA-Z-_]{1,45}$";
    private static final String DESCRIPTION_PATTERN = "[^*<>\\\\/{|}]{0,100}";
    private static final String POSITIVE_INT_REGEX = "^[1-9]\\d{0,9}$";
    private static final double MIN_PRICE = 1.0D;
    private static final double MAX_PRICE = 100000.0D;
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 100;

    private GiftCertificateValidator() {
    }

    public static boolean isGiftCertificateDataCorrect(GiftCertificateDto certificate) {
        return isNameCorrect(certificate.getName()) &&
               isDescriptionCorrect(certificate.getDescription()) &&
               isPriceCorrect(certificate.getPrice()) &&
               isDurationCorrect(certificate.getDuration());
    }

    public static boolean isGiftCertificateOptionalDataCorrect(GiftCertificateDto certificate){
        return isOptionalNameCorrect(certificate.getName()) &&
               isOptionalDescriptionCorrect(certificate.getDescription()) &&
               isOptionalPriceCorrect(certificate.getPrice()) &&
               isOptionalDurationCorrect(certificate.getDuration());
    }

    public static boolean isOptionalNameCorrect(String name) {
        return name==null || name.matches(NAME_PATTERN);
    }

    public static boolean isOptionalDescriptionCorrect(String description) {
        return description==null || description.matches(DESCRIPTION_PATTERN);
    }

    public static boolean isOptionalPriceCorrect(BigDecimal price) {
        return price == null || price.doubleValue() > 0;
    }

    public static boolean isOptionalDurationCorrect(Integer duration) {
        return duration == null || String.valueOf(duration).matches(POSITIVE_INT_REGEX);
    }

    public static boolean isNameCorrect(String name) {
        return Objects.nonNull(name) && name.matches(NAME_PATTERN);
    }

    public static boolean isDescriptionCorrect(String description) {
        return Objects.nonNull(description) && description.matches(DESCRIPTION_PATTERN);
    }

    public static boolean isPriceCorrect(BigDecimal price) {
        return price.compareTo(BigDecimal.valueOf(MIN_PRICE)) >= 0 && price.compareTo(BigDecimal.valueOf(MAX_PRICE)) <= 0;
    }

    public static boolean isDurationCorrect(int duration) {
        boolean result = true;
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            result = false;
        }
        return result;
    }
}