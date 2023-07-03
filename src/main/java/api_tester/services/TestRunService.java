package api_tester.services;

import api_tester.entities.TestData;
import api_tester.exceptions.TestFileParsingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestRunService
{

    @Autowired
    private TestCrudService testCrudService;

    public String runAllTests()
            throws TestFileParsingException
    {
        List<TestData> allTests = testCrudService.getAllTests();
        allTests.stream().forEach(t -> sendRequest(t));

        return "ok";
    }

    private static void sendRequest(TestData testData)
    {
        URL                             url;
        String                          output;
        HttpURLConnection connection;
        OutputStream os;
        OutputStreamWriter osw;
        LinkedHashMap<String, String>   headers;
        InputStreamReader in;
        BufferedReader br;
        long                            initialTimestamp;
        LinkedHashMap<String, String>   assertions;
        Gson gson;
        List<Boolean>                   collect;

        gson = new GsonBuilder()
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create();
        initialTimestamp = System.currentTimeMillis();
        try
        {
            // set url
            url = new URL(testData.getLocation());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // set headers
            headers = testData.getHeaders();
            headers.entrySet().stream().forEach(e -> connection.setRequestProperty(e.getKey(), e.getValue()));
            // set body
            connection.setDoOutput(true);
            os = connection.getOutputStream();
            osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(gson.toJson(testData.getBody()));
            osw.flush();
            osw.close();
            os.close();

            connection.connect();
            // check return
            if (connection.getResponseCode() == testData.getResponseStatus())
            {
                in = new InputStreamReader(connection.getInputStream());
                br = new BufferedReader(in);
                while ((output = br.readLine()) != null)
                {
                    assertions = testData.getResponseAssertions();
                    LinkedHashMap mappedResponse = gson.fromJson(output, LinkedHashMap.class);
                    collect = assertions.entrySet().stream().map(e -> mappedResponse.get(e.getKey()).toString().equals(e.getValue())).collect(Collectors.toList());
                    if (collect.contains(false))
                    {
                        log.info("Error - [" + (System.currentTimeMillis() - initialTimestamp) + "ms]");
                        log.info("Expected: " + assertions.toString());
                        log.info("Received: " + mappedResponse.toString());
                    } else {
                        log.info("Success - [" + (System.currentTimeMillis() - initialTimestamp) + "ms]");
                    }
                }
                connection.disconnect();
            } else
                log.info("Response code: " + connection.getResponseCode());

        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe.getMessage());
        }
    }

}
