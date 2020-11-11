package com.devsegal.jserve;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResponseWriter extends PrintWriter implements Response {	
	private StringBuilder responseContents; 
	private Path originalServerPath;
	
	
    public ResponseWriter(OutputStream output) {
        super(output);
		responseContents = new StringBuilder();
	}
	
	public ResponseWriter(OutputStream output, Path originalServerPath) {
		super(output);
		this.originalServerPath = originalServerPath;

		responseContents = new StringBuilder();
	}

	public void setResponseHeaders(ResponseHeaders responseHeaders) {
		responseContents.insert(0, ResponseHeaders.convertToString(responseHeaders));
	}

	@Override
	public void insertContent(String content) {
        responseContents.append("\n" + content + "\n");
	}

	public void insertContent(List<String> content) {
		for(String contentMember : content) {
			responseContents.append(contentMember);
		}
	}

	private void readContentFromFile(Path path) {
		try {
			insertContent("\n");
			insertContent(Files.readAllLines(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void readContentFromFile(Path path, TransformPath translatePath) {
		Path transformedPath = translatePath.transform(path);

		try {
			insertContent("\n");
			insertContent(Files.readAllLines(transformedPath));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void readContentFromFile(Path path, boolean transformPathFromOriginalServerPath) {
		if(transformPathFromOriginalServerPath) {
			readContentFromFile(path, (path2) -> {
				return Path.of(originalServerPath.toString() + "/" + path2.toString());
			});
		} else {
			readContentFromFile(path);
		}
	}

	public void readContentFromFile(String path, boolean transformPathFromOriginalServerPath) {
		readContentFromFile(Path.of(path), transformPathFromOriginalServerPath);
	}

	public void send() {
		print(responseContents);
		close();
	}
}
