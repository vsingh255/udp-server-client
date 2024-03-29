/*
Copy binary file using Streams
This example shows how to copy a binary file using Java FileInputStream
and FileOutputStream classes. If you want to copy text file use
FileReader and FileWriter classes instead of FileInputStream and
FileOutputStream classes.
 */

import java.io.*;
import java.security.*;

public class CutnCopyBinaryFile {

	// Get the number of bytes in the file
	FileInputStream fin;
	FileOutputStream fout, fout2;
	byte[] b = new byte[1024];
	int noOfBytes = 0;
	long tot_no_bytes = 0;
	String strSourceFile, strDestinationFile, strDestinationFile2;
	File file, temp1,temp2;
	long length, fs_half;
	int length1,length2;

	CutnCopyBinaryFile(String filename) {

		file = new File(filename + ".mp3");
		length = file.length();
		fs_half = (long) (length / 2);
		strSourceFile = filename + ".mp3";
		strDestinationFile = filename + "_part1.mp3";
		strDestinationFile2 = filename + "_part2.mp3";
		try {
			//create FileInputStream object for source file
			fin = new FileInputStream(strSourceFile);

			//create FileOutputStream object for destination file
			fout = new FileOutputStream(strDestinationFile);
			fout2 = new FileOutputStream(strDestinationFile2);


			System.out.println("Copying file using streams");

			//read bytes from source file and write to destination file
			while (((noOfBytes = fin.read(b)) != -1) && (tot_no_bytes < fs_half)) {
				fout.write(b, 0, noOfBytes);
				tot_no_bytes += noOfBytes;
			}
			while ((noOfBytes = fin.read(b)) != -1) {
				fout2.write(b, 0, noOfBytes);
			}

			System.out.println("File copied!");

			//close the streams
			fin.close();
			fout.close();
			fout2.close();

		} catch (FileNotFoundException fnf) {
			System.err.println("Specified file not found :" + fnf);
		} catch (IOException ioe) {
			System.err.println("Error while copying file :" + ioe);
		}
	}

	public String getfn1() {
		return strDestinationFile;
	}

	public String getfn2() {
		return strDestinationFile2;
	}

	public byte[] getHash1() {

		byte[] digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream is = new FileInputStream(strDestinationFile);
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

	public byte[] getHash2() {

		byte[] digest = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream is = new FileInputStream(strDestinationFile2);

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
	/* The method getLengthOfParts, takes the parts in order to construct their respective byte arrays.
	   Then it gets the length of each byte array and stores the values in "length1" and "length2" */
	public void  getLengthOfParts(){

		byte[] temp1,temp2;
		temp1 = strDestinationFile.getBytes();
		temp2 = strDestinationFile2.getBytes();
		length1 = temp1.length;
		length2 = temp2.length;
	}
	/* The method getLength1, returns the length of the original files's first half*/
	public int getLength1(){
		return length1;
	}
	/* The method getLength2, returns the length of the original file's second half*/
	public int getLength2(){
		return length2;
	}
}

/*
Typical output would be
Copying file using streams
File copied!
 */
