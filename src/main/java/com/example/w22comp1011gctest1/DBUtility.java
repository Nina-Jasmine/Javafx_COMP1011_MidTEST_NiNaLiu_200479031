package com.example.w22comp1011gctest1;

 import java.sql.*;

import java.util.ArrayList;
import java.util.Collection;
 import java.sql.DriverManager;


public class DBUtility {
    private static String user = "NiNa200479031";
    private static String password = "NFqnmGtIZt";
    private static String connectURL = "jdbc:mysql://172.31.22.43:3306/NiNa200479031";

    public static ArrayList<Student> getStudentFromDB() {
        ArrayList<Student> students = new ArrayList<>();


        //query the DB and create objects / add them to the list

        String sql = "select studentNum, firstName, lastName, homeAddress, province, telephone, avgGrade, major \n" +
                "from students;";


        try (
                Connection conn = DriverManager.getConnection(connectURL, user, password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {
                int studentNum = resultSet.getInt("studentNum");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("lastName");
                String telephone = resultSet.getString("telephone");
                String address = resultSet.getString("homeAddress");
                ProvinceList province = ProvinceList.valueOf(resultSet.getString("province"));
                int avgGrade = resultSet.getInt("avgGrade");
                String major = resultSet.getString("major");


                Student newStudent = new Student(studentNum, firstName, lastName, telephone, address, province, avgGrade, major);
                students.add(newStudent);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static ArrayList<String> getAreaCodeFromDB() {
        ArrayList<String> areaCodes = new ArrayList<>();


        //query the DB and get telephone

        String sql = "select distinct left(telephone, 3) as areaCode\n" +
                "from students;";


        try (
                Connection conn = DriverManager.getConnection(connectURL, user, password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while (resultSet.next()) {

                String areaCode = resultSet.getString("areaCode");

                areaCodes.add(areaCode);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return areaCodes;
    }
}
