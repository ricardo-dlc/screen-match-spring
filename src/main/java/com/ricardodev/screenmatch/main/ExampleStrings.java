package com.ricardodev.screenmatch.main;

import java.util.Arrays;
import java.util.List;

public class ExampleStrings {
    public void showExample() {
        List<String> names = Arrays.asList("Luis", "Alfredo", "Matías", "Pedro");
        names.stream()
                .sorted()
                .limit(2)
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);
    }
}
