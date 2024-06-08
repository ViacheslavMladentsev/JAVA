package com.lieineyes.chat.repositories;

import com.lieineyes.chat.models.User;

import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size);
}
