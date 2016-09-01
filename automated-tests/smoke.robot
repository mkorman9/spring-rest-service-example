*** Setting ***
Library  HttpLibrary.HTTP

*** Test Cases ***
Valid API for retrieving cats should be exposed
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    GET  /all
    Response Status Code Should Equal  200
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Asking for not-existing context should return 404 and valid JSON
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    GET  /some_non_existing_context
    Response Status Code Should Equal  404
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
