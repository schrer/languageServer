package at.schrer.languageServer.frontend.karate;

import com.intuit.karate.junit5.Karate;

public class TextRunner {

    @Karate.Test
    Karate testLZW(){
        return Karate.run("classpath:at/schrer/languageServer/frontend/karate/text/text-to-text-lzw.feature");
    }

    @Karate.Test
    Karate testIdentity(){
        return Karate.run("classpath:at/schrer/languageServer/frontend/karate/text/text-to-text-identity.feature");
    }
}
