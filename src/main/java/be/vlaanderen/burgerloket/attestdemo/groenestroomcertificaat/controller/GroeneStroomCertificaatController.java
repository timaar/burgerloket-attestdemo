package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.GroeneStroomCertificaatDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AccessDeniedException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AttestNotFoundException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CommonsLog
@RestController
@RequestMapping(value = "/v1/certificates", produces = {MediaTypes.HAL_JSON_VALUE})
public class GroeneStroomCertificaatController {

    private final GroeneStroomCertficaatRepository repository;
    private final GroeneStroomCertificaatAssembler groeneStroomCertificaatAssembler;
    private final PagedResourcesAssembler<GroeneStroomCertificaat> pagedResourcesAssembler;

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_LIMIT = 10;

    GroeneStroomCertificaatController(GroeneStroomCertficaatRepository repository,
                                      GroeneStroomCertificaatAssembler groeneStroomCertificaatAssembler,
                                      PagedResourcesAssembler<GroeneStroomCertificaat> pagedResourcesAssembler) {
        this.repository = repository;
        this.groeneStroomCertificaatAssembler = groeneStroomCertificaatAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{insz}")
    public ResponseEntity<PagedModel<GroeneStroomCertificaatDTO>> findAll(@PathVariable String insz,
                                                                          @RequestParam("page") Optional<Integer> page,
                                                                          @RequestParam("limit") Optional<Integer> limit) {

        Page<GroeneStroomCertificaat> certificaten = repository.findAllByInsz(insz, PageRequest.of(page.orElse(DEFAULT_PAGE), limit.orElse(DEFAULT_LIMIT)));
        PagedModel<GroeneStroomCertificaatDTO> collModel = pagedResourcesAssembler
                .toModel(certificaten, groeneStroomCertificaatAssembler);

        return ResponseEntity.ok(collModel);
    }

    @GetMapping("/{insz}/{id}")
    public ResponseEntity<EntityModel<GroeneStroomCertificaatDTO>> findOne(@PathVariable long id,
                                                                           @PathVariable String insz) {
        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findById(id);

        GroeneStroomCertificaat certificaat = optionalGroenStroomCertificaat.orElseThrow(() -> new AttestNotFoundException("Could not find the certificate you are looking for."));
        if (!certificaat.getInsz().equals(insz)) {
            throw new AccessDeniedException("Access Denied");
        }

        return ResponseEntity.ok(EntityModel.of(groeneStroomCertificaatAssembler.toModel(certificaat)));
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
