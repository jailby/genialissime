package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;
import static fr.um2.projetl3.tarotandroid.jeu.Context.*;

public class Scores
{
	
	
	private Vector<Integer[]> scores;

	/*
	 * ----------------------------------------------------------------------------------
	 * --------------------------------CONSTRUCTEUR--------------------------------------
	 * ---------------------------------------------------------------------------------- 
	 */
	
	public Scores()
	{
		int J = P.getNombreDeJoueurs();
		scores = new Vector<Integer[]>();
		Integer[] derniereLigne = new Integer[J];
		for (int i = 0; i < J; i++)
		{
			derniereLigne[i] = 0;
		}
		scores.add(derniereLigne);

	}
	
	
	/*
	 * ----------------------------------------------------------------------------------
	 * -------------------------METHODES-------------------------------------------------
	 * ---------------------------------------------------------------------------------- 
	 */
	
	/**
	 * a appeler pour calculer le score à la fin d'une donne
	 * scores est initialise dans inilitationPartie()
	 */
	public void phaseScore()
	{	
		int Gain =  calculGain(calculDesPointsDansLeVecteurAttaque(), calculNombreDeBoutsDansVecteurAttaque());

		calculDerniereLigneScore(P.donne().getContratEnCours(), Gain, joueurReussi(Gain), P.donne().getPreneur().getID());
	}
	

	/**
	 * @author niavlys
	 * @return Retourne le nombre de donnes effectuées (taille du vecteur scores), utilisé dans P.partieFinie()
	 */
	public int nbDonnes()
	{
		return scores.size();
	}
	
	public int meilleurScore()
	{
		int meilleurScore = scores.lastElement()[0];
		
		for(int i=1; i<P.getNombreDeJoueurs(); i++)
		{
			if(scores.lastElement()[i] > meilleurScore)
			{
				meilleurScore = scores.lastElement()[i];
			}
		}
		
		return meilleurScore;
		
	}

	/*
	 * cette fonction actualise le tableau de score en calculant la derniere
	 * ligne les variables en paramettre sont le type du contrat, le gain(de
	 * combien le jouer la remporte ou perdu, valeur toujour positive) si le
	 * joueur qui a fait le contrat l'a remporte, et finalament quel est le
	 * jouer qui a fait le contrat 
	 *
	 */
	public void calculDerniereLigneScore(Contrat typeDuContrat, int Gain,
			boolean joueurReussie, int joueurContrat)
	{
		int J = P.getNombreDeJoueurs();
		int valeurScore = calculScore(typeDuContrat, Gain);
		Integer[] dernierResultat = new Integer[J];
		Integer[] derniereLigne = new Integer[J];
		Integer[] nouvelleLigne = new Integer[J];
		derniereLigne = scores.lastElement();
		dernierResultat = ScoreLigne(valeurScore, joueurReussie, joueurContrat);
		
		for (int i = 0; i < J; i++)
		{
			nouvelleLigne[i] = derniereLigne[i] + dernierResultat[i];
		}
		assert (sommePointsNul(nouvelleLigne));
		scores.add(nouvelleLigne);
	}

	// pour lassertion : verifie que la somme des points d'une ligne soit toujours nulle
	public boolean sommePointsNul(Integer[] ligneScore)
	{
		int s = 0;
		for (int i = 0; i < P.getNombreDeJoueurs(); i++)
		{
			s = s + ligneScore[i];
		}
		if (s == 0)
			return true;
		else
			return false;
	}

	// affiche chaque ligne de score de la partie
	public void affiche()
	{
		System.out.println("Scores : ");
		int J = P.getNombreDeJoueurs();
		int I = scores.size();
		/*
		 * pour afficher les noms de joueurs en debut de tableau // ? pourqoui l'avoir commenter c'etait bien non ?
		 * 
		 * for(int i = 0; i<J ; i++) {
		 * 
		 * System.out.print(getNomJoueur(i)); } /*
		 */
		for (int i = 0; i < I; i++)
		{
			for (int j = 0; j < J; j++)
			{
				System.out.print(scores.get(i)[j]);
				System.out.print("\t");
			}
			System.out.println();
		}

	}

	// calcule une ligne de score en fonction des points que chaque joueur
	// gangen ou pert et du contrat et du jouer qui a prit le contrat
	public Integer[] ScoreLigne(int valeurScore, boolean joueurReussie, int joueurContrat)
	{
		Integer[] lscore = new Integer[P.getNombreDeJoueurs()];
		if (joueurReussie)
		{
			for (int i = 0; i < P.getNombreDeJoueurs(); i++)
			{
				if (i == joueurContrat)
				{
					lscore[i] = valeurScore * (P.getNombreDeJoueurs() - 1);
				} 
				else
				{
					lscore[i] = -valeurScore;
				}
			}
		} 
		else
		{
			for (int i = 0; i < P.getNombreDeJoueurs(); i++)
			{
				if (i == joueurContrat)
				{
					lscore[i] = -(valeurScore) * (P.getNombreDeJoueurs() - 1);
				} 
				else
				{
					lscore[i] = valeurScore;
				}
			}
		}
		return lscore;
	}

	// calcule le nombre de points remporte a la fin d'un tour
	public int calculePoints(Vector<Carte> cartesRemportees)
	{
		int demipoints = 0;
		for (int i = 0; i < cartesRemportees.size(); i++)
		{
			Carte c = cartesRemportees.get(i);
			demipoints = demipoints + c.valeur();
		}
		demipoints = demipoints / 2; // ! perd un demi-point si nombre impair de
										// demi-points

		return demipoints;
	}

