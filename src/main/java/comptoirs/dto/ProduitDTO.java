package comptoirs.dto;
import lombok.Data;
@Data
public class ProduitDTO {
    private Integer reference;
    // nom du produit pour la ligne
    private String nom;
    private Integer prixUnitaire;
}
