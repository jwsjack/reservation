package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Reservation;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by User on 08.03.2017.
 */

public interface ReservationDao {
    public void create(Reservation reservation);
    public void update(Reservation reservation);
    public void delete(Reservation reservation);
    public Reservation find(Reservation reservation);
    public List<Reservation> findAll()  throws IOException, ParserConfigurationException;


}
