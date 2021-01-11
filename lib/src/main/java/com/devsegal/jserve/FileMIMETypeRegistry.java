package com.devsegal.jserve;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class FileMIMETypeRegistry {
    private Properties fileTypeToMIMEType = new Properties();
    private static final String DEFAULT_PROPERTIES_LOCATION = "properties.props";

    public FileMIMETypeRegistry() throws IOException {
        // initialize MIME types from default properties file location
        fileTypeToMIMEType.load( new InputStreamReader( new FileInputStream(DEFAULT_PROPERTIES_LOCATION) ) );
    }

    public FileMIMETypeRegistry(String properties) throws IOException {
        fileTypeToMIMEType.load( new InputStreamReader( new FileInputStream( properties ) ) );
    }

    public FileMIMETypeRegistry(Path properties) throws IOException {
        // initialize MIME types from given properties file
        this(properties.toString());
    }

    public String getMIMEType(String fileType) {
        return fileTypeToMIMEType.get(fileType).toString();
    }
}
