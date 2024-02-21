package co.uk.leaseloco.leasingnormaliser.controller;

import co.uk.leaseloco.leasingnormaliser.service.LeaseOfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LeaseOfferControllerTest {

  @MockBean
  private LeaseOfferService leaseOfferService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testInvalidPath() throws Exception {
    // Perform get request to invalid path
    mockMvc.perform(MockMvcRequestBuilders.get("/invalidPath")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  /**
   * Tests the findByParameters method of the LeaseOfferController class.
   */
  @Test
  void testFindByParameters() throws Exception {
    // Mock the LeaseOfferService
    when(leaseOfferService.findByParameters("make", "model", 10000L, 36, 100.0, 250.0))
        .thenReturn(new ArrayList<>());

    // Perform the GET request
    final MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
            .param("make", "audi")
            .param("model", "A4")
            .param("mileage", "10000")
            .param("term", "36")
            .param("minPrice", "0.0")
            .param("maxPrice", "300.0")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    // Verify the response
    assertEquals(200, response.getResponse().getStatus());
    assertNotNull(response.getResponse());
  }
}
