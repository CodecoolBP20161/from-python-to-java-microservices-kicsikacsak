**Goal**

Our goal was to create a Banner Service which allows the client to use a banner with customized content such as _advertisement_, _product placement_.
If you want to see the demo, just clone this repository and run the BannerService, and Main class, after that the advertisements are appear in http://0.0.0.0:8888/banner url.

**Summary**

We provided an API to use this microservice. 
The content is based on the data sent by the client. In this Beta-version, only the _advertisement_ function works, but it's already _superb_. 
In later versions, we plan to enable product placement suggestions based on previous purchase informations. 
**Install Steps**

You just have to clone this repository, you have to run it localy, and you can reach it in the fixed port.

**Integration**

It's pretty simple to integrate our banner service into your webshop:
- You have to create some kind of controller class, where you connect to the microservice with the specific port and url

- If you use this api locally you have to:
  -change the connectionSAMPLE.properties file name to connection.properties
  -fill the data inside like:
      - database=jdbc:postgresql://localhost/YOURDATABASE
      - user=YOURDBUSERNAME
      - password=YOURPASSWORD
  - fill the database with the init.sql file 
  - if you want to send requests with username, you have to put an APIKEY to the clients table. 

**Example requests**

To get the advertisement you have to send a post request to the api, with a Json string in the request body. This can be:
  Request:

    -empty : {} - this is a free advertisement for the codecool webpage.
    -filled with username, and apikey : "{user:user, apikey:apikey}" - this feature is not implementet yet, but here the user will get a suggestion
      based on the recent orders.
    -filled with username, cart, and apikey :"{user:user, apikey:1234, cart: [{name:iphone(String), category:mobile(String), defaultprice:100USD(String), quantity:4(Integer)}, {name:ledtv, category:tv, defaultprice:200USD, quantity:1}]}}"
      based on the recent orders.

      IMPORTANT** - if you use this api localy you have to put an API key in the clients table, with client name.

  Response:
    - If you made a good request, the response body will contain a Json string.
    - After you converted this string into a Json object there will be : {"Advertisement":HTMLBLOCK, "status":"done"}
    - You have to extract the "Advertisement" key from the object.
    - After you save this string in a variable you can fill it in the html. 
    - Thymeleaf example : <div th:utext="${banner}"></div> -- where banner is the variable which contains the HTMLBLOCK 

**Error Handling**
  - If the request body is empty, or there are no API key (if its needed) the API will return an error with 400 error code,
  and send back a Json string with the following messages:
     - "{error:Empty request body}"
     - "{error:API key required please contact the developers}")
  
 ![alt tag](http://www.dumpaday.com/wp-content/uploads/2016/04/funny-25.png)

**License**

License granted by kicsiKacsák authorizes the customer to integrate and use the service with a unique, non-transferable identifier key. No changes or modifications are allowed.

**Copyright Notice**
Copyright © 2016-2017 kicsiKacsák Dev.
Any rights not expressly granted herein are reserved.
