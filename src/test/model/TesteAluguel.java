package model;

public class TesteAluguel {
    public static void main(String[] args) {
        Veiculo veiculo1 = new Veiculo("ABC1234", "Toyota", "Corolla", 2020, TipoVeiculo.AUTOMATICO);
        Veiculo veiculo2 = new Veiculo("XYZ5678", "Honda", "Civic", 2019, TipoVeiculo.MANUAL);
        Veiculo veiculo3 = new Veiculo("ELE1234", "Tesla", "Model 3", 2021, TipoVeiculo.ELETRICO);

        Aluguel aluguel1 = new Aluguel(veiculo1, 5);
        Aluguel aluguel2 = new Aluguel(veiculo2, 3);
        Aluguel aluguel3 = new Aluguel(veiculo3, 7);

        System.out.println("Detalhes do Aluguel 1:");
        System.out.println(aluguel1);
        System.out.println("Valor Total: R$ " + aluguel1.calcularValorTotal());
        System.out.println();

        System.out.println("Detalhes do Aluguel 2:");
        System.out.println(aluguel2);
        System.out.println("Valor Total: R$ " + aluguel2.calcularValorTotal());
        System.out.println();

        System.out.println("Detalhes do Aluguel 3:");
        System.out.println(aluguel3);
        System.out.println("Valor Total: R$ " + aluguel3.calcularValorTotal());
    }
}
