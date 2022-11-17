package at.schrer.languageServer.frontend.karate;

import com.intuit.karate.junit5.Karate;

public class BaseTestRunner {

    @Karate.Test
    Karate missingFields(){
        return Karate.run("classpath:at/schrer/languageServer/frontend/karate/base/missing-fields.feature");
    }
}
