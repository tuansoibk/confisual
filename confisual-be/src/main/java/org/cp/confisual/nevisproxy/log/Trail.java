package org.cp.confisual.nevisproxy.log;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Trail {

	private final String uri;
	private Map<String, String> headers;
	private int responseCode;
	private Instant timestamp;
	private List<String> filters;
}
