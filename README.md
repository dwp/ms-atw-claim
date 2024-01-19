# ms-claim

A Java Springboot service within Access to Work (AtW) that will facilitate and manage to submission of claims within AtW

## Service Endpoints

### POST /submit for Equipment Or Adaptations

Submit a claim to be saved in the mongo database

#### Body (all fields are required)

```json5
{
  "claimType": "EQUIPMENT_OR_ADAPTATION",
  "nino": "AA370773A",
  "atwNumber": "ATW1234567895",
  "declarationVersion": 2.1,
  "claim": [
    {
      "description": "Item 1",
      "dateOfPurchase": {
        "dd": "22",
        "mm": "11",
        "yyyy": "2020"
      }
    }
  ],
  "cost": "2211",
  "hasContributions": true,
  "evidence": [
    {
      "fileId": "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    }
  ],
  "payee": {
    "newPayee": true, //for EA newPayee must be true
    "details": {
      "fullName": "George Herbert",
      "emailAddress": "name@name.com"
    },
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND", // Optional
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY", // Optional
      "postcode": "NE26 4RS"
    },
    "bankDetails": {
      "accountHolderName": "George Herbert",
      "sortCode": "000004",
      "accountNumber": "12345677",
      "rollNumber": "12345677"// Optional
    }
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777", 
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    }
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "equipment-cost": {
        "cost": 123.2
      }
    }
  }
}
```

#### Response

201 - Created

```json5
{
  "claimReference": "EA15",
  "claimNumber": 15,
  "claimType": "EQUIPMENT_OR_ADAPTATION"
}
```

### POST /submit for Adaptation To Vehicle

Submit a claim to be saved in the mongo database

#### Body (all fields are required)

```json5
{
  "claimType": "ADAPTATION_TO_VEHICLE",
  "nino": "AA370773A",
  "atwNumber": "ATW1234567895",
  "declarationVersion": 2.1,
  "claim": {
    "0": {
      "claimDescription": {
        "description": "Item 1",
        "dateOfInvoice": {
          "dd": "22",
          "mm": "11",
          "yyyy": "2020"
        }
      }
    },
    "1": {
      "claimDescription": {
        "description": "Item 2",
        "dateOfInvoice": {
          "dd": "2",
          "mm": "11",
          "yyyy": "2020"
        }
      }
    }
  },
  "cost": "2211",
  "hasContributions": true,
  "evidence": [
    {
      "fileId": "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    }
  ],
  "payee": {
    "newPayee": true, //for AV newPayee must be true
    "details": {
      "fullName": "George Herbert",
      "emailAddress": "name@name.com"
    },
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND", // Optional
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY", // Optional
      "postcode": "NE26 4RS"
    },
    "bankDetails": {
      "accountHolderName": "George Herbert",
      "sortCode": "000004",
      "accountNumber": "12345677",
      "rollNumber": "12345677"// Optional
    }
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777", 
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    }
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "equipment-cost": {
        "cost": 123.2
      }
    }
  }
}
```

#### Response

201 - Created

```json5
{
  "claimReference": "AV15",
  "claimNumber": 15,
  "claimType": "ADAPTATION_TO_VEHICLE"
}
```

### POST /submit for Support Worker

Submit a claim to be saved in the mongo database

#### Body (all fields are required)

