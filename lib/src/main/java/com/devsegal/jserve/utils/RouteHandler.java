package com.devsegal.jserve.utils;

import java.util.function.BiConsumer;
import java.nio.file.Path;

import com.devsegal.jserve.*;

public class RouteHandler {
    public static BiConsumer<RequestParser, ResponseStream> createDefaultHandlerForPath(Path pathToAssetFile, FileMIMETypeRegistry fileTypes) {
        String contentType = com.devsegal.jserve.utils.Path.getContentType(pathToAssetFile, fileTypes);

        // If there exists a content type that matches this asset file
        if(!contentType.equals("")) {
            
            return (request, response) -> {
                    try {
                        response.setResponseHeaders(new ResponseHeaders(contentType, "close"));
                    } catch (ResponseStatusNullException e) {
                        e.printStackTrace();
                    }

                    response.readContentFromFile(pathToAssetFile, false);
                    response.send();
            };
            
        } else {
            
            return (request, response) -> {
                try {
                    response.setResponseHeaders(new ResponseHeaders("text/plain", "close"));
                } catch(ResponseStatusNullException e) {
                    e.printStackTrace();
                }

                response.readContentFromFile(pathToAssetFile, false);
                response.send();
            };

        }
    }    
}
