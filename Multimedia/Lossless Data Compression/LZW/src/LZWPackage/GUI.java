package LZWPackage;
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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 555, 263);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(158, 54, 283, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					algorithm.compress(textField.getText(),textField_1.getText());
					JOptionPane.showMessageDialog(null, "File compressed successfully");

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File not found");
				}
				
				
			}
		});
		btnNewButton.setBounds(134, 163, 124, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Decompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					algorithm.decompress(textField.getText(),textField_1.getText());
					JOptionPane.showMessageDialog(null, "File decompressed successfully");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File not found");
				}
			}
		});
		btnNewButton_1.setBounds(307, 163, 122, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblFile = new JLabel("File Location :");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFile.setBounds(41, 54, 84, 24);
		frame.getContentPane().add(lblFile);
		
		JButton btnNewButton_2 = new JButton("...");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File ("."));
				fileChooser.setDialogTitle("File Location");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.showOpenDialog(btnNewButton_2);
				textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnNewButton_2.setBounds(465, 57, 34, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("...");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File ("."));
				fileChooser.setDialogTitle("Folder Location");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(btnNewButton_3);
				textField_1.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		btnNewButton_3.setBounds(465, 101, 34, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(158, 98, 283, 29);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSaveLocation = new JLabel("Save Location :");
		lblSaveLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSaveLocation.setBounds(41, 98, 111, 24);
		frame.getContentPane().add(lblSaveLocation);

	}
}
