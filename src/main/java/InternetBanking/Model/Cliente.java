package InternetBanking.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;
    private String nome;
    private Boolean planoExclusive = false;
    private BigDecimal saldo = new BigDecimal(0);
    @Size(max = 6)
    private String numeroConta = geradorConta();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Transacao> transacoes = new ArrayList<>();

    public Cliente(String nome, Boolean planoExclusive, LocalDate dataNascimento) {
        this.nome = nome;
        this.planoExclusive = planoExclusive;
        this.dataNascimento = dataNascimento;
    }

    public String geradorConta(){
        UUID uuid = UUID.randomUUID();
        String randomString = uuid.toString().replaceAll("[^0-9]", "");
        return randomString.substring(0, 6);
    }
}
