package com.devsegal.jserve;

import com.devsegal.jserve.utils.RouteHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.HashMap;
import java.util.stream.Stream;

public class RouteRegistry {
    private HashMap<String, BiConsumer<RequestParser, ResponseStream>> routeToHandler;
    private FileMIMETypeRegistry fileTypes;

    public RouteRegistry(FileMIMETypeRegistry fileTypes) {
        routeToHandler = new HashMap<>();
        this.fileTypes = fileTypes;
    }

    public BiConsumer<RequestParser, ResponseStream> getHandler(String route) {
        return routeToHandler.get(route);
    }

     /**
     * @param path represents a route (from the root of the server website.com/route) 
     * @param method the HTTP method the WebRouteHandler should be invoked on.
     * @param handler the code that will be executed when a request that matches the path + method occurs.
     */
    public void registerRoute(String path, String method, BiConsumer<RequestParser, ResponseStream> handler) {
        routeToHandler.put(path + method, handler);
    }

    private String generateRouteFromAsset(Path asset, String prefix) {
            return prefix + "/" + com.devsegal.jserve.utils.Path.getFileName(asset);
    }

    private void registerAssetFileAsRoute(Path asset, String assetFolderPrefix) {
        if(!com.devsegal.jserve.utils.Path.pathIsDirectory(asset)) {
            registerRoute(generateRouteFromAsset(asset, assetFolderPrefix), "GET", RouteHandler.createDefaultHandlerForPath(asset, fileTypes));
        }
    }

    /**
     * Registers all files in a folder to the routesHandler, so these assets will be sent/found instead of a 404 page.
     * @param assetFolder the folder to register all assets from.
     * @param assetFolderPrefix instead of routes that start from the root of the server, you can add a prefix to these routes.
     */
    public void registerAssetFolderAsRoutes(Path assetFolder, String assetFolderPrefix) {
        Stream<Path> publicAssets = null;

        try {
            
            publicAssets = Files.walk(assetFolder);
            publicAssets.forEach(asset -> registerAssetFileAsRoute(asset, assetFolderPrefix));

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            publicAssets.close();
        }
    }
}
