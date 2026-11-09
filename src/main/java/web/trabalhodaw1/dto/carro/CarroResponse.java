package web.trabalhodaw1.dto.carro;

import java.math.BigDecimal;

public record CarroResponse(
    Long id,
    String placa,
    String marca,
    String modelo,
    String cor,
    BigDecimal valorDiaria,
    boolean disponivel
) {
}
