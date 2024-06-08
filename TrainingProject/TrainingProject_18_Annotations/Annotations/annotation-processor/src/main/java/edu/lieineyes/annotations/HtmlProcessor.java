package edu.lieineyes.annotations;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;


@SupportedAnnotationTypes({"edu.lieineyes.annotations.HtmlForm", "edu.lieineyes.classes.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    private static final String ACTION_LINE_HTML_FORM = "<form action = \"";
    private static final String METHOD_LINE_HTML_FORM = "method = \"";

    private static final String INPUT_TYPE_LINE_HTML = "\t\t<input type = \"";
    private static final String NAME_LINE_HTML = "name = \"";
    private static final String PLACEHOLDER_LINE_HTML = "placeholder = \"";
    private static final String LAST_LINE_HTML = "\t\t<input type = \"submit\" value = \"Send\">\n</form>";
    private static final String TARGET_PATH = "target/classes/";

    private static final String QUOTATION_MARK_AND_SPACE = "\" ";
    private static final String QUOTATION_MARK_AND_END_LINE_WITH_NEW_LINE = "\">\n";


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element annotatedHtmlForm : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            if (annotatedHtmlForm.getKind() == ElementKind.CLASS) {
                HtmlForm htmlForm = annotatedHtmlForm.getAnnotation(HtmlForm.class);
                StringBuilder outputText = new StringBuilder();
                outputText.append(ACTION_LINE_HTML_FORM)
                        .append(htmlForm.action())
                        .append(QUOTATION_MARK_AND_SPACE)
                        .append(METHOD_LINE_HTML_FORM)
                        .append(htmlForm.method())
                        .append(QUOTATION_MARK_AND_END_LINE_WITH_NEW_LINE);

                for (Element annotatedHtmlInput : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {
                    if (annotatedHtmlInput.getKind() == ElementKind.FIELD
                            && annotatedHtmlInput.getEnclosingElement().getSimpleName().equals(annotatedHtmlForm.getSimpleName())) {
                        HtmlInput htmlInput = annotatedHtmlInput.getAnnotation(HtmlInput.class);
                        outputText.append(INPUT_TYPE_LINE_HTML)
                                .append(htmlInput.type())
                                .append(QUOTATION_MARK_AND_SPACE)
                                .append(NAME_LINE_HTML)
                                .append(htmlInput.name())
                                .append(QUOTATION_MARK_AND_SPACE)
                                .append(PLACEHOLDER_LINE_HTML)
                                .append(htmlInput.placeholder())
                                .append(QUOTATION_MARK_AND_END_LINE_WITH_NEW_LINE);
                    }
                }
                outputText.append(LAST_LINE_HTML);
                try {
                    FileWriter outHTMLFile = new FileWriter(TARGET_PATH + annotatedHtmlForm.getAnnotation(HtmlForm.class).fileName());
                    outHTMLFile.write(String.valueOf(outputText));
                    outputText.append(LAST_LINE_HTML);
                    outHTMLFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return true;
    }
}
