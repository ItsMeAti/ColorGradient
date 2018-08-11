package GUI;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import imgData.LabImg;
import imgData.RGBImg;
import util.Util;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JLabel image = new JLabel();
	JButton btnBrowse = new JButton("Browse");
	JButton btnSave = new JButton("Save");
	
	int width = 250, height = 250;
	BufferedImage targetImage;
	RGBImg input = new RGBImg(width,height);
	ImagePanel panel0 = new ImagePanel();
	ImagePanel panel1 = new ImagePanel();
	ImagePanel panel2 = new ImagePanel();
	ImagePanel panel3 = new ImagePanel();
	
	RGBImg RgbCx = new RGBImg(width-2, height-2);
	RGBImg RgbCy = new RGBImg(width-2, height-2);
	RGBImg RgbD = new RGBImg(width-2, height-2); 
	RGBImg RgbM = new RGBImg(width-2, height-2); 
	
	public GUI() {
		super("ColorGradient");
		setLayout(null);
		setSize(3*width+50,2*height+60);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		// addlabel - for input image | need to be label, not panel cuz of resizeing
		image.setBounds(10, 10, width, height);
		add(image);
		
		//add ImagePanel0
		panel0.setBounds(width+20, 10, width-3, height-3);
		add(panel0);
		
		//add ImagePanel1
		panel1.setBounds(width*2+30, 10, width-3, height-3);
		add(panel1);
		
		//add ImagePanel2
		panel2.setBounds(width+20, 20+height, width-3, height-3);
		add(panel2);
		
		//add ImagePanel3
		panel3.setBounds(width*2+30, 20+height, width-3, height-3);
		add(panel3);

		//add Browse btn
		btnBrowse.setBounds(10, height+20, 100, 30);
		add(btnBrowse);
		
		//add Save btn
		btnSave.setBounds(width-100+10, height+20, 100, 30);
		btnSave.setEnabled(false);
		add(btnSave);
		
		//logic for browse button & pic loading
		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				// filter the files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				// if the user click on save in Jfilechooser
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					try {
						BufferedImage image1 = ImageIO.read(selectedFile);
						targetImage = Util.resize(image1, width, height);
						for (int i = 0; i < width; i++) {
							for (int j = 0; j < height; j++) {
								int clr = targetImage.getRGB(i, j);
								int red = (clr & 0x00ff0000) >> 16;
								int green = (clr & 0x0000ff00) >> 8;
								int blue = clr & 0x000000ff;

								input.setRgb(i, j, red, green, blue);
							}
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String path = selectedFile.getAbsolutePath();
					image.setIcon(FromPathToIcon(path));
					btnSave.setEnabled(true);
					
					//Convert sRGB to XYZ color space then -> Convert XYZ to Lab color space
					LabImg inputLab = input.toLab();
					
					//apply sobel kernel to x
					LabImg Cx = inputLab.applyCxKernel();
					RgbCx = Cx.labImageToRGBImage(); 
					panel2.addImage(RgbCx.getImage());
					
					//apply sobel to y
					LabImg Cy = inputLab.applyCyKernel();
					RgbCy = Cy.labImageToRGBImage(); 
					panel3.addImage(RgbCy.getImage());
					
					//generates the gradients direction image
					LabImg direction = inputLab.direction(Cx, Cy);
					RgbD = direction.imageToGrayscale(); 
					panel1.addImage(RgbD.getImage());
					
					//generates the gradients magnitude image
					LabImg magnitude = inputLab.magnitude(Cx, Cy);
					RgbM = magnitude.imageToGrayscale(); 
					panel0.addImage(RgbM.getImage());
					
					System.out.println("Done");

				}
				// if the user click on save in Jfilechooser

				else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No File Select");
				}
			}
		});
		
		//Saveing
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				File outputfile0 = new File("magnitude.jpg");
				try {
					ImageIO.write(RgbM.getImage(), "jpg", outputfile0);
					System.out.println("Saved");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				File outputfile1 = new File("direction.jpg");
				try {
					ImageIO.write(RgbD.getImage(), "jpg", outputfile1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				File outputfile2 = new File("Cx.jpg");
				try {
					ImageIO.write(RgbCx.getImage(), "jpg", outputfile2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				File outputfile3 = new File("Cy.jpg");
				try {
					ImageIO.write(RgbCy.getImage(), "jpg", outputfile3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	private ImageIcon FromPathToIcon(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

}
