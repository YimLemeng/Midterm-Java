package sms;

import java.util.List;

/**
 * Interface: Manageable
 * ----------------------------------------------------
 * OOP Concept: Interface & Abstraction
 * Defines standard CRUD operations for managing records.
 *
 * @param <T> Entity type to manage
 */
public interface Manageable<T> {
    boolean add(T item);
    boolean delete(String id);
    T findById(String id);
    List<T> getAll();
    void displayAll();
    int size();
}
