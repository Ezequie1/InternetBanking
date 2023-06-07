package InternetBanking.Model.ErrorModel;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.directory.InvalidAttributesException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class HandlerException extends RuntimeException{

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Não foi encontrado nenhuma transação/cliente com os dados passados!"
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> saldoInsuficienteException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Não foi possível realizar a transação, saldo insuficiente!."
        ));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> transacaoPorDataException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Ops!!! Acho que você passou algum valor inválido, utilize a formatação yyyy-MM-dd. Exemplo: 2022-01-15"

        ));
    }

    @ExceptionHandler(InvalidAttributesException.class)
    public ResponseEntity<ErrorResponse> nomeInvalido() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Não é possivel salvar dados vazios, preencha todos os campos!."
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentosInvalidos() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Necessário preencher todos os campos!."
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> maximoCaracteresAtingido() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "O número da conta só pode ter até 6 números!"
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> valorInvalido() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Preencha os campos valores com números ao invés de texto!"
        ));
    }



}
