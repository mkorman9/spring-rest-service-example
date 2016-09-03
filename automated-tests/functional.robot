*** Setting ***
Library  HttpLibrary.HTTP
Library  keywords.py

*** Keywords ***
Get Id For Group
    [Arguments]  ${name}
    Next Request Should Succeed
    GET  /groups
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    ${response}=  Get Json Value  ${response}  /data
    ${id}=  Find Id By Name  ${response}  ${name}
    [Return]  ${id}

Get Id For Cat
    [Arguments]  ${name}
    Next Request Should Succeed
    GET  /all
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    ${response}=  Get Json Value  ${response}  /data
    ${id}=  Find Id By Name  ${response}  ${name}
    [Return]  ${id}

Add cat
    [Arguments]  ${roleName}  ${name}  ${duelsWon}  ${groupId}
    Next Request Should Succeed
    Set Request Body  {"roleName":"${roleName}","name":"${name}","duelsWon":${duelsWon},"group":{"id":${groupId}}}
    Set Request Header  Content-Type  application/json
    POST  /add
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Delete cat
    [Arguments]  ${id}
    Next Request Should Succeed
    DELETE /delete/${id}
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Read all cats
    Next Request Should Succeed
    GET  /all
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    ${response}=  Get Json Value  ${response}  /data
    [Return]  ${response}

*** Test Cases ***
Added cat should be remembered and returned
    Create HTTP Context  localhost:%{APPLICATION_PORT}

    ${piratesId}=  Get Id For Group  Pirates
    ${banditsId}=  Get Id For Group  Bandits

    Add cat  Pirate  Barnaba  13  ${piratesId}
    Add cat  Bandit  Bonny  12  ${banditsId}

    ${cats}=  Read all cats
    Cat Should Exist On List  ${cats}  Pirate  Barnaba  13  Pirates
    Cat Should Exist On List  ${cats}  Bandit  Bonny  12  Bandits

Cat should be deleted successfully
    Create HTTP Context  localhost:%{APPLICATION_PORT}

    ${banditsId}=  Get Id For Group  Bandits
    Add cat  Robber  Gutek  2  ${piratesId}
    ${catId}=  Find Id For Cat  Gutek

    Delete cat  ${catId}

    ${cats}=  Read all cats
    Cat Should Not Exist On List  ${cats}  Robber  Gutek  2  Bandits