package com.example.dbjavafx;

import com.example.dbjavafx.ConnectionFactory;
import com.example.dbjavafx.domain.Book;
import com.example.dbjavafx.domain.PersonReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRegisterController {

    @FXML
    private Button cleanBtn;
    @FXML
    private TextField personReaderIdTxtFld;
    @FXML
    private TextField bookIdTxtFld;

    @FXML
    private DatePicker issuedDateDtPicker;

    @FXML
    private DatePicker returnedDateDtPicker;

    @FXML
    private Button saveBtn;

    Connection connection;
    PreparedStatement ps;
    PersonReader personId;
    Book bookId;


    long bookRegisterId;
    String query;
    private boolean update;

    @FXML
    void saveClick(ActionEvent event) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();

            Long bookId = Long.valueOf(bookIdTxtFld.getText());
            Date issuedDt = Date.valueOf(issuedDateDtPicker.getValue());
            Long personReaderId = Long.valueOf(personReaderIdTxtFld.getText());
            Date returnedDt = Date.valueOf(returnedDateDtPicker.getValue());


            if (returnedDt == null ||  issuedDt == null || personReaderId == 0 || bookId == 0 ) {
                saveBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error");
                        alert.setContentText("Please fill all cells");
                        alert.showAndWait();
                        alert.show();
                    }
                });

            } else {
                getQuery();
                insertDate();
                clean();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void setUpdate(boolean a) {
        this.update = a;
    }

    private void getQuery() {
        if (!update)
            query = "INSERT INTO `bookregister2.0`(bookId, vydanoDt, personReaderId, povernenoDt) " +
                    "VALUES (?, ?, ?, ?)";
        else {
            query =  "UPDATE `bookregister2.0` SET " +
                    "bookId = ?," +
                    "vydanoDt = ?, " +
                    "personReaderId = ?, " +
                    "povernenoDt = ?" +
                    "WHERE id='"+ bookRegisterId +"'";
        }
    }
    private void insertDate() throws SQLException{
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(  1, Long.parseLong(bookIdTxtFld.getText()));
            ps.setDate(  2, Date.valueOf(issuedDateDtPicker.getValue()));
            ps.setLong(  3, Long.parseLong(personReaderIdTxtFld.getText()));
            ps.setDate(  4, Date.valueOf(returnedDateDtPicker.getValue()));

            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clean() {
        bookIdTxtFld.setText(null);
        issuedDateDtPicker.setValue(null);
        personReaderIdTxtFld.setText(null);
        returnedDateDtPicker.setValue(null);
    }

    public void setRegisterTextField(long id, Book bookId, Date issuedDt, PersonReader personId, Date returnedDt) {
        bookRegisterId = id;
        this.bookId = bookId;
        issuedDateDtPicker.setValue(issuedDt.toLocalDate());
        this.personId = personId;
        returnedDateDtPicker.setValue(returnedDt.toLocalDate());
    }

}
