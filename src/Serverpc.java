
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

                String pagina = process(f);
                System.out.println("condicion :" + pagina);
                String paginaHTML;
                //necesito abrir este puto HTML
                if (pagina.equals("")) {
                    paginaHTML
                            = "HTTP/1.1 200 OK\n\n"
                            +  "<html>\n"
                                + "<head>\n"
                                + "<meta charset=\\\"UTF-8\\\">\n"
                                + "</head>\n"
                                + "<body>\n"
                                + "<header> <h1>404 pagina no encontrada</h1> </header>\n"
                                + "Por: Gibran Raydan, Andres Galeano. ;(\n"
                                + "</html>";
                    output.flush();//vacia contenido
                    output.println(paginaHTML);
                    clientSocket.close();
                } else {
                    if (pagina.equals("noEncontrado")) {
                        pagina = "HTTP/1.1 200 OK\n\n"+
                                "<html>\n"
                                + "<head>\n"
                                + "<meta charset=\\\"UTF-8\\\">\n"
                                + "</head>\n"
                                + "<body>\n"
                                + "<header> <h1>Esta pagina ha sido bloqueada</h1> </header>\n"
                                + "Por: Gibran Raydan, Andres Galeano. ;(\n"
                                + "</html>";
                        output.flush();//vacia contenido
                        output.println(pagina);
                        clientSocket.close();
                    } else {
                        output.flush();//vacia contenido
                        output.println(pagina);
                        clientSocket.close();
                    }
                }
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
    public static String process(String f) throws FileNotFoundException, IOException {
        if (f != null) {
            if (f.equals("hello")) {
                return retornarPagina(f);
            } else {
                if (f.equals("redes")) {
                    return retornarPagina(f);
                } else {
                    if (f.equals("image")) {
                        return retornarPagina(f);
                    } else {
                        File archivo_texto = new File("C:\\Users\\FiJus\\Downloads\\serverpc\\src\\archivoDePalabras.txt");
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
                            return "noEncontrado";
                        }
                    }
                }
            }

        } else {
            System.out.println("No hay parametros");
            return retornarPagina("blabla");
        }
        return "";
    }

    public static String retornarPagina(String pagina) {
        try {
            File file = new File("C:/Users/FiJus/Downloads/serverpc/src/directorioConPaginasWeb" + "/" + pagina + ".html");
            Scanner sc = new Scanner(file);
            StringBuffer page = new StringBuffer("");
            int f = 0;
            while (sc.hasNextLine()) {
                if (f == 0) {
                    page.append("HTTP/1.1 200 OK\n\n");
                    f = 1;
                } else {
                    page.append(sc.nextLine()).append("\n");

                }
            }
            return page.toString();
        } catch (FileNotFoundException ex) {
            return "";
        }
    }

}
