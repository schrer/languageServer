package at.schrer.languageServer.evaluation.language;

import at.schrer.languageServer.evaluation.util.EvaluationOption;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionEvaluator {
    private final static Logger logger = LoggerFactory.getLogger(ExpressionEvaluator.class);

    private final Map<String, String> activeOptions;
    private final Map<String, EvaluationOption> supportedOptions;

    public ExpressionEvaluator(){
        this.activeOptions = new HashMap<>();
        this.supportedOptions = MapUtils.unmodifiableMap(Map.of());
    }

    public ExpressionEvaluator(final Map<String, EvaluationOption> supportedOptions){
        this.activeOptions = new HashMap<>();
        this.supportedOptions = MapUtils.unmodifiableMap(supportedOptions);
    }
    public Map<String, String> getActiveOptions(){
        return activeOptions;
    }

    public Map<String, EvaluationOption> getSupportedOptions() {
        return supportedOptions;
    };

    /**
     * Set options on an Evaluator to give directions for the evaluation process.
     *
     * @param options a Map containing option names (keys) and option values (values)
     * @return true if all given options were set successfully, false otherwise. If an evaluator supports no options and an empty or null map is given, it will return true as well, false if the map contains values.
     */
    public boolean setOptions(Map<String, String> options){
        if (MapUtils.isEmpty(getSupportedOptions())){
            return MapUtils.isEmpty(options);
        }

        getActiveOptions().clear();
        boolean allOptionsSupported = true;

        for (Map.Entry<String, String> entry : options.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            if (!getSupportedOptions().containsKey(key)) {
                logger.debug("Option " + key + " not supported.");
                allOptionsSupported = false;
                continue;
            }

            if (!getSupportedOptions().get(key).validateValue(value)) {
                logger.debug("Value " + value + " for option " + key + " not supported.");
                allOptionsSupported = false;
                continue;
            }

            activeOptions.put(key, value);
        }

        return allOptionsSupported;
    }

    /**
     * Returns a map with information about supported options.
     * Keys are the option names, values are descriptions and allowed values.
     * @return a map of supported options for this Evaluator implementation.
     */
    public Map<String, String> supportedOptions(){
        if (MapUtils.isEmpty(getSupportedOptions())){
            return Collections.emptyMap();
        }

        Map<String, String> supportedOptionsInfo = new HashMap<>();
        for (Map.Entry<String, EvaluationOption> entry: getSupportedOptions().entrySet()) {
            supportedOptionsInfo.put(entry.getKey(), entry.getValue().getAutoDescription());
        }
        return supportedOptionsInfo;
    }

    /**
     * Returns the active value of on option, or the default if none has been set.
     *
     * @param key the option key
     * @return the active value or default value if no value was set. null if the option is not supported.
     */
    public String getOptionValueOrDefault(final String key){
        String value = getActiveOptions().get(key);

        if (value != null || !getSupportedOptions().containsKey(key)) {
            // Return value if found, or null if option not supported
            return value;
        }

        return getSupportedOptions().get(key).getDefaultValue();
    }

    /**
     * Evaluates a program with the previously given options (if any).
     *
     * @param program the program in String form
     * @return the result in String form
     * @throws IllegalArgumentException if the program is not valid in the specified source language
     */
    public abstract String evaluate(String program) throws IllegalArgumentException;
}
