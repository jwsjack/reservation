package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Role;
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
 * @description Interface pour ROLES
 */
public interface RoleDao {
    public void create(Role local);
    public void update(Role local);
    public void delete(Role local);
    public Role find(Role local);
    public List<Role> findAll()  throws IOException, ParserConfigurationException;

    public Role findById(int roleId);
}
