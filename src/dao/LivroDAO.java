package dao;

import conexao.ConexaoBD;
import model.Livro;
import model.Autor;
import model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    
    public void adicionar(Livro livro) {
        String sql = "INSERT INTO Livros (titulo, id_autor, id_categoria) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAutor().getId());
            stmt.setInt(3, livro.getCategoria().getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }
    
    public List<Livro> listar() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int idAutor = rs.getInt("id_autor");
                int idCategoria = rs.getInt("id_categoria");
                
                Autor autor = new AutorDAO().listar().stream().filter(a -> a.getId() == idAutor).findFirst().orElse(null);
                Categoria categoria = new CategoriaDAO().listar().stream().filter(c -> c.getId() == idCategoria).findFirst().orElse(null);
                
                Livro livro = new Livro(id, titulo, autor, categoria);
                livros.add(livro);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }
}

