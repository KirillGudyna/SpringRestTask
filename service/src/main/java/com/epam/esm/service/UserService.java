package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> findById(long id);

    List<UserDto> findAll(Integer limit, Integer offset);

    Optional<TagDto> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
