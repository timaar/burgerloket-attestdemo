package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Preferably use HAL implementations to avoid errors in generating
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkNotHalDTO {
    private String rel;
    private String href;
}
