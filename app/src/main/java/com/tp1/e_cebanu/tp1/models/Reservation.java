package com.tp1.e_cebanu.tp1.models;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Java# version 1.8.0
 *
 * @class Reservation
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des reservations
 */

public class Reservation {
    protected int id, local, user, raison;
    protected String autreRraison, cours;
    public static final String FILENAME = "reservations.xml";


    public Reservation() {
        //constructeur vide
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRaison() {
        return raison;
    }

    public void setRaison(int raison) {
        this.raison = raison;
    }

    public String getAutreRraison() {
        return autreRraison;
    }

    public void setAutreRraison(String autreRraison) {
        this.autreRraison = autreRraison;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public static String getFILENAME() {
        return FILENAME;
    }



    public Reservation xmlToReservationMapper(Node node) {
        Reservation reservation = new Reservation();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            reservation.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
            reservation.setLocal(Integer.parseInt(eElement.getElementsByTagName("local").item(0).getTextContent()));
            reservation.setUser(Integer.parseInt(eElement.getElementsByTagName("user").item(0).getTextContent()));
            reservation.setRaison(Integer.parseInt(eElement.getElementsByTagName("raison").item(0).getTextContent()));
            reservation.setAutreRraison(eElement.getElementsByTagName("autreRaison").item(0).getTextContent());
            reservation.setCours(eElement.getElementsByTagName("cours").item(0).getTextContent());
        }
        return reservation;
    }
}
