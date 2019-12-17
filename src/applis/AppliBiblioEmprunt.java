package applis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppliBiblioEmprunt {
		private static final int PORT = 2600;
		private static String HOST; 
	
	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));	
		
		// connexion
		
		System.out.print("Tapez l'url de connexion (par défaut Client:AdresseIP:PORT)");
		String url = args[0];
		try {urlValide(url);} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Socket socket = null;		
		try {
			
			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			
			// Informe l'utilisateur de la connexion
			System.out.println("Connecté au serveur d'emprunt " + socket.getInetAddress() + ":"+ socket.getPort());
			
			String line;
			do {// réception et affichage de la question provenant du service
				line = sin.readLine();
				if (line == null) break; // fermeture par le service
				System.out.println(line);
				// prompt d'invite à la saisie
				System.out.print("->");
				
				line = clavier.readLine();
				if (line.equals("")) 
					break; // fermeture par le client
				sout.println(line);
			} while (true);
			socket.close();
		}
		catch (IOException e) { System.err.println("Fin du service"); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { ; }		
	}

	private static void urlValide(String url) throws Exception { 
		String[] elts = url.split(":");
		if ((elts.length != 2) || 
				(!elts[0].equals("Client"))){
			throw new Exception("URL non respectée");
		}
		HOST = elts[1]; 
	}

}
