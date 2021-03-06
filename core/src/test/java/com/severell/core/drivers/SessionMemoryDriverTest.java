package com.severell.core.drivers;

import com.severell.core.http.Request;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpSession;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SessionMemoryDriverTest {

    @Test
    public void sessionPutsCorrectValues() {
        Request req = mock(Request.class);
        HttpSession mockSession = mock(HttpSession.class);
        given(req.getSession()).willReturn(mockSession);

        String testKey = "userid";
        String testVal = "1234";

        ArgumentCaptor<String> key = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Function<String,String>> val = ArgumentCaptor.forClass(Function.class);

        SessionMemoryDriver session = new SessionMemoryDriver();
        session.setRequest(req);
        session.put(testKey, testVal);
        verify(mockSession).setAttribute(key.capture(), val.capture());
        assertEquals(key.getValue(), testKey);
        assertEquals(val.getValue(), testVal);

    }

    @Test
    public void sessionGetsCorrectValue() {
        Request req = mock(Request.class);
        HttpSession mockSession = mock(HttpSession.class);
        given(req.getSession()).willReturn(mockSession);

        String testKey = "userid";
        String testVal = "1234";

        given(mockSession.getAttribute(testKey)).willReturn(testVal);


        SessionMemoryDriver session = new SessionMemoryDriver();
        session.setRequest(req);
        String retVal = (String) session.get(testKey);
        assertEquals(retVal, testVal);
    }

    @Test
    public void sessionGetStringHandlesNull() {
        Request req = mock(Request.class);
        HttpSession mockSession = mock(HttpSession.class);
        given(req.getSession()).willReturn(mockSession);

        String testKey = "userid";

        given(mockSession.getAttribute(testKey)).willReturn(null);


        SessionMemoryDriver session = new SessionMemoryDriver();
        session.setRequest(req);
        String retVal = session.getString(testKey);
        assertNull(retVal);
    }

    @Test
    public void sessionGetStringReturnsCorrectString() {
        Request req = mock(Request.class);
        HttpSession mockSession = mock(HttpSession.class);
        given(req.getSession()).willReturn(mockSession);

        String testKey = "userid";
        String testVal = "1234";

        given(mockSession.getAttribute(testKey)).willReturn(testVal);


        SessionMemoryDriver session = new SessionMemoryDriver();
        session.setRequest(req);
        String retVal = session.getString(testKey);
        assertEquals(retVal, testVal);
    }

    @Test
    public void sessionGetIdReturnsActualSessionId() {
        Request req = mock(Request.class);
        HttpSession mockSession = mock(HttpSession.class);
        given(req.getSession()).willReturn(mockSession);

        String testVal = "1234";

        given(mockSession.getId()).willReturn(testVal);


        SessionMemoryDriver session = new SessionMemoryDriver();
        session.setRequest(req);
        String retVal = session.getId();
        assertEquals(retVal, testVal);
    }

}
