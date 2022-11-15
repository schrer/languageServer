package at.schrer.languageServer.evaluation.language.text;

import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;
import at.schrer.languageServer.evaluation.util.EvaluationOption;
import at.schrer.languageServer.evaluation.util.Evaluator;
import at.schrer.languageServer.util.compression.RunLengthEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Evaluator(name = "textToText", sourceLang = "text", targetLang = "text")
public class TextToTextEvaluator extends ExpressionEvaluator {
    private final static Logger logger = LoggerFactory.getLogger(TextToTextEvaluator.class);


    private static final String ALLOWED_VALUES_ALGORITHM_IDENTITY = "identity";
    private static final String ALLOWED_VALUES_ALGORITHM_LZW = "lzw";
    private static final String[] ALLOWED_VALUES_ALGORITHM = {ALLOWED_VALUES_ALGORITHM_IDENTITY, ALLOWED_VALUES_ALGORITHM_LZW};


    private static final String OPTION_ALGORITHM = "algorithm";


    private static final Map<String, EvaluationOption> SUPPORTED_OPTIONS = Map.of(
            OPTION_ALGORITHM, new EvaluationOption(OPTION_ALGORITHM, EvaluationOption.OptionType.STRING, ALLOWED_VALUES_ALGORITHM_IDENTITY, ALLOWED_VALUES_ALGORITHM)
    );

    private TextToTextEvaluator(){
        super(SUPPORTED_OPTIONS);
    }

    @Override
    public String evaluate(String program) throws IllegalArgumentException {
        String algorithm = getOptionValueOrDefault(OPTION_ALGORITHM);

        if (ALLOWED_VALUES_ALGORITHM_LZW.equals(algorithm)) {
            return RunLengthEncoding.encode(program);
        }

        // Identity algorithm as default
        return program;
    }
}
