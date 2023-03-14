package com.example.dbjavafx.dao;

import com.example.dbjavafx.domain.Book;
import javafx.collections.ObservableList;

import java.util.List;

public interface BookDAO {

    Book getBookById(long id);
    List<Book> getBookByTitleAndAuthor(String title, String author);
    List<Book> getAllBooks();
    boolean insertBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(long id);

}
