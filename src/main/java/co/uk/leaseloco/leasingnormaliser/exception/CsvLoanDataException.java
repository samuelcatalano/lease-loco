package co.uk.leaseloco.leasingnormaliser.exception;

public class CsvLoanDataException extends Exception {

  public CsvLoanDataException() {
    super();
  }

  public CsvLoanDataException(final String message) {
    super(message);
  }

  public CsvLoanDataException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CsvLoanDataException(final Throwable cause) {
    super(cause);
  }
}
