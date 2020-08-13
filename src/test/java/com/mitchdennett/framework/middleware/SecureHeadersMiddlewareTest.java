package com.mitchdennett.framework.middleware;

import com.mitchdennett.framework.http.Response;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SecureHeadersMiddlewareTest {

    @Test
    public void headersShouldBeSet() {
        Response resp = mock(Response.class);

        ArgumentCaptor<HashMap<String, String>> key = ArgumentCaptor.forClass(HashMap.class);

        SecureHeadersMiddleware middleware = new SecureHeadersMiddleware();
        middleware.before(resp);

        verify(resp).headers(key.capture());

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Strict-Transport-Security", "max-age=63072000; includeSubdomains");
        headers.put("X-Frame-Options", "SAMEORIGIN");
        headers.put("X-XSS-Protection", "1; mode=block");
        headers.put("X-Content-Type-Options", "nosniff");
        headers.put("Referrer-Policy", "no-referrer, strict-origin-when-cross-origin");
        headers.put("Cache-control", "no-cache, no-store, must-revalidate");
        headers.put("Pragma", "no-cache");

        assertEquals(headers, key.getValue());
    }
}