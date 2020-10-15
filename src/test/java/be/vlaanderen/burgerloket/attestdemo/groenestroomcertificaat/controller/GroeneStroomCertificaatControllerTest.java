package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AccessDeniedException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AttestNotFoundException;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security.JWTSecurityService;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(GroeneStroomCertificaatController.class)
@Import({ GroeneStroomCertificaatAssembler.class, JWTSecurityService.class})
@CommonsLog
public class GroeneStroomCertificaatControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroeneStroomCertficaatRepository repository;

    @Test
    public void findAllShouldFetchAHalDocument() throws Exception {

        Page<GroeneStroomCertificaat> page = new PageImpl<>( Arrays.asList(
                new GroeneStroomCertificaat(1l, "83020711970", "2019","Groene stroom certificaat 2019", "nl"),
                new GroeneStroomCertificaat(2l, "83020711970", "2018", "Groene stroom certificaat 2018", "nl")
        ));

        given(repository.findAllByInsz(any(), any())).willReturn(page);

        mvc.perform(get("/v1/certificates/83020711970")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.certificates[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.certificates[0].year", is("2019")))
                .andExpect(jsonPath("$._embedded.certificates[0].language", is("nl")))
                .andExpect(jsonPath("$._embedded.certificates[0].name", is("Groene stroom certificaat 2019")))
                .andExpect(jsonPath("$._embedded.certificates[0]._links.self.href", is("http://localhost/v1/certificates/83020711970/1")))

                .andExpect(jsonPath("$._embedded.certificates[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.certificates[1].year", is("2018")))
                .andExpect(jsonPath("$._embedded.certificates[1].language", is("nl")))
                .andExpect(jsonPath("$._embedded.certificates[1].name", is("Groene stroom certificaat 2018")))
                .andExpect(jsonPath("$._embedded.certificates[1]._links.self.href", is("http://localhost/v1/certificates/83020711970/2")))

                .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/certificates/83020711970")))

                .andExpect(jsonPath("$.page.size", is(2)))
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andReturn();
    }

    @Test
    public void findAllPagingTest() throws Exception {

        Page<GroeneStroomCertificaat> page = new PageImpl<>( Arrays.asList(
                new GroeneStroomCertificaat(1l, "83020711970", "2019","Groene stroom certificaat 2019", "nl"),
                new GroeneStroomCertificaat(2l, "83020711970", "2018", "Groene stroom certificaat 2018", "nl")
        ));

        given(repository.findAllByInsz(any(), any())).willReturn(page);

        mvc.perform(get("/v1/certificates/83020711970?page=0&limit=2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.certificates[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.certificates[0].year", is("2019")))
                .andExpect(jsonPath("$._embedded.certificates[0].language", is("nl")))
                .andExpect(jsonPath("$._embedded.certificates[0].name", is("Groene stroom certificaat 2019")))
                .andExpect(jsonPath("$._embedded.certificates[0]._links.self.href", is("http://localhost/v1/certificates/83020711970/1")))

                .andExpect(jsonPath("$._embedded.certificates[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.certificates[1].year", is("2018")))
                .andExpect(jsonPath("$._embedded.certificates[1].language", is("nl")))
                .andExpect(jsonPath("$._embedded.certificates[1].name", is("Groene stroom certificaat 2018")))
                .andExpect(jsonPath("$._embedded.certificates[1]._links.self.href", is("http://localhost/v1/certificates/83020711970/2")))

                .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/certificates/83020711970?page=0&limit=2")))

                .andExpect(jsonPath("$.page.size", is(2)))
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andReturn();
    }

    @Test
    public void findAllNothingFoundShouldNotThrow404() throws Exception {

        Page<GroeneStroomCertificaat> page = new PageImpl<>( Collections.emptyList());

        given(repository.findAllByInsz(any(), any())).willReturn(page);

        mvc.perform(get("/v1/certificates/83020711970")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/certificates/83020711970")))

                .andExpect(jsonPath("$.page.size", is(0)))
                .andExpect(jsonPath("$.page.totalElements", is(0)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andReturn();
    }

    @Test
    public void findOneShouldFetchAHalDocument() throws Exception {
        given(repository.findById(2l)).
                willReturn(Optional.of(new GroeneStroomCertificaat(2l, "83020711970", "2018", "Groene stroom certificaat 2018", "nl")));

        mvc.perform(get("/v1/certificates/83020711970/2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.year", is("2018")))
                .andExpect(jsonPath("$.language", is("nl")))
                .andExpect(jsonPath("$.name", is("Groene stroom certificaat 2018")))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/certificates/83020711970/2")))
                .andExpect(jsonPath("$._links.download.href", is("http://localhost/v1/certificates/83020711970/2018/nl/download")))

                .andReturn();
    }

    @Test
    public void findOneNotFound() throws Exception {
        mvc.perform(get("/v1/certificates/83020711970/2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.title", is("An error occurred!")))
                .andExpect(jsonPath("$.detail", is("Could not find the certificate you are looking for.")))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))

                .andReturn();
    }

    @Test
    public void download() throws Exception {

        given(repository.findByInszAndJaartalAndTaal(any(), any(), any())).
                willReturn(Optional.of(new GroeneStroomCertificaat(2l, "83020711970", "2018", "Groene stroom certificaat 2018", "nl")));

        mvc.perform(get("/v1/certificates/83020711970/2018/nl/download")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.contentType", is("application/pdf")))
                .andExpect(jsonPath("$.attest", anything()))

                .andReturn();
    }

    @Test
    public void downloadNotFound() throws Exception {

        mvc.perform(get("/v1/certificates/83020711970/2018/nl/download")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.title", is("An error occurred!")))
                .andExpect(jsonPath("$.detail", is("Could not find the certificate you are looking for.")))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))

                .andReturn();
    }

    @Test
    public void testErrorHandlingNotFound() throws Exception {
        given(repository.findById(2l)).willThrow(new AttestNotFoundException("Niet gevonden message"));

        mvc.perform(get("/v1/certificates/83020711970/2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.title", is("An error occurred!")))
                .andExpect(jsonPath("$.detail", is("Niet gevonden message")))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))

                .andReturn();
    }

    @Test
    public void testErrorHandlingAccessDenied() throws Exception {
        given(repository.findById(2l)).willThrow(new AccessDeniedException("Access Denied"));

        mvc.perform(get("/v1/certificates/83020711970/2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.title", is("An error occurred!")))
                .andExpect(jsonPath("$.detail", is("Access Denied")))
                .andExpect(jsonPath("$.status", is(HttpStatus.FORBIDDEN.value())))

                .andReturn();
    }

    @Test
    public void testErrorHandlingAllOthers() throws Exception {
        given(repository.findById(2l)).willThrow(new RuntimeException("Internal problem"));

        mvc.perform(get("/v1/certificates/83020711970/2")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andExpect(jsonPath("$.title", is("An error occurred!")))
                .andExpect(jsonPath("$.detail", is("Internal problem")))
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))

                .andReturn();
    }
}
