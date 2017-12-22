package patricia;

import java.util.ArrayList;

/*
 * C'est un noeud du Patricia trie
*/
public class PatriciaTrieNoeud {
	private Contenu [] contenu;
	static int asciiFDM = 3;  
	static String finDeMot= Character.toString((char)asciiFDM);
	
	public PatriciaTrieNoeud() {	
		this.contenu= null;
	}

	public Contenu getContenu(int index){
		return contenu[index];
	}

	public void setContenu(String elem, int index) {
		if(this.contenu==null){
			this.contenu=new Contenu[128]; 
		}
		
		this.contenu[index] = new Contenu(elem);
	}
	
	private void viderContenu(int index){
		this.contenu[index]=null;
	}

	private void setContenu(String restElem, PatriciaTrieNoeud fils, int i) {
		if(this.contenu==null){
			this.contenu=new Contenu[128]; 
		}
		this.contenu[i] = new Contenu(restElem, fils);
		
	}

	public void setFinMot() {
		this.getContenu(3).setElem(finDeMot);
	}

	public int getNbrCaracterCoincident(String mot, int index){
		int nbrCaractCoinc=0;
		while (nbrCaractCoinc<mot.length() && nbrCaractCoinc<this.getContenu(index).getElem().length()) {
			if(mot.charAt(nbrCaractCoinc)!= this.getContenu(index).getElem().charAt(nbrCaractCoinc)) break;
			nbrCaractCoinc++;
		}
		return nbrCaractCoinc;
		
	}

	/* Renvoie le premier caractere de la cle.""" */
	public char prem(String c){
		return(c.charAt(0));
	}

	public void suppression(String mot){
		if (mot.length() <= 0) {
			return;
		}
		
		this.suppressionRec(mot+finDeMot);
	}
	
	public Boolean suppressionRec(String mot) {
		boolean supp=false;
		boolean propagar=false;
		if(this.contenu==null) 
			return supp;	
		int index = (int) prem(mot);
		Contenu courrentCont = this.getContenu(index);
		if(courrentCont==null) 
			return supp;
		int nbrCaracCoinc = this.getNbrCaracterCoincident(mot, index);
		if (nbrCaracCoinc<courrentCont.getElem().length()) 
			return supp;
		if ((nbrCaracCoinc==mot.length() && nbrCaracCoinc==courrentCont.getElem().length() && courrentCont.isFinDeMot() )) {
			//supprimer
			this.viderContenu(index);
			propagar=true;

		}else{
			//supp rec
			String restMot = mot.substring(nbrCaracCoinc);
			supp=this.getContenu(index).getFils().suppressionRec(restMot);
			Contenu cont = null;
			if (supp){
				int cpt = 0;
				for (int i = 0; i < this.contenu.length; i++) {
					if(this.getContenu(index).getFils().getContenu(i)!=null) {
						cpt++;
						cont = this.getContenu(index).getFils().getContenu(i);
					}
					if (cpt>1)
						break;
				}
				if(cpt==0) {
					this.contenu=null;
					propagar=true;
				}
					
				if(cpt==1){
					this.getContenu(index).setElem(this.getContenu(index).getElem()+cont.getElem());
					this.getContenu(index).setFils(cont.getFils());
					propagar=true;
				}
					
				
			}
		}
		
		return propagar;
	}

