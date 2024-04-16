package de.claudioaltamura.java.pdf;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;

public class RecipeFactory {

    private RecipeFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Recipe getScrambledEggsRecipe() throws MalformedURLException {
        var img = new Img("eggs",
                URI.create("https://unsplash.com/photos/4gmBIFraSuE/download?ixid=M3wxMjA3fDB8MXxzZWFyY2h8Mnx8c2NyYW1ibGVkJTIwZWdnc3xlbnwwfHx8fDE3MTMyNTM5NDB8MA&force=true&w=640").toURL(),
                "Photo by amirali mirhashemian on Unsplash");
        var ingredients = Arrays.asList(
                "2 eggs",
                "6 tbsp milk",
                "butter",
                "salt"
        );
        var steps = Arrays.asList(
                "Whisk eggs, milk and a pinch of salt together until the mixture has just one consistency.",
                "Heat a frying pan for a minute or so, then add a butter and let it melt.",
                "Pour in the egg mixture and let it sit, without stirring. Then stir with a wooden spoon, lifting and folding it over from the bottom of the pan.",
                "Let it sit then stir and fold again.",
                "Repeat until the eggs are softly set.",
                "Give a final stir and serve without delay."
        );

        return new Recipe("Scrambled eggs", img, ingredients, steps);
    }
}
