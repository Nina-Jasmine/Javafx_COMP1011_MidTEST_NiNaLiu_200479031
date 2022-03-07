package com.example.w22comp1011gctest1;

public class Student {

    private int studentNum;
    private String firstName;
    private String lastName;
    private String telephone;
    private String address;
    private ProvinceList province;
    private int avgGrade;
    private String major;

    public Student(int studentNum, String firstName, String lastName, String telephone,  String address, ProvinceList province, int avgGrade, String major) {
        setStudentNum(studentNum);
        setFirstName(firstName);
        setLastName(lastName);
        setTelephone(telephone);
        setAddress(address);
        setProvince(province);
        setAvgGrade (avgGrade);
        setMajor(major);
    }



    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        if(studentNum > 200034000)
        this.studentNum = studentNum;
        else
            System.out.println("student number should be greater than 200034000");
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.length()>1)
        this.firstName = firstName;
        else
            System.out.println(" First name must be more than 1 character");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length()>1)
        this.lastName = lastName;
        else
            System.out.println(" Last name must be more than 1 character");
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone.matches("\\(?[2-9][0-9][0-9]\\)?[-\\s.]?[2-9]\\d{2}[-\\s.]?[0-9]{4}"))
        this.telephone = telephone;
        else
            System.out.println("c.\tTelephone number should match the North American dialing plan");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address.length()>6)
        this.address = address;
        else
            System.out.println("Address must be more than 6 characters");
    }

    public ProvinceList getProvince() {
        return province;
    }

    public void setProvince(ProvinceList province) {
        this.province = province;
    }

    public int getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(int avgGrade) {
        if (avgGrade >=0 && avgGrade <=100)
        this.avgGrade = avgGrade;
        else
            System.out.println("f.\tAverage grade must be in the range of 0-100 ");
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        if (major.length() > 5)
        this.major = major;
        else
            System.out.println("Major must be greater than 5 characters");

    }
}
