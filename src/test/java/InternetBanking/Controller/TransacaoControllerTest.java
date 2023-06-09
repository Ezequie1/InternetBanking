package InternetBanking.Controller;

import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Model.RequestsModel.TransacaoUpdate;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class TransacaoControllerTest {

    @Mock
    private TransacaoService serviceMock;

    @InjectMocks
    private TransacaoController controller;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void deveRetornarUmaListaDeTransacoesQuandoForPassadoPageEData() {
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(new Transacao());
        transacoes.add(new Transacao());
        Page<Transacao> transacaoPage = new PageImpl<>(transacoes);

        when(serviceMock.getAllTransacoes(eq("2023-05-30"), any(Pageable.class))).thenReturn(transacaoPage);

        ResponseEntity<Page<Transacao>> response = controller.transacaoPorData("2023-05-30", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoPage, response.getBody());
    }

    @Test
    public void deveRetornarPageDeTransacoesQuandoForPassadoDataTipoMovimentacaoSaqueEPage() {
        TipoMovimentacao tipoTransacao = TipoMovimentacao.SAQUE;

        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(new Transacao());
        transacoes.add(new Transacao());
        Page<Transacao> transacaoPage = new PageImpl<>(transacoes);

        when(serviceMock.getTransacoesPorTipo(eq(tipoTransacao), eq("2023-05-30"), any(Pageable.class))).thenReturn(transacaoPage);

        ResponseEntity<Page<Transacao>> response = controller.saquePorData(tipoTransacao, "2023-05-30", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoPage, response.getBody());
    }

    @Test
    public void deveRetornarPageDeTransacoesQuandoForInformadoContaEPage() throws ClassNotFoundException {
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(new Transacao());
        transacoes.add(new Transacao());
        Page<Transacao> transacaoPage = new PageImpl<>(transacoes);

        when(serviceMock.getTransacoesPorConta(eq("123456"), any(Pageable.class))).thenReturn(transacaoPage);

        ResponseEntity<Page<Transacao>> response = controller.getTransacoesPorConta("123456", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacaoPage, response.getBody());
    }

    @Test
    public void deveCriarERetornarDepositoQuandoForPassadoTransacaoRequest() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        Transacao transacao = new Transacao();

        when(serviceMock.depositar(eq(transacaoRequest))).thenReturn(transacao);

        ResponseEntity<Transacao> response = controller.deposito(transacaoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transacao, response.getBody());
    }

    @Test
    public void deveRetornarTranscaoCriadaQuandoForPassadoTranscaoRequest() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        Transacao transacao = new Transacao();

        when(serviceMock.sacar(eq(transacaoRequest))).thenReturn(transacao);

        ResponseEntity<Transacao> response = controller.saque(transacaoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transacao, response.getBody());
    }

    @Test
    public void deveCancelarTransacaoERetornarMensagemDeSucesso() throws ClassNotFoundException {
        ResponseEntity<String> response = controller.cancelaTransacao("123456", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transação deletada com sucesso, seu saldo foi ajustado!", response.getBody());
    }

    @Test
    public void deveRetornarTransacaoQueFoiAtualizada() throws ClassNotFoundException {
        TransacaoUpdate transacaoUpdate = new TransacaoUpdate();
        String numeroConta = "123456";
        Long idTransacao = 1L;
        Transacao transacao = new Transacao();

        when(serviceMock.atualizaTransacao(eq(transacaoUpdate), eq(numeroConta), eq(idTransacao))).thenReturn(transacao);

        ResponseEntity<Transacao> response = controller.atualizaTransacao(transacaoUpdate, numeroConta, idTransacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transacao, response.getBody());
    }
}