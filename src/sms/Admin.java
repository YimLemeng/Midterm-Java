package sms;

/**
 * Class: Admin
 * ----------------------------------------------------
 * OOP Concept: Inheritance & Polymorphism
 * Represents a school administrator with full privileges (Add, View, Update, Delete, Persistence).
 */
public class Admin extends Person {
    private String office;

    public Admin(String id, String name, String office) {
        super(id, name);
        setOffice(office);
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        if (office == null || office.trim().isEmpty()) {
            this.office = "Main Administration";
        } else {
            this.office = office.trim();
        }
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void displayInfo() {
        System.out.printf("[Admin] ID: %s | Name: %s | Office: %s\n",
                getId(), getName(), office);
    }
}
