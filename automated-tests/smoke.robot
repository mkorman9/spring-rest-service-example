*** Setting ***
Library  HttpLibrary.HTTP

*** Test Cases ***
Application should expose valid API for retrieving cats
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    GET  /all
    Response Status Code Should Equal  200
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
