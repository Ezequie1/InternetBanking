package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.TransacaoUpdate;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
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

    public Page<Transacao> getAllTransacoes(String data, Pageable pageable) {
        return repository.getByDiaTransacao(LocalDate.parse(data, DateTimeFormatter.ISO_DATE), pageable);
    }

    public Page<Transacao> getTransacoesPorTipo(TipoMovimentacao movimentacao, String data, Pageable pageable) {
        return repository.getByDiaTransacaoAndTipoMovimentacao(movimentacao, LocalDate.parse(data, DateTimeFormatter.ISO_DATE), pageable);
    }

    public Transacao depositar(TransacaoRequest transacao) throws ClassNotFoundException {
        Transacao novaTransacao = new Transacao(transacao.valorTransacao, transacao.conta, TipoMovimentacao.DEPOSITO);
        clienteService.addSaldo(novaTransacao);
        addTransacao(novaTransacao);
        return novaTransacao;
    }

    public Transacao sacar(TransacaoRequest transacao) throws ClassNotFoundException {
        Cliente cliente = clienteService.getClienteByConta(transacao.conta);

        if(hasSaldo(transacao, cliente)){

            Transacao novaTransacao = new Transacao(transacao.valorTransacao, transacao.conta, TipoMovimentacao.SAQUE);

            addTransacao(novaTransacao);
            clienteService.debit(cliente, valorComTaxa(transacao.valorTransacao, cliente.getPlanoExclusive()));

            return novaTransacao;
        }

        throw new IllegalArgumentException("Não foi possível realizar a transação, saldo insuficiente!");
    }

    public Boolean hasSaldo(TransacaoRequest transacao, Cliente cliente) {

        BigDecimal valorFinal = valorComTaxa(transacao.getValorTransacao(), cliente.getPlanoExclusive());

        return cliente.getSaldo().compareTo(valorFinal) >= 0;
    }

    public void addTransacao(Transacao transacao) throws ClassNotFoundException {
        transacao.setCliente(clienteService.getClienteByConta(transacao.getNumeroConta()));
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

    public void updateTransactionsCliente(Cliente cliente) {

        List<Transacao> transacoes = cliente.getTransacoes();

        transacoes.forEach( transacao -> {
            transacao.setNumeroConta(cliente.getNumeroConta());
            repository.save(transacao);
        });
    }

    public Page<Transacao> getTransacoesPorConta(String conta, Pageable pageable) throws ClassNotFoundException {
        return repository.getByCliente( clienteService.getClienteByConta(conta) , pageable);
    }

    public void cancelaTransacao(String numeroConta, Long idTransacao) throws ClassNotFoundException {
        Cliente cliente = clienteService.getClienteByConta(numeroConta);
        Transacao transacao = repository.getByIdTransacaoAndCliente(numeroConta, idTransacao);

        if(transacao.getTipoMovimentacao() == TipoMovimentacao.SAQUE){
            cliente.setSaldo(cliente.getSaldo().add(transacao.getValorTransacao()));
        }else if (transacao.getTipoMovimentacao() == TipoMovimentacao.DEPOSITO) {
            cliente.setSaldo(cliente.getSaldo().subtract(transacao.getValorTransacao()));
        }

        clienteService.saveChanges(cliente);
        repository.delete(transacao);

    }

    public Transacao atualizaTransacao(TransacaoUpdate novaTransacao, String numeroConta, Long idTransacao) throws ClassNotFoundException {
        Cliente cliente = clienteService.getClienteByConta(numeroConta);
        Transacao transacao = repository.getByIdTransacaoAndCliente(numeroConta, idTransacao);

        int valor = transacao.getValorTransacao().compareTo(novaTransacao.getValorTransacao());

        if(transacao.getTipoMovimentacao() == TipoMovimentacao.SAQUE){
            cliente.setSaldo( valor > 0 ?
                    cliente.getSaldo().subtract(novaTransacao.getValorTransacao().subtract(transacao.getValorTransacao())) :
                    cliente.getSaldo().add(transacao.getValorTransacao().subtract(novaTransacao.getValorTransacao())));

        }else if (transacao.getTipoMovimentacao() == TipoMovimentacao.DEPOSITO) {
            cliente.setSaldo( valor < 0 ?
                    cliente.getSaldo().subtract(transacao.getValorTransacao().subtract(novaTransacao.getValorTransacao())) :
                    cliente.getSaldo().add(novaTransacao.getValorTransacao().subtract(transacao.getValorTransacao())));
        }

        transacao.setValorTransacao(novaTransacao.getValorTransacao());
        clienteService.saveChanges(cliente);
        repository.save(transacao);

        return transacao;
    }
}
