package com.flkoliv.subtitles.beans;

public class Phrase {
	private int numero;
	private String minutageDebut;
	private String minutageFin;
	private String texteOriginal;
	private String texteTraduit;
	
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getMinutageDebut() {
		return minutageDebut;
	}
	public void setMinutageDebut(String minutageDebut) {
		this.minutageDebut = minutageDebut;
	}
	public String getMinutageFin() {
		return minutageFin;
	}
	public void setMinutageFin(String minutageFin) {
		this.minutageFin = minutageFin;
	}
	public String getTexteOriginal() {
		return texteOriginal;
	}
	public void setTexteOriginal(String texteOriginal) {
		this.texteOriginal = texteOriginal;
	}
	public String getTexteTraduit() {
		return texteTraduit;
	}
	public void setTexteTraduit(String texteTraduit) {
		this.texteTraduit = texteTraduit;
	}
	
	public String toString() {
		return numero + "\n" + minutageDebut + " --> " + minutageFin + "\n" + texteOriginal;
	}
}
