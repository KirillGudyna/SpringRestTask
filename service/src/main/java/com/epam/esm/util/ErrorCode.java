package com.epam.esm.util;

public enum ErrorCode {
    GIFT_CERTIFICATE_NOT_FOUND(40401, "certificateNotFound"),
    TAG_NOT_FOUND(40402, "tagNotFound"),
    UPDATE_PARAMETERS_WRONG_FORMAT(40425, "wrongUpdateField"),
    USER_NOT_FOUND(40436, "userNotFound"),
    BUY_PARAMETERS_WRONG_FORMAT(40437, "wrongBuy"),
    ORDER_NOT_FOUND(40448, "orderNotFound"),
    WRONG_OPTIONAL_PARAMETERS(40001, "wrongOptional"),
    TAG_WRONG_PARAMETERS(40002, "wrongTag"),
    CERTIFICATE_WRONG_PARAMETERS(40003, "wrongCertificate");

    private final int messageCode;
    private final String messageKey;

    ErrorCode(int messageCode, String messageKey) {
        this.messageCode = messageCode;
        this.messageKey = messageKey;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}