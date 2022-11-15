package at.schrer.languageServer.evaluation.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class EvaluationOption {

    public enum OptionType{
        BOOLEAN, STRING, INTEGER, REAL_NUMBER
    }

    public static final String UNRESTRICTED_CONTENT_PATTERN = ".*";

    private final String name;
    private final OptionType contentType;
    private final String contentPattern;
    private final String[] allowedValues;
    private final String defaultValue;



    public EvaluationOption(String name, OptionType contentType, String defaultValue, String contentPattern) {
        this.name = name;
        this.contentType = contentType;
        this.defaultValue = defaultValue;
        this.contentPattern = contentPattern;
        this.allowedValues = null;
    }

    public EvaluationOption(String name, OptionType contentType, String defaultValue, String[] allowedValues) {
        this.name = name;
        this.contentType = contentType;
        this.defaultValue = defaultValue;
        this.contentPattern = UNRESTRICTED_CONTENT_PATTERN;
        this.allowedValues = allowedValues;
    }

    public EvaluationOption(String name, OptionType contentType, String defaultValue) {
        this.name = name;
        this.contentType = contentType;
        this.defaultValue = defaultValue;
        this.contentPattern = UNRESTRICTED_CONTENT_PATTERN;
        this.allowedValues = null;
    }

    public String getName() {
        return name;
    }

    public OptionType getContentType() {
        return contentType;
    }

    public String getContentPattern() {
        return contentPattern;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean validateValue (final String value) {
        return switch (this.contentType) {
            case STRING -> validateString(value);
            case BOOLEAN -> validateBoolean(value);
            case INTEGER -> validateInt(value);
            case REAL_NUMBER -> validateRealNumber(value);
        };
    }

    public String getAutoDescription () {
        // TODO generate descriptions for all types of content
        return "";
    }

    private boolean validateString(final String value){
        if (ArrayUtils.isNotEmpty(allowedValues)) {
            return ArrayUtils.contains(allowedValues, value);
        }

        return StringUtils.isNotBlank(value) && value.matches(contentPattern);
    }

    private boolean validateBoolean(final String value){
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    private boolean validateInt(final String value){
        try{
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean validateRealNumber(final String value){
        try{
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
