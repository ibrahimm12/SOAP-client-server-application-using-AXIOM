package db.service;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;

import org.apache.axiom.om.OMElement;

import org.apache.axiom.om.OMFactory;

import org.apache.axiom.om.OMNamespace;

public class SOAPDBService {

	public OMElement GetDBQueryResult(OMElement element) throws XMLStreamException {

		// Here we build the xml tree using AXIOM API for the request element

		element.build();

		// Here we detach the node from its parent container
		element.detach();

		// Here we get the first element, which is personToGreet .

		OMElement firstElement = element.getFirstElement();

		String nameSpaceURI = firstElement.getQName().getNamespaceURI();

		String userName = firstElement.getAttributeValue(new QName(nameSpaceURI, "user_name"));
		String password = firstElement.getAttributeValue(new QName(nameSpaceURI, "password"));
		String name = firstElement.getAttributeValue(new QName(nameSpaceURI, "name"));
		String date = firstElement.getAttributeValue(new QName(nameSpaceURI, "date"));
		String comment = firstElement.getAttributeValue(new QName(nameSpaceURI, "comment"));
		


		// Here we get the value for " personToGreet " element.
		String query = firstElement.getText();

		// Here we instantiate the DBQuery classa.
		DBQuery queryDB = new DBQuery(userName, password);

		// Here we call queryDB.executeQuery() method to execute
		// the query and then save the results to queryResults.
		String queryResults = queryDB.executeQuery(query,name,date,comment);

		// In the following we build a response.
		OMFactory omFactory = OMAbstractFactory.getOMFactory();

		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.db/xsd", "dbs");

		OMElement methodElement = omFactory.createOMElement("GetQueryResultResponse", omNameSpace);

		OMElement responseElement = omFactory.createOMElement("queryResult", omNameSpace);

		// Here we add the greeting text to "greeting" element.

		responseElement.setText(queryResults);
		/*
		 * responseElement.addChild(omFactory.createOMText(responseElement,
		 * queryResults));
		 */

		methodElement.addChild(responseElement);

		return methodElement;

	}
	
	public OMElement GetGuestResult(OMElement element) throws XMLStreamException {

		

		element.build();

		
		element.detach();


		OMElement firstElement = element.getFirstElement();

		String nameSpaceURI = firstElement.getQName().getNamespaceURI();

		String userName = firstElement.getAttributeValue(new QName(nameSpaceURI, "user_name"));
		String password = firstElement.getAttributeValue(new QName(nameSpaceURI, "password"));
		String name = firstElement.getAttributeValue(new QName(nameSpaceURI, "name"));
		
		String query1 = firstElement.getText();
		String query = query1+name+"'";
	
		DBQuery queryDB = new DBQuery(userName, password);

		String queryResults = queryDB.executeQuery1(query);

		OMFactory omFactory = OMAbstractFactory.getOMFactory();

		OMNamespace omNameSpace = omFactory.createOMNamespace("http://service.db/xsd", "dbs");

		OMElement methodElement = omFactory.createOMElement("GetGuestResultResponse", omNameSpace);

		OMElement responseElement = omFactory.createOMElement("GuestResult", omNameSpace);
		

		responseElement.setText(queryResults);
		

		methodElement.addChild(responseElement);

		return methodElement;

	}

}