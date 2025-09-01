package pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoCredito implements Pagamento {
    private String numeroCartao;
    private String nomeTitular;
    private LocalDate dataValidade;
    private String cvv;

    public PagamentoCredito(String numeroCartao, String nomeTitular, LocalDate dataValidade, String cvv) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.dataValidade = dataValidade;
        this.cvv = cvv;
        if (LocalDate.now().isAfter(dataValidade)){
            throw new IllegalArgumentException("Cartão expirado");
        }
    }

    @Override
    public boolean pagar(BigDecimal valor) {
        return true;
    }
}

