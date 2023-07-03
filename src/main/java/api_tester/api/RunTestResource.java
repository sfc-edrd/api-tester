package api_tester.api;

import api_tester.services.TestRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/run")
@Slf4j
public class RunTestResource
{
    @Autowired
    private TestRunService testRunService;

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public String runAll()
    {
        log.info("Running all tests");
        return (testRunService.runAllTests());
    }

}
