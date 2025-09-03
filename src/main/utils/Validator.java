package utils;

public class Validator {

    public static boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return false;

        // letras, acentos e espaços
        return nome.matches("[A-Za-zÀ-ÿ ]+");
    }

    // ==================== CPF/CNPJ ====================
    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;

        // Remove pontos e traços
        String somenteNumeros = cpf.replaceAll("[\\.\\-]", "");

        // Verifica se tem exatamente 11 dígitos
        return somenteNumeros.matches("\\d{11}");
    }

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;

        // Remove pontos, barras e traço
        String somenteNumeros = cnpj.replaceAll("[\\.\\-/]", "");

        // Verifica se tem exatamente 14 dígitos
        return somenteNumeros.matches("\\d{14}");
    }

    // ==================== Placa de Veículo ====================
    public static boolean validarPlaca(String placa) {
        if (placa == null) return false;

        // Remove possíveis espaços extras
        String normalizada = placa.trim().toUpperCase();

        // Regex que aceita tanto padrão antigo (AAA-1234) quanto Mercosul (AAA1A23)
        String regex = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";

        return normalizada.matches(regex);
    }
}