```json5
{
  "nino": "AA370773A",
  "declarationVersion": 2.1,
  "atwNumber": "ATW1234567895",
  "claimType": "SUPPORT_WORKER",
  "previousClaimId": 1, // Optional. This field links the previous claim to the claim that is being resubmitted.
  "claim": {
    "0": {
      "monthYear": {
        "mm": "04",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfSupport": "1",
          "timeOfSupport": {
            "hoursOfSupport": 2,
            "minutesOfSupport": 15
          },
          "nameOfSupport": "Joe Bloggs"// Optional
        },
        {
          "dayOfSupport": "2",
          "timeOfSupport": {
            "hoursOfSupport": 3,
            "minutesOfSupport": 0
          },
          "nameOfSupport": "John Smith"// Optional
        }
      ]
    },
    "1": {
      "monthYear": {
        "mm": "05",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfSupport": "12",
          "timeOfSupport": {
            "hoursOfSupport": 0,
            "minutesOfSupport": 15
          }
        },
        {
          "dayOfSupport": "14",
          "timeOfSupport": {
            "hoursOfSupport": 12,
            "minutesOfSupport": 15
          },
          "nameOfSupport": "John Smith"// Optional
        }
      ]
    }
  },
  "cost": "2000",
  "hasContributions": false,
  "evidence": [
    {
      "fileId": "09672038-7155-4cb9-8b2e-56eda1fd6b53/7e67a9c5-f7c7-4565-a2ab-6c36530e0710",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    },
    {
      "fileId": "b9c2ea02-f424-4cd3-bdc1-0ab1c26706fe/cfda6946-7bb5-4886-8b27-beaccbd8e834",
      "fileName": "Technical Architect.docx"
    }
  ],
  "payee": {
    "newPayee": true, 
    "details": { // details must always be present
      "fullName": "INeed Paying",
      "emailAddress": "payment@now.com"// Optional if newPayee is set to false
    },
    "address": { // if newPayee is set to false, address is not required
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND", // Optional
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY", // Optional
      "postcode": "NE26 4RS"
    },
    "bankDetails": { // if newPayee is set to false, bankDetails is not required
      "accountHolderName": "Ineed Paying",
      "sortCode": "000004",
      "accountNumber": "12345677"
    }
  },
  "workplaceContact": {
    "fullName": "Count Signer",
    "emailAddress": "count@signer.com"
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    }
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "support-cost": {
        "cost": 123.2
      }
    }
  }
}
```

#### Response

201 - Created

```json5
{
  "claimReference": "SW15",
  "claimNumber": 15,
  "claimType": "SUPPORT_WORKER"
}
```

### POST /submit for Travel To Work

Submit a claim to be saved in the mongo database

#### Body (all fields are required)

```json5
{
  "nino": "AA370773A",
  "atwNumber": "ATW1234567895",
  "declarationVersion": 2.1,
  "claimType": "TRAVEL_TO_WORK",
  "previousClaimId": 1, // Optional. This field links the previous claim to the claim that is being resubmitted for an employed employmentStatus
  "travelDetails": {
    "howDidYouTravelForWork": "lift", // Values: lift or taxi
    "journeysOrMileage": "mileage"// Optional. Will only be show if above is lift. Values: mileage or Journeys
  },
  "cost": 121, // If TW and howDidYouTravelForWork == lift then it will not be presented
  "hasContributions": true,
  "claim": {
    "0": {
      "monthYear": {
        "mm": "04",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfTravel": "12",
          "totalTravel": "13" // Both fields are required. No optional fields as this is a TW specific field
        }
      ]
    },
    "1": {
      "monthYear": {
        "mm": "05",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfTravel": "12",
          "totalTravel": "13"
        }
      ]
    }
  },
  "evidence": [// Same as SW and EA
    {
      "fileId": "583d32fb-4b1d-418b-a25b-d1a47feb095f/4a5d1bdf-7b60-402d-93bf-de3e7a369044",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    }
  ],
  "payee": { // Same as SW and EA
    "newPayee": true,
    "details": { // details must always be present
      "fullName": "INeed Paying",
      "emailAddress": "payment@now.com"// Optional if newPayee is set to false
    },
    "address": {// if newPayee is set to false, address is not required
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND", // Optional
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY", // Optional
      "postcode": "NE26 4RS"
    },
    "bankDetails": {// if newPayee is set to false, bankDetails is not required
      "accountHolderName": "Ineed Paying",
      "sortCode": "000004",
      "accountNumber": "12345677"
    }
  },
  "workplaceContact": {//This block is different for TW
    "employmentStatus": "employed", // Only shown on TW. Values: employed or selfEmployed
    "fullName": "Count Signer", // Only shown when employmentStatus is employed
    "emailAddress": "Count@sign.com"// Only shown when employmentStatus is employed
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    }
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "travel-cost": {
        "cost": 123.2
      }
    }
  }
}
```

