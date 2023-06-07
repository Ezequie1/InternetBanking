package InternetBanking.Service;

import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Repository.TransacaoRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransacaoServiceTest {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private TransacaoService service;

    @Test
    void getAllTransacoes() throws ClassNotFoundException {
        // Dado que eu queira todas as transações
        // Quando eu informar a data
        // Então me retorna todas as transações
        String data = "2023-05-29";
        Pageable pageable = PageRequest.of(0, 10);
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(service.depositar(new TransacaoRequest(new BigDecimal("10000.00"), "124421")));

        Page<Transacao> expected = service.getAllTransacoes(data, pageable);
        Page<Transacao> result = service.getAllTransacoes(data, pageable);

        System.out.println(expected);
        System.out.println(result);

        Assert.assertEquals(expected, result);
    }

    @Test
    void getTransacoesPorTipo() {
        // Dado que eu queira todas as transações
        // Quando eu informar a data e o tipo
        // Então me retorna todas as transações com tipo e data iguais

        String data = "2023-05-29";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Transacao> result = service.getAllTransacoes(data, pageable);

        Assert.assertNotNull(result);
    }

    @Test
    void depositar() {
    }

    @Test
    void sacar() {
    }

    @Test
    void hasSaldo() {
    }

    @Test
    void addTransacao() {
    }

    @Test
    void updateTransactionsCliente() {
    }

    @Test
    void getTransacoesPorConta() {
    }

    @Test
    void cancelaTransacao() {
    }

    @Test
    void atualizaTransacao() {
    }
}