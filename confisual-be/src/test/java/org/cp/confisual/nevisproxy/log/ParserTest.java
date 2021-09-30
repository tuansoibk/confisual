package org.cp.confisual.nevisproxy.log;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;

import org.cp.confisual.TestUtils;
import org.junit.jupiter.api.Test;

class ParserTest {

	@Test
	void shouldParseLogToTraceList() {
		Parser underTest = new Parser();

		List<Trace> results = underTest.parse(TestUtils.getTestResourceFile("navajo.log"));

		assertEquals(2, results.size());
		assertEquals("02314.139697183344384.0a156446-090a-660a1504-174b3ea5032-000010d5",results.get(0).getId());
		assertEquals("02314.139697183344384.0a156446-090a-660a1504-174b3ea5075-000010d6",results.get(1).getId());

		Trail client2Proxy = results.get(0).getClient2Proxy();
		assertEquals("/cams-srvc/rest/api/common/access-control/check-authorization", client2Proxy.getUri());
		assertEquals(14, client2Proxy.getHeaders().size());
		assertEquals(18, client2Proxy.getFilters().size());
		assertEquals(Instant.parse("2020 09 22 11:45:11.730"), client2Proxy.getTimestamp());
	}
}
