package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.User;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Java# version 1.8.0
 *
 * @class UserXmlImpl
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Interface pour USERS
 */

public interface UserDao {

    public void create(User user) throws IOException, ParserConfigurationException, TransformerException;

    public void update(User user);

    public void delete(User user);

    public User find(User user);

    public User findByLogin(String login);

    public User findByLoginPassword(String login, String password);


    public List<User> findAll() throws IOException, ParserConfigurationException;

    public User findById(int userId);
}
