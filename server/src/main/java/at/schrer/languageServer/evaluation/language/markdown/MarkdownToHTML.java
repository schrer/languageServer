package at.schrer.languageServer.evaluation.language.markdown;

import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;
import at.schrer.languageServer.evaluation.util.Evaluator;

@Evaluator(name="markdownToHTML", sourceLang = "markdown", targetLang = "HTML")
public class MarkdownToHTML extends ExpressionEvaluator {

    @Override
    public String evaluate(String program) {
        return null;
    }
}
