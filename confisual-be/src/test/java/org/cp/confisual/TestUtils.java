package org.cp.confisual;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

public class TestUtils {

  private TestUtils() {}

  public static URI getTestResourceURI(String resourceName) {
      return URI.create(TestUtils.class.getResource("/" + resourceName).toString());
  }

  public static File getTestResourceFile(String resourceName) {
    return new File(getTestResourceURI(resourceName));
  }

  public static String getTestResourceContent(String resourceName) throws IOException {
    return FileUtils.readFileToString(getTestResourceFile(resourceName), Charset.defaultCharset());
  }
}
