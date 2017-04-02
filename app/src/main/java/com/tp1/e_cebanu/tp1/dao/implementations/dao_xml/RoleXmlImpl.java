package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

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
 * Java# version 1.8.0
 *
 * @class UserXmlImpl
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Mise en œuvre de lecture / écriture XML pour ROLES
 */

public class RoleXmlImpl implements RoleDao {


    public XMLParser xmlParser = null;
    public RoleXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Role.FILENAME);
    }


    @Override
    public void create(Role role) {
        Document doc = null;
        try {
            doc = xmlParser.getNodeListFromResources();
            Node parent = doc.getFirstChild();
            Node child = role.roleToXmlMapper(doc);
            parent.appendChild((Node) child);
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
    public void update(Role role) {
        try {
            Document doc = xmlParser.getNodeListFromResources();
            NodeList nodeList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()) == role.getId()) {
                        Node name = element.getElementsByTagName("name").item(0).getFirstChild();
                        name.setNodeValue(role.getName());

                        Node superAdmin = element.getElementsByTagName("superadmin").item(0).getFirstChild();
                        superAdmin.setNodeValue(String.valueOf(role.getSuperadmin()));
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