	// calcule de combien le tour a ete remporte en fonction du nombre de points
	// fait par le prenneur de contrat et le nombre de bouts qu'il possedait
	// le gain peut etre possitif, ou negatif si le joueur ne la pas remporte
	public int calculGain(int Points, int nbrdebouts)
	{
		switch (nbrdebouts)
		{
		case 0: // aucun bout
			return Points - 56;
		case 1: // 1 bout
			return Points - 51;
		case 2: //
			return Points - 41;
		case 3: //
			return Points - 36;
		default:
			System.out.println("nombre de bouts incorrect");
			return 0;
		}

	}

	// calcule la valeur du score en fonction du type de contrat et de combien
	// la partie a ete remporte, arondi a la dixaine la plus proche.
	public int calculScore(Contrat typeDuContrat, int Gain)
	{
		int resultat = 0;
		if(Gain< 0)
			resultat += -Gain;
		else 
			resultat += Gain;

			resultat +=typeDuContrat.getValeurContrat();
			resultat *= typeDuContrat.getFacteur();

		if(resultat % 10 < 5)
		{
			resultat = resultat - (resultat % 10);
		}
		else 
		{
			resultat = resultat - (resultat % 10) + 10;
		}
		return resultat;
	}
	
	public boolean joueurReussi(int Gain)
	{
		if ( Gain < 0 ) return false; 
		else return true;
	}
	
	/*
	 * author Heykel
	 * implementation de calcul points dans vecteur Attaque/Defense
	 * puis calcul du nombre de bouts (inutile pour le moment au Vecteur Defense mais pourrait servir pour verifier qu'il
	 * n'yait pas plus de 3 bouts ou moins
	 */
	
	public int calculDesPointsDansLeVecteurAttaque() //
	{
		int sommeDesValeursDesCartes = 0;
		
		for(Carte c : P.donne().plisAttaque)
		{
			sommeDesValeursDesCartes += c.valeur();
		}
		
		return sommeDesValeursDesCartes;
	}
	
	public int calculNombreDeBoutsDansVecteurAttaque()
	{
		int nombreDeBoutsDansVecteurAttaque = 0;
		
		for(Carte c : P.donne().plisAttaque)
		{
			if(c.isBout())
			{
				nombreDeBoutsDansVecteurAttaque ++;
			}
		}
		
		return nombreDeBoutsDansVecteurAttaque;
	}
	
	public int calculDesPointsDansLeVecteurDefense() //
	{
		int sommeDesValeursDesCartes = 0;
		
		for(Carte c : P.donne().plisDefense)
		{
			sommeDesValeursDesCartes += c.valeur();
		}
		
		return sommeDesValeursDesCartes;
	}
	
	public int calculNombreDeBoutsDansVecteurDefense()
	{
		int nombreDeBoutsDansVecteurDefense = 0;
		
		for(Carte c : P.donne().plisDefense)
		{
			if(c.isBout())
			{
				nombreDeBoutsDansVecteurDefense ++;
			}	
		}
		
		return nombreDeBoutsDansVecteurDefense;
	}
	
	/*
	 * author JB
	 * calcule une ligne du vecteur score
	 */
	public void calculeLigneScore()
	{
		int pointsDansLeVecteurAttaque = 0; 
		int nombreDeBoutDuPreneur = 0; 
		assert(Constantes.TOTAL_DES_POINTS_DANS_LE_JEU == calculDesPointsDansLeVecteurAttaque() + calculDesPointsDansLeVecteurDefense());
		//assertion pour bien verifier que le nombre total des vecteurs attaque/defense verifie la constante TOTAL_DES_POINTS_DANS_LE_JEU
		assert(Constantes.NOMBRE_DE_BOUTS == calculNombreDeBoutsDansVecteurAttaque() + calculNombreDeBoutsDansVecteurDefense());
		int gain = calculGainPartie(P.donne().getContratEnCours(), pointsDansLeVecteurAttaque, nombreDeBoutDuPreneur);
		int nombreDeJoueur = P.getNombreDeJoueurs();
		
		
		for(int i=0;i<nombreDeJoueur;i++)
		{
			if((nombreDeJoueur == 3) || (nombreDeJoueur == 3))
			{
				if(P.getJoueur(i) == P.donne().getPreneur())
				{
					
				}
				else
				{
					
				}
			}
			else
			{
				if(P.getJoueur(i) == P.donne().getPreneur())
				{
					
				}
				else if (P.getJoueur(i) == P.donne().getPreneur())
				{
					
				}
				else
				{
					
				}
			}
		}
	}
	/*
	 * @author JB
	 * 
	 * calcule le gain (qu'il soit negatif ou positif) de la donne
	 * il faut ensuite repartir equitablement entre le preneur et les defenseur et eventuellement l'attaquant
	 */
	public int calculGainPartie(Contrat typeDuContrat, int pointsDansLeVecteurAttaque, int nombreDeBoutDuPreneur)
	{
		int gain = calculGain(pointsDansLeVecteurAttaque, nombreDeBoutDuPreneur);
		
		
		if (PrefsRegles.ManiereDeCompter)
		{
			if(gain < 0)
			{
				// ! faire l'arrondissement des point ici
				gain += -25;
			}
			else if (gain >= 0)
			{
				gain += 25;
			}
			
			gain *= typeDuContrat.getFacteur();
		}
		else
		{
			gain += typeDuContrat.getValeurContrat();
		}

		return gain;

	}

	/* test de classe */
	public static void main(String[] args)
	{
		Scores S = new Scores();
		Contrat C = Contrat.GARDE_SANS;
		S.calculDerniereLigneScore(C, 10, true, 2);
		C = Contrat.PETITE;
		S.calculDerniereLigneScore(C, 20, false, 0);

		S.affiche();

	}

}