package com.devsegal.jserve;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public interface Serverable {
    public void setupOriginalServerPath(Path path);
    public void setupOriginalServerPath(String path);
    public void setupPublicAssetFolder(Path path);
    public void setupPublicAssetFolder(String path);
    public void setupPublicAssetFolder(String assetFolder, String assetFolderPrefix);
    public void setupNotFoundPageHandler(BiConsumer<RequestParser, ResponseStream> notFoundPageHandler);
    public void route(String path, String method, BiConsumer<RequestParser, ResponseStream> handler);
    public void run();
    public void stop();
}
