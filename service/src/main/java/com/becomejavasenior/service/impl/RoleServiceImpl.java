package com.becomejavasenior.service.impl;


import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.RoleDao;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Role;
import com.becomejavasenior.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;
    public RoleServiceImpl(){
        roleDao =  new DaoFactoryImpl().getRoleDao();
    }
    @Override
    public int create(Role role) throws DatabaseException {
        return roleDao.create(role);
    }

    @Override
    public Role getRoleById(int id) throws DatabaseException {
        return roleDao.getRoleById(id);
    }

    @Override
    public int update(Role role) throws DatabaseException {
        return roleDao.update(role);
    }

    @Override
    public int getMaxId() throws DatabaseException {
        return roleDao.getMaxId();
    }

    @Override
    public int delete(Role role) throws DatabaseException {
        return roleDao.delete(role);
    }

    @Override
    public List<Role> findAll() throws DatabaseException {
        return roleDao.findAll();
    }
}
