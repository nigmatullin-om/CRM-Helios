package com.becomejavasenior.dao;

import com.becomejavasenior.Role;

import java.util.List;

public interface RoleDao {
    public int create(Role role);
    public Role read(int id);
    public boolean update(Role role);
    public boolean delete(Role role);
    public List<Role> findAll();
}
