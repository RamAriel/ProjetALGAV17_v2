package hybride;

public class TrieHybrideNoeud {
	private String cle;
	private TrieHybrideNoeud inf;
	private TrieHybrideNoeud eq;
	private TrieHybrideNoeud sup;
	private Integer val;

	/*
	 * constructeur du trieH vide
	 */
	public TrieHybrideNoeud() {
		this.cle = "";
		this.val = -1;
	}

	/*
	 * Renvoie le trie hybride construit avec le caracter c, et le valeur v qu'a
	 * comme sous arbre inf, eq et sup les tries hybrides correspondants
	 */

	public TrieHybrideNoeud(String cle, TrieHybrideNoeud inf, TrieHybrideNoeud eq, TrieHybrideNoeud sup, Integer val) {
		this.cle = cle;
		this.inf = inf;
		this.eq = eq;
		this.sup = sup;
		this.val = val;
	}

	public TrieHybrideNoeud(String c, Integer v) {
		this.insert(c, v);
	}

	/*
	 * getters et setters
	 */
	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public TrieHybrideNoeud getInf() {
		return inf;
	}

	public void setInf(TrieHybrideNoeud inf) {
		this.inf = inf;
	}

	public TrieHybrideNoeud getEq() {
		return eq;
	}

	public void setEq(TrieHybrideNoeud eq) {
		this.eq = eq;
	}

	public TrieHybrideNoeud getSup() {
		return sup;
	}

	public void setSup(TrieHybrideNoeud sup) {
		this.sup = sup;
	}

	public Integer getVal() {
		return val;
	}

	public void setVal(Integer val) {
		this.val = val;
	}
	// ***Primitives***

	/* Renvoie vrai ssi TrieH est vide.""" */
	public Boolean estVide() {
		return (this.cle == "");
	}

	/* Renvoie la longeur du clé """ */
	public Integer lgeur(String c) {
		return (c.length());
	}

	/* Renvoie le premier caractere de la cle.""" */
	public String prem(String c) {
		return (c.substring(0, 1));
	}

	/* Renvoie la cle privee de son premier caractere.""" */
	public String reste(String c) {
		return (c.substring(1));
	}

	public void insert(String c, Integer v) {

		if (this.estVide()) {
			if (c.length() == 1) {
				this.insert(prem(c), new TrieHybrideNoeud(), new TrieHybrideNoeud(), new TrieHybrideNoeud(), v);
			} else {
				TrieHybrideNoeud theq = new TrieHybrideNoeud();
				theq.insert(reste(c), v);
				this.insert(prem(c), new TrieHybrideNoeud(), theq, new TrieHybrideNoeud(), -1);
			}

		} else {
			String p = prem(c);
			if (p.charAt(0) < this.getCle().charAt(0)) {
				this.getInf().insert(c, v);
			} else if (p.charAt(0) > this.getCle().charAt(0)) {
				this.getSup().insert(c, v);
			} else {
				if (c.length() > 1)
					this.getEq().insert(reste(c), v);
			}
		}
	}

