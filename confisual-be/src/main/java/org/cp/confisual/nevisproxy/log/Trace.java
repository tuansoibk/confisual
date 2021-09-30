package org.cp.confisual.nevisproxy.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
public class Trace {

  private final String id;
  private final Instant startTime;
  private Instant endTime;
  private Trail client2Proxy;
  private Trail proxy2Backend;
  private Trail backend2Proxy;
  private Trail proxy2Client;
}
