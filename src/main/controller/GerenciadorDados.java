package controller;

import model.Aluguel;
import model.Cliente;
import model.Veiculo;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
            ArrayList<Cliente> clientes = (ArrayList<Cliente>) ois.readObject();
            ArrayList<Veiculo> veiculos = (ArrayList<Veiculo>) ois.readObject();
            ArrayList<Aluguel> alugueis = (ArrayList<Aluguel>) ois.readObject();

            clienteRepositorio.limpar();
            veiculoRepositorio.limpar();
            aluguelRepositorio.limpar();

            clienteRepositorio.adicionarLista(clientes);
            veiculoRepositorio.adicionarLista(veiculos);
            aluguelRepositorio.adicionarLista(alugueis);

            System.out.println("Dados carregados com sucesso de " + fileName);
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}
