# 🎓 Teacher Workflow - Complete Implementation Guide

## 📋 Overview
Hệ thống hoàn chỉnh cho Giáo viên quản lý điểm số, xem lớp học và xuất báo cáo thống kê GPA.

---

## 1️⃣ TẦNG TIỆN ÍCH (Util Layer)

### `util/GradingUtil.java`
**Mục đích**: Xếp loại sinh viên dựa trên GPA

```java
- getRank(double score) → String
  • 8.5+ → "Giỏi" (Excellent)
  • 7.0-8.4 → "Khá" (Good)
  • 5.5-6.9 → "Trung bình" (Average)
  • < 5.5 → "Yếu" (Poor)
```

**Trạng thái**: ✅ Hoàn chỉnh

---

## 2️⃣ TẦNG DATA (DAO Layer)

### `dao/GradeDAO.java`
**Methods**:
- `getStudentsAndGrades(classId, subjectId)` → Lấy danh sách sinh viên với điểm hiện tại
- `saveGradesBatch(classId, studentIds[], scores[])` → Lưu điểm hàng loạt (UPSERT)

**Trạng thái**: ✅ Hoàn chỉnh

### `dao/ReportDAO.java`
**Methods**:
- `getStudentGPAList()` → Lấy danh sách GPA trung bình của tất cả sinh viên (GROUP BY, ORDER BY DESC)

**Trạng thái**: ✅ Hoàn chỉnh

---

## 3️⃣ TẦNG XỬ LÝ (Servlet Layer)

### `controller/MyClassServlet.java`
**URL**: `/teacher/my-classes`
**Chức năng**: Hiển thị danh sách lớp được phân công
- Lấy teacherId từ session
- Gọi `ClassDAO.getClassesByTeacherId(teacherId)`
- Forward tới `my-classes.jsp`

**Trạng thái**: ✅ Hoàn chỉnh

### `controller/GradeServlet.java`
**URL**: `/teacher/grades`
**Chức năng**: Liệt kê các lớp để chọn nhập điểm
- Lấy teacherId từ session
- Gọi `ClassDAO.getClassesByTeacherId(teacherId)`
- Forward tới `grades/list.jsp`

**Trạng thái**: ✅ Hoàn chỉnh

### `controller/GradeEntryServlet.java`
**URL**: `/teacher/grades/entry?classId=X&subjectId=Y`
**Chức năng**: Hiển thị bảng nhập điểm hàng loạt
- **GET**: Lấy danh sách sinh viên trong lớp → forward `entry.jsp`
- **POST**: Lưu điểm batch → redirect với thông báo success/error

**Trạng thái**: ✅ Hoàn chỉnh

### `controller/ReportServlet.java`
**URL**: `/teacher/reports`
**Chức năng**: Xuất báo cáo GPA và xếp loại
- Lấy `getStudentGPAList()` từ ReportDAO
- Tính xếp loại bằng `GradingUtil.getRank()`
- Đếm hạng (Giỏi, Khá, TB, Yếu)
- Forward tới `reports/index.jsp`

**Trạng thái**: ✅ Hoàn chỉnh

---

## 4️⃣ TẦNG GIAO DIỆN (JSP Views)

### `views/teacher/classes/my-classes.jsp`
**Hiển thị**: Danh sách lớp được phân công cho giáo viên
- Card layout với sĩ số
- Nút "Nhập / Sửa Điểm" → link tới `/teacher/grades/entry`

**Trạng thái**: ✅ Hoàn chỉnh

### `views/teacher/grades/list.jsp`
**Hiển thị**: Danh sách lớp để chọn nhập điểm
- Tương tự my-classes.jsp nhưng với text "Quản lý Điểm số"

**Trạng thái**: ✅ Hoàn chỉnh

### `views/teacher/grades/entry.jsp`
**Hiển thị**: Bảng nhập điểm hàng loạt
- Table với 4 cột: STT, Mã SV, Họ tên, Điểm (input number)
- Form POST về `/teacher/grades/entry`
- Success/Error alert messages

**Trạng thái**: ✅ Hoàn chỉnh

