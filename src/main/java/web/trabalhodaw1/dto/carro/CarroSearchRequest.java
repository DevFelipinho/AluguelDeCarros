package web.trabalhodaw1.dto.carro;

import java.math.BigDecimal;

public record CarroSearchRequest(
    Long id,
    String placa,
    String marca,
    String modelo,
    String cor,
    BigDecimal valorDiaria,
    Boolean disponivel
) {
    public boolean isEmpty() {
        return id == null &&
               (placa == null || placa.isBlank()) &&
               (marca == null || marca.isBlank()) &&
               (modelo == null || modelo.isBlank()) &&
               (cor == null || cor.isBlank()) &&
               valorDiaria == null &&
               disponivel == null;
    }
}
