package org.cp.confisual.nevisproxy.log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.cp.confisual.TestUtils;
import org.junit.jupiter.api.Test;

class ParserTest {

	@Test
	void shouldParseLogToTraceList() throws IOException {
		Parser underTest = new Parser();

		List<Trace> results = underTest.parse(TestUtils.getTestResourceFile("navajo_simple.log"));

		assertEquals(2, results.size());
		Trace trace1 = results.get(0);
		assertEquals("02314.139697183344384.0a156446-090a-660a1504-174b3ea5032-000010d5", trace1.getId());
		assertEquals(Instant.from(Parser.NAVAJO_DATETIME_FORMATER.parse("2020 09 22 11:45:11.730")), trace1.getStartTime());
		Trace trace2 = results.get(1);
		assertEquals("02314.139697183344384.0a156446-090a-660a1504-174b3ea5075-000010d6", trace2.getId());
		assertEquals(Instant.from(Parser.NAVAJO_DATETIME_FORMATER.parse("2020 09 22 11:45:11.798")), trace2.getStartTime());

		Trail client2Proxy = trace1.getClient2Proxy();
		assertEquals("/cams-srvc/rest/api/common/access-control/check-authorization", client2Proxy.getUri());
		assertEquals("POST", client2Proxy.getMethod());
		assertEquals(Instant.from(Parser.NAVAJO_DATETIME_FORMATER.parse("2020 09 22 11:45:11.730")), client2Proxy.getTimestamp());
	}
}
