package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tp1.e_cebanu.tp1.R;

import javax.xml.parsers.ParserConfigurationException;

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
    private String description;

    public static final String FILENAME = "locations.xml";
    // Constructeur
    public Local(int nombre, int type, int capacite, String description) {
        this.nombre = nombre;
        this.type = type;
        this.capacite = capacite;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int result = String.valueOf(nombre) != null ? String.valueOf(nombre).hashCode() : 0;
        result = 31 * result + id;
        return result;
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
            local.setDescription(String.valueOf(eElement.getElementsByTagName("description").item(0).getTextContent()));
        }
        return local;
    }

    public Node localToXmlMapper(Document doc) throws ParserConfigurationException {
        Element item = doc.createElement("item");
        Element id = doc.createElement("id");
        Element nombre = doc.createElement("nombre");
        Element type = doc.createElement("type");
        Element capacite = doc.createElement("capacite");
        Element description = doc.createElement("description");
        if (getId() == 0) {
            //generate ID
            id.setTextContent(String.valueOf(hashCode()));
        } else {
            id.setTextContent(String.valueOf(getId()));
        }
        nombre.setTextContent(String.valueOf(getNombre()));
        type.setTextContent(String.valueOf(getType()));
        capacite.setTextContent(String.valueOf(getCapacite()));
        description.setTextContent(String.valueOf(getDescription()));
        item.appendChild(id);
        item.appendChild(nombre);
        item.appendChild(type);
        item.appendChild(capacite);
        item.appendChild(description);
        return item;
    }
}
