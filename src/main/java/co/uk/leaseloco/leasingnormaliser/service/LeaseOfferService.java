package co.uk.leaseloco.leasingnormaliser.service;

import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferTransferObject;
import co.uk.leaseloco.leasingnormaliser.exception.ServiceException;

import java.util.List;

/**
 * Interface for the LeaseOfferService.
 * This service provides methods for finding lease offers based on their name, type, and mileage.
 */
public interface LeaseOfferService {

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
  List<LeaseOfferTransferObject> findByParameters(
      final String name, final String type, final Long mileage, final Integer term, final Double minPrice, final Double maxPrice
  ) throws ServiceException;
}
