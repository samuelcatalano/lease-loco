package co.uk.leaseloco.leasingnormaliser.runner;

import co.uk.leaseloco.leasingnormaliser.service.impl.LeaseOffersNormaliseService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * A component that runs the lease offers normalisation service.
 */
@Component
public class LeaseOffersNormaliseServiceRunner implements ApplicationRunner {

  /**
   * The lease offers normalisation service.
   */
  private final LeaseOffersNormaliseService leaseOffersNormaliseService;

  /**
   * Creates a new instance of {@link LeaseOffersNormaliseServiceRunner}.
   * @param leaseOffersNormaliseService the lease offers normalisation service
   */
  public LeaseOffersNormaliseServiceRunner(final LeaseOffersNormaliseService leaseOffersNormaliseService) {
    this.leaseOffersNormaliseService = leaseOffersNormaliseService;
  }

  /**
   * Runs the lease offers normalisation service.
   *
   * @param args the application arguments
   * @throws Exception if an error occurs
   */
  @Override
  public void run(final ApplicationArguments args) throws Exception {
    leaseOffersNormaliseService.normaliseAllOffersFromProviders();
  }
}
