package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GroeneStroomCertificaat {

    @Id @GeneratedValue private Long id;
    private String insz;
    private String jaartal;
    private String naam;
    private String taal; // mogelijkheden die worden geInterpreteerd nl, fr, de en en

    public GroeneStroomCertificaat(String naam, String insz, String jaartal, String taal) {
        this.naam = naam;
        this.insz = insz;
        this.jaartal = jaartal;
        this.taal = taal;
    }



}
