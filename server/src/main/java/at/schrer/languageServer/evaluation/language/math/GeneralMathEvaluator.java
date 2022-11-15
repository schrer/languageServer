package at.schrer.languageServer.evaluation.language.math;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;

import java.math.BigDecimal;

class GeneralMathEvaluator {

    public BigDecimal evaluateMathProgram(String program) throws IllegalArgumentException {
        Expression expression = new Expression(program);
        try {
            var result = expression.evaluate();
            return result.getNumberValue();
        } catch (EvaluationException e) {
            throw new RuntimeException("Unable to evaluate math expression.", e);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to parse program as math expression.", e);
        }
    }
}
