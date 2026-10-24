package br.com.autoGestor.RotaCerta.Controller;

import br.com.autoGestor.RotaCerta.Model.VeiculoModel;
import br.com.autoGestor.RotaCerta.Services.VeiculoServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/VeiculoModel")
public class VeiculoController {

    private final VeiculoServices veiculoservices;

    public VeiculoController (VeiculoServices veiculoservices){
        this.veiculoservices = veiculoservices;
    }
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@RequestBody VeiculoModel veiculomodel){
        return veiculoservices.cadastrarVeiculo(veiculomodel);
   }

   @GetMapping
    public List<VeiculoModel> listarVeiculos(){
        return veiculoservices.listarTodosVeiculos();
   }

   @GetMapping("/{id}")
    public VeiculoModel buscarVeiculo(@PathVariable Long id ){
        return veiculoservices.buscarPorId(id);
   }

   @PutMapping("/{id}")
    public VeiculoModel atualizarDadosVeicular(@PathVariable Long id, @RequestBody VeiculoModel veiculomodel ){
        return veiculoservices.atualizarVeiculo(id, veiculomodel);
   }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deletarVeiculo(@PathVariable Long id){
        veiculoservices.deletarVeiculo(id);
   }
}
