package com.becomejavasenior.service;

import com.becomejavasenior.dao.RoleDao;
import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.model.Role;
import com.becomejavasenior.service.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RoleServiceImpl.class)
public class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleDao roleDao;

    @Mock
    private Role role;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getRoleDao()).thenReturn(roleDao);
        roleService = new RoleServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        when(roleDao.create(any(Role.class))).thenReturn(1);
        roleService.create(role);

        verify(roleDao).create(role);

    }

    @Test
    public void testGetRoleById() throws Exception {
        PowerMockito.when(roleDao.getRoleById(1)).thenReturn(role);

        Role resultRole = roleService.getRoleById(1);

        verify(roleDao).getRoleById(1);
        Assert.assertEquals(role, resultRole);
    }

    @Test
    public void testUpdate() throws Exception {
        when(roleDao.update(any(Role.class))).thenReturn(1);
        roleService.update(role);

        verify(roleDao).update(role);
    }

    @Test
    public void testDelete() throws Exception {
        when(roleDao.delete(any(Role.class))).thenReturn(1);
        roleService.delete(role);

        verify(roleDao).delete(role);

    }

    @Test
    public void testFindAll() throws Exception {
        List<Role> roles = new LinkedList<>();

        Role firstRole = new Role();
        firstRole.setId(1);
        firstRole.setName("First Role");

        roles.add(firstRole);

        Role secondRole = new Role();
        secondRole.setId(2);
        secondRole.setName("Second Role");

        roles.add(secondRole);

        when(roleDao.findAll()).thenReturn(roles);
        List<Role> result = roleService.findAll();

        verify(roleDao).findAll();
        assertEquals(2, result.size());
    }

}
