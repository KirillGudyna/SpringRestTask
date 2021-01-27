package com.epam.esm.validation;

import java.util.Objects;

public class TagValidator {
    private static final String NAME_PATTERN = "^[0-9a-zA-Z-_]{1,45}$";

    private TagValidator() {
    }

    public static boolean isNameCorrect(String name) {
        return !Objects.nonNull(name) || !name.matches(NAME_PATTERN);
    }
}
