package patricia;

public class Contenu {
	private String elem;
	private PatriciaTrieNoeud fils;
	static int asciiFDM = 3;
	
	public Contenu(String elem, PatriciaTrieNoeud fils) {
		this.elem = elem;
		this.fils = fils;
	}

	public Contenu(String elem) {
		this.elem = elem;
		this.fils = new PatriciaTrieNoeud();
	}

	public String getElem() {
		return elem;
	}

	public void setElem(String elem) {
		this.elem = elem;
	}

	public PatriciaTrieNoeud getFils() {
		return fils;
	}

	public void setFils(PatriciaTrieNoeud fils) {
		this.fils = fils;
	}

	public boolean isFinDeMot() {

		return this.getElem().indexOf(Character.toString((char)asciiFDM))!=-1;
	}
	
	
}
