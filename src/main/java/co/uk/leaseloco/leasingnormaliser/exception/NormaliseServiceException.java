package co.uk.leaseloco.leasingnormaliser.exception;

public class NormaliseServiceException extends Exception {

  public NormaliseServiceException() {
    super();
  }

  public NormaliseServiceException(final String message) {
    super(message);
  }

  public NormaliseServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public NormaliseServiceException(final Throwable cause) {
    super(cause);
  }
}
