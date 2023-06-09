package InternetBanking.Controller;

import InternetBanking.Model.RequestsModel.TransacaoUpdate;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping()
    public ResponseEntity<Page<Transacao>> transacaoPorData(@RequestParam String data,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body( service.getAllTransacoes(data, pageable) );
    }

    @GetMapping("/TipoMovimentacao")
    public ResponseEntity<Page<Transacao>> saquePorData(@RequestParam TipoMovimentacao tipoTransacao,
                                                        @RequestParam String data,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransacoesPorTipo(tipoTransacao, data, pageable));
    }

    @GetMapping("/Conta")
    public ResponseEntity<Page<Transacao>> getTransacoesPorConta(@RequestParam String conta,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) throws ClassNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransacoesPorConta(conta, pageable) );
    }

    @PostMapping("/Deposito")
    public ResponseEntity<Transacao> deposito(@RequestBody TransacaoRequest transacao) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.depositar(transacao));
    }

    @PostMapping("/Saque")
    public ResponseEntity<Transacao> saque(@RequestBody TransacaoRequest transacao) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sacar(transacao));
    }

    @DeleteMapping("/Deletar")
    public ResponseEntity<String> cancelaTransacao(@RequestParam String numeroConta, @RequestParam Long idTransacao) throws ClassNotFoundException {
        service.cancelaTransacao(numeroConta, idTransacao);
        return ResponseEntity.status(HttpStatus.OK).body("Transação deletada com sucesso, seu saldo foi ajustado!");
    }

    @PutMapping("/Update")
    public ResponseEntity<Transacao> atualizaTransacao(@RequestBody TransacaoUpdate transacao, @RequestParam String numeroConta, @RequestParam Long idTransacao) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.atualizaTransacao(transacao, numeroConta, idTransacao));
    }

}
