package at.schrer.languageServer.languages.annotations;

import java.lang.annotation.*;

/**
 * Marks classes as evaluators for certain languages.
 * Adding this to any class will register it for being added to the {@link at.schrer.languageServer.languages.LanguageRegister}.
 * To load and use it, the class will also have to implement {@link at.schrer.languageServer.languages.ExpressionEvaluator}
 */
@Documented
@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Evaluator {
    String name();
    String sourceLang();
    String targetLang();
}
