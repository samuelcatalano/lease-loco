package co.uk.leaseloco.leasingnormaliser.converter;

import com.opencsv.bean.AbstractBeanField;

/**
 * A bean field that converts a string representation of a mileage value to a long value,
 * by removing the "k" suffix and multiplying the result by 1000.
 */
public class ConvertMileageFromAmazingCarsProvider extends AbstractBeanField<String, Long> {

  @Override
  protected Long convert(final String value) {
    // Remove the K suffix
    final String mileageWithoutSuffix = value.replace("k", "");
    // Convert the String to a Long value
    long mileage = Long.parseLong(mileageWithoutSuffix);
    // Multiply the value by 1000 to convert to a thousand
    mileage *= 1000;
    // Assign the result
    return mileage;
  }
}
