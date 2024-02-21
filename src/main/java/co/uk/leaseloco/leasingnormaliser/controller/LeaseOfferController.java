package co.uk.leaseloco.leasingnormaliser.controller;

import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferTransferObject;
import co.uk.leaseloco.leasingnormaliser.exception.ApiException;
import co.uk.leaseloco.leasingnormaliser.exception.ServiceException;
import co.uk.leaseloco.leasingnormaliser.service.LeaseOfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing lease offers.
 */
@RestController
@RequestMapping("/offers")
@Slf4j
public class LeaseOfferController {

  private final LeaseOfferService leaseOfferServiceImpl;

  /**
   * Creates a new {@link LeaseOfferController} instance.
   *
   * @param leaseOfferService the service for managing lease offers
   */
  public LeaseOfferController(final LeaseOfferService leaseOfferService) {
    this.leaseOfferServiceImpl = leaseOfferService;
  }

  /**
   * Returns a list of LeaseOffer objects filtered by name, type, and mileage.
   *
   * @param make    the name of the lease offer
   * @param model   the type of the lease offer (e.g. personal, business)
   * @param mileage the mileage of the lease offer
   * @return a list of LeaseOffer objects that match the specified criteria
   */
  @GetMapping
  public ResponseEntity<List<LeaseOfferTransferObject>> findByNameAndTypeAndMileage
  (
      @RequestParam(value = "make", required = false) final String make,
      @RequestParam(value = "model", required = false) final String model,
      @RequestParam(value = "mileage", required = false) final Long mileage,
      @RequestParam(value = "term", required = false) final Integer term,
      @RequestParam(value = "minPrice", required = false) final Double minPrice,
      @RequestParam(value = "maxPrice", required = false) final Double maxPrice
  )
  throws ApiException {
    try {
      return ResponseEntity.ok(leaseOfferServiceImpl.findByParameters(make, model, mileage, term, minPrice, maxPrice));
    } catch (final ServiceException e) {
      log.error("Error finding by parameters: {}", e.getMessage(), e);
      throw new ApiException("Error finding by parameters: {}", e);
    }
  }
}
