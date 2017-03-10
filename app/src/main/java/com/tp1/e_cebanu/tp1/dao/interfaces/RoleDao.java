package com.tp1.e_cebanu.tp1.dao.interfaces;

import com.tp1.e_cebanu.tp1.models.Role;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public interface RoleDao {
    public void create(Role local);
    public void update(Role local);
    public void delete(Role local);
    public Role find(Role local);
    public List<Role> findAll()  throws IOException, ParserConfigurationException;

    public Role findById(int roleId);
}
