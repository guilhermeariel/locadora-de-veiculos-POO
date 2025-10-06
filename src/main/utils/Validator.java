package utils;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Validator {

    private Validator() { /* classe utilitária */ }

    // Regex compiladas para performance
    private static final Pattern NOME_REGEX = Pattern.compile("[A-Za-zÀ-ÿ ]+");
    private static final Pattern PLACA_REGEX = Pattern.compile("^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$");

    public static final Predicate<String> NOME_VALIDO = nome ->
            nome != null && !nome.trim().isEmpty() && NOME_REGEX.matcher(nome.trim()).matches();

    public static final Predicate<String> CPF_VALIDO = cpf -> {
        if (cpf == null) return false;
        String numeros = cpf.trim().replaceAll("\\D", "");
        return numeros.length() == 11;
    };

    public static final Predicate<String> CNPJ_VALIDO = cnpj -> {
        if (cnpj == null) return false;
        String numeros = cnpj.trim().replaceAll("\\D", "");
        return numeros.length() == 14;
    };

    public static final Predicate<String> PLACA_VALIDA = placa ->
            placa != null && PLACA_REGEX.matcher(placa.trim().toUpperCase()).matches();

    // ================== Validadores ==================
    public static boolean validarNome(String nome) {return NOME_VALIDO.test(nome);}

    public static boolean validarCPF(String cpf) {return CPF_VALIDO.test(cpf);}

    public static boolean validarCNPJ(String cnpj){return CNPJ_VALIDO.test(cnpj);}

    public static boolean validarPlaca(String placa) {return PLACA_VALIDA.test(placa);}
}
