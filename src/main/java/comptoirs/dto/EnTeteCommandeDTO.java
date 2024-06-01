package comptoirs.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class EnTeteCommandeDTO {
    private Integer numero;
    private ClientDTO client;
    private LocalDate saisiele;
}
