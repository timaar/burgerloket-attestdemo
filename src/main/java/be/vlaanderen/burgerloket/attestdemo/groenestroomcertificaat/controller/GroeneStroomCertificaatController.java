package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.GroeneStroomCertificaatDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security.JWTSecurityService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        // TODO Verify JWT Token in the security service

        Page<GroeneStroomCertificaat> certificaten = repository.findAllByInsz(insz, pageable);
        PagedModel<GroeneStroomCertificaatDTO> collModel = pagedResourcesAssembler
                .toModel(certificaten, groeneStroomCertificaatAssembler);

        return ResponseEntity.ok(collModel);
    }

    @GetMapping("/{insz}/{id}")
    public ResponseEntity<EntityModel<GroeneStroomCertificaat>> findOne(@PathVariable long id,
                                                                        @PathVariable String insz) {

        // TODO Verify JWT Token in the security service

        Optional<GroeneStroomCertificaat> optionalGroenStroomCertificaat = repository.findById(id);

        return optionalGroenStroomCertificaat
                .map(certificaat -> EntityModel.of(certificaat,
                        linkTo(methodOn(GroeneStroomCertificaatController.class).findOne(certificaat.getId(), insz))
                                .withSelfRel(),
                        linkTo(methodOn(GroeneStroomCertificaatController.class).download(insz, certificaat.getJaartal(), certificaat.getTaal()))
                                .withRel("download")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{insz}/{jaar}/{taal}/download")
    public ResponseEntity<Resource> download(@PathVariable String insz,
                                             @PathVariable String jaar,
                                             @PathVariable String taal) {

        // TODO Verify JWT Token in the security service

        log.debug("In real application get pdf from database or other webservice.");
        ClassPathResource resource = new ClassPathResource("dummy.pdf");
        if (resource != null) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}