package com.devsegal.jserve;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ResponseWriter extends OutputStream implements Response {	
	private StringBuilder responseContents; 
	private Path responseFileContents;
	private Path originalServerPath;
	private OutputStream outputStream;

	
    public ResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
		responseContents = new StringBuilder();
	}
	
	public ResponseWriter(OutputStream outputStream, Path originalServerPath) {
		this.outputStream = outputStream;
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
		responseFileContents = path;
	}

	private void readContentFromFile(Path path, TransformPath translatePath) {
		responseFileContents = translatePath.transform(path);
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

	@Override
	public void write(int b) throws IOException {
		outputStream.write(b);
	}

	public void send() {
		try {
			outputStream.write(responseContents.toString().getBytes());	
						
			if(responseFileContents != null)
				outputStream.write(Files.readAllBytes(responseFileContents));

			outputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
