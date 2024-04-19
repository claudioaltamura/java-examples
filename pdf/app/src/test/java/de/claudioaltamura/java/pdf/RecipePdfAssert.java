package de.claudioaltamura.java.pdf;

import org.assertj.core.api.AbstractAssert;

import java.util.List;

class RecipePdfAssert extends AbstractAssert<RecipePdfAssert, RecipeTextRenderListener> {

    RecipePdfAssert(RecipeTextRenderListener recipeTextRenderListener) {
        super(recipeTextRenderListener, RecipePdfAssert.class);
    }

    static RecipePdfAssert assertThat(RecipeTextRenderListener recipeTextRenderListener) {
        return new RecipePdfAssert(recipeTextRenderListener);
    }

    RecipePdfAssert hasTitle(String title) {
        isNotNull();
        if (!actual.getTexts().contains(title)) {
            failWithMessage("Expected title '%s' not found",
                    title);
        }
        return this;
    }

    RecipePdfAssert containsIngredients(List<String> ingredients) {
        isNotNull();
        var ingredientsIndex = actual.getTexts().indexOf("Ingredients");
        var stepsIndex = actual.getTexts().indexOf("Steps");
        var ingredientsInPdf = actual.getTexts().subList(ingredientsIndex + 1, stepsIndex - 1);
        if (!ingredientsInPdf.containsAll(ingredients)) {
            failWithMessage("Expected ingredients '%s' not found but was '%s'",
                    ingredients, ingredientsInPdf);
        }
        return this;
    }

    RecipePdfAssert hasStepSize(int stepSize) {
        isNotNull();
        var steps = actual.getTexts().stream()
                .filter(step -> step.contains("Step "))
                .toList();
        if (steps.size() != stepSize) {
            failWithMessage("Expected step size '%d' but it was '%s'",
                    stepSize, steps.size());
        }
        return this;
    }

}
