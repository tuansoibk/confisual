package org.cp.confisual.util;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.cp.confisual.VisualisationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PlantUmlUtils {

  private PlantUmlUtils() {}

  public static Map<String, String> generateImages(Map<String, String> domainObjects) throws VisualisationException {
    Map<String, String> encodedImages = new HashMap<>();
    for (Map.Entry<String, String> entry : domainObjects.entrySet()) {
      SourceStringReader reader = new SourceStringReader(entry.getValue());
      try {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        reader.outputImage(os, new FileFormatOption(FileFormat.PNG));
        os.close();
        encodedImages.put(entry.getKey(), Base64.getEncoder().encodeToString(os.toByteArray()));
      }
      catch (IOException e) {
        throw new VisualisationException("Can't visualize img for object " + entry.getKey(), e);
      }
    }

    return encodedImages;
  }
}
