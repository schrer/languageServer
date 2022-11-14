package at.schrer.languageServer.languages;

import java.util.Collections;
import java.util.Map;

public interface ExpressionEvaluator {
    /**
     * Set options on an Evaluator to give directions for the evaluation process.
     *
     * @param options a Map containing option names (keys) and option values (values)
     * @return true if all given options were set successfully, false otherwise. If an evaluator supports no options and an empty or null map is given, it will return true as well, false if the map contains values.
     */
    default boolean setOptions(Map<String, String> options){
        return options == null || options.isEmpty();
    }

    /**
     * Returns a map with information about supported options.
     * Keys are the option names, values are descriptions and allowed values.
     * @return a map of supported options for this Evaluator implementation.
     */
    default Map<String, String> supportedOptions(){
        return Collections.emptyMap();
    }

    /**
     * Evaluates a program with the previously given options (if any).
     *
     * @param program the program in String form
     * @return the result in String form
     * @throws IllegalArgumentException if the program is not valid in the specified source language
     */
    String evaluate(String program) throws IllegalArgumentException;
}
