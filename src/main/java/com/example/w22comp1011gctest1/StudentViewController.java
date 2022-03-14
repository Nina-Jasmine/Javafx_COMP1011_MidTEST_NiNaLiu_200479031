package com.example.w22comp1011gctest1;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).

        //cast ArrayList to observableArrayList
        // ObservableList<Student> backingList = FXCollections.observableArrayList();
        /*for (Student student : DBUtility.getStudentFromDB()) {
            backingList.add(student);
        }*/
        // backingList.addAll(DBUtility.getStudentFromDB());



       ObservableList<Student> backingList =  tableView.getItems();
        FilteredList<Student> filteredData = new FilteredList<>(backingList, s -> true);


        // 2d. Set the filter Predicate whenever the filter changes.
        ontarioCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
            filteredData.setPredicate(student -> {
                //  if newValue is  unchecked, check the other 2 filters.

                if (!newValue && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                        && (areaCodeComboBox.getSelectionModel().isEmpty() || (student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem()))
                )) {
                    return true;
                }else {

                    if (student.getProvince().toString().equals("ON") && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                            && (areaCodeComboBox.getSelectionModel().isEmpty() || (student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem()))
                    )) {

                            return true; // Filter matches 'ON'.
                    } else
                            return false; // Does not match.
                     }
                });
        });

        //Question 2e honourRollCheckBox

        honourRollCheckBox .selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
            filteredData.setPredicate(student -> {
                // If not selected, check the other 2 filters.
                if (!newValue  && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                        && (areaCodeComboBox.getSelectionModel().isEmpty() || (student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem()))
                )) {
                    return true;
                }else {

                    if (student.getAvgGrade() >=80 && (!ontarioCheckBox.isSelected() || (student.getProvince().toString().equals("ON")))
                            && ((areaCodeComboBox.getSelectionModel().isEmpty()) || (student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem()))
                    )){

                        return true; // Filter matches 'ON'.
                    } else
                       return false; // Does not match.
                }
            });
        });

        //Question 2f areaCodeComboBox
        areaCodeComboBox.valueProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If not selected, display all students.
                if (areaCodeComboBox.getSelectionModel().isEmpty()) {
                    return true;
                }
                if (student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem())
                        && (!ontarioCheckBox.isSelected() || (student.getProvince().toString().equals("ON")))
                        && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                ) {
                    return true;
                } else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
         SortedList<Student> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.

        //this.tableView.getItems().clear();
         tableView.setItems(sortedData);
        numOfStudentsLabel.setText("Number of Students: " +  sortedData.size());
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Question 2a: populate the tableview, instantiate 1000 Student objects based on a query to the database
        studentNumCol.setCellValueFactory(new PropertyValueFactory<>("studentNum"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
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

         applyFilter();


        
         
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
