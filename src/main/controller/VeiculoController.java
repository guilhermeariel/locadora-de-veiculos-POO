package controller;

import model.Veiculo;
import servicos.VeiculoServiceImpl;

import java.util.List;

public class VeiculoController {

    private final VeiculoServiceImpl veiculoService;

    public VeiculoController(VeiculoServiceImpl veiculoService) {
        this.veiculoService = veiculoService;
    }

    public void cadastrarVeiculo(String placa, String modelo, String tipoVeiculoStr) {
        try {
            veiculoService.cadastrarVeiculo(placa, modelo, tipoVeiculoStr);
            System.out.println("Veículo cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar veículo: " + e.getMessage());
        }
    }

    public void atualizarVeiculo(String placa, String novoTipo, Boolean novaDisponibilidade) {
        try {
            veiculoService.atualizarVeiculo(placa, novoTipo, novaDisponibilidade);
            System.out.println("Veículo atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    public void buscarVeiculoPorModelo(String modelo) {
        try {
            List<Veiculo> veiculos = veiculoService.buscarVeiculoPorModelo(modelo);
            System.out.println("Veículos encontrados:");
            veiculos.forEach(v -> System.out.println(v.getPlaca() + " - " + v.getModelo()));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar veículo: " + e.getMessage());
        }
    }

    public void validarDisponibilidade(String placa) {
        try {
            boolean disponivel = veiculoService.validarDisponibilidade(placa);
            System.out.println("Veículo " + placa + " está " + (disponivel ? "disponível" : "indisponível"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao validar disponibilidade: " + e.getMessage());
        }
    }
}