	void insertRec(String mot){
		int index = (int) prem(mot);
		Contenu courrentCont = this.getContenu(index);
		
		if(courrentCont==null) {
			this.setContenu(mot+finDeMot, index);
			return;
		}
		int nbrCaracCoinc = this.getNbrCaracterCoincident(mot, index);
		
		if ((nbrCaracCoinc==mot.length() && nbrCaracCoinc==courrentCont.getElem().length()-1 && courrentCont.isFinDeMot() )) {
			//element repete
			return;
		}
		
		if ((nbrCaracCoinc==mot.length() && 
				((nbrCaracCoinc<courrentCont.getElem().length() && !courrentCont.isFinDeMot()) ||
				(nbrCaracCoinc<courrentCont.getElem().length()-1 && courrentCont.isFinDeMot())
				))) {	
			// mon mot a inserer est plus petit que mon element actuel
			// je dois transformer l'element en deux noued, le prefix(maintenant un mot) sera le pere du reste 
			// de l'element actuel et l'ancien fils de mon element vais etre le fils du reste
			String restElem = courrentCont.getElem().substring(nbrCaracCoinc);
			PatriciaTrieNoeud ptn = new PatriciaTrieNoeud();
			ptn.setContenu(finDeMot, asciiFDM);
			int indexReste = restElem.charAt(0);
			ptn.setContenu(restElem,courrentCont.getFils() ,indexReste);

			this.getContenu(index).setFils(ptn);
			this.getContenu(index).setElem(mot.substring(0,nbrCaracCoinc));
		}
		String restMot = mot.substring(nbrCaracCoinc);

		if (nbrCaracCoinc<mot.length() && nbrCaracCoinc == courrentCont.getElem().length()-1 && courrentCont.isFinDeMot()) {
			this.supFinMot(index);
			this.getContenu(index).getFils().setContenu(finDeMot, asciiFDM);
//			this.setContenu(finDeMot, asciiFDM);
			this.getContenu(index).getFils().insertRec(restMot);
		}
		
		if (nbrCaracCoinc<mot.length() && nbrCaracCoinc == courrentCont.getElem().length() && !courrentCont.isFinDeMot()) {
			//busco el index del resto de mot
			//inserto el resto de mot en el hijo correspondiente
			int indexRestMot = restMot.charAt(0);
			if (courrentCont.getFils().getContenu(indexRestMot)==null){
				this.getContenu(index).getFils().setContenu(restMot+finDeMot, indexRestMot);
			}else{
				this.getContenu(index).getFils().insertRec(restMot);	
			}
			
		}
		if(nbrCaracCoinc<mot.length() && (
				(nbrCaracCoinc<courrentCont.getElem().length()-1 && courrentCont.isFinDeMot()) ||
				(nbrCaracCoinc<courrentCont.getElem().length() && !courrentCont.isFinDeMot())
				)){
			
			PatriciaTrieNoeud ptn = new PatriciaTrieNoeud();
			String restElem = courrentCont.getElem().substring(nbrCaracCoinc);
			int indexReste = restElem.charAt(0);
			ptn.setContenu(restElem,courrentCont.getFils() ,indexReste);

			this.getContenu(index).setFils(ptn);
			this.getContenu(index).setElem(mot.substring(0,nbrCaracCoinc));
			this.getContenu(index).getFils().insertRec(restMot);
			return;
		}		
		return;
	}

	public Boolean recherche(String mot){
		if (mot.length() <= 0) {
			return false;
		}
		
		return this.rechercheRec(mot+finDeMot);
	}
	
	public int prefixe(String mot) {
		if (mot.length() <= 0) {
			return 0;
		}
		
		return this.prefixeRec(mot);
	}

	private int prefixeRec(String mot) {
		if (this.contenu==null) return 0;
		int index = (int) prem(mot);
		Contenu courrentCont = this.getContenu(index);
		
		if(courrentCont==null) {
			return 0;
		}
		
		int nbrCaracCoinc = this.getNbrCaracterCoincident(mot, index);
		
		if (nbrCaracCoinc< mot.length() && nbrCaracCoinc<courrentCont.getElem().length()) {
			return 0;
		}
		
		if (nbrCaracCoinc == mot.length() && nbrCaracCoinc <= courrentCont.getElem().length() 
				&& courrentCont.getElem().indexOf(finDeMot)!=-1/* fin de mot*/ ) {
			return 1;
		}
		
		if (nbrCaracCoinc == mot.length()){
			return this.getContenu(index).getFils().comptageMots();
		}
		String restMot = mot.substring(nbrCaracCoinc);
		return courrentCont.getFils().prefixeRec(restMot);

	}

