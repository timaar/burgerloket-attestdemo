package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface GroeneStroomCertficaatRepository extends PagingAndSortingRepository<GroeneStroomCertificaat, Long> {

    Page<GroeneStroomCertificaat> findAllByInsz(String insz, Pageable pageable);

    Optional<GroeneStroomCertificaat> findByInszAndJaartalAndTaal(String insz, String jaartal, String taal);
}
