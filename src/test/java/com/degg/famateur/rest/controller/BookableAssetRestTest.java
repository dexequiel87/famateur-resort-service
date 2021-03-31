package com.degg.famateur.rest.controller;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.degg.famateur.FamateurApplication;
import com.degg.famateur.exception.InvalidBookableAssetException;
import com.degg.famateur.rest.model.BookableAssetDto;
import com.degg.famateur.service.BookableAssetService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookableAssetController.class)
@ContextConfiguration(classes = {FamateurApplication.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@ExtendWith(RestDocumentationExtension.class)
public class BookableAssetRestTest {

    private static final String RESORT_ID = "00001";
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookableAssetService service;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    UserService userService;

    private String BOOKABLE_ASSET_VALID_ID;
    private String endpointUrl;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        BOOKABLE_ASSET_VALID_ID = "9451d9c51sc1s5d1csd231c";
        endpointUrl = String.format("/api/v1/resorts/%s/assets/", RESORT_ID);

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

    private BookableAssetDto getValidBookableAsset() {
        return mockBookableAsset("Mocked Asset 1", "Mocked Asset 1 long description");
    }

    @Test
    void list() throws Exception {
        given(service.findAllAssetsByResortId(RESORT_ID)).willReturn(Arrays.asList(getValidBookableAsset()));

        mockMvc.perform(get(endpointUrl)
                .param("pageSize", "20")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        given(service.findBookableAssetByResortIdAndBookableAssetId(RESORT_ID, BOOKABLE_ASSET_VALID_ID))
                .willReturn(getValidBookableAsset());

        mockMvc.perform(get(endpointUrl + BOOKABLE_ASSET_VALID_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void create() throws Exception {
        BookableAssetDto assetDto = getValidBookableAsset();
        String assetDtoJson = objectMapper.writeValueAsString(assetDto);
        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetDtoJson))
                .andExpect(status().isCreated());
        verify(service, times(1)).addBookableAssetToResort(RESORT_ID, assetDto);
    }

    @Test
    void badRequestCreate() throws Exception {
        BookableAssetDto assetDto = mockBookableAsset("", "Test Asset long description");
        String assetDtoJson = objectMapper.writeValueAsString(assetDto);

        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(assetDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestUpdate() throws Exception {

        BookableAssetDto bookableAssetDto = mockBookableAsset("", "Test Bookable Asset 1 long description");
        String resortJson = objectMapper.writeValueAsString(bookableAssetDto);

        doThrow(InvalidBookableAssetException.class)
            .when(service).addBookableAssetToResort(RESORT_ID, bookableAssetDto);

        mockMvc.perform(put(endpointUrl + BOOKABLE_ASSET_VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {

        BookableAssetDto assetDto = mockBookableAsset("Test Asset 1", "Test Asset 1 long description");
        String resortJson = objectMapper.writeValueAsString(assetDto);

        mockMvc.perform(put(endpointUrl + BOOKABLE_ASSET_VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isOk());
        verify(service, times(1)).updateBookableAsset(RESORT_ID, assetDto.getId(), assetDto);
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(endpointUrl + BOOKABLE_ASSET_VALID_ID)
        ).andExpect(status().isOk());
        verify(service, times(1)).deleteBookableAsset(RESORT_ID, BOOKABLE_ASSET_VALID_ID);
    }

    private BookableAssetDto mockBookableAsset(String title, String description) {
        return BookableAssetDto.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title(title)
                .description(description)
                .calendarType("google-calendar")
                .calendarId("00001")
                .build();
    }
}
