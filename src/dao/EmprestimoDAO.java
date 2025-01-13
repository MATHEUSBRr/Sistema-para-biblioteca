package dao;

import conexao.ConexaoBD;
import model.Emprestimo;
import model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoDAO {

    public void adicionar(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimos (id_livro, data_retirada, data_devolucao) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getLivro().getId());
            stmt.setDate(2, new java.sql.Date(emprestimo.getDataRetirada().getTime()));
            if (emprestimo.getDataDevolucao() != null) {
                stmt.setDate(3, new java.sql.Date(emprestimo.getDataDevolucao().getTime()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar empréstimo: " + e.getMessage());
        }
    }

    public List<Emprestimo> listar() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = """
            SELECT e.id, e.data_retirada, e.data_devolucao, l.id AS livro_id, l.titulo
            FROM Emprestimos e
            INNER JOIN Livros l ON e.id_livro = l.id
        """;

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Date dataRetirada = rs.getDate("data_retirada");
                Date dataDevolucao = rs.getDate("data_devolucao");

                Livro livro = new Livro();
                livro.setId(rs.getInt("livro_id"));
                livro.setTitulo(rs.getString("titulo"));

                Emprestimo emprestimo = new Emprestimo(id, livro, dataRetirada, dataDevolucao);
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empréstimos: " + e.getMessage());
        }
        return emprestimos;
    }

    public void registrarDevolucao(int idEmprestimo, Date dataDevolucao) {
        String sql = "UPDATE Emprestimos SET data_devolucao = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(dataDevolucao.getTime()));
            stmt.setInt(2, idEmprestimo);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Devolução registrada com sucesso!");
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }
}
