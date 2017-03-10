package com.tp1.e_cebanu.tp1.models;

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
    private int nombre, type, capacite;

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
        return "Salle de cours";
    }

    public String toString() {
        String str = "Local: " + nombre + "\t, type: " + getTypeNom() + "\t, capacité: " + capacite + "\t";
        return str;
    }

    public boolean equals(Local autre) {
        return this.nombre == autre.nombre;
    }
}
