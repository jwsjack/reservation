package com.tp1.e_cebanu.tp1.models;

import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.UserXmlImpl;

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

    /*------------------------- SERVICES -------------------------*/

    /*-- USERS --*/

    public static User authenticate(String login, String password) {
        //retrieve l'utilisateur dans BD par son login et mot de passe
        // Victor: implement here search function for user login password in XML - UserXmlImpl

        if (!login.isEmpty() && !password.isEmpty()) {
            return AppService.getUserObject().findByLoginPassword(login, password);
        } else {
            return new User(); // utilisateur vide
        }
    }

    /**
     * Fournit une nouvelle instance de UserXmlImpl
     * @return
     */
    public static UserXmlImpl getUsersService() {
        return new UserXmlImpl();
    }

    /**
     * Fournit une nouvelle instance de User
     * @return
     */
    public static User getUserObject() {
        return new User();
    }

    /*-- REASONS --*/



    /**
     * Fournit une nouvelle instance de Reason
     * @return
     */
    public static Reason getReasonObject() {
        return new Reason();
    }


    /*-- LOCALS --*/



    /**
     * Fournit une nouvelle instance de Local
     * @return
     */
    public static Local getLocalObject() {
        return new Local();
    }

    /*-- RESERVATIONS --*/




    /**
     * Fournit une nouvelle instance de Reservation
     * @return
     */
    public static Reservation getReservationObject() {
        return new Reservation();
    }



    /*-- ROLES --*/


    /**
     * Fournit une nouvelle instance de Role
     * @return
     */
    public static Role getRoleObject() {
        return new Role();
    }

}
