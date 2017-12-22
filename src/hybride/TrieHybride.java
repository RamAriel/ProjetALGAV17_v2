package hybride;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrieHybride {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		long debut;
		TrieHybrideNoeud th = new TrieHybrideNoeud();
		System.out.println("Trie Hybride Test\n");
		char ch;
		do {
			System.out.println("\nTrie Hybride Operations\n");
			System.out.println("1. Insertion avec equilibrage");
			System.out.println("2. Impression ");
			System.out.println("3. Calcul de l'hauteur sauf le branche egal");
			System.out.println("4. Insertion Shakespeare");

			int choice = scan.nextInt();
			switch (choice) {
			case 1:

				FileReader fr = new FileReader("C:/Users/pc/workspace/ProjetALGAV17_v2/fichier/algav.txt");
				BufferedReader bf = new BufferedReader(fr);

				String line = bf.readLine();
				Integer cpt = 0;
				while (line != null) {
					String[] decompose = line.split(" ");
					for (String string : decompose) {
						// System.out.println(string);
//						th.insert(string, cpt);
						th = th.insertEq(string, cpt);
						cpt++;
					}
					line = bf.readLine();
				}

				bf.close();

				System.out.println("Insertion correcte");
				break;
			case 2:
				printRecursive(th, "");
				break;
			case 3:
				System.out.println(th.hauteurInfSup());
				break;
			case 4:
				File repertoire = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare");
				File repertoire3;
				
				String[] listefichiers;

				int i;
				listefichiers = repertoire.list();
				debut = System.currentTimeMillis();
				Integer cpt1 = 0;
				for (i = 0; i < listefichiers.length; i++) {
					if (listefichiers[i].endsWith(".txt") == true) {

						System.out.println(listefichiers[i]);
						repertoire3 = new File(System.getProperty("user.home")+"/workspace/ProjetALGAV17_v2/fichier/Shakespeare/"+listefichiers[i]);
						
						th = insert(repertoire3,th,cpt1);
					}
				}
				System.out.println("Temps de insertion: "+(System.currentTimeMillis()-debut)+"ms");
				
				break;
			default:
				System.out.println("Erreur de saisie \n ");
				break;
			}

			System.out.println("\nVoulez-vous continuer(y or n) \n");
			ch = scan.next().charAt(0);
		} while (ch == 'Y' || ch == 'y');

	}

	public static void printRecursive(TrieHybrideNoeud th, String prefix) {
		if (th.getVal() >= 0) {
			System.out.println(prefix + th.getCle());
		}

		if (th.hasInf()) {
			printRecursive(th.getInf(), prefix);
		}

		if (th.hasEq()) {
			printRecursive(th.getEq(), prefix + th.getCle());
		}
		if (th.hasSup()) {
			printRecursive(th.getSup(), prefix);
		}

	}
	
	public static TrieHybrideNoeud insert(File repertoire, TrieHybrideNoeud th, int cpt) throws IOException {
		FileReader fr = null;
		fr = new FileReader(repertoire);
		BufferedReader bf = new BufferedReader(fr);

		String line = null;
		line = bf.readLine();

		while (line != null) {
			String[] decompose = line.split(" ");
			for (String string : decompose) {
//				ptn.insert(string);
				th = th.insertEq(string, cpt);
				cpt++;
			}
			line = bf.readLine();
		}

		bf.close();
		// System.out.println("Insertion correcte");
		return th;

	}

	


}
