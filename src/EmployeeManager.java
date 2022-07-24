

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
// import javax.swing.SwingUtilities;
// import javax.swing.Timer;
// import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class EmployeeManager extends JFrame {
	private Connection connex = null;
	private ResultSet result = null;
	private PreparedStatement prs = null;
	private JPanel mainPane = new JPanel();
	private JPanel tablePane = new JPanel();
	private JPanel fieldPane = new JPanel();
	private JPanel imagePane = new JPanel();
	private JLabel image = new JLabel();
	private JPanel datePane = new JPanel(new FlowLayout());

	// A décrire !!!
	private JMenuBar menu = new JMenuBar();
	private JMenu file = new JMenu("Fichier");
	private JMenu edit = new JMenu("Gérer");

	private JMenuItem exitMenu = new JMenuItem("Quitter");
	private JMenuItem adding = new JMenuItem("Ajouter employé");
	private JMenuItem saving = new JMenuItem("Enregistrer employé");
	private JMenuItem deleting = new JMenuItem("Supprimer ref. employé");
	private JMenuItem cleaning = new JMenuItem("Initialiser champs");

	// A décrire !!!
	private JButton add = new JButton("Ajouter");
	private JButton save = new JButton("Enregistrer");
	private JButton delete = new JButton("Supprimer");
	private JButton clean = new JButton("Initialiser");
	private JButton close = new JButton("Fermer");
	// private JButton upload = new JButton("Ajouter une image");

	// A décrire !!!
	private JLabel empIDtxt = new JLabel("EmployeeID : ");
	private JLabel nametxt = new JLabel("Name : ");
	private JLabel gendertxt = new JLabel("Gender : ");
	private JLabel agetxt = new JLabel("Age : ");
	private JLabel bloodgptxt = new JLabel("BloodGroup : ");
	private JLabel contacttxt = new JLabel("Contact : ");
	private JLabel qualificationtxt = new JLabel("Qualification : ");
	private JLabel dojtxt = new JLabel("Date of join : ");
	private JLabel addresstxt = new JLabel("Address : ");
	private JLabel daytxt = new JLabel("Day:");
	private JLabel mounthtxt = new JLabel("Mounth:");
	private JLabel yeartxt = new JLabel("Year:");

	private JTextField empID = new JTextField();
	private JTextField name = new JTextField();
	private JTextField age = new JTextField();
	private JTextField contact = new JTextField();
	private JTextField address = new JTextField();
	private JTextField day = new JTextField();
	private JTextField mounth = new JTextField();
	private JTextField year = new JTextField();
	private JTextField qualification = new JTextField();

	private JCheckBox male = new JCheckBox("Male");
	private JCheckBox female = new JCheckBox("Female");

	private String combos[] = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
	private JComboBox<?> bloodgp = new JComboBox<Object>(combos);

	private String title[] = { "EmployeeID", "Name", "Gender", "Age", "BloodGroup", "Contact", "Qualification",
			"Date of Join", "Address" };

	private JTable table = new JTable();
	private JScrollPane scroll = new JScrollPane(table);
	private DefaultTableModel model = (DefaultTableModel) table.getModel();

	// A décrire !!!
	public EmployeeManager() {
		setTitle("Gestion des employés");
		componentlook();
		initComponent();
		actions();
		connex = MysqlConnection.mysqlDbConnection("lucas", "Motdepasse01");
		loading_DB();
	}

	// A décrire !!!
	private void actions() {
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				try {

					connex.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});

		// A décrire !!!
		male.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (female.isSelected()) {
					female.setSelected(false);
				}
			}
		});
		
		// A décrire !!!
		female.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (male.isSelected()) {
					male.setSelected(false);
				}
			}
		});

		// A décrire !!!
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				empID.setText("");
				name.setText("");
				male.setSelected(false);
				female.setSelected(false);
				age.setText("");
				bloodgp.setSelectedItem("");
				contact.setText("");
				qualification.setText("");
				day.setText("");
				mounth.setText("");
				year.setText("");
				address.setText("");
				image.setIcon(null);
				JOptionPane.showMessageDialog(null, "Champs initialisés, prêt pour l'ajout d'employé !");
			}
		});

		// A décrire !!!
		adding.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				empID.setText("");
				name.setText("");
				male.setSelected(false);
				female.setSelected(false);
				age.setText("");
				bloodgp.setSelectedItem("");
				contact.setText("");
				qualification.setText("");
				day.setText("");
				mounth.setText("");
				year.setText("");
				address.setText("");
				image.setIcon(null);
				JOptionPane.showMessageDialog(null, "Champs initialisés, prêt pour l'ajout d'employé !");

			}
		});

		// A décrire !!!
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (empID.getText().equals("")) {
					if (name.getText().equals("") || !male.isSelected() && !female.isSelected()
							|| contact.getText().equals("") || qualification.getText().equals("")
							|| day.getText().equals("") || mounth.getText().equals("") || day.getText().equals("")
							|| address.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Erreur ! Tous les champs doivent être remplis !");
					}
					else {

						try {
							String doj = year.getText() + "-" + mounth.getText() + "-" + day.getText();
							String addSql1 = "insert into employeeinfo (Name,Gender,Age,BloodGroup,ContactNo,Qualification,DOJ,Address,EmpImage)";
							String addSql2 = "values(\"" + name.getText() + "\",\"";
							String sex = "";
							if (male.isSelected()) {
								sex = "Male";
							}
							if (female.isSelected()) {
								sex = "Female";
							}
							addSql2 += sex + "\"," + age.getText() + ",\"" + bloodgp.getSelectedItem().toString()
									+ "\",";
							addSql2 += contact.getText() + ",\"" + qualification.getText() + "\",'" + doj + "',\""
									+ address.getText() + "\",0);";
							addSql1 += " " + addSql2;


							String tempsql = "select employeeID from employeeinfo;";

							prs = connex.prepareStatement(addSql1);
							prs.execute();

							prs = connex.prepareStatement(tempsql);
							result = prs.executeQuery();
							while (!result.isLast()) {
								result.next();
							}

							String id = result.getString("employeeID");
							empID.setText(id);
							Object ob[] = { id, name.getText(), sex, age.getText(),
									bloodgp.getSelectedItem().toString(), contact.getText(), qualification.getText(),
									doj, address.getText() };
							model.addRow(ob);
						}

						catch (Exception exe) {
							exe.printStackTrace();
						} finally {
							try {
								result.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}

				}
				else {
					try {
						String doj = year.getText() + "-" + mounth.getText() + "-" + day.getText();
						String updateSql = "update employeeinfo set ";
						String addSql = "Name=\"" + name.getText() + "\",";
						String sex = "";
						if (male.isSelected()) {
							sex = "Male";
						}
						if (female.isSelected()) {
							sex = "Female";
						}
						addSql += "Gender=\"" + sex + "\",";
						addSql += "Age=" + age.getText() + ",";
						addSql += "BloodGroup=\"" + bloodgp.getSelectedItem().toString() + "\",";
						addSql += "ContactNo=" + contact.getText() + ",";
						addSql += "Qualification=\"" + qualification.getText() + "\",";
						addSql += "DOJ='" + doj + "',";
						addSql += "Address=\"" + address.getText() + "\" where employeeID=" + empID.getText() + ";";

						prs = connex.prepareStatement(updateSql + "" + addSql);
						prs.execute();

						model.setValueAt(name.getText(), table.getSelectedRow(), 1);
						model.setValueAt(sex, table.getSelectedRow(), 2);
						model.setValueAt(age.getText(), table.getSelectedRow(), 3);
						model.setValueAt(bloodgp.getSelectedItem().toString(), table.getSelectedRow(), 4);
						model.setValueAt(contact.getText(), table.getSelectedRow(), 5);
						model.setValueAt(qualification.getText(), table.getSelectedRow(), 6);
						model.setValueAt(doj, table.getSelectedRow(), 7);
						model.setValueAt(address.getText(), table.getSelectedRow(), 8);

						JOptionPane.showMessageDialog(null, "Enregistrement mis à jour avec succès !");

					} catch (Exception exe) {
						exe.printStackTrace();
					} finally {
						try {
							result.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
		});

		// A décrire !!!
		saving.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (empID.getText().equals("")) {
					if (name.getText().equals("") || !male.isSelected() && !female.isSelected()
							|| contact.getText().equals("") || qualification.getText().equals("")
							|| day.getText().equals("") || mounth.getText().equals("") || day.getText().equals("")
							|| address.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Erreur ! Tous les champs doivent être remplis !");
					} else {

						try {
							String doj = year.getText() + "-" + mounth.getText() + "-" + day.getText();
							String addSql1 = "insert into employeeinfo (Name,Gender,Age,BloodGroup,ContactNo,Qualification,DOJ,Address,EmpImage)";
							String addSql2 = "values(\"" + name.getText() + "\",\"";
							String sex = "";
							if (male.isSelected()) {
								sex = "Male";
							}
							if (female.isSelected()) {
								sex = "Female";
							}
							addSql2 += sex + "\"," + age.getText() + ",\"" + bloodgp.getSelectedItem().toString()
									+ "\",";
							addSql2 += contact.getText() + ",\"" + qualification.getText() + "\",'" + doj + "',\""
									+ address.getText() + "\",0);";
							String tempsql = "select employeeID from employeeinfo;";
							addSql1 += " " + addSql2;

							prs = connex.prepareStatement(addSql1);
							prs.execute();
							prs = connex.prepareStatement(tempsql);
							result = prs.executeQuery();
							while (!result.isLast()) {
								result.next();
							}
							String id = result.getString("employeeID");
							empID.setText(id);
							Object ob[] = { id, name.getText(), sex, age.getText(),
									bloodgp.getSelectedItem().toString(), contact.getText(), qualification.getText(),
									doj, address.getText() };
							model.addRow(ob);
						}

						catch (Exception exe) {
							exe.printStackTrace();
						} finally {
							try {
								result.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}

				} else {
					try {
						String doj = year.getText() + "-" + mounth.getText() + "-" + day.getText();
						String updateSql = "update employeeinfo set ";
						String addSql = "Name=\"" + name.getText() + "\",";
						String sex = "";
						if (male.isSelected()) {
							sex = "Male";
						}
						if (female.isSelected()) {
							sex = "Female";
						}
						addSql += "Gender=\"" + sex + "\",";
						addSql += "Age=" + age.getText() + ",";
						addSql += "BloodGroup=\"" + bloodgp.getSelectedItem().toString() + "\",";
						addSql += "ContactNo=" + contact.getText() + ",";
						addSql += "Qualification=\"" + qualification.getText() + "\",";
						addSql += "DOJ='" + doj + "',";
						addSql += "Address=\"" + address.getText() + "\" where employeeID=" + empID.getText() + ";";
						prs = connex.prepareStatement(updateSql + "" + addSql);
						prs.execute();
						model.setValueAt(name.getText(), table.getSelectedRow(), 1);
						model.setValueAt(sex, table.getSelectedRow(), 2);
						model.setValueAt(age.getText(), table.getSelectedRow(), 3);
						model.setValueAt(bloodgp.getSelectedItem().toString(), table.getSelectedRow(), 4);
						model.setValueAt(contact.getText(), table.getSelectedRow(), 5);
						model.setValueAt(qualification.getText(), table.getSelectedRow(), 6);
						model.setValueAt(doj, table.getSelectedRow(), 7);
						model.setValueAt(address.getText(), table.getSelectedRow(), 8);
						JOptionPane.showMessageDialog(null, "Enregistrement mis à jour avec succès !");

					} catch (Exception exe) {
						exe.printStackTrace();
					} finally {
						try {
							result.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			}
		});


		// A décrire !!!
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!empID.getText().equals("")) {
					int rep = JOptionPane.showConfirmDialog(null,
							"Suppression de " + name.getName() + " aves ID = " + empID.getText() + "?");
					if (rep == JOptionPane.YES_OPTION) {
						try {
							String deleteSql = "delete from employeeinfo where employeeID=" + empID.getText();
							prs = connex.prepareStatement(deleteSql);
							prs.execute();
							model.removeRow(table.getSelectedRow());
							frameRefresh();
							JOptionPane.showMessageDialog(null, "Employé supprimer avec succès !");
						} catch (Exception exe) {
							exe.printStackTrace();
						} finally {
							try {
								result.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Aucun enregistrement à supprimer!");

				}

			}

		});

		// A décrire !!!
		deleting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!empID.getText().equals("")) {
					int rep = JOptionPane.showConfirmDialog(null,
							"Suppression de " + name.getName() + " avec ID = " + empID.getText() + "?");
					if (rep == JOptionPane.YES_OPTION) {
						try {
							String deleteSql = "delete from employeeinfo where employeeID=" + empID.getText();
							prs = connex.prepareStatement(deleteSql);
							prs.execute();
							model.removeRow(table.getSelectedRow());
							frameRefresh();
							JOptionPane.showMessageDialog(null, "Enregistrement Employé supprimé avec succès !");
						} catch (Exception exe) {
							exe.printStackTrace();
						} finally {
							try {
								result.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Pas d'enregistrement à supprimer !");

				}

			}

		});

		// A décrire !!!
		clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				empID.setText("");
				name.setText("");
				male.setSelected(false);
				female.setSelected(false);
				age.setText("");
				bloodgp.setSelectedItem("");
				contact.setText("");
				qualification.setText("");
				day.setText("");
				mounth.setText("");
				year.setText("");
				address.setText("");
				image.setIcon(null);
				JOptionPane.showMessageDialog(null, "Initialiser !");

			}
		});

		// A décrire !!!
		cleaning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				empID.setText("");
				name.setText("");
				male.setSelected(false);
				female.setSelected(false);
				age.setText("");
				bloodgp.setSelectedItem("");
				contact.setText("");
				qualification.setText("");
				day.setText("");
				mounth.setText("");
				year.setText("");
				address.setText("");
				image.setIcon(null);
				JOptionPane.showMessageDialog(null, "Initialiser !");

			}
		});
		
		// A décrire !!!
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});
		
		// A décrire !!!
		exitMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// A décrire !!!
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				String doj[] = model.getValueAt(table.getSelectedRow(), 7).toString().split("-");

				empID.setText(model.getValueAt(table.getSelectedRow(), 0).toString());
				name.setText(model.getValueAt(table.getSelectedRow(), 1).toString());
				if (model.getValueAt(table.getSelectedRow(), 2).toString().toLowerCase().equals("male")) {
					male.setSelected(true);
					female.setSelected(false);
				}
				if (model.getValueAt(table.getSelectedRow(), 2).toString().toLowerCase().equals("female")) {
					male.setSelected(false);
					female.setSelected(true);
				}
				age.setText(model.getValueAt(table.getSelectedRow(), 3).toString());
				bloodgp.setSelectedItem(model.getValueAt(table.getSelectedRow(), 4).toString());
				contact.setText(model.getValueAt(table.getSelectedRow(), 5).toString());
				qualification.setText(model.getValueAt(table.getSelectedRow(), 6).toString());
				address.setText(model.getValueAt(table.getSelectedRow(), 8).toString());
				year.setText(doj[0]);
				mounth.setText(doj[1]);
				day.setText(doj[2]);
				String imageGet = "select EmpImage from employeeinfo where employeeID=" + empID.getText();
				try {
					prs = connex.prepareStatement(imageGet);
					result = prs.executeQuery();
					result.next();
					//String img = result.getString("EmpImage") + "p";
					//image.setIcon(new ImageIcon("pics/" + img + ".jpg"));
					// prendre l'image dans la base de données
					byte[] images = null;
					images = result.getBytes("EmpImage");
					
					// creer l'image
					Image img = Toolkit.getDefaultToolkit().createImage(images);
					ImageIcon icon = new ImageIcon(img);
					
					image.setIcon(icon); // Affiche l'image dans le JLabel image
				} catch (Exception exp) {
					exp.printStackTrace();
				} finally {
					try {
						result.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
		});
	}

	// A décrire !!!
	private void frameRefresh() {
		repaint();
	}

	// A décrire !!!
	private void loading_DB() {
		try {
			String sql = "select * from employeeinfo";
			prs = connex.prepareStatement(sql);
			result = prs.executeQuery();
			while (result.next()) {
				String empID = result.getString("employeeID");
				String name = result.getString("Name");
				String gender = result.getString("Gender");
				String age = result.getString("Age");
				String bloodgroup = result.getString("BloodGroup");
				String contactno = result.getString("ContactNo");
				String qualification = result.getString("Qualification");
				String doj = result.getString("DOJ");
				String address = result.getString("Address");

				Object ob[] = { empID, name, gender, age, bloodgroup, contactno, qualification, doj, address };
				model.addRow(ob);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void componentlook() {
		Font ft = new Font("Tahoma", 0, 12);
		empID.setEditable(false);
		empID.setForeground(Color.gray);
		empID.setFont(ft);
		name.setFont(ft);
		age.setFont(ft);
		contact.setFont(ft);
		bloodgp.setFont(ft);
		address.setFont(ft);
		qualification.setFont(ft);
		day.setFont(ft);
		mounth.setFont(ft);
		year.setFont(ft);

		nametxt.setFont(ft);
		gendertxt.setFont(ft);
		agetxt.setFont(ft);
		bloodgptxt.setFont(ft);
		contacttxt.setFont(ft);
		qualificationtxt.setFont(ft);
		addresstxt.setFont(ft);
		dojtxt.setFont(ft);
		empIDtxt.setFont(ft);

		empID.setColumns(6);
		name.setColumns(20);
		age.setColumns(3);
		contact.setColumns(10);
		address.setColumns(20);
		qualification.setColumns(20);
		day.setColumns(2);
		mounth.setColumns(2);
		year.setColumns(4);

		table.setDragEnabled(false);
		table.setGridColor(Color.black);

	}

	private void initComponent() {
		Color high = new Color(255, 255, 255);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		imagePane.add(image);
		image.setSize(new Dimension(screenSize.width , screenSize.height));
		mainPane.setBackground(high);
		tablePane.setBackground(mainPane.getBackground());
		imagePane.setBackground(mainPane.getBackground());
		fieldPane.setBackground(mainPane.getBackground());
		datePane.setBackground(mainPane.getBackground());

		add.setIcon(new ImageIcon("icons/add.png"));
		save.setIcon(new ImageIcon("icons/save.png"));
		delete.setIcon(new ImageIcon("icons/delete.png"));
		clean.setIcon(new ImageIcon("icons/clean.png"));

		int size = GroupLayout.PREFERRED_SIZE;
		datePane.add(yeartxt);
		datePane.add(year);
		datePane.add(mounthtxt);
		datePane.add(mounth);
		datePane.add(daytxt);
		datePane.add(day);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(mainPane);
		tablePane.setBackground(mainPane.getBackground());
		fieldPane.setBackground(mainPane.getBackground());
		setJMenuBar(menu);
		menu.add(file);
		menu.add(edit);
		file.add(exitMenu);
		edit.add(adding);
		edit.add(saving);
		edit.add(deleting);
		edit.add(cleaning);

		model.setColumnCount(9);
		model.setColumnIdentifiers(title);

		GroupLayout mainGroup = new GroupLayout(mainPane);
		GroupLayout tableGroup = new GroupLayout(tablePane);
		GroupLayout fieldGroup = new GroupLayout(fieldPane);

		fieldPane.setLayout(fieldGroup);
		fieldGroup.setHorizontalGroup(fieldGroup.createSequentialGroup()
				.addGroup(fieldGroup.createParallelGroup(Alignment.LEADING).addComponent(empIDtxt, size, size, size)
						.addComponent(nametxt, size, size, size).addComponent(gendertxt, size, size, size)
						.addComponent(agetxt, size, size, size).addComponent(bloodgptxt, size, size, size)
						.addComponent(contacttxt, size, size, size).addComponent(qualificationtxt, size, size, size)
						.addComponent(dojtxt, size, size, size).addComponent(addresstxt, size, size, size)

				)
				.addGroup(fieldGroup.createParallelGroup(Alignment.LEADING).addComponent(empID, size, size, size)
						.addComponent(name, size, size, size)
						.addGroup(fieldGroup.createSequentialGroup().addComponent(male, size, size, size)
								.addComponent(female, size, size, size))
						.addComponent(age, size, size, size).addComponent(bloodgp, size, size, size)
						.addComponent(contact, size, size, size).addComponent(qualification, size, size, size)
						.addComponent(datePane, size, size, size).addComponent(address, size, size, size)

				));

		fieldGroup
				.setVerticalGroup(fieldGroup.createSequentialGroup()
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(empIDtxt, size, size, size).addComponent(empID, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(nametxt, size, size, size).addComponent(name, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(gendertxt, size, size, size)
								.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
										.addComponent(male, size, size, size).addComponent(female, size, size, size)))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(agetxt, size, size, size).addComponent(age, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(bloodgptxt, size, size, size).addComponent(bloodgp, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(contacttxt, size, size, size).addComponent(contact, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(qualificationtxt, size, size, size)
								.addComponent(qualification, size, size, size))
						.addGap(2, 5, 10)
						.addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(dojtxt, size, size, size).addComponent(datePane, size, size, size))

						.addGap(2, 5, 10).addGroup(fieldGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(addresstxt, size, size, size).addComponent(address, size, size, size)));
		fieldPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Infos Fields",
				javax.swing.border.TitledBorder.ABOVE_TOP, javax.swing.border.TitledBorder.TOP,
				new Font("Tahoma", 0, 14), Color.black));

		tablePane.setLayout(tableGroup);
		tableGroup.setHorizontalGroup(
				tableGroup.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scroll, size, 640, 1000)
						.addGroup(tableGroup.createSequentialGroup().addComponent(add, size, size, size)
								.addComponent(save, size, size, size).addComponent(delete, size, size, size)
								.addComponent(clean, size, size, size)));
		tableGroup.setVerticalGroup(tableGroup.createSequentialGroup().addComponent(scroll, size, size, 640)
				.addGroup(tableGroup.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(add, size, size, size).addComponent(save, size, size, size)
						.addComponent(delete, size, size, size).addComponent(clean, size, size, size)));

		mainPane.setLayout(mainGroup);
		mainGroup.setHorizontalGroup(
				mainGroup.createSequentialGroup().addComponent(tablePane, size, 800, 1100).addGap(10, 40, 60)
						.addGroup(mainGroup.createParallelGroup(Alignment.CENTER)
								.addComponent(fieldPane, size, size, size).addComponent(imagePane, 300, 300, 300)
								.addComponent(close, size, size, size)));
		mainGroup.setVerticalGroup(
				mainGroup.createParallelGroup(Alignment.LEADING).addComponent(tablePane, size, 640, 640)
						.addGroup(mainGroup.createSequentialGroup().addComponent(fieldPane, size, size, size)
								.addComponent(imagePane, 300, 300, 300).addComponent(close, size, size, size)));
		tablePane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des employés",
				javax.swing.border.TitledBorder.ABOVE_TOP, javax.swing.border.TitledBorder.TOP,
				new Font("Tahoma", 0, 14), Color.black));
		imagePane.setBorder(
				javax.swing.BorderFactory.createTitledBorder(null, "Picture", javax.swing.border.TitledBorder.ABOVE_TOP,
						javax.swing.border.TitledBorder.TOP, new Font("Tahoma", 0, 14), Color.black));

		pack();
	}
}