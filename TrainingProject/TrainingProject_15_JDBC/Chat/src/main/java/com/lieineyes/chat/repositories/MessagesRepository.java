package com.lieineyes.chat.repositories;

import com.lieineyes.chat.models.Message;

import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(long id);
    void save(Message msg);
    void update(Message msg);
}
