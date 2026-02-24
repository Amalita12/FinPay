package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactureDAOTest {
    @Test
    void Paid_Test(){
        assertEquals(Statut.PAYEE, FactureDAO.updateFactureStatut(1,Statut.PAYEE) );
    }
    @Test
    void InpaidTest(){
        assertEquals(Statut.NON_PAYEE, FactureDAO.updateFactureStatut(1, Statut.NON_PAYEE));
    }
    @Test
    void pertielleTest(){
        assertEquals(Statut.PARTIELLE, FactureDAO.updateFactureStatut(1,Statut.PARTIELLE));
    }

}