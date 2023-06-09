package InternetBanking.Service;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Model.RequestsModel.TransacaoUpdate;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.TransacaoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class TransacaoServiceTest {

    @InjectMocks
    TransacaoService service;

    @Mock
    TransacaoRepository repository;

    @Mock
    TransacaoService serviceMock;

    @Mock
    ClienteService serviceClienteMock;

    @Test
    void deveRetornarTodasTransacoesComDataIgualAInformada() {
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(LocalDate.now().toString(), formatter);
        List<Transacao> transacoes = Collections.singletonList(new Transacao(new BigDecimal("10000.00"), "124421", TipoMovimentacao.DEPOSITO));

        when(repository.getByDiaTransacao(date, pageable)).thenReturn(new PageImpl<>(transacoes));

        service.getAllTransacoes(date.toString(), pageable);

        Mockito.verify(repository).getByDiaTransacao(date, pageable);
        Assert.assertEquals(new PageImpl<>(transacoes), service.getAllTransacoes(date.toString(), pageable));
    }

    @Test
    void deveRetornarPaginaComListaDeTranacoesVaziaQuandoNaoHouverTransacoes() {
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2000-02-15", formatter);
        List transacoes = new ArrayList();

        when(service.getAllTransacoes(date.toString(), pageable)).thenReturn(new PageImpl<>(transacoes));

        service.getAllTransacoes(date.toString(), pageable);

        Mockito.verify(repository).getByDiaTransacao(date, pageable);
        Assert.assertEquals(new PageImpl<>(transacoes), service.getAllTransacoes(date.toString(), pageable));
    }

    @Test
    void deveRetornarTodasTransacoesComTipoEDataIguaisAPassada() {
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2022-01-05", formatter);
        List<Transacao> transacoes = new ArrayList<>();

        when(service.getTransacoesPorTipo(TipoMovimentacao.DEPOSITO, "2022-01-05", pageable)).thenReturn(new PageImpl<>(transacoes));
        service.getTransacoesPorTipo(TipoMovimentacao.DEPOSITO, "2022-01-05", pageable);

        Mockito.verify(repository).getByDiaTransacaoAndTipoMovimentacao(TipoMovimentacao.DEPOSITO, date, pageable);
        Assert.assertEquals(new PageImpl<>(transacoes), service.getTransacoesPorTipo(TipoMovimentacao.DEPOSITO, "2022-01-05", pageable));
    }

    @Test
    void deveCriarTransacaoDeDepositoAssimQueSerPassadoTransacaoRequest() throws ClassNotFoundException {

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.DEPOSITO);

        when(serviceMock.depositar(transacaoRequest)).thenReturn(transacao);

        serviceMock.depositar(transacaoRequest);

        verify(serviceMock).depositar(transacaoRequest);
        Assert.assertEquals(transacao, serviceMock.depositar(transacaoRequest));
    }

    @Test
    void deveRetornarClassNotFoundExceptionQuandoContaNaoExistir() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        when(serviceMock.depositar(transacaoRequest)).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> serviceMock.depositar(transacaoRequest);

        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void deveCriarTransacaoDeSaqueAssimQueSerPassadoTransacaoRequest() throws ClassNotFoundException {

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(serviceMock.sacar(transacaoRequest)).thenReturn(transacao);

        serviceMock.sacar(transacaoRequest);

        verify(serviceMock).sacar(transacaoRequest);
        Assert.assertEquals(transacao, serviceMock.sacar(transacaoRequest));
    }

    @Test
    void deveRetornarClassNotFoundExceptionQuandoContaNaoExistirParaSaque() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        when(serviceMock.sacar(transacaoRequest)).thenThrow(IllegalArgumentException.class);

        Executable methodCall = () -> serviceMock.sacar(transacaoRequest);

        assertThrows(IllegalArgumentException.class, methodCall);
    }

    @Test
    void deveRetornarTrueQuandoClientePossuirSaldo() {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.hasSaldo(transacaoRequest, cliente)).thenReturn(true);

        serviceMock.hasSaldo(transacaoRequest, cliente);

        Assert.assertTrue(serviceMock.hasSaldo(transacaoRequest, cliente));
    }

    @Test
    void deveRetornarFalseQuandoClientePossuirSaldo() {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.hasSaldo(transacaoRequest, cliente)).thenReturn(false);

        serviceMock.hasSaldo(transacaoRequest, cliente);

        Assert.assertFalse(serviceMock.hasSaldo(transacaoRequest, cliente));
    }

    @Test
    void deveSalvarTransacaoAoClienteQuandoForPassadoCliente() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(repository.save(transacao)).thenReturn(transacao);
        when(serviceClienteMock.getClienteByConta(transacao.getNumeroConta())).thenReturn(new Cliente("Ezequiel", true,  LocalDate.now()));

        service.addTransacao(transacao);

        verify(serviceClienteMock).getClienteByConta(transacao.getNumeroConta());
        Assert.assertEquals("123456", transacao.getNumeroConta());
    }

    @Test
    void deveRetornarClassNotFoundExceptionQuandoPassarContaInvalidaParaAdicionarTransacao() throws ClassNotFoundException {
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "114411");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(serviceClienteMock.getClienteByConta(transacao.getNumeroConta())).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.addTransacao(transacao);

        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void deveAtualizarNumeroContaDasTransacoesQuandoClienteTrocarDeNumeroConta() throws ClassNotFoundException {
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());
        service.updateTransactionsCliente(cliente);

        cliente.getTransacoes().forEach(transacao -> {
            Assert.assertEquals(cliente.getNumeroConta(), transacao.getNumeroConta());
        });
    }

    @Test
    void deveRetornarTransacoesComNumeroContaQuandoForPassadoNumeroContaValido() throws ClassNotFoundException {
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2022-01-05", formatter);
        List<Transacao> transacoes = new ArrayList<>();
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceClienteMock.getClienteByConta("123456")).thenReturn(cliente);
        when(repository.getByCliente(cliente, pageable)).thenReturn(new PageImpl<>(transacoes));

        Assert.assertEquals(new PageImpl<>(transacoes), service.getTransacoesPorConta("123456", pageable));
        verify(serviceClienteMock).getClienteByConta("123456");
    }

    @Test
    void deveRetornarClassNotFoundExceptionQuandoNaoExistirConta() throws ClassNotFoundException {
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.getTransacoesPorConta("123456", pageable);
        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void deveRetornarExceptionQuandoForPassadoIdTransacaoENumeroContaInvalidos() throws ClassNotFoundException {
        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.cancelaTransacao("123456", 1L);
        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void deveRetornarExceptionQuandoClienteNaoExiste() throws ClassNotFoundException {
        TransacaoUpdate transacao = new TransacaoUpdate();
        transacao.setValorTransacao(new BigDecimal("12000.00"));

        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.atualizaTransacao(transacao,"123456", 1L);
        assertThrows(ClassNotFoundException.class, methodCall);
    }
}