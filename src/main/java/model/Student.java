package model;

import java.sql.Date;

public class Student {

    private int id;
    private String studentCode;
    private String fullName;
    private Date dob;
    private Date enrollmentDate;
    private String homeroomClass;
    private int majorId;

    public Student() {
    }

    public Student(int id, String studentCode, String fullName, Date dob, Date enrollmentDate, String homeroomClass, int majorId) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.dob = dob;
        this.enrollmentDate = enrollmentDate;
        this.homeroomClass = homeroomClass;
        this.majorId = majorId;
    }

    // --- Getters & Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getHomeroomClass() {
        return homeroomClass;
    }

    public void setHomeroomClass(String homeroomClass) {
        this.homeroomClass = homeroomClass;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }
}
