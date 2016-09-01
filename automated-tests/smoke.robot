*** Test Cases ***
Application should expose valid API for retrieving cats
    Create HTTP Context  http://127.0.0.1:%{APPLICATION_PORT}
    GET  /all
    Response Status Code Should Equal  200
    Follow Response
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
