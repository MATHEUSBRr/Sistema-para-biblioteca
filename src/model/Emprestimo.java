package model;

import java.util.Date;

//criação da classe empréstimo
public class Emprestimo {
    private int id;
    private Livro livro;
    private Date dataRetirada;
    private Date dataDevolucao;

    public Emprestimo() {
    }

    public Emprestimo(int id, Livro livro, Date dataRetirada, Date dataDevolucao) {
        this.id = id;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
