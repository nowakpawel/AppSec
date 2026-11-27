package com.nowakpawel.document_access_lab.web.controller;

import com.nowakpawel.ReviewerAssignments;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@RestController
public class DocumentController {
    private record Document(long applicationId, String content) {}
    private final ReviewerAssignments assignments;

    private final Map<Long, Document> documents = Map.of(
      1L, new Document(42L, "Grant application 42: project summary.")
    );

    public DocumentController(ReviewerAssignments assignments) {
        this.assignments = assignments;
    }

    @GetMapping(value = "/document/{id}/download", produces = MediaType.TEXT_PLAIN_VALUE)
    public String download(@PathVariable("id") long documentId, Principal principal) {
        Document document = documents.get(documentId);

        if (document == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if ((!assignments.isAssigned(principal.getName(), document.applicationId)))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return document.content;
    }
}
