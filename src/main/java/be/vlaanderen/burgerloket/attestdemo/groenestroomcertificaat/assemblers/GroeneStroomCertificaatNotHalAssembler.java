package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.GroeneStroomCertificaatNotHalDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.GroeneStroomCertificaatNotHalPagedDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.LinkNotHalDTO;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.dto.nothal.PageMetadata;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CommonsLog
@Component
public class GroeneStroomCertificaatNotHalAssembler {

    /**
     * {
     * "certificates": [
     * {
     * "id": "85144567-7043-4469-9e79-279f4eb31e27",
     * "language": "nl",
     * "name": "Dienstencheques 2019",
     * "year": 2019,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl/download"
     * }
     * ]
     * },
     * {
     * "id": "85144567-7043-4469-9e79-279f4eb31e27",
     * "language": "en",
     * "name": "Dienstencheques 2019",
     * "year": 2019,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/en"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/en/download"
     * }
     * ]
     * },
     * {
     * "id": "8a4faf91-300e-46f0-8dad-0e1e572dbca1",
     * "language": "nl",
     * "name": "Sportkamp Tennis",
     * "community": 23088,
     * "year": 2018,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8a4faf91-300e-46f0-8dad-0e1e572dbca1"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8a4faf91-300e-46f0-8dad-0e1e572dbca1/download"
     * }
     * ]
     * },
     * {
     * "id": "25da5b3c-4de4-457c-a803-cea5cafbfff5",
     * "language": "nl",
     * "name": "Dienstencheques 2018",
     * "year": 2018,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/25da5b3c-4de4-457c-a803-cea5cafbfff5"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/25da5b3c-4de4-457c-a803-cea5cafbfff5/download"
     * }
     * ]
     * },
     * {
     * "id": "8e3405ea-ef23-4d8a-b5b4-9cb645ac312b",
     * "language": "nl",
     * "name": "Deelname opleiding recreatiemedewerker",
     * "year": 2019,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8e3405ea-ef23-4d8a-b5b4-9cb645ac312b"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8e3405ea-ef23-4d8a-b5b4-9cb645ac312b/download"
     * }
     * ]
     * },
     * {
     * "id": "8df1a0b0-1278-46a7-a3dc-cce7f593d02c",
     * "language": "nl",
     * "name": "Sportkamp tennis",
     * "community": 11002,
     * "year": 2012,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8df1a0b0-1278-46a7-a3dc-cce7f593d02c"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/8df1a0b0-1278-46a7-a3dc-cce7f593d02c/download"
     * }
     * ]
     * },
     * {
     * "id": "34492c86-7bf8-4d96-9e61-e3c2f51d172a",
     * "language": "nl",
     * "name": "Dienstencheques 2015",
     * "year": 2015,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/34492c86-7bf8-4d96-9e61-e3c2f51d172a"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/34492c86-7bf8-4d96-9e61-e3c2f51d172a/download"
     * }
     * ]
     * },
     * {
     * "id": "a681c382-8e99-4757-9fdb-487b9b6aeb78",
     * "language": "nl",
     * "name": "Trainersopleiding voetbal",
     * "community": 71053,
     * "year": 2013,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/a681c382-8e99-4757-9fdb-487b9b6aeb78"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/a681c382-8e99-4757-9fdb-487b9b6aeb78/download"
     * }
     * ]
     * },
     * {
     * "id": "6973ab14-15c6-4ee7-944c-9d1162d9de71",
     * "language": "nl",
     * "name": "Dienstencheques 2014",
     * "year": 2014,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/6973ab14-15c6-4ee7-944c-9d1162d9de71"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/6973ab14-15c6-4ee7-944c-9d1162d9de71/download"
     * }
     * ]
     * },
     * {
     * "id": "d61fdf2e-8099-4757-93d9-2709f1ce1ae2",
     * "language": "nl",
     * "name": "Vlaamse kinderbijslag",
     * "year": 2019,
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/d61fdf2e-8099-4757-93d9-2709f1ce1ae2"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/d61fdf2e-8099-4757-93d9-2709f1ce1ae2/download"
     * }
     * ]
     * }
     * ],
     * "pageMetadata": {
     * "number": 1,
     * "size": 10,
     * "totalElements": 40,
     * "totalPages": 4
     * },
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=0"
     * },
     * {
     * "rel": "next",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=1"
     * },
     * {
     * "rel": "start",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=0"
     * },
     * {
     * "rel": "last",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302?limit=10&page=3"
     * }
     * ]
     * }
     */
    public GroeneStroomCertificaatNotHalPagedDTO toModel(Page<GroeneStroomCertificaat> certificaten, Integer page, Integer limit) {
        return GroeneStroomCertificaatNotHalPagedDTO.builder()
                .certificates(getCertificaten(certificaten.getContent()))
                .links(mapListLinks(certificaten, page, limit))
                .pageMetadata(mapPageMetadata(certificaten))
                .build();
    }

