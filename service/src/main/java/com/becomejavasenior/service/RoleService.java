package com.becomejavasenior.service;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.Role;

import java.util.List;

public interface RoleService {
    int create(Role role) throws DatabaseException;
    Role getRoleById(int id) throws DatabaseException;
    int update(Role role) throws DatabaseException;
    int getMaxId() throws DatabaseException;
    int delete(Role role) throws DatabaseException;
    List<Role> findAll() throws DatabaseException;
}
