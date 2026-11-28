# Document access after assignment revocation

Can a reviewer use a saved download link after their assignment ends while they are still signed in?

## Access rule
In this fictional grant review portal, document 1 belongs to grant application 42. Maya can download the document while she is assigned to review that application. On each download request, the server looks up the document's application ID and checks the reviewer's current assignment.

## Integration test result
Maya signed in once. The test sent `GET /document1/download` twice using the same authenticated session. Between request, her assignment to application 42 has been revoked.

| Assignment to application 42 | HTTP Response | Document Content |
|---|---|---|
| Active | 200 OK | Matched the expected document text |
| Revoked | 403 Forbidden | Document text was absent |

The test also verified that Spring Security still identified the user as 'maya' on the denied request. This distinguishes assignment revokation from session expiry.

## Run the test
From the `document-access-tab` directory:

```
bash
./mvnw -Dtest=DocumentAccessRevocationTest test
```

[View the integration test](src/test/java/com/nowakpawel/document_access_lab/web/controller/DocumentAccessRevocationTest.java)