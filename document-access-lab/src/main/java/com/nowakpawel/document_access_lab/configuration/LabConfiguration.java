package com.nowakpawel.document_access_lab.configuration;

import com.nowakpawel.document_access_lab.assignment.ReviewerAssignments;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class LabConfiguration {

    @Bean
    ReviewerAssignments reviewerAssignments() {
        var assignmets = new ReviewerAssignments();
        assignmets.assign("maya", 42L);
        return assignmets;
    }
}
