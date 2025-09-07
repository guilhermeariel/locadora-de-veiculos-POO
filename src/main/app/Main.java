package app;

import model.*;
import repository.*;
import servicos.*;

import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {

    AluguelRepository aluguelRepository = new AluguelRepository();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    VeiculoRepositorio veiculoRepositorio = new VeiculoRepositorio();

    // Serviços
    ClienteService clienteService = new ClienteServiceImpl(clienteRepositorio);
    VeiculoService veiculoService = new VeiculoService(veiculoRepositorio);
    AluguelService aluguelService = new AluguelService(aluguelRepository);

    // Exemplo de cadastro de cliente PF
    clienteService.cadastrarCliente("João", "123.456.789-00", true);

    // Exemplo de cadastro de cliente PJ
    clienteService.cadastrarCliente("Empresa ABC", "12.345.678/0001-00", false);

    // Buscar cliente cadastrado (para usar no aluguel)
    Cliente pf = clienteService.buscarClientePorId("123.456.789-00");

    // Exemplo de cadastro de veículo
    Veiculo carro = new Veiculo("ABC-1234", TipoVeiculo.PEQUENO, "Gol", true);
    veiculoService.cadastrar(carro);

    // Exemplo de aluguel
    Aluguel aluguel = new Aluguel(pf, carro, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
    aluguelService.registrarAluguel(aluguel);

    System.out.println("Veículo cadastrado:");
    System.out.println(carro);
  }
}
