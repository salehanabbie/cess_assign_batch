package com.bsva.commons;



import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;


public class ProcessUnmashalling {

	public static void main(String[] args) {

//		JAXBContext jc;
//		
//		try {
//			jc = JAXBContext.newInstance("iso.std.iso._20022.tech.xsd.pain_009_001");
//		
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
// 
//        // Unmarshal the objects
//        File pathXML = new File("src/main/resources/PAIN009XML.xml");
//        
//        Document doc = (Document) JAXBIntrospector.getValue(unmarshaller.unmarshal(pathXML));
//        
//        String str = doc.getMndtInitnReq().getGrpHdr().getMsgId();        
//        XMLGregorianCalendar str2 = doc.getMndtInitnReq().getGrpHdr().getCreDtTm();
//        
//        System.out.println(str);
//        
//        System.out.println(str2);
//        
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
