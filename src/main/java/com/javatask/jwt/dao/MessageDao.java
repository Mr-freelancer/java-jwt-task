package com.javatask.jwt.dao;

import com.javatask.jwt.model.DAOMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends CrudRepository<DAOMessage,Integer> {

}
