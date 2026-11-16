package br.com.autoGestor.RotaCerta.Repository;

import br.com.autoGestor.RotaCerta.Model.usuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface usuarioRepository extends JpaRepository<usuarioModel, Long> {
    Optional<usuarioModel> findByEmail(String email);
}
