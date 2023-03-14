package com.example.dbjavafx;

import com.example.dbjavafx.ConnectionFactory;
import com.example.dbjavafx.domain.PersonReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddReaderController {


    @FXML
    private TextField addressTxtFld;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Button cleanBtn;

    @FXML
    private TextField firstNameTxtFld;

    @FXML
    private TextField lastNameTxtFld;

    @FXML
    private TextField middleNameTxtFld;

    @FXML
    private TextField numPassportTxtFld;

    @FXML
    private TextField phoneTxtFld;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField serialPassportTxtFld;

    Connection connection;
    PreparedStatement ps;
    PersonReader personReader = new PersonReader();
    long personId;
    String query;
    private boolean update;

    @FXML
    void saveClick(ActionEvent event) throws SQLException {
        connection = ConnectionFactory.getConnection();

        String firstName = firstNameTxtFld.getText();
        String middleName = middleNameTxtFld.getText();
        String lastName = lastNameTxtFld.getText();
        String phone = phoneTxtFld.getText();
        DatePicker birthDate = birthDatePicker;
        String serialPassport = serialPassportTxtFld.getText();
        Integer numPassport = Integer.valueOf(numPassportTxtFld.getText());
        String address = addressTxtFld.getText();

        if (firstName.isEmpty() | middleName.isEmpty() | lastName.isEmpty() |
            phone.isEmpty() | birthDate.equals(0) | serialPassport.isEmpty() |
            numPassport.equals(0) | address.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please fill all cells");
            alert.showAndWait();
            alert.show();
        } else {
            getQuery();
            insertDate();
            clean();
        }
    }

    public void setUpdate(boolean a) {
        this.update = a;
    }

    private void getQuery() {
        if (!update)
            query = "INSERT INTO `personreader`(`firstName`, `middleName`, `lastName`, `phone`, `birthDt`," +
                    "`serialOfPassport`, `numOfPassport`,  `address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        else {
            query =  " UPDATE `personreader` SET " +
                    "`firstName` =?," +
                    "`middleName` =?," +
                    "`lastName` =?," +
                    "`phone` =?," +
                    "`birthDt` =?," +
                    "`serialOfPassport` =?," +
                    "`numOfPassport` = ?, " +
                    "`address` = ?" +
                    "WHERE id='"+ personId +"'";
        }
    }
    private void insertDate() throws SQLException{
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, firstNameTxtFld.getText());
            ps.setString(2, middleNameTxtFld.getText());
            ps.setString(3, lastNameTxtFld.getText());
            ps.setString(4, phoneTxtFld.getText());
            ps.setDate(  5, Date.valueOf(birthDatePicker.getValue()));
            ps.setString(6, serialPassportTxtFld.getText());
            ps.setInt(   7, Integer.parseInt(numPassportTxtFld.getText()));
            ps.setString(8, addressTxtFld.getText());

            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clean() {
        firstNameTxtFld.setText(null);
        middleNameTxtFld.setText(null);
        lastNameTxtFld.setText(null);
        phoneTxtFld.setText(null);
        birthDatePicker.setValue(null);
        serialPassportTxtFld.setText(null);
        numPassportTxtFld.setText(null);
        addressTxtFld.setText(null);
    }

    public void setReaderTextField(long id, String name, String middleName, String lastName, String phone,
                                   Date birthDt, String serialOfPassport, int numOfPassport,
                                   String address) {
        personId = id;
        firstNameTxtFld.setText(name);
        middleNameTxtFld.setText(middleName);
        lastNameTxtFld.setText(lastName);
        phoneTxtFld.setText(phone);
        birthDatePicker.setValue(birthDt.toLocalDate());
        serialPassportTxtFld.setText(serialOfPassport);
        numPassportTxtFld.setText(String.valueOf(Integer.parseInt(String.valueOf(numOfPassport))));
        addressTxtFld.setText(address);
    }

}
