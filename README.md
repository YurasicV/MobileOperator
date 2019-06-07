# Mobile Operator

REST API service

## Getting Started

This application allows to store information about clients of mobile operator.  Also, it stores information about clients calls

### Endpoints

1. Creating a client (GET)   
   http://localhost:8080/api/client/

   ```
   RequestBody example:
   {
	"fullName": "Fedorov",
	"birthday": "1990-04-04",
	"gender": "MALE",
	"phoneNumbers": [
		{"phone": "0661234567"},
		{"phone": "0501234567"}
	]
   }
   ResponceBody:
   {
    "id": 1,
    "fullName": "Fedorov",
    "birthday": "1990-04-04T00:00:00.000+0000",
    "gender": "MALE",
    "phoneNumbers": [
        {
            "id": 2,
            "phone": "0661234567"
        },
        {
            "id": 3,
            "phone": "0501234567"
        }
    ]
   }
   ```

2. Storing information about a client's call (POST)   
   http://localhost:8080/api/call/
   ```
   RequestBody example:
   {
	"callerPhoneNumber": {"phone": "0661234567"},
	"recipientPhoneNumber": {"phone": "0631234567"},
	"callDt": "2019-06-06T04:20:00.000+0000",
	"duration": 400,
	"city": {"name": "Dnipro"}
   }
   ResponceBody:
   {
    "id": 10,
    "callerPhoneNumber": {
        "id": 2,
        "phone": "0661234567"
    },
    "recipientPhoneNumber": {
        "id": 6,
        "phone": "0631234567"
    },
    "callDt": "2019-06-06T04:20:00.000+0000",
    "duration": 400,
    "city": {
        "id": 9,
        "name": "Dnipro"
    }
   }
   ``` 

3. Providing information on a number of calls per each city (GET)   
   http://localhost:8080/api/calls-per-cities/
   ```
   ResponceBody example:
   [
    {
        "cityName": "Kyiv",
        "callsNumber": 3
    },
    {
        "cityName": "Dnipro",
        "callsNumber": 4
    }
   ]
   ``` 

4. Providing information about the longest call for defined client id and specified date range (POST)   
   http://localhost:8080/api/the-longest-call/
   ```
   RequestBody example:
   {
	"clientId": 1, 
	"dateFrom": "2019-06-04",
	"dateTo": "2019-06-07"
   }
   ResponceBody:
   {
    "id": 12,
    "callerPhoneNumber": {
        "id": 3,
        "phone": "0501234567"
    },
    "recipientPhoneNumber": {
        "id": 5,
        "phone": "0731234567"
    },
    "callDt": "2019-06-06T04:20:00.000+0000",
    "duration": 500,
    "city": {
        "id": 11,
        "name": "Kyiv"
    }
   }
   ```

## Additional DB tools

  DB platform: postgres   
  DB name: dbmobile   
  Url: jdbc:postgresql://localhost:5432/dbmobile   
  Username: postgres   
  Password: postgres   

## Built With

* [SpringBoot](https://spring.io/projects/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [JUnit](https://junit.org/junit4/) - Used for testing


## Author

* **Yurii Vislobodskyi** - *Mobile Operator* - [MobileOperator](https://github.com/Yurasicv/)

