package com.devsegal.jserve;

import com.devsegal.jserve.utils.Path;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPathUtils {
    @Test
    public void testGetFileExtensionReturnsExtensionForFilename() {
        assertEquals("exe", Path.getFileExtension("program.exe"));
    }

    @Test
    public void testGetFileExtensionReturnsEmptyStringForEmptyFilename() {
        assertEquals("", Path.getFileExtension(""));
    }

    @Test
    public void testGetFileExtensionReturnsEmptyStringForFilenameWithNoExtension() {
            assertEquals("", Path.getFileExtension("program"));
    }

    @Test
    public void testGetFileExtensionReturnsEmptyStringForFilenameThatStartsWithDot() {
        assertEquals("", Path.getFileExtension(".gitignore"));
    }

    @Test
    public void testGetFileExtensionReturnsEmptyStringForFileThatEndsWithDot() {
        assertEquals("", Path.getFileExtension("program."));
    }
}
