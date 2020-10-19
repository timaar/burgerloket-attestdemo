# TODO
1. security using the spec

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

# Discuss in team

In short the attesten-service in production is not using the HAL representation but something that looks like it. However in the documentation mijnburgerprofiel claim they require HAL.
In beta-environment of mijn burgerprofiel. I changed the implementation that it also handles "official HAL".
 
1. Pagemetadata: In specification it is: 'pageMetadata' in spring-default it is: 'page'
2. links: In specification it is: 'links' in hateoas specification it is: '_links'
3. certificates: In specification it is: 'certificates' in hateoas specification it is: '_embedded/certificates'

not hal related:
1. languages are not taken correctly (see dataloader and see what comes out on mijnburgerprofiel)
2. PDF is just a stream isn't it better to do it with some content see code in comment  

# What's next?

* My suggestion is to either make attesten-service HAL ready (already done in feature branch) and changing the specification or not call the specification a HAL specification.
* The other solution is to change the specification and not name it HAL and accept regular json other then json+hal. 
* In any case we will need to update the documentation whatever option we choose.
* Clean up the code in the feature branch: create a new type of Bron: EXTHAL or something so we don't break code for "meal vouchers"

# Solved to match specification but still discuss in team better to use enterprise standards...

1. Pagination: In specification it is: 'page' & 'limit' in spring-default it is: 'page' & 'size' (see @PageableDefault Pageable pageable)
