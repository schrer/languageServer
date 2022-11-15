package at.schrer.languageServer.evaluation.util;

import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;

import java.lang.annotation.*;

/**
 * Marks classes as evaluators for certain languages.
 * Adding this to any class will register it for being added to the {@link at.schrer.languageServer.evaluation.LanguageRegister}.
 * To load and use it, the class will also have to implement {@link ExpressionEvaluator}
 */
@Documented
@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Evaluator {
    String name();
    String sourceLang();
    String targetLang();
}
