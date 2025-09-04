package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VeiculoTest {
    @Test
    void testInstanciacao() {
        Veiculo v = new Veiculo("ABC-1234", "Civic", TipoVeiculo.PEQUENO);
        assertEquals("ABC-1234", v.getPlaca());
        assertEquals("Civic", v.getModelo());
        assertEquals(TipoVeiculo.PEQUENO, v.getTipo());
        assertTrue(v.isDisponivel());
    }

    @Test
    void testSetDisponivel() {
        Veiculo v = new Veiculo("XYZ-9876", "Onix", TipoVeiculo.PEQUENO);
        v.setDisponivel(false);
        assertFalse(v.isDisponivel());
        v.setDisponivel(true);
        assertTrue(v.isDisponivel());
    }

    @Test
    void testGetIdentificador() {
        Veiculo v = new Veiculo("DEF-5678", "HB20", TipoVeiculo.PEQUENO);
        assertEquals("DEF-5678", v.getIdentificador());
    }
}

