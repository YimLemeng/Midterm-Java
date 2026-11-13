package sms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class: StudentManager
 * ----------------------------------------------------
 * OOP Concept: Interface Implementation, Encapsulation & Collections
 * Manages all business operations on Student entities.
 */
public class StudentManager implements Manageable<Student> {

    // Encapsulated data collection
    private final List<Student> students;
    private final String filePath;

    /**
     * Constructor
     */
    public StudentManager(String filePath) {
        this.students = new ArrayList<>();
        this.filePath = filePath;
        initData();
    }

    /**
     * Loads existing data from file, or loads sample records on first run.
     */
    private void initData() {
        File file = new File(filePath);
        if (file.exists()) {
            loadFromFile();
        } else {
            loadSampleData();
            saveToFile();
        }
    }

    // ==========================================
    // CORE CRUD OPERATIONS (Manageable Interface)
    // ==========================================

    /**
     * Adds a new student record.
     * Enforces unique ID data validation.
     */
    @Override
    public boolean add(Student student) {
        if (student == null) {
            return false;
        }
        // Validation: Unique ID check
        if (findById(student.getId()) != null) {
            System.out.printf("  [!] Error: Student ID '%s' already exists.\n", student.getId());
            return false;
        }
        students.add(student);
        saveToFile(); // Auto-persist changes
        return true;
    }

    /**
     * Deletes student by ID.
     */
    @Override
    public boolean delete(String id) {
        Student target = findById(id);
        if (target != null) {
            students.remove(target);
            saveToFile(); // Auto-persist changes
            return true;
        }
        return false;
    }

    /**
     * Searches student by exact ID (case-insensitive).
     */
    @Override
    public Student findById(String id) {
        if (id == null) return null;
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(students); // Defensive copy
    }

    @Override
    public int size() {
        return students.size();
    }

    /**
     * Updates an existing student's grade.
     */
    public boolean updateGrade(String id, double newGrade) {
        Student student = findById(id);
        if (student == null) {
            return false;
        }
        student.setGrade(newGrade);
        saveToFile(); // Auto-persist changes
        return true;
    }

    // ==========================================
    // SEARCH & FILTERING (Optional Enhancements)
    // ==========================================

    /**
     * Searches students by name (partial keyword match).
     */
    public List<Student> findByName(String keyword) {
        List<Student> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return results;
        }
        String lowerKeyword = keyword.trim().toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(lowerKeyword)) {
                results.add(s);
            }
        }
        return results;
    }

    // ==========================================
    // SORTING (Optional Enhancements)
    // ==========================================

    /**
     * Sorts students by grade descending (Highest to Lowest).
     */
    public void sortByGradeDescending() {
        students.sort((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()));
    }

    /**
     * Sorts students by grade ascending (Lowest to Highest).
     */
    public void sortByGradeAscending() {
        students.sort(Comparator.comparingDouble(Student::getGrade));
    }

    /**
     * Sorts students alphabetically by Name.
     */
    public void sortByName() {
        students.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
    }

    /**
     * Sorts students by ID.
     */
    public void sortById() {
        students.sort((s1, s2) -> s1.getId().compareToIgnoreCase(s2.getId()));
    }

    // ==========================================
    // PRESENTATION & STATISTICS
    // ==========================================

    /**
     * Displays all students in a formatted ASCII table.
     */
    @Override
    public void displayAll() {
        displayTable(this.students, "ALL REGISTERED STUDENTS");
    }

    /**
     * Displays a given list of students as a table.
     */
    public void displayTable(List<Student> list, String title) {
        System.out.println("\n" + "=".repeat(62));
        System.out.printf("  %-56s\n", title + " (" + list.size() + " total)");
        System.out.println("=".repeat(62));

        if (list.isEmpty()) {
            System.out.println("  No student records found.");
            System.out.println("=".repeat(62));
            return;
        }

        System.out.printf("| %-10s | %-22s | %-7s | %-6s | %-6s |\n",
                "ID", "STUDENT NAME", "GRADE", "LETTER", "STATUS");
        System.out.println("|" + "-".repeat(12) + "|" + "-".repeat(24) + "|"
                + "-".repeat(9) + "|" + "-".repeat(8) + "|" + "-".repeat(8) + "|");

        for (Student student : list) {
            student.displayInfo();
        }
        System.out.println("=".repeat(62));
    }

    /**
     * Computes and displays class statistics:
     * - Average score
     * - Highest & Lowest grade
     * - Pass/Fail count and Passing percentage
     */
    public void displayClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("\n  [!] Cannot compute statistics: No student records exist.");
            return;
        }

        double total = 0.0;
        Student highest = students.get(0);
        Student lowest = students.get(0);
        int passCount = 0;

        for (Student s : students) {
            double g = s.getGrade();
            total += g;
            if (g > highest.getGrade()) highest = s;
            if (g < lowest.getGrade()) lowest = s;
            if (g >= 50.0) passCount++;
        }

        int count = students.size();
        double average = total / count;
        int failCount = count - passCount;
        double passRate = ((double) passCount / count) * 100.0;

        System.out.println("\n" + "=".repeat(50));
        System.out.println("            CLASS PERFORMANCE STATISTICS          ");
        System.out.println("=".repeat(50));
        System.out.printf("  Total Students      : %d\n", count);
        System.out.printf("  Class Average Grade : %.2f\n", average);
        System.out.printf("  Highest Grade       : %.2f (%s - %s)\n",
                highest.getGrade(), highest.getName(), highest.getId());
        System.out.printf("  Lowest Grade        : %.2f (%s - %s)\n",
                lowest.getGrade(), lowest.getName(), lowest.getId());
        System.out.println("-".repeat(50));
        System.out.printf("  Passed (>= 50)      : %d students (%.1f%%)\n", passCount, passRate);
        System.out.printf("  Failed (< 50)       : %d students (%.1f%%)\n", failCount, 100.0 - passRate);
        System.out.println("=".repeat(50));
    }

    // ==========================================
    // FILE HANDLING (Save and Load records)
    // ==========================================

    /**
     * Saves student records to text/CSV file.
     */
    public boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student s : students) {
                writer.write(s.toCsv());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("  [!] Error saving records to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads student records from text/CSV file.
     */
    public boolean loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromCsv(line);
                if (student != null) {
                    students.add(student);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("  [!] Error loading records from file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prepopulates sample records for initial testing.
     */
    private void loadSampleData() {
        students.add(new Student("STU001", "Sokha Chan", 88.5));
        students.add(new Student("STU002", "Dara Heng", 94.0));
        students.add(new Student("STU003", "Bopha Pich", 45.0));
        students.add(new Student("STU004", "Visal Keo", 76.5));
        students.add(new Student("STU005", "Chenda Srun", 62.0));
    }
}
