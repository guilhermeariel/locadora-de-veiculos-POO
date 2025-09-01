package servicos;

import pagamento.JsonPix;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.TrimToLength;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class VaultService {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static JsonPix getDadosPIX() throws IOException {
        Map<String, Object> dados = mapper.readValue(new File("src/exercicios/ex5/vault/config.json"), Map.class);
        JsonPix json = mapper.convertValue(dados.get("pix"), JsonPix.class);
        return json;
    }

    public static String getPixQRCode(java.math.BigDecimal valor, Integer idPedido) throws IOException {
        JsonPix dadosPix = getDadosPIX();
        String valorString = valor.setScale(2).toPlainString();
        String nome = dadosPix.getNome();
        nome = TrimToLength.trim(nome, 25);
        String cidade = dadosPix.getCidade();
        cidade = TrimToLength.trim(cidade, 15).toUpperCase();
        String chave = dadosPix.getChave();
        chave = TrimToLength.trim(chave, 77);

        String qrString = "000201" +
                "26580014BR.GOV.BCB.PIX" +
                "01%02d%s".formatted(chave.length(), chave) +
                "52040000" +
                "5303986" +
                "54%02d%s".formatted(valorString.length(), valorString) +
                "5802BR" +
                "59%02d%s".formatted(nome.length(), nome) +
                "60%02d%s".formatted(cidade.length(), cidade) +
                "62140510RPntNEWDeS" +
                "6304906A";
        return qrString;
    }
}
