package it.epicode.GestioneDispositiviAziendali.model;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DispositivoRequest {

    @NotNull(message = "tipo obbligatorio")
    @NotEmpty(message = "tipo obbligatorio")
    private String tipo;

    private StatoDispositivo statoDispositivo;

    private Integer idDipendente;
}
