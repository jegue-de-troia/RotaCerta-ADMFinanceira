package br.com.autoGestor.RotaCerta.Repository;

import br.com.autoGestor.RotaCerta.Model.VeiculoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<VeiculoModel, Long> {
}
