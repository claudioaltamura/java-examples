package de.claudioaltamura.java.pdf;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.util.ArrayList;
import java.util.List;

public class RecipeTextRenderListener implements RenderListener {

    private final List<String> texts = new ArrayList<>();

    public List<String> getTexts() {
        return texts;
    }

    @Override
    public void beginTextBlock() {

    }

    @Override
    public void renderText(TextRenderInfo textRenderInfo) {
        texts.add(textRenderInfo.getText());
    }

    @Override
    public void endTextBlock() {

    }

    @Override
    public void renderImage(ImageRenderInfo imageRenderInfo) {

    }
}
