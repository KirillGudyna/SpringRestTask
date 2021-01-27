package com.epam.esm.validation;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.Objects;

public class GiftCertificateValidator {
    private static final String NAME_PATTERN = "^[0-9a-zA-Z-_]{1,45}$";
    private static final String DESCRIPTION_PATTERN = "[^*<>\\\\/{|}]{0,100}";
    private static final double MIN_PRICE = 1.0D;
    private static final double MAX_PRICE = 100000.0D;
    private static final int MIN_DURATION = 1;
    private static final int MAX_DURATION = 100;

    private GiftCertificateValidator() {
    }

    public static String isGiftCertificateDataCorrect(GiftCertificate certificate) {
        StringBuilder builder = new StringBuilder();
        if (!isNameCorrect(certificate.getName())) {
            builder.append("incorrect certificate name");
            builder.append("\n");
        }

        if (isDescriptionCorrect(certificate.getDescription())) {
            builder.append("incorrect description");
            builder.append("\n");
        }

        if (!isPriceCorrect((double) certificate.getPrice())) {
            builder.append("incorrect price");
            builder.append("\n");
        }

        if (!isDurationCorrect(certificate.getDuration())) {
            builder.append("incorrect duration");
            builder.append("\n");
        }

        return builder.toString();
    }

    public static boolean isNameCorrect(String name) {
        return Objects.nonNull(name) && name.matches(NAME_PATTERN);
    }

    public static boolean isDescriptionCorrect(String description) {
        return !Objects.nonNull(description) || !description.matches(DESCRIPTION_PATTERN);
    }

    public static boolean isPriceCorrect(double price) {
        return price >= MIN_PRICE && price <= MAX_PRICE;
    }

    public static boolean isDurationCorrect(int duration) {
        boolean result = true;
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            result = false;
        }

        return result;
    }
}