#### Response

201 - Created

```json5
{
  "claimReference": "TW15",
  "claimNumber": 15,
  "claimType": "TRAVEL_TO_WORK"
}
```

### PUT /accept

Submit an update to a claim in the mongo database

#### Body (all fields are required)

```json5
{
  "claimNumber": 3,
  "claimType": "SUPPORT_WORKER",
  "organisation": "company2",
  "declarationVersion": 2.1,
  "jobTitle": "boss2",
  "address": {
    "address1": "THE COTTAGE",
    "address2": "ST. MARYS ISLAND", // Optional
    "address3": "WHITLEY BAY",
    "address4": "WHITLEY BAY", // Optional
    "postcode": "NE26 4RS"
  }
}
```

#### Response

204 - No Content

### PUT /reject

Submit an update to reject a claim in the mongo database

#### Body (all fields are required)

```json5
{
  "claimNumber": 1,
  "claimType": "TRAVEL_TO_WORK",
  "organisation": "company",
  "jobTitle": "boss",
  "address": {
    "address1": "THE COTTAGE",
    "address2": "ST. MARYS ISLAND", // Optional
    "address3": "WHITLEY BAY",
    "address4": "WHITLEY BAY", // Optional
    "postcode": "NE26 4RS"
  },
  "reason": "This claim is not valid" // Max limit of 300 characters
}
```

#### Response

204 - No Content

### POST "/claim-for-reference-and-nino"

Retrieve claim request for the claim reference (claim type and claim number)

#### Body (all fields are required)

```json5
{
  "claimReference": "TW0001",
  "nino": "AA370773A"
}
```

#### Response

200 - a claim request for the claim number and claim type given

```json5
{
  "id": 1,
  "createdDate": "2022-01-18T16:31:03.297",
  "lastModifiedDate": "2022-01-18T16:31:29.095",
  "claimStatus": "COUNTER_SIGN_REJECTED",
  "nino": "AA370773A",
  "claimType": "TRAVEL_TO_WORK",
  "atwNumber": "ATW123456789",
  "cost": 2211.0,
  "evidence": [
    {
      "fileId": "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    }
  ],
  "payee": {
    "details": {
      "fullName": "George Herbert",
      "emailAddress": "name@name.com"
    },
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND",
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY",
      "postcode": "NE26 4RS"
    },
    "bankDetails": {
      "accountHolderName": "George Herbert",
      "sortCode": "000004",
      "accountNumber": "12345677",
      "rollNumber": "12345677"
    }
  },
  "declarationVersion": 2.1,
  "travelDetails": {
    "howDidYouTravelForWork": "taxi",
    "journeysOrMiles": null
  },
  "claim": {
    "0": {
      "monthYear": {
        "mm": "04",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfTravel": "12",
          "totalTravel": "13"
        }
      ]
    }
  },
  "workplaceContact": {
    "emailAddress": "email@company.com",
    "organisation": "company",
    "jobTitle": "boss",
    "reason": "nulla facilisi etiam dignissim",
    "updatedOn": "2021-11-18T14:00:38.477"
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    },
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "travel-cost": {
        "cost": 123.2
      }
    }
  }
}
```

### POST "/claim-to-workplace-contact"

Retrieve claim request for the claim reference (claim type and claim number)

#### Body (all fields are required)

```json5
{
  "claimReference": "TW0001"
}
```

#### Response

200 - a claim request for the claim number and claim type with AWAITING_COUNTER_SIGN value only

