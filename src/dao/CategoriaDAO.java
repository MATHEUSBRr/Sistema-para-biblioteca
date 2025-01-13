package dao;

import conexao.ConexaoBD;
import model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
    public void adicionar(Categoria categoria) {
        String sql = "INSERT INTO Categorias (descricao) VALUES (?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria.getDescricao());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar categoria: " + e.getMessage());
        }
    }
    
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("descricao"));
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
        return categorias;
    }
}
