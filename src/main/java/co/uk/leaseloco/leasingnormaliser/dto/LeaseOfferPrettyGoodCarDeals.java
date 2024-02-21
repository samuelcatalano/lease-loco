package co.uk.leaseloco.leasingnormaliser.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object class for Lease Offer Pretty Good Car Deals data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseOfferPrettyGoodCarDeals implements Serializable {

  @CsvBindByName(column = "MAKE")
  private String make;
  @CsvBindByName(column = "MODEL")
  private String model;
  @CsvBindByName(column = "DESCRIPTION")
  private String description;
  @CsvBindByName(column = "MILEAGE")
  private Long mileage;
  @CsvBindByName(column = "PRICE")
  private Double price;
  @CsvBindByName(column = "TERM")
  private Integer term;

}
