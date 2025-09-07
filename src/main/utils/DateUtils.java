package utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateUtils {

    public static long calcularDiarias(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas.");
        }
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Data de fim não pode ser antes da data de início.");
        }

        long dias = Duration.between(inicio, fim).toDays();

        // Verifica se houve sobra de horas além dos dias completos
        if (Duration.between(inicio, fim).toHours() % 24 > 0) {
            dias++;
        }

        return dias > 0 ? dias : 1;
    }
}
