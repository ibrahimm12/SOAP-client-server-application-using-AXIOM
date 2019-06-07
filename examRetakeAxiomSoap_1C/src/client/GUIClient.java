package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class GUIClient extends JFrame implements ActionListener {

	Container contentPane;
	JPanel inputPanel, buttonPanel, outputPanel;
	JScrollPane scrollPane;
	JTable resultTable;
	JLabel userNameLabel, passwordLabel, queryLabel,resultLable,queryLabel1,resultLable1, 
	targetEPRLabel,nameLabel,dateLabel,commentLabel,senameLabel;
	JTextField userNameTextField, queryTextField, targetEPRTextField,resultTextField,newqueryTextField,
	nameTextField,dateTextField,commentTextField,senameTextField,resultTextField1;
	JPasswordField passwordTextField;
	JButton submitButton;

	GridBagLayout gridBagLayOut;
	GridBagConstraints gridBagConstraints;

	private boolean tableAdded = false;

	public GUIClient(String title) {

		super(title);

		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));

		userNameLabel = new javax.swing.JLabel("User name");
		inputPanel.add(userNameLabel);
		userNameTextField = new JTextField();
		inputPanel.add(userNameTextField);

		passwordLabel = new JLabel("Password");
		inputPanel.add(passwordLabel);

		passwordTextField = new JPasswordField();
		inputPanel.add(passwordTextField);
		
		nameLabel = new javax.swing.JLabel("Name");
		inputPanel.add(nameLabel);
		nameTextField = new JTextField();
		inputPanel.add(nameTextField);
		
		dateLabel = new javax.swing.JLabel("Date");
		inputPanel.add(dateLabel);
		dateTextField = new JTextField();
		inputPanel.add(dateTextField);
		
		commentLabel = new javax.swing.JLabel("Comment");
		inputPanel.add(commentLabel);
		commentTextField = new JTextField();
		inputPanel.add(commentTextField);
		
		
		queryLabel = new JLabel("SQL Query");
		inputPanel.add(queryLabel);

		queryTextField = new JTextField("insert into ws_ex_1 values (?, ?, ?)");
		inputPanel.add(queryTextField);
		
		senameLabel = new javax.swing.JLabel("Select name");
		inputPanel.add(senameLabel);
		senameTextField = new JTextField();
		inputPanel.add(senameTextField);
		
		queryLabel1 = new JLabel("SQL Query1");
		inputPanel.add(queryLabel1);
		newqueryTextField = new JTextField("select name from ws_ex_1 where name ='");
		inputPanel.add(newqueryTextField);

		targetEPRLabel = new JLabel("Service URL");
		inputPanel.add(targetEPRLabel);

		targetEPRTextField = new JTextField("http://app3.cc.puv.fi/axis2_177/services/e1401184_4_23testing1");

		inputPanel.add(targetEPRTextField);
                                     
		buttonPanel = new JPanel(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		submitButton = new JButton("Submit");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.ipady = 20;
		gridBagConstraints.ipadx = 20;
		buttonPanel.add(submitButton, gridBagConstraints);

		submitButton.addActionListener(this);

		contentPane.add(inputPanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		// Here we make sure that the application stops
		// after closing its window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Here we set the size of the window and make
		// it visible.
		this.setSize(new Dimension(400, 300));
		this.setVisible(true);
		// this.setResizable(false);

	}

	private String getServiceResults(EndpointReference targetEPR, String userName, String password,
			String name,String date,String comment,String query) {

		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient client;
		String response = "";

		try {

			client = new ServiceClient();

			client.setOptions(options);

			// String query = "";

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.db/xsd", "dbs");

			OMElement methodElement = omFactory.createOMElement("GetDBQueryResult", omNameSpace);

			OMElement argumentElement = omFactory.createOMElement("query", omNameSpace);

			argumentElement.addAttribute("user_name", userName, omNameSpace);
			argumentElement.addAttribute("password", password, omNameSpace);
			argumentElement.addAttribute("name", name, omNameSpace);
			argumentElement.addAttribute("date", date, omNameSpace);
			argumentElement.addAttribute("comment", comment, omNameSpace);
			argumentElement.setText(query);
			// argumentElement.addChild(omFactory.createOMText(argumentElement,
			// query));

			methodElement.addChild(argumentElement);
			
			
			
			// Here we call the service and save the results to an
			// OMElement object.
			OMElement responseElement = client.sendReceive(methodElement);
			// Here we get the text node of the result element
			response = responseElement.getFirstElement().getText();

			// System.out.println(response);

		} catch (AxisFault e) {

			JOptionPane.showMessageDialog(null, "Error with creating ServiceClient");
		}

		return response;
	}
	
	private String getGuestResults(EndpointReference targetEPR, String userName, String password,
			String sename,String query1) {

		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient client;
		String response = "";

		try {

			client = new ServiceClient();

			client.setOptions(options);

			// String query = "";

			OMFactory omFactory = OMAbstractFactory.getOMFactory();

			OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.db/xsd", "dbs");

			OMElement methodElement = omFactory.createOMElement("GetGuestResult", omNameSpace);

			OMElement argumentElement = omFactory.createOMElement("query", omNameSpace);

			argumentElement.addAttribute("user_name", userName, omNameSpace);
			argumentElement.addAttribute("password", password, omNameSpace);
			argumentElement.addAttribute("sename", sename, omNameSpace);


			argumentElement.setText(query1);
			// argumentElement.addChild(omFactory.createOMText(argumentElement,
			// query));

			methodElement.addChild(argumentElement);
			
			
			
			// Here we call the service and save the results to an
			// OMElement object.
			OMElement responseElement = client.sendReceive(methodElement);
			// Here we get the text node of the result element
			response = responseElement.getFirstElement().getText();

			// System.out.println(response);

		} catch (AxisFault e) {

			JOptionPane.showMessageDialog(null, "Error with creating ServiceClient");
		}

		return response;
	}


	public void actionPerformed(ActionEvent e) {

		String targetEPRText = targetEPRTextField.getText();
		String userName = userNameTextField.getText();
		String name = nameTextField.getText();
		String date = dateTextField.getText();
		String comment = commentTextField.getText();
		String sename=senameTextField.getText();         
		String query1=newqueryTextField.getText();
		char[] passwordChars = passwordTextField.getPassword();
		String password = "";
		for (char c : passwordChars)
			password += c;

		String query = queryTextField.getText();

		// Here we get the results of query

		if (targetEPRText.isEmpty() || userName.isEmpty() || password.isEmpty() || query.isEmpty()||name.isEmpty()
				||date.isEmpty()||comment.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Some data is missing!");
			return;
		}

		EndpointReference targetEPR = new EndpointReference(targetEPRText);

		String result = getServiceResults(targetEPR, userName, password,name,date,comment,query);
		
		String guest_re=getGuestResults(targetEPR, userName, password,sename,query1);

		outputPanel=new JPanel();
		outputPanel.setLayout(new GridLayout(2, 2));
		resultLable = new javax.swing.JLabel("Result");
		outputPanel.add(resultLable);
		resultTextField = new JTextField(result);
		outputPanel.add(resultTextField);
		resultLable1 = new javax.swing.JLabel("Guest Result");
		outputPanel.add(resultLable1);
		resultTextField1 = new JTextField(guest_re);
		outputPanel.add(resultTextField1);
		
		if (outputPanel != null)
			contentPane.remove(outputPanel);

		

		contentPane.add(outputPanel, BorderLayout.CENTER);

		Graphics g = contentPane.getGraphics();
		paintAll(g);

	}

	public static void main(String[] args) throws AxisFault {

		// Here we present data as a table.
		new GUIClient("Axiom Database Communication");

	}

}