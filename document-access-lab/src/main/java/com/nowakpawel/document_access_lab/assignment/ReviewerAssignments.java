package com.nowakpawel.document_access_lab.assignment;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReviewerAssignments {
    private record Assignment(String reviewer, long applicationId) {}

    private final Set<Assignment> assignments = ConcurrentHashMap.newKeySet();

    public void assign(String reviewer, long applicationId) {
        assignments.add(new Assignment(reviewer, applicationId));
    }

    public boolean isAssigned(String reviewer, long applicationId) {
        return assignments.contains(new Assignment(reviewer, applicationId));
    }

    public void revoke(String reviewer, long applicationId) {
        assignments.remove(new Assignment(reviewer, applicationId));
    }

}
