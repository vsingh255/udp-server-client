import java.io.*; 
import java.net.*; 
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.lang.*;

class UDPClient { 
	public static void main(String args[]) throws Exception 
	{ 

		//User Input
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

		//Create client side UDP Socket
		DatagramSocket clientSocket = new DatagramSocket(); 

		//Get Host IP address
		InetAddress IPAddress = InetAddress.getByName("localhost"); 

		//create data Bytes
		byte[] sendData = new byte[1024]; 
		byte[] receiveData = new byte[1024]; 
		byte[] sendDataH2 = new byte[1024];
		byte[] sendDataP1 = new byte[1024]; 
		byte[] sendDataP2 = new byte[1024];
		byte[] sendDataH1 = new byte[1024];
		byte[] combinedBytes = new byte[214748364];  
		
		//if the first packet passed includes the filesize, then this can be initialized to the exact size of the file, as of now, there are trailing zero'es

		//strings server expects	
		String requestPart1 = "GET F1" , requestHash1 = "GET H1", 
		requestPart2 = "GET F2", requestHash2 = "GET H2";

		//convert strings to bytes to sequence of bytes to send to server
		//stores byte sequence to respective byte[]
		sendDataP1 =requestPart1.getBytes();
		sendDataH1 =requestHash1.getBytes();
		sendDataP2 =requestPart2.getBytes();
		sendDataH1 =requestHash2.getBytes();

		//create packer for data "sendPacket"
		//passing in sendData, length,IP and port number (hardcoded) 
		DatagramPacket sendRequestPart1 = new DatagramPacket (sendDataP1, sendDataP1.length, IPAddress, 1337);
		DatagramPacket sendRequestPart2 = new DatagramPacket (sendDataP2, sendDataP2.length, IPAddress, 1337);
		DatagramPacket sendRequestHashPart1 = new DatagramPacket (sendDataH1, sendDataH1.length, IPAddress, 1337);
		DatagramPacket sendRequestHashPart2 = new DatagramPacket (sendDataH2, sendDataH2.length, IPAddress, 1337);
	
		//create space for expected received packet
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		DatagramPacket receivePacket2 = new DatagramPacket(receiveData, receiveData.length); 

		boolean fin = false;
		File f1 = new File("blahp1.mp3");
		File f2 = new File("blahp2.mp3");
		File fTotal = new File("blah.mp3");
		int lengthChecker = 1024;
		int counter = 0;
		
		while ( !fin)
		{
			System.out.println("you are in while loop");
			System.out.println(new String(sendRequestPart1.getData()));
			clientSocket.send(sendRequestPart1);
			System.out.println("you have sent request Part1");
			// UDP Socket wait
			//receives datagram packet from socket throws into buffer, also had IP and port info.
			while(counter!=133120)     deliberately erroring this line to draw attention to the fact that the hard coded line needs fixed
			{
				clientSocket.receive(receivePacket);
				lengthChecker = receivePacket.getLength();
				System.out.println(receivePacket.getLength());
				System.out.println(counter);
				
				System.arraycopy(receivePacket.getData(), 0, combinedBytes, counter, receivePacket.getLength());
				counter = counter + lengthChecker;
			}
			
			System.out.println("!!"+combinedBytes.length);
				System.out.println("you have received Part 1");
			
			FileOutputStream fos = new FileOutputStream(f1);
			//BufferedOutputStream bos = new BufferedOutputStream(fos);
			BufferedOutputStream bos = null;
			bos = new BufferedOutputStream(new FileOutputStream(f1));
			//This will write the data in the bufferoutputstream to the fileoutputstream fos
			//to a file named blah.mp3			
			Thread.currentThread().sleep(1000);

			fos.write(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println("You have writtin to file");
									
			byte[] remoteHash = receivePacket.getData();

			// HERE I WILL PUT CODE TO CHECK HASH OF THE PART
			byte[] byteArray1 = getHash("blahp1.mp3");
			boolean blnResult = Arrays.equals(byteArray1,remoteHash);
			if (blnResult == true){
				System.out.println("yay, hashes are the same no corruption!");
			}
			else {
				System.out.println("hashes aren't equal too bad exiting");
			}
			
			clientSocket.send(sendRequestPart2);
			System.out.println("you have sent request Part2");
			//receives datagram packet from socket throws into buffer, also had IP and port info.
			
			
			while(receivePacket2.getLength()==1024)
			{
				clientSocket.receive(receivePacket2);
				lengthChecker = receivePacket2.getLength();
				System.out.println(receivePacket2.getLength());
				System.out.println(counter);
				
				System.arraycopy(receivePacket2.getData(), 0, combinedBytes, counter, receivePacket2.getLength());
				counter = counter + lengthChecker;
			}
			System.out.println("you have received Part2");

			FileOutputStream fos2 = new FileOutputStream(f2);
                        BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
            //+++++++++++++++++++++++THIS IS WHERE WE NEED TO MAKE IT WAIT
            //++++++++++++++++++++++++TO FILL BUFFER
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        
                        Thread.currentThread().sleep(1000);
                        fos2.write(receivePacket2.getData(), 0, receivePacket2.getLength());
			
			byte[] remoteHash2 = receivePacket2.getData();
			
			//COMBINE THE TWO PARTS
			
//			System.arraycopy(receivePacket.getData(), 0, combinedBytes, 0, receivePacket.getLength());
//			System.arraycopy(receivePacket2.getData(), 0, combinedBytes, 1024, receivePacket2.getLength());
//			
			FileOutputStream fos3 = new FileOutputStream(fTotal);
			fos3.write(combinedBytes, 0, combinedBytes.length);
			// HERE I WILL PUT CODE TO CHECK HASH OF THE PART
			byte[] byteArray2 = getHash("blahp1.mp3");
			boolean blnResult2 = Arrays.equals(byteArray2,remoteHash2);
			if (blnResult2 == true)
				System.out.print("yay, hashes are the same no corruption!");
			else 
				System.out.print("hashes aren't equal too bad exiting");


						fin = true;//easy exit yay
		}


		boolean finished = false;
		while ( !finished ) 
		{

			//double noofpackets=Math.ceil(((int) (new File("blarg.mp3")).length())/packetsize);
			//for(double i=0;i<noofpackets+1;i++)
			//{
			//InputStream IS = clientSocket.getOutputStream();
			//byte[] mybytearray = new byte[packetsize];
			//int bytesRead = IS.read(mybytearray, 0,mybytearray.length );
			//System.out.println("Packet:"+(i+1));
			//bos.write(mybytearray, 0,mybytearray.length);
			//}
			
		}//end of while loop

		//========================= code to put the files together ========
		//decodes data into clients charset and dumps to "modifiedSentence"
		//String modifiedSentence = new String(receivePacket.getData()); 
		//=================================================================

		clientSocket.close(); 
	} 
	public static byte[] getHash(String filename) {

		byte[] digest = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream is = new FileInputStream(filename);

			try {
				is = new DigestInputStream(is, md);
				// read stream to EOF as normal...

			} finally {
				is.close();
			}
			digest = md.digest();
			return digest;

		} catch (IOException IOE) {
			System.err.println("Oops io error " + IOE);

		} catch (NoSuchAlgorithmException NSAE) {
			System.err.println("Oops no algorithm " + NSAE);
		}
		return digest;
	}
} 
