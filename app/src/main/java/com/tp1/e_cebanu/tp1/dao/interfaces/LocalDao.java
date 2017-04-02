package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Local;
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
 * @description Interface pour LOCALS
 */

public interface LocalDao {
    public void create(Local local);
    public void update(Local local);
    public void delete(Local local);
    public Local find(Local local);
    public Local findByNombre(int nombre);
    public List<Local> findAll()  throws IOException, ParserConfigurationException;

    public Local findById(int localId);
}
