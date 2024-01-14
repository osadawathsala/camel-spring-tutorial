package com.tutorial.services;

import com.tutorial.model.Message;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Osada
 * @Date 1/14/2024
 */
@Service("messageService")
public class MessageServiceIml implements MessageService{
    private final Map<Integer, Message> messages = new TreeMap<>();

    public MessageServiceIml() {
        messages.put(1, new Message(1, "Osada","osadasliit@gmail.com"));
        messages.put(2, new Message(2, "Wathsala","osadasliit@gmail.com"));
        messages.put(3, new Message(3, "Kalubadanage","osadasliit@gmail.com"));
    }

    @Override
    public Collection<Message> findMessages() {
        return messages.values();
    }

    @Override
    public Message findMessageById(Integer id) {
        return messages.get(id);
    }

    @Override
    public void updateMessage(Message message) {
        messages.put(message.getId(),message);
    }

}
