package fr.um2.projetl3.tarotandroid.jeu;

import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class Annonces 
{
	
	private static Contrat[] tableauDesContrats;
	
	/*
	 *  ---------------------------------------------------------------------------------------------
	 *  ------------------------Juste la méthode pour effectuer la phase des annonces----------------
	 *  ------------------------------------faut encore faire des tests----------------------------------------
	 */
	
	/**
	 * @author JB
	 *   permet de connaitre le preneur et le type de contrat fait par le joueur
	 * 		et si le jeu est à 5 le joeur appelée est decidé
	 * 
	 */
	protected static void phaseAnnonce()
	{
		int nombreDeJoueurs = P.getNombreDeJoueurs(); 
		tableauDesContrats = new Contrat[nombreDeJoueurs];
		
		int numeroDuJoueur = D.getNumJoueurApres(D.getNumDonneur());
		System.out.print("Le donneur était " + P.getNomNumJoueur(D.getNumDonneur()) + ", ");
		System.out.println("le premier à parler est " + P.getNomNumJoueur(numeroDuJoueur));
		
		Contrat contrat = Contrat.AUCUN;
		Contrat contratMax = Contrat.AUCUN;
		
		int numDernierJoueur = D.getNumDonneur();
		int numDernierJoueurTemporaire = D.getNumDonneur();

		int joueurQuiVaPrendre = -1;
		int combienVeulentPrendre = 0;
		
		for(int i=0;i<nombreDeJoueurs;i++)
		{
			tableauDesContrats[i]=Contrat.AUCUN;
		}
		
		boolean conditionArret = true;
		while(conditionArret)
		{
			if(tableauDesContrats[numeroDuJoueur] != Contrat.PASSE ) // si le joueur n'as pas passer il peut annoncer
			{
				if(joueurQuiVaPrendre==numeroDuJoueur) // sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre
				{
					conditionArret = false;
					System.out.println("sortie d'annonce : la boucle est revenu sur le joueur qui veux prendre");
				}
				else
				{
					contrat = demanderAnnonceJoueur(numeroDuJoueur, contratMax);  // demande au joueur quel contrat il veut faire et renvoie un contrat valide
					direJoueursAnnonce(contrat, P.getJoueur(numeroDuJoueur));
					
					if (contrat.getPoids() > contratMax.getPoids())
					{
						contratMax = contrat;
					}
					System.out.println("Contrat du joueur "+P.getJoueur(numeroDuJoueur).getNomDuJoueur()+" : "+contrat.getName());
					
					tableauDesContrats[numeroDuJoueur] = contrat ; // on stocke les contrat que les joueur veulent faire
	
					if(contrat != Contrat.PASSE) // si un joeur passe pas
					{
						combienVeulentPrendre++;
						
						if(contrat.getPoids() == Contrat.GARDE_CONTRE.getPoids()) // alors c'est une garde_sans => la phase d'annonce est finit 
						{
							joueurQuiVaPrendre = numeroDuJoueur;
							conditionArret = false;
							System.out.println("sortie d'annonce : garde sans");
						}
						else 
						{
							joueurQuiVaPrendre = numeroDuJoueur;
							numDernierJoueurTemporaire = numeroDuJoueur;
						}
					}
					/*   Test
					 *	
					 *	System.out.println("numero du joueur : "+ numeroDuJoueur);
						System.out.println("numero du dernier : "+ numDernierJoueur);
						System.out.println("numero du dernier temporaire : "+ numDernierJoueurTemporaire); 
					 */
					if(numeroDuJoueur == numDernierJoueur) // si on as fait un tour d'annonce
					{
						System.out.println("4");
						// si il y a une seule prise on lance la partie
						if (combienVeulentPrendre == 0) // dans ce cas l� �a veux dire que tout le monde � passer
						{
							contrat = Contrat.AUCUN;
							conditionArret = false ;
							
							System.out.println("sortie d'annonce : tlm passe");
						}
						else if (combienVeulentPrendre == 1)
						{
							conditionArret = false;
							System.out.println("sortie d'annonce : un seul veux prendre");
						}
						else if(combienVeulentPrendre > 1) // si plusieur joueur veulent prendre on refait un tour des joueur qui voulaient prendre
						{
							combienVeulentPrendre = 1 ; // remit � u1 car si tout le monde passe apres il faut conserver celui qui avait pris en dernier
							numeroDuJoueur = numDernierJoueurTemporaire+1;
							// Pour que la boucle recommence juste apres le dernier joueur qui veux prendre
							numDernierJoueur = numDernierJoueurTemporaire;
							// Pour que la boucle s'arrete lorsque l'on retombe sur le dernier joueur à vouloir prendre
							
							/*	Test
							System.out.println("\tnumero du joueur : "+ numeroDuJoueur);
							System.out.println("\tnumero du dernier : "+ numDernierJoueur);
							System.out.println("\tnumero du dernier temporaire : "+ numDernierJoueurTemporaire);
							*/
						}
					}
				}
			}
			numeroDuJoueur = D.getNumJoueurApres(numeroDuJoueur);
		}

		D.setContratEnCours(contrat);
		D.setPreneur(joueurQuiVaPrendre); // preneur est donc à -1 si personne n’a pris
		if (joueurQuiVaPrendre != -1)
		{
			System.out.println("Contrat en cours : " + contrat+ " par " + P.getJoueur(joueurQuiVaPrendre).getNomDuJoueur()+ "(" + joueurQuiVaPrendre + ")");
		}
		
		if(nombreDeJoueurs == 5)
		{
			phaseAppelRoi();
		}
	}
	
	/**
	 * Demande à un joueur son annonce, et vérifie si elle est valide (redemande jusqu’à recevoir une valide)
	 */
	protected static Contrat demanderAnnonceJoueur(int num, Contrat contratMax)
	{
		// TODO: demanderAnnonce jusqu’à recevoir une annonce valide
		Contrat annonceProposée = Contrat.AUCUN;
		annonceProposée = P.getJoueur(num).demanderAnnonce(contratMax);
		return annonceProposée;
	}
	
	protected static void direJoueursAnnonce(Contrat c, IJoueur joueur)
	{
		for(IJoueur j: P.getJoueurs())
		{
			j.direAnnonce(c, joueur);
		}
	}
	
	/**
	 * Indique au joueur les annonces qu’il peut dire.
	 */
	public Contrat[] getAnnoncesValides()
	{
		Contrat[] annoncesValides = null;
		return annoncesValides;
	}
	
	protected static void phaseAppelRoi()
	{
		Carte Roi = P.getJoueur(D.getPreneur()).demanderRoi();
		int nombreDeJoueurs = P.getNombreDeJoueurs();
		for(int i = 0; i<nombreDeJoueurs; i++)
		{
			if(P.getJoueur(i).possedeRoi(Roi))
			{
				D.setJoueurAppele(i);
			} 
			else // si le chien n'est pas dans la main d'un joueur il est dans le chien, le preneur se retrouve donc tout seul.
			{
				D.setJoueurAppele(D.getPreneur());
			}
		}
	}
}
