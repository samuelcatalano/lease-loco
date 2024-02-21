package co.uk.leaseloco.leasingnormaliser.csv;

import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferAmazingCars;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferPrettyGoodCarDeals;
import co.uk.leaseloco.leasingnormaliser.exception.CsvLoanDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the {@link CSVLoanDataProvider} class.
 */
@ExtendWith(MockitoExtension.class)
class CSVLoanDataProviderTest {

  private static final String AMAZING_CARS_PROVIDER = "pricing.csv";
  private static final String PRETTY_GOOD_CAR_DEALS_PROVIDER = "data.csv";

  /**
   * Mocked {@link ResourceLoader} instance.
   */
  @Mock
  private ResourceLoader resourceLoader;

  /**
   * Mocked {@link Resource} instance.
   */
  @Mock
  private Resource resource;

  /**
   * Tested {@link CSVLoanDataProvider} instance.
   */
  @InjectMocks
  private CSVLoanDataProvider csvLoanDataProvider;

  /**
   * Tests reading and converting loans from the "Amazing Cars" CSV file.
   */
  @Test
  void testReadAndConvertLoans_Amazing_Cars_Provider() throws IOException, CsvLoanDataException {
    final List<LeaseOfferAmazingCars> loans = csvLoanDataProvider.readAndConvertLoans(AMAZING_CARS_PROVIDER, LeaseOfferAmazingCars.class);
    assertEquals(6, loans.size());
  }

  /**
   * Tests reading and converting loans from the "Pretty Good Car Deals" CSV file.
   */
  @Test
  void testReadAndConvertLoans_Pretty_Good_Car_Deals_Provider() throws CsvLoanDataException {
    final List<LeaseOfferPrettyGoodCarDeals> loans = csvLoanDataProvider.readAndConvertLoans(PRETTY_GOOD_CAR_DEALS_PROVIDER,
          LeaseOfferPrettyGoodCarDeals.class);

    assertEquals(6, loans.size());
  }

  /**
   * Tests that an exception is thrown when the file path is incorrect.
   */
  @Test
  void testReadAndConvertLoans_File_Path_Incorrect() {
    final String filePath = "test.csv";

    assertThrows(CsvLoanDataException.class, () -> {
      csvLoanDataProvider.readAndConvertLoans(filePath, LeaseOfferAmazingCars.class);
    });
  }
}
