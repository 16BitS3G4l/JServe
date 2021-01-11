package com.devsegal.jserve.utils;

import com.devsegal.jserve.FileMIMETypeRegistry;

public class Path {
    public static boolean pathIsDirectory(java.nio.file.Path path) {
        return path.toFile().isDirectory();
    }

    public static String getFileName(java.nio.file.Path path) {
        return path.getFileName().toString();
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        // dot has not been found
        if(dotIndex == -1) {
            return "";
        }

        // the file starts with a dot, no file extension
        else if(dotIndex == 0) {
            return "";
        }

        // dot has been found, but it is the last character of the filename
        // meaning there is no file extension
        else if(dotIndex == fileName.length()) {
            return "";
        }

        // a file extension exists
        else {
            return fileName.substring(dotIndex + 1, fileName.length());
        }
    }

    public static String getContentType(java.nio.file.Path path, FileMIMETypeRegistry fileTypes) {
        String fileName = getFileName(path);
        String fileExtension = getFileExtension(fileName);

        System.out.println(fileExtension);

        // we found a file extension
        if(!fileExtension.equals("")) {
            return fileTypes.getMIMEType(fileExtension);
        }

        return "";
    }
}
