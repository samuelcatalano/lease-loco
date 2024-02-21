package co.uk.leaseloco.leasingnormaliser.handler;

import co.uk.leaseloco.leasingnormaliser.dto.ErrorResponse;
import co.uk.leaseloco.leasingnormaliser.exception.ApiException;
import co.uk.leaseloco.leasingnormaliser.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A {@link ControllerAdvice} class that handles exceptions thrown by the application and returns
 * appropriate error response.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle {@link ApiException} exceptions thrown by the application and return an error response
   * with HTTP status code 500 (INTERNAL_SERVER_ERROR).
   *
   * @param ex the exception to handle
   * @param request the current request
   * @return an error response with HTTP status code 500 (INTERNAL_SERVER_ERROR)
   * @ExceptionHandler Specifies the type of exception this method handles.
   */
  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<ErrorResponse> handleServiceException(final ApiException ex, final WebRequest request) {
    final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value());

    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
  }
}