	public Boolean rechercheRec(String mot) {
		if (this.contenu==null) return false;
		int index = (int) prem(mot);
		Contenu courrentCont = this.getContenu(index);
		
		if(courrentCont==null) {
			return false;
		}
		
		int nbrCaracCoinc = this.getNbrCaracterCoincident(mot, index);

		if (nbrCaracCoinc == mot.length() && nbrCaracCoinc == courrentCont.getElem().length()) {
			return true;
		}
		
		if (nbrCaracCoinc<mot.length() && nbrCaracCoinc<courrentCont.getElem().length()) {
			return false;
		}
		
		String restMot = mot.substring(nbrCaracCoinc);
		return courrentCont.getFils().rechercheRec(restMot);
	}

	private void supFinMot(int index) {
		this.getContenu(index).setElem(this.getContenu(index).getElem().substring(0,this.getContenu(index).getElem().length()-1));
		
	}

	public void printRecursive(int niv){
		if (this.contenu==null) return;
		niv++;
		String spc="";
		for (int i = niv; i > 1 ; i--) {
			spc=spc+"--";
		}
		
		for(int i = 0 ; i < 128 ; i++){
			if(this.getContenu(i)!=null){
				System.out.println(spc+this.getContenu(i).getElem().toString());
				this.getContenu(i).getFils().printRecursive(niv);				
			}			
		}
		spc=spc+" ";
	}

	public int comptageMots() {
		if (this.contenu==null) return 0;
		int cpt=0;
		for (int i = 0; i < this.contenu.length; i++) {
			if(this.getContenu(i)==null){
				continue;
			}
			if(this.getContenu(i).getElem().indexOf(finDeMot)!=-1){
				cpt++;
			}else{
				cpt=cpt+this.getContenu(i).getFils().comptageMots();
			}
		}
		return cpt;
		
	}

	public ArrayList<String> listeMots(String prefix) {
		if (this.contenu==null) return null;
		ArrayList<String> res = new ArrayList<String> ();
		for (int i = 0; i < this.contenu.length; i++) {
			if(this.getContenu(i)==null){
				continue;
			}
			if(this.getContenu(i).getElem().indexOf(finDeMot)!=-1){
				res.add(prefix+this.getContenu(i).getElem());
				continue;
			}
			
			res.addAll(this.getContenu(i).getFils().listeMots(prefix+this.getContenu(i).getElem()));
					
		}
		return res;
	}

	public int comptageNil(int cpt) {
		if (this.contenu==null) return 0;
		for (int i = 0; i < this.contenu.length; i++) {
			if(this.getContenu(i)==null){
				cpt++;
				continue;
			}
			cpt=cpt+this.getContenu(i).getFils().comptageNil(0);
		}
		return cpt;
	}

	public void insert(String string) {
		if(this.contenu==null){
			this.contenu=new Contenu[128]; 
		}
		this.insertRec(string);
		
	}

	public int hauteur() {
		if (this.contenu==null) return 0;
		int maxRes=0;
		int res;
		for (int i = 0; i < this.contenu.length; i++) {
			if(this.getContenu(i)==null){
				continue;
			}
			if(this.getContenu(i).getElem().indexOf(finDeMot)!=-1){
				if(maxRes<1) maxRes=1;
				continue;
			}
			res= this.getContenu(i).getFils().hauteur();
			if(maxRes<res+1) maxRes=res+1;
					
		}
		return maxRes;
	}

	public int profondeurMoyenne() {
		if (this.contenu == null)
			return 0;
		int[] result = this.addProfondeur(0);
		if (result[0] > 0)
			return result[1] / result[0];
		return 0;
	}

	private int[] addProfondeur(int profPere) {
		if (this.contenu==null) return new int[] {0, 0};
		int addProf=0;
		int cptFeuille=0;
		int[] result;
		boolean isFeuille = true;
		for (int i = 0; i < this.contenu.length; i++) {
			if(this.getContenu(i)==null){
				continue;
			}
			if(this.getContenu(i).getElem().indexOf(finDeMot)!=-1){
				continue;
			}
			isFeuille = false;
			result = this.getContenu(i).getFils().addProfondeur(profPere+1);
			cptFeuille=cptFeuille+result[0];
			addProf=addProf + result[1];
					
		}
		if(isFeuille){
			addProf=addProf+profPere+1;
			cptFeuille=1;
		}
			
		return new int[] {cptFeuille, addProf};
		
	}

}
