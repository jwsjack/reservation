package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.LocalDao;
import com.tp1.e_cebanu.tp1.dao.interfaces.RoleDao;
import com.tp1.e_cebanu.tp1.models.Role;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by User on 08.03.2017.
 */

public class RoleXmlImpl implements RoleDao {


    public XMLParser xmlParser = null;
    public RoleXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Role.FILENAME);
    }


    @Override
    public void create(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Role role) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == role.getId()) {
                        node.getParentNode().removeChild(node);
                    }
                }
            }
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
    public Role find(Role role) {
        return null;
    }


    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Role role = new Role();
                    roles.add(role.xmlToRoleMapper(nNode));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role findById(int roleId) {
        Role local = new Role();
        List<Role> locals = findAll();
        for (Role item: locals) {
            if (item.getId() == roleId) {
                local = item;
            }
        }
        return local;
    }
}
