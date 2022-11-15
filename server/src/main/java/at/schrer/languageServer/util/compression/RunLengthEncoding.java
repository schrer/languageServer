package at.schrer.languageServer.util.compression;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Encodes or decodes text with RLE (without any optimizations/modifications).
 * Tokens in the encoded text are separated by one (1) whitespace by default, but the delimiter can be changed. The delimiter is not allowed to be used in inputs for encoding.
 * Encoded tokens have follow the pattern "\w\d+". Numbers in the input text are allowed.
 * A valid encoded piece of text could look like this: A5 C15 555 11
 */
public class RunLengthEncoding {

    private final static String DEFAULT_DELIMITER = " ";
    // Pattern that a token needs to have to be inflated
    private final static String INFLATE_TOKEN_PATTERN = "\\w\\d+";


    private RunLengthEncoding(String delimiter){}

    public static String encode(final String input){
        return encode(input, DEFAULT_DELIMITER);
    }

    public static String encode(final String input, final String delimiter){
        if (input.contains(delimiter)) {
            throw new IllegalArgumentException("Input may not contain the delimiter for encoded values.");
        }

        String result = "";
        int counter = 1;
        Character currentCharacter = null;
        for (Character character : input.toCharArray()) {
            if (character.equals(currentCharacter)) {
                counter++;
            } else {
                result = StringUtils.join(result, currentCharacter, counter, delimiter);
                currentCharacter = character;
                counter = 1;
            }
        }

        return result.substring(0,result.length()-1);
    }

    public static String decode(final String input) throws IllegalArgumentException {
        return decode(input, DEFAULT_DELIMITER);
    }

    public static String decode(final String input, final String delimiter) throws IllegalArgumentException {
        String[] tokens = StringUtils.split(input, delimiter);
        return Arrays.stream(tokens)
                .map(RunLengthEncoding::inflate)
                .collect(Collectors.joining());
    }

    private static String inflate(final String input) throws IllegalArgumentException {
        if(! input.matches(INFLATE_TOKEN_PATTERN)){
            throw new IllegalArgumentException(input + " does not match the pattern for an inlatable token.");
        }

        try {
            char character = input.charAt(0);
            int count = Integer.parseInt(input.substring(1));
            return StringUtils.repeat(character, count);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Could not get character count from " + input);
        }

    }
}
