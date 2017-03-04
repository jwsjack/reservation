package com.tp1.e_cebanu.tp1.models;

/**
 * Java# version 1.8.0
 *
 * @class AppService
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Modele - gestion des fonctions globales/service container
 */

public class AppService {

    //Constructeur
    public AppService() {
    }


    /*-- Users --*/
    public static User authenticate(String login, String password) {
        //retrieve l'utilisateur dans BD par son login et mot de passe
        // Victor: implement here search function for user login password in XML - UserXmlImpl
        return new User(1,"Eugeniu Cebanu","e_cebanu","qwe123",1);
    }

}
