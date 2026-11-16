package br.com.autoGestor.RotaCerta.Controller;

import br.com.autoGestor.RotaCerta.Model.Combustivel;
import br.com.autoGestor.RotaCerta.Model.VeiculoModel;
import br.com.autoGestor.RotaCerta.Services.VeiculoServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class avaliacaoController {

    record Estado(double licenciamento, double gasolina, double etanol,
                  double diesel, double gnv, double kwh) {}

    record Problema(String descricao, double custoAnual) {}

    // Licenciamento: PE é ESTIMATIVA (confirme no Detran-PE); CE conforme Detran-CE.
    // Preços de combustível: valores de EXEMPLO, troque pelos reais.
    private static final Map<String, Estado> ESTADOS = Map.of(
            "PE", new Estado(158.00, 6.00, 4.20, 6.00, 4.50, 0.90),
            "CE", new Estado(220.46, 6.20, 4.40, 6.10, 4.80, 0.95)
    );

    // chave = "marca modelo" em minúsculo. EXEMPLOS: troque pelos reais
    private static final Map<String, Problema> PROBLEMAS = Map.of(
            "fiat uno", new Problema("Exemplo: desgaste de suspensão", 800),
            "chevrolet onix", new Problema("Exemplo: falha em câmbio", 500)
    );

    private final VeiculoServices veiculoServices;

    public avaliacaoController(VeiculoServices veiculoServices) {
        this.veiculoServices = veiculoServices;
    }

    
    private double calcularIpva(String uf, VeiculoModel v, double valor,
                                double potenciaCv, List<String> obs) {

        int idade = Year.now().getValue() - v.getAnoFabricacao();
        Combustivel comb = v.getCombustivel();

        if (uf.equals("PE")) {
            if (idade >= 20) {
                obs.add("IPVA isento em PE (20 anos ou mais de fabricação)");
                return 0;
            }
            if (comb == Combustivel.ELETRICO) {
                obs.add("IPVA isento em PE (100% elétrico)");
                return 0;
            }
            if (comb == Combustivel.GNV && valor <= 100000) {
                return valor * 0.015;
            }
            return valor * 0.024;
        }

        if (uf.equals("CE")) {
            if (idade > 15) {
                obs.add("IPVA isento no CE (mais de 15 anos de fabricação)");
                return 0;
            }
            double aliquota = potenciaCv <= 100 ? 0.025
                    : potenciaCv <= 180 ? 0.03
                    : 0.035;
            return valor * aliquota;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UF sem regra de IPVA");
    }

    @GetMapping("/api/VeiculoModel/{id}/avaliacao")
    public Map<String, Object> avaliar(@PathVariable Long id,
                                       @RequestParam String uf,
                                       @RequestParam double valorVeiculo,
                                       @RequestParam double kmPorMes,
                                       @RequestParam double consumo,
                                       @RequestParam(defaultValue = "100") double potenciaCv) {

        if (valorVeiculo <= 0 || consumo <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Valor e consumo devem ser maiores que zero");
        }

        uf = uf.toUpperCase();
        VeiculoModel v = veiculoServices.buscarPorId(id);

        Estado e = ESTADOS.get(uf);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UF sem dados cadastrados");
        }

        List<String> observacoes = new ArrayList<>();

        // 1) documentação = IPVA + licenciamento
        double ipva = calcularIpva(uf, v, valorVeiculo, potenciaCv, observacoes);
        double documentacao = ipva + e.licenciamento();

        // 2) combustível (flex usa o preço da gasolina)
        double preco = switch (v.getCombustivel()) {
            case GASOLINA, FLEX -> e.gasolina();
            case ALCOOL -> e.etanol();
            case DIESEL -> e.diesel();
            case GNV -> e.gnv();
            case ELETRICO -> e.kwh();
        };
        double combustivel = (kmPorMes * 12 / consumo) * preco;

        // 3) problemas crônicos
        List<String> descricoes = new ArrayList<>();
        double custoProblemas = 0;
        Problema p = PROBLEMAS.get((v.getMarca() + " " + v.getModelo()).trim().toLowerCase());
        if (p != null) {
            custoProblemas = p.custoAnual();
            descricoes.add(p.descricao());
        }

        // resultado
        double total = documentacao + combustivel + custoProblemas;
        double percentual = total / valorVeiculo * 100;

        String veredito = percentual < 20 ? "VALE A PENA"
                : percentual < 35 ? "ATENÇÃO"
                : "NÃO COMPENSA";

        return Map.of(
                "documentacao", documentacao,
                "ipva", ipva,
                "licenciamento", e.licenciamento(),
                "combustivel", combustivel,
                "problemasCronicos", custoProblemas,
                "custoAnualTotal", total,
                "percentualDoValor", percentual,
                "veredito", veredito,
                "problemas", descricoes,
                "observacoes", observacoes
        );
    }
}