package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.RoleDao;
import com.tp1.e_cebanu.tp1.models.Role;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

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
    public void create(Role local) {

    }

    @Override
    public void update(Role local) {

    }

    @Override
    public void delete(Role local) {

    }

    @Override
    public Role find(Role local) {
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
