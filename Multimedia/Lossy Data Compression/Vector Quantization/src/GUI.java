import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private Algorithm algorithm = new Algorithm();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 635, 309);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(205, 38, 283, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					algorithm.compress(textField.getText(),textField_1.getText(),Integer.valueOf(textField_2.getText()),Integer.valueOf(textField_3.getText()), Integer.valueOf(textField_4.getText()));
					JOptionPane.showMessageDialog(null, "Image compressed successfully");

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Image not found");
				}
				
				
			}
		});
		btnNewButton.setBounds(156, 222, 124, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Decompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					algorithm.decompress(textField.getText(),textField_1.getText());
					JOptionPane.showMessageDialog(null, "Image decompressed successfully");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Image not found");
				}
			}
		});
		btnNewButton_1.setBounds(412, 222, 122, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblFile = new JLabel("Image Location :");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFile.setBounds(51, 38, 111, 24);
		frame.getContentPane().add(lblFile);
		
		JButton btnNewButton_2 = new JButton("...");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File ("."));
				fileChooser.setDialogTitle("Image Location");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.showOpenDialog(btnNewButton_2);
				textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnNewButton_2.setBounds(524, 41, 34, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("...");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File ("."));
				fileChooser.setDialogTitle("Save Location");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(btnNewButton_3);
				textField_1.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnNewButton_3.setBounds(524, 85, 34, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(205, 82, 283, 29);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSaveLocation = new JLabel("Save Location :");
		lblSaveLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSaveLocation.setBounds(51, 82, 111, 24);
		frame.getContentPane().add(lblSaveLocation);
		
		textField_2 = new JTextField();
		textField_2.setBounds(205, 123, 283, 29);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblQuantizationLevels = new JLabel("Quantization Levels :");
		lblQuantizationLevels.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantizationLevels.setBounds(51, 117, 126, 37);
		frame.getContentPane().add(lblQuantizationLevels);
		
		textField_3 = new JTextField();
		textField_3.setBounds(180, 170, 86, 23);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(420, 170, 86, 23);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblVectorHeight = new JLabel("Vector Height :");
		lblVectorHeight.setBounds(86, 170, 91, 23);
		frame.getContentPane().add(lblVectorHeight);
		
		JLabel lblVectorWidth = new JLabel("Vector Width :");
		lblVectorWidth.setBounds(329, 171, 91, 21);
		frame.getContentPane().add(lblVectorWidth);

	}
}
