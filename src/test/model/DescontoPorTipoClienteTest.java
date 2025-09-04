package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


    class ClienteFakePF extends Cliente {
        public ClienteFakePF(String nome) { super(nome); }
        @Override public String getIdentificador() { return "PF"; }
        @Override public boolean isPessoaFisica() { return true; }
    }

    class ClienteFakePJ extends Cliente {
        public ClienteFakePJ(String nome) { super(nome); }
        @Override public String getIdentificador() { return "PJ"; }
        @Override public boolean isPessoaFisica() { return false; }
    }

    public class DescontoPorTipoClienteTest {

        @Test
        void testDescontoPessoaFisicaMaisDe5Dias() {
            DescontoPorTipoCliente desconto = new DescontoPorTipoCliente();
            Cliente cliente = new ClienteFakePF("João");
            double total = 100.0;
            double resultado = desconto.aplicarDesconto(cliente, 6, total);
            assertEquals(95.0, resultado, 0.001);
        }

        @Test
        void testDescontoPessoaFisicaMenosDe5Dias() {
            DescontoPorTipoCliente desconto = new DescontoPorTipoCliente();
            Cliente cliente = new ClienteFakePF("João");
            double total = 100.0;
            double resultado = desconto.aplicarDesconto(cliente, 5, total);
            assertEquals(100.0, resultado, 0.001);
        }

        @Test
        void testDescontoPessoaJuridicaMaisDe3Dias() {
            DescontoPorTipoCliente desconto = new DescontoPorTipoCliente();
            Cliente cliente = new ClienteFakePJ("Empresa");
            double total = 200.0;
            double resultado = desconto.aplicarDesconto(cliente, 4, total);
            assertEquals(180.0, resultado, 0.001);
        }

        @Test
        void testDescontoPessoaJuridicaMenosDe3Dias() {
            DescontoPorTipoCliente desconto = new DescontoPorTipoCliente();
            Cliente cliente = new ClienteFakePJ("Empresa");
            double total = 200.0;
            double resultado = desconto.aplicarDesconto(cliente, 3, total);
            assertEquals(200.0, resultado, 0.001);
        }
    }
