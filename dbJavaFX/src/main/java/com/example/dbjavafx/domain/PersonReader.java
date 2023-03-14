package com.example.dbjavafx.domain;

import java.io.Serializable;
import java.sql.Date;
/*читачі*/
public class PersonReader implements Serializable {
    private static final long serialVersionUID = 1L;
    //поля
    private long id;
    //назви полів співпадають з назвами колонок таблиці personReader в dbntu
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private Date birthDt; //yyyy-mm-dd
    private String serialOfPassport;
    private int numOfPassport;
    private String address;

    //конструктор 1
    public PersonReader() {
        super();
    }

    //конструктор 2
    public PersonReader(String firstName, String middleName, String lastName,
                        String phone, Date birthDt, String serialOfPassport,
                        int numOfPassport, String address) {
        super();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDt = birthDt;
        this.serialOfPassport = serialOfPassport;
        this.numOfPassport = numOfPassport;
        this.address = address;
    }
    //конструктор 3
    public PersonReader(long id, String firstName, String middleName, String lastName,
                        String phone, Date birthDt,	String serialOfPassport,
                        int numOfPassport, String address) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDt = birthDt;
        this.serialOfPassport = serialOfPassport;
        this.numOfPassport = numOfPassport;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(Date birthDt) {
        this.birthDt = birthDt;
    }

    public String getSerialOfPassport() {
        return serialOfPassport;
    }

    public void setSerialOfPassport(String serialOfPassport) {
        this.serialOfPassport = serialOfPassport;
    }

    public int getNumOfPassport() {
        return numOfPassport;
    }

    public void setNumOfPassport(int numOfPassport) {
        this.numOfPassport = numOfPassport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonReader \n[id=" + id + ",\n firstName=" + firstName + ",\n middleName=" + middleName +
                ",\n lastName=" + lastName + ",\n phone=" + phone + ",\n birthDt=" + birthDt +
                ",\n serialOfPassport=" + serialOfPassport + ",\n numOfPassport=" + numOfPassport +
                ",\n address=" + address + "]";
    }
}