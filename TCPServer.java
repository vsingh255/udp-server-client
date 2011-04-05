import java.io.*;

import java.net.*;

import java.util.*; 

class TCPServer {

    public static void main(String argv[]) throws Exception{
      
      Scanner console = new Scanner(System.in);

   String clientSentence;

   String capitalizedSentence;
   
   System.out.println("Enter server port number");
   
   int portNumber = console.nextInt();
   
   Socket clientSocket = new Socket($machineName$, portNumber);

   ServerSocket welcomeSocket = new ServerSocket

    (6789);

   while (true) {

     Socket connectionSocket = welcomeSocket.accept();

  BufferedReader inFromClient =

      new BufferedReader(new InputStreamReader(

       connectionSocket.getInputStream()));

  DataOutputStream outToClient =

      new DataOutputStream(

       connectionSocket.getOutputStream());

   clientSentence =inFromClient.readLine();

   capitalizedSentence=clientSentence.toUpperCase()+'\n';

     outToClient.writeBytes(capitalizedSentence);

   }

}

}
