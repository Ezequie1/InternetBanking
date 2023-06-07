package InternetBanking.Model.RequestsModel;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteUpdate {

    @NotBlank
    private String nome;
    @Past
    private LocalDate dataNascimento;
    private boolean planoExclusive;
    private String numeroConta;
}
