import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String pass = "novaSenha";

        try(Connection connection = DriverManager.getConnection(url, user, pass)){
            ContatoDAO contatoDAO = new ContatoDAO(connection);

            //insere contato
            Contato novoContato = new Contato(1, "Arthur", "arthur@arthur.com", "99123456");
            contatoDAO.insere(novoContato);

            //listar contatos
            List<Contato> contatos = contatoDAO.lista();
            contatos.forEach(System.out::println);

            //alterar contato
            novoContato.setNome("Mariana");
            contatoDAO.altera(novoContato);

            //teste remove
            contatoDAO.remove(novoContato);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}