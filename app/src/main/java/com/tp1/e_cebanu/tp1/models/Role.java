package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Java# version 1.8.0
 *
 * @class Role
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des rôles
 */

public class Role {
    private String name;
    private int id;
    private int superadmin; // checkbox pour superadmin role

    public static final String FILENAME = "roles.xml";

    // Constructeur
    public Role(int id, String title, int superadmin) {
        this.name = name;
        this.id = id;
        this.superadmin = superadmin;
    }

    public Role() {
        //constructeur vide
    }

    //Getters/setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuperadmin() {
        return superadmin;
    }

    public void setSuperadmin(int superadmin) {
        this.superadmin = superadmin;
    }

    @Override
    public String toString() {
        return "Role{" +
                "title='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }

    /**
     * From XML node to role object
     *
     * @param node
     * @return
     */
    public Role xmlToRoleMapper(Node node) {
        Role role = new Role();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            role.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            role.setName(String.valueOf(eElement.getElementsByTagName("name").item(0).getTextContent()));
            role.setSuperadmin(Integer.parseInt(eElement.getElementsByTagName("superadmin").item(0).getTextContent()));
        }
        return role;
    }
    /**
     * Map from role to xml Node using Document
     * @param doc
     * @return
     * @throws ParserConfigurationException
     */
    public Node roleToXmlMapper(Document doc) throws ParserConfigurationException {
        Element item = doc.createElement("item");
        Element id = doc.createElement("id");
        Element name = doc.createElement("name");
        Element superadmin = doc.createElement("superadmin");
        if (getId() == 0) {
            //generate ID
            id.setTextContent(String.valueOf(hashCode()));
        } else {
            id.setTextContent(String.valueOf(getId()));
        }
        name.setTextContent(String.valueOf(getName()));
        superadmin.setTextContent(String.valueOf(getSuperadmin()));
        item.appendChild(id);
        item.appendChild(name);
        item.appendChild(superadmin);
        return item;
    }
}
