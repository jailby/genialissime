
package fr.um2.projetl3.tarotandroid.clients;

import java.util.Vector;

import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;

public interface IJoueur
{

	void setNomDuJoueur(String s);
	String getNomDuJoueur();
	
	
	/*--- Méthodes "demander" ---*/
	/* Les méthodes "demander" servent à demander une action de jeu, comme jouer une carte ou annoncer un contrat */
	Contrat demanderAnnonce(Contrat contrat);
	Vector<Carte> demanderEcart();
	Carte demanderCarte();
	Carte demanderAppelAuRoi(); //? A quoi sert cette fonction, ne serait-elle pas plus à se place dans Donne.getMain().possedeRoi() ?
	// Carte demanderUneCartePourLecart(); // Obsolète, préferer la méthode Vector<Carte> demanderEcart()
	
	/*--- Méthodes "dire" ---*/
	/* Les méthodes "dire" servent à informer les joueurs des évènements de la partie */
	void direChien(Vector<Carte> chien);
	void direCarteJouee(Carte c, int j);
	void direAnnonce(Contrat c, int j);
	void direPliRemporté(Vector<Carte> pli, int joueur);
	void direMain(Vector<Carte> m);
	void direScore(Vector<Integer[]> scores);
	
	/*
	 * ------------------------------------------------------------------------------------------
	 * -------------------------------demande initier par le joueur---------------------------------------------
	 * ------------------------à mettre ailleur--------------------------
	 
	
	void recupererMain();
	// le joueur demande sa main au serveur en cas de probleme
	void recupererPliEnCours();
	// le joueur demande le pli en cours au serveur 
	void recupererPliPrecedent();
	// le joueur doit pouvoir acceder au dernier pli
	void recupererScores();
	*/

}
