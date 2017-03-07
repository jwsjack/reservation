package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.User;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by e_cebanu on 3/6/2017.
 */

public interface UserDao {

    public void create(User user) throws IOException, ParserConfigurationException, TransformerException;

    public void update(User user);

    public void delete(User user);

    public User find(User user);

    public User findByLogin(String login);

    public List<User> findAll() throws IOException, ParserConfigurationException;
}
