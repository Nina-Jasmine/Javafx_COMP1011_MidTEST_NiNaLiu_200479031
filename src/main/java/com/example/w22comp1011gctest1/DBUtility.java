package com.example.w22comp1011gctest1;

 import java.sql.*;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
 import java.sql.DriverManager;


public class DBUtility {
    private static String user = "NiNa200479031";
    private static String password = "NFqnmGtIZt";
    private static String connectURL = "jdbc:mysql://172.31.22.43:3306/NiNa200479031";

    public static ArrayList<Student> getStudentFromDB() {
        ArrayList<Student> students = new ArrayList<>();

        //query the DB and create CO2Emission objects / add them to the list

        String sql = "select studentNum, firstName, lastName, homeAddress, province, telephone, avgGrade, major \n" +
                "from students;";


        try (
                Connection conn = DriverManager.getConnection(connectURL, user, password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                int studentNum = resultSet.getInt("Student#");
                String firstName = resultSet.getString("First Name");
                String lastName = resultSet.getString("Last Name");
                String telephone = resultSet.getString("Telephone");
                String address = resultSet.getString("Address");
                ProvinceList province = ProvinceList.valueOf(resultSet.getString("Province"));
                int avgGrade = resultSet.getInt("Avg Grade");
                String major = resultSet.getString("Major");


                Student newStudent = new Student(studentNum, firstName, lastName, telephone, address, province, avgGrade, major);

                students.add(newStudent);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
