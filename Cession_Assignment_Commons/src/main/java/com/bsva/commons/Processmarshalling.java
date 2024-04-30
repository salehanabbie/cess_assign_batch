package com.bsva.commons;


import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public class Processmarshalling {
	
	
		public static void main(String[] args) throws JAXBException, IOException {
			 
			 JAXBContext jc = JAXBContext.newInstance("iso.std.iso._20022.tech.xsd.pain_009_001");
		        Marshaller marshaller = jc.createMarshaller();
		        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 
		        // Create Domain Objects
//		        Document doc = new Document();
//		        MandateInitiationRequestV04 mandate = new MandateInitiationRequestV04();
//		        GroupHeader47 group = new GroupHeader47();
//		        group.setMsgId("Messageid");
//		        
//		        mandate.setGrpHdr(group);
//		        
//		        doc.setMndtInitnReq(mandate);
//		 
//		        // Marshal Customer
//		 
//		        // Marshal Billing Address
//		        ObjectFactory objectFactory = new ObjectFactory();
//		        JAXBElement<Document> je =  objectFactory.createDocument(doc);
//		        marshaller.marshal(je, System.out);
		 }

}
