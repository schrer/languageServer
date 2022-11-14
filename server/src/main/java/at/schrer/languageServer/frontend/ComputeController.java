package at.schrer.languageServer.frontend;

import at.schrer.languageServer.frontend.request.ComputeRequest;
import at.schrer.languageServer.frontend.response.ComputeResponse;
import at.schrer.languageServer.languages.LanguageRegister;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compute")
public class ComputeController {
    private final static Logger logger = LoggerFactory.getLogger(ComputeController.class);

    private final LanguageRegister languageRegister;

    @Autowired
    public ComputeController(LanguageRegister languageRegister) {
        this.languageRegister = languageRegister;
    }

    @PostMapping
    public ComputeResponse compute(@RequestBody ComputeRequest request){

        // TODO write exception handler/controller advice or some other thing to return sensible answer for non supported languages

        if (StringUtils.isBlank(request.getSourceLang()) || StringUtils.isBlank(request.getTargetLang())) {
            throw new IllegalArgumentException("Evaluation failed. Source and target language both need to be provided.");
        }

        if (StringUtils.isBlank(request.getProgram())) {
            throw new IllegalArgumentException("No program was provided.");
        }

        if (!languageRegister.isEvaluationSupported(request.getSourceLang(), request.getTargetLang())) {
            throw new IllegalArgumentException("Evaluation of source " + request.getSourceLang()
                    + " to target " + request.getTargetLang() + " is not supported.");
        }

        var evaluator = languageRegister.getEvaluator(request.getSourceLang(), request.getTargetLang())
                .orElseThrow( () -> new RuntimeException("Unexpected problem finding Evaluator class for "
                        + request.getSourceLang() + " to " + request.getTargetLang()));
        var result = evaluator.evaluate(request.getProgram());

        var response= new ComputeResponse();
        response.setResult(result);
        response.setLang(request.getTargetLang());
        return response;
    }

}
