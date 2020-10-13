package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "certificaten", itemRelation = "certificaat")
public class GroeneStroomCertificaatDTO extends RepresentationModel<GroeneStroomCertificaatDTO> {

    private Long id;
    //private String insz;
    private String year;
    private String language;
    private String name;
}
