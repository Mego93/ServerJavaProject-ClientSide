/**
 * Classe d'application du poste de Bibliothèque (Retour)
 * @author VO Thierry & VYAS Ishan
 * @version 1.5
 */
package applis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import codage.Decodage;

public class AppliBiblioRetour {
	private static final int PORT = 2700;
	private static final String HOST = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

		Socket socket = null;
		try {

			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			System.out
					.println("Connecté au serveur de réservation " + socket.getInetAddress() + ":" + socket.getPort());

			String line;
			do {

				line = sin.readLine();
				if (line == null)
					break; // fermeture par le service
				
				// On décode le message reçu (les #n décodés en \n)
				System.out.println(Decodage.decoder(line));
				System.out.print("=>");
				
				line = clavier.readLine();
				if (line.equals(""))
					break; // fermeture par le client
				sout.println(line);
				
			} while (true);
			socket.close();
		} catch (IOException e) {
			System.err.println("Fin du service");
		}	
		// Refermer dans tous les cas la socket
		try {
			if (socket != null)
				socket.close();
		} catch (IOException e2) {}
	}

}

