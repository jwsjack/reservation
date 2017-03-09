package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;
import com.tp1.e_cebanu.tp1.dao.interfaces.UserDao;
import com.tp1.e_cebanu.tp1.models.AppService;
import com.tp1.e_cebanu.tp1.models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
 * @description Mise en œuvre de lecture / écriture XML
 */
public class UserXmlImpl implements UserDao {

    public XMLParser xmlParser = null;
    public UserXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(User.FILENAME);
//        xmlParser.setContext();

    }
    @Override
    public void create(User user) {
        Document doc = null;
        try {
            doc = xmlParser.getNodeListFromResources();

            Element item = doc.createElement("item");
            Element id = doc.createElement("id");
            Element name = doc.createElement("name");
            Element login = doc.createElement("login");
            Element password = doc.createElement("password");
            Element role = doc.createElement("role");
            item.setAttribute("ItemName", user.getNom());
            id.setTextContent(String.valueOf(user.getId()));
            name.setTextContent(user.getNom());
            login.setTextContent(user.getLogin());
            password.setTextContent(user.getPassword());
            role.setTextContent(String.valueOf(user.getRole()));
            item.appendChild(id);
            item.appendChild(name);
            item.appendChild(login);
            item.appendChild(password);
            item.appendChild(role);

            xmlParser.saveDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User find(User user) {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        User user = new User();
        List<User> users = findAll();
        for (User item: users) {
            if (item.getLogin().equals(login)) {
                user = item;
            }
        }
        return user;
    }

    @Override
    public User findByLoginPassword(String login, String password) {
        User user = new User();
        List<User> users = findAll();
        for (User item: users) {
            if (item.getLogin().equals(login) && item.getPassword().equals(password) ) {
                user = item;
            }
        }
        return user;
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    User user = new User();
                    users.add(user.xmlToUserMapper(nNode));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return users;
    }
}