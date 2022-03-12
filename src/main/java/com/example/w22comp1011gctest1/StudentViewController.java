package com.example.w22comp1011gctest1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, Integer> studentNumCol;

    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> lastNameCol;

    @FXML
    private TableColumn<Student, String> telephoneCol;

    @FXML
    private TableColumn<Student, String> addressCol;

    @FXML
    private TableColumn<Student, String> provinceCol;

    @FXML
    private TableColumn<Student, Integer> avgGradeCol;

    @FXML
    private TableColumn<Student, String> majorCol;

    @FXML
    private CheckBox ontarioCheckBox;

    @FXML
    private Label numOfStudentsLabel;

    @FXML
    private CheckBox honourRollCheckBox;

    @FXML
    private ComboBox<String> areaCodeComboBox;

    @FXML
    private void applyFilter()  {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Question 2a: populate the tableview, instantiate 1000 Student objects based on a query to the database
         studentNumCol.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        avgGradeCol.setCellValueFactory(new PropertyValueFactory<>("avgGrade"));
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        tableView.getItems().clear();
        tableView.getItems().addAll(DBUtility.getStudentFromDB());

        //Question 2b: populate the ComboBox with a sorted list of distinct area codes from the student’s telephone numbers
        areaCodeComboBox.getItems().addAll(DBUtility.getAreaCodeFromDB());

        //Question 2c: The numOfStudentsLabel should indicate how many students are in the table
        //numOfStudentsLabel.setText("Number of Students: " + getNumOfStudent() );
        numOfStudentsLabel.setText("Number of Students: " + tableView.getItems().size() );

        areaCodeComboBox.getSelectionModel().getSelectedItem();
        
         
    }
    /*
    public static int getNumOfStudent(){
        int numOfStudent = 0;

        for(Student s : DBUtility.getStudentFromDB()){
            numOfStudent++;
        }

        return numOfStudent;
        
    }*/
}
