# api-tester 'CLI'
### [ Test APIs and confirm the result based on predefined parameters ]

Cli application that scans the directory configured in the application.properties file in the tests.folder.relative-path parameter and read all the .test files available in this location.
.test files. 

## INPUT
.test file parameters must match the following tags and values:

### location: url string
> #### location=https://api.example.com/v1/check_data

### headers: string list
> #### headers=["Content-Type: application/json", "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInA5cCI6IkpXVCIcImtpZCI6Imt1VGdaSTgxWVBCeS1aVXFvR1JCNCJ9..."]

### body: json body in one line
> #### body={"client_id": 42, "serial": "000000008A1390AC", "data_fmt": "H"}

### responseStatus: expected return http code
> #### responseStatus=200

### responseAssertions: list of parameters and values to be obtained from the response
> #### responseAssertions=["retCode: 0", "retValid: true", "retDescription: Serial number changed"]

## Output
#### The output will be shown by logs and will look like this:

<span style="color:green"><b>Test Success</b></span>
> Jun 26, 2023 11:51:30 PM api_tester.RequestThread run
> INFO: https://api.example.com/v1/check_data => OK [1696ms]

<span style="color:red"><b>Test Fail</b></span>
> Jun 26, 2023 11:51:30 PM api_tester.RequestThread run
> 
> INFO: https://api.example.com/v1/check_data => NOK [1695ms]
> 
> Expected: {retCode=1, retValid=true, retDesc=No error}
> 
> Received: {retCode=0, retValid=true, retDescription=No error}
> 
