package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AluguelTest {

    @Test
    void when_CalcularValorAluguelNaoFinalizado_thenThrowsException() {
        Cliente cliente = new PessoaFisica("Paula", "12345678901");
        Veiculo veiculo = new Veiculo("ABC1234", "Gol", TipoVeiculo.PEQUENO);
        Aluguel aluguel = new Aluguel(cliente, veiculo, LocalDateTime.now());

        assertThrows(IllegalStateException.class, aluguel::calcularValor);
    }

    @Test
    void when_CalcularValorAluguelFinalizadoPessoaFisicaComDesconto_thenCalculaValorCorreto() {
        Cliente cliente = new PessoaFisica("Paula", "12345678901");
        Veiculo veiculo = new Veiculo("ABC1234", "Gol", TipoVeiculo.PEQUENO);

        LocalDateTime inicio = LocalDateTime.now().minusDays(6); // 6 dias
        LocalDateTime fim = LocalDateTime.now();

        Aluguel aluguel = new Aluguel(cliente, veiculo, inicio);
        aluguel.setDataFim(fim);

        double valorEsperado = 6 * TipoVeiculo.PEQUENO.getValorDiaria() * 0.95; // 5% de desconto
        assertEquals(valorEsperado, aluguel.calcularValor(), 0.001);
    }

    @Test
    void when_CalcularValorAluguelFinalizadoPessoaJuridicaComDesconto_thenCalculaValorCorreto() {
        Cliente cliente = new PessoaJuridica("Empresa X", "12345678000199");
        Veiculo veiculo = new Veiculo("XYZ9876", "SUV", TipoVeiculo.SUV);

        LocalDateTime inicio = LocalDateTime.now().minusDays(4); // 4 dias
        LocalDateTime fim = LocalDateTime.now();

        Aluguel aluguel = new Aluguel(cliente, veiculo, inicio);
        aluguel.setDataFim(fim);

        double valorEsperado = 4 * TipoVeiculo.SUV.getValorDiaria() * 0.90; // 10% de desconto
        assertEquals(valorEsperado, aluguel.calcularValor(), 0.001);
    }

    @Test
    void when_CalcularValorAluguelFinalizadoPessoaFisicaSemDesconto_thenCalculaValorCorreto() {
        Cliente cliente = new PessoaFisica("Paula", "12345678901");
        Veiculo veiculo = new Veiculo("DEF5678", "Mobi", TipoVeiculo.PEQUENO);

        LocalDateTime inicio = LocalDateTime.now().minusDays(2); // 2 dias
        LocalDateTime fim = LocalDateTime.now();

        Aluguel aluguel = new Aluguel(cliente, veiculo, inicio);
        aluguel.setDataFim(fim);

        double valorEsperado = 2 * TipoVeiculo.PEQUENO.getValorDiaria(); // sem desconto
        assertEquals(valorEsperado, aluguel.calcularValor(), 0.001);
    }
}
