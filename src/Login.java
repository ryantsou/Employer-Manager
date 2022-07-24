import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.sql.*;
// import java.util.logging.Level;
// import java.util.logging.Logger;
import javax.swing.*;

public class Login extends JFrame {

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	private javax.swing.JButton btn_cancel;
	private javax.swing.JButton btn_login;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JPasswordField txt_password;
	public javax.swing.JTextField txt_username;

	private Timer timer;

	public Login() {
		initComponents();
		conn = MysqlConnection.mysqlDbConnection("user", "password");
	}

	public void Close() {
		WindowEvent wce = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wce);
	}

	@SuppressWarnings("deprecation")
	private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {

		try {
			String sql = "select * from user where username =? and password= ?";

			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_username.getText());
			pst.setString(2, txt_password.getText());

			rs = pst.executeQuery();

			if (rs.next()) {
				timer.start();

			} else {
				JOptionPane.showMessageDialog(null, "Utilisateur & mot de passe invalides !!!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}

	public class progressbar implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			jProgressBar1.setString("Chargement application ...");
			int n = jProgressBar1.getValue();
			if (n < 100) {
				n++;
				jProgressBar1.setValue(n); 
			} else {
				timer.stop();
				EmployeeManager adm = new EmployeeManager();
				adm.setVisible(true);
				Close();
			}
		}
	}

	private void formWindowOpened(java.awt.event.WindowEvent evt) {
		timer = new Timer(50, new progressbar());
	}

	private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {
		Close();
	}

	@SuppressWarnings("deprecation")
	private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {

		String username = txt_username.getText();

		if (username.equals("")) {
			JOptionPane.showMessageDialog(null, "Nom d'utilisateur obligatoire !");
		} else {

			if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
				try {

					String sql = "select * from user where username =? and password= ?";

					pst = conn.prepareStatement(sql);
					pst.setString(1, txt_username.getText());
					pst.setString(2, txt_password.getText());

					rs = pst.executeQuery();

					if (rs.next()) {
						timer.start();
						JOptionPane.showMessageDialog(null, "Utilisateur & mot de passe corrects !");

					} else {

						JOptionPane.showMessageDialog(null, "Utilisateur & mot de passe invalides !");
					}

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null, e);
				}

			}
		}
	}

	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} 
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Login().setVisible(true);
			}
		});

	}
	
	private void initComponents() {

		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		txt_password = new javax.swing.JPasswordField();
		txt_username = new javax.swing.JTextField();
		btn_login = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		btn_cancel = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		jProgressBar1 = new javax.swing.JProgressBar();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});

		jPanel2.setBackground(Color.white);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " LOGIN : ",
				javax.swing.border.TitledBorder.ABOVE_TOP, javax.swing.border.TitledBorder.TOP,
				new java.awt.Font("Arial", 1, 14), Color.black)); 

		jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); 
		jLabel2.setText("Mot de passe :");

		txt_password.setFont(new java.awt.Font("Arial", 1, 14)); 
		txt_password.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txt_password.setBorder(new javax.swing.border.LineBorder(Color.black, 1, true));
		txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txt_passwordKeyPressed(evt);
			}
		});

		txt_username.setFont(new java.awt.Font("Arial", 1, 14)); 
		txt_username.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		txt_username.setBorder(new javax.swing.border.LineBorder(Color.black, 1, true));

		btn_login.setFont(new java.awt.Font("Arial", 1, 12)); 
		btn_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Unlock-icon.png"))); 
		btn_login.setText("Login");
		btn_login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_loginActionPerformed(evt);
			}
		});

		jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); 
		jLabel1.setText("Utilisateur :");

		btn_cancel.setFont(new java.awt.Font("Arial", 1, 12)); 
		btn_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close-icon.png"))); 
		btn_cancel.setText("Annuler");
		btn_cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_cancelActionPerformed(evt);
			}
		});

		jLabel4.setForeground(Color.blue);
		jLabel4.setText("NOTE   :   Accès non autorisé n'est permis !!!");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel4)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jLabel2).addComponent(jLabel1))
								.addGroup(jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18)
												.addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(btn_cancel))
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(18, 18, 18)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(txt_password,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(txt_username))))))
						.addContainerGap(18, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel1))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel2))
						.addGap(18, 18, 18)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_login, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
						.addComponent(jLabel4).addGap(32, 32, 32)));

		jProgressBar1.setForeground(Color.black);
		jProgressBar1.setFont(new java.awt.Font("Arial", 3, 14));
		jProgressBar1.setStringPainted(true);

		jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/poisson.jpg"))); 

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(2, 2, 2)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jLabel3).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jProgressBar1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(6, 6, 6)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
		this.setLocationRelativeTo(null);
	}
}
