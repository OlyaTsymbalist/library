package com.example.dbjavafx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.dbjavafx.ConnectionFactory;
import com.example.dbjavafx.domain.PersonReader;

public class PersonReaderDAOImpl implements PersonReaderDAO{

    @Override
    public PersonReader getPersonReaderById(long id) {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM personreader WHERE id=" + id);
        )
        {



            if(rs.next())
            {
                ///для зручності перенесли даний код у приватний метод extractPersonReaderFromResultSet
            	/*
                PersonReader personReader = new PersonReader();

	        	personReader.setId(rs.getLong("id"));
	        	personReader.setFirstName( rs.getString("firstName") );
		        personReader.setMiddleName( rs.getString("middleName") );
		        personReader.setLastName( rs.getString("lastName") );
		        personReader.setBirthDt( rs.getDate("birthDt") );
		        personReader.setSerialOfPassport( rs.getString("serialOfPassport") );
		        personReader.setNumOfPassport( rs.getInt("numOfPassport") );
		        personReader.setAddress( rs.getString("address") );

                return personReader;
                */

                return extractPersonReaderFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
     /*   finally {
        	if (connection != null) {
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }*/

        return null;

    }

    @Override
    public List<PersonReader> getBookByFirstNameAndLastName(String firstName, String lastName) {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM personreader WHERE firstName=? AND lastName=?"))
        {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet rs = ps.executeQuery();

            List<PersonReader> personReaders = new ArrayList<>(); //змінна для формування списку читачів

            while(rs.next())
            {
                PersonReader personReader = extractPersonReaderFromResultSet(rs);
                personReaders.add(personReader);  //додаємо одного читача до списку
            }

            return personReaders; //повертаємо список читачів

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public List<PersonReader> getAllPersonReaders() {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM personreader");
        )
        {

            List<PersonReader> personReaders = new ArrayList<>(); //змінна для формування списку читачів

            while(rs.next()) {
                PersonReader personReader = extractPersonReaderFromResultSet(rs);
                personReaders.add(personReader);  //додаємо одного читача до списку
            }

            return personReaders; //повертаємо список читачів

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean insertPersonReader(PersonReader personReader) {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO personreader(firstName, middleName, lastName, phone, birthDt, serialOfPassport, numOfPassport,  address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        )
        {

            ps.setString(1, personReader.getFirstName());
            ps.setString(2, personReader.getMiddleName());
            ps.setString(3, personReader.getLastName());
            ps.setString(4, personReader.getPhone());
            ps.setDate(  5, personReader.getBirthDt());
            ps.setString(6, personReader.getSerialOfPassport());
            ps.setInt(   7, personReader.getNumOfPassport());
            ps.setString(8, personReader.getAddress());


            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if(i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    @Override
    public boolean updatePersonReader(PersonReader personReader) {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(""
                     + " UPDATE personreader "
                     + "    set firstName = ?, "
                     + "        middleName = ?, "
                     + "        lastName = ?, "
                     + "        phone = ?, "
                     + "        birthDt = ?, "
                     + "        serialOfPassport = ?, "
                     + "        numOfPassport = ?,  "
                     + "        address = ? "
                     + "  WHERE id=?");
        )
        {
            ps.setString(1, personReader.getFirstName());
            ps.setString(2, personReader.getMiddleName());
            ps.setString(3, personReader.getLastName());
            ps.setString(4, personReader.getPhone());
            ps.setDate(5, personReader.getBirthDt());
            ps.setString(6, personReader.getSerialOfPassport());
            ps.setInt(7, personReader.getNumOfPassport());
            ps.setString(8, personReader.getAddress());
            ps.setLong(9, personReader.getId());

            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if(i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    @Override
    public boolean deletePersonReader(long id) {
        //try-with-resources
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM personreader WHERE id=?");
        )
        {

            ps.setLong(1, id);

            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if(i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    private PersonReader extractPersonReaderFromResultSet(ResultSet rs) throws SQLException {

        PersonReader personReader = new PersonReader();

        personReader.setId(               rs.getLong("id"));
        personReader.setFirstName(        rs.getString("firstName") );
        personReader.setMiddleName(       rs.getString("middleName") );
        personReader.setLastName(         rs.getString("lastName") );
        personReader.setPhone(            rs.getString("phone") );
        personReader.setBirthDt(          rs.getDate("birthDt") );
        personReader.setSerialOfPassport( rs.getString("serialOfPassport") );
        personReader.setNumOfPassport(    rs.getInt("numOfPassport") );
        personReader.setAddress(          rs.getString("address") );

        return personReader;

    }

}