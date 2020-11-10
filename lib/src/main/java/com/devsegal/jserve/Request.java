package com.devsegal.jserve;

public class Request {
        private String method;
        private String path;

        public Request(String method, String path) {
            this.method = method;
            this.path = path;
        }
}
