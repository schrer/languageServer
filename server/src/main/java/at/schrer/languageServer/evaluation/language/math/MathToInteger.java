package at.schrer.languageServer.evaluation.language.math;

import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;
import at.schrer.languageServer.evaluation.util.Evaluator;

import java.math.BigDecimal;

@Evaluator(name="mathToInteger", sourceLang = "math", targetLang = "integer")
public class MathToInteger extends ExpressionEvaluator {

    @Override
    public String evaluate(String program) {
        GeneralMathEvaluator mathEvaluator = new GeneralMathEvaluator();
        BigDecimal result = mathEvaluator.evaluateMathProgram(program);

        // Round to precision 0 with HALF_UP algorithm
        // MathContext roundContext = new MathContext(1, RoundingMode.HALF_UP);
        // BigDecimal resultRoundedToInteger = result.divide(BigDecimal.TEN).round(roundContext).multiply(BigDecimal.TEN);
        // TODO use rounding instead of truncating
        var resultCutToInteger = result.toBigInteger();
        return resultCutToInteger.toString();
    }
}
