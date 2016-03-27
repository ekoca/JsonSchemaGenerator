JsonSchemaGenerator
===============

Json schema generator from POJOs using Jackson 2.4.0 (This also supports jackson v1.8.3 library)

To run project:

* Clone this project
* Run the main method of JsonSchemaGenerator class
* Take a look at folders called "schema-responses and schema-jsons"
* See the json schemas for input classes

Hint:
* Use jenkins to build schemas

Sample json schemas:

<pre>
-------------------------- JsonOperation.json --------------------------
{
  "type" : "object",
  "properties" : {
    "request" : {
      "type" : "object",
      "properties" : {
        "requestType" : {
          "type" : "string"
        },
        "data" : {
          "type" : "object",
          "properties" : {
            "username" : {
              "type" : "string"
            },
            "email" : {
              "type" : "string"
            },
            "password" : {
              "type" : "string"
            },
            "birthday" : {
              "type" : "string"
            },
            "coinsPackage" : {
              "type" : "string"
            },
            "coins" : {
              "type" : "string"
            },
            "transactionId" : {
              "type" : "string"
            },
            "isLoggedIn" : {
              "type" : "boolean",
              "required" : true
            }
          }
        }
      }
    },
    "response" : {
      "type" : "object",
      "properties" : {
        "requestType" : {
          "type" : "string"
        },
        "data" : {
          "type" : "object",
          "properties" : {
            "status" : {
              "type" : "string",
              "enum" : [ "OK", "ERROR" ]
            },
            "errorCode" : {
              "type" : "string",
              "enum" : [ "ERROR_INVALID_LOGIN", "ERROR_USERNAME_ALREADY_TAKEN", "ERROR_EMAIL_ALREADY_TAKEN" ]
            },
            "expiry" : {
              "type" : "string"
            },
            "coins" : {
              "type" : "integer"
            },
            "email" : {
              "type" : "string"
            },
            "birthday" : {
              "type" : "string"
            },
            "pictureUrl" : {
              "type" : "string"
            }
          }
        }
      }
    }
  }
}
-------------------------- Person.json --------------------------
{
  "type" : "object",
  "properties" : {
    "name" : {
      "type" : "string"
    },
    "age" : {
      "type" : "integer"
    }
  }
}
-------------------------- StatusResponse.json --------------------------
{
  "type" : "object",
  "properties" : {
    "status" : {
      "type" : "integer"
    },
    "message" : {
      "type" : "string"
    }
  }
}
</pre>
