package org.cp.confisual;/*
 * Author : AdNovum Informatik AG
 */

import java.util.List;

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
public class VisualizationController {
		@GetMapping("/")
		public String index() {
				return "Greetings from Spring Boot!";
		}

		@PostMapping(path = "/visualise/nevisAuth")
		public ResponseEntity<List<String>> visualizeNevisAuth(@RequestBody NevisAuthConfigRequest data) {
				NevisAuthVisualiser visualiser= new NevisAuthVisualiser();

				List<String> responseData = null;
				try {
						responseData = visualiser.visualiseDomains(data.getNevisAuthConfig());
				}
				catch (VisualisationException e) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				return new ResponseEntity<>(responseData, HttpStatus.OK);
		}
}
