package com.tp1.e_cebanu.tp1.dao.interfaces;

import java.util.List;

/**
 * Java# version 1.8.0
 *
 * @class DAO - Data Access Object
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description  Ici vont toutes les méthodes pour les opérations avec des objets
 */
public interface ObjectRepositoryDAO {
    //CRUD
    void create(Object o);
    void save(Object o);
    void delete(Object o);
    //GLOBAL
    Object find(int id);
    List<Object> findAll();
    List<Object> findBy(String[] $criteria);
    Object findOneBy(String[] $criteria);
    //CUSTOM
    Object findByName(String $name);
}
