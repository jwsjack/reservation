package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Local;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public interface LocalDao {
    public void create(Local local);
    public void update(Local local);
    public void delete(Local local);
    public Local find(Local local);
    public Local findByNombre(int nombre);
    public List<Local> findAll()  throws IOException, ParserConfigurationException;

    public Local findById(int localId);
}
