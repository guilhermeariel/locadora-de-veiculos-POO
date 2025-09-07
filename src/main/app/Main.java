package app;

import model.*;
import repository.*;
import servicos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    AluguelRepository aluguelRepository = new AluguelRepository();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    VeiculoRepositorio veiculoRepositorio = new VeiculoRepositorio();

    // Serviços
    ClienteService clienteService = new ClienteServiceImpl(clienteRepositorio);
    VeiculoService veiculoService = new VeiculoService(veiculoRepositorio);
    AluguelService aluguelService = new AluguelService(aluguelRepository);

    // Cadastro de clientes
    clienteService.cadastrarCliente("João", "123.456.789-00", true);
    clienteService.cadastrarCliente("Maria", "124.406.457-01", true);
    clienteService.cadastrarCliente("Empresa ABC", "12.345.678/0001-00", false);

    // Busca os clientes cadastrados
    List<Cliente> clientes = List.of(
        clienteService.buscarClientePorId("123.456.789-00"),
        clienteService.buscarClientePorId("124.406.457-01"),
        clienteService.buscarClientePorId("12.345.678/0001-00")
    );

    // Cria veículos para cada cliente
    List<Veiculo> veiculos = List.of(
        new Veiculo("ABC-1234", TipoVeiculo.PEQUENO, "Gol", true),
        new Veiculo("XYZ-5678", TipoVeiculo.SUV, "Creta", true),
        new Veiculo("GHJ-9999", TipoVeiculo.PEQUENO, "Onix", true)
    );

    // Cadastra os veículos
    for (Veiculo v : veiculos) {
      veiculoService.cadastrar(v);
    }

    // Cria e registra os aluguéis dinamicamente
    List<Aluguel> alugueis = new ArrayList<>();

    for (int i = 0; i < clientes.size(); i++) {
      Aluguel aluguel = new Aluguel(
          clientes.get(i),
          veiculos.get(i),
          LocalDateTime.now(),
          LocalDateTime.now().plusDays(3)
      );
      aluguelService.registrarAluguel(aluguel);
      alugueis.add(aluguel);
    }

    // Lista todos os clientes
    System.out.println("\n Clientes cadastrados:");
    for (Cliente cliente : clienteService.listarClientes()) {
      System.out.println(cliente);
    }

    // Lista todos os aluguéis
    System.out.println("\n Aluguéis registrados:");
    for (Aluguel a : alugueis) {
      System.out.println(a);
    }
  }
}
