package org.cp.confisual;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VisualisationResponse {

  private Map<String, String> diagrams;
  private List<String> errors;

  private VisualisationResponse() {
    diagrams = null;
    errors = Collections.emptyList();
  }

  public Map<String, String> getDiagrams() {
    return diagrams;
  }

  public List<String> getErrors() {
    return errors;
  }

  public static VisualisationResponse success(Map<String, String> diagrams) {
    VisualisationResponse response = new VisualisationResponse();
    response.diagrams = diagrams;

    return response;
  }

  public static VisualisationResponse error(List<String> errors) {
    VisualisationResponse response = new VisualisationResponse();
    response.errors = errors;

    return response;
  }
}
