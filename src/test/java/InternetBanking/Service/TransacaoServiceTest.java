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
@SpringBootTest
class TransacaoServiceTest {

    @InjectMocks
    TransacaoService service;

    @InjectMocks
    ClienteService serviceCliente;

    @MockBean
    TransacaoRepository repository;

    @Mock
    TransacaoService serviceMock;

    @Mock
    ClienteService serviceClienteMock;

    @Test
    void getAllTransacoesSuccess() throws ClassNotFoundException {
        // Dado que eu queira todas as transações
        // Quando eu informar a data
        // Então me retorna todas as transações com a data passada
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
    void getAllTransacoesEmpty() {
        // Dado que eu queira todas as transações
        // Quando eu informar a data que nao corresponde a nenhuma transação
        // Então me retorna um notFound

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
    void getTransacoesPorTipoSuccess() {
        // Dado que eu queira todas as transações com tipoMovimentacao e data
        // Quando eu informar a data e o tipo
        // Então me retorna todas as transações com tipo e data iguais
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
    void depositarSuccess() throws ClassNotFoundException {
        // Dado que eu queira fazer uma transação de deposito
        // Quando eu informar a Transacao request
        // Então me retorna a transação criada

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.DEPOSITO);

        when(serviceMock.depositar(transacaoRequest)).thenReturn(transacao);

        serviceMock.depositar(transacaoRequest);

        verify(serviceMock).depositar(transacaoRequest);
        Assert.assertEquals(transacao, serviceMock.depositar(transacaoRequest));
    }

    @Test
    void depositarAccountNotFound() throws ClassNotFoundException {
        // Dado que eu queira fazer uma transação de depósito
        // Quando eu informar a TransacaoRequest com conta inválida
        // Então me retorna ClassNotFoundException
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        when(serviceMock.depositar(transacaoRequest)).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> serviceMock.depositar(transacaoRequest);

        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void sacar() throws ClassNotFoundException {
        // Dado que eu queira fazer uma transação de SAQUE
        // Quando eu informar a Transacao request
        // Então me retorna a transação criada

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(serviceMock.sacar(transacaoRequest)).thenReturn(transacao);

        serviceMock.sacar(transacaoRequest);

        verify(serviceMock).sacar(transacaoRequest);
        Assert.assertEquals(transacao, serviceMock.sacar(transacaoRequest));
    }

    @Test
    void sacarFalhaSemSaldo() throws ClassNotFoundException {
        // Dado que eu queira fazer uma transação de SAQUE
        // Quando eu informar a Transacao request com valor maior que saldo disponível
        // Então me retorna IllegalArgumentException

        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        when(serviceMock.sacar(transacaoRequest)).thenThrow(IllegalArgumentException.class);

        Executable methodCall = () -> serviceMock.sacar(transacaoRequest);

        assertThrows(IllegalArgumentException.class, methodCall);
    }

    @Test
    void hasSaldo() {
        // Dado que eu queira verificar o valor do SAQUE e o saldo da conta
        // Quando eu informar a Transacao request
        // Então me retorna true
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.hasSaldo(transacaoRequest, cliente)).thenReturn(true);

        serviceMock.hasSaldo(transacaoRequest, cliente);

        Assert.assertTrue(serviceMock.hasSaldo(transacaoRequest, cliente));
    }

    @Test
    void notHasSaldo() {
        // Dado que eu queira verificar o valor do SAQUE e o saldo da conta
        // Quando eu informar a Transacao request com valor maior que o saldo
        // Então me retorna false
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "12126");
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());

        when(serviceMock.hasSaldo(transacaoRequest, cliente)).thenReturn(false);

        serviceMock.hasSaldo(transacaoRequest, cliente);

        Assert.assertFalse(serviceMock.hasSaldo(transacaoRequest, cliente));
    }

    @Test
    void addTransacao() throws ClassNotFoundException {
        // Dado que eu queira adicionar uma transacao a um cliente
        // Quando eu informar a Transacao
        // Então me salva o cliente, aqui é conferido o numero da conta se são iguais
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "123456");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(repository.save(transacao)).thenReturn(transacao);
        when(serviceClienteMock.getClienteByConta(transacao.getNumeroConta())).thenReturn(new Cliente("Ezequiel", true,  LocalDate.now()));

        service.addTransacao(transacao);

        verify(serviceClienteMock).getClienteByConta(transacao.getNumeroConta());
        Assert.assertEquals("123456", transacao.getNumeroConta());
    }

    @Test
    void addTransacaoFalha() throws ClassNotFoundException {
        // Dado que eu queira adicionar uma transacao a um cliente
        // Quando eu informar a Transacao com conta inexistente
        // Então lança uma ClassNotFoundException
        TransacaoRequest transacaoRequest = new TransacaoRequest(new BigDecimal("1000.00"), "114411");
        Transacao transacao = new Transacao(transacaoRequest.getValorTransacao(), transacaoRequest.getConta(), TipoMovimentacao.SAQUE);

        when(serviceClienteMock.getClienteByConta(transacao.getNumeroConta())).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.addTransacao(transacao);

        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void updateTransactionsCliente() throws ClassNotFoundException {
        // Dado que eu queira atualizar o numero da conta nas transacoes de um cliente
        // Quando eu informar o cliente
        // Então atualiza o numero conta de todas as transações daquele cliente
        Cliente cliente = new Cliente("Ezequiel", true, LocalDate.now());
        service.updateTransactionsCliente(cliente);

        cliente.getTransacoes().forEach(transacao -> {
            Assert.assertEquals(cliente.getNumeroConta(), transacao.getNumeroConta());
        });
    }

    @Test
    void getTransacoesPorConta() throws ClassNotFoundException {
        // Dado que eu queira pegar transacoes através do numero da conta
        // Quando eu informar o numero da conta
        // Então me retorna as transacoes
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
    void getTransacoesPorContaFalha() throws ClassNotFoundException {
        // Dado que eu queira pegar transacoes através do numero da conta
        // Quando eu informar o numero da conta errada
        // Então me retorna ClassNotFoundExcetption
        Pageable pageable = PageRequest.of(0, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.getTransacoesPorConta("123456", pageable);
        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void cancelaTransacaoFalha() throws ClassNotFoundException {
        // Dado que eu queira excluir uma transação
        // Quando eu informar o numero da conta e o idTransacao errado
        //verifica lanca uma exception

        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.cancelaTransacao("123456", 1L);
        assertThrows(ClassNotFoundException.class, methodCall);
    }

    @Test
    void atualizaTransacao() throws ClassNotFoundException {
        // Dado que eu queira atualizar uma transação
        // Quando eu informar o numero da conta e o idTransacao errado
        //verifica lanca uma exception
        TransacaoUpdate transacao = new TransacaoUpdate();
        transacao.setValorTransacao(new BigDecimal("12000.00"));

        when(serviceClienteMock.getClienteByConta("123456")).thenThrow(ClassNotFoundException.class);

        Executable methodCall = () -> service.atualizaTransacao(transacao,"123456", 1L);
        assertThrows(ClassNotFoundException.class, methodCall);
    }
}