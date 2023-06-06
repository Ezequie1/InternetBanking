package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private ClienteService clienteService;

    public List<Transacao> getAllTransacoes(String data) {
        return repository.getByDiaTransacao(LocalDate.parse(data, DateTimeFormatter.ISO_DATE));
    }

    public List<Transacao> getTransacoesPorTipo(TipoMovimentacao movimentacao, String data) {
        return repository.getTrasacoesByTipoTransacaoAndDate(movimentacao, LocalDate.parse(data, DateTimeFormatter.ISO_DATE));
    }

    public Transacao depositar(TransacaoRequest transacao) throws ClassNotFoundException {
        Transacao novaTransacao = new Transacao(transacao.valorTransacao, transacao.conta, TipoMovimentacao.DEPOSITO);
        clienteService.addSaldo(novaTransacao);
        addTransacao(novaTransacao);
        return novaTransacao;
    }

    public Transacao sacar(TransacaoRequest transacao) throws ClassNotFoundException {

        if(hasSaldo(transacao)){

            Cliente cliente = clienteService.getClienteByConta(transacao.conta);
            Transacao novaTransacao = new Transacao(transacao.valorTransacao, transacao.conta, TipoMovimentacao.SAQUE);

            addTransacao(novaTransacao);
            clienteService.debit(cliente, valorComTaxa(transacao.valorTransacao, cliente.getPlanoExclusive()));

            return novaTransacao;
        }

        throw new RuntimeException("Saldo insuficiente!");
    }

    public Boolean hasSaldo(TransacaoRequest transacao) throws ClassNotFoundException {

        Cliente cliente = clienteService.getClienteByConta(transacao.conta);
        BigDecimal valorFinal = valorComTaxa(transacao.getValorTransacao(), cliente.getPlanoExclusive());

        return cliente.getSaldo().compareTo(valorFinal) >= 0;
    }

    public void addTransacao(Transacao transacao) throws ClassNotFoundException {
        transacao.setCliente(clienteService.getClienteByConta(transacao.getConta()));
        repository.save(transacao);
    }

    private BigDecimal valorComTaxa(BigDecimal valorTransacao, boolean planoExclusive) {

        if(planoExclusive){
            return valorTransacao;
        } else if (valorTransacao.compareTo(new BigDecimal("100.00")) > 0 && valorTransacao.compareTo(new BigDecimal("300.00")) <= 0 ) {
            return valorTransacao.add(valorTransacao.multiply(new BigDecimal("0.004")));
        } else if (valorTransacao.compareTo(new BigDecimal("300.00")) >= 0) {
            return valorTransacao.add(valorTransacao.multiply(new BigDecimal("0.01")));
        }

        return valorTransacao;
    }
}
