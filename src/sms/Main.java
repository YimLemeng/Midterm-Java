package sms;

import java.util.List;
import java.util.Scanner;

/**
 * Main Application Class
 * ----------------------------------------------------
 * Entry point for Student Management System.
 * Implements a clean, menu-driven console interface with
 * Role-Based Access Control (Admin, Teacher, Student).
 * Business logic is encapsulated in StudentManager.
 */
public class Main {

    private static final String DATA_FILE = "students.txt";
    private final StudentManager manager;
    private final Scanner scanner;

    public Main() {
        this.manager = new StudentManager(DATA_FILE);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Minimal main method as required by project specifications.
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /**
     * Starts the application loop.
     */
    public void start() {
        boolean running = true;

        while (running) {
            printHeader("STUDENT MANAGEMENT SYSTEM (OOP JAVA)");
            System.out.println("  Select Login Role:");
            System.out.println("  1. Administrator  (Full Access: Add, Edit, Delete, View, Stats)");
            System.out.println("  2. Teacher        (Academic Access: Edit Grades, View, Stats)");
            System.out.println("  3. Student        (View-Only Access: View Records, Search, Stats)");
            System.out.println("  4. Exit Application");
            System.out.println("-".repeat(62));

            int roleChoice = InputValidator.readInt(scanner, "  Enter choice (1-4): ", 1, 4);

            switch (roleChoice) {
                case 1 -> {
                    // Polymorphism: instantiate Admin subclass of Person
                    Person adminUser = new Admin("ADM001", "System Administrator", "Academic Affairs");
                    handleAdminSession((Admin) adminUser);
                }
                case 2 -> {
                    // Polymorphism: instantiate Teacher subclass of Person
                    Person teacherUser = new Teacher("TCH001", "Professor David", "Computer Science");
                    handleTeacherSession((Teacher) teacherUser);
                }
                case 3 -> {
                    handleStudentSession();
                }
                case 4 -> {
                    running = false;
                    System.out.println("\n  Thank you for using Student Management System. Goodbye!");
                }
            }
        }
        scanner.close();
    }

    // ==========================================
    // ROLE: ADMINISTRATOR (Full Access)
    // ==========================================

    private void handleAdminSession(Admin admin) {
        boolean inSession = true;

        while (inSession) {
            printHeader("ADMINISTRATOR DASHBOARD");
            admin.displayInfo();
            System.out.println("-".repeat(62));
            System.out.println("  1. View All Students");
            System.out.println("  2. Search Student (by ID or Name)");
            System.out.println("  3. Add New Student");
            System.out.println("  4. Update Student Grade");
            System.out.println("  5. Delete Student Record");
            System.out.println("  6. Sort Students");
            System.out.println("  7. View Class Performance Statistics");
            System.out.println("  8. Reload Data from File");
            System.out.println("  9. Switch User / Logout");
            System.out.println("  0. Exit Application");
            System.out.println("-".repeat(62));

            int choice = InputValidator.readInt(scanner, "  Select an option (0-9): ", 0, 9);

            switch (choice) {
                case 1 -> manager.displayAll();
                case 2 -> performSearch();
                case 3 -> performAddStudent();
                case 4 -> performUpdateGrade();
                case 5 -> performDeleteStudent();
                case 6 -> performSortStudents();
                case 7 -> manager.displayClassStatistics();
                case 8 -> {
                    if (manager.loadFromFile()) {
                        System.out.println("  [+] Records successfully reloaded from " + DATA_FILE);
                    } else {
                        System.out.println("  [!] Failed to reload records.");
                    }
                }
                case 9 -> inSession = false;
                case 0 -> {
                    System.out.println("\n  Goodbye! Exiting application...");
                    System.exit(0);
                }
            }
            if (inSession) {
                InputValidator.pause(scanner);
            }
        }
    }

    // ==========================================
    // ROLE: TEACHER (Grade Editing + Reports)
    // ==========================================

    private void handleTeacherSession(Teacher teacher) {
        boolean inSession = true;

        while (inSession) {
            printHeader("TEACHER DASHBOARD");
            teacher.displayInfo();
            System.out.println("-".repeat(62));
            System.out.println("  1. View All Students");
            System.out.println("  2. Search Student (by ID or Name)");
            System.out.println("  3. Update Student Grade");
            System.out.println("  4. Sort Students");
            System.out.println("  5. View Class Performance Statistics");
            System.out.println("  6. Switch User / Logout");
            System.out.println("  0. Exit Application");
            System.out.println("-".repeat(62));

            int choice = InputValidator.readInt(scanner, "  Select an option (0-6): ", 0, 6);

            switch (choice) {
                case 1 -> manager.displayAll();
                case 2 -> performSearch();
                case 3 -> performUpdateGrade();
                case 4 -> performSortStudents();
                case 5 -> manager.displayClassStatistics();
                case 6 -> inSession = false;
                case 0 -> {
                    System.out.println("\n  Goodbye! Exiting application...");
                    System.exit(0);
                }
            }
            if (inSession) {
                InputValidator.pause(scanner);
            }
        }
    }

    // ==========================================
    // ROLE: STUDENT (View Only)
    // ==========================================

    private void handleStudentSession() {
        boolean inSession = true;

        while (inSession) {
            printHeader("STUDENT PORTAL (VIEW ONLY)");
            System.out.println("  Role: Student (Read-Only Privileges)");
            System.out.println("-".repeat(62));
            System.out.println("  1. View All Students");
            System.out.println("  2. Search Record by Student ID");
            System.out.println("  3. View Class Performance Statistics");
            System.out.println("  4. Switch User / Logout");
            System.out.println("  0. Exit Application");
            System.out.println("-".repeat(62));

            int choice = InputValidator.readInt(scanner, "  Select an option (0-4): ", 0, 4);

            switch (choice) {
                case 1 -> manager.displayAll();
                case 2 -> {
                    String id = InputValidator.readString(scanner, "  Enter Student ID to find: ");
                    Student student = manager.findById(id);
                    if (student != null) {
                        manager.displayTable(List.of(student), "SEARCH RESULT");
                    } else {
                        System.out.printf("  [!] Student with ID '%s' was not found.\n", id);
                    }
                }
                case 3 -> manager.displayClassStatistics();
                case 4 -> inSession = false;
                case 0 -> {
                    System.out.println("\n  Goodbye! Exiting application...");
                    System.exit(0);
                }
            }
            if (inSession) {
                InputValidator.pause(scanner);
            }
        }
    }

    // ==========================================
    // ACTION HANDLERS (Encapsulating UI Prompts)
    // ==========================================

    /**
     * Add a new student record
     */
    private void performAddStudent() {
        printSubHeader("ADD NEW STUDENT");

        String id = InputValidator.readString(scanner, "  Enter Student ID (e.g. STU101): ");

        // Check if ID already exists early to save time
        if (manager.findById(id) != null) {
            System.out.printf("  [!] Error: Student ID '%s' already exists in the system!\n", id);
            return;
        }

        String name = InputValidator.readString(scanner, "  Enter Student Name: ");
        double grade = InputValidator.readDouble(scanner, "  Enter Student Grade (0.0 - 100.0): ", 0.0, 100.0);

        Student student = new Student(id, name, grade);
        if (manager.add(student)) {
            System.out.println("\n  [+] SUCCESS: Student added successfully!");
            manager.displayTable(List.of(student), "NEWLY ADDED RECORD");
        } else {
            System.out.println("  [!] Failed to add student.");
        }
    }

    /**
     * Update an existing student's grade
     */
    private void performUpdateGrade() {
        printSubHeader("UPDATE STUDENT GRADE");

        String id = InputValidator.readString(scanner, "  Enter Student ID to update: ");
        Student existing = manager.findById(id);

        if (existing == null) {
            System.out.printf("  [!] Error: Student with ID '%s' does not exist.\n", id);
            return;
        }

        System.out.printf("  Current Record: %s (Grade: %.2f [%s])\n",
                existing.getName(), existing.getGrade(), existing.getLetterGrade());

        double newGrade = InputValidator.readDouble(scanner, "  Enter New Grade (0.0 - 100.0): ", 0.0, 100.0);

        if (manager.updateGrade(id, newGrade)) {
            System.out.println("\n  [+] SUCCESS: Grade updated successfully!");
            manager.displayTable(List.of(existing), "UPDATED RECORD");
        } else {
            System.out.println("  [!] Failed to update grade.");
        }
    }

    /**
     * Delete student record
     */
    private void performDeleteStudent() {
        printSubHeader("DELETE STUDENT RECORD");

        String id = InputValidator.readString(scanner, "  Enter Student ID to delete: ");
        Student existing = manager.findById(id);

        if (existing == null) {
            System.out.printf("  [!] Error: Student with ID '%s' not found.\n", id);
            return;
        }

        manager.displayTable(List.of(existing), "TARGET STUDENT TO DELETE");

        boolean confirm = InputValidator.readConfirmation(scanner,
                "  Are you sure you want to permanently delete this student?");
        if (confirm) {
            if (manager.delete(id)) {
                System.out.println("  [+] SUCCESS: Student record permanently deleted.");
            } else {
                System.out.println("  [!] Failed to delete record.");
            }
        } else {
            System.out.println("  [-] Action cancelled by user.");
        }
    }

    /**
     * Search student by ID or Name
     */
    private void performSearch() {
        printSubHeader("SEARCH STUDENT");
        System.out.println("  1. Search by Student ID");
        System.out.println("  2. Search by Name (Keyword)");
        int choice = InputValidator.readInt(scanner, "  Select search method (1-2): ", 1, 2);

        if (choice == 1) {
            String id = InputValidator.readString(scanner, "  Enter Student ID: ");
            Student s = manager.findById(id);
            if (s != null) {
                manager.displayTable(List.of(s), "SEARCH RESULT");
            } else {
                System.out.printf("  [!] No student found with ID '%s'.\n", id);
            }
        } else {
            String keyword = InputValidator.readString(scanner, "  Enter name or keyword: ");
            List<Student> results = manager.findByName(keyword);
            if (!results.isEmpty()) {
                manager.displayTable(results, "SEARCH RESULTS FOR: \"" + keyword + "\"");
            } else {
                System.out.printf("  [!] No students found matching \"%s\".\n", keyword);
            }
        }
    }

    /**
     * Sort students
     */
    private void performSortStudents() {
        printSubHeader("SORT STUDENTS");
        System.out.println("  1. Sort by Grade (Highest to Lowest)");
        System.out.println("  2. Sort by Grade (Lowest to Highest)");
        System.out.println("  3. Sort by Name (A-Z)");
        System.out.println("  4. Sort by Student ID");
        int sortChoice = InputValidator.readInt(scanner, "  Select sorting option (1-4): ", 1, 4);

        switch (sortChoice) {
            case 1 -> manager.sortByGradeDescending();
            case 2 -> manager.sortByGradeAscending();
            case 3 -> manager.sortByName();
            case 4 -> manager.sortById();
        }

        System.out.println("  [+] Records sorted successfully.");
        manager.displayAll();
    }

    // ==========================================
    // UI HELPERS
    // ==========================================

    private void printHeader(String title) {
        System.out.println("\n" + "=".repeat(62));
        System.out.printf("  %s\n", title);
        System.out.println("=".repeat(62));
    }

    private void printSubHeader(String title) {
        System.out.println("\n" + "-".repeat(40));
        System.out.printf("  %s\n", title);
        System.out.println("-".repeat(40));
    }
}
