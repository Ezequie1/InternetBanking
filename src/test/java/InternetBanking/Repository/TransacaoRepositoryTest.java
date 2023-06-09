package InternetBanking.Repository;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class TransacaoRepositoryTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ClienteRepository repository;

    @Test
    void testGetByDiaTransacao() {
        Transacao transacao = new Transacao();
        Pageable pageable = mock(Pageable.class);
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(transacao);

        when(transacaoRepository.getByDiaTransacao(transacao.getDiaTransacao(), pageable)).thenReturn(new PageImpl<>(transacoes));

        Assertions.assertEquals(new PageImpl<>(transacoes), transacaoRepository.getByDiaTransacao(transacao.getDiaTransacao(), pageable));
    }

    @Test
    void testGetByDiaTransacaoAndTipoMovimentacao() {
        Transacao transacao = new Transacao(new BigDecimal("1000.00"), "123456", TipoMovimentacao.DEPOSITO);
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(transacao);
        Pageable pageable = mock(Pageable.class);

        when(transacaoRepository.getByDiaTransacaoAndTipoMovimentacao(TipoMovimentacao.DEPOSITO, LocalDate.now(), pageable)).thenReturn(new PageImpl<>(transacoes));

        Assertions.assertEquals(new PageImpl<>(transacoes), transacaoRepository.getByDiaTransacaoAndTipoMovimentacao(TipoMovimentacao.DEPOSITO, LocalDate.now(), pageable));
    }

    @Test
    void testGetByCliente() {
        Cliente cliente = new Cliente("Juliano", true, LocalDate.now());
        repository.save(cliente);

        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacaoRepository.save(transacao);

        Pageable pageable = mock(Pageable.class);
        List<Transacao> transacoes = new ArrayList<>();
        transacoes.add(transacao);

        when(transacaoRepository.getByCliente(cliente, pageable)).thenReturn(new PageImpl<>(transacoes));
        Assertions.assertEquals(new PageImpl<>(transacoes),transacaoRepository.getByCliente(cliente, pageable));
    }

    @Test
    void testGetByIdTransacaoAndCliente() {
        Cliente cliente = new Cliente("Juliano", true, LocalDate.now());
        repository.save(cliente);

        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacaoRepository.save(transacao);

        when(transacaoRepository.getByIdTransacaoAndCliente(cliente.getNumeroConta(), transacao.getIdTransacao())).thenReturn(transacao);
        Assertions.assertEquals(transacao, transacaoRepository.getByIdTransacaoAndCliente(cliente.getNumeroConta(),transacao.getIdTransacao()));
    }
}
