//The MIT License (MIT)
//
//Copyright (c) 2014 Julien ROMERO
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

//Extraits du code propose par Arduino

package bracelet;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import admin.Commande;
import admin.Vestiaire;
import client.Destinataire;

import com.example.bump.actions.BFList;
import com.example.bump.actions.Connexion;


public class SerialTest implements SerialPortEventListener {

	private String ip;
	private String monSC = "0";
	private String tonSC = "0";
	private final int PORT = 4444;

	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		"/dev/tty.usbmodem411", // Mac OS X
		"/dev/ttyUSB0", // Linux
		"COM3", // Windows
	};

	/**
	 * A BufferedReader which will be fed by a InputStreamReader 
	 * converting the bytes into characters 
	 * making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {  
				ip=input.readLine();
				if (ip.compareTo("")!=0) {
					System.out.println(ip);//Traitement de l'information recue
					//envoieBF();
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		} 
		// Ignore all the other eventTypes, but you should consider the other ones.
	} 

	private void envoieBF() {

		BFList bfList = new BFList("listeBF.txt");

		if (bfList.isBF(ip)) { //S'il est deja dans la liste
			//Verifier si commande boisson sinon vestiaire
			if (Commande.enCommande(ip)) {//S'il a une commande au bar, c'est qu'il souhaite la retirer
				Commande.remove(ip);
			} else {
				Vestiaire.TraitementVestiaire(ip);
			}
		} else { //On applique le protocole pour l'ajour d'un bf

			DataOutputStream dos = null;
			ObjectOutputStream oos = null;
			try {
				Verrous.enCours.lock();
				oos = new ObjectOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(
										new File("enCours.txt")
										)
								)
						);
				oos.writeObject(InetAddress.getByName(ip));
				oos.flush();
				Verrous.monSC.lock();
				dos = new DataOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(
										new File ("monSC.txt")
										)
								)
						);
				dos.writeInt(Integer.parseInt(monSC));
				dos.flush();
				dos.close();

				Verrous.tonSC.lock();
				dos = new DataOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(
										new File ("tonSC.txt")
										)
								)
						);
				dos.writeInt(Integer.parseInt(tonSC));
				dos.flush();

				Destinataire destinataire = new Destinataire(InetAddress.getByName(ip),PORT);
				destinataire.envoieObjet(new Connexion(Byte.parseByte(monSC),Byte.parseByte(tonSC),InetAddress.getByName(getIpAddr())));

				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oos.close();
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


	}

	private String getIpAddr() { //Retourne l'ip de l'ordinateur
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}


}
