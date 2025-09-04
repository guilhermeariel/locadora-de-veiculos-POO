package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PessoaJuridicaTest {
    @Test
    void testInstanciacao() {
        PessoaJuridica pj = new PessoaJuridica("Empresa X", "12.345.678/0001-99");
        assertEquals("Empresa X", pj.getNome());
        assertEquals("12.345.678/0001-99", pj.getCnpj());
    }

    @Test
    void testGetIdentificador() {
        PessoaJuridica pj = new PessoaJuridica("Empresa Y", "98.765.432/0001-11");
        assertEquals("98.765.432/0001-11", pj.getIdentificador());
    }

    @Test
    void testIsPessoaFisica() {
        PessoaJuridica pj = new PessoaJuridica("Empresa Z", "22.333.444/0001-55");
        assertFalse(pj.isPessoaFisica());
    }
}

