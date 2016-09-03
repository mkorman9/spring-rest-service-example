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
    [Arguments]  ${roleName}  ${name}  ${duelsWon}  ${groupName}
    ${groupId}=  Get Id For Group  ${groupName}
    Next Request Should Succeed
    Set Request Body  {"roleName":"${roleName}","name":"${name}","duelsWon":${duelsWon},"group":{"id":${groupId}}}
    Set Request Header  Content-Type  application/json
    POST  /add
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Delete cat
    [Arguments]  ${name}
    ${catId}=  Get Id For Cat  ${name}
    Next Request Should Succeed
    DELETE  /delete/${catId}
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Update cat
    [Arguments]  ${roleName}  ${name}  ${duelsWon}  ${groupName}
    ${groupId}=  Get Id For Group  ${groupName}
    ${catId}=  Get Id For Cat  ${name}
    Next Request Should Succeed
    Set Request Body  {"roleName":"${roleName}","name":"${name}","duelsWon":${duelsWon},"group":{"id":${groupId}}}
    Set Request Header  Content-Type  application/json
    PUT  /edit/${catId}
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}

Read all cats
    Next Request Should Succeed
    GET  /all
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    ${response}=  Get Json Value  ${response}  /data
    [Return]  ${response}

Read single cat
    [Arguments]  ${name}
    ${catId}=  Get Id For Cat  ${name}
    Next Request Should Succeed
    GET  /get/${catId}
    ${response}=  Get Response Body
    Should Be Valid Json  ${response}
    ${response}=  Get Json Value  ${response}  /data
    [Return]  ${response}
