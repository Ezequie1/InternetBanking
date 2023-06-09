package InternetBanking.Repository;

import InternetBanking.Model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ClienteRepositoryTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void testGetByNumeroConta() {
        Cliente cliente = clienteRepository.getByNumeroConta("123456");

        Assertions.assertEquals(cliente, clienteRepository.getByNumeroConta("123456"));
    }

    @Test
    void testGetByNumeroContaInexistente() {
        Cliente result = clienteRepository.getByNumeroConta("999999");

        Assertions.assertNull(result);
    }

    @Test
    void testGetByNumeroContaComCamposNulos() {
        Cliente result = clienteRepository.getByNumeroConta(null);

        Assertions.assertNull(result);
    }

}