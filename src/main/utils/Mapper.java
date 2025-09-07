package utils;

import model.TipoVeiculo;

public class Mapper {

    public static TipoVeiculo mapearTipoVeiculo(String tipo) {
        if (tipo == null) return null;

        return switch (tipo.trim().toUpperCase()) {
            case "PEQUENO" -> TipoVeiculo.PEQUENO;
            case "MEDIO" -> TipoVeiculo.MEDIO;
            case "SUV" -> TipoVeiculo.SUV;
            default -> null;
        };
    }
}
