package ch.kerbtier.weproc.urlmodifiers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

/**
 * 
 * creates a hash over the content of the file.
 * 
 * the hash is 48 bits and encoded as base64 using alphanumeric and -_ chars.
 * 
 * with 1000 items the chance for a collision is about 1 / 500000000
 * with 1000000 items its about 1 / 560
 * 
 */

public class URLHasher implements URLModifier {

  private File basePath = new File(".").getAbsoluteFile();
  private Action action;

  public URLHasher(File basePath, Action action) {
    this.basePath = basePath.getAbsoluteFile();
    this.action = action;
  }

  public void setBasePath(File file) {
    this.basePath = file.getAbsoluteFile();
  }

  @Override
  public String change(String url) {

    boolean proto = url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");

    if (proto) {
      return url;
    }

    File path = new File(basePath, url);

    String ext = url.substring(url.lastIndexOf(".") + 1);

    Hasher hasher = Hashing.murmur3_128().newHasher();

    try {
      hasher.putBytes(FileUtils.readFileToByteArray(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String key = BaseEncoding.base64Url().encode(hasher.hash().asBytes(), 0, 6);

    action.process(path, key, ext);
    
    return key + "." + ext;
  }

  public interface Action {
    void process(File file, String key, String ext);
  }
}
