package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller.GroeneStroomCertificaatController;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.GroeneStroomCertificaatDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CommonsLog
@Component
public class GroeneStroomCertificaatAssembler
        extends RepresentationModelAssemblerSupport<GroeneStroomCertificaat, GroeneStroomCertificaatDTO> {

    public GroeneStroomCertificaatAssembler(){
        super(GroeneStroomCertificaatController.class, GroeneStroomCertificaatDTO.class);
    }

    @Override
    public GroeneStroomCertificaatDTO toModel(GroeneStroomCertificaat entity) {
        GroeneStroomCertificaatDTO dto = instantiateModel(entity);

        log.debug("mapping");
        dto.setId(entity.getId());
        dto.setName(entity.getNaam());
        dto.setYear(entity.getJaartal());
        dto.setLanguage(entity.getTaal());

        log.debug("provide default links");
        dto.add(linkTo(methodOn(GroeneStroomCertificaatController.class)
                .findOne(entity.getId(), entity.getInsz()))
                .withSelfRel());
        dto.add(linkTo(methodOn(GroeneStroomCertificaatController.class).download(entity.getInsz(), entity.getJaartal(), entity.getTaal()))
                .withRel("download"));
        return dto;
    }

    @Override
    public CollectionModel<GroeneStroomCertificaatDTO> toCollectionModel(Iterable<? extends GroeneStroomCertificaat> entities) {
        CollectionModel<GroeneStroomCertificaatDTO> dtos = super.toCollectionModel(entities);

        final String insz = entities.iterator().next().getInsz();

        log.debug("provide default link");
        dtos.add(linkTo(methodOn(GroeneStroomCertificaatController.class)
                .findAll(insz, PageRequest.of(0, 10)))
                .withSelfRel());

        return dtos;
    }
}
