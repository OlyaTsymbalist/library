package com.example.dbjavafx.domain;

import java.io.Serializable;
import java.sql.Date;
/*журнал реєстрації видачі-повернення книг*/
public class BookRegister implements Serializable {
    private static final long serialVersionUID = 1L;
    //поля
    private long id;
    //назви полів співпадають з назвами колонок таблиці personReader в dbntu
    private Book bookId;
    private Date vudanoDt; //yyyy-mm-dd
    private PersonReader personReaderId;
    private Date povernenoDt; //yyyy-mm-dd


    //конструктор 1
    public BookRegister() {
        super();
    }

    //конструктор 2
    public BookRegister(Book bookId, Date vudanoDt,
                        PersonReader personReaderId,
                        Date povernenoDt) {
        super();
        this.bookId = bookId;
        this.vudanoDt = vudanoDt;
        this.personReaderId = personReaderId;
        this.povernenoDt = povernenoDt;
    }
    //конструктор 3
    public BookRegister(long id, Book bookId,
                        Date vudanoDt,
                        PersonReader personReaderId,
                        Date povernenoDt) {
        super();
        this.id = id;
        this.bookId = bookId;
        this.vudanoDt = vudanoDt;
        this.personReaderId = personReaderId;
        this.povernenoDt = povernenoDt;
    }

    //конструктор 4
    public BookRegister(Book bookId,
                        Date vudanoDt,
                        PersonReader personReaderId) {
        super();
        this.bookId = bookId;
        this.vudanoDt = vudanoDt;
        this.personReaderId = personReaderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Date getVudanoDt() {
        return vudanoDt;
    }

    public void setVudanoDt(Date vudanoDt) {
        this.vudanoDt = vudanoDt;
    }

    public PersonReader getPersonReaderId() {
        return personReaderId;
    }

    public void setPersonReaderId(PersonReader personReaderId) {
        this.personReaderId = personReaderId;
    }

    public Date getPovernenoDt() {
        return povernenoDt;
    }

    public void setPovernenoDt(Date povernenoDt) {
        this.povernenoDt = povernenoDt;
    }

    @Override
    public String toString() {
        return "\nBookRegister"  + "\n[id=" + id + ", book=" + bookId.getId() + "\nbook title=" + bookId.getTitle()+
                "\nauthors=" + bookId.getAuthor() + "\nData vydachi=" + vudanoDt + ",\npersonReader=" +
                personReaderId.getId() + "\nFirst/Second name=" + personReaderId.getFirstName() + ", " + personReaderId.getLastName() +
                "\nphone number=" + personReaderId.getPhone() + ",\nData povernennya=" + povernenoDt + "]\n";
    }


}