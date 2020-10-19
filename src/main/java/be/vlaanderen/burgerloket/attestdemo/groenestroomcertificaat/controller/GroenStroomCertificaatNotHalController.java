package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatNotHalAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.GroeneStroomCertificaatNotHalDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.GroeneStroomCertificaatNotHalPagedDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AccessDeniedException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AttestNotFoundException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security.JWTSecurityService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CommonsLog
@RestController
@RequestMapping(value = "/v1/certificates/nothal/", produces = {"application/json"})
public class GroenStroomCertificaatNotHalController {

    private final GroeneStroomCertficaatRepository repository;
    private final GroeneStroomCertificaatNotHalAssembler groeneStroomCertificaatNotHalAssembler;
    private final JWTSecurityService jwtSecurityService;

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_LIMIT = 10;


    GroenStroomCertificaatNotHalController(GroeneStroomCertficaatRepository repository,
                                           GroeneStroomCertificaatNotHalAssembler groeneStroomCertificaatNotHalAssembler,
                                           JWTSecurityService jwtSecurityService) {
        this.repository = repository;
        this.groeneStroomCertificaatNotHalAssembler = groeneStroomCertificaatNotHalAssembler;
        this.jwtSecurityService = jwtSecurityService;
    }

    @GetMapping("/{insz}")
    public ResponseEntity<GroeneStroomCertificaatNotHalPagedDTO> findAll(@PathVariable String insz,
                                                                         @RequestParam("page") Optional<Integer> page,
                                                                         @RequestParam("limit") Optional<Integer> limit) {

        Page<GroeneStroomCertificaat> certificaten = repository.findAllByInsz(insz, PageRequest.of(page.orElse(DEFAULT_PAGE), limit.orElse(DEFAULT_LIMIT)));

        return ResponseEntity.ok(groeneStroomCertificaatNotHalAssembler.toModel(certificaten, page.orElse(DEFAULT_PAGE), limit.orElse(DEFAULT_LIMIT), "/v1/certificates/nothal/" + insz));
    }

    @GetMapping("/{insz}/{id}")
    public ResponseEntity<GroeneStroomCertificaatNotHalDTO> findOne(@PathVariable long id,
                                                                    @PathVariable String insz) {

        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findById(id);

        GroeneStroomCertificaat certificaat = optionalGroenStroomCertificaat.orElseThrow(() -> new AttestNotFoundException("Could not find the certificate you are looking for."));
        if (certificaat.getInsz().equals(insz)) {
            throw new AccessDeniedException("Access Denied");
        }

        return  ResponseEntity.ok(groeneStroomCertificaatNotHalAssembler.toModel(certificaat, "/v1/certificates/nothal/" + insz + "/" + id));
    }

    @GetMapping("/{insz}/{jaar}/{taal}/download")
    public ResponseEntity<Resource> download(@PathVariable String insz,
                                             @PathVariable String jaar,
                                             @PathVariable String taal) {

        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findByInszAndJaartalAndTaal(insz, jaar, taal);
        GroeneStroomCertificaat certificaat = optionalGroenStroomCertificaat.orElseThrow(() ->
                new AttestNotFoundException("Could not find the certificate you are looking for."));

        return ResponseEntity.ok().body(new ClassPathResource("dummy.pdf"));

        // The mijnburgerprofiel requires a resource instead of what was specified in the specs
//        try {
//            log.debug("In real application get pdf from database or other webservice.");
//            byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/dummy.pdf"));
//            GroeneStroomCertificaatPdfDTO verlofAntwoordDto = GroeneStroomCertificaatPdfDTO.builder()
//                    .attest(Base64.getEncoder().encodeToString(bytes))
//                    .contentType("application/pdf")
//                    .build();
//
//            return ResponseEntity.ok().body(verlofAntwoordDto);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
    }
}
