package com.ipvision.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class StreamClient {
	String url;
	Socket socket;
	String parts[];
	String ipAddress;
	int port;
	String rest;
	String restParts[];
	String middlePart=null;
	boolean visited = false;
	boolean domainName = false;
	
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public boolean isDomainName() {
		return domainName;
	}

	public void setDomainName(boolean domainName) {
		this.domainName = domainName;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getParts() {
		return parts;
	}


	public void setParts(String[] parts) {
		this.parts = parts;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public String getMiddlePart() {
		return middlePart;
	}


	public void setMiddlePart(String middlePart) {
		this.middlePart = middlePart;
	}


	public void setPort(int port) {
		this.port = port;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String[] getRestParts() {
		return restParts;
	}

	public void setRestParts(String[] restParts) {
		this.restParts = restParts;
	}

	
	public InputStream getInputStream(){
		/**Empty url or probably wrong url
		 */
		url = getUrl();
		
		if (url==null || !url.contains("://")){
			 return null;
		 }
		 
		//if(!isVisited()){ 
		
			parts = url.split("://");
			setParts(parts);
		 
			if (parts.length==2){
			 
				String splitParts[] = parts[1].split("/");
				char[] chars = splitParts[0].toCharArray();
				for (char c : chars) {
					if (Character.isLetter(c)) {
						//c='';
						domainName = true;
						break;
					}
				}
			
				int colon = 0;
				int slash = parts[1].indexOf('/');
				if (slash == -1) {
					parts[1] = parts[1] + "/";
					slash = parts[1].length() - 1;
				}
				/**
				 * Bug here!!! what if no slash after port?
				 */
				if (!domainName) {
					colon = parts[1].indexOf(':');
				}

				try {
					if (!domainName) {
						System.out.println("not domain name");
						ipAddress = parts[1].substring(0, colon);
						port = Integer.parseInt(parts[1]
								.substring(colon + 1, slash));
						
						setIpAddress(ipAddress);
						setPort(port);
						//System.out.println("PORT.............: "+ port);

					} else {
						System.out.println("domain name");
						try {
							InetAddress ip = InetAddress.getByName(splitParts[0]);
							ipAddress = ip.getHostAddress();
							
							if (parts[0].equalsIgnoreCase("http")) {
								port = 80;
							} else if (parts[0].equalsIgnoreCase("https")) {
								port = 443;
							} else if (parts[0].equalsIgnoreCase("ftp")) {
								port = 21;
							}
							setIpAddress(ipAddress);
							setPort(port);

						} catch (Exception e) {
							e.printStackTrace();
						}
						setDomainName(true);
					}
				
					rest = parts[1].substring(slash);
					setRest(rest);
			 
					restParts = rest.split("/");
					setRestParts(restParts);
				}catch (StringIndexOutOfBoundsException e1) {
					e1.printStackTrace();
				}
			 
			 System.out.println("IP " + ipAddress + " Port " + port + " Rest " + rest);
			 
			 for(int i =1;i<restParts.length-1;i++){
				 if(i==1)
					 middlePart = restParts[i]+"/";
				 else
					middlePart += restParts[i]+"/";
				}
			 
			 setMiddlePart(middlePart);
			 
				
			}
			//setVisited(true);
		//}
			 try{
				socket = new Socket(InetAddress.getByName(ipAddress), port);
				socket.setKeepAlive(true);
				socket.setSoTimeout(30000);

				PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
				out.print("GET " + rest + " HTTP/1.1\r\n");
				out.print("Host: 38.108.92.180:1935\r\n");
				out.print("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
				out.print("Accept-Language: en-US,en;q=0.5\r\n");
				out.print("Accept-Encoding: gzip, deflate\r\n");
				out.write("Connection: keep-alive\r\n");
				out.print("\r\n");
				out.flush();

				setSocket(socket);
			 }//try
			 catch (Exception e) {
				e.printStackTrace();
				return null;					
			}
		
		
			 byte data[] = new byte [1316];
			 InputStream in = null;
			 
			try {
				in = socket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			     
			int i = 0;
			try {
				i = in.read(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			     
			 if(i == -1) return null;
			    String response = new String(data);
			    //System.out.println("Response is.... " + response);
			    if(response.startsWith("HTTP/1.0 200 OK") || response.startsWith("HTTP/1.1 200 OK")){
			    	if(response.contains("ts"))
			    		in=new ByteArrayInputStream(response.getBytes());
			      	return in;
			    }
			    else{
			      	return null;
			    }
	 
	}

}