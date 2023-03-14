package com.example.dbjavafx.domain;

public class Book  {

    //поля
    private long id;
    //назви полів співпадають з назвами колонок таблиці book в dbntu
    private String title;// назва книги
    private String author;// автор або декілька авторів
    private int printYear;// рік видання
    private int countOfPages;//кількість сторінок

    //конструктор 1
    public Book() {
        super();
    }
    //конструктор 2
    public Book(String title, String author, int printYear, int countOfPages) {
        super();
        this.title = title;
        this.author = author;
        this.printYear = printYear;
        this.countOfPages = countOfPages;
    }
    //конструктор 3
    public Book(long id, String title, String author, int printYear, int countOfPages) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.printYear = printYear;
        this.countOfPages = countOfPages;
    }

    //геттери та сеттери
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPrintYear() {
        return printYear;
    }
    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }
    public int getCountOfPages() {
        return countOfPages;
    }
    public void setCountOfPages(int countOfPages) {
        this.countOfPages = countOfPages;
    }
    @Override
    public String toString() {
        return "Book" + "\n[id=" + id + ",\ntitle=" + title + ",\nauthor=" + author +
                ",\nprintYear=" + printYear + ",\ncountOfPages=" + countOfPages + "]";
    }






}