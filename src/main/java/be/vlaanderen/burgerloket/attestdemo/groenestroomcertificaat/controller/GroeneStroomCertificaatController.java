package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.GroeneStroomCertificaatDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.GroeneStroomCertificaatPdfDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AttestNotFoundException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security.JWTSecurityService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@CommonsLog
@RestController
@RequestMapping(value = "/v1/certificates", produces = {MediaTypes.HAL_JSON_VALUE})
public class GroeneStroomCertificaatController {

    private final GroeneStroomCertficaatRepository repository;
    private final GroeneStroomCertificaatAssembler groeneStroomCertificaatAssembler;
    private final PagedResourcesAssembler<GroeneStroomCertificaat> pagedResourcesAssembler;
    private final JWTSecurityService jwtSecurityService;

    GroeneStroomCertificaatController(GroeneStroomCertficaatRepository repository,
                                      GroeneStroomCertificaatAssembler groeneStroomCertificaatAssembler,
                                      PagedResourcesAssembler<GroeneStroomCertificaat> pagedResourcesAssembler,
                                      JWTSecurityService jwtSecurityService) {
        this.repository = repository;
        this.groeneStroomCertificaatAssembler = groeneStroomCertificaatAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jwtSecurityService = jwtSecurityService;
    }

    @GetMapping("/{insz}")
    public ResponseEntity<PagedModel<GroeneStroomCertificaatDTO>> findAll(@PathVariable String insz,
                                                                          @PageableDefault Pageable pageable) {

        // TODO Verify JWT Token in the security service and throw AccessDeniedException if not valid

        Page<GroeneStroomCertificaat> certificaten = repository.findAllByInsz(insz, pageable);
        PagedModel<GroeneStroomCertificaatDTO> collModel = pagedResourcesAssembler
                .toModel(certificaten, groeneStroomCertificaatAssembler);

        return ResponseEntity.ok(collModel);
    }

    @GetMapping("/{insz}/{id}")
    public ResponseEntity<EntityModel<GroeneStroomCertificaatDTO>> findOne(@PathVariable long id,
                                                                           @PathVariable String insz) {

        // TODO Verify JWT Token in the security service and throw AccessDeniedException if not valid

        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findById(id);

        // TODO validate if the user has the right to see this cert insz from path and from certificate should be the same

        return optionalGroenStroomCertificaat
                .map(certificaat -> EntityModel.of(groeneStroomCertificaatAssembler.toModel(certificaat)))
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new AttestNotFoundException("Could not find the certificate you are looking for."));
    }

    @GetMapping("/{insz}/{jaar}/{taal}/download")
    public ResponseEntity<GroeneStroomCertificaatPdfDTO> download(@PathVariable String insz,
                                             @PathVariable String jaar,
                                             @PathVariable String taal) {

        // TODO Verify JWT Token in the security service and throw AccessDeniedException if not valid
        // TODO validate if the user has the right to see this cert insz from path and from certificate should be the same

        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findByInszAndJaartalAndTaal(insz, jaar, taal);
        GroeneStroomCertificaat certificaat = optionalGroenStroomCertificaat.orElseThrow(() ->
                new AttestNotFoundException("Could not find the certificate you are looking for."));

        try {
            log.debug("In real application get pdf from database or other webservice.");
            byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/dummy.pdf"));
            GroeneStroomCertificaatPdfDTO verlofAntwoordDto = GroeneStroomCertificaatPdfDTO.builder()
                    .attest(Base64.getEncoder().encodeToString(bytes))
                    .contentType("application/pdf")
                    .build();

            return ResponseEntity.ok().body(verlofAntwoordDto);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