### `views/teacher/reports/index.jsp` 🆕 Enhanced
**Hiển thị**: Báo cáo tổng hợp GPA
- **Stats Cards**: Giỏi, Khá, TB, Yếu (với gradient colors + icons)
- **Ranking Table**: Bảng xếp hạng theo GPA (TOP 3 với 🥇🥈🥉)
- **Features**:
  - Responsive design
  - Print button
  - Total GPA min/max
  - Badge colors (success, primary, warning, danger)

**Trạng thái**: ✅ Hoàn chỉnh & Enhanced

---

## 🔄 Luồng Công Việc Giáo Viên

```
1. Đăng nhập → HOME
2. Sidebar → click "📝 Quản lý Điểm số"
   ↓
3. GradeServlet → Danh sách lớp (grades/list.jsp)
   ↓
4. Click "Nhập / Sửa Điểm" 
   ↓
5. GradeEntryServlet GET → Hiển thị bảng nhập điểm (entry.jsp)
   ↓
6. Nhập điểm sinh viên (input[type=number])
   ↓
7. Click "Lưu Bảng Điểm"
   ↓
8. GradeEntryServlet POST → saveGradesBatch() → Redirect với success msg
   ↓
9. Sidebar → click "📊 Báo cáo Học tập"
   ↓
10. ReportServlet → Report Dashboard (reports/index.jsp)
    ↓
    Xem thống kê: Giỏi, Khá, TB, Yếu + Ranking table
```

---

## 🛠️ Build & Run

### 1. Clean and Build (NetBeans)
```
Shift + F11
```
hoặc
```
Right-click Project → Clean and Build
```

### 2. Restart Tomcat
- Click "Stop" button
- Click "Start" button

### 3. Access URLs
- Danh sách lớp: `http://localhost:8080/Assignment/teacher/my-classes`
- Quản lý điểm: `http://localhost:8080/Assignment/teacher/grades`
- Nhập điểm: `http://localhost:8080/Assignment/teacher/grades/entry?classId=1&subjectId=1`
- Báo cáo: `http://localhost:8080/Assignment/teacher/reports`

---

## ✨ Key Features

✅ **Quản lý Lớp**
- Hiển thị danh sách lớp được phân công
- Sắp xếp, filter theo kỳ học

✅ **Nhập Điểm Hàng Loạt**
- Input number 0-10
- Lưu batch (UPSERT)
- Thông báo success/error

✅ **Báo Cáo GPA**
- Xếp loại Giỏi/Khá/TB/Yếu
- Ranking theo GPA DESC
- Top 3 với medal emoji 🥇🥈🥉
- Print functionality

✅ **UI/UX**
- Gradient colors + animations
- Responsive Bootstrap layout
- Icons từ Bootstrap Icons
- Dark/Light mode support

---

## 📊 Database Schema (Referenced)

### Grades Table
```sql
CREATE TABLE Grades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    class_id INT,
    score DOUBLE,
    created_at TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Students(id),
    FOREIGN KEY (class_id) REFERENCES Classes(id)
);
```

### Query for GPA
```sql
SELECT s.student_code, s.full_name, s.homeroom_class, 
       AVG(g.score) AS gpa
FROM Students s
JOIN Grades g ON s.id = g.student_id
GROUP BY s.student_code, s.full_name, s.homeroom_class
ORDER BY gpa DESC;
```

---

## 🐛 Debug Logs
- [DEBUG] GradeEntryServlet - classId=X, subjectId=Y
- [DEBUG] GradeEntryServlet - studentList size=N
- [DEBUG DAO] Executing SQL with classId=X
- [DEBUG DAO] Found X students for classId=X
- [DEBUG DAO] saveGradesBatch for classId=X with N students
- [DEBUG DAO] saveGradesBatch completed successfully

Check logs in **NetBeans Output** tab.

---

## 📝 Notes
- Tất cả date/time lưu tự động via TIMESTAMP
- GPA tính bằng AVG() tự động bỏ qua NULL
- Làm tròn 2 chữ số thập phân ở Java
- UPSERT dùng MERGE statement
- Role check bảo mật: roleId == 2 (Teacher)

---

**Status**: ✅ COMPLETE & READY TO TEST
