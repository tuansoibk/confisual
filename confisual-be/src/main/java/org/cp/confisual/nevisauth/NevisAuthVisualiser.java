package org.cp.confisual.nevisauth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.cp.confisual.ParserException;
import org.cp.confisual.VisualisationException;

public class NevisAuthVisualiser {

  public List<String> visualiseDomains(File nevisAuthFile) throws VisualisationException {
    List<Domain> domains;
    try {
      domains = Parser.parse(nevisAuthFile);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisAuth file", e);
    }

    return visualize(domains);
  }

  public List<String> visualiseDomains(String nevisAuthFile) throws VisualisationException {
    List<Domain> domains;
    try {
      domains = Parser.parse(nevisAuthFile);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisAuth string", e);
    }

    return visualize(domains);
  }


  private List<String> visualize(List<Domain> domains) throws VisualisationException {
    if (domains.isEmpty()) {
      throw new VisualisationException("nevisAuth file doesn't have any domains");
    }

    List<String> encodedImages = new ArrayList<>();

    for (Domain domain : domains) {
      PlantUmlVisitor visitor = new PlantUmlVisitor();
      domain.accept(visitor);
      SourceStringReader reader = new SourceStringReader(visitor.getSourceBuilder());

      try {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        reader.generateImage(os, new FileFormatOption(FileFormat.PNG));
        os.close();

        encodedImages.add(Base64.getEncoder().encodeToString(os.toByteArray()));
      }
      catch (IOException e) {
        throw new VisualisationException("Can't visualize img for domain " + domain.getName(), e);
      }
    }

    return encodedImages;
  }

}
