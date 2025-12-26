package manager;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    // Add student with validation for duplicate ID
    public boolean addStudent(Student s) {
        if (searchStudentById(s.getId()) != null) return false;
        students.add(s);
        return true;
    }

    // Search by ID
    public Student searchStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    // Delete student by ID
    public boolean deleteStudentById(String id) {
        Student s = searchStudentById(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Get total student count
    public int getStudentCount() {
        return students.size();
    }

    // Count students by department
    public int countByDepartment(String dept) {
        int count = 0;
        for (Student s : students) {
            if (s.getDepartment().equals(dept)) count++;
        }
        return count;
    }

    // Count students by batch
    public int countByBatch(String batch) {
        int count = 0;
        for (Student s : students) {
            if (s.getBatch() != null && s.getBatch().equals(batch)) count++;
        }
        return count;
    }

    // Enrollment status: "Enrolled" if courses assigned, otherwise "Not Enrolled"
    public String getEnrollmentStatus(Student s) {
        return s.getCourses().isEmpty() ? "Not Enrolled" : "Enrolled";
    }

    // Clear all students
    public void clearAllStudents() {
        students.clear();
    }
}
