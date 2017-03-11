package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Reason;

import java.util.List;

/**
 * Created by User on 09.03.2017.
 */

public interface ReasonDao {
    public Reason findById(int id);
    public List<Reason> findAll();
    public void delete(Reason reason);
}
