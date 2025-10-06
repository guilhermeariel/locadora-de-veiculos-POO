package utils;

import controller.GerenciadorDados;
import model.*;
import repository.AluguelRepositorio;
import repository.ClienteRepositorio;
import repository.VeiculoRepositorio;
import servicos.AluguelServiceImpl;
import servicos.ClienteServiceImpl;
import servicos.VeiculoServiceImpl;

import java.util.function.Supplier;
import java.util.Random;

public class GeradorDadosDeTeste {
    private final ClienteRepositorio clienteRepositorio;
    private final ClienteServiceImpl clienteService;
    private final VeiculoRepositorio veiculoRepositorio;
    private final VeiculoServiceImpl veiculoService;
    private final AluguelRepositorio aluguelRepositorio;
    private final AluguelServiceImpl aluguelService;
    private final GerenciadorDados gerenciadorDados;

    private static final Random random = new Random();

    public GeradorDadosDeTeste() {
        clienteRepositorio = new ClienteRepositorio();
        clienteService = new ClienteServiceImpl(clienteRepositorio);
        veiculoRepositorio = new VeiculoRepositorio();
        veiculoService = new VeiculoServiceImpl(veiculoRepositorio);
        aluguelRepositorio = new AluguelRepositorio();
        aluguelService = new AluguelServiceImpl(
                aluguelRepositorio,
                veiculoRepositorio,
                clienteRepositorio
        );
        gerenciadorDados = new GerenciadorDados(
                clienteRepositorio,
                veiculoRepositorio,
                aluguelRepositorio);
    }

    private void gerarClientesPF() {
        Supplier<Cliente> clienteSupplier = () -> new PessoaFisica(
                "clienteTeste",
                "%011d".formatted((long)(random.nextDouble() * 100_000_000_000L))
        );
        for (int i = 0; i < 20; i++) {
            Cliente cliente = clienteSupplier.get();
            clienteService.cadastrarCliente(
                    cliente.getNome(),
                    cliente.getIdentificador(),
                    true);
        }
    }

    private void gerarClientesPJ(){
        Supplier<Cliente> clienteSupplier = () -> new PessoaJuridica(
                "empresaTeste",
                "%08d000100".formatted((long)(random.nextDouble() * 100_000_000L))
        );
        for(int i=0; i<20; i++){
            Cliente cliente = clienteSupplier.get();
            clienteService.cadastrarCliente(
                    cliente.getNome(),
                    cliente.getIdentificador(),
                    false);
        }
    }

    private static Supplier<String> gerarPlaca = () ->{
      char[] letras = new char[3];
      for(int i=0; i<3; i++){
          letras[i] = (char)('A'+random.nextInt(26));
      }
      int numeros = random.nextInt(10000);
      return "%s-%04d".formatted(new String(letras), numeros);
    };

    private static Supplier<TipoVeiculo> gerarTipo = () -> {
        TipoVeiculo[] tipos = TipoVeiculo.values();
        return tipos[random.nextInt(tipos.length)];
    };

    public void gerarVeiculos(){
        for (int i = 0; i < 20; i++) {
            veiculoService.cadastrarVeiculo(
                    gerarPlaca.get(),
                    gerarTipo.get(),
                    "modeloX"
            );
        }
    }

    public void gerarAluguel(){
        Supplier<Cliente> clienteSupplier = () ->
                getClienteRepositorio().getLista().get(random.nextInt(getClienteRepositorio().getLista().size()));
        Supplier<Veiculo> veiculoSupplier = () ->
                getVeiculoRepositorio().getLista().get(random.nextInt(getVeiculoRepositorio().getLista().size()));
        aluguelService.alugarVeiculo(clienteSupplier.get(), veiculoSupplier.get());
    }

    public void salvarDados(){
        gerenciadorDados.salvarDados("dados_teste");
    }

    public void carregarDados(){
        gerenciadorDados.carregarDados("dados_teste");
    }

    public ClienteRepositorio getClienteRepositorio() {
        return clienteRepositorio;
    }

    public VeiculoRepositorio getVeiculoRepositorio() {
        return veiculoRepositorio;
    }

    public AluguelRepositorio getAluguelRepositorio() {
        return aluguelRepositorio;
    }

    public static void main(String[] args) {
        GeradorDadosDeTeste gerador = new GeradorDadosDeTeste();
        gerador.gerarClientesPF();
        gerador.gerarClientesPJ();
        gerador.gerarVeiculos();

        gerador.salvarDados();
        gerador.carregarDados();
    }
}
