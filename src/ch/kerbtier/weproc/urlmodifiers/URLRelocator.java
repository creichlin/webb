package ch.kerbtier.weproc.urlmodifiers;

import java.nio.file.Path;
import java.nio.file.Paths;


public class URLRelocator implements URLModifier {

  private Path basePath = Paths.get(".").normalize();
  private Path targetPath = Paths.get(".").normalize();
  
  public URLRelocator(){
  }
  
  public void setBasePath(Path path){
    this.basePath = path.normalize();
  }
  
  public void setTargetPath(Path path){
    this.targetPath = path.normalize();
  }
  
  @Override
  public String change(String url) {
    
    boolean proto = url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");
    
    if(proto) {
      return url;
    }
    
    Path n = Paths.get(basePath.toString(), url);
    
    Path relative = targetPath.relativize(n);
    
    return relative.toString();
  }
}
