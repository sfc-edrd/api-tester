package api_tester.api;

import api_tester.entities.TestData;
import api_tester.services.TestCrudService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/tests")
@Slf4j
@RequiredArgsConstructor
public class TestCrudResource
{
    /*
     *  Service Injection
     */
    @Autowired
    private final TestCrudService service;

    /**
     * Retrieve all existing Test Data
     * @return all existing tests
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TestData> getAllTests()
    {
        List<TestData>  allTests;

        log.info("Getting all tests");
        allTests = service.getAllTests();
        log.debug(allTests.toString());

        return (service.getAllTests());
    }

    /**
     * Get a Test file by Test ID (filename)
     * @param testId
     * @return TestData
     */
    @GetMapping("/{testId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TestData getTest(@PathVariable ("testId") String testId)
    {
        log.info("Getting test by ID: " + testId);

        return (service.getTest(testId));
    }


    /**
     * Add a new Test file to be used during tests attempts
     * @param testData
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTest(@RequestBody @Valid TestData testData)
    {
        log.info("Adding new test");
        log.debug(testData.toString());

        service.addTest();
    }

    /**
     * Update a pre-existing Test file
     * @param testId
     * @param testData
     */
    @PutMapping("/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putTest(@PathVariable ("testId") String testId,
                        @RequestBody @Valid TestData testData)
    {
        log.info("Putting test for ID " + testId);
        log.debug(testData.toString());
        service.putTest();
    }

    /**
     * Delete a test file
     * @param testId
     */
    @DeleteMapping("/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTest(@PathVariable ("testId") String testId)
    {
        log.info("Deleting test for ID " + testId);
        service.deleteTest();
    }
}
