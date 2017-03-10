package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tp1.e_cebanu.tp1.R;

/**
 * Java# version 1.8.0
 *
 * @class Local
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des données d'un local/salle
 */

public class Local {

    // Variables
    private int id, nombre, type, capacite;

    public static final String FILENAME = "locations.xml";
    // Constructeur
    public Local(int nombre, int type, int capacite) {
        this.nombre = nombre;
        this.type = type;
        this.capacite = capacite;
    }

    public Local() {
        //constructeur vide
    }

    // Setters/getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    // fonctions personnalisées

    public String getTypeNom() {
        if (this.type == 1) {
            return  MyApplication.getAppResources().getString(R.string.sale_reunion);
        }
        return MyApplication.getAppResources().getString(R.string.classroom);
    }

    public String toString() {
        String str = "Local: " + nombre + "\t, type: " + getTypeNom() + "\t, capacité: " + capacite + "\t";
        return str;
    }

    public boolean equals(Local autre) {
        return this.nombre == autre.nombre;
    }

    public Local xmlToLocalMapper(Node node) {
        Local local = new Local();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            local.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            local.setNombre(Integer.parseInt(eElement.getElementsByTagName("nombre").item(0).getTextContent()));
            local.setType(Integer.parseInt(eElement.getElementsByTagName("type").item(0).getTextContent()));
            local.setCapacite(Integer.parseInt(eElement.getElementsByTagName("capacite").item(0).getTextContent()));
        }
        return local;
    }
}
