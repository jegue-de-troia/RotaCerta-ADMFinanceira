package br.com.autoGestor.RotaCerta.Controller;

import br.com.autoGestor.RotaCerta.Model.usuarioModel;
import br.com.autoGestor.RotaCerta.Services.usuarioServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class usuarioController {

    private final usuarioServices usuarioservices;

    public usuarioController(usuarioServices usuarioservices){
        this.usuarioservices = usuarioservices;
    }

    @PostMapping
    public usuarioModel cadastrarUsuario(@RequestBody usuarioModel usuariomodel){
        return usuarioservices.cadastrarUsuario(usuariomodel.getEmail());
    }
}
