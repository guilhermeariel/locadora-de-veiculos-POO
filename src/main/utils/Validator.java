package utils;

import java.util.regex.Pattern;

public class Validator {

    private Validator() { /* classe utilitária */ }

    // Regex compiladas para performance
    private static final Pattern NOME_REGEX = Pattern.compile("[A-Za-zÀ-ÿ ]+");
    private static final Pattern PLACA_REGEX = Pattern.compile("^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$");

    public static boolean validarNome(String nome) {
        if (nome == null) return false;
        String trimmed = nome.trim();
        return !trimmed.isEmpty() && NOME_REGEX.matcher(trimmed).matches();
    }

    // ==================== CPF/CNPJ ====================
    private static boolean validarNumeros(String input, int tamanhoEsperado) {
        if (input == null) return false;
        String numeros = input.replaceAll("\\D", ""); // remove tudo que não é dígito
        return numeros.length() == tamanhoEsperado;
    }

    public static boolean validarCPF(String cpf) {
        return validarNumeros(cpf, 11);
    }

    public static boolean validarCNPJ(String cnpj) {
        return validarNumeros(cnpj, 14);
    }

    // ==================== Placa de Veículo ====================
    public static boolean validarPlaca(String placa) {
        if (placa == null) return false;
        String normalizada = placa.trim().toUpperCase();
        return PLACA_REGEX.matcher(normalizada).matches();
    }
}
