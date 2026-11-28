package com.nowakpawel.document_access_lab.web.controller;

import com.nowakpawel.document_access_lab.assignment.ReviewerAssignments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.security.user.password=lab-test-password")
@AutoConfigureMockMvc
public class DocumentAccessRevocationTest {
    private static final String DOWNLOAD_URL = "/document/1/download";
    private static final String DOCUMENT_TEXT = "Grant application 42: project summary.";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewerAssignments assignments;

    @AfterEach
    void restoreInitialAssignment() {
        assignments.assign("maya", 42L);
    }

    @Test
    void revokingAssignmentBlocksDownloadInTheSameSession() throws Exception {
        var login = mockMvc.perform(formLogin().user("maya").password("lab-test-password"))
                .andExpect(authenticated().withUsername("maya"))
                .andReturn();
        var session = (MockHttpSession) login.getRequest().getSession(false);
        assertNotNull(session, "Successful login should create a session");

        mockMvc.perform(get(DOWNLOAD_URL).session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(DOCUMENT_TEXT));

        assignments.revoke("maya", 42L);

        mockMvc.perform(get(DOWNLOAD_URL).session(session))
                .andExpect(authenticated().withUsername("maya"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(not(containsString(DOCUMENT_TEXT))));

    }
}
