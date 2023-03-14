package com.example.dbjavafx.dao;

import com.example.dbjavafx.ConnectionFactory;
import com.example.dbjavafx.domain.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.dbjavafx.ConnectionFactory.getConnection;


public class BookDAOImpl implements BookDAO {

    @Override
    public Book getBookById(long id) {
        // Connection connection = ConnectionFactory.getConnection();

        //try-with-resources
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE id=" + id);
        )
        {

            if(rs.next())
            {
                ///для зручності перенесли даний код у приватний метод extractBookFromResultSet
            	/*
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle( rs.getString("title") );
                book.setAuthor( rs.getString("author") );
                book.setPrintYear( rs.getInt("printYear") );
                book.setCountOfPages( rs.getInt("countOfPages") );

                return book;
                */

                return extractBookFromResultSet(rs);


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
    public List<Book> getBookByTitleAndAuthor(String title, String author) {

        //try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM book WHERE title=? AND author=?"))
        {

            ps.setString(1, title);
            ps.setString(2, author);

            ResultSet rs = ps.executeQuery();

            List<Book> books = new ArrayList<>(); //змінна для формування списку книжок

            while(rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);  //додаємо одну книжку до списку
            }

            return books; //повертаємо список книжок

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public List<Book> getAllBooks() {

        //try-with-resources
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM `book`");
        )
        {

            List<Book> books = new ArrayList<>(); //змінна для формування списку книжок

            while(rs.next())
            {
                Book book = extractBookFromResultSet(rs);
                books.add(book);  //додаємо одну книжку до списку
            }

            return books; //повертаємо список книжок

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean insertBook(Book book) {

        //try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO book(title, author, printyear, countofpages) VALUES (?, ?, ?, ?)");
        )
        {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPrintYear());
            ps.setInt(4, book.getCountOfPages());

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
    public boolean updateBook(Book book) {

        //try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE book set title = ?, author = ?, printyear = ?, countofpages = ? WHERE id=?" );
        )
        {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPrintYear());
            ps.setInt(4, book.getCountOfPages());
            ps.setLong(5, book.getId());

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
    public boolean deleteBook(long id) {

        //try-with-resources
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM book WHERE id=?");
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



    private static Book extractBookFromResultSet(ResultSet rs) throws SQLException {

        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle( rs.getString("title") );
        book.setAuthor( rs.getString("author") );
        book.setPrintYear( rs.getInt("printYear") );
        book.setCountOfPages( rs.getInt("countOfPages") );

        return book;

    }

}
