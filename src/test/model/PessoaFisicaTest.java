package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PessoaFisicaTest {
    @Test
    void testInstanciacao() {
        PessoaFisica pf = new PessoaFisica("João", "123.456.789-00");
        assertEquals("João", pf.getNome());
        assertEquals("123.456.789-00", pf.getCpf());
    }

    @Test
    void testGetIdentificador() {
        PessoaFisica pf = new PessoaFisica("Maria", "987.654.321-00");
        assertEquals("987.654.321-00", pf.getIdentificador());
    }

    @Test
    void testIsPessoaFisica() {
        PessoaFisica pf = new PessoaFisica("Carlos", "111.222.333-44");
        assertTrue(pf.isPessoaFisica());
    }
}

