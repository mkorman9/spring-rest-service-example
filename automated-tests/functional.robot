*** Setting ***
Library  HttpLibrary.HTTP

*** Keywords ***
Add cat
    [Arguments]  ${roleName}  ${name}  ${duelsWon}  ${groupId}
    Next Request Should Succeed
    Set Request Body  {"roleName":"${roleName}","name":"${name}","duelsWon":${duelsWon},"group":{"id":${groupId}}}
    Set Request Header  Content-Type  application/json
    POST  /add
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Read all cats
    Next Request Should Succeed
    GET  /all
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    [Return]  ${response}

*** Test Cases ***
Added cat should be remembered and returned
    Create HTTP Context  localhost:%{APPLICATION_PORT}
    Add cat  Pirate  Barnaba  13  1
    Add cat  Bandit  Bonny  12  2
    ${catsJson}=  Read all cats
    Log  ${catsJson}
