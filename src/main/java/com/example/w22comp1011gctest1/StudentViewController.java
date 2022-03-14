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
import java.util.TreeSet;

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

    ArrayList<Student> students = new ArrayList<>(DBUtility.getStudentFromDB());


    @FXML
    private void applyFilter()  {

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).


         ObservableList<Student> backingList =  tableView.getItems();
        FilteredList<Student> filteredData = new FilteredList<>(backingList, s -> true);


        // 2d. Set the filter Predicate whenever the filter changes.
        ontarioCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
            filteredData.setPredicate(student -> {
                //  if newValue is  unchecked, check the other 2 filters.

                if ((!newValue || student.getProvince().toString().equals("ON"))
                        && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                        && (areaCodeComboBox.getSelectionModel().isEmpty()
                        ||  areaCodeComboBox.getSelectionModel().getSelectedItem().equals("All")
                        ||  student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem())
                )) {
                            return true; // Filter matches
                    } else
                            return false; // Does not match.

                });
        });

        //Question 2e honourRollCheckBox

        honourRollCheckBox .selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
            filteredData.setPredicate(student -> {
                // If not selected, check the other 2 filters.
                if ((!newValue || student.getAvgGrade() >=80)
                        && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                        && (areaCodeComboBox.getSelectionModel().isEmpty()
                        || areaCodeComboBox.getSelectionModel().getSelectedItem().equals("All")
                        || student.getTelephone().substring(0, 3).equals(areaCodeComboBox.getSelectionModel().getSelectedItem()))
                )  {
                        return true; // Filter matches
                    } else
                       return false; // Does not match.
            });
        });

        //Question 2f areaCodeComboBox
        areaCodeComboBox.valueProperty().addListener((Observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If not selected, display all students.
                if ((areaCodeComboBox.getSelectionModel().isEmpty()
                        || areaCodeComboBox.getSelectionModel().getSelectedItem().equals("All")
                        || areaCodeComboBox.getSelectionModel().getSelectedItem().equals(student.getTelephone().substring(0, 3)))
                       &&( !ontarioCheckBox.isSelected() || student.getProvince().toString().equals("ON"))
                        && (!honourRollCheckBox.isSelected() || (student.getAvgGrade() >=80))
                )  {
                    return true;
                } else
                    return false;
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
         SortedList<Student> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
         //tableView.getItems().clear();
          tableView.setItems(sortedData);


         // tableView.getItems().addAll(sortedData);
        numOfStudentsLabel.setText("Number of Students: " +  tableView.getItems().size());
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
        tableView.getItems().addAll(students);

        //Question 2b: populate the ComboBox with a sorted list of distinct area codes from the student’s telephone numbers
        areaCodeComboBox.getItems().addAll(DBUtility.getAreaCodeFromDB());
        //areaCodeComboBox.getItems().addAll(getAreaCodes());

        //Question 2c: The numOfStudentsLabel should indicate how many students are in the table

        numOfStudentsLabel.setText("Number of Students: " + tableView.getItems().size() );

        areaCodeComboBox.getSelectionModel().getSelectedItem();

         applyFilter();
         
    }

    private TreeSet<String> getAreaCodes(){
        TreeSet areaCodes = new TreeSet();
        areaCodes.add("All");

        for(Student student : students){
            areaCodes.add(student.getTelephone().substring(0,3));
        }

        return areaCodes;
        
    }
}
