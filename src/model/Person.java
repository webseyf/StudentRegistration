package model;

/**
 * Base class representing a person in the system.
 * This class demonstrates encapsulation and inheritance.
 */
public class Person {

    // Encapsulated fields
    private String id;
    private String name;

    /**
     * Default constructor
     */
    public Person() {
    }

    /**
     * Parameterized constructor
     * 
     * @param id   unique identifier
     * @param name person's name
     */
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter for Name
    public String getName() {
        return name;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a readable string representation of a Person
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}
