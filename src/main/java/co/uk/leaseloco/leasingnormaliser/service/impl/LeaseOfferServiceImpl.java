package co.uk.leaseloco.leasingnormaliser.service.impl;

import co.uk.leaseloco.leasingnormaliser.domain.LeaseOffer;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferTransferObject;
import co.uk.leaseloco.leasingnormaliser.exception.ServiceException;
import co.uk.leaseloco.leasingnormaliser.repository.LeaseOfferRepository;
import co.uk.leaseloco.leasingnormaliser.service.LeaseOfferService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service class for handling lease offers.
 */
@Service
@Slf4j
public class LeaseOfferServiceImpl implements LeaseOfferService {

  private final LeaseOfferRepository leaseOfferRepository;
  private final ModelMapper modelMapper;

  /**
   * Constructs a new LeaseOfferServiceImpl with the given LeaseOfferRepository.
   * @param leaseOfferRepository the repository for handling lease offers
   */
  public LeaseOfferServiceImpl(final LeaseOfferRepository leaseOfferRepository, final ModelMapper modelMapper) {
    this.leaseOfferRepository = leaseOfferRepository;
    this.modelMapper = modelMapper;
  }

  /**
   * Finds all lease offers with the given name, type, and mileage.
   *
   * @param name     the name of the lease offers to find
   * @param type     the type of the lease offers to find
   * @param mileage  the mileage of the lease offers to find
   * @param term     the term of the lease offers to find
   * @param minPrice the minimum price of the lease offers to find
   * @param maxPrice the maximum price of the lease offers to find
   * @return a list of lease offers with the given name, type, and mileage
   * @throws ServiceException if there was an error retrieving the lease offers
   */
  @Override
  public List<LeaseOfferTransferObject> findByParameters(
      final String name, final String type, final Long mileage, final Integer term, final Double minPrice, final Double maxPrice
  )
  throws ServiceException {
    try {
      final List<LeaseOffer> leaseOffers = leaseOfferRepository.findByParameters(name, type, mileage, term, minPrice, maxPrice);
      return modelMapper.map(leaseOffers, new TypeToken<List<LeaseOfferTransferObject>>() {}.getType());
    } catch (final Exception e) {
      log.error("Error retrieving all lease offers by name, type and mileage: {}", e.getMessage());
      throw new ServiceException("Error retrieving all lease offers by name, type and mileage", e);
    }
  }
}
