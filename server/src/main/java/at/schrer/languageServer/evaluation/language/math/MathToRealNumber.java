package at.schrer.languageServer.evaluation.language.math;

import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;
import at.schrer.languageServer.evaluation.util.Evaluator;

import java.math.BigDecimal;

@Evaluator(name="mathToRealNumber", sourceLang = "math", targetLang = "realNumber")
public class MathToRealNumber extends ExpressionEvaluator {

    @Override
    public String evaluate(String program) {
        GeneralMathEvaluator mathEvaluator = new GeneralMathEvaluator();
        BigDecimal result = mathEvaluator.evaluateMathProgram(program);
        return result.toPlainString();
    }
}
