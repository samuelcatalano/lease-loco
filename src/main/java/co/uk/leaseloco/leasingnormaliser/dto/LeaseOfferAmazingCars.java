package co.uk.leaseloco.leasingnormaliser.dto;

import co.uk.leaseloco.leasingnormaliser.converter.ConvertMileageFromAmazingCarsProvider;
import co.uk.leaseloco.leasingnormaliser.converter.ConvertPriceFromAmazingCarsProvider;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object class for Lease Offer from Amazing Cars data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseOfferAmazingCars implements Serializable {

  @CsvBindByName(column = "name")
  private String name;
  @CsvBindByName(column = "type")
  private String type;
  @CsvCustomBindByName(converter = ConvertPriceFromAmazingCarsProvider.class)
  private Double price;
  @CsvBindByName(column = "term")
  private Integer term;
  @CsvCustomBindByName(converter = ConvertMileageFromAmazingCarsProvider.class)
  private Long mileage;

}
