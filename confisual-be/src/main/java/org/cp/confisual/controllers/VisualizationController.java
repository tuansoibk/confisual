/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisualizationController {
		@GetMapping("/")
		public String index() {
				return "Greetings from Spring Boot!";
		}
}
