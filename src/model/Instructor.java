package model;

/**
 * Instructor class represents a course instructor.
 * Simple OOP class with encapsulation.
 */
public class Instructor {

    private String id;
    private String name;
    private String department;

    // Constructor
    public Instructor(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getters and Setters
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

    // ToString method for display
    @Override
    public String toString() {
        return "Instructor ID: " + id + ", Name: " + name + ", Department: " + department;
    }
}
