# Things that need to get done #

  * Packet transfer
  * Code to Merge the 2 halfs


# Updates #

Write you updates here:

The client now asks the server for the length of the 2 MP3 halves. The server processes the request with out any issues, however if you look at the for loop around line 75 in UDPClient, you'll notice that it only processes the first 6 bits of the packet that ought to contain the length of the byte[.md](.md) of the two files, for now it only echo's the result. we still have to write the part that actually uses the length in conjunction the counter.