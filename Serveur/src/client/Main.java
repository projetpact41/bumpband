package client;

import java.net.InetAddress;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			EnvoieMessage eM = new EnvoieMessage(InetAddress.getByName("192.168.1.10"),4445);
			eM.Communication();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
