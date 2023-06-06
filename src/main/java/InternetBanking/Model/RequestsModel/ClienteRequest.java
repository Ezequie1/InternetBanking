package InternetBanking.Model.RequestsModel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    private String nome;
    private LocalDate dataNascimento;
    private boolean planoExclusive;
}
