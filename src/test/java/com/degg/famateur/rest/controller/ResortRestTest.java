package com.degg.famateur.rest.controller;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.degg.famateur.FamateurApplication;
import com.degg.famateur.model.Resort;
import com.degg.famateur.service.ResortService;
import com.degg.famateur.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResortController.class)
@ContextConfiguration(classes = {FamateurApplication.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@ExtendWith(RestDocumentationExtension.class)
public class ResortRestTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ResortService service;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    UserService userService;

    private String validId;
    private String endpointUrl;

    @BeforeEach
    void before(RestDocumentationContextProvider restDocumentation) {
        validId = "9451d9c51sc1s5d1csd231c";
        endpointUrl = "/api/v1/resorts/";

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
        .build();
    }

    private Resort getValidResort() {
        return Resort.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title("Mocked Resort 1")
                .description("Mocked Resort 1 long description")
                .enabled(Boolean.TRUE)
                .build();
    }

    @Test
    void getAll() throws Exception {
        // Mock repository
        given(service.findAll()).willReturn(Arrays.asList(getValidResort()));

        // Simulate HTTP Request
        mockMvc.perform(get(endpointUrl)
                .param("pageSize", "20")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {

        given(service.findById(validId)).willReturn(getValidResort());

        mockMvc.perform(get(endpointUrl + validId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void create() throws Exception {

        Resort resort = Resort.builder()
                .title("Test Resort 1")
                .description("Test Resort 1 long description")
                .enabled(Boolean.TRUE)
                .build();
        String resortJson = objectMapper.writeValueAsString(resort);

        given(service.save(any())).willReturn(getValidResort());

        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isCreated());
    }

    @Test
    void badRequestCreate() throws Exception {

        Resort resort = Resort.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title("Test Resort 1")
                .description("Test Resort 1 long description")
                .enabled(Boolean.TRUE)
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(resort);

        given(service.save(any())).willReturn(getValidResort());

        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestUpdate() throws Exception {

        Resort resort = Resort.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title("Test Resort 1")
                .description("Test Resort 1 long description")
                .enabled(Boolean.TRUE)
                .build();
        String resortJson = objectMapper.writeValueAsString(resort);

        given(service.save(any())).willReturn(getValidResort());

        mockMvc.perform(put(endpointUrl + validId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {

        Resort resort = Resort.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title("Test Resort 1")
                .description("Test Resort 1 long description")
                .enabled(Boolean.TRUE)
                .build();
        String resortJson = objectMapper.writeValueAsString(resort);

        given(service.save(any())).willReturn(getValidResort());

        mockMvc.perform(put(endpointUrl + validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testHandleDelete() throws Exception {
        mockMvc.perform(delete(endpointUrl + validId)
        ).andExpect(status().isOk());
    }
}
