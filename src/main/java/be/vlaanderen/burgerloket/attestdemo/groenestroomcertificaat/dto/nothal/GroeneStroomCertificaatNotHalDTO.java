package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *   "id": "85144567-7043-4469-9e79-279f4eb31e27",
 *   "language": "nl",
 *   "name": "certificate-1234",
 *   "year": "2020"
 *   "links": [
 *     {
 *       "rel": "self",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl"
 *     },
 *     {
 *       "rel": "download",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl/download"
 *     }
 *   ]
 * }
 *
 */

/**
 * Preferably use HAL implementations to avoid errors in generating
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroeneStroomCertificaatNotHalDTO {
    private Long id;
    //private String insz;
    private String year;
    private String language;
    private String name;

    private List<LinkNotHalDTO> links = new ArrayList<>();
}
