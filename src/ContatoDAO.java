import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private Connection connection;

    public ContatoDAO(Connection connection){
        this.connection = connection;
    }

    public void insere(Contato contato){
        String sql = "insert into contatos (id, nome, email, telefone) values (?,?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, contato.getId());
            stmt.setString(2, contato.getNome());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getTelefone());
            stmt.executeUpdate();
            System.out.println("Contato Inserido com Sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void altera(Contato contato) throws SQLException {
        String sql = "update contatos set nome=?, email=?, telefone=? where id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getTelefone());
            stmt.setInt(4, contato.getId());
            int rows = stmt.executeUpdate();
            if(rows > 0){
                System.out.println("Contato Atualizado!");
            }else{
                System.out.println("Contato nao Encontrado");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void remove(@org.jetbrains.annotations.NotNull Contato contato) {
        String sql = "DELETE FROM contatos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contato.getId());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Contato removido com sucesso!");
            } else {
                System.out.println("Contato não encontrado para remoção.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover contato: " + e.getMessage());
        }
    }

    public List<Contato> lista() {
        String sql = "SELECT * FROM contatos";
        List<Contato> contatos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contato contato = new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                contatos.add(contato);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contatos: " + e.getMessage());
        }
        return contatos;
    }
}
