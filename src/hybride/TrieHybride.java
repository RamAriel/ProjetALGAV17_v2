package hybride;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TrieHybride {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);

		TrieHybrideNoeud th = new TrieHybrideNoeud();
		System.out.println("Trie Hybride Test\n");
		char ch;
		do {
			System.out.println("\nTrie Hybride Operations\n");
			System.out.println("1. Insertion avec equilibrage");
			System.out.println("2. Impression ");
			System.out.println("7. Calcul de l'hauteur sauf le branche egal");

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
			case 7:
				System.out.println(th.hauteurInfSup());
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
	


}
