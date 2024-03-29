package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.activities.Contexts.TAG;
import static fr.um2.projetl3.tarotandroid.activities.Contexts.applicationContext;
import static fr.um2.projetl3.tarotandroid.jeu.Context.P;

import java.util.Arrays;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import fr.um2.projetl3.tarotandroid.R;
import fr.um2.projetl3.tarotandroid.clients.IJoueur;
import fr.um2.projetl3.tarotandroid.clients.JoueurGraphique;
import fr.um2.projetl3.tarotandroid.clients.JoueurIA;
import fr.um2.projetl3.tarotandroid.exceptions.CarteUIDInvalideException;
import fr.um2.projetl3.tarotandroid.jeu.Annonces;
import fr.um2.projetl3.tarotandroid.jeu.Carte;
import fr.um2.projetl3.tarotandroid.jeu.CarteGraphique;
import fr.um2.projetl3.tarotandroid.jeu.Contrat;
import fr.um2.projetl3.tarotandroid.jeu.Partie;
import fr.um2.projetl3.tarotandroid.jeu.PrefsApplication;
import fr.um2.projetl3.tarotandroid.jeu.PrefsRegles;

@SuppressWarnings("unused")
public class EcranJeu extends Activity
{
	IJoueur moi, ia1, ia2, ia3;
	IJoueur moi1;
	TextView logT;
	ScrollView logSV;
	RelativeLayout rl;
	SharedPreferences sp;
	

    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecran_jeu);

		// TextView pour log
		logT = (TextView)findViewById(R.id.log);
		logSV = (ScrollView)findViewById(R.id.logSV);
		
		// RelativeLayout principal (plateau)
		rl = (RelativeLayout) findViewById(R.id.mainLayout);		

		// Lancement de la partie
		PrefsRegles.sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		P = new Partie();
		moi = new JoueurGraphique("Moi", EcranJeu.this);
		ia1 = new JoueurIA("IA1", 42, 00); // 500
		ia2 = new JoueurIA("IA2", 43, 00);
		ia3 = new JoueurIA("IA3", 44, 00);
		P.setJoueur(0, moi);
		P.setJoueur(1, ia1);
		P.setJoueur(2, ia2);
		P.setJoueur(3, ia3);
		
		premierPli = true;
		sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		((TextView) findViewById(R.id.annonceO)).setText(sp.getString("AD1", "Ouest"));
		((TextView) findViewById(R.id.annonceN)).setText(sp.getString("AD2", "Nord"));
		((TextView) findViewById(R.id.annonceE)).setText(sp.getString("AD3", "Est"));
		// P.start() permet de lancer le thread, fait appel à P.run(), lequel fait appel à P.lancerPartie()
		P.start();
	}
	
	/**/
	public void afficherMain(final Vector<Carte> main)
	{
		// final Animation avancer = AnimationUtils.loadAnimation(this, R.anim.avancercarte);

		runOnUiThread(new Runnable()
		{
			public void run()
			{
				for(int i = 0; (i < 26); ++i)
				{
					// System.out.println("i : " + i);
					int imageViewId = 0;
					final Carte card = (i >= main.size()) ? null : main.get(i);
					String imageViewIdName = "imageCarte"+Integer.toString(i);
					//Carte cardL = (i >= cartesLegales.size()) ? null : main.get(i);
					//System.out.println(cardL + "est une carte legale!");
					try {
						imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
						//System.out.println("id = " + imageViewId);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
			
					final ImageView imageView = (ImageView) findViewById(imageViewId);
					//System.out.println("imageview : " + imageViewIdName);
					if (card == null) {
						imageView.setVisibility(View.GONE);
					} else {
						imageView.setVisibility(View.VISIBLE);
						// si visible on lui donne la bonne image....
						//imageView.setDrawableResource(card.getResource());
						try {
							imageView.setImageDrawable((new CarteGraphique(card.uid())).mImageView.getDrawable());
							imageView.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.MULTIPLY);	
							imageView.setClickable(false);
							//System.out.println("uid" + card.uid());
						} catch (CarteUIDInvalideException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					Vector<Carte> cartesLegales;
					try
					{
						cartesLegales = P.donne().indiquerCartesLegalesJoueur();
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						cartesLegales = main;
					}
					for(Carte c : cartesLegales)
					{
						
						//System.out.println("uid L : " + c.uid());
						if(card != null){
							if(c.uid() == card.uid()){
								imageView.clearColorFilter();
								imageView.setClickable(true);
								imageView.setOnClickListener(new OnClickListener(){
						            public void onClick(View v) {
						            	if(resultatAnnonce != Contrat.AUCUN)
						            	{
										// imageView.startAnimation(avancer);
										imageView.setVisibility(View.GONE);
										resultatCarte = card;
						            	}
						            }
						        });
							}							
						}
					}
					
				}
			}
		});
	}/**/
	
	public void afficherMainEcart(final Vector<Carte> main)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				for(int i = 0; (i < 26); ++i)
				{
					
					int imageViewId = 0;
					final Carte card = (i >= main.size()) ? null : main.get(i);
					String imageViewIdName = "imageCarte"+Integer.toString(i);
					//Carte cardL = (i >= cartesLegales.size()) ? null : main.get(i);
					//System.out.println(cardL + "est une carte legale!");
					
					try {
						imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
						//System.out.println("id = " + imageViewId);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
			
					final ImageView imageView = (ImageView) findViewById(imageViewId);
					//System.out.println("imageview : " + imageViewIdName);
					if (card == null) {
						imageView.setVisibility(View.GONE);
					} else {
						imageView.setVisibility(View.VISIBLE);
						// si visible on lui donne la bonne image....
						//imageView.setDrawableResource(card.getResource());
						try {
							imageView.setImageDrawable((new CarteGraphique(card.uid())).mImageView.getDrawable());
							imageView.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.MULTIPLY);	
							imageView.setClickable(false);
							//System.out.println("uid" + card.uid());
						} catch (CarteUIDInvalideException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
					if(card != null){
						imageView.clearColorFilter();
						imageView.setClickable(true);
						imageView.setOnClickListener(new OnClickListener(){
						public void onClick(View v) {
							if(!(resultatEcart.contains(card))){
								resultatEcart.add(card);
								imageView.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
								}
							else{
								resultatEcart.remove(card);
								imageView.clearColorFilter();
							}
							}
						});
					}							
				}
			}
		});
	}

	private Contrat resultatAnnonce;
	private boolean ecartOK;
	private AlertDialog alerte = null;
	private void afficherDemandeAnnonce()
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Annonce");
				
				Log.d(TAG, "Passage dans afficherDemandeAnnonce");
				
				Vector<Contrat> listeAnnoncesDispo = (Vector<Contrat>) Contrat.getListeContratsDisponibles().clone();
				Log.d(TAG, "dispos : "+listeAnnoncesDispo.toString());
				Contrat contratMax = Annonces.getContratMax();
				Vector<CharSequence> listeAnnoncesCS = new Vector<CharSequence>();
				for(Contrat c: listeAnnoncesDispo)
				{
					if(c.getPoids() > contratMax.getPoids() || c == Contrat.PASSE)
					{
						listeAnnoncesCS.add((CharSequence)c.toString());
					}
				}
				Log.d(TAG, "autorisés : "+listeAnnoncesCS.toString());
				
				final CharSequence[] listeAnnonces = new CharSequence[listeAnnoncesCS.size()];
				
				listeAnnoncesCS.toArray(listeAnnonces);
				alert.setSingleChoiceItems(listeAnnonces, -1, new DialogInterface.OnClickListener()
				{	
					
					public void onClick(DialogInterface arg0, int i)
					{
						makeToast((String)listeAnnonces[i]);
						if(listeAnnonces[i] == "Passe")
						{
							resultatAnnonce = Contrat.PASSE;
						}
						else if(listeAnnonces[i] == "Petite")
						{
							resultatAnnonce = Contrat.PETITE;
						}
						else if(listeAnnonces[i] == "Garde")
						{
							resultatAnnonce = Contrat.GARDE;
						}
						else if(listeAnnonces[i] == "Garde sans")
						{
							resultatAnnonce = Contrat.GARDE_SANS;
						}
						else if(listeAnnonces[i] == "Garde contre")
						{
							resultatAnnonce = Contrat.GARDE_CONTRE;
						}
						else
						{
							resultatAnnonce = Contrat.AUCUN; 
						}
						alerte.dismiss();
					}
				});
				alerte = alert.create();
				alerte.show();
			}
		});
	}
	public Contrat demanderAnnonce(Contrat contrat)
	{
		resultatAnnonce = Contrat.AUCUN;
		final Button bDemandeAnnonce = new Button(this);
		bDemandeAnnonce.setText(getString(R.string.bannoncer));
		bDemandeAnnonce.setOnClickListener(new View.OnClickListener()
		{
			
			public void onClick(View v)
			{
				afficherDemandeAnnonce();
			}
		});
		final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ABOVE, R.id.horizontalScrollView1);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.addView(bDemandeAnnonce, lp);
			}
		});
				
		while(resultatAnnonce == Contrat.AUCUN)
		{} // On attend que resultatAnnonce soit différent de Contrat.AUCUN
		
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				rl.removeView(bDemandeAnnonce);
			}
		});
		
		// resultatAnnonce != Contrat.AUCUN
		System.out.println("On va retourner "+resultatAnnonce);
		
		return resultatAnnonce;
	}
	
	public Vector<Carte> resultatEcart = new Vector<Carte>();
	public Vector<Carte> demanderEcart()
	{
		resultatEcart.clear();
		ecartOK = false;
		final Vector<Carte> cartesLegalesEcart = P.donne().indiquerCartesLegalesEcart();
		afficherMainEcart(cartesLegalesEcart);
		checked = new boolean[cartesLegalesEcart.size()];
		Arrays.fill(checked, false);
				
		/*final Button bDemandeEcart = new Button(this);
		bDemandeEcart.setText("Faire son écart");
		bDemandeEcart.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				afficherDemandeEcart(cartesLegalesEcart);
			}
		});*/
		final Button bValideEcart = new Button(this);
		bValideEcart.setText(getString(R.string.bEcart));
		bValideEcart.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if(resultatEcart.size() == P.getnombreDeCartesPourLeChien())
				{
					ecartOK = true;
					
				}
				else
					makeToast("Il faut "+P.getnombreDeCartesPourLeChien()+" cartes dans le chien, pas "+resultatEcart.size()+".");
			}
		});
		
		final LinearLayout llEcart = new LinearLayout(this);
		final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ABOVE, R.id.horizontalScrollView1);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.addView(llEcart, lp);
				//llEcart.addView(bDemandeEcart);
				llEcart.addView(bValideEcart);
			}
		});
				
		while(!ecartOK)
		{}
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.removeView(llEcart);
			}
		});
		
		return resultatEcart;
	}
	
	boolean[] checked;

	public void afficherDemandeEcart(final Vector<Carte> cartesLegalesEcart)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
				alert.setTitle("Faire son écart");
				
				Vector<CharSequence> vListeCartesLegalesCS = new Vector<CharSequence>();
				for(Carte c: cartesLegalesEcart)
				{
					vListeCartesLegalesCS.add(c.toStringShort());
				}
				
				final CharSequence[] listeCartesLegalesCS = new CharSequence[vListeCartesLegalesCS.size()];
				vListeCartesLegalesCS.toArray(listeCartesLegalesCS);
				
				alert.setMultiChoiceItems(listeCartesLegalesCS, checked, new DialogInterface.OnMultiChoiceClickListener()
				{	
					public void onClick(DialogInterface arg0, int i, boolean b)
					{
						if(!b) // décochage
						{
							for(Carte c: resultatEcart)
							{
								if(c.uid() == cartesLegalesEcart.get(i).uid())
								{
									resultatEcart.remove(c);
									checked[i] = false;
									break;
								}
							}
						}
						else // cochage
						{
							resultatEcart.add(cartesLegalesEcart.get(i));
							checked[i] = true;
						}
					}
				});
				alerte = alert.create();
				alerte.show();
			}
		});
	}
	
	private Carte resultatCarte;
    public void afficherDemandeCarte()
    {
		runOnUiThread(new Runnable()
		{
	        public void run()
	        {
	            AlertDialog.Builder alert = new AlertDialog.Builder(EcranJeu.this);
	            alert.setTitle("Jouer une carte");
	            
	            final Vector<Carte> cartesLegales = P.donne().indiquerCartesLegalesJoueur();
	            Vector<CharSequence> vListeCartesLegalesCS = new Vector<CharSequence>();
	            for(Carte c: cartesLegales)
	            {
	            	vListeCartesLegalesCS.add(c.toStringShort());
	            	
	            }
	            
	            final CharSequence[] listeCarteLegales = new CharSequence[vListeCartesLegalesCS.size()];
	            vListeCartesLegalesCS.toArray(listeCarteLegales);
	            alert.setSingleChoiceItems(listeCarteLegales, -1, new DialogInterface.OnClickListener()
	            {
	                public void onClick(DialogInterface arg0, int i)
	                {
	                    resultatCarte = cartesLegales.get(i);
	                    alerte.dismiss();
	                }
	            });
	            alerte = alert.create();
            alerte.show();
	        }
		});
	}
    public Carte demanderCarte()
	{
		resultatCarte = null;
		
		afficherMain(P.donne().getMain().triMain());

		while(resultatCarte == null)
		{} // On attend.
		
		System.out.println("On va retourner "+resultatCarte);
		//afficherMain(P.donne().indiquerCartesLegalesJoueur());
		return resultatCarte;
	}
	
	public void direMain(Vector<Carte> main)
	{
		afficherMain(P.donne().indiquerCartesLegalesJoueur());

		if (PrefsApplication.triDansAnnonce)
		{
			afficherMain(P.donne().getMain().triMain());
		}
		else
		{
			afficherMain(P.donne().indiquerCartesLegalesJoueur());
		}
	}
	
	public void direAnnonce(final Contrat c, int j)
	{
		final TextView tvAnnonce;
		boolean ok = true;
		switch(j)
		{
		case 0:
			tvAnnonce = null;
			// tvAnnonce = (TextView) findViewById(R.id.annonceS);
			break;
		case 1:
			tvAnnonce = (TextView) findViewById(R.id.annonceO);
			break;
		case 2:
			tvAnnonce = (TextView) findViewById(R.id.annonceN);
			break;
		case 3:
			tvAnnonce = (TextView) findViewById(R.id.annonceE);
			break;
		default:
			log("Annonce de joueur inconnu "+j);
			tvAnnonce = null;
			ok = false;
		}
		
		if(ok)
		{
			hh.post(new Runnable()
			{
				public void run()
				{
					if(tvAnnonce != null)
					{
						tvAnnonce.setText(c.toString());
					}
				}
			});
		}
	}

	public boolean premierPli;
	public void direCarteJouee(final Carte c, final int j)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				if(premierPli)
				{
					((TextView) findViewById(R.id.annonceO)).setText(sp.getString("AD1", "Ouest"));
					((TextView) findViewById(R.id.annonceN)).setText(sp.getString("AD2", "Nord"));
					((TextView) findViewById(R.id.annonceE)).setText(sp.getString("AD3", "Est"));
					premierPli = false;
				}
				
				String imageViewIdName = "carte";
				int imageViewId = -1;
				switch(j)
				{
				case 0:
					imageViewIdName += "S";
					break;
				case 1:
					imageViewIdName += "O";
					break;
				case 2:
					imageViewIdName += "N";
					break;
				case 3:
					imageViewIdName += "E";
					break;
				}
			
				try {
					imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
	
				ImageView imageView = (ImageView) findViewById(imageViewId);
				// System.out.println("Affichage de la carte "+c+" en "+imageViewId);
				imageView.setVisibility(View.VISIBLE);
				imageView.bringToFront();
				rl.bringChildToFront(imageView);
				try {
					imageView.setImageDrawable((new CarteGraphique(c.uid())).mImageView.getDrawable());
				} catch (CarteUIDInvalideException e) {
					e.printStackTrace();
				}
			}
		});
        							
	}
	
	public boolean attendrePli;
	public void direPliRemporté(Vector<Carte> pli, int joueur)
	{
		attendrePli = true;
		(new Thread()
		{
			public void run()
			{
				try
				{
					sleep(1000);
					attendrePli = false;
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).run();
		
		while(attendrePli)
		{}
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				ImageView carteS = (ImageView) findViewById(R.id.carteS);
				ImageView carteO = (ImageView) findViewById(R.id.carteO);
				ImageView carteN = (ImageView) findViewById(R.id.carteN);
				ImageView carteE = (ImageView) findViewById(R.id.carteE);
				
				carteS.setVisibility(View.GONE);
				carteO.setVisibility(View.GONE);
				carteN.setVisibility(View.GONE);
				carteE.setVisibility(View.GONE);	
			}
		});
	}
	
	public boolean okChien;
	public Button bOKChien;
	public void direChien(final Vector<Carte> chien)
	{
		okChien = false;
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				LinearLayout ll = (LinearLayout) findViewById(R.id.zoneChien);
				ll.setVisibility(View.VISIBLE);
				for(int i=0; i<6; i++)
				{
					int imageViewId = 0;
					String imageViewIdName = "chien"+Integer.toString(i);
					try {
						imageViewId = R.id.class.getDeclaredField(imageViewIdName).getInt(null);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
			
					final ImageView imageView = (ImageView) findViewById(imageViewId);
					try
					{
						imageView.setImageDrawable((new CarteGraphique(chien.get(i).uid())).mImageView.getDrawable());
					} catch (CarteUIDInvalideException e)
					{
						e.printStackTrace();
					}
				}
				
				bOKChien = new Button(applicationContext);
				bOKChien.setText("OK");
				bOKChien.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						okChien = true;
					}
				});
			
				final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.addRule(RelativeLayout.BELOW, R.id.zoneChien);
				lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
				rl.addView(bOKChien, lp);
			}
		});
		
		while(!okChien)
		{}
		
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				rl.removeView(bOKChien);
				LinearLayout ll = (LinearLayout) findViewById(R.id.zoneChien);
				ll.setVisibility(View.GONE);
			}
		});
		
	}
	
	public Dialog dialogScore;
	
	public void direScore(final Vector<Integer[]> scores)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				dialogScore = new Dialog(EcranJeu.this);
				dialogScore.setContentView(R.layout.scores_layout);
				dialogScore.setTitle("Scores");
				TextView tvScores = (TextView) dialogScore.findViewById(R.id.textScores);
				
				tvScores.append("Moi\t\t"+sp.getString("AD1", "Ouest")+"\t\t"+sp.getString("AD2", "Nord")+"\t\t"+sp.getString("AD3", "Est")+"\n");
				for(Integer[] s: scores)
				{
					if(s != scores.firstElement())
					{
						for(int i=0; i<P.getNombreDeJoueurs(); i++)
						{
							tvScores.append(""+s[i]+"\t\t");
						}
						tvScores.append("\n");
					}
				}
				
				Button bFermerScores = (Button) dialogScore.findViewById(R.id.bFermerScores);
				bFermerScores.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						dialogScore.dismiss();
					}
				});
				
				dialogScore.show();
			}
		});
	}
	
	Handler hh = new Handler();
	
	public void makeToast(String s, boolean court)
	{
		final String rS = s;
		final boolean rCourt = court;
		runOnUiThread(new Runnable()
		{
			
			public void run()
			{
				Toast.makeText(EcranJeu.this, rS, (rCourt ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)).show();
				// log("Toast : " + rS);				
			}	
		});
	}
	
	public void makeToast(String s)
	{
		makeToast(s, false);
	}
	
	public void log(String s)
	{
		log(s, false);
	}
	
	public void log(String s, final boolean important)
	{
		//System.out.println("Log : "+s);
		final String msg = s;
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				Log.d("Genialissime","Log : "+msg);
				if(important)
				{
					logT.append((CharSequence)"\n"+msg);
				}
				logSV.fullScroll(ScrollView.FOCUS_DOWN);
				
			}
		});
	}



}
