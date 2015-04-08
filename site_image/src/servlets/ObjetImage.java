package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ObjetImage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int[] tabRg = new int[8];
	int[] tabBy = new int[16];
	int[] tabWb = new int[16];
	Signatures signature;
	private BufferedImage image = null;
	int rg, by, wb;

	public ObjetImage(String pathImage) {
		try {
			image = ImageIO.read(new File(pathImage));
			signature = new Signatures(this.tabRg, this.tabBy, this.tabWb);
		} catch (IOException e) {
			System.out.println("Fichier introuvable!!");
			e.printStackTrace();
		}
		remplirTableRGB(image);
		initUI(image);
		// affiche();
	}

	/**
	 * R�cup�rer les valeurs rgb de chaque pixele de l'image stocker les valeurs
	 * dans 3 tableaux differants
	 * 
	 * @param image
	 * @throws Exception
	 */
	public void remplirTableRGB(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				/* Acc�der au rgb pixel (i, j) */
				int rgb = image.getRGB(i, j);
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = rgb & 0xff;

				calculerValeur(r, g, b);
				tabRg[Utiles.deffinirIntervalRG(rg)]++;
				tabBy[Utiles.deffinirIntervalBY(by)]++;
				tabWb[Utiles.deffinirIntervalWB(wb)]++;
			}
		}
	}

	public void affiche() {
		for (int i = 0; i < tabRg.length; i++) {
			System.out.println("valeur numero " + i + " : " + tabRg[i]);
		}
		System.out.println("****************");
		for (int i = 0; i < tabBy.length; i++) {
			System.out.println("valeur numero " + i + " : " + tabBy[i]);
		}
		System.out.println("****************");
		for (int i = 0; i < tabWb.length; i++) {
			System.out.println("valeur numero " + i + " : " + tabWb[i]);
		}
		System.out.println("****************");
	}

	/**
	 * calcule des valeur de rg by et wb avec les formules suivantes; rg = r -
	 * g; by = 2*b-r-g; wb = r + g + b;
	 * 
	 * @param r
	 * @param g
	 * @param b
	 */
	private void calculerValeur(int r, int g, int b) {
		rg = r - g;
		by = 2 * b - r - g;
		wb = r + g + b;
	}

	private void initUI(BufferedImage image) {
		setTitle("Images view");
		Surface surface = new Surface(image);
		add(surface);
		setSize(image.getHeight(), image.getWidth());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public int getTabRgElement(int elementNumero) {
		return tabRg[elementNumero];
	}

	public int getTabByElement(int elementNumero) {
		return tabBy[elementNumero];
	}

	public int getTabWbElement(int elementNumero) {
		return tabWb[elementNumero];
	}

	public int[] getTabRg() {
		return tabRg;

	}

	public int[] getTabBy() {
		return tabBy;
	}

	public int[] getTabWb() {
		return tabWb;
	}

	public String getTabRgS() {
		return Arrays.toString(tabRg);

	}

	public String getTabByS() {
		return Arrays.toString(tabBy);
	}

	public String getTabWbS() {
		return Arrays.toString(tabWb);
	}

	public Signatures getSignatures() {
		return this.signature;
	}

}
