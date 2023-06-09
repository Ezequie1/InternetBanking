package InternetBanking.Controller;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.RequestsModel.ClienteUpdate;
import InternetBanking.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.InvalidAttributesException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ClienteControllerTest {

    @Mock
    private ClienteService serviceMock;

    @InjectMocks
    private ClienteController controller;

    private List<Cliente> clientes = new ArrayList<>();

    @Test
    void deveRetornarListaDeClientes() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Cliente> clientePage = new PageImpl<>(clientes, pageable, clientes.size());

        when(serviceMock.getAllClientes(pageable)).thenReturn(clientePage);

        ResponseEntity<Page<Cliente>> response = controller.getClientes(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientePage, response.getBody());
        verify(serviceMock, times(1)).getAllClientes(pageable);
    }

    @Test
    void deveRetornarClienteQuandoSerPassadoContaValida() throws ClassNotFoundException {
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.getClienteByConta("123456")).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.getClientes("123456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(serviceMock, times(1)).getClienteByConta("123456");
    }

    @Test
    void deveRetornarClassNotFoundExceptionQuandoEnviadoContaInexistente() throws ClassNotFoundException {
        when(serviceMock.getClienteByConta("654321")).thenThrow(ClassNotFoundException.class);

        assertThrows(ClassNotFoundException.class, () -> controller.getClientes("654321"));
        verify(serviceMock, times(1)).getClienteByConta("654321");
    }

    @Test
    void deveCriarClienteQuandoPassarClienteRequesteERetornarClienteCriado() throws InvalidAttributesException {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("Ezequiel");
        clienteRequest.setDataNascimento(LocalDate.now());
        clienteRequest.setPlanoExclusive(true);
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.addCliente(clienteRequest)).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.addCliente(clienteRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(serviceMock, times(1)).addCliente(clienteRequest);
    }

    @Test
    void deveLancarInvalidAttributeExceptionQuandoNomeClienteEmpty() throws InvalidAttributesException {
        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setNome("");
        clienteRequest.setDataNascimento(LocalDate.now());
        clienteRequest.setPlanoExclusive(true);

        when(serviceMock.addCliente(clienteRequest)).thenThrow(InvalidAttributesException.class);

        assertThrows(InvalidAttributesException.class, () -> controller.addCliente(clienteRequest));
        verify(serviceMock, times(1)).addCliente(clienteRequest);
    }

    @Test
    void deveAtualizarContaERetornarAContaAtualizada() throws ClassNotFoundException {
        ClienteUpdate clienteUpdate = new ClienteUpdate();
        clienteUpdate.setNome("Ezequiel");
        clienteUpdate.setPlanoExclusive(true);
        String numeroConta = "123456";
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.atualizarCliente(clienteUpdate, numeroConta)).thenReturn(cliente);

        ResponseEntity<Cliente> response = controller.atualizarConta(clienteUpdate, numeroConta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
        verify(serviceMock, times(1)).atualizarCliente(clienteUpdate, numeroConta);
    }

    @Test
    void develancarClassNotFoundExceptionQuandoNaoEncontrarClienteParaAtualizar() throws ClassNotFoundException {
        ClienteUpdate clienteUpdate = new ClienteUpdate();
        clienteUpdate.setNome("Ezequiel");
        clienteUpdate.setPlanoExclusive(true);
        String numeroConta = "654321";

        when(serviceMock.atualizarCliente(clienteUpdate, numeroConta)).thenThrow(ClassNotFoundException.class);

        assertThrows(ClassNotFoundException.class, () -> controller.atualizarConta(clienteUpdate, numeroConta));
        verify(serviceMock, times(1)).atualizarCliente(clienteUpdate, numeroConta);
    }

    @Test
    void deveRetornarMensagemDeSucessoQuandoDeletarCliente() throws ClassNotFoundException {
        ResponseEntity<String> response = controller.deletarCliente("123456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("A conta foi deletada com sucesso!", response.getBody());
        verify(serviceMock, times(1)).deleteCliente("123456");
    }

    @Test
    void deveLancarExceptionQuandoNaoEncontrarClienteParaDeletar() throws ClassNotFoundException {
        doThrow(ClassNotFoundException.class).when(serviceMock).deleteCliente("654321");

        assertThrows(ClassNotFoundException.class, () -> controller.deletarCliente("654321"));
        verify(serviceMock, times(1)).deleteCliente("654321");
    }
}
