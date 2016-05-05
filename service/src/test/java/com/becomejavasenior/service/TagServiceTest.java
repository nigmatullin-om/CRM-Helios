package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.TagDaoImpl;
import com.becomejavasenior.model.Tag;
import com.becomejavasenior.service.impl.TagServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDaoImpl tagDao;

    @Mock
    private Tag tag;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        when(tagDao.create(any(Tag.class))).thenReturn(1);
        tagService.create(tag);

        verify(tagDao).create(tag);
    }

    @Test
    public void testGetTagById() throws Exception {
       when(tagDao.getTagById(1)).thenReturn(tag);

        Tag resultTag = tagService.getTagById(1);

        verify(tagDao).getTagById(1);
        Assert.assertEquals(tag, resultTag);
    }

    @Test
    public void testUpdate() throws Exception {
        when(tagDao.update(any(Tag.class))).thenReturn(1);
        tagService.update(tag);

        verify(tagDao).update(tag);
    }

    @Test
    public void testDelete() throws Exception {
        when(tagDao.delete(any(Tag.class))).thenReturn(1);
        tagService.delete(tag);

        verify(tagDao).delete(tag);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Tag> tags = new LinkedList<>();

        Tag firstTag  = new Tag();
        firstTag.setId(1);
        tags.add(firstTag);

        Tag secondTag  = new Tag();
        secondTag.setId(2);
        tags.add(secondTag);

        when(tagDao.findAll()).thenReturn(tags);
        List<Tag> result = tagService.findAll();

        verify(tagDao).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMaxId() throws DatabaseException {
        when(tagDao.getMaxId()).thenReturn(1);
        int result = tagService.getMaxId();

        verify(tagDao).getMaxId();
        assertEquals(1, result);
    }

    @Test
    public void testIsExist() throws  DatabaseException{
        List<Tag> tags = new LinkedList<>();

        Tag firstTag  = new Tag();
        firstTag.setId(1);
        firstTag.setName("first");
        tags.add(firstTag);

        Tag secondTag  = new Tag();
        secondTag.setId(2);
        secondTag.setName("second");
        tags.add(secondTag);

        when(tagDao.findAll()).thenReturn(tags);
        boolean result = tagService.isExist(firstTag);
        assertEquals(true,result);
    }
}
