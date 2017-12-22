package patricia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class PatriciaTrie {
	public static void main(String args[]) throws IOException {
		Scanner scan = new Scanner(System.in);
		PatriciaTrieNoeud ptn = new PatriciaTrieNoeud();
		long debut;
		File repertoire;
		File repertoire3;
		String[] listefichiers;
		System.out.println("Patricia Trie Test\n");
		char ch;

		do {
			System.out.println("\nPatricia Trie Operations\n");
			System.out.println("1. Insertion ");
			System.out.println("2. Impression ");
			System.out.println("3. Recherche ");
			System.out.println("4. Comptage de mots ");
			System.out.println("5. Liste de mots ");
			System.out.println("6. Comptage des pointeurs vers Nil ");
			System.out.println("7. Calcul de l'hauteur");
			System.out.println("8. Calcul de la profondeur moyenne");
			System.out.println("9. Comptage des mots prefixés par un autre mot donné");
			System.out.println("10. Suppression de mot");
			System.out.println("11. Insertion Shakespeare");
			System.out.println("12. Suppression Shakespeare");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				File repertoire2 = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/algav.txt");
				debut = System.currentTimeMillis();
				ptn = insert(repertoire2, ptn);
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 2:
				debut = System.currentTimeMillis();
				ptn.printRecursive(0);
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 3:
				System.out.println("Saisir le mot a chercher: ");
				
				Scanner entradaEscaner = new Scanner (System.in); 
				String entradaTeclado = entradaEscaner.nextLine (); 

				System.out.print("Rechercer : " + entradaTeclado + " => ");
				debut = System.currentTimeMillis();
				System.out.println(ptn.recherche(entradaTeclado));
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 4:
				debut = System.currentTimeMillis();
				System.out.println(ptn.comptageMots());
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 5:
				debut = System.currentTimeMillis();
				ArrayList<String> lm = ptn.listeMots("");
				System.out.println(lm);
				System.out.println("la taille est: "+lm.size());
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 6:
				debut = System.currentTimeMillis();
				System.out.println(ptn.comptageNil(0));
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 7:
				debut = System.currentTimeMillis();
				System.out.println(ptn.hauteur());
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 8:
				debut = System.currentTimeMillis();
				System.out.println(ptn.profondeurMoyenne());
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 9:
				System.out.println("Saisir le mot : ");
				
				Scanner entradaEscaner2 = new Scanner (System.in); 
				String entradaTeclado2 = entradaEscaner2.nextLine (); 
				debut = System.currentTimeMillis();
				System.out.println("Resultat : "+ptn.prefixe(entradaTeclado2));
				System.out.println("Temps de calcul: "+(System.currentTimeMillis()-debut)+"ms");
				break;
			case 10:
				System.out.println("Saisir le mot : ");
				
				Scanner entradaEscaner3 = new Scanner (System.in); 
				String entradaTeclado3 = entradaEscaner3.nextLine (); 
				debut = System.currentTimeMillis();
				ptn.suppression(entradaTeclado3);
				System.out.println("Temps de suppression: "+(System.currentTimeMillis()-debut)+"ms");
				break;	
				
			case 11:
				repertoire = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare");

				int i;
				listefichiers = repertoire.list();
				debut = System.currentTimeMillis();
				for (i = 0; i < listefichiers.length; i++) {
					if (listefichiers[i].endsWith(".txt") == true) {

						System.out.println(listefichiers[i]);
						repertoire3 = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare/"+listefichiers[i]);
						
						ptn = insert(repertoire3,ptn);
					}
				}
				System.out.println("Temps de insertion: "+(System.currentTimeMillis()-debut)+"ms");
				
				break;
			case 12:
				repertoire = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare");
				int j;
				listefichiers = repertoire.list();
				debut = System.currentTimeMillis();
				for (j = 0; j < listefichiers.length; j++) {
					if (listefichiers[j].endsWith(".txt") == true) {

						System.out.println(listefichiers[j]);
						repertoire3 = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare/"+listefichiers[j]);
						
						ptn = suppression(repertoire3,ptn);
					}
				}
				System.out.println("Temps de suppression: "+(System.currentTimeMillis()-debut)+"ms");

				break;
			default:
				System.out.println("Erreur de saisie \n ");
				break;
			}

			System.out.println("\nVoulez-vous continuer(y or n) \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');
	}
	
	private static PatriciaTrieNoeud suppression(File repertoire,
			PatriciaTrieNoeud ptn) throws IOException {
		FileReader fr = null;
		fr = new FileReader(repertoire);
		BufferedReader bf = new BufferedReader(fr);

		String line = null;
		line = bf.readLine();

		while (line != null) {
			String[] decompose = line.split(" ");
			for (String string : decompose) {
				ptn.suppression(string);			}
			line = bf.readLine();
		}

		bf.close();
		return ptn;
	}

	public static PatriciaTrieNoeud insert(File repertoire, PatriciaTrieNoeud ptn) throws IOException {
		FileReader fr = null;
		fr = new FileReader(repertoire);
		BufferedReader bf = new BufferedReader(fr);

		String line = null;
		line = bf.readLine();

		while (line != null) {
			String[] decompose = line.split(" ");
			for (String string : decompose) {
				ptn.insert(string);
			}
			line = bf.readLine();
		}

		bf.close();
		return ptn;

	}

}
