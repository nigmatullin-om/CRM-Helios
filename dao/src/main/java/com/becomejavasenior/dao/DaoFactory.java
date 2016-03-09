package com.becomejavasenior.dao;

public interface DaoFactory {
    public CompanyDao getCompanyDao();
    public ContactDao getContactDao();
    public DealDao getDealDao();
    public FileDao getFileDao();
    public LanguageDao getLanguageDao();
    public NoteDao getNoteDao();
    public RoleDao getRoleDao();
    public TagDao getTagDao();
    public TaskDao getTaskDao();
    public UserDao getUserDao();

}
