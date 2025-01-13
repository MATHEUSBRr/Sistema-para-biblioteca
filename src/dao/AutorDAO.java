package dao;

import conexao.ConexaoBD;
import model.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    
    public void adicionar(Autor autor) {
        String sql = "INSERT INTO Autores (nome) VALUES (?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, autor.getNome());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar autor: " + e.getMessage());
        }
    }
    
    public List<Autor> listar() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autores";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Autor autor = new Autor(rs.getInt("id"), rs.getString("nome"));
                autores.add(autor);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        }
        return autores;
    }
}
