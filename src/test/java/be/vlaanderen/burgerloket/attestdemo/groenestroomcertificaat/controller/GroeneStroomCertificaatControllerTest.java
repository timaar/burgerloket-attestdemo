package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.controller;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.assemblers.GroeneStroomCertificaatAssembler;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.domain.GroeneStroomCertificaat;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.repository.GroeneStroomCertficaatRepository;
import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security.JWTSecurityService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(GroeneStroomCertificaatController.class)
@Import({ GroeneStroomCertificaatAssembler.class, JWTSecurityService.class})
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
    public void download() throws Exception {

        mvc.perform(get("/v1/certificates/83020711970/2018/nl/download")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))

                .andReturn();
    }
}
