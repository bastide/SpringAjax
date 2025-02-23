package comptoirs.rest;

import comptoirs.dto.CommandeDTO;
import comptoirs.dto.EnTeteCommandeDTO;
import comptoirs.dto.LigneDTO;
import comptoirs.entity.Commande;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import comptoirs.service.CommandeService;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@RestController // Cette classe est un contrôleur REST
@RequestMapping(path = "/api/services/commandes") // chemin d'accès
public class CommandeController {
	private final CommandeService commandeService;
	private final ModelMapper mapper;
	// @Autowired
	public CommandeController(CommandeService commandeService, ModelMapper mapper) {
		this.commandeService = commandeService;
		this.mapper = mapper;
	}

	@PostMapping("ajouterPour/{clientCode}")
	public  ResponseEntity<CommandeDTO> ajouter(@PathVariable @NonNull String clientCode) {
        log.info("Contrôleur : ajouter commande pour {}", clientCode);
		Commande commande = commandeService.creerCommande(clientCode);
        var body = mapper.map(commande, CommandeDTO.class);
        return ResponseEntity.ok(body);
	}

	@PostMapping("expedier/{commandeNum}")
	public ResponseEntity<EnTeteCommandeDTO> expedier(@PathVariable Integer commandeNum) {
        log.info("Contrôleur : expédier la commande {}", commandeNum);
        var body = mapper.map(commandeService.enregistreExpedition(commandeNum), EnTeteCommandeDTO.class);
		return ResponseEntity.ok(body);
	}

	@PostMapping("ajouterLigne")
	public ResponseEntity<LigneDTO> ajouterLigne(@RequestParam int commandeNum, @RequestParam int produitRef, @RequestParam int quantite) {
        log.info("Contrôleur : ajouterLigne {} {} {}", commandeNum, produitRef, quantite);
		var ligne = commandeService.ajouterLigne(commandeNum, produitRef, quantite);
        var body = mapper.map(ligne, LigneDTO.class);
        return ResponseEntity.ok(body);
	}

    @DeleteMapping("supprimerLigne/{idLigne}")
    public ResponseEntity<Void>  supprimerLigne(@PathVariable Integer idLigne) {
        log.info("Contrôleur : supprimerLigne {}", idLigne);
        commandeService.supprimerLigne(idLigne);
        // Renvoie : 204 - No Content
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{commandeNum}")
    public ResponseEntity<CommandeDTO> getCommande(@PathVariable Integer commandeNum) {
        log.info("Contrôleur : getCommande {}", commandeNum);
        var body = mapper.map(commandeService.getCommande(commandeNum), CommandeDTO.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping("enCoursPour/{clientCode}")
    public ResponseEntity<List<EnTeteCommandeDTO>> getCommandeEnCoursPour(@PathVariable @NonNull String clientCode) {
        log.info("Contrôleur : getCommandeEnCoursPour {}", clientCode);
        List<Commande> commandes = commandeService.getCommandeEnCoursPour(clientCode);

        List<EnTeteCommandeDTO> result = new ArrayList<>();
        for (Commande commande : commandes) {
            result.add(mapper.map(commande, EnTeteCommandeDTO.class));
        }
        // Ca peut s'écrire en une ligne avec une expression lambda
        // List<EnTeteCommandeDTO> result = commandes.stream().map(commande -> mapper.map(commande, EnTeteCommandeDTO.class)).collect(Collectors.toList());

        return  ResponseEntity.ok(result);
    }
}
