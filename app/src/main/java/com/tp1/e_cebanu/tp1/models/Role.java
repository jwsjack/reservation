package com.tp1.e_cebanu.tp1.models;

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

    // Constructeur
    public Role(int id, String title) {
        this.name = name;
        this.id = id;
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

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = name;
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
}
