package gatech.course.optimizer.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by 204069126 on 4/7/15.
 */
public class WebServiceException extends RuntimeException {

    private HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public WebServiceException() {
        super();
    }

    public WebServiceException(String message, Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, HttpStatus status) {
        super(message);
        this.responseStatus = status;
    }

    public WebServiceException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(HttpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
