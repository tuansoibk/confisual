package org.cp.confisual.plantuml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.security.SFile;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.NevisAuthConfigVisitor;

public class Visualiser {
  public static String IMG_EXTENSION = ".PNG";

  private static SourceStringReader reader;

  public static void visualise(NevisAuthConfigVisitor visitor, Domain domain, Path destinationFolder) throws VisualizerException {
    if (domain == null) {
      throw new VisualizerException("Domain is null");
    }

    if (!Files.exists(destinationFolder) || !Files.isDirectory(destinationFolder)) {
      throw new VisualizerException("Destination is invalid");
    }

    domain.accept(visitor);
    reader = new SourceStringReader(visitor.getSourceBuilder());
    File img = new File(destinationFolder.resolve(domain.getName() + IMG_EXTENSION).toUri());

    try {
      reader.outputImage(SFile.fromFile(img));
    }
    catch (IOException e) {
      throw new VisualizerException("Can't visualize img for domain " + domain.getName(), e);
    }
  }
}
