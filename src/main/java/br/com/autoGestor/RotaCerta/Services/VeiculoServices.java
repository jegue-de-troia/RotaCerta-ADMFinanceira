package br.com.autoGestor.RotaCerta.Services;

import br.com.autoGestor.RotaCerta.Model.VeiculoModel;
import br.com.autoGestor.RotaCerta.Repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VeiculoServices {

    private final VeiculoRepository veiculorepository;

    public VeiculoServices(VeiculoRepository veiculorepository){

        this.veiculorepository = veiculorepository;
    }

    public VeiculoModel cadastrarVeiculo(VeiculoModel veiculosmodel){
        veiculosmodel.setId(null); // para garantir que sempre seja um cadastro novo
        return veiculorepository.save(veiculosmodel);
    }

    public List<VeiculoModel> listarTodosVeiculos(){
        return veiculorepository.findAll();
    }

    public VeiculoModel buscarPorId(Long id){
        return veiculorepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, " Veículo nao encontrado"));
    }

   public VeiculoModel atualizarVeiculo(Long id, VeiculoModel veiculosmodel){
        VeiculoModel veiculo = buscarPorId(id);
        if (veiculo.getQuilometragem() < veiculosmodel.getQuilometragem()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "a Quilometragem não pode diminuir");
        }
        veiculo.setMarca(veiculosmodel.getMarca());
        veiculo.setModelo(veiculosmodel.getModelo());
        veiculo.setAnoFabricacao(veiculosmodel.getAnoFabricacao());
        veiculo.setAnoModelo(veiculosmodel.getAnoModelo());
        veiculo.setVersao(veiculosmodel.getVersao());
        veiculo.setMotorizacao(veiculosmodel.getMotorizacao());
        veiculo.setCombustivel(veiculosmodel.getCombustivel());
        veiculo.setQuilometragem(veiculosmodel.getQuilometragem());
        veiculo.setEstadoUF(veiculosmodel.getEstadoUF());
        return veiculorepository.save(veiculo);
   }

   public void deletarVeiculo(Long id ){
        buscarPorId(id);
        veiculorepository.deleteById(id);
   }

}
