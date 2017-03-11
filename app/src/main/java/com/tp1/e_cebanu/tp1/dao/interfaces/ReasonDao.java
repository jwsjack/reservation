package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Reason;
import java.util.List;
/**
 * Java# version 1.8.0
 *
 * @class UserXmlImpl
 * @package    TP #1 / IFT 1155 A - Programmation mobile à plateforme libre
 * @author     EUGENIU CEBANU / matricule: 20025851
 * @author     jwsjack3@gmail.com
 * @version    1
 * @date       2017-02-20
 * @description Interface pour REASONS
 */
public interface ReasonDao {
    public void create(Reason reason);
    public void update(Reason reason);
    public void delete(Reason reason);
    public Reason findById(int id);
    public List<Reason> findAll();
}
