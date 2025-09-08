package view;

public class Validations {
    public static boolean dataValida(String data) {
        // Verifica se a data está no formato dd/mm/aaaa
        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }

        String[] partes = data.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        // Verifica se o ano, mês e dia são válidos
        if (ano < 1900 || mes < 1 || mes > 12 || dia < 1 || dia > 31) {
            return false;
        }

        // Verifica os dias em cada mês
        if (mes == 2) { // Fevereiro
            boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
            if (dia > (bissexto ? 29 : 28)) {
                return false;
            }
        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) { // Abril, Junho, Setembro, Novembro
            if (dia > 30) {
                return false;
            }
        }
        return true;
    }
    public static boolean horaValida(String hora) {
        // Verifica se a hora está no formato hh:mm
        if (!hora.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);

        // Verifica se as horas e minutos são válidos
        return (horas >= 0 && horas < 24) && (minutos >= 0 && minutos < 60);
    }

    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;

        // Remove pontos e traços
        String somenteNumeros = cpf.replaceAll("[\\.\\-]", "");

        // Verifica se tem exatamente 11 dígitos
        return somenteNumeros.matches("\\d{11}");
    }

    public static boolean cnpjValido(String cnpj) {
        if (cnpj == null) return false;

        // Remove pontos, barras e traço
        String somenteNumeros = cnpj.replaceAll("[\\.\\-/]", "");

        // Verifica se tem exatamente 14 dígitos
        return somenteNumeros.matches("\\d{14}");
    }

    public static boolean documentoValido(String doc) {
        return cpfValido(doc) || cnpjValido(doc);
    }

    public static boolean nomeValido(String nome) {
        return nome != null && !nome.trim().isEmpty() && nome.matches("[A-Za-zÀ-ú ]+");
    }
}
