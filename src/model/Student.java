package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Student class represents a student in the system.
 * Now supports assigned courses.
 */
public class Student {

    private String id;
    private String name;
    private String department;

    // New feature: list of courses
    private List<Course> courses;

    // Constructor
    public Student(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.courses = new ArrayList<>(); // initialize empty course list
    }

    // --- Getters and Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCourses() {
        return courses;
    }

    // --- Course Helper Methods ---

    // Add a course to student
    public boolean addCourse(Course course) {
        if (course == null) return false;
        if (!courses.contains(course)) {
            courses.add(course);
            return true;
        }
        return false; // course already assigned
    }

    // Remove a course from student
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    // Get a formatted string of all course names
    public String listCourseNames() {
        if (courses.isEmpty()) return "No courses assigned";
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.getCourseName()).append(" (").append(c.getId()).append(")");
            sb.append("\n");
        }
        return sb.toString();
    }

    // --- ToString ---
    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Department: " + department;
    }
}
