package com.javatask.jwt.controller;

import com.javatask.jwt.config.JwtTokenUtil;
import com.javatask.jwt.model.*;
import com.javatask.jwt.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MessageController {
    @Lazy
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Lazy
    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/message")
    public ResponseEntity<?> getAllMessagesByUser(HttpServletRequest request){
        String token = request.getHeader("authorization").replace("Bearer ", "");
        String userName = jwtTokenUtil.getUserNameFromToken(token);
        UserDetails user = jwtUserDetailsService.loadUserByUsername(userName);
        return ResponseEntity.ok(messageService.getAllMessagesByUser(userName));
     }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public ResponseEntity<?> saveMessage(@RequestBody MessageDTO message, HttpServletRequest request) throws Exception {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        String userName = jwtTokenUtil.getUserNameFromToken(token);

        String regex = "^(history\\s)(\\d{1,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message.getMessage());

        if ( matcher.matches() ) {
            // Get limit messages
            int historyLimit = Integer.parseInt(matcher.group(2));
            return ResponseEntity.ok(messageService.getMessagesByUserByLimit(message.getName(),historyLimit));
        }else if(userName.equals(message.getName())){
            // Save message
            return ResponseEntity.ok(messageService.save(message));
        }
        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
    }

}
