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
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.ClienteRepository;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.naming.directory.InvalidAttributesException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ClienteServiceTest {

    @MockBean
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteService clienteServiceMock;

    @Mock
    private TransacaoService transacaoServiceMock;

    @InjectMocks
    private TransacaoService transacaoService;


    @Test
    void getAllClientesSuccess() {
        // Dado que eu queira todos os Clientes
        // Quando eu der o get
        // Então me retorna todos os clientes
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(LocalDate.now().toString(), formatter);
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Ezequiel", true, LocalDate.now()));

        when(clienteRepository.findAll(pageable)).thenReturn(new PageImpl<>(clientes));

        clienteService.getAllClientes(pageable);

        assertEquals(new PageImpl<>(clientes), clienteService.getAllClientes(pageable));
    }

    @Test
    void addCliente() {
    }

    @Test
    void addSaldo() {
    }

    @Test
    void getClienteByConta() {
    }

    @Test
    void debit() {
    }

    @Test
    void deleteCliente() {
    }

    @Test
    void atualizarCliente() {
    }

    @Test
    void saveChanges() {
    }
}

