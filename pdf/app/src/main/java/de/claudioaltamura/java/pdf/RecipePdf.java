package de.claudioaltamura.java.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class RecipePdf {

    public static void main(String[] args) throws IOException, DocumentException {
        var recipePdf = new RecipePdf();
        var recipe = RecipeFactory.getScrambledEggsRecipe();
        recipePdf.printRecipe(recipe);
    }

    String printRecipe(Recipe recipe) throws IOException, DocumentException {
        var document = new Document();
        var filePath = createFilePath(recipe);
        var writer = PdfWriter.getInstance(
                document,
                new FileOutputStream(
                        filePath
                )
        );
        document.open();
        addMetadata(document,recipe);
        addTitle(document, recipe);
        addImage(document, recipe);
        addIngredients(document, recipe);
        addSteps(document, recipe);
        document.close();
        writer.close();
        return filePath;
    }

    private static String createFilePath(Recipe recipe) {
        var tmpdir = System.getProperty("java.io.tmpdir");
        String fileName = recipe.title().replaceAll("\\s", "");
        return tmpdir + "/" + fileName + ".pdf";
    }

    private static void addMetadata(Document document, Recipe recipe) {
        document.addTitle(recipe.title());
        document.addSubject("Recipe");
    }

    private static void addTitle(Document document, Recipe recipe) throws DocumentException {
        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD);
        var title = new Paragraph(recipe.title(), font);
        document.add(title);
    }

    private static void addImage(Document document, Recipe recipe) throws DocumentException, IOException {
        var image = Image.getInstance(recipe.image().url());
        image.scaleAbsolute(300f, 200f);
        document.add(image);
    }

    private static void addIngredients(Document document, Recipe recipe) throws DocumentException {
        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, Font.DEFAULTSIZE, Font.BOLD);
        var ingredientsHeader = new Paragraph("Ingredients", font);
        ingredientsHeader.setSpacingBefore(10f);
        document.add(ingredientsHeader);

        var ingredients = new List(List.ORDERED);
        recipe.ingredients().forEach(ingredient ->
            ingredients.add(new ListItem(ingredient))
        );
        document.add(ingredients);
    }

    private static void addSteps(Document document, Recipe recipe) throws DocumentException {
        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, Font.DEFAULTSIZE, Font.BOLD);
        var stepsHeader = new Paragraph("Steps", font);
        stepsHeader.setSpacingBefore(10f);
        document.add(stepsHeader);

        var table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = {1f};
        table.setWidths(columnWidths);

        for (int i = 0; i < recipe.steps().size(); i++) {
            var name = "Step " + (i + 1);
            var step = recipe.steps().get(i);
            var paragraph = new Paragraph(name + "\n" + step);
            PdfPCell cell = new PdfPCell(paragraph);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setPaddingLeft(10);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        document.add(table);
    }

}