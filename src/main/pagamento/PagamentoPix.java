package pagamento;

import servicos.VaultService;

import java.io.IOException;
import java.math.BigDecimal;

public class PagamentoPix implements Pagamento {
    private String qrCode;

    public void PagamentoPix(BigDecimal valor, int AluguelId) {
        try {
            this.qrCode = VaultService.getPixQRCode(valor, AluguelId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getQrCode() {
        return qrCode;
    }

    @Override
    public boolean pagar(BigDecimal valor) {
        return true;
    }
}
