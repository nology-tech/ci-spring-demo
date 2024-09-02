package io.nology.blog.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;

public class ValidationErrors {
    private HashMap<String, ArrayList<String>> errors;

    public ValidationErrors() {
        this.errors = new HashMap<>();
    }

    public boolean isEmpty() {
        return this.errors.isEmpty();
    }

    public boolean hasErrors() {
        return !this.isEmpty();
    }

    public Map<String, ArrayList<String>> getErrors() {
        return Collections.unmodifiableMap(this.errors);
    }

    public void addError(String field, String errorMessage) {
        // if(this.errors.containsKey(field)) {
        // this.errors.get(field).add(errorMessage);
        // } else {
        // ArrayList<String> newList = new ArrayList<>();
        // newList.add(errorMessage);
        // errors.put(field, newList);
        // }
        this.errors.computeIfAbsent(field, f -> new ArrayList<>()).add(errorMessage);
    }

    @Override
    public String toString() {
        return this.errors.toString();
    }

}
