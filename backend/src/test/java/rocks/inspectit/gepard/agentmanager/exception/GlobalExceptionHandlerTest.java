/* (C) 2024 */
package rocks.inspectit.gepard.agentmanager.exception;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  void handleValidationErrors() {
    MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = Mockito.mock(BindingResult.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);

    Mockito.when(bindingResult.getFieldErrors())
        .thenReturn(List.of(new FieldError("requestURI", "fieldError", "field error")));

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleValidationErrors(exception, httpServletRequest);

    assertEquals(List.of("field error"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleBadRequestError() {
    HttpMessageNotReadableException exception = Mockito.mock(HttpMessageNotReadableException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleBadRequestError(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleMethodArgumentTypeMismatch() {
    MethodArgumentTypeMismatchException exception =
        Mockito.mock(MethodArgumentTypeMismatchException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleMethodArgumentTypeMismatch(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleNotFoundError() {
    NoSuchElementException exception = Mockito.mock(NoSuchElementException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleNotFoundError(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void handleFileAccessException_INTERNAL_SERVER_ERROR() {
    FileAccessException exception = Mockito.mock(FileAccessException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");
    Mockito.when(exception.getHttpStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleFileAccessException(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void handleFileAccessException_NOT_FOUND() {
    FileAccessException exception = Mockito.mock(FileAccessException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");
    Mockito.when(exception.getHttpStatus()).thenReturn(HttpStatus.NOT_FOUND);

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleFileAccessException(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void handleGitOperationException() {
    GitOperationException exception = Mockito.mock(GitOperationException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleGitOperationException(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void handleJsonParseError() {
    JsonParseException exception = Mockito.mock(JsonParseException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleJsonParseError(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void handleUnrecognizedPropertyError() {
    UnrecognizedPropertyException exception = Mockito.mock(UnrecognizedPropertyException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleUnrecognizedProperty(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleInvalidPattern() {
    InvalidPatternException exception = Mockito.mock(InvalidPatternException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleInvalidPattern(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleInvalidPatternSyntax() {
    PatternSyntaxException exception = Mockito.mock(PatternSyntaxException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleInvalidPatternSyntax(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleMissingHeaderException() {
    MissingHeaderException exception = Mockito.mock(MissingHeaderException.class);
    HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn("requestURI");
    Mockito.when(exception.getMessage()).thenReturn("exception message");

    ResponseEntity<ApiError> response =
        globalExceptionHandler.handleMissingHeaderException(exception, httpServletRequest);

    assertEquals(List.of("exception message"), Objects.requireNonNull(response.getBody()).errors());
    assertEquals("requestURI", response.getBody().path());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
