package com.example.dbjavafx.dao;

import com.example.dbjavafx.domain.PersonReader;

import java.util.List;

public interface PersonReaderDAO {
    PersonReader getPersonReaderById(long id);
    List<PersonReader> getBookByFirstNameAndLastName(String firstName, String lastName);
    List<PersonReader> getAllPersonReaders();
    boolean insertPersonReader(PersonReader personReader);
    boolean updatePersonReader(PersonReader personReader);
    boolean deletePersonReader(long id);
}
