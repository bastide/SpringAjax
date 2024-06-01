package comptoirs.dto;
import lombok.Data;
@Data
public class LigneDTO {
    // nom du produit pour la ligne
    private ProduitDTO produit;
    private Integer quantite;
}
