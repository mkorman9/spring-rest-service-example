*** Setting ***
Library  HttpLibrary.HTTP

*** Test Cases ***
Valid API for retrieving cats should be exposed
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    Next Request Should Succeed
    GET  /all
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Asking for not-existing context should return 404 and valid JSON
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    Next Request Should Have Status Code  404
    GET  /some_non_existing_context
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
