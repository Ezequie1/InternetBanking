package InternetBanking.Repository;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Page<Transacao> getByDiaTransacao(LocalDate data, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.tipoMovimentacao = :tipo AND t.diaTransacao = :data")
    Page<Transacao> getByDiaTransacaoAndTipoMovimentacao(@Param("tipo") TipoMovimentacao tipoMovimentacao, @Param("data") LocalDate data, Pageable pageable);

    Page<Transacao> getByCliente(Cliente cliente, Pageable pageable);
    @Query("SELECT t FROM Transacao t WHERE t.numeroConta = :numeroConta AND t.idTransacao = :idTransacao")
    Transacao getByIdTransacaoAndCliente(@Param("numeroConta") String numeroConta,@Param("idTransacao") Long idTransacao);
}
