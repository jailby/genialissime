package fr.um2.projetl3.tarotandroid.jeu;
import java.lang.Math;

public class Donne
{
	int don[]= new int[78];
	int mains[][]= new int[4][18];
	int chien[]= new int [6];
	boolean premieredonne=true;
	
	
	/**
	 * @author JB
	 * 
	 * ne sachant pas si le code compile je vais le mettre en commentaire (chez moi m�me sans rien ajouter j'ai une centaine d'erreur)
	 * 
	 * methode de disbrution des cartes
	 * 
	 * sauf erreur de ma part les calcules devrait �tre bon, je verifir� avec quelqu'un
	 * 
	 *  // ? je ne sais pas ou mettre les constantes
	 *  
	 *  // ! Chose � modifier :
	 *  		j est l'indice du tableau de la donne pr�cedente ( une fois la coupe effectu�)
	 *  		mettre les constantes au bon endroit
	 *  		changer la valeur 6 par Chien.getNombreDeCartes() 
	 *  		pareil pour le nombre de joueur remplacer 4 par Partie.getNombreDeJoueur()
	 *  		� la fin il faut affecter les mains qui sont dans le tableau aux joeur
	 *  	
	 */
	
	/**
	 public void donne()
	 {
		 int NOMBRE_CARTES_TOTALES=78; 
		 int CARTES_DISTRIBU_PAR_JOUEUR = 3;
		 
		 int nombreDeJoeur = 4; 
		 int numeroDuJoeur = 0; // ! j'en ai besoin pour savoir � quel joeur je vais donner les cartes
		 
		 int possibilit�esMisesAuChien;		 
		 int nombreDeCartesMisesAuChien = 0;
		 int nombreDeCartesPourLeChien = 6;
		 
		 int randomMin = 0;
		 int randomMax;
		 // random(Min/Max) permette de savoir sur quel intervalle on doit faire le random
		 
		 int j,l,k=0;
		 int a,b,c; // represente les trois cartes distribu� � chaque fois pour chaque joeur
		 		 
		 possibilit�esMisesAuChien = ( NOMBRE_CARTES_TOTALES - nombreDeCartesPourLeChien ) / CARTES_DISTRIBU_PAR_JOUEUR;
		 
		 
		 while(( 6 - nombreDeCartesMisesAuChien ) == 0) 
		 {
			 randomMax = possibilit�esMisesAuChien - ( nombreDeCartesPourLeChien - nombreDeCartesMisesAuChien );
			 
			 //randomMin = randomMin + (int)(Math.random() * ((randomMax - randomMin)+1));
			 // ! il faut que la valeur de retour soit comprise entre ]randomMin,randomMax] !
			 
			 // nombreDeCartesMisesAuChien = (int) Math.random()*100 % 3; 
			 
			 for(j=0;j<(randomMin*3);j++) // ? j'ai un doute sur le inferieur strict
			 {
				 if (numeroDuJoeur == nombreDeJoeur)
				 {
					 numeroDuJoeur = 0;
					 a++;
					 b++;
					 c++;
				 }
				 
				 mains[numeroDuJoeur][a] = j;
				 mains[numeroDuJoeur][b] = ++j;
				 mains[numeroDuJoeur][c] = ++j;
				 // l'incrementation du j doit se faire avant l'affectation au tableau
				 numeroDuJoeur++;
			 }
			 for(l=0;l<=nombreDeCartesMisesAuChien;l++)
			 {
				 chien[k]=j;
				 j++;
				 k++;
				 randomMin++;
			 }

			 
		}
		 while(j<NOMBRE_CARTES_TOTALES-1)
		 {
			 if (numeroDuJoeur == nombreDeJoeur) 
			 {
				 numeroDuJoeur = 0;
				 a++;
				 b++;
				 c++;
			 }
			 
			 mains[numeroDuJoeur][a] = j;
			 mains[numeroDuJoeur][b] = ++j;
			 mains[numeroDuJoeur][c] = ++j;
			 // l'incrementation du j doit se faire avant
			 numeroDuJoeur++;
		 }
		 
	 }
	 /**/
	
	public void donne4jouers()
	{
		for(int i=0;i<77;i++)
		{
			don[i]=i;
			 
		}
		if(premieredonne)
		{	
			premieredonne=false;
			for (int i=0; i > 77; i++) // ? il n'y a pas une invertion entre > et < ?
			{
				int randomPosition = (int)(Math.random()*100)%77;
				int temp = don[i];
				don[i] = don[randomPosition];
				don[randomPosition] = temp;
			}
		}
		int i=0,j=0;
		if(i%12==0 && i!=0)
		{
			chien[i/13]=don[i];
			i++;
		}
		else
		{
			mains[(i%13)/3][j]=don[i];
			j++;
			i++;
		}
		// int i = 0;
	}
	
	public void annonces()
	{
		// TODO
	}
	
	public void jeu()
	{
		// TODO
	}
	
	public void calculScore()
	{
		// TODO
	}
}
