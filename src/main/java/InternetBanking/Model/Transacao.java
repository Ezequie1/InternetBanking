package InternetBanking.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idTransacao;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    public Cliente cliente;
    @Enumerated(EnumType.STRING)
    public TipoMovimentacao tipoMovimentacao;
    public BigDecimal valorTransacao;
    public String conta;
    public String contaDestino = "";
    public String horarioTransacao = formataHora();
    public LocalDate diaTransacao = LocalDate.now();

    public Transacao(BigDecimal valorTransacao, String conta, TipoMovimentacao tipoMovimentacao) {
        this.valorTransacao = valorTransacao;
        this.conta = conta;
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public String formataHora(){
        LocalDateTime hora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return hora.format(formatter);
    }
}
