package at.schrer.languageServer.languages.math;

import at.schrer.languageServer.languages.ExpressionEvaluator;
import at.schrer.languageServer.languages.annotations.Evaluator;

import java.math.BigDecimal;

@Evaluator(name="mathToRealNumber", sourceLang = "math", targetLang = "realNumber")
public class MathToRealNumber implements ExpressionEvaluator {

    @Override
    public String evaluate(String program) {
        GeneralMathEvaluator mathEvaluator = new GeneralMathEvaluator();
        BigDecimal result = mathEvaluator.evaluateMathProgram(program);
        return result.toPlainString();
    }
}
