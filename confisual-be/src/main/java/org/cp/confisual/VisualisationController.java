package org.cp.confisual;/*
 * Author : AdNovum Informatik AG
 */

import java.util.Collections;
import java.util.Map;

import org.cp.confisual.nevisauth.NevisConfig;
import org.cp.confisual.nevisauth.NevisAuthVisualiser;
import org.cp.confisual.nevisproxy.NevisProxyVisualiser;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@BasePathAwareController
public class VisualisationController {

		@PostMapping(path = "/v1/visualise/nevisAuth")
		public ResponseEntity<VisualisationResponse> visualiseNevisAuth(@RequestBody NevisConfig data) {
				NevisAuthVisualiser visualiser= new NevisAuthVisualiser();
				VisualisationResponse response;
				try {
						Map<String, String> diagrams = visualiser.visualiseDomains(data.getXmlConfig());
						response = VisualisationResponse.success(diagrams);
				}
				catch (VisualisationException e) {
						response = VisualisationResponse.error(Collections.singletonList(e.getMessage()));
				}

				return new ResponseEntity<>(response, HttpStatus.OK);
		}

	@PostMapping(path = "/v1/visualise/nevisProxy")
	public ResponseEntity<VisualisationResponse> visualiseNevisProxy(@RequestBody NevisConfig data) {
		NevisProxyVisualiser visualiser = new NevisProxyVisualiser();
		VisualisationResponse response;
		try {
			Map<String, String> diagrams = visualiser.visualiseUrlPattern(data.getXmlConfig());
			response = VisualisationResponse.success(diagrams);
		}
		catch (VisualisationException e) {
			response = VisualisationResponse.error(Collections.singletonList(e.getMessage()));
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
