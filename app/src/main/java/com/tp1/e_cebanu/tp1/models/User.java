package com.tp1.e_cebanu.tp1.models;

/**
 * Java# version 1.8.0
 *
 * @class User
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des utilisateurs
 */

public class User {
    private String nom, login, password;
    private int id, role;

    // Constructeur
    public User(int id, String nom, String login, String password, int role) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.password = password;
        this.role = role;
    }
    // Constructeur pour obtenir un objet qui se compare (equals)
    public User(String nom, String login, String password, int role) {
        this(0,nom, login, password, role);
    }

    public User(String login){
        this.login = login;
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

    public boolean isSuperAdmin() {
        return  this.getRole() == 1;
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
        return login.hashCode();
    }
}
