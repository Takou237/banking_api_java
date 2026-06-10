package com.banking.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * DTOs d'entrée — équivalents des classes Pydantic BaseModel en Python.
 */
public class Requests {

    /**
     * Équivalent de CompteCreate (Python).
     */
    public static class CompteCreate {
        @NotBlank(message = "Le nom du titulaire est obligatoire")
        private String nomTitulaire;

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        private String email;

        public String getNomTitulaire() { return nomTitulaire; }
        public void setNomTitulaire(String nomTitulaire) { this.nomTitulaire = nomTitulaire; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    /**
     * Équivalent de TransactionMontant (Python).
     */
    public static class TransactionMontant {
        @Positive(message = "Le montant doit être positif")
        private double montant;

        public double getMontant() { return montant; }
        public void setMontant(double montant) { this.montant = montant; }
    }

    /**
     * Équivalent de VirementData (Python).
     */
    public static class VirementData {
        @NotBlank(message = "Le numéro de compte destination est obligatoire")
        private String numeroCompteDestination;

        @Positive(message = "Le montant doit être positif")
        private double montant;

        public String getNumeroCompteDestination() { return numeroCompteDestination; }
        public void setNumeroCompteDestination(String numeroCompteDestination) {
            this.numeroCompteDestination = numeroCompteDestination;
        }

        public double getMontant() { return montant; }
        public void setMontant(double montant) { this.montant = montant; }
    }
}
