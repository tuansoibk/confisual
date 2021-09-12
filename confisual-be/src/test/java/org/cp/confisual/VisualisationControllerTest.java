package org.cp.confisual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cp.confisual.nevisauth.NevisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VisualisationControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  void visualiseNevisAuth() throws Exception {
    // given
    NevisConfig config = new NevisConfig(TestUtils.getTestResourceContent("esauth4.xml"));

    // when
    MvcResult mvcResult = mockMvc.perform(post("http://localhost:" + port + "/visualise/nevisAuth")
            .contentType("application/json")
            .content(mapper.writeValueAsString(config)))
            .andReturn();

    // then
    VisualisationResponse response = mapper.readValue(
            mvcResult.getResponse().getContentAsString(),
            VisualisationResponse.class
    );
    assertEquals(1, response.getDiagrams().size());
    assertTrue(response.getDiagrams().containsKey("TWOFASSO"));
    assertTrue(response.getErrors().isEmpty());
  }

  @Test
  void visualiseNevisProxy() throws Exception {
    // given
    NevisConfig config = new NevisConfig(TestUtils.getTestResourceContent("web.xml"));

    // when
    MvcResult mvcResult = mockMvc.perform(post("http://localhost:" + port + "/visualise/nevisProxy")
            .contentType("application/json")
            .content(mapper.writeValueAsString(config)))
            .andReturn();

    // then
    VisualisationResponse response = mapper.readValue(
            mvcResult.getResponse().getContentAsString(),
            VisualisationResponse.class
    );
    assertEquals(6, response.getDiagrams().size());
    assertTrue(response.getDiagrams().containsKey("/*"));
    assertTrue(response.getDiagrams().containsKey("/sso/*"));
    assertTrue(response.getDiagrams().containsKey("/2fa/mailtan/*"));
    assertTrue(response.getDiagrams().containsKey("/2fa/oath/*"));
    assertTrue(response.getDiagrams().containsKey("/protected/mailtan/*"));
    assertTrue(response.getDiagrams().containsKey("/protected/oath/*"));
    assertTrue(response.getErrors().isEmpty());
  }
}
