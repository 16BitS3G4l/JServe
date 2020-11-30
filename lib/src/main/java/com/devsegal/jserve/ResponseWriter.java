package com.devsegal.jserve;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;
import java.nio.file.Files;
import java.nio.file.Path;

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

	public void setResponseHeaders(ResponseHeaders responseHeaders) throws ResponseStatusNullException {
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

	private void readContentFromFile(Path path, Function<Path, Path> convertPathToServerPath) {
		responseFileContents = convertPathToServerPath.apply(path);
	}

	public void readContentFromFile(Path path, boolean convertPathToServerPath) {
		if(convertPathToServerPath) {
			readContentFromFile(path, (path2) -> {
				return Path.of(originalServerPath.toString() + "/" + path2.toString());
			});
		} else {
			readContentFromFile(path);
		}
	}

	public void readContentFromFile(String path, boolean convertPathToServerPath) {
		readContentFromFile(Path.of(path), convertPathToServerPath);
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
