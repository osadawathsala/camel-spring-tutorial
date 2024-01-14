package com.tutorial.services;

import com.tutorial.model.Message;

import java.util.Collection;

/**
 * @author Osada
 * @Date
 */
public interface MessageService {
    Collection<Message> findMessages();
    Message findMessageById(Integer id);
    void updateMessage(Message message);
}
