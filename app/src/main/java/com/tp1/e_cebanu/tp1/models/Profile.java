package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;

import static com.tp1.e_cebanu.tp1.R.string.active;
import static com.tp1.e_cebanu.tp1.R.string.role;

/**
 * Java# version 1.8.0
 *
 * @author EUGENIU CEBANU / matricule: 20025851
 * @author jwsjack3@gmail.com
 * @version 1
 * @class User
 * @package TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @date 2017-02-20
 * @description Modele - gestion des données personnelles des utilisateurs
 */

public class Profile {
    private String nom, login, password, email, telephone, address;
    private int id, keepConnected;
    private User user;

    public static final String FILENAME = "profiles.xml";

    // Constructeur
    public Profile() {}

    //Setters/getters


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKeepConnected() {
        return keepConnected;
    }

    public void setKeepConnected(int keepConnected) {
        this.keepConnected = keepConnected;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "nom='" + nom + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profileO = (Profile) o;

        return user.getLogin().equals(profileO.user.getLogin()) || login.equals(profileO.login);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    /**
     * From XML node to user object
     *
     * @param node
     * @return
     */

    public Profile xmlToProfileMapper(Node node) {
        Profile profile = new Profile();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            profile.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            profile.setNom(eElement.getElementsByTagName("name").item(0).getTextContent());
            profile.setLogin(eElement.getElementsByTagName("login").item(0).getTextContent());
            profile.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
            profile.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
            profile.setTelephone(eElement.getElementsByTagName("telephone").item(0).getTextContent());
            profile.setAddress(eElement.getElementsByTagName("address").item(0).getTextContent());
            profile.setKeepConnected(Integer.parseInt(eElement.getElementsByTagName("keepconnected").item(0).getTextContent()));

            int userId = Integer.parseInt(eElement.getElementsByTagName("user").item(0).getTextContent());
            User user = AppService.getUsersService().findById(userId);
            profile.setUser(user);
        }
        return profile;
    }

    /**
     * Map from user to xml Node using Document
     * @param doc
     * @return
     * @throws ParserConfigurationException
     */
    public Node profileToXmlMapper(Document doc) throws ParserConfigurationException {
        Element item = doc.createElement("item");
        Element id = doc.createElement("id");
        Element name = doc.createElement("name");
        Element login = doc.createElement("login");
        Element password = doc.createElement("password");
        Element email = doc.createElement("email");
        Element telephone = doc.createElement("telephone");
        Element address = doc.createElement("address");
        Element keepconnected = doc.createElement("keepconnected");
        Element user = doc.createElement("user");
        if (getId() == 0) {
            //generate ID
            id.setTextContent(String.valueOf(hashCode()));
        } else {
            id.setTextContent(String.valueOf(getId()));
        }

        name.setTextContent(getNom());
        login.setTextContent(getLogin());
        password.setTextContent(getPassword());
        email.setTextContent(String.valueOf(getEmail()));
        telephone.setTextContent(String.valueOf(getTelephone()));
        address.setTextContent(String.valueOf(getAddress()));
        keepconnected.setTextContent(String.valueOf(getKeepConnected()));
        user.setTextContent(String.valueOf(getUser().getId()));

        item.appendChild(id);
        item.appendChild(name);
        item.appendChild(login);
        item.appendChild(password);
        item.appendChild(email);
        item.appendChild(telephone);
        item.appendChild(address);
        item.appendChild(keepconnected);
        item.appendChild(user);
        return item;
    }
}