```json5
{
  "id": 1,
  "createdDate": "2022-01-18T16:31:03.297",
  "lastModifiedDate": "2022-01-18T16:31:29.095",
  "atwNumber": "ATW123456789",
  "claimStatus": "AWAITING_COUNTER_SIGN",
  "nino": "AA370773A",
  "claimType": "TRAVEL_TO_WORK",
  "cost": 2211.0,
  "evidence": [
    {
      "fileId": "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3",
      "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
    }
  ],
  "payee": {
    "details": {
      "fullName": "George Herbert",
      "emailAddress": "name@name.com"
    },
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND",
      "address3": "WHITLEY BAY",
      "address4": "WHITLEY BAY",
      "postcode": "NE26 4RS"
    },
    "bankDetails": {
      "accountHolderName": "George Herbert",
      "sortCode": "000004",
      "accountNumber": "12345677",
      "rollNumber": "12345677"
    }
  },
  "declarationVersion": 2.1,
  "travelDetails": {
    "howDidYouTravelForWork": "taxi",
    "journeysOrMiles": null
  },
  "claim": {
    "0": {
      "monthYear": {
        "mm": "04",
        "yyyy": "2020"
      },
      "claim": [
        {
          "dayOfTravel": "12",
          "totalTravel": "13"
        }
      ]
    }
  },
  "workplaceContact": {
    "emailAddress": "email@company.com",
    "organisation": "company",
    "jobTitle": "boss",
    "reason": "nulla facilisi etiam dignissim",
    "updatedOn": "2021-11-18T14:00:38.477"
  },
  "claimant": {
    "forename": "Odin",
    "surname": "Surtsson",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "Odin.Surtsson@gmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07700900630",
    "company": "Company 1",
    "address": {
      "address1": "1 The Street",
      "address2": "Village Name",
      "address3": "Town",
      "address4": "County",
      "postcode": "NE26 4RS"
    },
  },
  "journeyContext": { // This is the data as it is saved in Casa
    "data": {
      "travel-cost": {
        "cost": 123.2
      }
    }
  }
}
```

### POST /claims-for-nino-and-type

Retrieve all claims for the user (by Nino)

#### Body (all fields are required)

```json5
{
  "nino": "AA370773A"
}
```

#### Response

200 - a list of claims (all fields will be populated):

```json5
[
  {
    "id": 11,
    "createdDate": "2022-01-18T16:31:03.297",
    "lastModifiedDate": "2022-01-18T16:31:29.095",
    "atwNumber": "ATW123456789",
    "claimStatus": "AWAITING_COUNTER_SIGN",
    "nino": "AA370773A",
    "claimType": "TRAVEL_TO_WORK",
    "cost": 2211.0,
    "evidence": [
      {
        "fileId": "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3",
        "fileName": "6b99f480c27e246fa5dd0453cd4fba29.pdf"
      }
    ],
    "payee": {
      "details": {
        "fullName": "George Herbert",
        "emailAddress": "name@name.com"
      },
      "address": {
        "address1": "THE COTTAGE",
        "address2": "ST. MARYS ISLAND",
        "address3": "WHITLEY BAY",
        "address4": "WHITLEY BAY",
        "postcode": "NE26 4RS"
      },
      "bankDetails": {
        "accountHolderName": "George Herbert",
        "sortCode": "000004",
        "accountNumber": "12345677",
        "rollNumber": "12345677"
      }
    },
    "declarationVersion": 2.1,
    "travelDetails": {
      "howDidYouTravelForWork": "taxi",
      "journeysOrMiles": null
    },
    "claim": {
      "0": {
        "monthYear": {
          "mm": "04",
          "yyyy": "2020"
        },
        "claim": [
          {
            "dayOfTravel": "12",
            "totalTravel": "13"
          }
        ]
      }
    },
    "workplaceContact": {
      "emailAddress": "email@company.com",
      "organisation": "company",
      "jobTitle": "boss",
      "reason": "nulla facilisi etiam dignissim",
      "updatedOn": "2021-11-18T14:00:38.477"
    },
    "claimant": {
      "forename": "Odin",
      "surname": "Surtsson",
      "dateOfBirth": "1930-11-22",
      "emailAddress": "Odin.Surtsson@gmail.com",
      "homeNumber": "01277777777",
      "mobileNumber": "07700900630",
      "company": "Company 1",
      "address": {
        "address1": "1 The Street",
        "address2": "Village Name",
        "address3": "Town",
        "address4": "County",
        "postcode": "NE26 4RS"
      }
    },
    "journeyContext": { // This is the data as it is saved in Casa
      "data": {
        "travel-cost": {
          "cost": 123.2
        }
      }
    }
  }
]
```

