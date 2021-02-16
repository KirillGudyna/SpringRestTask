package com.epam.esm.validation;

import java.util.Arrays;

public class GiftEntityValidator {

    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String PRICE = "price";
    private static final String NAME = "name";
    private static final String CREATE_DATE = "create-date";
    private static final String LAST_UPDATE_DATE = "last-update-date";
    private static final String DURATION = "duration";
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private GiftEntityValidator() {
    }

    public static boolean correctOptionalParameters(String name,
                                                    String description,
                                                    String tagName,
                                                    String sortType,
                                                    String direction) {
        if (name != null && !name.matches(NAME_REGEX)) {
            return false;
        }
        if (description != null && !description.matches(CERTIFICATE_DESCRIPTION_REGEX)) {
            return false;
        }
        if (tagName != null && !tagName.matches(NAME_REGEX)) {
            return false;
        }
        return correctSortType(sortType) && correctDirection(direction);
    }

    private static boolean correctSortType(String sortType) {
        return sortType == null || sortType.equals(PRICE) || sortType.equals(NAME) || sortType.equals(CREATE_DATE)
                || sortType.equals(LAST_UPDATE_DATE) || sortType.equals(DURATION);
    }

    private static boolean correctDirection(String direction) {
        return direction == null || direction.equals(ASC) || direction.equals(DESC);
    }

    public static boolean correctId(long... ids) {
        return Arrays.stream(ids).allMatch(id -> String.valueOf(id).matches(ID_REGEX));
    }
}