package com.devsegal.jserve;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class MockHTTPServer implements Serverable {
    public boolean isStopped = false;

    @Override
    public void setupOriginalServerPath(Path path) {

    }

    @Override
    public void setupOriginalServerPath(String path) {

    }

    @Override
    public void setupPublicAssetFolder(Path path) {

    }

    @Override
    public void setupPublicAssetFolder(String path) {

    }

    @Override
    public void setupPublicAssetFolder(String assetFolder, String assetFolderPrefix) {

    }

    @Override
    public void setupNotFoundPageHandler(BiConsumer<RequestParser, ResponseStream> notFoundPageHandler) {

    }

    @Override
    public void route(String path, String method, BiConsumer<RequestParser, ResponseStream> handler) {

    }

    @Override
    public void run() {
        while(!isStopped) {
            // just work until this changes
        }
    }

    @Override
    public void stop() {
        isStopped = true;
    }
}
