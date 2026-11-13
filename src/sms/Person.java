package sms;

/**
 * Abstract Class: Person
 * ----------------------------------------------------
 * OOP Concept: Abstraction & Encapsulation
 * Serves as the base superclass for all school members (Student, Teacher, Admin).
 * Cannot be directly instantiated.
 */
public abstract class Person {
    // Encapsulation: private attributes accessible only through public methods
    private String id;
    private String name;

    /**
     * Parameterized Constructor
     */
    public Person(String id, String name) {
        setId(id);
        setName(name);
    }

    // Getters and Setters with Validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty.");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    // Abstraction: abstract methods that every subclass must implement
    public abstract String getRole();

    public abstract void displayInfo();

    @Override
    public String toString() {
        return String.format("[%s] ID: %s, Name: %s", getRole(), id, name);
    }
}
