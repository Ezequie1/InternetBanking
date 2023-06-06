package InternetBanking.Controller;

import InternetBanking.Model.RequestsModel.TranferenciaRequest;
import InternetBanking.Model.TipoMovimentacao;
import InternetBanking.Model.Transacao;
import InternetBanking.Model.RequestsModel.TransacaoRequest;
import InternetBanking.Service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping("/{data}")
    public ResponseEntity<List<Transacao>> transacaoPorData(@PathVariable String data){
        return ResponseEntity.status(HttpStatus.OK).body( service.getAllTransacoes(data) );
    }

    @GetMapping("/{tipoTransacao}/{data}")
    public ResponseEntity<List<Transacao>> SaquePorData(@PathVariable TipoMovimentacao tipoTransacao, @PathVariable String data){
        return ResponseEntity.status(HttpStatus.OK).body(service.getTransacoesPorTipo(tipoTransacao, data));
    }

    @PostMapping("/Deposito")
    public ResponseEntity<Transacao> deposito(@RequestBody TransacaoRequest transacao) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.depositar(transacao));
    }

    @PostMapping("/Saque")
    public ResponseEntity<Transacao> saque(@RequestBody TransacaoRequest transacao) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sacar(transacao));
    }

    @PostMapping("/Transferencia")
    public ResponseEntity<Transacao> transferencia(@RequestBody TranferenciaRequest transacao) throws ClassNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.tranferencia(transacao));
    }

}
