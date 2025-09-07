package servicos;

import model.TipoVeiculo;
import model.Veiculo;
import repository.VeiculoRepositorio;
import utils.Mapper;
import utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepositorio veiculoRepositorio;

    public VeiculoServiceImpl(VeiculoRepositorio veiculoRepositorio) {
        this.veiculoRepositorio = veiculoRepositorio;
    }

    public void cadastrarVeiculo(String placa, String modelo, String tipoVeiculoStr) {
        if (veiculoRepositorio.buscarPorIdentificador(placa) != null) {
            throw new IllegalArgumentException("Já existe um veículo com essa placa.");
        }

        if (!Validator.validarPlaca(placa)) {
            throw new IllegalArgumentException("Placa inválida.");
        }

        if (!Validator.validarNome(modelo)) {
            throw new IllegalArgumentException("Modelo inválido.");
        }

        TipoVeiculo tipoVeiculo = Mapper.mapearTipoVeiculo(tipoVeiculoStr);
        if (tipoVeiculo == null) {
            throw new IllegalArgumentException("Tipo de veículo inválido.");
        }

        Veiculo veiculo = new Veiculo(placa, modelo, tipoVeiculo);

        veiculoRepositorio.salvar(veiculo);
    }

    public void atualizarVeiculo(String placa, String novoTipoStr, Boolean novaDisponibilidade) {
        Veiculo veiculo = veiculoRepositorio.buscarPorIdentificador(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        if (novoTipoStr != null) {
            TipoVeiculo novoTipo = Mapper.mapearTipoVeiculo(novoTipoStr);
            if (novoTipo == null) {
                throw new IllegalArgumentException("Tipo de veículo inválido.");
            }
            veiculo.setTipo(novoTipo);
        }

        if (novaDisponibilidade != null) {
            veiculo.setDisponivel(novaDisponibilidade);
        }

        veiculoRepositorio.atualizar(veiculo);
    }

    public List<Veiculo> buscarVeiculoPorModelo(String nome) {
        List<Veiculo> veiculos = veiculoRepositorio.listar().stream()
                .filter(v -> v.getModelo().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());

        if (veiculos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum veículo encontrado com o modelo: " + nome);
        }

        return veiculos;
    }

    public Veiculo buscarVeiculoPorId(String placa) {
        Veiculo veiculo = veiculoRepositorio.buscarPorIdentificador(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veiculo não encontrado.");
        }

        return veiculo;
    }

    public boolean validarDisponibilidade(String placa) {
        Veiculo veiculo = veiculoRepositorio.buscarPorIdentificador(placa);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        return veiculo.isDisponivel();
    }
}
