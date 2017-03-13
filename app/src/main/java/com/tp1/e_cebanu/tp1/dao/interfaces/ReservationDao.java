package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Reservation;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
 * @description Interface pour RESERVATIONS
 */

public interface ReservationDao {
    public void create(Reservation reservation);
    public void update(Reservation reservation);
    public void delete(Reservation reservation);
    public Reservation find(Reservation reservation);
    public List<Reservation> findByDate(Calendar date);
    public List<Reservation> findAll()  throws IOException, ParserConfigurationException;


}
