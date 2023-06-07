package InternetBanking.Model.RequestsModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransacaoRequest {

    public BigDecimal valorTransacao;
    public String conta;

    public TransacaoRequest(BigDecimal valorTransacao, String conta) {
        this.valorTransacao = valorTransacao;
        this.conta = conta;
    }

    public TransacaoRequest(BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }
}
