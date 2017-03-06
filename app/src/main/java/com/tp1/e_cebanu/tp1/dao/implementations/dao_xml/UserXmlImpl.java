package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;
import com.tp1.e_cebanu.tp1.dao.interfaces.UserDao;
import com.tp1.e_cebanu.tp1.models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
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
    public void create(User user) throws IOException, ParserConfigurationException, TransformerException {
        Document nodeList = xmlParser.getNodeListFromResources();
        Node userNode = user.userToXmlMapper(user);
        nodeList.adoptNode(userNode);
        xmlParser.saveDocument(nodeList);
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