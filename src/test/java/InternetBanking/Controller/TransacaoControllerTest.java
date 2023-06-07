package InternetBanking.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Model.RequestsModel.TransacaoUpdate;
import InternetBanking.Model.TipoMovimentacao;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;

public class TransacaoControllerTest {
    /**
     * Method under test: {@link TransacaoController#transacaoPorData(String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testTransacaoPorData() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.getAllTransacoes(String, org.springframework.data.domain.Pageable)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.transacaoPorData(TransacaoController.java:28)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).transacaoPorData("Data", 1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#transacaoPorData(String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testTransacaoPorData2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Page index must not be less than zero
        //       at InternetBanking.Controller.TransacaoController.transacaoPorData(TransacaoController.java:27)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).transacaoPorData("Data", -1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#saquePorData(TipoMovimentacao, String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testSaquePorData() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.getTransacoesPorTipo(InternetBanking.Model.TipoMovimentacao, String, org.springframework.data.domain.Pageable)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.saquePorData(TransacaoController.java:37)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).saquePorData(TipoMovimentacao.SAQUE, "Data", 1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#saquePorData(TipoMovimentacao, String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testSaquePorData2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.getTransacoesPorTipo(InternetBanking.Model.TipoMovimentacao, String, org.springframework.data.domain.Pageable)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.saquePorData(TransacaoController.java:37)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).saquePorData(TipoMovimentacao.DEPOSITO, "Data", 1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#saquePorData(TipoMovimentacao, String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testSaquePorData3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Page index must not be less than zero
        //       at InternetBanking.Controller.TransacaoController.saquePorData(TransacaoController.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).saquePorData(TipoMovimentacao.SAQUE, "Data", -1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#getTransacoesPorConta(String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetTransacoesPorConta() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.getTransacoesPorConta(String, org.springframework.data.domain.Pageable)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.getTransacoesPorConta(TransacaoController.java:45)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).getTransacoesPorConta("Conta", 1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#getTransacoesPorConta(String, int, int)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetTransacoesPorConta2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Page index must not be less than zero
        //       at InternetBanking.Controller.TransacaoController.getTransacoesPorConta(TransacaoController.java:44)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).getTransacoesPorConta("Conta", -1, 3);
    }

    /**
     * Method under test: {@link TransacaoController#deposito(TransacaoRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testDeposito() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.depositar(InternetBanking.Model.RequestsModel.TransacaoRequest)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.deposito(TransacaoController.java:50)
        //   See https://diff.blue/R013 to resolve this issue.

        TransacaoController transacaoController = new TransacaoController();
        transacaoController.deposito(new TransacaoRequest());
    }

    /**
     * Method under test: {@link TransacaoController#deposito(TransacaoRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testDeposito2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.depositar(InternetBanking.Model.RequestsModel.TransacaoRequest)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.deposito(TransacaoController.java:50)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).deposito(mock(TransacaoRequest.class));
    }

    /**
     * Method under test: {@link TransacaoController#saque(TransacaoRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testSaque() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.sacar(InternetBanking.Model.RequestsModel.TransacaoRequest)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.saque(TransacaoController.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        TransacaoController transacaoController = new TransacaoController();
        transacaoController.saque(new TransacaoRequest());
    }

    /**
     * Method under test: {@link TransacaoController#saque(TransacaoRequest)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testSaque2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.sacar(InternetBanking.Model.RequestsModel.TransacaoRequest)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.saque(TransacaoController.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).saque(mock(TransacaoRequest.class));
    }

    /**
     * Method under test: {@link TransacaoController#cancelaTransacao(String, Long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testCancelaTransacao() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.cancelaTransacao(String, java.lang.Long)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.cancelaTransacao(TransacaoController.java:60)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransacaoController()).cancelaTransacao("Numero Conta", 1L);
    }

    /**
     * Method under test: {@link TransacaoController#atualizaTransacao(TransacaoUpdate, String, Long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAtualizaTransacao() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.atualizaTransacao(InternetBanking.Model.RequestsModel.TransacaoUpdate, String, java.lang.Long)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.atualizaTransacao(TransacaoController.java:66)
        //   See https://diff.blue/R013 to resolve this issue.

        TransacaoController transacaoController = new TransacaoController();

        TransacaoUpdate transacaoUpdate = new TransacaoUpdate();
        transacaoUpdate.setValorTransacao(BigDecimal.valueOf(42L));
        transacaoController.atualizaTransacao(transacaoUpdate, "Numero Conta", 1L);
    }

    /**
     * Method under test: {@link TransacaoController#atualizaTransacao(TransacaoUpdate, String, Long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testAtualizaTransacao2() throws ClassNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "InternetBanking.Service.TransacaoService.atualizaTransacao(InternetBanking.Model.RequestsModel.TransacaoUpdate, String, java.lang.Long)" because "this.service" is null
        //       at InternetBanking.Controller.TransacaoController.atualizaTransacao(TransacaoController.java:66)
        //   See https://diff.blue/R013 to resolve this issue.

        TransacaoController transacaoController = new TransacaoController();
        TransacaoUpdate transacaoUpdate = mock(TransacaoUpdate.class);
        doNothing().when(transacaoUpdate).setValorTransacao((BigDecimal) any());
        transacaoUpdate.setValorTransacao(BigDecimal.valueOf(42L));
        transacaoController.atualizaTransacao(transacaoUpdate, "Numero Conta", 1L);
    }
}

