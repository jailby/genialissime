package fr.um2.projetl3.tarotandroid.clients;

import java.util.Scanner;

import android.R.string;

import fr.um2.projetl3.tarotandroid.jeu.*;

public class JoueurTexte implements Joueur // implements Joueur (quand Joueur era une interface)
{
	private int pID;
	private Main pMain;
	private String nom;

	public void setID(int pID)
	{
		this.pID = pID;
	}

	public Main getMain()
	{
		return pMain;
	}
	
	public void setMain(Main pMain)
	{
		this.pMain = pMain;
	}
	
	public String nom()
	{
		return nom;
	}

	public Contrat demanderAnnonce(Contrat contrat)
	{
		Contrat c = null;
		int compteur=0;//pour que le joueur ne puisse pas rentrer plus de 5 fois une mauvaise garde
		boolean mauvais_Contrat=true;
		System.out.println("À vous de parler :");
		while(mauvais_Contrat && compteur<5)
		{
			compteur++;
			switch(contrat.getPoids())
			{
			case 0:
				System.out.println(" Vos Choix :  0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 1:
				System.out.println(" Vos Choix :  0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 2:
				System.out.println(" Vos Choix :  0 = Passe, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
				break;
			case 3:
				System.out.println(" Vos Choix :  0 = Passe, 3 = Garde sans, 4 = GardeContre");
				break;
			case 4:
				System.out.println(" Vos Choix :  0 = Passe, 4 = GardeContre");
				break;
			default:
				//! il faudrait lancer une exception
				System.out.println("0 = Passe, 1 = Petite, 2 = Garde, 3 = Garde sans, 4 = GardeContre");
			}
					
			int id = (new Scanner(System.in)).nextInt();
			switch (id)
			{
			case 0:
				c = Contrat.PASSE;
				break;
			case 1:
				c = Contrat.PETITE;
				break;
			case 2:
				c = Contrat.GARDE;
				break;
			case 3:
				c = Contrat.GARDE_SANS;
				break;
			case 4:
				c = Contrat.GARDE_CONTRE;
				break;
			default:
				System.out.println("Pas compris "+id+", disons Passe.");
				c = Contrat.PASSE;
			}
			if( c != Contrat.PASSE && c.getPoids() <= contrat.getPoids() )
			{
				System.out.println(c.getPoids()+","+contrat.getPoids()+id);
				mauvais_Contrat=true;
				System.out.println("Votre choix est invalide veuillez le refaire");
			}
			else
			{
				mauvais_Contrat=false;
			}
		}
		if(compteur==5)
		{
			c = Contrat.PASSE;
			System.out.println("trop de mauvais choix, Passe par defaut");
		}
			
		return c;
	}
	/**/
	public Carte demanderCarte()
	{
		int num;
		Scanner sc = new Scanner(System.in);
		do
		{
			pMain.affiche();
			System.out.println("Jouez une carte en donnant un chiffre entre 1 et "+pMain.nbCartesRestantes());
			num = sc.nextInt();
			if(num < 0 || num >= pMain.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+pMain.nbCartesRestantes()+" !");
			}
		} while(num < 0 || num >= pMain.nbCartesRestantes());
		
		return pMain.getCarte(num);
	}/**/
	
	public JoueurTexte(String nom)
	{
		this.nom = nom;
	}
	public JoueurTexte(int pid, String nom)
	{
		pID = pid;
		this.nom = nom;
	}


	public int getID() 
	{
		return pID;
	}

	public void setNomDuJoueur(String s) 
	{
		// TODO Auto-generated method stub
		this.nom = s;
	}


	public String getNomDuJoueur() 
	{
		// TODO Auto-generated method stub
		return this.nom;
	}

	public void addChienDansMain(Carte[] chien) 
	{
		// TODO Auto-generated method stub
		for(Carte c:chien)
		{
			pMain.addCarte(c);
		}
	}

	public Carte[] demanderEcart() 
	{
		// TODO Auto-generated method stub
		Carte ecart[] = new Carte[Partie.getnombreDeCartesPourLeChien()];
		System.out.println("Vous allez devoir choisir "+Partie.getnombreDeCartesPourLeChien()+" à mettre dans le votre ecart");
		for(int i=0;i < Partie.getnombreDeCartesPourLeChien()-1;i++)
		{
			ecart[i] = demananderUneCartePourLecart();
		}
		return ecart;
	}

	private Carte demananderUneCartePourLecart() 
	{
	
		int num;
		Scanner sc = new Scanner(System.in);
		do
		{
			pMain.affiche();
			System.out.println("Mettez une carte à l'ecart en donnant un chiffre entre 1 et "+pMain.nbCartesRestantes());
			num = sc.nextInt();
			if(num < 0 || num >= pMain.nbCartesRestantes())
			{
				System.out.println("… entre 1 et "+pMain.nbCartesRestantes()+" !");
			}
		} while(num < 0 || num >= pMain.nbCartesRestantes());
		
		return pMain.getCarte(num);
		
	}
	
	
	public CarteCouleur appelerRoi()
	{
		CarteCouleur Roi = new CarteCouleur(14);
		System.out.println("Donnez la couleur du roi que vous voulez appeler");
		int id=-1;
		while(id<=0 || id >4)
		{
			System.out.println(" Vos Choix :  1 = coeur, 2 = pique, 3 = treffle, 4 = carreau");
			id = (new Scanner(System.in)).nextInt();
			switch (id)
			{
			case 1:
				System.out.println(" Vous avez appele le roi de coeur");
				Roi.setCouleur(Couleur.Coeur);
				break;
			case 2:
				System.out.println("Vous avez appele le roi de pique");
				Roi.setCouleur(Couleur.Pique);
				break;
			case 3:
				System.out.println(" Vous avez appele le roi de treffle");
				Roi.setCouleur(Couleur.Trefle);
				break;
			case 4:
				System.out.println(" Vous avez appele le roi de carreau");
				Roi.setCouleur(Couleur.Carreau);
				break;
			default:
				//! il faudrait lancer une exception
				System.out.println("Entree incorrecte, veuillez ressayer");
			}
		}
		return Roi;
	}
	
	public boolean possedeRoi(CarteCouleur roi)
	{
		return pMain.RoiDansLaMain(roi);
	}

}
