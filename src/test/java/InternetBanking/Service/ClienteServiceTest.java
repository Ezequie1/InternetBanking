package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.RequestsModel.ClienteUpdate;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.InvalidAttributesException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void deveRetornarTodosClientes() {
        Pageable pageable = mock(Pageable.class);
        Page<Cliente> expectedClientes = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(expectedClientes);

        Page<Cliente> actualClientes = clienteService.getAllClientes(pageable);

        assertEquals(expectedClientes, actualClientes);
        verify(repository).findAll(pageable);
    }

    @Test
    void deveRetornarOClienteESalvalo() throws InvalidAttributesException {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("John Doe");
        clienteRequest.setPlanoExclusive(true);
        clienteRequest.setDataNascimento(LocalDate.now());

        Cliente expectedCliente = new Cliente(clienteRequest.getNome(), clienteRequest.isPlanoExclusive(), clienteRequest.getDataNascimento());
        when(repository.save(any(Cliente.class))).thenReturn(expectedCliente);

        Cliente actualCliente = clienteService.addCliente(clienteRequest);

        assertEquals(expectedCliente, actualCliente);
        verify(repository).save(any(Cliente.class));
    }

    @Test
    void deveRetornarExceptionQuandoEnviarClienteComCamposVazios() {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("");

        assertThrows(InvalidAttributesException.class, () -> clienteService.addCliente(clienteRequest));
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void deveAlterarOSaldoDosClientes() throws ClassNotFoundException {
        Transacao transacao = new Transacao();
        transacao.setNumeroConta("123");
        transacao.setValorTransacao(BigDecimal.valueOf(100));

        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.ZERO);
        when(repository.getByNumeroConta(transacao.getNumeroConta())).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);

        clienteService.addSaldo(transacao);

        assertEquals(BigDecimal.valueOf(100), cliente.getSaldo());
        verify(repository).save(cliente);
    }

    @Test
    void deveRetornarClienteSegundoNumeroDaConta() throws ClassNotFoundException {
        String conta = "123456";
        Cliente expectedCliente = new Cliente();
        when(repository.getByNumeroConta(conta)).thenReturn(expectedCliente);

        Cliente actualCliente = clienteService.getClienteByConta(conta);

        assertEquals(expectedCliente, actualCliente);
        verify(repository).getByNumeroConta(conta);
    }

    @Test
    void deveRetornarExceptionQuandoNaoEncontrarOCliente(){
        String conta = "123";
        when(repository.getByNumeroConta(conta)).thenReturn(null);

        assertThrows(ClassNotFoundException.class, () -> clienteService.getClienteByConta(conta));
        verify(repository).getByNumeroConta(conta);
    }

    @Test
    void deveDebitarEAlterarOSaldoDoCliente() {
        Cliente cliente = new Cliente();
        cliente.setSaldo(BigDecimal.valueOf(100));

        BigDecimal valorComTaxa = BigDecimal.valueOf(10);
        BigDecimal expectedSaldo = cliente.getSaldo().subtract(valorComTaxa);

        clienteService.debit(cliente, valorComTaxa);

        assertEquals(expectedSaldo, cliente.getSaldo());
        verify(repository).save(cliente);
    }

    @Test
    void deveDeletarAContaDeUmClienteQueExiste() throws ClassNotFoundException {
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(repository.getByNumeroConta("123456")).thenReturn(cliente);

        clienteService.deleteCliente("123456");

        verify(repository).delete(cliente);

    }

    @Test
    void deveRetornarUmaExceptionPorNaoEncontrarOCliente() throws ClassNotFoundException {
        String numeroConta = "123";
        assertThrows(ClassNotFoundException.class, () -> clienteService.deleteCliente(numeroConta));
        verify(repository, never()).delete(any(Cliente.class));
    }

    @Test
    void deveAtualizarClienteValidarERetornarOClienteAtualizado() throws ClassNotFoundException {
        String numeroConta = "123";
        ClienteUpdate clienteUpdate = new ClienteUpdate();
        clienteUpdate.setNome("John Doe");
        clienteUpdate.setPlanoExclusive(true);
        clienteUpdate.setDataNascimento(LocalDate.now());

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setNome("Jane Smith");
        clienteSalvo.setPlanoExclusive(false);
        clienteSalvo.setDataNascimento(LocalDate.now().minusYears(1));
        clienteSalvo.setNumeroConta(numeroConta);

        when(repository.getByNumeroConta(numeroConta)).thenReturn(clienteSalvo);
        when(repository.save(clienteSalvo)).thenReturn(clienteSalvo);

        Cliente updatedCliente = clienteService.atualizarCliente(clienteUpdate, numeroConta);

        assertEquals(clienteUpdate.getNome(), updatedCliente.getNome());
        assertEquals(numeroConta, updatedCliente.getNumeroConta());

        verify(transacaoService).updateTransactionsCliente(clienteSalvo);
        verify(repository).save(clienteSalvo);
    }

    @Test
    void testaSeAsAlteracoesForamSalvas() {
        Cliente cliente = new Cliente();
        clienteService.saveChanges(cliente);

        verify(repository).save(cliente);
    }
}
