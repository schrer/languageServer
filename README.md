# LanguageServer

Spring application offering an API to execute code/expressions in different languages.

This is a playground to try new things, so will probably never be fully finished, only idle.

## Things that I tried so far

Things that I tried, sometimes for the first time.

| What                          | Where                                                                                                | Description                                                                                                                                                                                                                                                                                                               |
|-------------------------------|------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Loading classes by Annotation | [LanguageRegister](./server/src/main/java/at/schrer/languageServer/evaluation/LanguageRegister.java) | Classes marked with the Annotation [Evaluator](./server/src/main/java/at/schrer/languageServer/evaluation/util/Evaluator.java) are automatically found in the evaluator package during startup. Registering a new Evaluator-class is as easy as adding the Annotation on a class extending the ExpressionEvaluator-class. |
| Gradle subprojects            | [settings.gradle](./settings.gradle)                                                                 | This project uses Gradle subprojects, server and potInterpreter.                                                                                                                                                                                                                                                          |
| Git submodules                | [.gitmodules](./.gitmodules)                                                                         | A git submodule is used here as well. The potInterpreter is placed in its own [repository](https://github.com/schrer/potInterpreter).        |