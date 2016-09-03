*** Setting ***
Library  HttpLibrary.HTTP
Resource  resources/keywords.robot

*** Test Cases ***
Added cat should be remembered and returned
    Create HTTP Context  localhost:%{APPLICATION_PORT}

    Add cat  Pirate  Barnaba  13  Pirates
    Add cat  Bandit  Bonny  12  Bandits

    ${cats}=  Read all cats
    Cat Should Exist On List  ${cats}  Pirate  Barnaba  13  Pirates
    Cat Should Exist On List  ${cats}  Bandit  Bonny  12  Bandits
    ${barnaba}=  Read Single Cat  Barnaba
    Cat Should Be Equal To  ${barnaba}  Pirate  Barnaba  13  Pirates

Cat should be deleted successfully
    Create HTTP Context  localhost:%{APPLICATION_PORT}

    Add cat  Robber  Gutek  2  Bandits
    Delete cat  Gutek

    ${cats}=  Read all cats
    Cat Should Not Exist On List  ${cats}  Robber  Gutek  2  Bandits

Cat should be updated successfully
    Create HTTP Context  localhost:%{APPLICATION_PORT}

    Add cat  Hunter  Bronek  7  Bandits
    Update cat  Hunter  Bronek  8  Bandits

    ${cats}=  Read all cats
    Cat Should Not Exist On List  ${cats}  Hunter  Bronek  7  Bandits
    Cat Should Exist On List  ${cats}  Hunter  Bronek  8  Bandits