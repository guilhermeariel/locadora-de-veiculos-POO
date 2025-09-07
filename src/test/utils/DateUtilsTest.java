package utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateUtilsTest {

    @Test
    void when_DatasValidasComDiasCompletos_thenRetornaNumeroDeDias() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 1, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 9, 4, 10, 0); // exatos 3 dias

        long dias = DateUtils.calcularDiarias(inicio, fim);
        assertEquals(3, dias);
    }

    @Test
    void when_DatasValidasComHorasSobrando_thenContaComoDiaCompleto() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 1, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 9, 4, 12, 0); // 3 dias + 2 horas

        long dias = DateUtils.calcularDiarias(inicio, fim);
        assertEquals(4, dias, "Horas adicionais contam como uma diária extra");
    }

    @Test
    void when_DataInicioIgualDataFim_thenRetornaUm() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 1, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 9, 1, 10, 0);

        long dias = DateUtils.calcularDiarias(inicio, fim);
        assertEquals(1, dias, "Mesmo que datas sejam iguais, deve retornar 1 diária mínima");
    }

    @Test
    void when_DataFimAntesDoInicio_thenThrownException() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 5, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 9, 4, 10, 0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            DateUtils.calcularDiarias(inicio, fim);
        });

        assertEquals("Data de fim não pode ser antes da data de início.", e.getMessage());
    }

    @Test
    void when_DataInicioNula_thenThrownException() {
        LocalDateTime fim = LocalDateTime.of(2025, 9, 4, 10, 0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            DateUtils.calcularDiarias(null, fim);
        });

        assertEquals("Datas não podem ser nulas.", e.getMessage());
    }

    @Test
    void when_DataFimNula_thenThrownException() {
        LocalDateTime inicio = LocalDateTime.of(2025, 9, 4, 10, 0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            DateUtils.calcularDiarias(inicio, null);
        });

        assertEquals("Datas não podem ser nulas.", e.getMessage());
    }
}
