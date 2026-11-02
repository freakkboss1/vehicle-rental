package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Repository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) { items.add(item); }

    public boolean remove(T item) { return items.remove(item); }

    public List<T> getAll() { return new ArrayList<>(items); }

    public Optional<T> findFirst(Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst();
    }

    public List<T> filter(Predicate<T> predicate) {
        return items.stream().filter(predicate).toList();
    }

    public int size() { return items.size(); }
}