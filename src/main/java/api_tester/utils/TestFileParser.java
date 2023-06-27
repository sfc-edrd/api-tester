//package api_tester.utils;
//
//import java.io.*;
//import java.util.*;
//
//public class TestFileParser
//{
//    public TestFileParser(File testFile)
//    {
//            List<String> requiredFields;
//            LinkedHashMap<String, Object> dataMapping;
//            String                          line;
//            String[] lineData;
//
//            try
//            {
//                requiredFields = new ArrayList<>(List.of("location", "headers", "body", "responseStatus", "responseAssertions"));
//                dataMapping = new LinkedHashMap<>();
//                BufferedReader buffReader = new BufferedReader(new FileReader(testFile));
//                line = buffReader.readLine();
//                while (line != null)
//                {
//                    lineData = line.split("=");
//                    String lineKey = lineData[0];
//                    String lineValue = lineData[1];
//                    if (requiredFields.contains(lineKey))
//                    {
//                        requiredFields.remove(lineKey);
//                        if (lineValue.startsWith("["))
//                        {
//                            Map<String, String> valueMap = new LinkedHashMap<>();
//                            String[] valueSplit = lineValue.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
//                            for (String values : valueSplit)
//                            {
//                                String valueKey = values.split(":")[0].replace("\"", "").trim();
//                                String valueData = values.split(":")[1].replace("\"", "").trim();
//                                valueMap.put(valueKey, valueData);
//                            }
//                            dataMapping.put(lineKey, valueMap);
////                        continue;
//                        }
//                        else
//                            dataMapping.put(lineKey, lineValue);
//                    }
//                    line = buffReader.readLine();
//                }
//
//                if (requiredFields.isEmpty())
//                    return (dataMapping);
//                log.info("Arguments missing: " + requiredFields.toString());
//                throw new MissingFormatArgumentException(requiredFields.toString());
//            }
//            catch (FileNotFoundException e)
//            {
//                throw new RuntimeException(e);
//            }
//            catch (IOException e)
//            {
//                throw new RuntimeException(e);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (MissingFormatArgumentException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//    }
//    }
//}
