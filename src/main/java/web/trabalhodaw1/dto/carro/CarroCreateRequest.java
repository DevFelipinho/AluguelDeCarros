package web.trabalhodaw1.dto.carro;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CarroCreateRequest(
    @NotBlank(message = "A placa é obrigatória")
    @Pattern(regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9][0-9]{2}$", message = "A placa deve seguir o padrão tradicional (ex: ABC-1234) ou Mercosul (ex: ABC1D23)")
    String placa,

    @NotBlank(message = "A marca é obrigatória")
    String marca,

    @NotBlank(message = "O modelo é obrigatório")
    String modelo,

    @NotBlank(message = "A cor é obrigatória")
    String cor,

    @NotNull(message = "O valor da diária é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da diária deve ser maior que zero")
    BigDecimal valorDiaria,

    Boolean disponivel
) {
    public CarroCreateRequest {
        if (disponivel == null) {
            disponivel = true;
        }
        if (placa != null) {
            placa = placa.toUpperCase().trim();
        }
    }
}
