package com.example.bump.actions;


public class TransmissionException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8388822235222596024L;
	private ErreurTransmission erreur;
	
	public TransmissionException (ErreurTransmission erreur) {
		this.erreur =erreur;
	}
	
	public ErreurTransmission getErreur () {
		return erreur;
	}
}
