package InternetBanking.Repository;

import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> getByDiaTransacao(LocalDate data);

    @Query("SELECT t FROM Transacao t WHERE t.tipoMovimentacao = :tipoMovimentacao AND t.diaTransacao = :diaTransacao")
    List<Transacao> getTrasacoesByTipoTransacaoAndDate(@Param("tipoMovimentacao")TipoMovimentacao saque, @Param("diaTransacao") LocalDate data);

}
