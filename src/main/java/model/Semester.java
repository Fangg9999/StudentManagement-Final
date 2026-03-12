package model;

public class Semester {
    private int id;
    private String semesterCode;
    private String semesterName;

    public Semester() {}
    public Semester(int id, String semesterCode, String semesterName) {
        this.id = id; this.semesterCode = semesterCode; this.semesterName = semesterName;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSemesterCode() { return semesterCode; }
    public void setSemesterCode(String semesterCode) { this.semesterCode = semesterCode; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
}