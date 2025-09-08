package model;

import java.time.LocalDateTime;

public class TesteAluguel {
    public static void main(String[] args) {
        // Criar veículos válidos
        Veiculo veiculo1 = new Veiculo("ABC1234", TipoVeiculo.SEDAN, "Toyota Corolla", true);
        Veiculo veiculo2 = new Veiculo("XYZ5678", TipoVeiculo.HATCH, "Fiat Argo", true);
        Veiculo veiculo3 = new Veiculo("ELE1234", TipoVeiculo.SUV, "Tesla Model 3", true);

        // Criar clientes de teste
        Cliente cliente1 = new PessoaFisica("João", "12345678901");
        Cliente cliente2 = new PessoaFisica("Maria", "98765432100");
        Cliente cliente3 = new PessoaFisica("Carlos", "11122233344");

        // Criar aluguéis
        Aluguel aluguel1 = new Aluguel(cliente1, veiculo1, LocalDateTime.now(), LocalDateTime.now().plusDays(12));
        Aluguel aluguel2 = new Aluguel(cliente2, veiculo2, LocalDateTime.now(), LocalDateTime.now().plusDays(3));
        Aluguel aluguel3 = new Aluguel(cliente3, veiculo3, LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        System.out.println("Detalhes do Aluguel 1:");
        System.out.println(aluguel1);
        System.out.println("Valor Total: R$ " + aluguel1.calcularValor());
        System.out.println();

        System.out.println("Detalhes do Aluguel 2:");
        System.out.println(aluguel2);
        System.out.println("Valor Total: R$ " + aluguel2.calcularValor());
        System.out.println();

        System.out.println("Detalhes do Aluguel 3:");
        System.out.println(aluguel3);
        System.out.println("Valor Total: R$ " + aluguel3.calcularValor());
    }
}
