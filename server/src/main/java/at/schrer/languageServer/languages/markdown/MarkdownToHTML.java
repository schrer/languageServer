package at.schrer.languageServer.languages.markdown;

import at.schrer.languageServer.languages.ExpressionEvaluator;
import at.schrer.languageServer.languages.annotations.Evaluator;

@Evaluator(name="markdownToHTML", sourceLang = "markdown", targetLang = "HTML")
public class MarkdownToHTML implements ExpressionEvaluator {

    @Override
    public String evaluate(String program) {
        return null;
    }
}
