package InternetBanking.Model.ErrorModel;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlerExceptionTest {

    @Test
    public void testNotFoundException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Não foi encontrado nenhuma transação/cliente com os dados passados!"
        );

        ResponseEntity<ErrorResponse> response = handlerException.notFoundException();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }

    @Test
    public void testSaldoInsuficienteException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Não foi possível realizar a transação, saldo insuficiente!."
        );

        ResponseEntity<ErrorResponse> response = handlerException.saldoInsuficienteException();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }

    @Test
    public void testTransacaoPorDataException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Ops!!! Acho que você passou algum valor inválido, utilize a formatação yyyy-MM-dd. Exemplo: 2022-01-15"
        );

        ResponseEntity<ErrorResponse> response = handlerException.transacaoPorDataException();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }

    @Test
    public void testInvalidAttributesException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Não é possivel salvar dados vazios, preencha todos os campos!."
        );

        ResponseEntity<ErrorResponse> response = handlerException.nomeInvalido();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }

    @Test
    public void testMethodArgumentNotValidException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Necessário preencher todos os campos!."
        );

        ResponseEntity<ErrorResponse> response = handlerException.argumentosInvalidos();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }

    @Test
    public void testConstraintViolationException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "O número da conta só pode ter até 6 números!"
        );

        ResponseEntity<ErrorResponse> response = handlerException.maximoCaracteresAtingido();

        assertEquals(expectedStatus, response.getStatusCode());
    }

    @Test
    public void testHttpMessageNotReadableException() {
        HandlerException handlerException = new HandlerException();
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse expectedErrorResponse = new ErrorResponse(
                expectedStatus.value(),
                expectedStatus.getReasonPhrase(),
                "Preencha os campos valores com números ao invés de texto!"
        );

        ResponseEntity<ErrorResponse> response = handlerException.valorInvalido();

        assertEquals(expectedStatus, response.getStatusCode());
        assertEquals(expectedErrorResponse, response.getBody());
    }
}