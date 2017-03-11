package com.tp1.e_cebanu.tp1.models;

import android.content.SharedPreferences;

import com.tp1.e_cebanu.tp1.authenticator.AuthenticatorActivity;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.LocalXmlImpl;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.ReasonXmlImpl;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.ReservationXmlImpl;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.RoleXmlImpl;
import com.tp1.e_cebanu.tp1.dao.implementations.dao_xml.UserXmlImpl;

import static android.content.Context.MODE_PRIVATE;

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

    /**
     * Exécute l'authentification
     * @param login
     * @param password
     * @return
     */
    public static User authenticate(String login, String password) {
        //retrieve l'utilisateur dans BD (xml fichiers dans local storage) par son login et mot de passe
        if (!login.isEmpty() && !password.isEmpty()) {
            return AppService.getUsersService().findByLoginPassword(login, password);
        } else {
            return new User(); // utilisateur vide
        }
    }

    /**
     * Fournir un utilisateur connecté
     * @return
     */
    public static User getLiu() {
        SharedPreferences prefsAut = MyApplication.getAppContext().getSharedPreferences(AuthenticatorActivity.MON_CLE_LOGIN, MODE_PRIVATE);
        String restoredNom = prefsAut.getString("login", null);
        String restoredPass = prefsAut.getString("pass", null);
        String restoredLiu = prefsAut.getString("liu", null);
        if (!restoredLiu.isEmpty()) {
            return User.fromJSON(restoredLiu);
        } else  {
            return AppService.getUserObject(); //instance vide User
        }
    }

    /**
     * Fournit une nouvelle instance de LocalXmlImpl
     * @return
     */
    public static LocalXmlImpl getLocalsService() {
        return new LocalXmlImpl();
    }


    public static ReasonXmlImpl getReasonsService() {
        return new ReasonXmlImpl();
    }

    public static RoleXmlImpl getRolesService() {
        return new RoleXmlImpl();
    }

    public static UserXmlImpl getUsersService() {
        return new UserXmlImpl();
    }

    public static ReservationXmlImpl getReservationService() {
        return new ReservationXmlImpl();
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
