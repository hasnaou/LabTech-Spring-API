package com.developer.techlab.controller;

import com.developer.techlab.DTO.*;
import com.developer.techlab.entities.*;
import com.developer.techlab.service.AnalyseService;
import com.developer.techlab.service.ReactifService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalyseControllerTest {

    @InjectMocks
    private AnalyseController analyseController;
    @Mock
    private ReactifService reactifService;
    @Mock
    private AnalyseService analyseService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAnalyse() throws Exception {
        AnalyseDTO inputAnalyseDTO = new AnalyseDTO(5L, "Analyse Libelle");
        mockMvc = standaloneSetup(analyseController).build();
        mockMvc.perform(post("/analyses/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputAnalyseDTO)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(analyseService, times(1)).saveAnalyse(any(AnalyseDTO.class));
    }

    @Test
    public void testGetAnalyse() throws Exception {
        AnalyseDTO expectedAnalyseDTO = new AnalyseDTO(5L, "Analyse Libelle");
        when(analyseService.getAnalyseById(5L)).thenReturn(expectedAnalyseDTO);
        mockMvc = standaloneSetup(analyseController).build();
        mockMvc.perform(get("/analyses/{analyseId}", 5L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        verify(analyseService, times(1)).getAnalyseById(5L);
    }

    @Test
    void testGetAllAnalyses() throws Exception {
        List<AnalyseDTO> expectedAnalyses = Arrays.asList(
                new AnalyseDTO(10L, "Analyse Libelle"),
                new AnalyseDTO(11L, "Analyse Libelle"));
        when(analyseService.getAllAnalyses()).thenReturn(expectedAnalyses);
        mockMvc = standaloneSetup(analyseController).build();
        mockMvc.perform(get("/analyses/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void testDeleteAnalyse() throws Exception {
        long analyseId = 1L;
        doNothing().when(analyseService).deleteAnalyse(analyseId);

        mockMvc = standaloneSetup(analyseController).build();
        mockMvc.perform(delete("/analyses/delete/{analyseId}", analyseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testUpdateAnalyse() throws Exception{
        List<Teste> testes = new ArrayList<>();
        Teste teste = new Teste("test",12.00,16.00,18.00);
        testes.add(teste);
        AnalyseDTO updatedAnalyseDTO = new AnalyseDTO();
        updatedAnalyseDTO.setLibelle("analyse");
        updatedAnalyseDTO.setTestes(testes);
        when(analyseService.updateAnalyse(eq(updatedAnalyseDTO.getId()), any(AnalyseDTO.class))).thenReturn(updatedAnalyseDTO);
        mockMvc = standaloneSetup(analyseController).build();
        mockMvc.perform(put("/analyses/update/{analyseId}", updatedAnalyseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAnalyseDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}