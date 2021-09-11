package org.cp.confisual;/*
 * Author : AdNovum Informatik AG
 */

import java.util.Collections;
import java.util.Map;

import org.cp.confisual.nevisauth.NevisAuthConfigRequest;
import org.cp.confisual.nevisauth.NevisAuthVisualiser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VisualisationController {
		@GetMapping("/")
		public String index() {
				return "Greetings from Spring Boot!";
		}

		@PostMapping(path = "/visualise/nevisAuth")
		public ResponseEntity<VisualisationResponse> visualizeNevisAuth(@RequestBody NevisAuthConfigRequest data) {
				NevisAuthVisualiser visualiser= new NevisAuthVisualiser();
				VisualisationResponse response;
				try {
						Map<String, String> diagrams = visualiser.visualiseDomains(data.getNevisAuthConfig());
						response = VisualisationResponse.success(diagrams);
				}
				catch (VisualisationException e) {
						response = VisualisationResponse.error(Collections.singletonList(e.getMessage()));
				}

				return new ResponseEntity<>(response, HttpStatus.OK);
		}
}
