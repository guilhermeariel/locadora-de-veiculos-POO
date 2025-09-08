//package app;
//
//import model.*;
//import repository.*;
//import servicos.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//  public static void main(String[] args) {
//
//    // Repositórios
//    AluguelRepository aluguelRepository = new AluguelRepository();
//    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
//    VeiculoRepositorio veiculoRepositorio = new VeiculoRepositorio();
//
//    // Serviços
//    ClienteService clienteService = new ClienteServiceImpl(clienteRepositorio);
//    VeiculoService veiculoService = new VeiculoService(veiculoRepositorio);
//    AluguelService aluguelService = new AluguelService(aluguelRepository);
//
//    // Cadastro de clientes
//    clienteService.cadastrarCliente("João", "123.456.789-00", true);
//    clienteService.cadastrarCliente("Maria", "124.406.457-01", true);
//    clienteService.cadastrarCliente("Empresa ABC", "12.345.678/0001-00", false);
//
//    // Busca os clientes cadastrados
//    List<Cliente> clientes = List.of(
//        clienteService.buscarClientePorId("123.456.789-00"),
//        clienteService.buscarClientePorId("124.406.457-01"),
//        clienteService.buscarClientePorId("12.345.678/0001-00")
//    );
//
//    // Cria veículos
//    List<Veiculo> veiculos = List.of(
//        new Veiculo("ABC-1234", TipoVeiculo.PEQUENO, "Gol", true),
//        new Veiculo("XYZ-5678", TipoVeiculo.SUV, "Creta", true),
//        new Veiculo("GHJ-9999", TipoVeiculo.PEQUENO, "Onix", true)
//    );
//
//    // Cadastra veículos
//    for (Veiculo v : veiculos) {
//      veiculoService.cadastrar(v);
//    }
//
//    // Registra aluguéis
//    List<Aluguel> alugueis = new ArrayList<>();
//    for (int i = 0; i < clientes.size(); i++) {
//      Aluguel aluguel = new Aluguel(
//          clientes.get(i),
//          veiculos.get(i),
//          LocalDateTime.now().minusDays(3), // início simulado 3 dias atrás
//          LocalDateTime.now()               // fim simulado agora
//      );
//      aluguelService.registrarAluguel(aluguel);
//      alugueis.add(aluguel);
//    }
//
//    // Lista clientes
//    System.out.println("\nClientes cadastrados:");
//    for (Cliente cliente : clienteService.listarClientes()) {
//      System.out.println(cliente);
//    }
//
//    // Lista aluguéis
//    System.out.println("\nAluguéis registrados:");
//    for (Aluguel a : alugueis) {
//      System.out.println(a);
//    }
//
//    // Faz devolução de veículos
//    System.out.println("\n--- DEVOLUÇÃO DE VEÍCULOS ---");
//    for (int i = 0; i < clientes.size(); i++) {
//      Cliente cliente = clientes.get(i);
//      Veiculo veiculo = veiculos.get(i);
//
//      aluguelService.devolver(cliente, veiculo);
//
//      Cliente joao = clienteService.buscarClientePorId("123.456.789-00");
//      Veiculo gol = veiculoService.buscarPorPlaca("ABC-1234");
//
//      aluguelService.devolver(joao, gol);
//
//      // Recupera o aluguel atualizado e imprime o valor
//      Aluguel aluguelDevolvido = aluguelRepository.buscarPorVeiculo(veiculo);
//      if (aluguelDevolvido != null) {
//        System.out.printf("Valor final do aluguel de %s (%s): R$ %.2f\n",
//            veiculo.getModelo(),
//            veiculo.getPlaca(),
//            aluguelDevolvido.calcularValor());
//      }
//    }
//
//    // Verifica disponibilidade
//    System.out.println("\nStatus de disponibilidade dos veículos:");
//    for (Veiculo v : veiculos) {
//      System.out.printf("%s (%s) disponível: %b\n", v.getModelo(), v.getPlaca(), v.isDisponivel());
//    }
//  }
//}