	public TrieHybrideNoeud insertEq(String c, Integer v) {
		TrieHybrideNoeud nth = new TrieHybrideNoeud();
		if (this.estVide()) {
			if (c.length() == 1) {
				this.insert(prem(c), new TrieHybrideNoeud(), new TrieHybrideNoeud(), new TrieHybrideNoeud(), v);
				nth = this;
			} else {
				TrieHybrideNoeud theq = new TrieHybrideNoeud();
				theq.insertEq(reste(c), v);
				this.insert(prem(c), new TrieHybrideNoeud(), theq, new TrieHybrideNoeud(), -1);
				nth = this;
			}

		} else {
			String p = prem(c);
			if (p.charAt(0) < this.getCle().charAt(0)) {
				int hFI = this.getInf().hauteurInfSup();
				// insertion normal dans une nouveau arbre
				nth.insert(this.getCle(), (this.getInf().insertEq(c, v)), this.getEq(), this.getSup(), this.getVal());
				int hInf = nth.getInf().hauteurInfSup();
				int hSup = nth.getSup().hauteurInfSup();
				if (hInf - hSup >= 2) {
					// rotation
					if (hFI-nth.getInf().getInf().hauteurInfSup()>= 1) {
						nth = nth.rotationGauche(nth.getInf(),nth);
					}
					nth = nth.rotationDroit(nth,nth);
				}

			} else if (p.charAt(0) > this.getCle().charAt(0)) {
				int hFS = this.getSup().hauteurInfSup();
				// insertion normal dans une nouveau arbre
				nth.insert(this.getCle(), this.getInf(), this.getEq(), (this.getSup().insertEq(c, v)), this.getVal());
				int hInf = nth.getInf().hauteurInfSup();
				int hSup = nth.getSup().hauteurInfSup();
				if (hSup - hInf >= 2) {
					// rotation
					if (hFS - nth.getSup().getInf().hauteurInfSup() >= 1) {
						nth = nth.rotationDroit(nth.getSup(),nth);
					}
					nth = nth.rotationGauche(nth,nth);
				}
			} else if (c.length() > 1) {
				this.getEq().insertEq(reste(c), v);
				nth = this;
			}
		}
		return nth;
	}

	private TrieHybrideNoeud rotationDroit(TrieHybrideNoeud t, TrieHybrideNoeud nt) {
		TrieHybrideNoeud x = t;
		TrieHybrideNoeud y = t.getInf();

		x.setInf(new TrieHybrideNoeud());
		y.setSup(x);
		
		if (t==nt){
			nt=y;
		}else{
			nt.setSup(y);			
		}
		return nt;
	}

	private TrieHybrideNoeud rotationGauche(TrieHybrideNoeud t, TrieHybrideNoeud nt) {
		TrieHybrideNoeud x = t;
		TrieHybrideNoeud y = t.getSup();

		x.setSup(new TrieHybrideNoeud());
		y.setInf(x);
		if (t==nt){
			nt=y;
		}else{
			nt.setInf(y);			
		}
		return nt;
	}

	private void insert(String c, TrieHybrideNoeud inf, TrieHybrideNoeud eq, TrieHybrideNoeud sup, Integer v) {
		this.cle = c;
		this.inf = inf;
		this.eq = eq;
		this.sup = sup;
		this.val = v;
	}

	public Boolean hasInf() {
		return (this.inf != null);
	}

	public Boolean hasEq() {
		return (this.eq != null);
	}

	public Boolean hasSup() {
		return (this.sup != null);
	}

	public int hauteurInfSup() {
		if (this.getCle() == ""||this.getCle() == null)
			return 0;
		return Math.max(this.getInf().hauteurInfSup(), this.getSup().hauteurInfSup()) + 1;
	}

	public void  print_trie(int depth, String prev){
		if (this.estVide())
			System.out.println("");
		if (depth==0)
			System.out.println("");
		if (depth==0)
			System.out.print("└── ");
		System.out.println(this.getCle());
		if (this.getVal()==-1){
			System.out.println("");
		}else{
			System.out.println(", #{PRINT_EOW}\n");
		}
	}
	/*
	 * 
# prints the trie for debug purposes
  def print_trie(depth = 0, prev = [' '])

    @value.nil? ? print("\n") : print(", #{PRINT_EOW}\n")
    print_depth_path(depth, prev)
    print("├── ")
    @inf_child.nil? ? print("\n") : @inf_child.print_trie(depth + 1, prev + ['│'])
    print_depth_path(depth, prev)
    print("├── ")
    @eq_child.nil? ? print("\n") : @eq_child.print_trie(depth + 1, prev + ['│'])
    print_depth_path(depth, prev)
    print("└── ")
    @sup_child.nil? ? print("\n") : @sup_child.print_trie(depth + 1, prev + [' '])
    "Hope you enjoyed this representation :)"
  end

  def print_depth_path(depth, prev)
    (0..depth).each do |h|
      print("#{prev[h]}  ")
    end
  end
  */
}