### PUT /change-status/uploaded-to-document-batch
Update claim status to UPLOADED_TO_DOCUMENT_BATCH
#### Body

```json5
{
  "claimReference" : "SW11",
  "requestId" : "db1543c5-449a-448c-9cb7-5aab84bd4670"
} 
```
#### Response

204 - No Content

### PUT /change-status/drs-error
Update claim status to DRS_ERROR
#### Body

```json5
{
  "requestId" : "db1543c5-449a-448c-9cb7-5aab84bd4670"
} 
```
#### Response

204 - No Content

### PUT /change-status/awaiting-agent-approval
Update claim status to AWAITING_AGENT_APPROVAL
#### Body

```json5
{
  "requestId" : "db1543c5-449a-448c-9cb7-5aab84bd4670"
} 
```
#### Response

204 - No Content

### PUT /update-workplace-contact-details
Update the workplace details before the submitted claim is accepted or reject by the workplace contact
#### Body

```json5
{
  "nino": "AA370773A",
  "claimReference": "TW6",
  "workplaceContact" :
  {
    "fullName": "Counter Signer1",
    "emailAddress": "countemail2@signer.com"
  }
}
```
#### Response

204 - No Content


## Contact Information endpoints

### POST /update-contact-information
Update contact information which is saved in the mongo database
#### Body

```json5
{
  "accessToWorkNumber": "ATW12006521",
  "nino": "AA370773A",
  "declarationVersion" : 2.3,
  "currentContactInformation" : {
    "forename": "Martha", //optional
    "surname": "Ledgrave", //optional
    "dateOfBirth": "1930-11-22", //optional
    "emailAddress": "martha.ledgrave@email.com", //optional
    "homeNumber": "01277777777", //optional
    "mobileNumber": "07627847834", //optional
    "address": { //optional
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND",//optional
      "address3": "WHITLEY BAY",
      "postcode": "NE26 4RS"
    }
  },
  "newContactInformation" : {
    "forename": "Martha", //optional
    "surname": "Ledgrave", //optional
    "dateOfBirth": "1930-11-22", //optional
    "emailAddress": "martha.ledgrave@hotmail.com", //optional
    "homeNumber": "01277777777", //optional
    "mobileNumber": "07627847834", //optional
    "address": { //optional
      "address1": "15 Redburry Grove",
      "address2": "Bramhope", //optional
      "address3": "Leeds",
      "address4": "West Yorkshire", //optional
      "postcode": "LS6 7RU"
    }
  }
}
```

#### Response

201 - Created

```json5
{
  "contactInformationStatus": "AWAITING_UPLOAD",
  "createdDate": "2022-02-25T16:40:59.492887",
  "accessToWorkNumber": "ATW12006521",
  "nino": "AA370773A",
  "declarationVersion": 2.3,
  "currentContactInformation": {
    "forename": "Martha",
    "surname": "Ledgrave",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "martha.ledgrave@email.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07627847834",
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND",
      "address3": "WHITLEY BAY",
      "address4": null,
      "postcode": "NE26 4RS"
    }
  },
  "newContactInformation": {
    "forename": "Martha",
    "surname": "Ledgrave",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "martha.ledgrave@hotmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07627847834",
    "address": {
      "address1": "15 Redburry Grove",
      "address2": "Bramhope",
      "address3": "Leeds",
      "address4": "West Yorkshire",
      "postcode": "LS6 7RU"
    }
  }
}
```

