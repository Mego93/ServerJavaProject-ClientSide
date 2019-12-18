package applis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliClient {
	private static final int PORT = 2500;
	private static final String HOST = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

		Socket socket = null;
		try {

			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			// Informe l'utilisateur de la connexion
			System.out
					.println("Connecté au serveur de réservation " + socket.getInetAddress() + ":" + socket.getPort());

			String line;
			do {// réception et affichage de la question provenant du service

				line = sin.readLine();
				if (line == null)
					break; // fermeture par le service

				System.out.println(line);
				// prompt d'invite à la saisie
				System.out.print("->");

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
		} catch (IOException e2) {
			;
		}
	}

}
