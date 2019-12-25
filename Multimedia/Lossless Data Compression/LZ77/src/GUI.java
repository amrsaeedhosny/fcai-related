import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private Algorithm algorithm = new Algorithm();

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
		frame.setBounds(100, 100, 576, 291);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(140, 92, 260, 33);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					algorithm.compress(textField.getText());
					JOptionPane.showMessageDialog(null, "File compressed successfully");

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File not found");
				}
				
				
			}
		});
		btnNewButton.setBounds(127, 163, 124, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Decompress");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					algorithm.decompress(textField.getText());
					JOptionPane.showMessageDialog(null, "File decompressed successfully");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File not found");
				}
			}
		});
		btnNewButton_1.setBounds(298, 163, 122, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblFile = new JLabel("File Location :");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFile.setBounds(49, 94, 84, 24);
		frame.getContentPane().add(lblFile);
		
		JLabel lblEg = new JLabel("E.g. E:/files/file.txt");
		lblEg.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEg.setBounds(410, 96, 113, 23);
		frame.getContentPane().add(lblEg);

	}
}
