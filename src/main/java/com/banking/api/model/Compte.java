package com.banking.api.model;

/**
 * Représente un compte bancaire (équivalent du dict Python et de CompteResponse Pydantic).
 */
public class Compte {

    private String id;
    private String numeroCompte;
    private String nomTitulaire;
    private String email;
    private double solde;
    private String dateCreation;

    public Compte() {}

    public Compte(String id, String numeroCompte, String nomTitulaire, String email,
                  double solde, String dateCreation) {
        this.id = id;
        this.numeroCompte = numeroCompte;
        this.nomTitulaire = nomTitulaire;
        this.email = email;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public String getNomTitulaire() { return nomTitulaire; }
    public void setNomTitulaire(String nomTitulaire) { this.nomTitulaire = nomTitulaire; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }
}
