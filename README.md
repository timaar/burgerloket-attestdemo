# How to use this demo-template
This is a possible certificates provider for "[mijn burgerprofiel](https://www.vlaanderen.be/uw-overheid/mijn-burgerprofiel)". 
If you want to integrate with burgerprofiel you have to create a client that produces json as shown in this project. 
To see your certificate(s) on mijn burgerprofiel there is also some configuration needed on the Vlaanderen side but 
if you implement this demo for your use-case the integration with "mijn burgerprofiel" is a matter of hours.

# Demo
You can find a demo of the application here: [demo](http://burgerloketattestdemogroenestroomcer-env.eba-cm8dp3tp.eu-west-1.elasticbeanstalk.com/v1/certificates/83020711970)
1. Get a bearer token as authorization: [get bearertoken](https://beta.openid.burgerprofiel.dev-vlaanderen.be/op/token?code=83020711970.Bar.Foo.80689076-8c4a-4bef-abc4-82805e17988d&grant_type=authorization_code&standardAudiences=true)
2. Copy content of id_token
3. Use Postman (or any other REST tool) to query: [http://burgerloketattestdemogroenestroomcer-env.eba-cm8dp3tp.eu-west-1.elasticbeanstalk.com/v1/certificates/83020711970?page=0&limit=100](http://burgerloketattestdemogroenestroomcer-env.eba-cm8dp3tp.eu-west-1.elasticbeanstalk.com/v1/certificates/83020711970?page=0&limit=100)
4. Use as Bearer token the one you copied in step 2. You should be able to make request to this service.

#Build
- mvn clean package
- cp the jar in target to your favorite cloud

# Related tutorials
1. [Vlaanderen certificates api - Specification](https://documentatie.burgerprofiel.vlaanderen.be/attesten/index.html#section/Certificates-API)
2. [Spring HATEOAS - Official spring documentation on latest version](https://docs.spring.io/spring-hateoas/docs/current/reference/html/)
3. [Spring HATEOAS - Embedded collection model name](https://howtodoinjava.com/spring5/hateoas/embedded-collection-name/)
4. [Spring HATEOAS - Pagination Links](https://howtodoinjava.com/spring5/hateoas/pagination-links/)
5. [Spring Security - Open id implementation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2resourceserver-jwt-jwkseturi)
6. What is HAL: [wikipedia](https://en.wikipedia.org/wiki/Hypertext_Application_Language)
7. [Internet draft of what is HAL](https://tools.ietf.org/html/draft-kelly-json-hal-08)


# Notified specification oddities:
1. languages are not taken correctly (see dataloader and see what comes out on mijnburgerprofiel)
2. PDF is just a stream-of-pdf isn't it better to do it with some markup like: fileid: , inhoud: bytearray
3. Pagination: In previous specification it is: 'page' & 'limit' in spring-default it is: 'page' & 'size' (see @PageableDefault Pageable pageable) and the latest api specifaction it is: offset and size.  


# Request came to use pageMetaData instead of page
Problem: spring-hatoas uses 'page' in the response of a HAL request but 'Mijn burgerprofiel' asked if it is possible to change to their 'pageMetaData'.
I didn't found an easy configurable solution. So my solution was to change the code of the library and use my custom lib instead. Here is how:

1. Check if you can change page to pageMetadata for hal (done changed the library of spring)
    - to test you can use:
        
        		<dependency>
        			<groupId>org.springframework.hateoas</groupId>
        			<artifactId>spring-hateoas</artifactId>
        			<version>1.2.0.Custom-aiv-SNAPSHOT</version>
        		</dependency>
      but first you need to pull the code from spring and change this class and update the pom to the appropriate version you want:
      ![diagram](changePageToPageMetaData.jpg)
