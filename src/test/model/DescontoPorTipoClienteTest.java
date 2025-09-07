package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DescontoPorTipoClienteTest {

    private final DescontoPorTipoCliente desconto = new DescontoPorTipoCliente();

    @Test
    void when_PessoaFisicaComMenosDe6Dias_thenSemDesconto() {
        Cliente cliente = new PessoaFisica("Paula", "12345678901");
        double total = 600.0;
        long dias = 5;

        double resultado = desconto.aplicarDesconto(cliente, dias, total);

        assertEquals(total, resultado, 0.001);
    }

    @Test
    void when_PessoaFisicaComMaisDe5Dias_thenAplica5PorCento() {
        Cliente cliente = new PessoaFisica("Paula", "12345678901");
        double total = 600.0;
        long dias = 6;

        double resultado = desconto.aplicarDesconto(cliente, dias, total);

        assertEquals(total * 0.95, resultado, 0.001);
    }

    @Test
    void when_PessoaJuridicaComMenosDe4Dias_thenSemDesconto() {
        Cliente cliente = new PessoaJuridica("Empresa X", "12345678000100");
        double total = 600.0;
        long dias = 3;

        double resultado = desconto.aplicarDesconto(cliente, dias, total);

        assertEquals(total, resultado, 0.001);
    }

    @Test
    void when_PessoaJuridicaComMaisDe3Dias_thenAplica10PorCento() {
        Cliente cliente = new PessoaJuridica("Empresa X", "12345678000100");
        double total = 600.0;
        long dias = 4;

        double resultado = desconto.aplicarDesconto(cliente, dias, total);

        assertEquals(total * 0.90, resultado, 0.001);
    }
}
