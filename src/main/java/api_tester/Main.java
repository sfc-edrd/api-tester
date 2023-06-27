package api_tester;

import api_tester.config.LoggingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

@SpringBootApplication
public class Main
{
    static Logger log;

    static
    {
        log = LoggingConfiguration.getLogInstance();
    }

    public static void main(String[] args)
    {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
        System.out.println(run);
    }

//    private static void sendRequest(LinkedHashMap<String, Object> requestData)
//    {
//        URL                             url;
//        String                          output;
//        HttpURLConnection               connection;
//        OutputStream                    os;
//        OutputStreamWriter              osw;
//        LinkedHashMap<String, String>   headers;
//        InputStreamReader               in;
//        BufferedReader                  br;
//        long                            initialTimestamp;
//        LinkedHashMap<String, String>   assertions;
//        Gson                            gson;
//        List<Boolean>                   collect;
//
//        gson = new GsonBuilder()
//                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
//                .create();
//        initialTimestamp = System.currentTimeMillis();
//        try
//        {
//            // set url
//            url = new URL(requestData.get("location").toString());
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            // set headers
//            headers = (LinkedHashMap<String, String>) requestData.get("headers");
//            headers.entrySet().stream().forEach(e -> connection.setRequestProperty(e.getKey(), e.getValue()));
//            // set body
//            connection.setDoOutput(true);
//            os = connection.getOutputStream();
//            osw = new OutputStreamWriter(os, "UTF-8");
//            osw.write(requestData.get("body").toString());
//            osw.flush();
//            osw.close();
//            os.close();
//
//            connection.connect();
//            // check return
//            if (connection.getResponseCode() == Integer.parseInt(requestData.get("responseStatus").toString()))
//            {
//                in = new InputStreamReader(connection.getInputStream());
//                br = new BufferedReader(in);
//                while ((output = br.readLine()) != null)
//                {
//                    assertions = (LinkedHashMap<String, String>) requestData.get("responseAssertions");
//                    LinkedHashMap mappedResponse = gson.fromJson(output, LinkedHashMap.class);
//                    collect = assertions.entrySet().stream().map(e -> mappedResponse.get(e.getKey()).toString().equals(e.getValue())).collect(Collectors.toList());
//                    if (collect.contains(false))
//                    {
//                        log.info("Error - [" + (System.currentTimeMillis() - initialTimestamp) + "ms]");
//                        log.info("Expected: " + assertions.toString());
//                        log.info("Received: " + mappedResponse.toString());
//                    } else {
//                        log.info("Success - [" + (System.currentTimeMillis() - initialTimestamp) + "ms]");
//                    }
//                }
//                connection.disconnect();
//            } else
//                log.info("Response code: " + connection.getResponseCode());
//
//        }
//        catch (IOException ioe)
//        {
//            throw new RuntimeException(ioe.getMessage());
//        }
//    }
//
//    private static LinkedHashMap<String, Object> checkRequiredFieldsAndGetRequestDataMapping(File test)
//    {
//        List<String>                    requiredFields;
//        LinkedHashMap<String, Object>   dataMapping;
//        String                          line;
//
//        try
//        {
//            requiredFields = new ArrayList<>(List.of("location", "headers", "body", "responseStatus", "responseAssertions"));
//            dataMapping = new LinkedHashMap<>();
//            BufferedReader buffReader = new BufferedReader(new FileReader(test));
//            line = buffReader.readLine();
//            while (line != null)
//            {
//                String[] lineData = line.split("=");
//                String lineKey = lineData[0];
//                String lineValue = lineData[1];
//                if (requiredFields.contains(lineKey))
//                {
//                    requiredFields.remove(lineKey);
//                    if (lineValue.startsWith("["))
//                    {
//                        Map<String, String> valueMap = new LinkedHashMap<>();
//                        String[] valueSplit = lineValue.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
//                        for (String values : valueSplit)
//                        {
//                            String valueKey = values.split(":")[0].replace("\"", "").trim();
//                            String valueData = values.split(":")[1].replace("\"", "").trim();
//                            valueMap.put(valueKey, valueData);
//                        }
//                        dataMapping.put(lineKey, valueMap);
////                        continue;
//                    }
//                    else
//                        dataMapping.put(lineKey, lineValue);
//                }
//                line = buffReader.readLine();
//            }
//
//            if (requiredFields.isEmpty())
//                return (dataMapping);
//            log.info("Arguments missing: " + requiredFields.toString());
//            throw new MissingFormatArgumentException(requiredFields.toString());
//        }
//        catch (FileNotFoundException e)
//        {
//            throw new RuntimeException(e);
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
}
