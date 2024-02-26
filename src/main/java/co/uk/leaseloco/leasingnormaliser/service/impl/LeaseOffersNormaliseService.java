package co.uk.leaseloco.leasingnormaliser.service.impl;

import co.uk.leaseloco.leasingnormaliser.csv.CSVLoanDataProvider;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferAmazingCars;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferPrettyGoodCarDeals;
import co.uk.leaseloco.leasingnormaliser.exception.NormaliseServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaseOffersNormaliseService {

  private static final String AMAZING_CARS_PROVIDER = "pricing.csv";
  private static final String PRETTY_GOOD_CAR_DEALS_PROVIDER = "data.csv";

  private final CSVLoanDataProvider loanDataProvider;

  /**
   * Constructs a new LeaseOffersNormaliseService with the specified CSV loan data provider, lease offer repository,
   * and modelmapper.
   * @param loanDataProvider the CSV loan data provider
   */
  public LeaseOffersNormaliseService(final CSVLoanDataProvider loanDataProvider) {
    this.loanDataProvider = loanDataProvider;
  }

  /**
   * Normalises the offers from the providers.
   * @throws NormaliseServiceException if there is an error normalising the offers
   */
  public void normaliseAllOffersFromProviders() throws NormaliseServiceException {
    try {
      // Get data from Amazing Cars provider
      loanDataProvider.readAndConvertLoans(AMAZING_CARS_PROVIDER, LeaseOfferAmazingCars.class);
      // Get data from Pretty Good Car Deals provider
      loanDataProvider.readAndConvertLoans(PRETTY_GOOD_CAR_DEALS_PROVIDER, LeaseOfferPrettyGoodCarDeals.class);

    } catch (final Exception e) {
      log.error("Error normalising offers: {}", e.getMessage());
      throw new NormaliseServiceException("Error normalising offers: " + e.getMessage());
    }
  }
}
