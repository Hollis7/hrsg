package com.hdb.hrsguard.abse.scheme;

//import java.util.ArrayList;

import it.unisa.dia.gas.jpbc.Element;

public class BswabePrv {
	/*
	 * A private key
	 */
	public Element v;  /* v=g^ac */
	public Element sig_user; /*¦Ä_user */
	public Element y_user; /* y_user */
	
	public Element sig[];//(¦Ä_i)*
	public Element y[];//(y_i)*
	
	
	
	//ArrayList<BswabePrvComp> comps; /* BswabePrvComp */
}