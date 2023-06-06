package InternetBanking.Model.RequestsModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    @NotBlank
    private String nome;
    @Past
    private LocalDate dataNascimento;
    private boolean planoExclusive;
}
