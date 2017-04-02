package com.tp1.e_cebanu.tp1.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Java# version 1.8.0
 *
 * @author EUGENIU CEBANU / matricule: 20025851
 * @author jwsjack3@gmail.com
 * @version 1
 * @class User
 * @package TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @date 2017-02-20
 * @description Modele - gestion des utilisateurs
 */

public class User {
    private String nom, login, password;
    private int id, role, active;

    public static final String FILENAME = "users.xml";

    // Constructeur
    public User(int id, String nom, String login, String password, int role, int active) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.password = password;
        this.role = role;
        this.active = active;
    }


    // Constructeur pour obtenir un objet qui se compare (equals)
    public User(String nom, String login, String password, int role, int active) {
        this(0, nom, login, password, role, active);
    }

    public User(String login) {
        this.login = login;
    }

    public User() {
    }

    //Getters/setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public boolean isSuperAdmin() {
        return this.getRole() == 1;
    }

    @Override
    public String toString() {
        String str = nom + "\t, login: " + login + "\t";
        if (isSuperAdmin()) {
            str += "\t, est SuperAdmin";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    /**
     * Conversion en JSON
     *
     * @return
     */
    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getNom());
            jsonObject.put("login", getLogin());
            jsonObject.put("password", getPassword());
            jsonObject.put("role", getRole());
            jsonObject.put("active", getActive());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static User fromJSON(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            int id = obj.getInt("id");
            String name = obj.getString("name");
            String login = obj.getString("login");
            String password = obj.getString("password");
            int role = obj.getInt("role");
            int active = obj.getInt("active");
            return new User(id, name, login, password, role, active);
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
            return new User();
        }
    }


    /**
     * From XML node to user object
     *
     * @param node
     * @return
     */
    public User xmlToUserMapper(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            user.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            user.setNom(eElement.getElementsByTagName("name").item(0).getTextContent());
            user.setLogin(eElement.getElementsByTagName("login").item(0).getTextContent());
            user.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
            user.setRole(Integer.parseInt(eElement.getElementsByTagName("role").item(0).getTextContent()));
            user.setActive(Integer.parseInt(eElement.getElementsByTagName("active").item(0).getTextContent()));
        }
        return user;
    }

    /**
     * Map from user to xml Node using Document
     * @param doc
     * @return
     * @throws ParserConfigurationException
     */
    public Node userToXmlMapper(Document doc) throws ParserConfigurationException {
        Element item = doc.createElement("item");
        Element id = doc.createElement("id");
        Element name = doc.createElement("name");
        Element login = doc.createElement("login");
        Element password = doc.createElement("password");
        Element role = doc.createElement("role");
        Element active = doc.createElement("active");
        item.setAttribute("ItemName", getNom());
        if (getId() == 0) {
            //generate ID
            id.setTextContent(String.valueOf(hashCode()));
        } else {
            id.setTextContent(String.valueOf(getId()));
        }
        name.setTextContent(getNom());
        login.setTextContent(getLogin());
        password.setTextContent(getPassword());
        role.setTextContent(String.valueOf(getRole()));
        active.setTextContent(String.valueOf(getActive()));
        item.appendChild(id);
        item.appendChild(name);
        item.appendChild(login);
        item.appendChild(password);
        item.appendChild(role);
        item.appendChild(active);
        return item;
    }


}
