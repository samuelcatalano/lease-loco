package co.uk.leaseloco.leasingnormaliser.service;

import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferTransferObject;
import co.uk.leaseloco.leasingnormaliser.exception.NormaliseServiceException;
import co.uk.leaseloco.leasingnormaliser.exception.ServiceException;
import co.uk.leaseloco.leasingnormaliser.repository.LeaseOfferRepository;
import co.uk.leaseloco.leasingnormaliser.service.impl.LeaseOfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the LeaseOfferService class.
 */
@SpringBootTest
@Transactional
class LeaseOfferServiceTest {

  @Autowired
  private LeaseOfferServiceImpl leaseOfferService;

  @Autowired
  private LeaseOfferRepository leaseOfferRepository;

  @InjectMocks
  private ModelMapper modelMapper;

  /**
   * Sets up the test environment.
   *
   * @throws NormaliseServiceException if an error occurs while normalising the data
   */
  @BeforeEach
  public void setUp() throws NormaliseServiceException {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Tests the findByParameters method.
   *
   * @throws ServiceException if an error occurs while processing the request
   */
  @Test
  void findByParameters() throws ServiceException {
    final String name = "audi";
    final String type = "A4";
    final Long mileage = 10000L;
    final Integer term = 24;
    final Double minPrice = 0.0;
    final Double maxPrice = 500.0;

    // Execute the method to be tested
    final List<LeaseOfferTransferObject> result = leaseOfferService.findByParameters(name, type, mileage, term, minPrice, maxPrice);

    // Verify the result
    assertEquals(2, result.size());
  }

  /**
   * Tests the findByParameters method with invalid input.
   *
   * @throws ServiceException if an error occurs while processing the request
   */
  @Test
  void findByParameters_Should_Return_Zero() throws ServiceException {
    final String name = "bmw";
    final String type = "118d";
    final Long mileage = 10000L;
    final Integer term = 36;
    final Double minPrice = 0.0;
    final Double maxPrice = 500.0;

    // Execute the method to be tested
    final List<LeaseOfferTransferObject> result = leaseOfferService.findByParameters(name, type, mileage, term, minPrice, maxPrice);

    // Verify the result
    assertEquals(0, result.size());
  }

  /**
   * Tests the findByParameters method with no filters.
   *
   * @throws ServiceException if an error occurs while processing the request
   */
  @Test
  void findByParameters_Should_Return_All_Entries() throws ServiceException {
    final String name = "";
    final String type = "";
    final Long mileage = null;
    final Integer term = null;
    final Double minPrice = null;
    final Double maxPrice = null;

    // Execute the method to be tested
    final List<LeaseOfferTransferObject> result = leaseOfferService.findByParameters(name, type, mileage, term, minPrice, maxPrice);

    // Verify the result
    assertEquals(12, result.size());
  }
}
