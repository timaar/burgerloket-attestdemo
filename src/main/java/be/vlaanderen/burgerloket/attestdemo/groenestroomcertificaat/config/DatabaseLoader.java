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
            for (int i = 0; i < 35; i++) {
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "83020711970", String.valueOf(i), "nl"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "83020711970", String.valueOf(i), "fr"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "83020711970", String.valueOf(i), "en"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "83020711970", String.valueOf(i), "de"));

                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "78122248790", String.valueOf(i), "nl"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "78122248790", String.valueOf(i), "fr"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "78122248790", String.valueOf(i), "en"));
                repository.save(new GroeneStroomCertificaat("Groene stroom certificaat " + (2020 - i), "78122248790", String.valueOf(i), "de"));
            }
        };
    }
}
