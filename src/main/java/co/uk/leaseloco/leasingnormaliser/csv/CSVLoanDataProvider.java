package co.uk.leaseloco.leasingnormaliser.csv;

import co.uk.leaseloco.leasingnormaliser.exception.CsvLoanDataException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A component that provides a data provider for reading and converting loans from CSV files.
 */
@Component
@Slf4j
public class CSVLoanDataProvider {

  /**
   * Reads and converts loans from a CSV file.
   *
   * @param <T>         the type of loan to read and convert
   * @param filePath    the path of the CSV file to read
   * @param targetClass the class of the loan
   * @return a list of loans read from the CSV file
   * @throws CsvLoanDataException if there is an error reading or converting the loans
   */
  public <T> List<T> readAndConvertLoans(final String filePath, final Class<T> targetClass) throws CsvLoanDataException {
    try {
      final ClassPathResource resource = new ClassPathResource(filePath);
      final InputStream inputStream = resource.getInputStream();
      final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      final CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
          .withType(targetClass)
          .withIgnoreLeadingWhiteSpace(true)
          .build();
      return new ArrayList<>(csvToBean.parse());
    } catch (final Exception e) {
      throw new CsvLoanDataException("Failed to find file: " + filePath, e);
    }
  }
}
