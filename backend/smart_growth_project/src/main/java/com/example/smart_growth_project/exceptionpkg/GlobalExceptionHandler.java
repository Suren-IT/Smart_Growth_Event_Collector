package com.example.smart_growth_project.exceptionpkg;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //need to hold og loger(everything I see in console )

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StadardException> handleNotFound(ResourceNotFoundException rs){
        logger.warn("Resource not found: {}", rs.getMessage());
        StadardException st = new StadardException(HttpStatus.NOT_FOUND.value(),rs.getMessage());
        return  new ResponseEntity<StadardException>(st,HttpStatus.NOT_FOUND);

    }
    //duplicate user
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<StadardException> duplicateUser(DuplicateUserException ds){
        logger.warn("Duplicate User : {}", ds.getMessage());
        StadardException st = new StadardException(HttpStatus.CONFLICT.value(),ds.getMessage());
       return new ResponseEntity<>(st,HttpStatus.CONFLICT);

    }
    //external Api failing
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<StadardException> externalError(ExternalApiException es){
        logger.warn("External Api Failed : {}", es.getMessage());
        StadardException st = new StadardException(HttpStatus.SERVICE_UNAVAILABLE.value(),es.getMessage());
        return new ResponseEntity<>(st,HttpStatus.SERVICE_UNAVAILABLE);

    }

    //Common Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StadardException> commonException(RuntimeException es){
        logger.warn("Common Exception occurs  : {}", es.getMessage());
        StadardException st = new StadardException(HttpStatus.INTERNAL_SERVER_ERROR.value(),es.getMessage());
        return new ResponseEntity<>(st,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //Nohandler for wrong end point
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StadardException> handleNotFoundEndPoints(NoHandlerFoundException ne){
        logger.warn("No handelr found {} {} ",ne.getHttpMethod(),ne.getRequestURL());
        StadardException st= new StadardException(HttpStatus.NOT_FOUND.value(),"The Invalid method/endpoint ");
        return new ResponseEntity<>(st,HttpStatus.NOT_FOUND);

    }
}
