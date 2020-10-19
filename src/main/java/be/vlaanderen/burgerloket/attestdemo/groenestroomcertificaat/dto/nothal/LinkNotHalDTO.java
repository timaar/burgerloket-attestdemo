package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkNotHalDTO {
    private String rel;
    private String href;
}
