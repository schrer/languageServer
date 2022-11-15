package at.schrer.languageServer.evaluation;

import at.schrer.languageServer.evaluation.util.Evaluator;
import at.schrer.languageServer.evaluation.language.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A register for all available Evaluators.
 * It offers functions to check if evaluation for a language is supported and can create and return instances of {@link ExpressionEvaluator} implementations.
 */
@Service
public class LanguageRegister {
    private final static Logger logger = LoggerFactory.getLogger(LanguageRegister.class);

    private final List<EvaluatorInfo> evaluators;


    public LanguageRegister() {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Evaluator.class));

        final Set<AnnotatedBeanDefinition> beanDefinitions = provider.findCandidateComponents("at.schrer.languageServer.evaluation.language")
                .stream()
                .filter(bd -> bd instanceof AnnotatedBeanDefinition)
                .map(bd -> (AnnotatedBeanDefinition) bd).collect(Collectors.toSet());

        evaluators = new ArrayList<>();

        for (AnnotatedBeanDefinition bd : beanDefinitions) {
            try {
                var beanClass = Class.forName(bd.getBeanClassName());
                if (!ExpressionEvaluator.class.isAssignableFrom(beanClass)) {
                    logger.error("Class " + beanClass.getName() + " has annotation Evaluator but does not implement the interface ExpressionEvaluator. Class will not be loaded.");
                    continue;
                }
                var evaluatorClass = (Class<? extends ExpressionEvaluator>) beanClass;

                MergedAnnotation<Evaluator> annotation = bd.getMetadata().getAnnotations().get(Evaluator.class);
                var name = annotation.getString("name");
                var sourceLang = annotation.getString("sourceLang");
                var targetLang = annotation.getString("targetLang");
                evaluators.add(new EvaluatorInfo(name,sourceLang,targetLang, false, evaluatorClass));
                logger.info("Loaded evaluator: " + name + " (" + sourceLang + "->" + targetLang + "), Class name: "
                        + evaluatorClass.getSimpleName());
            } catch (ClassNotFoundException e) {
                var beanClassName = bd.getBeanClassName();
                throw new RuntimeException("Unable to find class definition for previously found class " + beanClassName, e);
            }
        }

        if (evaluators.isEmpty()) {
            throw new RuntimeException("No Evaluator implementations were found. Unable to evaluate any languages.");
        }

    }

    /**
     * True if an implementation of {@link ExpressionEvaluator} is available for the combination of source/target language
     * @param sourceLang the source language
     * @param targetLang the target language
     * @return true if evaluation from source to taraget language is supported, false otherwise
     */
    public boolean isEvaluationSupported(String sourceLang, String targetLang){
        return getEvaluatorInfo(sourceLang, targetLang).isPresent();
    }

    /**
     * Creates and returns an empty instance of the evaluator needed for a source and target language pair.
     *
     * @param sourceLang the source language
     * @param targetLang the target language
     * @return an {@link Optional} containing the {@link ExpressionEvaluator} instance. If no match for source/target lang is found the {@link Optional} will be empty.
     */
    public Optional<ExpressionEvaluator> getEvaluator(String sourceLang, String targetLang){
        var evaluatorClassOpt = getEvaluatorClass(sourceLang, targetLang);
        if (evaluatorClassOpt.isEmpty()) {
            return Optional.empty();
        }

        var evaluatorClass = evaluatorClassOpt.get();
        try {
            Constructor<? extends ExpressionEvaluator> constructor = evaluatorClass.getConstructor();
            return Optional.of(constructor.newInstance());

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "No matching constructor found for Evaluator associated with " + getEvaluatorErrorInfo(sourceLang, targetLang), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(
                    "Error during instantiation of Evaluator associated with " + getEvaluatorErrorInfo(sourceLang, targetLang), e);
        }
    }

    private Optional<EvaluatorInfo> getEvaluatorInfo(String sourceLang, String targetLang){
        return evaluators.stream()
                .filter(ev -> ev.sourceLang.equalsIgnoreCase(sourceLang)
                        && ev.targetLang.equalsIgnoreCase(targetLang)).findFirst();
    }

    private Optional<Class<? extends ExpressionEvaluator>> getEvaluatorClass(String sourceLang, String targetLang){
        var evaluatorInfoOpt = getEvaluatorInfo(sourceLang, targetLang);
        return evaluatorInfoOpt.map(EvaluatorInfo::evaluatorClass);
    }

    /**
     * Finds either the name of the evaluator associated with the source/target lang combination or concatenates the names of source/target langs
     * Only for display in error messages, not for matching Evaluators.
     *
     * @param sourceLang the source language
     * @param targetLang the target language
     * @return a text representation/name of the needed Evaluator
     */
    private String getEvaluatorErrorInfo(String sourceLang, String targetLang){
        var evaluatorInfoOpt = getEvaluatorInfo(sourceLang, targetLang);
        return evaluatorInfoOpt.isPresent()
                ? evaluatorInfoOpt.get().name : sourceLang + "->" + targetLang;
    }

    private record EvaluatorInfo(String name, String sourceLang, String targetLang, boolean supportsOptions,
                                Class<? extends ExpressionEvaluator> evaluatorClass) {
    }
}
