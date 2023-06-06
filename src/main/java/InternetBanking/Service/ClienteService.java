package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.ClienteRepository;
import InternetBanking.Repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private TransacaoService transacaoService;

    public List<Cliente> getAllClientes() {
        return repository.findAll();
    }

    public Cliente addCliente(ClienteRequest cliente) {
        return repository.save(new Cliente(cliente.getNome(), cliente.isPlanoExclusive(), cliente.getDataNascimento()));
    }

    public void addSaldo(Transacao transacao) {

        Cliente cliente = repository.getByNumeroConta(transacao.getConta());
        cliente.setSaldo(cliente.getSaldo().add(transacao.getValorTransacao()));
        repository.save(cliente);
    }

    public Cliente getClienteByConta(String conta) throws ClassNotFoundException {

        if(clienteExist(conta)) return repository.getByNumeroConta(conta);

        throw new ClassNotFoundException("Não há cliente com conta " + conta);
    }

    public Boolean clienteExist(String conta){
        return repository.getByNumeroConta(conta) != null;
    }

    public void debit(Cliente cliente, BigDecimal valorComTaxa) {

        cliente.setSaldo(cliente.getSaldo().subtract(valorComTaxa));
        repository.save(cliente);
    }
}
