package servlets;

import java.security.AccessController;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class SaveImageToDatabase {

	public static String pathBase = "/Users/cash/Desktop/base/";

	public SaveImageToDatabase() {
	}

	private Connection dbconx() throws SQLException {
		Connection connection = null;

		String connectionURL = "jdbc:mysql://localhost:8889/test";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(connectionURL, "root",
					"root");
			System.out.println("Connected to database");
		}

		catch (Exception ex) {
			System.out.println("Found some error : " + ex);
		}
		return connection;

	}

	public void saveImg(String img, String nom) throws SQLException {

		ResultSet rs = null;
		PreparedStatement psmnt = null;
		FileInputStream fis;
		try {

			Connection conn = dbconx();
			File image = new File(img);
			ObjetImage imgr = new ObjetImage(img);

			System.out.println(image.exists());

			fis = new FileInputStream(image);

			psmnt = conn
					.prepareStatement("insert into image(name, img, sigrg, sigby, sigwb) "
							+ "values(?,?,?,?,?)");
			psmnt.setString(1, nom);

			psmnt.setBinaryStream(2, (InputStream) fis, (int) (image.length()));

			// çalçulér la signature et remplacer les zero par des sig
			psmnt.setString(3, imgr.getTabRgS());
			psmnt.setString(4, imgr.getTabWbS());
			psmnt.setString(5, imgr.getTabByS());

			int s = psmnt.executeUpdate();
			if (s > 0) {
				System.out.println("Uploaded successfully !");
				conn.close();
				psmnt.close();
			} else {
				System.out.println("unsucessfull to upload image.");
				conn.close();
				psmnt.close();
			}
		}
		// catch if found any exception during rum time.
		catch (Exception ex) {
			System.out.println("Found some error : " + ex);
		}
	}

	public void chargeIMG(String name, String location) throws Exception {

		// AccessController.checkPermission(new FilePermission(location,
		// "read,write"));

		FileOutputStream ostreamImage = new FileOutputStream(location + "/"
				+ name );
		System.out.println("download successfully !");

		try {
			Connection conn = dbconx();
			PreparedStatement ps = conn
					.prepareStatement("select img from image where name=?");

			try {
				ps.setString(1, name);
				ResultSet rs = ps.executeQuery();

				try {
					if (rs.next()) {
						InputStream istreamImage = rs.getBinaryStream("img");

						byte[] buffer = new byte[1024];
						int length = 0;

						while ((length = istreamImage.read(buffer)) != -1) {
							ostreamImage.write(buffer, 0, length);
						}
					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} finally {
			ostreamImage.close();
		}
	}

	// méthode :camparer lés signaturés(image)
	/*
	 * çalçulér la signatur de limage én paramétre çhargér une signatures dans
	 * 3tableaux tableaux calculer la distance jugé ét écider si distance ok
	 * charger l'image dans un array
	 */

	public void trouverSimilaire(ObjetImage img) throws SQLException {

		int[] rgSig = img.getTabRg();
		int[] bySig = img.getTabBy();
		int[] wbSig = img.getTabWb();
		int[] sigRgfromDb = null;
		int[] sigByfromDb = null;
		int[] sigWbfromDb = null;
		float dist = 0;
		ArrayList<File> images = new ArrayList<File>();

		try {
			Connection conn = dbconx();
			PreparedStatement ps = conn
					.prepareStatement("select name, sigrg, sigby, sigwb from image");
			 

			try {

				ResultSet rs = ps.executeQuery();

				try {
					if (rs.next()) {
						do {

							Signatures sig = new Signatures(rs.getString(2),
									rs.getString(3), rs.getString(4));

							dist = Utiles
									.calculerDistanceIntersection(img, sig);
							if (dist != 0) {
								
								 try {
									this.chargeIMG(rs.getString(1), "/Users/cash/Desktop/base");
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						} while (rs.next());

					}

				} finally {
					rs.close();
				}
			} finally {
				ps.close();
				 
				conn.close();

			}
		} finally {
		}
	}

}