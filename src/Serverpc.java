
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Serverpc {

    /**
     * Puerto
     */
    private final static int PORT = 5000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        try {
            //Socket de servidor para esperar peticiones de la red
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor> Servidor iniciado");
            System.out.println("Servidor> En espera de cliente...");
            //Socket de cliente
            Socket clientSocket;
            while (true) {
                //en espera de conexion, si existe la acepta
                clientSocket = serverSocket.accept();
                //Para leer lo que envie el cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //para imprimir datos de salida                
                PrintStream output = new PrintStream(clientSocket.getOutputStream());
                //se lee peticion del cliente
                String request = "e";
                String out = "";
                String palabras[];
                while (!request.equals("")) {
                    request = input.readLine();
                    if (request.contains("GET")) {
                        out = request;
                    }
                    System.out.println("" + request + "");
                }
                palabras = out.split("/");
                out = palabras[1];
                String out2[] = out.split(" ");
                String f = out2[0];
                System.out.println("Parametro :" + f);

                int condicion = process(f);
                System.out.println("condicion :" + condicion);

                
                
              //necesito abrir este puto HTML
                String paginaHTML = "<HTML>\n"
                        + "<HEAD>\n"
                        + "<TITLE>Hello World in HTML</TITLE>\n"
                        + "</HEAD>\n"
                        + "<BODY>\n"
                        + "<CENTER><H1>Hello World!</H1></CENTER>\n"
                        + "</BODY>\n"
                        + "</HTML>";
                
               
             
                String response = "HTTP/1.1 200 OK \n \n" + paginaHTML; 
                
                output.flush();//vacia contenido
                output.println(response);
                clientSocket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * procesa peticion del cliente y retorna resultado
     *
     * @param request peticion del cliente
     * @return int
     */
    public static int process(String f) throws FileNotFoundException, IOException {
        if (f != null) {
            if (f.equals("hello")) {
                return 1;
            } else {
                if (f.equals("redes")) {
                    return 2;
                } else {
                    if (f.equals("image")) {
                        return 3;
                    } else {
                        File archivo_texto = new File("C:\\Users\\Gibran\\Documents\\NetBeansProjects\\serverpc\\src\\archivoDePalabras.txt");
                        Scanner sc = new Scanner(archivo_texto);
                        String linea;
                        boolean aux = false;
                        while (sc.hasNextLine()) {
                            linea = sc.nextLine();
                            if (f.equals(linea)) {
                                aux = true;
                                break;
                            }
                        }
                        if (aux == true) {
                            System.out.println("palabra fea");
                            return 4;
                        }
                    }
                }
            }

        } else {
            System.out.println("No hay parametros");

        }
        return 0;

    }

}
