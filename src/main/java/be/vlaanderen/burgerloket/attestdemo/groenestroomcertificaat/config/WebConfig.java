package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // To be able to use Pageable on the controllers
        HateoasPageableHandlerMethodArgumentResolver myResolver = new HateoasPageableHandlerMethodArgumentResolver();
        myResolver.setMaxPageSize(100);
        argumentResolvers.add(myResolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}
