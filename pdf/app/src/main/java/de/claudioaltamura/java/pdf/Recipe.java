package de.claudioaltamura.java.pdf;

import java.util.List;

public record Recipe(String title, Img image, List<String> ingredients, List<String> steps) {
}