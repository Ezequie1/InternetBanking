package InternetBanking.Controller;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Model.RequestsModel.ClienteUpdate;
import InternetBanking.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;

@RestController
@RequestMapping("/Clientes")
public class ClienteController {      //Acesse http://localhost:8080/swagger-ui/index.html para utilizar o swagger

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Page<Cliente>> getClientes(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body( service.getAllClientes(pageable) );
    }

    @GetMapping("/{conta}")
    public ResponseEntity<Cliente> getClientes(String conta) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body( service.getClienteByConta(conta) );
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody ClienteRequest cliente) throws InvalidAttributesException {
        return ResponseEntity.status(HttpStatus.CREATED).body( service.addCliente(cliente) );
    }

    @PutMapping("/Update/{numeroConta}")
    public ResponseEntity<Cliente> atualizarConta (@RequestBody ClienteUpdate cliente, @PathVariable String numeroConta) throws ClassNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body( service.atualizarCliente(cliente, numeroConta));
    }

    @DeleteMapping("/Delete/{numeroConta}")
    public ResponseEntity<String> deletarCliente (@RequestParam String numeroConta) throws ClassNotFoundException {
        service.deleteCliente(numeroConta);
        return ResponseEntity.status(HttpStatus.OK).body("A conta foi deletada com sucesso!");
    }

}
