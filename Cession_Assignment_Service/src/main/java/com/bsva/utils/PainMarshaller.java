/**
 * 
 */
package com.bsva.utils;

import iso.std.iso._20022.tech.xsd.pacs_002_001.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class PainMarshaller {

	/**
	 * Marshal the object
	 * @param object
	 * @param String
	 * @return String. 
	 * @throws FileNotFoundException 
	 */
	
//	public static Object marshallFile(Object obj, String out, String xsdHome) throws FileNotFoundException {
	public static Object marshallFile(Object object, File file, String urn,String schemaPath) throws FileNotFoundException {

		return marshal(object, file, urn, schemaPath);

	}

	/**
	 * Marshal the object
	 * @param object
	 * @return
	 * @throws FileNotFoundException 
	 */
//	private static <T> String marshal(T object, String F, String xsdHome) throws FileNotFoundException {
	private static <T> String marshal(Object object, File file, String urn,String schemaPath) 
	{
		//OLD CODE
//		try 
//		{
//			OutputStream outputStream = new FileOutputStream(F);
//			JAXBContext jc = JAXBContext.newInstance(xsdHome);
//			Marshaller m = jc.createMarshaller();
//			m.marshal(object, outputStream);
//			return outputStream.toString();
//		} 
//		catch (JAXBException e) 
//		{
//			e.printStackTrace();
//		}
		
		try
		{
			 	SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				Schema schema = sf.newSchema(new File(schemaPath));
			
				JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
	           Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	           
	           jaxbMarshaller.setSchema(schema);
	            jaxbMarshaller.setEventHandler(new UnmarshallEventHandler());
	           
	        // format the XML output
	           jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	           
	           QName qName = new QName(urn, "Document");
	           
	           Document doc = (Document) object;
	           JAXBElement<Document> root = new JAXBElement<Document>(qName, Document.class, doc);

	           jaxbMarshaller.marshal(root, file);
	           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
