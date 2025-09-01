package pagamento;

public class JsonPix {
    private String chave;
    private String nome;
    private String cidade;

    public JsonPix(String chave, String nome, String cpf, String cidade) {
        this.chave = chave;
        this.nome = nome;
        this.cidade = cidade;
    }

    public JsonPix() {

    }

    public String getChave() {
        return chave;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
