# TODO
1. Use Hal Way support both versions
2. Check if you can change page to pageMetadata
3. Update specification

# How to use this demo-template
This is a possible certificates provider for "[mijn burgerprofiel](https://www.vlaanderen.be/uw-overheid/mijn-burgerprofiel)". 
If you want to integrate with burgerprofiel you have to create a client that produces json as shown in this project. 
To see your certificate(s) on mijn burgerprofiel there is also some configuration needed on the Vlaanderen side but 
if you implement this demo for your use-case the integration with "mijn burgerprofiel" is a matter of hours.

# Demo
You can find a demo of the application here: [demo](http://burgerloketattestdemogroenestroomcer-env.eba-cm8dp3tp.eu-west-1.elasticbeanstalk.com/v1/certificates/83020711970)

# Related tutorials

1. [Vlaanderen certificates api - Specification](https://documentatie.burgerprofiel.vlaanderen.be/attesten/index.html#section/Certificates-API)
2. [Spring HATEOAS - Official spring documentation on latest version](https://docs.spring.io/spring-hateoas/docs/current/reference/html/)
3. [Spring HATEOAS - Embedded collection model name](https://howtodoinjava.com/spring5/hateoas/embedded-collection-name/)
4. [Spring HATEOAS - Pagination Links](https://howtodoinjava.com/spring5/hateoas/pagination-links/)
5. [Spring Security - Open id implementation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2resourceserver-jwt-jwkseturi)
6. What is HAL: [wikipedia](https://en.wikipedia.org/wiki/Hypertext_Application_Language)
7. [Internet draft of what is HAL](https://tools.ietf.org/html/draft-kelly-json-hal-08)


# Specification oddities:
1. languages are not taken correctly (see dataloader and see what comes out on mijnburgerprofiel)
2. PDF is just a stream-of-pdf isn't it better to do it with some markup like: fileid: , inhoud: bytearray
3. Pagination: In specification it is: 'page' & 'limit' in spring-default it is: 'page' & 'size' (see @PageableDefault Pageable pageable)  
