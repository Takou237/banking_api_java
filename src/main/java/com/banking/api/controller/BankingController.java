package com.banking.api.controller;

import com.banking.api.model.Compte;
import com.banking.api.model.Requests;
import com.banking.api.model.Transaction;
import com.banking.api.service.BankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST — équivalent des endpoints @app.get / @app.post / @app.delete de FastAPI.
 *
 * Correspondance des routes :
 *   POST   /comptes                              → creer_compte()
 *   GET    /comptes                              → lister_comptes()
 *   GET    /comptes/{numeroCompte}               → consulter_compte()
 *   DELETE /comptes/{numeroCompte}               → supprimer_compte()
 *   POST   /comptes/{numeroCompte}/depot         → depot()
 *   POST   /comptes/{numeroCompte}/retrait       → retrait()
 *   POST   /comptes/{numeroCompte}/virement      → virement()
 *   GET    /comptes/{numeroCompte}/transactions  → historique_transactions()
 */
@RestController
@RequestMapping("/comptes")
@Tag(name = "Comptes Bancaires", description = "Gestion des comptes, dépôts, retraits et virements")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // ─── Comptes ──────────────────────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un compte", description = "Crée un nouveau compte bancaire")
    public Compte creerCompte(@Valid @RequestBody Requests.CompteCreate data) {
        return bankingService.creerCompte(data);
    }

    @GetMapping
    @Operation(summary = "Lister les comptes", description = "Retourne tous les comptes bancaires")
    public List<Compte> listerComptes() {
        return bankingService.listerComptes();
    }

    @GetMapping("/{numeroCompte}")
    @Operation(summary = "Consulter un compte", description = "Retourne un compte par son numéro")
    public Compte consulterCompte(@PathVariable String numeroCompte) {
        return bankingService.consulterCompte(numeroCompte);
    }

    @DeleteMapping("/{numeroCompte}")
    @Operation(summary = "Supprimer un compte", description = "Supprime un compte et ses transactions")
    public Object supprimerCompte(@PathVariable String numeroCompte) {
        return bankingService.supprimerCompte(numeroCompte);
    }

    // ─── Transactions ─────────────────────────────────────────────────────────

    @PostMapping("/{numeroCompte}/depot")
    @Operation(summary = "Dépôt", description = "Effectue un dépôt sur un compte")
    public Transaction depot(@PathVariable String numeroCompte,
                             @Valid @RequestBody Requests.TransactionMontant data) {
        return bankingService.depot(numeroCompte, data);
    }

    @PostMapping("/{numeroCompte}/retrait")
    @Operation(summary = "Retrait", description = "Effectue un retrait sur un compte")
    public Transaction retrait(@PathVariable String numeroCompte,
                               @Valid @RequestBody Requests.TransactionMontant data) {
        return bankingService.retrait(numeroCompte, data);
    }

    @PostMapping("/{numeroCompte}/virement")
    @Operation(summary = "Virement", description = "Effectue un virement vers un autre compte")
    public Transaction virement(@PathVariable String numeroCompte,
                                @Valid @RequestBody Requests.VirementData data) {
        return bankingService.virement(numeroCompte, data);
    }

    @GetMapping("/{numeroCompte}/transactions")
    @Operation(summary = "Historique des transactions", description = "Retourne les transactions d'un compte")
    public List<Transaction> historiqueTransactions(@PathVariable String numeroCompte) {
        return bankingService.historiqueTransactions(numeroCompte);
    }
}
