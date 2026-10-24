package br.com.autoGestor.RotaCerta.Model;

import jakarta.persistence.*;

@Entity
public class VeiculoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String marca;
    private String modelo;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private String versao;
    private String motorizacao;
    @Enumerated(EnumType.STRING)
    private Combustivel combustivel;
    private Integer quilometragem;
    private String estadoUF;

    public VeiculoModel(Long id, String marca, String modelo, Integer anoFabricacao, Integer anoModelo,
                        String versao, String motorizacao, Combustivel combustivel, Integer quilometragem,
                        String estadoUF) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.versao = versao;
        this.motorizacao = motorizacao;
        this.combustivel = combustivel;
        this.quilometragem = quilometragem;
        this.estadoUF = estadoUF;
    }

    public VeiculoModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getMotorizacao() {
        return motorizacao;
    }

    public void setMotorizacao(String motorizacao) {
        this.motorizacao = motorizacao;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getEstadoUF() {
        return estadoUF;
    }

    public void setEstadoUF(String estadoUF) {
        this.estadoUF = estadoUF;
    }
}
