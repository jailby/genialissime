package fr.um2.projetl3.tarotandroid.jeu;

import java.util.Vector;

public class Scores
{
	private Vector<Integer[]> scores;

	public Scores()
	{
		int J = Partie.getNombreDeJoueurs();
		scores = new Vector<Integer[]>();
		Integer[] derniereLigne = new Integer[J];
		for(int i =0; i<J;i++)
		{
			derniereLigne[i]=0;
		}
		scores.add(derniereLigne);

	}
	
	/*
	 * cette fonction actualise le tableau de score en calculant la derniere ligne
	 * les variables en paramettre sont le type du contrat, le gain(de combien le jouer la remporte ou perdu, valeur toujour positive)
	 * si le joueur qui a fait le contrat l'a remporte, et finalament quel est le jouer qui a fait le contrat
	 /**/
	public void calculDerniereLigneScore(Contrat typeDuContrat, int Gain,boolean joueurReussie, int joueurContrat)
	{
		int J = Partie.getNombreDeJoueurs();
		int valeurScore = calculScore(typeDuContrat, Gain);
		Integer[] dernierResultat = new Integer[J];
		Integer[] derniereLigne = new Integer[J];
		Integer[] nouvelleLigne = new Integer[J];
		derniereLigne = scores.lastElement();
		dernierResultat = ScoreLigne(valeurScore, joueurReussie, joueurContrat);
		for(int i = 0; i<J; i++)
		{
			nouvelleLigne[i] = derniereLigne[i] + dernierResultat[i];
		}
		assert(sommePointsNul(nouvelleLigne));
		scores.add(nouvelleLigne);
	}
	
	//pour lassertion que la somme des points d'une ligne soit toujours nulle
	public boolean sommePointsNul(Integer[] ligneScore){
		int s=0;
		for(int i = 0; i<Partie.getNombreDeJoueurs(); i++)
		{
			s = s + ligneScore[i]; 
		}
		if(s==0) return true;
		else return false;
	}
	
	// affiche chaque ligne de score de la partie
	public void affiche(){
		System.out.println("Scores : ");
		int J = Partie.getNombreDeJoueurs();
		int I = scores.size();
		/*
		pour afficher les noms de joueurs en debut de tableau 
		
		for(int i = 0; i<J ; i++)
		{
		 
			System.out.print(getNomJoueur(i));
		}
		/**/
		for(int i = 0; i<I; i++)
		{
			for(int j = 0; j<J; j++)
			{
				System.out.print(scores.get(i)[j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		
	}
	



	// calcule une ligne de score en fonction des points que chaque joueur gangen ou pert et du contrat et du jouer qui a prit le contrat 
	public Integer[] ScoreLigne(int valeurScore, boolean joueurReussie, int joueurContrat)
	{
		Integer[] lscore = new Integer[Partie.getNombreDeJoueurs()];
		if(joueurReussie)
		{
			for(int i=0; i < Partie.getNombreDeJoueurs(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = valeurScore * (Partie.getNombreDeJoueurs()-1);
				}
				else
				{
					lscore[i] = -valeurScore;
				}
			}
		}
		else 
		{
			for(int i=0; i < Partie.getNombreDeJoueurs(); i++)
			{
				if(i == joueurContrat)
				{
					lscore[i] = -valeurScore * (Partie.getNombreDeJoueurs()-1);
				}
				else
				{
					lscore[i] = valeurScore;
				}
			}
		}
		return lscore;
	}
	
	//calcule le nombre de points remporte a la fin d'un tour
	public int calculePoints(CartesRemportes a)
	{
		int demipoints=0;
		for(int i=0; i<a.getsize(); i++)
		{
			Carte c =  a.getCarte(i);
			demipoints = demipoints + c.valeur();
		}
		demipoints = demipoints / 2; // ! perd un demi-point si nombre impair de demi-points
				
		return demipoints;
	}

	//calcule de combien le tour a ete remporte en fonction du nombre de points fait par le prenneur de contrat et le nombre de bouts qu'il possedait
	// le gain peut etre possitif, ou negatif si le joueur ne la pas remporte
	public int calculGain(Contrat typeDuContrat, int Points, int nbrdebouts)
	{
		switch(nbrdebouts)
		{
		case 0:  //aucun bout
			return Points - 56;
		case 1:  //1 bout 
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
	
	//calcule la valeur du score en fonction du type de contrat et de combien la partie a ete remporte
	public int calculScore(Contrat typeDuContrat, int Gain)
	{
		int resultat = 25;
		resultat = resultat + Gain ;
		
		// ! faire les types de contrats 
		if( typeDuContrat == Contrat.GARDE){
			resultat*=2;
		}
		else if( typeDuContrat == Contrat.GARDE_SANS){
			resultat*=4;
		}
		else if( typeDuContrat == Contrat.GARDE_CONTRE){
			resultat*=6;
		}
		
		return resultat;
	}

/* test de classe	*/
	public static void main(String[] args)
	{
		Scores S = new Scores();
		Contrat C = Contrat.GARDE_SANS;
		S.calculDerniereLigneScore(C,10,true,2);
		C = Contrat.PETITE;
		S.calculDerniereLigneScore(C,20,false,0);
	
		S.affiche();

	}

}

 
/**
 * @author jbsubils
 */
// ! gros bordel dans cette class je vais la modifier
