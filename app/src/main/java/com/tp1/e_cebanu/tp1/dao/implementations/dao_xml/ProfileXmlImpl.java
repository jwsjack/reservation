package com.tp1.e_cebanu.tp1.dao.implementations.dao_xml;

import com.tp1.e_cebanu.tp1.dao.interfaces.ProfileDao;
import com.tp1.e_cebanu.tp1.models.Profile;
import com.tp1.e_cebanu.tp1.models.User;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Java# version 1.8.0
 *
 * @author EUGENIU CEBANU / matricule: 20025851
 * @author jwsjack3@gmail.com
 * @version 1
 * @class UserXmlImpl
 * @package TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @date 2017-02-20
 * @description Mise en œuvre de lecture / écriture XML pour PROFILES
 */

public class ProfileXmlImpl implements ProfileDao {

    public XMLParser xmlParser = null;

    public ProfileXmlImpl() {
        xmlParser = new XMLParser();
        xmlParser.setFileName(Profile.FILENAME);
    }

    @Override
    public void update(Profile profile) {

    }

    @Override
    public Profile findByLogin(String login) {
        return null;
    }

    @Override
    public Profile findByUserId(int userId) {
        return null;
    }

    @Override
    public List<Profile> findAll() throws IOException, ParserConfigurationException {
        return null;
    }

    @Override
    public Profile findById(int profileId) {
        return null;
    }
}
