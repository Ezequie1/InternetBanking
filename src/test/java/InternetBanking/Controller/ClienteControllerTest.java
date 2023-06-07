package InternetBanking.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.RequestsModel.ClienteUpdate;

import java.time.LocalDate;
import javax.naming.directory.InvalidAttributesException;

import org.junit.Ignore;
import org.junit.Test;

public class ClienteControllerTest {
    /**
     * Method under test: {@link ClienteController#getClientes(int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetClientes() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.getAllClientes(org.springframework.data.domain.Pageable)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.getClientes(ClienteController.java:28)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ClienteController()).getClientes(1, 3);
    }

    /**
     * Method under test: {@link ClienteController#getClientes(String)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetClientes2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.getClienteByConta(String)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.getClientes(ClienteController.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ClienteController()).getClientes("Conta");
    }

    /**
     * Method under test: {@link ClienteController#addCliente(ClienteRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAddCliente() throws InvalidAttributesException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.addCliente(InternetBanking.Model.RequestsModel.ClienteRequest)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.addCliente(ClienteController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        ClienteController clienteController = new ClienteController();

        ClienteRequest clienteRequest = new ClienteRequest();
        clienteRequest.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteRequest.setNome("Nome");
        clienteRequest.setPlanoExclusive(true);
        clienteController.addCliente(clienteRequest);
    }

    /**
     * Method under test: {@link ClienteController#addCliente(ClienteRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAddCliente2() throws InvalidAttributesException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.addCliente(InternetBanking.Model.RequestsModel.ClienteRequest)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.addCliente(ClienteController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        ClienteController clienteController = new ClienteController();
        ClienteRequest clienteRequest = mock(ClienteRequest.class);
        doNothing().when(clienteRequest).setDataNascimento((LocalDate) any());
        doNothing().when(clienteRequest).setNome((String) any());
        doNothing().when(clienteRequest).setPlanoExclusive(anyBoolean());
        clienteRequest.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteRequest.setNome("Nome");
        clienteRequest.setPlanoExclusive(true);
        clienteController.addCliente(clienteRequest);
    }

    /**
     * Method under test: {@link ClienteController#atualizarConta(ClienteUpdate, String)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAtualizarConta() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.atualizarCliente(InternetBanking.Model.RequestsModel.ClienteUpdate, String)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.atualizarConta(ClienteController.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        ClienteController clienteController = new ClienteController();

        ClienteUpdate clienteUpdate = new ClienteUpdate();
        clienteUpdate.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteUpdate.setNome("Nome");
        clienteUpdate.setNumeroConta("Numero Conta");
        clienteUpdate.setPlanoExclusive(true);
        clienteController.atualizarConta(clienteUpdate, "Numero Conta");
    }

    /**
     * Method under test: {@link ClienteController#atualizarConta(ClienteUpdate, String)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAtualizarConta2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.atualizarCliente(InternetBanking.Model.RequestsModel.ClienteUpdate, String)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.atualizarConta(ClienteController.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        ClienteController clienteController = new ClienteController();
        ClienteUpdate clienteUpdate = mock(ClienteUpdate.class);
        doNothing().when(clienteUpdate).setDataNascimento((LocalDate) any());
        doNothing().when(clienteUpdate).setNome((String) any());
        doNothing().when(clienteUpdate).setNumeroConta((String) any());
        doNothing().when(clienteUpdate).setPlanoExclusive(anyBoolean());
        clienteUpdate.setDataNascimento(LocalDate.ofEpochDay(1L));
        clienteUpdate.setNome("Nome");
        clienteUpdate.setNumeroConta("Numero Conta");
        clienteUpdate.setPlanoExclusive(true);
        clienteController.atualizarConta(clienteUpdate, "Numero Conta");
    }

    /**
     * Method under test: {@link ClienteController#deletarCliente(String)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testDeletarCliente() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.ClienteService.deleteCliente(String)" because "this.service" is null
        //       at InternetBanking.Controller.ClienteController.deletarCliente(ClienteController.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ClienteController()).deletarCliente("Numero Conta");
    }
}

