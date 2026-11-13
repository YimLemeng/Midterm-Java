package sms;

/**
 * Class: Teacher
 * ----------------------------------------------------
 * OOP Concept: Inheritance & Polymorphism
 * Represents a teacher who has permissions to update grades and view reports.
 */
public class Teacher extends Person {
    private String department;

    public Teacher(String id, String name, String department) {
        super(id, name);
        setDepartment(department);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            this.department = "General Education";
        } else {
            this.department = department.trim();
        }
    }

    @Override
    public String getRole() {
        return "Teacher";
    }

    @Override
    public void displayInfo() {
        System.out.printf("[Teacher] ID: %s | Name: %s | Department: %s\n",
                getId(), getName(), department);
    }
}
