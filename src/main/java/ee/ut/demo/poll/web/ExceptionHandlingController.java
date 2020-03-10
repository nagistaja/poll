package ee.ut.demo.poll.web;

import ee.ut.demo.poll.constant.ErrorCodes;
import ee.ut.demo.poll.dto.FieldError;
import ee.ut.demo.poll.dto.response.CommonErrorResponse;
import ee.ut.demo.poll.dto.response.ExtendedErrorResponse;
import ee.ut.demo.poll.exception.ApplicationException;
import ee.ut.demo.poll.exception.InvalidParameterException;
import ee.ut.demo.poll.exception.NonUniqueException;
import ee.ut.demo.poll.exception.NotFoundException;
import ee.ut.demo.poll.service.util.DateUtilService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    private final DateUtilService dateUtilService;

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    public final ResponseEntity<CommonErrorResponse> exceptionHandler(final Exception ex) {
        log.debug("Got exception : {}", ex);
        CommonErrorResponse error = new CommonErrorResponse();
        error.setTimestampAsStr(getDateUtilService().getInstantNow());
        HttpStatus currStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(),
                ResponseStatus.class);
        if (responseStatus != null) {
            currStatus = responseStatus.value();
            error.setMessage(responseStatus.reason());
            error.setCode(ErrorCodes.APPLICATION_EXCEPTION);
        } else {
            error.setMessage("system_exception");
            error.setCode(ErrorCodes.UNKNOWN_EXCEPTION);
        }
        return new ResponseEntity<CommonErrorResponse>(error, currStatus);
    }

    @Override
    protected final ResponseEntity<Object> handleExceptionInternal(final Exception ex,
                                                                   final Object body, final HttpHeaders headers, final HttpStatus status,
                                                                   final WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (body == null) {
            CommonErrorResponse error = new CommonErrorResponse();
            error.setTimestampAsStr(getDateUtilService().getInstantNow());
            error.setCode(ErrorCodes.APPLICATION_EXCEPTION);
            error.setMessage(ex.getMessage());
            return new ResponseEntity<>(error, headers, status);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(value = { ApplicationException.class })
    public final ResponseEntity<CommonErrorResponse> handleApplicationException(
            final Exception ex) {
        CommonErrorResponse error = new CommonErrorResponse();
        error.setTimestampAsStr(getDateUtilService().getInstantNow());
        error.setMessage("application_exception");
        error.setCode(ErrorCodes.APPLICATION_EXCEPTION);
        return new ResponseEntity<CommonErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    public final ResponseEntity<CommonErrorResponse> handleNotFound(final Exception ex) {
        CommonErrorResponse error = new CommonErrorResponse();
        error.setTimestampAsStr(getDateUtilService().getInstantNow());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<CommonErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public final ResponseEntity<CommonErrorResponse> handleIllegalArgumentException(
            final Exception ex) {
        CommonErrorResponse error = new CommonErrorResponse();
        error.setTimestampAsStr(getDateUtilService().getInstantNow());
        error.setMessage(ex.getMessage());
        error.setCode(ErrorCodes.APPLICATION_EXCEPTION);
        return new ResponseEntity<CommonErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InvalidParameterException.class, NonUniqueException.class })
    public final ResponseEntity<Object> handleInvalidParameterException(
            final InvalidParameterException ex) {
        ExtendedErrorResponse<FieldError> error = new ExtendedErrorResponse<FieldError>();
        error.setTimestampAsStr(getDateUtilService().getInstantNow());
        error.setMessage("wrong_data");
        error.setCode(ErrorCodes.INVALID_DATA);
        List<FieldError> fieldErrors = new ArrayList<>(1);
        FieldError fe = new FieldError(ex.getParameter(), ex.getMessage());
        fieldErrors.add(fe);
        error.setExtraData(fieldErrors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
