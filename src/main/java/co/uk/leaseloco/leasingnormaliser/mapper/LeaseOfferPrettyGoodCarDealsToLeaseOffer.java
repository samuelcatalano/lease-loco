package co.uk.leaseloco.leasingnormaliser.mapper;

import co.uk.leaseloco.leasingnormaliser.domain.LeaseOffer;
import co.uk.leaseloco.leasingnormaliser.dto.LeaseOfferPrettyGoodCarDeals;
import org.modelmapper.PropertyMap;

/**
 * Maps a LeaseOfferPrettyGoodCarDeals object to a LeaseOffer object.
 */
public class LeaseOfferPrettyGoodCarDealsToLeaseOffer extends PropertyMap<LeaseOfferPrettyGoodCarDeals, LeaseOffer> {

  /**
   * Configures the mapping between the source and target objects.
   */
  @Override
  protected void configure() {
    map().setName(source.getMake());
    map().setType(source.getModel());
  }
}
