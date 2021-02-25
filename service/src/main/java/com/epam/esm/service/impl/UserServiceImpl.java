package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.UserOrderDao;
import com.epam.esm.service.UserService;
import com.epam.esm.util.DtoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserOrderDao userOrderDao;
    private TagDao tagDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserOrderDao userOrderDao, TagDao tagDao) {
        this.userDao = userDao;
        this.userOrderDao = userOrderDao;
        this.tagDao = tagDao;
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userDao.findById(id).map(DtoWrapper::toUserDto);
    }

    @Override
    public List<UserDto> findAll(Integer limit, Integer offset) {
        return userDao.findAll(limit, offset)
                .stream()
                .map(DtoWrapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TagDto> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Long userId = userDao.findUserIdWithHighestOrderSum();
        Long tagId = userOrderDao.findMostPopularTagIdOfUser(userId);
        return tagDao.findById(tagId).map(DtoWrapper::toTagDto);
    }
}
