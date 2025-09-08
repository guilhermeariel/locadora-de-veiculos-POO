package main;

import java.util.List;
import model.*;
import repository.*;
import servicos.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainScanner {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    VeiculoRepositorio veiculoRepo = new VeiculoRepositorio();
    ClienteRepositorio clienteRepo = new ClienteRepositorio();
    AluguelRepository aluguelRepo = new AluguelRepository();

    VeiculoService veiculoService = new VeiculoService(veiculoRepo);
    ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepo);
    AluguelService aluguelService = new AluguelService(aluguelRepo);

    int opcao;
    do {
      System.out.println("\n--- MENU LOCATECAR ---");
      System.out.println("1. Cadastrar veículo");
      System.out.println("2. Listar veículos");
      System.out.println("3. Cadastrar cliente");
      System.out.println("4. Listar clientes");
      System.out.println("5. Alugar veículo");
      System.out.println("6. Lista de veiculos alugados");
      System.out.println("7. Devolver veículo");
      System.out.println("0. Sair");
      System.out.print("Escolha uma opção: ");
      opcao = scanner.nextInt();
      scanner.nextLine(); // limpa buffer

      switch (opcao) {
        case 1:
          System.out.print("Placa: ");
          String placa = scanner.nextLine();
          System.out.print("Modelo: ");
          String modelo = scanner.nextLine();
          System.out.print("Tipo (PEQUENO, MEDIO, SUV): ");
          String tipoStr = scanner.nextLine().toUpperCase();
          try {
            TipoVeiculo tipo = TipoVeiculo.valueOf(tipoStr);
            Veiculo novoVeiculo = new Veiculo(placa, tipo, modelo, true);
            veiculoService.cadastrar(novoVeiculo);
          } catch (IllegalArgumentException e) {
            System.out.println("Tipo de veículo inválido. Use: PEQUENO, MEDIO ou SUV.");
          }
          break;

        case 2:
          System.out.println("\n--- VEÍCULOS CADASTRADOS ---");
          veiculoRepo.listar().forEach(System.out::println);
          break;

        case 3:
          System.out.print("Nome: ");
          String nome = scanner.nextLine();
          System.out.print("CPF ou CNPJ: ");
          String doc = scanner.nextLine();
          System.out.print("É pessoa física? (s/n): ");
          boolean isPf = scanner.nextLine().equalsIgnoreCase("s");
          clienteService.cadastrarCliente(nome, doc, isPf);
          System.out.println("Cliente cadastrado com sucesso!");
          break;

        case 4:
          System.out.println("\n--- CLIENTES CADASTRADOS ---");
          clienteRepo.listar().forEach(System.out::println);
          break;

        case 5:
          System.out.print("Placa do veículo: ");
          String placaAluguel = scanner.nextLine();
          System.out.print("CPF ou CNPJ do cliente: ");
          String docAluguel = scanner.nextLine();
          Veiculo veiculoAluguel = veiculoRepo.buscarPorIdentificador(placaAluguel);
          Cliente clienteAluguel = clienteRepo.buscarPorIdentificador(docAluguel);
          aluguelService.alugar(clienteAluguel, veiculoAluguel);
          break;

        case 6: // ✅ Listar veículos alugados
          System.out.println("\n--- VEÍCULOS ALUGADOS ---");
          List<Veiculo> alugados = veiculoService.listarVeiculosAlugados();
          if (alugados.isEmpty()) {
            System.out.println("Nenhum veículo está alugado no momento.");
          } else {
            alugados.forEach(System.out::println);
          }
          break;

        case 7: // ✅ Devolver veículo
          System.out.print("Placa do veículo: ");
          String placaDevolucao = scanner.nextLine();
          System.out.print("CPF ou CNPJ do cliente: ");
          String docDevolucao = scanner.nextLine();
          Veiculo veiculoDevolucao = veiculoRepo.buscarPorIdentificador(placaDevolucao);
          Cliente clienteDevolucao = clienteRepo.buscarPorIdentificador(docDevolucao);
          aluguelService.devolver(clienteDevolucao, veiculoDevolucao);
          break;

        case 0:
          System.out.println("Encerrando o sistema...");
          break;

        default:
          System.out.println("Opção inválida. Tente novamente.");
      }

    } while (opcao != 0);

    scanner.close();
  }
}
