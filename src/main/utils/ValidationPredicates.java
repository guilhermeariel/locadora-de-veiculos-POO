package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class ValidationPredicates {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");
    private static final Pattern NOME_PATTERN = Pattern.compile("[A-Za-zÀ-ú ]+");
    private static final Pattern PLACA_ANTIGA_PATTERN = Pattern.compile("[A-Z]{3}-?\\d{4}");
    private static final Pattern PLACA_MERCOSUL_PATTERN = Pattern.compile("[A-Z]{3}\\d[A-Z]\\d{2}");
    private static final Pattern DATA_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    private static final Pattern HORA_PATTERN = Pattern.compile("\\d{2}:\\d{2}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final Predicate<String> cpfValido = cpf ->
            cpf != null && CPF_PATTERN.matcher(cpf.replaceAll("\\D", "")).matches();

    public static final Predicate<String> cnpjValido = cnpj ->
            cnpj != null && CNPJ_PATTERN.matcher(cnpj.replaceAll("\\D", "")).matches();

    public static final Predicate<String> documentoValido = doc ->
            cpfValido.or(cnpjValido).test(doc);

    public static final Predicate<String> nomeValido = nome ->
            nome != null && !nome.trim().isEmpty() && NOME_PATTERN.matcher(nome).matches();

    public static final Predicate<String> placaValida = placa ->
            placa != null && (PLACA_ANTIGA_PATTERN.matcher(placa).matches() ||
                    PLACA_MERCOSUL_PATTERN.matcher(placa).matches());

    private ValidationPredicates() {
        // Classe utilitária: construtor privado para evitar instanciação
    }

    public static boolean ehDataValida(String data) {
        if (data == null || !DATA_PATTERN.matcher(data).matches()) {
            return false;
        }
        try {
            LocalDate.parse(data, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean ehHoraValida(String hora) {
        if (hora == null || !HORA_PATTERN.matcher(hora).matches()) {
            return false;
        }

        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);

        return horas >= 0 && horas < 24 && minutos >= 0 && minutos < 60;
    }

    public static boolean ehCpfValido(String cpf) {
        return cpfValido.test(cpf);
    }

    public static boolean ehCnpjValido(String cnpj) {
        return cpfValido.test(cnpj);
    }

    public static boolean ehDocumentoValido(String documento) {
        return documentoValido.test(documento);
    }

    public static boolean ehNomeValido(String nome) {
        return nomeValido.test(nome);
    }

    public static boolean ehPlacaValida(String placa) {
        return placaValida.test(placa);
    }
}
