package manager;

import model.Student;
import model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StudentManager now supports filtering by courses
 */
public class StudentManager {

    private List<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    // --- Existing Methods ---

    // Add student (returns false if duplicate ID)
    public boolean addStudent(Student student) {
        if (searchStudentById(student.getId()) != null) {
            return false; // duplicate ID
        }
        students.add(student);
        return true;
    }

    // Search student by ID
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

    // --- New Feature: Filter Students by Course ---
    /**
     * Returns a list of students assigned to a specific course
     *
     * @param course the course to filter by
     * @return list of students enrolled in the course
     */
    public List<Student> getStudentsByCourse(Course course) {
        if (course == null) return new ArrayList<>();
        return students.stream()
                .filter(s -> s.getCourses().contains(course))
                .collect(Collectors.toList());
    }
}
