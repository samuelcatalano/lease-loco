package co.uk.leaseloco.leasingnormaliser.service.impl;

import co.uk.leaseloco.leasingnormaliser.csv.CSVLoanDataProvider;
import co.uk.leaseloco.leasingnormaliser.domain.LeaseOffer;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferAmazingCars;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferPrettyGoodCarDeals;
import co.uk.leaseloco.leasingnormaliser.exception.NormaliseServiceException;
import co.uk.leaseloco.leasingnormaliser.repository.LeaseOfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LeaseOffersNormaliseService {

  private static final String AMAZING_CARS_PROVIDER = "pricing.csv";
  private static final String PRETTY_GOOD_CAR_DEALS_PROVIDER = "data.csv";

  private final CSVLoanDataProvider loanDataProvider;
  private final LeaseOfferRepository leaseOfferRepository;
  private final ModelMapper modelMapper;

  /**
   * Constructs a new LeaseOffersNormaliseService with the specified CSV loan data provider, lease offer repository,
   * and modelmapper.
   *
   * @param loanDataProvider     the CSV loan data provider
   * @param leaseOfferRepository the lease offer repository
   * @param modelMapper          the model mapper
   */
  public LeaseOffersNormaliseService(
      final CSVLoanDataProvider loanDataProvider,
      final LeaseOfferRepository leaseOfferRepository,
      final ModelMapper modelMapper)
  {
    this.loanDataProvider = loanDataProvider;
    this.leaseOfferRepository = leaseOfferRepository;
    this.modelMapper = modelMapper;
  }

  /**
   * Normalises the offers from the providers.
   * @throws NormaliseServiceException if there is an error normalising the offers
   */
  public void normaliseAllOffersFromProviders() throws NormaliseServiceException {
    try {
      final List<LeaseOffer> offers = new ArrayList<>();
      // Get data from Amazing Cars provider
      final List<LeaseOfferAmazingCars> amazingCarsData = loanDataProvider.readAndConvertLoans(
            AMAZING_CARS_PROVIDER, LeaseOfferAmazingCars.class);
      // Get data from Pretty Good Car Deals provider
      final List<LeaseOfferPrettyGoodCarDeals> prettyGoodDealsData = loanDataProvider.readAndConvertLoans(
            PRETTY_GOOD_CAR_DEALS_PROVIDER, LeaseOfferPrettyGoodCarDeals.class);

      offers.addAll(normaliseOffersFromProviders(amazingCarsData));
      offers.addAll(normaliseOffersFromProviders(prettyGoodDealsData));

      // Store list of normalised offers to the database
      leaseOfferRepository.saveAll(offers);

    } catch (final Exception e) {
      log.error("Error normalising offers: {}", e.getMessage());
      throw new NormaliseServiceException("Error normalising offers: " + e.getMessage());
    }
  }

  /**
   * Normalises the offers from the providers.
   *
   * @param leaseOffers the list of lease offers
   * @return the list of normalised offers
   * @throws NormaliseServiceException if there is an error normalising the offers
   */
  private List<LeaseOffer> normaliseOffersFromProviders(final List<?> leaseOffers) throws NormaliseServiceException {
    try {
      // create a new list to hold the normalised offers
      final List<LeaseOffer> offers = new ArrayList<>();
      // loop through each lease offer in the list
      for (final Object leaseOffer : leaseOffers) {
        // map the lease offer to a LeaseOffer object using ModelMapper
        final LeaseOffer offer = modelMapper.map(leaseOffer, LeaseOffer.class);
        // save the offer to the database

        offer.setName(offer.getName().toUpperCase());
        offer.setDescription(offer.getDescription() != null ? offer.getDescription() : offer.getName().toUpperCase() + " "
                                                                                     + offer.getType().toUpperCase());
        offers.add(leaseOfferRepository.save(offer));
      }
      // return the list of normalised offers
      return offers;
    } catch (final Exception e) {
      // log the error and throw a NormaliseServiceException
      log.error("Error normalising offers", e);
      throw new NormaliseServiceException("Error normalising offers", e);
    }
  }
}
