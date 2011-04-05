import java.io.*;

import java.net.*;

import java.util.*; 

class TCPClient {

public static void main(String argv[]) throws Exception

{
   Scanner console = new Scanner(System.in);
   
   String sentence;

   String modifiedSentence;

   BufferedReader inFromUser = new BufferedReader(

        new InputStreamReader(System.in));

   System.out.println("Would you like to start? (Y/N)");
   
   char answer = console.next().charAt(0);
   
   boolean start_flag = false;
   
   if((answer == 'Y') || (answer == 'y')){
     
     start_flag = true;}
   
    while(start_flag == false){
      
      System.out.println("Would you like to start? (Y/N)");
      
      answer = console.next().charAt(0);
      
    if((answer == 'Y') || (answer == 'y')){
      
     start_flag = true;}}
      
   Socket clientSocket = new Socket("lapper.towson.edu", 6789);

   DataOutputStream outToServer = new DataOutputStream(

        clientSocket.getOutputStream());

   BufferedReader inFromServer =

        new BufferedReader(new InputStreamReader(

                clientSocket.getInputStream()));

   sentence = inFromUser.readLine();

   outToServer.writeBytes(sentence+'\n');

   modifiedSentence =inFromServer.readLine();

   System.out.println("FROM SERVER: "+modifiedSentence);

   clientSocket.close();

}

}
