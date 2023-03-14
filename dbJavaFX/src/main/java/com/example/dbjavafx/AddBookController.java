package com.example.dbjavafx;

import com.example.dbjavafx.ConnectionFactory;
import com.example.dbjavafx.domain.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookController {

    @FXML
    private Button cleanBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private TextField authorTxtFld;
    @FXML
    private TextField nameTxtFld;
    @FXML
    private TextField pagesTxtFld;
    @FXML
    private TextField yearPrintTxtFld;

    Connection connection;
    PreparedStatement ps;
    Book book = new Book();
    long bookId;
    String query;
    private boolean update;


    @FXML
    void saveClick(ActionEvent event) throws SQLException {

        connection = ConnectionFactory.getConnection();

        String name = nameTxtFld.getText();
        String author = authorTxtFld.getText();
        int printYear = Integer.parseInt(yearPrintTxtFld.getText());
        int countOfPages = Integer.parseInt(pagesTxtFld.getText());

        if(name.trim().isEmpty() | author.trim().isEmpty() | printYear == 0 | countOfPages == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Please fill all cells");
            alert.showAndWait();
            alert.show();
        }
        else {
            getQuery();
            insertData();
            clean();
        }
    }

    public void setUpdate(boolean a) {
        this.update = a;
    }

    private void getQuery() throws SQLException {
        if (!update)
            query = "INSERT INTO `book`( `title`, `author`, `printyear`, `countofpages`) VALUES (?,?,?,?)";
        else {
            query = "UPDATE `book` SET "
                    + "`title`=?,"
                    + "`author`=?,"
                    + "`printyear`=?,"
                    + "`countofpages`=? WHERE id = '" + bookId + "'";
        }
    }
    private void insertData() throws SQLException {
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nameTxtFld.getText());
            ps.setString(2, authorTxtFld.getText());
            ps.setInt(3, Integer.parseInt(yearPrintTxtFld.getText()));
            ps.setInt(4, Integer.parseInt(pagesTxtFld.getText()));
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void clean() {
        pagesTxtFld.setText(null);
        yearPrintTxtFld.setText(null);
        nameTxtFld.setText(null);
        authorTxtFld.setText(null);
    }

    public void setBookTextField(long id, String name, String author, int printYear, int countOfPages) {
        bookId = id;
        nameTxtFld.setText(name);
        authorTxtFld.setText(author);
        yearPrintTxtFld.setText(String.valueOf(printYear));
        pagesTxtFld.setText(String.valueOf(countOfPages));
    }


}
