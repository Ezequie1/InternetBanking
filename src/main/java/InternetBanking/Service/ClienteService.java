package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.RequestsModel.ClienteUpdate;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private TransacaoService transacaoService;

    public Page<Cliente> getAllClientes(Pageable pagina) {
        return repository.findAll(pagina);
    }

    public Cliente addCliente(ClienteRequest cliente) throws InvalidAttributesException {

        if (cliente.getNome().isEmpty()) throw new InvalidAttributesException();

        return repository.save(new Cliente(cliente.getNome(), cliente.isPlanoExclusive(), cliente.getDataNascimento()));
    }

    public void addSaldo(Transacao transacao) throws ClassNotFoundException {
        Cliente cliente = getClienteByConta(transacao.getNumeroConta());

        cliente.setSaldo(cliente.getSaldo().add(transacao.getValorTransacao()));
        repository.save(cliente);
    }

    public Cliente getClienteByConta(String conta) throws ClassNotFoundException {

        Cliente cliente = repository.getByNumeroConta(conta);

        if (cliente != null){
            return cliente;
        }else{
            throw new ClassNotFoundException();
        }
    }

    public void debit(Cliente cliente, BigDecimal valorComTaxa) {

        cliente.setSaldo(cliente.getSaldo().subtract(valorComTaxa));
        repository.save(cliente);
    }

    public void deleteCliente(String numeroConta) throws ClassNotFoundException {
        Cliente cliente = getClienteByConta(numeroConta);
        repository.delete(cliente);
    }

    public Cliente atualizarCliente(ClienteUpdate cliente, String numeroConta) throws ClassNotFoundException {

        Cliente clienteSalvo = getClienteByConta(numeroConta);
        clienteSalvo.setNome( cliente.getNome().equals("") ? clienteSalvo.getNome() : cliente.getNome());
        clienteSalvo.setPlanoExclusive( cliente.isPlanoExclusive());
        clienteSalvo.setDataNascimento( cliente.getDataNascimento().equals(LocalDate.now()) ? clienteSalvo.getDataNascimento() : cliente.getDataNascimento());
        clienteSalvo.setNumeroConta( cliente.getNumeroConta() == null ? clienteSalvo.getNumeroConta() : cliente.getNumeroConta());

        transacaoService.updateTransactionsCliente(clienteSalvo);

        return repository.save(clienteSalvo);
    }

    public void saveChanges(Cliente cliente) {
        repository.save(cliente);
    }
}