### POST /retrieve-contact-information
Retrieve contact information from the mongo database. The contactInformationStatus available are AWAITING_UPLOAD, PROCESSING_UPLOAD, UPLOADED_TO_DOCUMENT_BATCH
#### Body 

```json5
{
  "requestId": "6234b56be952e507b56268f8"
}
```

#### Response
200 - a list of the contact information that has been submitted to be updated 
```json5
{
  "id": "623c9f1c9ca76741d2ba883a",
  "contactInformationStatus": "AWAITING_UPLOAD",
  "createdDate": "2022-03-24T16:41:00.628",
  "accessToWorkNumber": "ATW12006521",
  "nino": "AA370773A",
  "declarationVersion": 2.3,
  "currentContactInformation": {
    "forename": "Martha",
    "surname": "Ledgrave",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "martha.ledgrave@email.com",
    "homeNumber": "01277777777",
    "mobileNumber": "07627847834",
    "address": {
      "address1": "THE COTTAGE",
      "address2": "ST. MARYS ISLAND",
      "address3": "WHITLEY BAY",
      "address4": null,
      "postcode": "NE26 4RS"
    }
  },
  "newContactInformation": {
    "forename": "Martha",
    "surname": "Ledgrave",
    "dateOfBirth": "1930-11-22",
    "emailAddress": "martha.ledgrave@hotmail.com",
    "homeNumber": "01277777777",
    "mobileNumber": "1",
    "address": {
      "address1": "15 Redburry Grove",
      "address2": "Bramhope",
      "address3": "Leeds",
      "address4": "West Yorkshire",
      "postcode": "LS6 7RU"
    }
  }
}
```

### PUT /contact/change-status/processing-upload
Update the contact information status to be PROCESSING_UPLOAD
#### Body

```json5
{
  "requestId": "6234b56be952e507b56268f8"
}
```
#### Response

204 - No Content

### PUT /contact/change-status/completed-upload
Update the contact information status to be COMPLETE_UPLOAD
#### Body

```json5
{
  "requestId": "6234b56be952e507b56268f8"
}
```
#### Response

204 - No Content


### POST /count-rejected-claims
End point to get the count of rejected claims
#### Body

```json5
{
  "nino": "AA370773A"
}
```
#### Response
```json5
{
"count": 1
}
```

### POST /count-rejected-claims-by-claim-type
End point to get the count of rejected claims per claimtype
#### Body

```json5
{
  "nino": "AA370773A"
}
```
#### Response
```json5
[
  {
    "claimType": "TRAVEL_TO_WORK",
    "count": 1
  },
  {
    "claimType": "SUPPORT_WORKER",
    "count": 3
  }
]
```
## Health check endpoints

This service utilises Spring Boot actuator.

### GET /actuator/health

200:

```json5
{
  "status": "UP"
}
```

503:

```json5
{
  "status": "DOWN"
}
```

## Run service

### Locally
if this is your first time running this service locally, please [set up your AWS credentials](#setting-up-local-aws-credentials)

- spin up the dependencies of this service

- start the service
  ```shell
  mvn spring-boot:run -Dspring-boot.run.profiles=local 
  ```

### Setting up local AWS credentials 
there are [a few different ways to load AWS credentials](https://stackoverflow.com/a/43503324).
The credentials for localstack can be found [on the first few lines of this file](https://gitlab.com/dwp/health/atw/atw-local/-/blob/develop/localstack/init.sh)



Maintainer Team: Bluejay

Contributing file: ../CONTRIBUTING.md