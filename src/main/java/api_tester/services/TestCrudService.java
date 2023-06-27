package api_tester.services;

import api_tester.entities.TestData;
import api_tester.exceptions.TestFileParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestCrudService
{
    @Value(value = "${tests.folder.relative-path}")
    String testFolderRelativePath;

    private final String TEST_FILENAME_SUFFIX = ".test";

    public List<TestData> getAllTests()
            throws TestFileParsingException
    {
        List<File>      testFileList;
        TestData        testData;
        List<TestData>  testDataList;

        log.info("Relative path: " + testFolderRelativePath);
        log.info("Listing test files");
        testFileList = Arrays.asList(new File(testFolderRelativePath)
                .listFiles())
                .stream()
                .filter(f -> f.getName().endsWith(TEST_FILENAME_SUFFIX))
                .collect(Collectors.toList());
        log.debug("Tests found: " + testFileList);
        testDataList = new ArrayList<>();

        for (File testFile : testFileList)
        {
            testData = new TestData();
            testData.parse(testFile);
            testDataList.add(testData);
        }

        return (testDataList);
    }

    public TestData getTest(String testId)
            throws NoSuchElementException
    {
        File        testFile;
        TestData    testData;

        log.info("Relative path: " + testFolderRelativePath);
        log.info("Getting test file ID: " + testId);
        testFile = Arrays.asList(new File(testFolderRelativePath)
                .listFiles())
                .stream()
                .filter(f -> f.getName().endsWith(testId + TEST_FILENAME_SUFFIX))
                .findFirst()
                .orElseThrow();
        log.debug("Test found: " + testFile);
        log.debug("Parsing to Object");
        testData = new TestData();
        testData.parse(testFile);
        log.debug("Parsed: " + testData);

        return (testData);
    }

    public void addTest() {
    }

    public void putTest() {
    }

    public void deleteTest() {
    }

}
