package com.banking.api.service;

import com.banking.api.model.Compte;
import com.banking.api.model.Requests;
import com.banking.api.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Couche service — contient toute la logique métier.
 * Équivalent des fonctions Python du fichier main.py.
 * Stockage en mémoire (List), comme dans la version Python.
 */
@Service
public class BankingService {

    // Stockage en mémoire — équivalent des listes Python `comptes` et `transactions`
    private final List<Compte> comptes = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    // ─── Comptes ──────────────────────────────────────────────────────────────

    /**
     * Crée un nouveau compte. Lève 400 si l'email est déjà utilisé.
     * Équivalent de creer_compte() en Python.
     */
    public Compte creerCompte(Requests.CompteCreate data) {
        boolean emailExistant = comptes.stream()
                .anyMatch(c -> c.getEmail().equals(data.getEmail()));
        if (emailExistant) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email déjà utilisé");
        }

        Compte nouveau = new Compte(
                UUID.randomUUID().toString(),
                "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                data.getNomTitulaire(),
                data.getEmail(),
                0.0,
                LocalDateTime.now().toString()
        );
        comptes.add(nouveau);
        return nouveau;
    }

    /**
     * Retourne tous les comptes.
     * Équivalent de lister_comptes() en Python.
     */
    public List<Compte> listerComptes() {
        return new ArrayList<>(comptes);
    }

    /**
     * Retourne un compte par son numéro. Lève 404 si introuvable.
     * Équivalent de consulter_compte() en Python.
     */
    public Compte consulterCompte(String numeroCompte) {
        return comptes.stream()
                .filter(c -> c.getNumeroCompte().equals(numeroCompte))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable"));
    }

    /**
     * Supprime un compte et ses transactions associées.
     * Équivalent de supprimer_compte() en Python.
     */
    public Object supprimerCompte(String numeroCompte) {
        Compte compte = consulterCompte(numeroCompte);

        List<Transaction> txnsSupprimees = transactions.stream()
                .filter(t -> numeroCompte.equals(t.getCompteSource())
                          || numeroCompte.equals(t.getCompteDestination()))
                .collect(Collectors.toList());

        comptes.remove(compte);
        transactions.removeAll(txnsSupprimees);

        return new java.util.LinkedHashMap<String, Object>() {{
            put("succes", true);
            put("compte_supprime", numeroCompte);
            put("transactions_supprimees", txnsSupprimees.size());
        }};
    }

    // ─── Transactions ─────────────────────────────────────────────────────────

    /**
     * Effectue un dépôt sur un compte.
     * Équivalent de depot() en Python.
     */
    public Transaction depot(String numeroCompte, Requests.TransactionMontant data) {
        Compte compte = consulterCompte(numeroCompte);
        compte.setSolde(compte.getSolde() + data.getMontant());

        Transaction t = new Transaction(
                UUID.randomUUID().toString(),
                "depot",
                data.getMontant(),
                LocalDateTime.now().toString(),
                numeroCompte,
                null
        );
        transactions.add(t);
        return t;
    }

    /**
     * Effectue un retrait sur un compte. Lève 400 si solde insuffisant.
     * Équivalent de retrait() en Python.
     */
    public Transaction retrait(String numeroCompte, Requests.TransactionMontant data) {
        Compte compte = consulterCompte(numeroCompte);

        if (compte.getSolde() < data.getMontant()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solde insuffisant");
        }

        compte.setSolde(compte.getSolde() - data.getMontant());

        Transaction t = new Transaction(
                UUID.randomUUID().toString(),
                "retrait",
                data.getMontant(),
                LocalDateTime.now().toString(),
                numeroCompte,
                null
        );
        transactions.add(t);
        return t;
    }

    /**
     * Effectue un virement entre deux comptes.
     * Équivalent de virement() en Python.
     */
    public Transaction virement(String numeroCompte, Requests.VirementData data) {
        if (numeroCompte.equals(data.getNumeroCompteDestination())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Impossible de virer vers le même compte");
        }

        Compte source = consulterCompte(numeroCompte);

        Compte destination = comptes.stream()
                .filter(c -> c.getNumeroCompte().equals(data.getNumeroCompteDestination()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Compte destination introuvable"));

        if (source.getSolde() < data.getMontant()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solde insuffisant");
        }

        source.setSolde(source.getSolde() - data.getMontant());
        destination.setSolde(destination.getSolde() + data.getMontant());

        Transaction t = new Transaction(
                UUID.randomUUID().toString(),
                "virement",
                data.getMontant(),
                LocalDateTime.now().toString(),
                numeroCompte,
                data.getNumeroCompteDestination()
        );
        transactions.add(t);
        return t;
    }

    /**
     * Retourne l'historique des transactions d'un compte.
     * Équivalent de historique_transactions() en Python.
     */
    public List<Transaction> historiqueTransactions(String numeroCompte) {
        consulterCompte(numeroCompte); // vérifie l'existence du compte
        return transactions.stream()
                .filter(t -> numeroCompte.equals(t.getCompteSource())
                          || numeroCompte.equals(t.getCompteDestination()))
                .collect(Collectors.toList());
    }
}
