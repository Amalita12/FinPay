CREATE DATABASE finpay_db;
USE finpay_db;

CREATE TABLE clients (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE prestataires (
    id_prestataire INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150) NOT NULL
);

CREATE TABLE factures (
    id_facture INT AUTO_INCREMENT PRIMARY KEY,
    id_client INT NOT NULL,
    id_prestataire INT NOT NULL,
    montant_total DECIMAL(10,2) NOT NULL,
    statut ENUM('NON_PAYEE', 'PARTIELLE', 'PAYEE') DEFAULT 'NON_PAYEE',
    date_facture DATE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_client) REFERENCES clients(id_client),
    FOREIGN KEY (id_prestataire) REFERENCES prestataires(id_prestataire)
);

CREATE TABLE paiements (
    id_paiement INT AUTO_INCREMENT PRIMARY KEY,
    id_facture INT NOT NULL,
    montant_paye double  NOT NULL,
    commission_finpay double NOT NULL,
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_facture) REFERENCES factures(id_facture)
);



CREATE TABLE historique_transactions (
    id_historique INT AUTO_INCREMENT PRIMARY KEY,
    type_operation VARCHAR(50),
    date_operation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO clients (nom) VALUES
('Entreprise Alpha'),
('Société Beta'),
('Client Gamma'),
('Startup Delta'),
('Groupe Epsilon'),
('Holding Zeta');


INSERT INTO prestataires (nom) VALUES
('Prestataire Orange'),
('Tech Solutions'),
('Consulting Pro'),
('Services Cloud'),
('Fournisseur Global');


INSERT INTO factures (id_client, id_prestataire, montant_total, statut, date_facture) VALUES
(1, 1, 1500.00, 'NON_PAYEE', '2025-01-05'),
(1, 2, 2800.00, 'PARTIELLE', '2025-01-08'),
(2, 2, 3200.50, 'PAYEE', '2025-01-10'),
(2, 3, 950.00, 'NON_PAYEE', '2025-01-14'),
(3, 3, 800.00, 'PAYEE', '2025-01-12'),
(3, 4, 4500.00, 'PARTIELLE', '2025-01-18'),
(4, 1, 1200.00, 'NON_PAYEE', '2025-01-15'),
(4, 5, 6000.00, 'PAYEE', '2025-01-20'),
(5, 4, 2100.00, 'PARTIELLE', '2025-01-22'),
(5, 2, 1750.00, 'NON_PAYEE', '2025-01-25'),
(6, 5, 9800.00, 'PAYEE', '2025-01-28');

INSERT INTO paiements (id_facture, montant_paye, commission_finpay) VALUES
(2, 1000.00, 50.00),
(3, 3200.50, 160.03),
(5, 800.00, 40.00),
(6, 2000.00, 100.00),
(8, 6000.00, 300.00),
(9, 1100.00, 55.00),
(11, 9800.00, 490.00);


INSERT INTO historique_transactions (type_operation) VALUES
('CREATION_FACTURE'),
('CREATION_FACTURE'),
('PAIEMENT_PARTIEL'),
('PAIEMENT_COMPLET'),
('CREATION_FACTURE'),
('PAIEMENT_PARTIEL'),
('CONSULTATION_FACTURE'),
('PAIEMENT_COMPLET');