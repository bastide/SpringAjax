package comptoirs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import comptoirs.entity.Commande;
import comptoirs.entity.Ligne;
import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called LigneRepository

public interface LigneRepository extends JpaRepository<Ligne, Integer> {
    List<Ligne> findByQuantite(Integer quantite);
    List<Ligne> findByCommande(Commande commande);
    List<Ligne> findByProduitReference(Integer reference);
    List<Ligne> findByCommandeNumero(Integer numero);
    Optional<Ligne> findByCommandeNumeroAndProduitReference(Integer numero, Integer reference);

    // @Query("SELECT l FROM Ligne l WHERE l.commande.numero = ?1 AND l.produit.reference = ?2")
    // public List<Ligne> produitDansCommande(Integer numero, Integer reference);

}
