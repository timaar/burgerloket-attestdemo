package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.config;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner init(GroeneStroomCertficaatRepository repository) {

        return args -> {
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2019", "83020711970", "2019", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2018", "83020711970", "2018", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2017", "83020711970", "2017", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2016", "83020711970", "2016", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2015", "83020711970", "2015", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2014", "83020711970", "2014", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2013", "83020711970", "2013", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2012", "83020711970", "2012", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2011", "83020711970", "2011", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2010", "83020711970", "2010", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2009", "83020711970", "2009", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2008", "83020711970", "2008", "nl"));

            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2019", "78122248790", "2019", "nl"));
            repository.save(new GroeneStroomCertificaat("Groene stroom certificaat 2018", "78122248790", "2018", "nl"));
        };
    }
}
