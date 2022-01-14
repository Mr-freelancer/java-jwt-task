package com.javatask.jwt.service;

import com.javatask.jwt.dao.MessageDao;
import com.javatask.jwt.dao.UserDao;
import com.javatask.jwt.model.DAOMessage;
import com.javatask.jwt.model.DAOUser;
import com.javatask.jwt.model.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.*;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageRepository;

    @Autowired
    private UserDao userRepository;

    public DAOMessage save(MessageDTO message) {
        DAOMessage newMessage = new DAOMessage();
        newMessage.setMessageText(message.getMessage());
        DAOUser user = userRepository.findByUsername(message.getName());
        newMessage.setUser(user);
        return messageRepository.save(newMessage);
    }

    public Set<DAOMessage> getAllMessagesByUser(String user){
        return userRepository.findByUsername(user).getMessages();
    }

    public List<DAOMessage> getMessagesByUserByLimit(String user, int limit){
        List<DAOMessage> messages = new ArrayList<>(userRepository.findByUsername(user).getMessages());
        sort(messages, Comparator.comparing(DAOMessage::getId));
        reverse(messages);
        return messages.stream().limit(limit).collect(Collectors.toList());
    }


}
