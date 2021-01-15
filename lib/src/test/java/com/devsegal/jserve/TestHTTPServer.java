package com.devsegal.jserve;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestHTTPServer {
    @Test
    public void testRunStopsAfterCallingStopMethod() {
        MockHTTPServer mockServer = new MockHTTPServer();

        new Thread(() -> {
            mockServer.run();
        }).start();

        // Give the server some time to run (by sleeping), then stop it
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        mockServer.stop();

        assertEquals(mockServer.isStopped, true);
    }

    @Test
    public void testRunDoesNotStartServerAgainAfterStopped() {
        MockHTTPServer mockServer = new MockHTTPServer();

        new Thread(() -> {
            mockServer.run();
        }).start();

        // Give the server some time to run (by sleeping), then stop it
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        mockServer.stop();

        // As long as this completes (not running forever), then the test will pass
        mockServer.run();
    }
}
