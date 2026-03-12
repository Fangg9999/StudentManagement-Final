package model;

public class ClassRoom {
    private int id;
    private String classCode;
    private int subjectId;
    private int semesterId;
    private int teacherId;

    public ClassRoom() {}

    public ClassRoom(int id, String classCode, int subjectId, int semesterId, int teacherId) {
        this.id = id;
        this.classCode = classCode;
        this.subjectId = subjectId;
        this.semesterId = semesterId;
        this.teacherId = teacherId;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public int getSemesterId() { return semesterId; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
}