package com.banking.api.model;

/**
 * Représente une transaction bancaire (équivalent de TransactionResponse Pydantic).
 */
public class Transaction {

    private String id;
    private String type;
    private double montant;
    private String date;
    private String compteSource;
    private String compteDestination; // null pour dépôt/retrait

    public Transaction() {}

    public Transaction(String id, String type, double montant, String date,
                       String compteSource, String compteDestination) {
        this.id = id;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCompteSource() { return compteSource; }
    public void setCompteSource(String compteSource) { this.compteSource = compteSource; }

    public String getCompteDestination() { return compteDestination; }
    public void setCompteDestination(String compteDestination) { this.compteDestination = compteDestination; }
}
