package servlets;

import java.util.Arrays;

public class Signatures  {
	private int[] rg;
	private int[] by;
	private int[] wb;
	
	
	public Signatures (int[] r,int[] b,int[] w){
		
		this.rg=r;
		this.by=b;
		this.wb=w;
	}
public Signatures(String r, String b, String w) {
		// TODO Auto-generated constructor stub
this.rg=Utiles.tointTabRg(r);
this.by=Utiles.tointTabWb(b);
this.wb=Utiles.tointTabWb(w);
 
	}

public int  toTab(String s){
	
	return 0;
}
public void setRg(int[] rg){
	this.rg=rg;

}
public void setBy(int[] by){
	this.by=by;


}
public void setWb(int[] wb){
	this.wb=wb;


}
public int[]getRg() {
	return this.rg;
}
public int[]getBy() {
	return this.by;
}
public int[]getWb() {
	return this.wb;
}

}