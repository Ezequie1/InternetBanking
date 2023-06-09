package InternetBanking.Model.ErrorModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ErrorResponseTest {

    int status = 404;

    @Test
    public void testGetStatus() {
        ErrorResponse errorResponse = new ErrorResponse(status, "", "");

        int result = errorResponse.getStatus();

        assertEquals(status, result);
    }

    @Test
    public void testSetStatus() {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(status);

        assertEquals(status, errorResponse.getStatus());
    }

    @Test
    public void testGetError() {
        String error = "Not Found";
        ErrorResponse errorResponse = new ErrorResponse(0, error, "");

        String result = errorResponse.getError();

        assertEquals(error, result);
    }

    @Test
    public void testSetError() {
        String error = "Not Found";
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setError(error);

        assertEquals(error, errorResponse.getError());
    }

    @Test
    public void testGetMessage() {
        String message = "The requested resource was not found.";
        ErrorResponse errorResponse = new ErrorResponse(0, "", message);

        String result = errorResponse.getMessage();

        assertEquals(message, result);
    }

    @Test
    public void testSetMessage() {
        String message = "The requested resource was not found.";
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setMessage(message);

        assertEquals(message, errorResponse.getMessage());
    }

}