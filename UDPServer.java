
import java.io.*;
import java.net.*;

class UDPServer {

    public static void main(String args[]) throws Exception {
        //create server socket
        DatagramSocket serverSocket = new DatagramSocket(1337);

        //create data bytes
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        String filename = "smurfs";
        CutnCopyBinaryFile helper = new CutnCopyBinaryFile(filename);

        while (true) {
            //dump received packet into server socket
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            
            System.err.println("you have mail!");

            //dump data from packet to string "sentence"
            String sentence = new String(receivePacket.getData(),0,6);

            //hold IP Address of packet in "IPAddres"
            InetAddress IPAddress = receivePacket.getAddress();

            //hold the port number of packet in "port"
            int port = receivePacket.getPort();
            
            System.err.println("You are about to hit the giant block");

            if (sentence.equalsIgnoreCase("GET F1")) {
            	System.err.println("Get 1");
                String fn = helper.getfn1();
                sendstuff(fn, IPAddress, port, serverSocket);
            } else if (sentence.equals("GET H1")) {
            	System.err.println("Get Hash 1");
                sendstuff(helper.getHash1(), IPAddress, port, serverSocket);
            } else if (sentence.equals("GET F2")) {
            	System.err.println("Get 2");
                String fn = helper.getfn2();
                sendstuff(fn, IPAddress, port, serverSocket);
            } else if (sentence.equals("GET H2")) {
            	System.err.println("Get 2");
                sendstuff(helper.getHash2(), IPAddress, port, serverSocket);
            } else {
                System.err.println("bad input to server");
            }
            System.err.println("You made it");
        } //end of while loop
    }//end of main

    public static void sendstuff(String fn, InetAddress IPAddress, int port, DatagramSocket serverSocket) throws Exception {
        int noOfBytes = 0;
        long total =0;
        byte[] b = new byte[1024];
        FileInputStream fin = new FileInputStream(fn);
        while ((noOfBytes = fin.read(b)) != -1) {
            DatagramPacket sendPacket = new DatagramPacket(b, b.length, IPAddress, port);
            serverSocket.send(sendPacket);
            //total += noOfBytes;
            //System.err.println("total :"+total);
        }
    }

    public static void sendstuff(byte[] stuff, InetAddress IPAddress, int port, DatagramSocket serverSocket) throws Exception {
        int noOfBytes = 0;
        byte[] b = stuff;
        DatagramPacket sendPacket = new DatagramPacket(b, b.length, IPAddress, port);
        serverSocket.send(sendPacket);

    }
}//end of class

