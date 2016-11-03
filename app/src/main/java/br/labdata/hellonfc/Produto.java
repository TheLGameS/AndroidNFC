package br.carlos.nupeds.hellonfc;

/**
 * Created by CARLOS.VILARINHO on 03/11/2016 11:20.
 */

public class Produto {

    private Integer codigo;
    private String descricao;

    public Produto(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Produto() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
