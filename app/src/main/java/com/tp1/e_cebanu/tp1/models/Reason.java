package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Java# version 1.8.0
 *
 * @class Reason
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des motifs
 */

public class Reason {
    protected String name;
    protected int id;

    public static final String FILENAME = "reasons.xml";

    // Constructeur
    public Reason(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reason() {
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

    @Override
    public String toString() {
        return "Reason{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reason reason = (Reason) o;

        if (id != reason.id) return false;
        return name != null ? name.equals(reason.name) : reason.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    public Reason xmlToLocalMapper(Node node) {
        Reason reason = new Reason();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            reason.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            reason.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
        }
        return reason;
    }
}
