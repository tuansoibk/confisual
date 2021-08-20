package org.cp.confisual.nevisauth;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.security.SFile;
import org.cp.confisual.ParserException;
import org.cp.confisual.VisualisationException;

public class NevisAuthVisualiser {
  public static String IMG_EXTENSION = ".PNG";

  private SourceStringReader reader;

  private Path destinationFolder;

  public NevisAuthVisualiser(Path destinationFolder) throws VisualisationException {
    if (!Files.exists(destinationFolder) || !Files.isDirectory(destinationFolder)) {
      throw new VisualisationException("Destination is invalid");
    }

    this.destinationFolder = destinationFolder;
  }

  public void visualiseDomains(File nevisAuthFile) throws VisualisationException {
    List<Domain> domains;
    try {
      domains = new Parser().parse(nevisAuthFile);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisAuth file", e);
    }

    if (domains.isEmpty()) {
      throw new VisualisationException("nevisAuth file doesn't have any domains");
    }

    for (Domain domain : domains) {
      PlantUmlVisitor visitor = new PlantUmlVisitor();
      domain.accept(visitor);
      reader = new SourceStringReader(visitor.getSourceBuilder());
      File img = new File(destinationFolder.resolve(domain.getName() + IMG_EXTENSION).toUri());

      try {
        reader.outputImage(SFile.fromFile(img));
      }
      catch (IOException e) {
        throw new VisualisationException("Can't visualize img for domain " + domain.getName(), e);
      }
    }
  }
}
