package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Profile;
import com.tp1.e_cebanu.tp1.models.User;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Java# version 1.8.0
 *
 * @class UserXmlImpl
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Interface pour PROFIL d'utilisateur
 */

public interface ProfileDao {

    public void update(Profile profile);


    public Profile findByLogin(String login);

    public Profile findByUserId(int userId);

    public List<Profile> findAll() throws IOException, ParserConfigurationException;

    public Profile findById(int profileId);


}
