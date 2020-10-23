package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * {
 *   "certificates": [
 *     {
 *       "id": "85144567-7043-4469-9e79-279f4eb31e27",
 *       "language": "nl",
 *       "name": "Dienstencheques 2019",
 *       "year": 2019,
 *       "links": [
 *         {
 *           "rel": "self",
 *           "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl"
 *         },
 *         {
 *           "rel": "download",
 *           "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl/download"
 *         }
 *       ]
 *     },...,
 *   "pageMetadata": {
 *     "number": 1,
 *     "size": 10,
 *     "totalElements": 40,
 *     "totalPages": 4
 *   },
 *   "links": [
 *     {
 *       "rel": "self",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=0"
 *     },
 *     {
 *       "rel": "next",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=1"
 *     },
 *     {
 *       "rel": "start",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=0"
 *     },
 *     {
 *       "rel": "last",
 *       "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=3"
 *     }
 *   ]
 * }
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated // USE HAL
public class GroeneStroomCertificaatNotHalPagedDTO {
    private List<GroeneStroomCertificaatNotHalDTO> certificates = new ArrayList<>();
    private PageMetadata pageMetadata;
    private List<LinkNotHalDTO> links = new ArrayList<>();
}
