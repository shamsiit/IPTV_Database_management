package com.ipvision.monitorModule.threads;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ipvision.controller.PageController;
import com.ipvision.domain.ChannelObj;
import com.ipvision.domain.LiveChannel;
import com.ipvision.domain.User;
import com.ipvision.domain.UserRole;
import com.ipvision.monitorModule.FFMPEGCommands;
import com.ipvision.monitorModule.ImageCompare;
import com.ipvision.network.StreamClient;
import com.ipvision.util.HibernateUtil;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MonitorThreadTwo implements Runnable {

	// private static final String IMAGE_INPUT_FILE = "/home/mizan/testTs.ts";
	// static String rootPath = context.getRealPath("/resources/assets/img/");
	// private static final String ImageRootPath =
	// "/media/mizan/development/apache-tomcat-7.0.54/wtpwebapps/ImageUpdate/resources/assets/img/channel_images/";

	private static double mVideoStreamIndex = -1;

	/**
	 * upper side variables for taking image frame
	 * */

	OutputStream out = null;

	public static String UserName = System.getProperty("user.name");
	public static String path = "/home/support/test/";// Use when deploy
	// public static String path = "/home/shams/test/";
	public static long thenTime;

	public static Set<Integer> set;

	private String ipAddress;
	private int port;
	private String[] restParts;
	private String middlePart;
	private String[] parts;
	private int position;
	private Integer index;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

	public void setPort(int port) {
		this.port = port;
	}

	public String[] getRestParts() {
		return restParts;
	}

	public void setRestParts(String[] restParts) {
		this.restParts = restParts;
	}

	public String getMiddlePart() {
		return middlePart;
	}

	public void setMiddlePart(String middlePart) {
		this.middlePart = middlePart;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	FFMPEGCommands ffmpegCommands = new FFMPEGCommands();

	HashMap<Integer, ChannelObj> mapObj;
	HashMap<Integer, Integer> coreStreamDownMap = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> sourceStreamDownMap = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> coreStreamStuckMap = new HashMap<Integer, Integer>();

	@Override
	public void run() {
		while (true) {

			mapObj = populateUrl();

			HashMap<Integer, Integer> tempCoreStreamDownMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> tempSourceStreamDownMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> tempStreamStuckMap = new HashMap<Integer, Integer>();

			for (Map.Entry<Integer, ChannelObj> map : mapObj.entrySet()) {

				setIndex(map.getKey());

				long then = System.currentTimeMillis();
				thenTime = then;
				String url = mapObj.get(map.getKey()).getChannelURL();

				StreamClient cl = null;

				try {
					cl = new StreamClient();
					cl.setUrl(url);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

				InputStream in = null;

				/**
				 * returns the input stream from any url
				 * */
				try {
					in = cl.getInputStream();
				} catch (Exception e1) {
					e1.printStackTrace();
					mapObj.get(map.getKey()).setStatus(false);
				}

				if (in != null) {
					try {
						setIpAddress(cl.getIpAddress());
						setPort(cl.getPort());
						setRestParts(cl.getRestParts());
						setMiddlePart(cl.getMiddlePart());
						setParts(cl.getParts());
					} catch (Exception e) {
						System.out.println("parts problem");
						e.printStackTrace();
					}

					checkMethod(in, cl, map.getKey());

				} else {
					mapObj.get(map.getKey()).setStatus(false);
				}

				/**
				 * Number of downs map
				 */
				if (coreStreamDownMap.get(map.getKey()) != null) {
					Integer val = coreStreamDownMap.get(map.getKey());
					tempCoreStreamDownMap.put(map.getKey(), val);
				} else {
					tempCoreStreamDownMap.put(map.getKey(), 0);
				}

				if (sourceStreamDownMap.get(map.getKey()) != null) {
					Integer val = sourceStreamDownMap.get(map.getKey());
					tempSourceStreamDownMap.put(map.getKey(), val);
				} else {
					tempSourceStreamDownMap.put(map.getKey(), 0);
				}

				if (coreStreamStuckMap.get(map.getKey()) != null) {
					Integer val = coreStreamStuckMap.get(map.getKey());
					tempStreamStuckMap.put(map.getKey(), val);
				} else {
					tempStreamStuckMap.put(map.getKey(), 0);
				}

				/**
				 * just shows and sets status if channel is up or down
				 * */
				if (mapObj.get(map.getKey()).isStatus()) {
					System.out.println(mapObj.get(map.getKey())
							.getChannelName() + "  Channel up\n");
					mapObj.get(map.getKey()).setStatus(true);
					/**
					 * Check if channel is Stuck or not
					 */
					int channelId = mapObj.get(map.getKey()).getChannelId();
					try {
						ImageCompare ic = new ImageCompare(
								"/home/support/test/" + channelId + "_old.jpeg",
								"/home/support/test/" + channelId + ".jpeg");
						ic.compare();
						if (ic.match()) {
							int numberOfStuck = tempStreamStuckMap.get(map
									.getKey());
							numberOfStuck++;
							tempStreamStuckMap.put(map.getKey(), numberOfStuck);
						}
						ic = null;
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					System.out.println(mapObj.get(map.getKey())
							.getChannelName() + "  nChannel down\n");
					mapObj.get(map.getKey()).setStatus(false);
					String command = "/home/support/bin/ffprobe -timeout 10000k -i "
							+ mapObj.get(map.getKey()).getSourceLink();
					boolean isSourceOk = ffmpegCommands
							.checkIfSourceOk(command);
					if (!isSourceOk) {
						/**
						 * what will be if source down that logic goes here
						 * */
						int numberOfDown = tempSourceStreamDownMap.get(map
								.getKey());
						numberOfDown++;
						tempSourceStreamDownMap.put(map.getKey(), numberOfDown);
					}
					/**
					 * what will be if down that logic goes here
					 * */
					int numberOfDown = tempCoreStreamDownMap.get(map.getKey());
					numberOfDown++;
					tempCoreStreamDownMap.put(map.getKey(), numberOfDown);

				}

				System.out.println(mapObj.get(map.getKey()).getChannelName()
						+ " Channel Down Number = "
						+ tempCoreStreamDownMap.get(map.getKey()));

				System.out.println(mapObj.get(map.getKey()).getChannelName()
						+ " Channel Source Down Number = "
						+ tempSourceStreamDownMap.get(map.getKey()));

				System.out.println(mapObj.get(map.getKey()).getChannelName()
						+ " Channel Stuck Number = "
						+ tempStreamStuckMap.get(map.getKey()));

				if (tempCoreStreamDownMap.get(map.getKey()) == 3) {
					tempCoreStreamDownMap.put(map.getKey(), 0);
					if (tempSourceStreamDownMap.get(map.getKey()) == 3) {
						tempSourceStreamDownMap.put(map.getKey(), 0);
						String[] adminEmails = returnAdminsEmail();
						for(int i=0;i<adminEmails.length;i++){
							System.out.println("Mail to Admin "+adminEmails[i]);
							//sendMail(adminEmails[i], " Source down ", mapObj.get(map.getKey()).getChannelName());
						}
						
					} else {
						String mail = returnPublishersEmail(map.getKey());
						System.out.println("Mail to publisher "+mail);
						//sendMail(mail, " down ", mapObj.get(map.getKey()).getChannelName());
					}
				}

				if (tempStreamStuckMap.get(map.getKey()) == 3) {
					tempStreamStuckMap.put(map.getKey(), 0);
					String mail = returnPublishersEmail(map.getKey());
					System.out.println("Mail to publisher that it's stuck "+mail);
					//sendMail(mail, " Stuck ", mapObj.get(map.getKey()).getChannelName());
				}

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				setIndex(map.getKey());

			}

			coreStreamDownMap = tempCoreStreamDownMap;
			sourceStreamDownMap = tempSourceStreamDownMap;
			coreStreamStuckMap = tempStreamStuckMap;

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public HashMap<Integer, ChannelObj> populateUrl() {

		HashMap<Integer, ChannelObj> mapObj = new HashMap<Integer, ChannelObj>();

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List channels = session.createQuery("FROM LiveChannel").list();
			System.err.println("CHANNEL SIZE : " + channels.size());
			for (Iterator iterator = channels.iterator(); iterator.hasNext();) {
				LiveChannel channel = (LiveChannel) iterator.next();
				mapObj.put(
						channel.getChannelId(),
						new ChannelObj(false, channel.getChannelName(), channel
								.getLink180(), false, channel.getChannelId(),
								channel.getLogo(), channel.getLink()));
				System.err
						.println("CHANNEL NAME : " + channel.getChannelName());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return mapObj;
	}

	public String returnPublishersEmail(int channelId) {
		LiveChannel ch = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ch = (LiveChannel) session
					.createCriteria(LiveChannel.class)
					.add(Restrictions.eq("channelId",channelId)).uniqueResult();
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int userId = ch.getUser().getUserId();
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User) session
					.createCriteria(User.class)
					.add(Restrictions.eq("userId",userId)).uniqueResult();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userEmail = user.getEmail();
		
		session.close();
		
		return userEmail;
	}
	
	public String[] returnAdminsEmail(){
		
		List<UserRole> userRoleList = null;
		User user = null;
		String[] emails = null;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			userRoleList =  session
					.createCriteria(UserRole.class)
					.add(Restrictions.eq("userRoleName","Admin")).list();
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		emails = new String[userRoleList.size()];
		int counter = 0;
		for(UserRole userRole : userRoleList){
			try {
				tx = session.beginTransaction();
				user =  (User) session
						.createCriteria(User.class)
						.add(Restrictions.eq("userId",userRole.getUser().getUserId())).uniqueResult();
				emails[counter] = user.getEmail();
				counter++;
				tx.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		session.close();
		
		return emails;
	}
	
	public void sendMail(String toMail,String state,String channelName){
		String to = toMail;//change accordingly

	      // Sender's email ID needs to be mentioned
	      String from = "livestreamer@ipvisionsoft.com";//change accordingly
	      final String username = "livestreamer@ipvisionsoft.com";//change accordingly
	      final String password = "ipvision123";//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "mail.ipvisionsoft.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	     // props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      javax.mail.Session session = javax.mail.Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject("Channel Issue");

	         // Now set the actual message
	         message.setText(channelName+" is "+state+" . Please check this out..");

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	            System.out.println(e.toString());
	      }
	}

	private void checkMethod(InputStream in, StreamClient cl, Integer index) {

		try {
			DataInputStream din;
			String s = null;
			int fileType = 0; // 1 for got chunkList; 2 for got .ts file
			String ss = null;

			if (in != null) {
				din = new DataInputStream(in);

				try {
					s = IOUtils.toString(din);
					System.err.println("response" + s);

					/**
					 * checks response if it is chunklist response or ts
					 * response. deciding that sends on method if chunklist
					 * found then again execute this method untill we find ts.
					 * if ts found go to ts download method
					 * */
					if (s.contains("chunklist")) {
						int startIndex = s.indexOf("chunklist");
						String chunkList = s.substring(startIndex, s.length());
						s = chunkList;
						fileType = 1;
					} else if (s.contains("ts")) {
						String[] x = s.split("\n");
						s = x[x.length - 1];
						s = s.substring(1, s.length());
						System.err.println("ts:" + s);
						ss = s;
						fileType = 2;
					}

				} catch (IOException e) {
					e.printStackTrace();
					mapObj.get(index).setStatus(false);
					return;
				}
			} else {
				System.out.println("input stream null found\n");
				mapObj.get(index).setStatus(false);
				return;
			}

			/**
			 * if it does not contain ts then type 1 and create next url to find
			 * ts it ts found write ts
			 * */
			if (fileType == 1) {
				if (s != null)
					createUrlAndLookForTs(s, cl, index);

			} else if (fileType == 2) {
				analyzeTS(ss, cl, true);
			}

		} catch (Exception e) {
			System.out.println("check method exception");
			e.printStackTrace();
			mapObj.get(index).setStatus(false);
			return;
		}

	}

	private void createUrlAndLookForTs(String s, StreamClient cl, Integer index) {
		/**
		 * joins all the parts and create new url to feed check methods
		 * */

		try {
			cl.setUrl("http://" + cl.getIpAddress() + ":" + cl.getPort() + "/"
					+ getMiddlePart() + s);

			InputStream in2 = null;
			try {
				in2 = cl.getInputStream();
			} catch (NullPointerException e) {
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}

			if (in2 != null)
				checkMethod(in2, cl, index);
			else
				return;

		} catch (Exception e) {
			System.out.println("create url method exception");
			e.printStackTrace();
			mapObj.get(index).setStatus(false);
			return;
		}
	}

	private void analyzeTS(String s, StreamClient cl, boolean ifDomain) {
		/**
		 * write ts into disk to analyze
		 * */
		System.out.println("Downloading here.... ");

		if (s != null) {
			String URL = "http://" + cl.getIpAddress() + ":" + cl.getPort()
					+ "/" + getMiddlePart() + s;
			System.err.println("URL: " + URL);
			URL u = null;

			try {
				u = new URL(URL);
			} catch (MalformedURLException e) {
				System.out.println("couldn't create URL");
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}

			URLConnection uc = null;
			InputStream in = null;
			try {
				uc = u.openConnection();
				InputStream raw = uc.getInputStream();
				in = new BufferedInputStream(raw);
			} catch (IOException e) {
				System.out.println("couldn't create connection");
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}

			int contentLength = 0;

			try {
				contentLength = uc.getContentLength();
			} catch (Exception e) {
				System.out.println("content length not found");
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}

			/**
			 * start reading bytes of ts file
			 * */
			byte[] data = new byte[contentLength];
			int bytesRead = 0;
			int offset = 0;

			/**
			 * feed all bytes into byte array
			 * */
			while (offset < contentLength) {

				try {
					bytesRead = in.read(data, offset, data.length - offset);
				} catch (IOException e) {
					System.out.println("could not read");
					e.printStackTrace();
					mapObj.get(index).setStatus(false);
				}

				if (bytesRead == -1)
					break;

				offset += bytesRead;
			}

			try {
				in.close();
			} catch (IOException e) {
				System.out.println("closed");
				e.printStackTrace();
			}

			/**
			 * write whole byte array into file
			 * */
			try {
				out = new FileOutputStream(new File(path + "output.ts"));
				out.write(data);
				out.flush();
				out.close();
				cl.getSocket().close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * alanyze downloaded ts
			 * */
			DownLoadTsAnalyze();

		}
	}

	private void DownLoadTsAnalyze() {
		/**
		 * read ts file from disk. checks pid from packets
		 * */

		Set<Integer> set = new HashSet<Integer>();

		File file = new File(path + "output.ts");

		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			System.out.println("fin problem");
			e1.printStackTrace();
			mapObj.get(index).setStatus(false);
		}

		byte first = 0;
		byte now[] = null;
		int byteCounter = 1;
		int nosRead = 0;

		while (byteCounter < 1362) {
			try {
				first = (byte) fin.read();
			} catch (IOException e) {
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}

			now = new byte[188];
			now[0] = first;

			try {
				nosRead = fin.read(now, 1, 187);
			} catch (IOException e) {
				e.printStackTrace();
				mapObj.get(index).setStatus(false);
			}
			if (nosRead == -1)
				break;

			int totalRead = 0, startPos = 0;
			totalRead = nosRead;
			while (totalRead < 187) {
				startPos = totalRead + 1;
				int remaining = 187 - totalRead;
				int currentRead = 0;
				try {
					currentRead = fin.read(now, startPos, remaining);
					// System.out.println("Now: "+now+"startPos: "+startPos+"remaining"+remaining);
				} catch (IOException e) {
					e.printStackTrace();
					mapObj.get(index).setStatus(false);
				}
				totalRead += currentRead;
			}

			int pid = ((now[1] & 0x1f) << 8) + now[2];
			set.add(pid);
			// System.out.println("packet no:"+byteCounter+"  read:"+nosRead+"  pid:"+pid);

			byteCounter++;

		}
		System.out.println("Done analysis");

		/**
		 * code for getting image frame from our downloaded ts
		 * */
		System.out.println("Befor Image snapshot..");
		File previousSnapShot = new File(path
				+ mapObj.get(getIndex()).getChannelId() + ".jpeg");
		if (previousSnapShot.exists()) {
			File changeFile = new File(path
					+ mapObj.get(getIndex()).getChannelId() + "_old.jpeg");
			previousSnapShot.renameTo(changeFile);
		}
		String command = "ffmpeg -i " + path + "output.ts -vframes 1 " + path
				+ mapObj.get(getIndex()).getChannelId() + ".jpeg";
		ffmpegCommands.dumpImage(command);
		System.out.println("Image snapshot taken......"
				+ mapObj.get(getIndex()).getChannelId() + ".jpeg");
		/**
		 * Finally set if the channel is up or down
		 * */
		if (set.size() <= 4) {
			mapObj.get(getIndex()).setStatus(true);
		} else {
			mapObj.get(getIndex()).setStatus(false);
		}

		long nowTime = System.currentTimeMillis();

		if (set.size() >= 1 && set.size() <= 4)
			System.out.println("\nChannel up\n");
		else
			System.out.println("\nChannel down\n");

		System.out.println("Time taken: " + (nowTime - thenTime) + "\n");
		System.out.println(set.size() + "\n");
		System.out.println("------------------------------------------");

	}

}
