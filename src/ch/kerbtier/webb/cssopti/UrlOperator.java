package ch.kerbtier.webb.cssopti;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlOperator {
  private static Pattern URL = Pattern.compile("url\\(\\s*'(.*?)'\\s*\\)|url\\((.*?)\\)");

  public static Set<Path> find(Path base, String source) {
    Set<Path> resources = new HashSet<>();
    Matcher m = URL.matcher(source);

    while (m.find()) {
      String url = m.group(1);
      if (url == null) {
        url = m.group(2);
      }

      Path resource = base.resolve(url).normalize();
      resources.add(resource);
    }
    return resources;
  }

  public static String replace(Map<Path, String> map, Path base, String css) {
    Matcher m = URL.matcher(css);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {

      String url = m.group(1);
      if (url == null) {
        url = m.group(2);
      }

      Path resource = base.resolve(url).normalize();

      String replacement = "url(" + map.get(resource) + ")";
      m.appendReplacement(sb, replacement);
    }
    m.appendTail(sb);

    return sb.toString();
  }
}
