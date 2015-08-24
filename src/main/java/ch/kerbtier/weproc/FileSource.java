package ch.kerbtier.weproc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSource implements Source {

  private Path path;
  private Charset charset;

  public FileSource(Path path, Charset charset) {
    this.path = path;
    this.charset = charset;
  }

  public FileSource(File file, Charset charset) {
    this.path = Paths.get(file.toString());
    this.charset = charset;
  }

  public FileSource(Path path) {
    this(path, Charset.forName("utf-8"));
  }

  public FileSource(File file) {
    this(Paths.get(file.toString()), Charset.forName("utf-8"));
  }

  @Override
  public String get() {
    try {
      return new String(Files.readAllBytes(path), charset);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getName() {
    return path.getFileName().toString();
  }
}
