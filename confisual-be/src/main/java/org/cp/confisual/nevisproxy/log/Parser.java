/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy.log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {

	// Sample trace line:
	// 2020 09 22 11:45:11.730 isi3web    NavajoOp   02314.139697183344384.0a156446-090a-660a1504-174b3ea5032-000010d5 7-DBG_LO:  >>>
	private static final Pattern TRACE_DETECTION_PATTERN = Pattern.compile("([\\d :\\.]+).*?NavajoOp   (.*?) 7-DBG_LO:  (>>>|<<<|===)");

	// Sample Client2Proxy trail start line:
	// 2020 09 22 11:45:11.730 isi3web    NavajoOp   02314.139697183344384.0a156446-090a-660a1504-174b3ea5032-000010d5 7-DBG_LO:  >>>>> 'POST /cams-srvc/rest/api/common/access-control/check-authorization' rmIP='10.21.100.70' trID='0a156446-090a-660a1504-174b3ea5032-000010d5'
	private static final Pattern TRAIL_DETECTION_PATTERN = Pattern.compile("([\\d :\\.]+).*?7-DBG_LO:  >>>>> '(POST|GET|PUT|OPTIONS) (.*?)'");
	public static final DateTimeFormatter NAVAJO_DATETIME_FORMATER = DateTimeFormatter
			.ofPattern("yyyy MM dd HH:mm:ss.SSS")
			.withZone(ZoneId.systemDefault());

	public List<Trace> parse(File logFile) throws IOException {
		// get all lines
		List<String> lines = Arrays.asList(FileUtils.readFileToString(logFile, Charset.defaultCharset()).split("\n"));

		// put all lines of the same trace to a maps
		Map<String, Trace> traces = new HashMap<>();
		Map<String, List<String>> traceLines = new HashMap<>();

		// for each group of line that belong to the same trace
		//  - find lines of trail client2proxy
		//    - get first line: parse timestamp, uri
		//    - get header line
		//    - get filter line
		//  - find lines of other trails

		for (String line : lines) {
			Matcher matcher = TRACE_DETECTION_PATTERN.matcher(line);
			if (matcher.find()) {
				String timestamp = matcher.group(1);
				String traceId = matcher.group(2);
				if (!traces.containsKey(traceId)) {
					traces.put(traceId, new Trace(traceId, Instant.from(NAVAJO_DATETIME_FORMATER.parse(timestamp.trim()))));
					traceLines.put(traceId, new ArrayList<>());
				}
				traceLines.get(traceId).add(line);
			}
		}
		for (Map.Entry<String, Trace> entry : traces.entrySet()) {
			updateTrace(entry.getValue(), traceLines.get(entry.getKey()));
		}

		return new ArrayList<>(traces.values());
	}

	private void updateTrace(Trace trace, List<String> lines) {
		trace.setClient2Proxy(findAndParseClient2ProxyTrail(lines));
//		trace.setProxy2Backend(findAndParseProxy2BackendTrail(lines));
//		trace.setBackend2Proxy(findAndParseBackend2ProxyTrail(lines));
//		trace.setProxy2Client(findAndParseProxy2ClientTrail(lines));
	}

	private Trail findAndParseClient2ProxyTrail(List<String> lines) {
		Trail client2ProxyTrail = null;
		for (String line : lines) {
			Matcher matcher = TRAIL_DETECTION_PATTERN.matcher(line);
			if (matcher.find()) {
				client2ProxyTrail = new Trail(
						matcher.group(3),
						matcher.group(2),
						Instant.from(NAVAJO_DATETIME_FORMATER.parse(matcher.group(1).trim())));
			}
		}

		return client2ProxyTrail;
	}
}
