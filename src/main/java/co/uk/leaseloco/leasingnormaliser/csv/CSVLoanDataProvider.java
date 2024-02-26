package co.uk.leaseloco.leasingnormaliser.csv;

import co.uk.leaseloco.leasingnormaliser.domain.LeaseOffer;
import co.uk.leaseloco.leasingnormaliser.exception.CsvLoanDataException;
import co.uk.leaseloco.leasingnormaliser.repository.LeaseOfferRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A component that provides a data provider for reading and converting loans from CSV files.
 */
@Component
@Slf4j
public class CSVLoanDataProvider {

  private final LeaseOfferRepository leaseOfferRepository;
  private final ModelMapper modelMapper;

  /**
   * Constructs a new CSVLoanDataProvider with the specified LeaseOfferRepository and ModelMapper.
   * @param leaseOfferRepository the LeaseOfferRepository to use  for normalising the offers
   */
  public CSVLoanDataProvider(final LeaseOfferRepository leaseOfferRepository, final ModelMapper modelMapper) {
    this.leaseOfferRepository = leaseOfferRepository;
    this.modelMapper = modelMapper;
  }

  /**
   * Reads and converts loans from a CSV file.
   *
   * @param <T>         the type of loan to read and convert
   * @param filePath    the path of the CSV file to read
   * @param targetClass the class of the loan
   * @throws CsvLoanDataException if there is an error reading or converting the loans
   */
  public <T> void readAndConvertLoans(final String filePath, final Class<T> targetClass) throws CsvLoanDataException {
    try {
      final ClassPathResource resource = new ClassPathResource(filePath);
      final InputStream inputStream = resource.getInputStream();
      final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      final CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
           .withType(targetClass)
           .withIgnoreLeadingWhiteSpace(true)
           .build();
      csvToBean.stream().parallel().forEach(this::normaliseAndSaveOffer);
    } catch (final Exception e) {
      log.error("Error reading and converting loans from file path : {}", e.getMessage());
      throw new CsvLoanDataException("Failed to map and insert entry from file path : " + filePath, e);
    }
  }

  /**
   * Normalizes the name and description of the provided offer and saves it to the repository.
   * <p>This method takes a generic offer object, maps it to a {@link LeaseOffer} using a ModelMapper,
   * and then normalizes the name and description attributes of the lease. The name is converted to uppercase,
   * and the description is set to the uppercase name concatenated with the uppercase offer type if available,
   * or just the uppercase name if the type is null. The resulting {@code LeaseOffer} is then saved to the repository.
   *
   * @param offer The generic offer object to be normalized. It should be mappable to a {@link LeaseOffer}.
   * @param <T>   The type of the offer object.
   * @throws org.modelmapper.MappingException If mapping from the offer to {@link LeaseOffer} fails.
   * @see LeaseOffer
   */
  private <T> void normaliseAndSaveOffer(final T offer) {
    final LeaseOffer lease = modelMapper.map(offer, LeaseOffer.class);
    lease.setName(lease.getName().toUpperCase());
    lease.setDescription(lease.getDescription() != null ? lease.getDescription() : lease.getName().toUpperCase() + " "
        + lease.getType().toUpperCase());
    leaseOfferRepository.save(lease);
  }
}
