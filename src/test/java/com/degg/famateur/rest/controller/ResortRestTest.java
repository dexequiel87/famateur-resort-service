package com.degg.famateur.rest.controller;

import com.degg.famateur.FamateurApplication;
import com.degg.famateur.rest.model.ResortDto;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
    void setup(RestDocumentationContextProvider restDocumentation) {
        validId = "9451d9c51sc1s5d1csd231c";
        endpointUrl = "/api/v1/resorts/";
        mockMvc = MockMvcFactory.getMockMvc(restDocumentation, context, objectMapper);
    }

    private ResortDto getValidResort() {
        return mockResort("Mocked Resort 1", "Mocked Resort 1 long description");
    }

    @Test
    void list() throws Exception {
        given(service.findAll()).willReturn(Arrays.asList(getValidResort()));
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
        ResortDto resort = mockResort("Test Resort 1", "Test Resort 1 long description");
        String resortJson = objectMapper.writeValueAsString(resort);
        given(service.save(any())).willReturn(getValidResort());
        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isCreated());
    }

    @Test
    void badRequestCreate() throws Exception {
        ResortDto resort = mockResort("", "Test Resort 1 long description");
        String resortDto = objectMapper.writeValueAsString(resort);
        given(service.save(any())).willReturn(getValidResort());
        mockMvc.perform(post(endpointUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestUpdate() throws Exception {
        ResortDto resort = mockResort("", "Test Resort 1 long description");
        String resortJson = objectMapper.writeValueAsString(resort);
        given(service.save(any())).willReturn(getValidResort());
        mockMvc.perform(put(endpointUrl + validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        ResortDto resort = mockResort("Test Resort 1", "Test Resort 1 long description");
        String resortJson = objectMapper.writeValueAsString(resort);
        given(service.save(any())).willReturn(getValidResort());
        mockMvc.perform(put(endpointUrl + validId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resortJson))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(endpointUrl + validId))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteById(validId);
    }

    private ResortDto mockResort(String s, String s2) {
        return ResortDto.builder()
                .id("9451d9c51sc1s5d1csd231c")
                .title(s)
                .description(s2)
                .enabled(Boolean.TRUE)
                .build();
    }
}
