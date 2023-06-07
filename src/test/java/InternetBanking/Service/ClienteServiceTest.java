package InternetBanking.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Repository.ClienteRepository;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.naming.directory.InvalidAttributesException;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClienteServiceTest {

    ClienteRequest clienteRequest;
    @Mock
    private ClienteRepository clienteRepository = Mockito.mock(ClienteRepository.class);

    @Mock
    private ClienteService clienteService = Mockito.mock(ClienteService.class);

    @Test
    public void testAddCliente() throws InvalidAttributesException {

        clienteRequest.setDataNascimento(LocalDate.now());
        clienteRequest.setNome("Ezequiel");
        clienteRequest.setPlanoExclusive(false);

        Cliente clienteExpected = clienteService.addCliente(clienteRequest);

        when(clienteService.addCliente(clienteRequest)).thenReturn(clienteExpected);

        Cliente clienteAtual = clienteService.addCliente(clienteRequest);

        System.out.println(clienteExpected);
        System.out.println(clienteAtual);

        assertEquals(clienteExpected, clienteAtual);
    }

    @Test
    public void testAddCliente2() throws InvalidAttributesException {
        Cliente cliente = new Cliente("U", true, LocalDate.ofEpochDay(1L));

        when(clienteRepository.save((Cliente) any())).thenReturn(cliente);

        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteRequest.setNome("Nome");
        clienteRequest.setPlanoExclusive(true);
        Cliente actualAddClienteResult = clienteService.addCliente(clienteRequest);
        assertSame(cliente, actualAddClienteResult);
        assertEquals("0", actualAddClienteResult.getSaldo().toString());
        verify(clienteRepository).save((Cliente) any());
    }

    @Test
    public void testAddCliente3() throws InvalidAttributesException {
        when(clienteRepository.save((Cliente) any())).thenReturn(null);

        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteRequest.setNome("Nome");
        clienteRequest.setPlanoExclusive(true);
        assertNull(clienteService.addCliente(clienteRequest));
        verify(clienteRepository).save((Cliente) any());
    }

}

