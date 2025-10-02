package app;

import java.util.List;
import model.*;
import repository.*;
import servicos.*;

import java.util.Scanner;

public class MainScanner {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    VeiculoRepositorio veiculoRepo = new VeiculoRepositorio();
    ClienteRepositorio clienteRepo = new ClienteRepositorio();
    AluguelRepositorio aluguelRepo = new AluguelRepositorio();

    VeiculoService veiculoService = new VeiculoService(veiculoRepo);
    ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepo);
    AluguelServiceImpl aluguelService = new AluguelServiceImpl(aluguelRepo, veiculoRepo, clienteRepo);

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
          System.out.print("Placa (ex: ABC1D23): ");
          String placa = scanner.nextLine().toUpperCase();
          if (!placa.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$")) {
            System.out.println("❌ Placa inválida. Formato esperado: ABC1D23.");
            break;
          }

          System.out.print("Modelo (ex: Fiat Argo, Toyota Corolla 2.0, Jeep Compass): ");
          String modelo = scanner.nextLine();

          System.out.print("Tipo (Hatch, Sedan ou SUV): ");
          String tipoStr = scanner.nextLine().toUpperCase();
          try {
            TipoVeiculo tipo = TipoVeiculo.valueOf(tipoStr);
            Veiculo novoVeiculo = new Veiculo(placa, tipo, modelo, true);
            veiculoService.cadastrar(novoVeiculo);
          } catch (IllegalArgumentException e) {
            System.out.println("❌ Tipo inválido. Use: Hatch, Sedan ou SUV.");
          }
          break;


        case 2:
          System.out.println("\n--- VEÍCULOS CADASTRADOS ---");
          veiculoRepo.getLista().forEach(System.out::println);
          break;

        case 3:
          System.out.print("Nome: ");
          String nome = scanner.nextLine();
          System.out.print("CPF ou CNPJ: ");
          String doc = scanner.nextLine().replaceAll("[^\\d]", ""); // remove pontos, traços, barras
          boolean isPf;
          if (doc.matches("^\\d{11}$")) {
            isPf = true;
          } else if (doc.matches("^\\d{14}$")) {
            isPf = false;
          } else {
            System.out.println("❌ Documento inválido. CPF deve ter 11 dígitos e CNPJ 14 dígitos.");
            break;
          }
          clienteService.cadastrarCliente(nome, doc, isPf);
          System.out.println("✅ Cliente cadastrado com sucesso!");
          break;

        case 4:
          System.out.println("\n--- CLIENTES CADASTRADOS ---");
          clienteRepo.getLista().forEach(System.out::println);
          break;

        case 5:
          System.out.print("Placa do veículo: ");
          String placaAluguel = scanner.nextLine().toUpperCase();
          Veiculo veiculoAluguel = veiculoRepo.buscarPorIdentificador(placaAluguel);
          if (veiculoAluguel == null) {
            System.out.println("❌ Veículo com a placa informada não foi encontrado.");
            break;
          }
          if (!veiculoAluguel.isDisponivel()) {
            System.out.println("❌ Veículo já está alugado.");
            break;
          }
          System.out.print("CPF ou CNPJ do cliente: ");
          String docAluguel = scanner.nextLine().replaceAll("[^\\d]", ""); // normaliza documento
          Cliente clienteAluguel = clienteRepo.buscarPorIdentificador(docAluguel);
          if (clienteAluguel == null) {
            System.out.println("❌ Cliente com o CPF/CNPJ informado não foi encontrado.");
            break;
          }
          aluguelService.alugarVeiculo(clienteAluguel, veiculoAluguel);
          System.out.println("✅ Aluguel realizado com sucesso!");
          break;

        case 6: //  Listar veículos alugados
          System.out.println("\n--- VEÍCULOS ALUGADOS ---");
          List<Veiculo> alugados = veiculoService.listarVeiculosAlugados();
          if (alugados.isEmpty()) {
            System.out.println("Nenhum veículo está alugado no momento.");
          } else {
            alugados.forEach(System.out::println);
          }
          break;

        case 7:
          System.out.print("Placa do veículo: ");
          String placaDevolucao = scanner.nextLine().toUpperCase();
          Veiculo veiculoDevolucao = veiculoRepo.buscarPorIdentificador(placaDevolucao);
          if (veiculoDevolucao == null) {
            System.out.println("❌ Veículo com a placa informada não foi encontrado.");
            break;
          }
          if (veiculoDevolucao.isDisponivel()) {
            System.out.println("❌ Este veículo não está alugado no momento.");
            break;
          }
          System.out.print("CPF ou CNPJ do cliente: ");
          String docDevolucao = scanner.nextLine().replaceAll("[^\\d]", ""); // normaliza
          Cliente clienteDevolucao = clienteRepo.buscarPorIdentificador(docDevolucao);
          if (clienteDevolucao == null) {
            System.out.println("❌ Cliente com o CPF/CNPJ informado não foi encontrado.");
            break;
          }
          aluguelService.devolverVeiculo(clienteDevolucao, veiculoDevolucao);
          System.out.println("✅ Devolução realizada com sucesso!");
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
