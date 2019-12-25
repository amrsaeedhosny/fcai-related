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
		frame.setBounds(100, 100, 628, 296);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(205, 54, 283, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					algorithm.compress(textField.getText(),textField_1.getText(),Integer.valueOf(textField_2.getText()));
					JOptionPane.showMessageDialog(null, "Image compressed successfully");

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Image not found");
				}
				
				
			}
		});
		btnNewButton.setBounds(169, 194, 124, 23);
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
		btnNewButton_1.setBounds(395, 194, 122, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblFile = new JLabel("Image Location :");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFile.setBounds(51, 54, 111, 24);
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
		btnNewButton_2.setBounds(524, 57, 34, 23);
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
		btnNewButton_3.setBounds(524, 101, 34, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(205, 98, 283, 29);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSaveLocation = new JLabel("Save Location :");
		lblSaveLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSaveLocation.setBounds(51, 98, 111, 24);
		frame.getContentPane().add(lblSaveLocation);
		
		textField_2 = new JTextField();
		textField_2.setBounds(205, 139, 283, 29);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblQuantizationLevels = new JLabel("Quantization Levels :");
		lblQuantizationLevels.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantizationLevels.setBounds(51, 133, 126, 37);
		frame.getContentPane().add(lblQuantizationLevels);

	}
}