    private PageMetadata mapPageMetadata(Page<GroeneStroomCertificaat> certificaten) {
        return PageMetadata.builder()
                .number(certificaten.getNumber())
                .size(certificaten.getSize())
                .totalElements(certificaten.getTotalElements())
                .totalPages(certificaten.getTotalPages())
                .build();
    }

    private List<LinkNotHalDTO> mapListLinks(Page<GroeneStroomCertificaat> certificaten, Integer page, Integer limit) {
        List<LinkNotHalDTO> links = new ArrayList<>();
        links.add(getSelfListLink(page, limit));
        links.add(getNextListLink(certificaten, page, limit));
        links.add(getStartListLink(limit));
        links.add(getLastListLink(certificaten, limit));
        return links;
    }

    private LinkNotHalDTO getSelfListLink(Integer currentPage, Integer limit) {
        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + getInsz() + "?limit=" + limit + "&page=" + currentPage)
                .rel("self")
                .build();
    }

    private LinkNotHalDTO getNextListLink(Page<GroeneStroomCertificaat> certificaten, Integer currentPage, Integer limit) {
        Integer nextPage = currentPage >= certificaten.getTotalPages() - 1 ? currentPage : currentPage + 1;

        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + getInsz() + "?limit=" + limit + "&page=" + nextPage)
                .rel("next")
                .build();
    }

    private LinkNotHalDTO getStartListLink(Integer limit) {
        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + getInsz() + "?limit=" + limit + "&page=" + 0)
                .rel("start")
                .build();
    }

    private LinkNotHalDTO getLastListLink(Page<GroeneStroomCertificaat> certificaten, Integer limit) {
        Integer page = certificaten.getTotalPages();

        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + getInsz() + "?limit=" + limit + "&page=" + page)
                .rel("last")
                .build();
    }

    private List<GroeneStroomCertificaatNotHalDTO> getCertificaten(List<GroeneStroomCertificaat> content) {
        return content.stream().map(certificaat -> map(certificaat)).collect(Collectors.toList());
    }

    private GroeneStroomCertificaatNotHalDTO map(GroeneStroomCertificaat certificaat) {
        return GroeneStroomCertificaatNotHalDTO.builder()
                .id(certificaat.getId())
                .language(certificaat.getTaal())
                .name(certificaat.getNaam())
                .year(certificaat.getJaartal())
                .links(mapLinks(certificaat))
                .build();
    }

    private List<LinkNotHalDTO> mapLinks(GroeneStroomCertificaat certificaat) {
        List<LinkNotHalDTO> links = new ArrayList<>();
        links.add(getSelfCertificaatLink(certificaat));
        links.add(getDownloadCertificaatLink(certificaat));
        return links;
    }

    private LinkNotHalDTO getDownloadCertificaatLink(GroeneStroomCertificaat certificaat) {
        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + certificaat.getInsz() + "/" + certificaat.getJaartal() + "/" + certificaat.getTaal() + "/download")
                .rel("download")
                .build();
    }

    private LinkNotHalDTO getSelfCertificaatLink(GroeneStroomCertificaat certificaat) {
        return LinkNotHalDTO.builder()
                .href(getBaseUri() + "/v1/certificates/nothal/" + certificaat.getInsz() + "/" + certificaat.getId())
                .rel("self")
                .build();
    }

    /**
     * {
     * "id": "85144567-7043-4469-9e79-279f4eb31e27",
     * "language": "nl",
     * "name": "certificate-1234",
     * "links": [
     * {
     * "rel": "self",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl"
     * },
     * {
     * "rel": "download",
     * "href": "https://burgerprofiel.vlaanderen.be/v1/certificates/90061638302/85144567-7043-4469-9e79-279f4eb31e27/nl/download"
     * }
     * ]
     * }
     */
    public GroeneStroomCertificaatNotHalDTO toModel(GroeneStroomCertificaat certificaat) {
        return map(certificaat);
    }

    private String getBaseUri() {
        String scheme = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getScheme();
        String host = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getHost();
        int port = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPort();
        return (scheme != null ? scheme + "://" : "") + (host != null ? host : "") + (port != -1 ? ":" + port : "");
    }

    private String getInsz() {
        String path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toString();

        if (path != null) {
            return Arrays.stream(path.split("/"))
                    .filter(s -> Pattern.compile("[\\d]{11}$").asPredicate().test(s))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
