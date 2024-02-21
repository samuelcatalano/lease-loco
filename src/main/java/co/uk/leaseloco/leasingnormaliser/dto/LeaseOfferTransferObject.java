package co.uk.leaseloco.leasingnormaliser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LeaseOfferTransferObject is a data transfer object used to transfer lease offer data between services.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaseOfferTransferObject {

  private Long id;
  private String name;
  private String type;
  private Double price;
  private Integer term;
  private Long mileage;
  private String description;

}
