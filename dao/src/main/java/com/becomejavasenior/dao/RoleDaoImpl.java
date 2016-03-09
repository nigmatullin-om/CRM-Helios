package com.becomejavasenior.dao;

import com.becomejavasenior.Role;

import javax.sql.DataSource;
import java.util.List;

public class RoleDaoImpl extends AbstractDao implements RoleDao {
    public int create(Role role) {
        return 0;
    }

    public Role read(int id) {
        return null;
    }

    public boolean update(Role role) {
        return false;
    }

    public boolean delete(Role role) {
        return false;
    }

    public List<Role> findAll() {
        return null;
    }

    public RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }
}
