package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteFake extends Cliente {
    public ClienteFake(String nome) {
        super(nome);
    }

    @Override
    public String getIdentificador() {
        return "FAKE123";
    }

    @Override
    public boolean isPessoaFisica() {
        return true;
    }
}

public class ClienteTest {

    @Test
    void testGetNome() {
        Cliente cliente = new ClienteFake("João");
        assertEquals("João", cliente.getNome());
    }

    @Test
    void testSetNome() {
        Cliente cliente = new ClienteFake("João");
        cliente.setNome("Maria");
        assertEquals("Maria", cliente.getNome());
    }

    @Test
    void testGetIdentificador() {
        Cliente cliente = new ClienteFake("João");
        assertEquals("FAKE123", cliente.getIdentificador());
    }

    @Test
    void testIsPessoaFisica() {
        Cliente cliente = new ClienteFake("João");
        assertTrue(cliente.isPessoaFisica());
    }
}