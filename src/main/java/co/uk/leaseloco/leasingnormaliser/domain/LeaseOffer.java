package co.uk.leaseloco.leasingnormaliser.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * LeaseOffer class represents a lease offer.
 * It contains information such as id, name, type, price, term, mileage, make, model, and description.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "lease_offer")
public class LeaseOffer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String type;
  private Double price;
  private Integer term;
  private Long mileage;
  private String description;

}
