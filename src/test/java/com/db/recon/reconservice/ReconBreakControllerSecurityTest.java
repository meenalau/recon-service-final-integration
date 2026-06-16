package com.db.recon.reconservice;

import com.db.recon.reconservice.controller.ReconBreakController;
import com.db.recon.reconservice.model.ReconBreak;
import com.db.recon.reconservice.securityconfig.SecurityConfig;
import com.db.recon.reconservice.service.ReconBreakService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReconBreakController.class)
@Import(SecurityConfig.class)
class ReconBreakControllerSecurityTest {


        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private ReconBreakService service;

        // tests...


    @Test
    @WithMockUser(username = "ops", roles = {"OPS"})
    void createBreak_authorized() throws Exception {

        ReconBreak breakObj = new ReconBreak();

        //  Stub service.create() so controller doesn't NPE
        when(service.create(any(ReconBreak.class))).thenReturn(breakObj);

        mockMvc.perform(
                        post("/api/v1/recon/breaks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(breakObj))
                )
                .andExpect(status().isCreated());  //  Now passes
    }

    @Test
    void createBreak_withoutCredentials() throws Exception {

        ReconBreak breakObj = new ReconBreak();

        mockMvc.perform(
                        post("/api/v1/recon/breaks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(breakObj))
                )
                .andExpect(status().isUnauthorized());  //  No stub needed, blocked before service
    }
}



