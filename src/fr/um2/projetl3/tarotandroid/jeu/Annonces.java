package fr.um2.projetl3.tarotandroid.jeu;


public class Annonces 
{
	
	public void phaseAnnonce()
	{
		boolean conditionArret = true;
		int compteurPourToutLeMondePasse = 0;
		int nombreDeJoueur=Partie.getNombreDeJoueurs(); // ? pourquoi il y a un warning ?
		Contrat contrat = new Contrat("Aucune prise", -1);;
		int numeroDuJoueur = 0;
		Contrat tableauDesContrat[] = null; // ! devra etre initialis� au nombre de joeur dans la partie 
		
		
		while(conditionArret)
		{
			
			contrat = Partie.getJoueur(numeroDuJoueur).demanderAnnonce(); 
			
			tableauDesContrat[numeroDuJoueur] = contrat ;
			
			if(contrat == Contrat.PASSE)
			{
				compteurPourToutLeMondePasse++;
			}
			
			/**
			 * 		 
			si passe, petite, garde ou garde sans  on continue � demander
				sauf si on est arriver au dernier joeur l� cas particulier
			
			/**/
			if (compteurPourToutLeMondePasse+1 == nombreDeJoueur)
			{
				contrat = Contrat.AUCUN;
				conditionArret = false ;
			}
			if(contrat.getPoids() == 5) // alors c'est une garde_sans => la phase d'annonce est finit 
			{
				conditionArret = false;
			}
			else
			{

			}
		}
		
		Donne.setContratEnCours(contrat);
	}
	
	public void annonce4joueurs()
	{
	
		Contrat con = Contrat.PASSE;
		Contrat controle = Contrat.PASSE;
		for(int i=0; i<8 ; i++ )
		{
			controle = demandejouer(con,i%4);
			if	(controleContrats(con,controle))
			{
				if (controle.getName()=="Passe"){
					informejoueurs(con,controle);
				}
				con=controle;
				informejoueurs(con);
			}
			else
			{
				gardeillegale(i%4);
			}
		}
	}
	
	
	private Contrat demandejouer(Contrat con, int i) // ! m�thode d�ja existante dan Joueur
	{
		// TODO Auto-generated method stub
		return null;
	}


	public boolean controleContrats(Contrat a,Contrat b)
	{
		if(b.getPoids()==0)
		{
			return true;
		}
		else if(a.getPoids()==5) return false;//?ce cas ne devrait jamais arriver on arrete de demander les jouers une fois le plus grand contract fait
		else if(a.getPoids()<b.getPoids()) return true;
		else return false;
		
	}

	
	public void informejoueurs(Contrat ancien, Contrat nouveau){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	public void informejoueurs(Contrat con){
		//TO-DO informe tous les joueurs si le jouer n'a pas pris de contrats ou si le contrats a augmente
	}
	
	public void gardeillegale(int i){
		//TO-DO informe le jouer i que sa garde est illegale
		// ! je comprend pas cette m�thode en quoi une garde est illegale ( �quel moment une garde peut �tre ill�gale) ?
	}
}
