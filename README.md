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
4. [Spring Security - Open id implementation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2resourceserver-jwt-jwkseturi)

# Discuss in team

1. Pagemetadata: In specification it is: 'pageMetadata' in spring-default it is: 'page'
2. links: In specification it is: 'links' in hateoas specification it is: '_links'
3. certificates: In specification it is: 'certificates' in hateoas specification it is: '_embedded/certificates'

# Solved to match specification but still discuss in team

1. Pagination: In specification it is: 'page' & 'limit' in spring-default it is: 'page' & 'size' (see @PageableDefault Pageable pageable)
