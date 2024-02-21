package co.uk.leaseloco.leasingnormaliser.config;

import co.uk.leaseloco.leasingnormaliser.mapper.LeaseOfferPrettyGoodCarDealsToLeaseOffer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures ModelMapper for the application.
 * Adds a mapping between LeaseOfferPrettyGoodCarDealsToLeaseOffer.
 */
@Configuration
public class ModelMapperConfig {

  /**
   * Configures ModelMapper for the application.
   * Adds a mapping between LeaseOfferPrettyGoodCarDealsToLeaseOffer.
   * Adds a mapping between LeaseOfferAmazingCarsToLeaseOffer.
   *
   * @return ModelMapper instance
   */
  @Bean
  public ModelMapper modelMapper() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(new LeaseOfferPrettyGoodCarDealsToLeaseOffer());
    return modelMapper;
  }
}
