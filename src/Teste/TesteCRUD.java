/* package teste;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.LivroDAO;
import model.Autor;
import model.Categoria;
import model.Livro;

public class TesteCRUD {
    public static void main(String[] args) {

        Autor autor = new Autor("George Orwell");
        new AutorDAO().adicionar(autor); 
        
        Categoria categoria = new Categoria("Distopia");
        new CategoriaDAO().adicionar(categoria);

        autor = new AutorDAO().listar().stream().filter(a -> a.getNome().equals("George Orwell")).findFirst().orElse(null);
        categoria = new CategoriaDAO().listar().stream().filter(c -> c.getDescricao().equals("Distopia")).findFirst().orElse(null);

        Livro livro = new Livro("1984", autor, categoria);
        new LivroDAO().adicionar(livro);

        System.out.println(new LivroDAO().listar());
    }
}*/
//Apenas teste 
