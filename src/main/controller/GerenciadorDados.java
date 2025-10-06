package controller;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.Repositorio;
import repository.VeiculoRepositorio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GerenciadorDados {
    private final ClienteRepositorio clienteRepositorio;
    private final VeiculoRepositorio veiculoRepositorio;
    private final AluguelRepositorio aluguelRepositorio;

    public GerenciadorDados(ClienteRepositorio clienteRepositorio, VeiculoRepositorio veiculoRepositorio, AluguelRepositorio aluguelRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.veiculoRepositorio = veiculoRepositorio;
        this.aluguelRepositorio = aluguelRepositorio;
    }

    public void salvarDados(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(clienteRepositorio.getLista());
            oos.writeObject(veiculoRepositorio.getLista());
            oos.writeObject(aluguelRepositorio.getLista());
            System.out.println("Dados salvos com sucesso em " + fileName);
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Cliente> clientes = (List<Cliente>) ois.readObject();
            List<Veiculo> veiculos = (List<Veiculo>) ois.readObject();
            List<Aluguel> alugueis = (List<Aluguel>) ois.readObject();

            carregarRepositorio(clienteRepositorio, clientes);
            carregarRepositorio(veiculoRepositorio, veiculos);
            carregarRepositorio(aluguelRepositorio, alugueis);

            System.out.println("Dados carregados com sucesso de " + fileName);
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private <T, K> void carregarRepositorio(Repositorio<T, K> repo, List<T> dados) {
        Consumer<Repositorio<T, K>> limpar = Repositorio::limparLista;
        Consumer<Repositorio<T, K>> adicionar = r -> r.adicionarLista(new ArrayList<>(dados));
        Consumer<Repositorio<T, K>> pipeline = limpar.andThen(adicionar);

        pipeline.accept(repo);
    }
}
