package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;

public class TagValidator {
    private static final String NAME_PATTERN = "^[0-9a-zA-Z-_]{1,45}$";

    private TagValidator() {
    }

    public static boolean isNameCorrect(String name) {
        return name != null && name.matches(NAME_PATTERN);
    }

    public static boolean isTagCorrect(TagDto tagDto) {
        return tagDto != null && isNameCorrect(tagDto.getName());
    }
}
