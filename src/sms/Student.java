package sms;

/**
 * Class: Student
 * ----------------------------------------------------
 * OOP Concept: Inheritance, Polymorphism & Encapsulation
 * Represents a student enrolled in the school.
 */
public class Student extends Person {
    // Encapsulated attribute
    private double grade;

    /**
     * Parameterized Constructor
     */
    public Student(String id, String name, double grade) {
        super(id, name); // Calls superclass (Person) constructor
        setGrade(grade); // Validates and sets grade
    }

    // Encapsulation: Getter and Setter with data validation (0 <= grade <= 100)
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        this.grade = grade;
    }

    /**
     * Converts numeric score to letter grade
     */
    public String getLetterGrade() {
        if (grade >= 90.0) return "A";
        if (grade >= 80.0) return "B";
        if (grade >= 70.0) return "C";
        if (grade >= 60.0) return "D";
        if (grade >= 50.0) return "E";
        return "F";
    }

    /**
     * Determines passing status (Passing grade >= 50.0)
     */
    public String getStatus() {
        return (grade >= 50.0) ? "PASS" : "FAIL";
    }

    // Polymorphism: concrete implementation of abstract method from Person
    @Override
    public String getRole() {
        return "Student";
    }

    // Polymorphism: concrete implementation of displayInfo
    @Override
    public void displayInfo() {
        System.out.printf("| %-10s | %-22s | %7.2f | %-6s | %-6s |\n",
                getId(), getName(), grade, getLetterGrade(), getStatus());
    }

    /**
     * Serializes student object into CSV format for file persistence
     */
    public String toCsv() {
        return String.format("%s,%s,%.2f", getId(), getName(), grade);
    }

    /**
     * Factory method: parses CSV line into Student object
     */
    public static Student fromCsv(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split(",");
        if (parts.length != 3) {
            return null;
        }
        String id = parts[0].trim();
        String name = parts[1].trim();
        double grade = Double.parseDouble(parts[2].trim());
        return new Student(id, name, grade);
    }

    @Override
    public String toString() {
        return String.format("Student [ID=%s, Name=%s, Grade=%.2f (%s), Status=%s]",
                getId(), getName(), grade, getLetterGrade(), getStatus());
    }
}
