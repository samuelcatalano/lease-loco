package co.uk.leaseloco.leasingnormaliser.converter;

import com.opencsv.bean.AbstractBeanField;

/**
 * A bean field that converts a string price value to a double value,
 * by removing the pound symbol and converting to a double.
 */
public class ConvertPriceFromAmazingCarsProvider extends AbstractBeanField<String, Double> {

  @Override
  protected Double convert(final String value) {
    // Remove the £ symbol
    final String priceWithoutSymbol = value.replace("£", "");
    // Convert the string to a Double value
    return Double.parseDouble(priceWithoutSymbol);
  }
}
