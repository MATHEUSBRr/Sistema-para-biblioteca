package src;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.EmprestimoDAO;
import dao.LivroDAO;
import model.Autor;
import model.Categoria;
import model.Emprestimo;
import model.Livro;

import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main extends JFrame {

    private JButton btnAdicionarAutor, btnAdicionarCategoria, btnAdicionarLivro;
    private JButton btnListarAutores, btnListarCategorias, btnListarLivros;
    private JButton btnRegistrarEmprestimo, btnListarEmprestimos, btnRegistrarDevolucao; // Novo botão para registrar devolução
    private JTextArea textArea;

    public Main() {
        setTitle("Sistema de Biblioteca");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        btnAdicionarAutor = new JButton("Adicionar Autor");
        btnAdicionarCategoria = new JButton("Adicionar Categoria");
        btnAdicionarLivro = new JButton("Adicionar Livro");
        btnListarAutores = new JButton("Listar Autores");
        btnListarCategorias = new JButton("Listar Categorias");
        btnListarLivros = new JButton("Listar Livros");
        btnRegistrarEmprestimo = new JButton("Registrar Empréstimo");
        btnListarEmprestimos = new JButton("Listar Empréstimos");
        btnRegistrarDevolucao = new JButton("Registrar Devolução"); // Novo botão

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panelButtons = new JPanel(new GridLayout(4, 5, 10, 10)); 
        panelButtons.add(btnAdicionarAutor);
        panelButtons.add(btnAdicionarCategoria);
        panelButtons.add(btnAdicionarLivro);
        panelButtons.add(btnRegistrarEmprestimo);
        panelButtons.add(btnListarAutores);
        panelButtons.add(btnListarCategorias);
        panelButtons.add(btnListarLivros);
        panelButtons.add(btnListarEmprestimos);
        panelButtons.add(btnRegistrarDevolucao); 

        add(panelButtons, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnAdicionarAutor.addActionListener(e -> adicionarAutor());
        btnAdicionarCategoria.addActionListener(e -> adicionarCategoria());
        btnAdicionarLivro.addActionListener(e -> adicionarLivro());
        btnRegistrarEmprestimo.addActionListener(e -> registrarEmprestimo());
        btnListarAutores.addActionListener(e -> listarAutores());
        btnListarCategorias.addActionListener(e -> listarCategorias());
        btnListarLivros.addActionListener(e -> listarLivros());
        btnListarEmprestimos.addActionListener(e -> listarEmprestimos());
        btnRegistrarDevolucao.addActionListener(e -> registrarDevolucao());
    }

    private void adicionarAutor() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do autor:");
        if (nome != null && !nome.trim().isEmpty()) {
            Autor autor = new Autor(nome);
            new AutorDAO().adicionar(autor);
            JOptionPane.showMessageDialog(this, "Autor adicionado com sucesso!");
        }
    }

    private void adicionarCategoria() {
        String descricao = JOptionPane.showInputDialog(this, "Digite a descrição da categoria:");
        if (descricao != null && !descricao.trim().isEmpty()) {
            Categoria categoria = new Categoria(descricao);
            new CategoriaDAO().adicionar(categoria);
            JOptionPane.showMessageDialog(this, "Categoria adicionada com sucesso!");
        }
    }

    private void adicionarLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Digite o título do livro:");
        String autorNome = JOptionPane.showInputDialog(this, "Digite o nome do autor:");
        String categoriaDescricao = JOptionPane.showInputDialog(this, "Digite a descrição da categoria:");

        if (titulo != null && autorNome != null && categoriaDescricao != null &&
            !titulo.trim().isEmpty() && !autorNome.trim().isEmpty() && !categoriaDescricao.trim().isEmpty()) {

            Autor autor = new AutorDAO().listar().stream()
                .filter(a -> a.getNome().equals(autorNome)).findFirst().orElse(null);

            Categoria categoria = new CategoriaDAO().listar().stream()
                .filter(c -> c.getDescricao().equals(categoriaDescricao)).findFirst().orElse(null);

            if (autor == null) {
                autor = new Autor(autorNome);
                new AutorDAO().adicionar(autor);
            }

            if (categoria == null) {
                categoria = new Categoria(categoriaDescricao);
                new CategoriaDAO().adicionar(categoria);
            }

            Livro livro = new Livro(titulo, autor, categoria);
            new LivroDAO().adicionar(livro);

            JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");
        }
    }

    private void registrarEmprestimo() {
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.listar();

        if (livros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há livros disponíveis para empréstimo.");
            return;
        }

        String[] titulos = livros.stream().map(Livro::getTitulo).toArray(String[]::new);
        String tituloSelecionado = (String) JOptionPane.showInputDialog(this, 
            "Selecione um livro para empréstimo:", 
            "Registrar Empréstimo", 
            JOptionPane.PLAIN_MESSAGE, 
            null, 
            titulos, 
            titulos[0]);

        if (tituloSelecionado == null) return;

        Livro livroSelecionado = livros.stream()
            .filter(livro -> livro.getTitulo().equals(tituloSelecionado))
            .findFirst()
            .orElse(null);

        if (livroSelecionado != null) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setLivro(livroSelecionado);
            emprestimo.setDataRetirada(new java.util.Date());

            EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
            emprestimoDAO.adicionar(emprestimo);

            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");
        }
    }

   private void registrarDevolucao() {
    try {
        String idEmprestimoStr = JOptionPane.showInputDialog(this, "Digite o ID do empréstimo:");
        int idEmprestimo = Integer.parseInt(idEmprestimoStr);

        String dataDevolucaoStr = JOptionPane.showInputDialog(this, "Digite a data de devolução (yyyy-MM-dd):");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = sdf.parse(dataDevolucaoStr);
        java.sql.Date dataDevolucao = new java.sql.Date(utilDate.getTime());

        new EmprestimoDAO().registrarDevolucao(idEmprestimo, dataDevolucao);
        JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao registrar devolução: " + e.getMessage());
    }
}

    private void listarAutores() {
        List<Autor> autores = new AutorDAO().listar();
        textArea.setText("Lista de Autores:\n");
        for (Autor autor : autores) {
            textArea.append(autor.getId() + " - " + autor.getNome() + "\n");
        }
    }

    private void listarCategorias() {
        List<Categoria> categorias = new CategoriaDAO().listar();
        textArea.setText("Lista de Categorias:\n");
        for (Categoria categoria : categorias) {
            textArea.append(categoria.getId() + " - " + categoria.getDescricao() + "\n");
        }
    }

    private void listarLivros() {
        List<Livro> livros = new LivroDAO().listar();
        textArea.setText("Lista de Livros:\n");
        for (Livro livro : livros) {
            textArea.append(livro.getId() + " - " + livro.getTitulo() +
                            " (Autor: " + livro.getAutor().getNome() +
                            ", Categoria: " + livro.getCategoria().getDescricao() + ")\n");
        }
    }

    private void listarEmprestimos() {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        List<Emprestimo> emprestimos = emprestimoDAO.listar();

        if (emprestimos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum empréstimo encontrado.");
            return;
        }

        textArea.setText("Lista de Empréstimos:\n");
        for (Emprestimo emprestimo : emprestimos) {
            textArea.append("ID: " + emprestimo.getId() + "\n");
            textArea.append("Livro: " + emprestimo.getLivro().getTitulo() + "\n");
            textArea.append("Data de Retirada: " + emprestimo.getDataRetirada() + "\n");
            textArea.append("Data de Devolução: " +
                            (emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao() : "Não devolvido") + "\n\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
