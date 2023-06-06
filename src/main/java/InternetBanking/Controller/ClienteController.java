package InternetBanking.Controller;

import InternetBanking.Model.Cliente;
import InternetBanking.Model.RequestsModel.ClienteRequest;
import InternetBanking.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes(){
        return ResponseEntity.status(HttpStatus.OK).body( service.getAllClientes() );
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody ClienteRequest cliente){
        return ResponseEntity.status(HttpStatus.CREATED).body( service.addCliente(cliente) );
    }

}
