package gg.nils.minecraftstatusapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PingTargetsDto {

    @NotNull
    @NotBlank
    private String token;
}
