package br.com.autoGestor.RotaCerta.Services;

import br.com.autoGestor.RotaCerta.Repository.usuarioRepository;
import br.com.autoGestor.RotaCerta.Model.usuarioModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class usuarioServices {

    private final usuarioRepository usuariorepository;

    public usuarioServices(usuarioRepository usuariorepository) {
        this.usuariorepository = usuariorepository;
    }

    public usuarioModel cadastrarUsuario(String emailDigitado){
        String email = emailDigitado == null ?  "": emailDigitado.trim().toLowerCase();

        if (email.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email obrigatório");
        }

        return usuariorepository.findByEmail(email).orElseGet(() ->{
            usuarioModel usuario = new usuarioModel();
            usuario.setEmail(email);
            return usuariorepository.save(usuario);
        });
    }

    public List<usuarioModel> listarUsuario(){
        return usuariorepository.findAll();
    }

    public usuarioModel buscarUsuarioPorID(Long id){
        return usuariorepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

    }

    public usuarioModel atualizarUsuario(Long id,usuarioModel usuariomodel){
        usuarioModel usuario = buscarUsuarioPorID(id);

        usuario.setEmail(usuariomodel.getEmail());

        return usuariorepository.save(usuario);
    }

    public void deletarusuario(Long id){
        buscarUsuarioPorID(id);
        usuariorepository.deleteById(id);
    }
}
