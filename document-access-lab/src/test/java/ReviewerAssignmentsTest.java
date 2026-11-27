import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewerAssignmentsTest {

    @Test
    void revokingOneAssignmentPreservesTheReviewersOtherAssignment() {
        var assignments = new ReviewerAssignments();
        assignments.assign("Maya", 42L);
        assignments.assign("Maya", 73L);

        assertTrue(assignments.isAssigned("Maya", 42L));

        assignments.revoke("Maya", 42L);

        assertFalse(assignments.isAssigned("Maya", 42L));
        assertTrue(assignments.isAssigned("Maya", 73L));
    }

}