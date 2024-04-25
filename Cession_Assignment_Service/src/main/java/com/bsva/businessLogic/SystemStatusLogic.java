package com.bsva.businessLogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.entities.MdtAcOpsMndtCountEntity;
import com.bsva.translator.AdminTranslator;

public class SystemStatusLogic implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(SystemStatusLogic.class);
	
	public SystemStatusLogic()
	{
		
	}
	
	public List<MandatesCountCommonsModel> retrieveAllSystemStatus(List<MdtAcOpsMndtCountEntity> countEntity)
	{
			List<MandatesCountCommonsModel> commonsModelList = new ArrayList<MandatesCountCommonsModel >();
			for(MdtAcOpsMndtCountEntity systemStatusEntity : countEntity)
			{
				MandatesCountCommonsModel countCommonsModel = new MandatesCountCommonsModel();
				countCommonsModel = new AdminTranslator().translateCommonsToSystemStatusEntity(systemStatusEntity);
				commonsModelList.add(countCommonsModel);
			}
			log.debug("commonsModelList : " + commonsModelList.toString());
			return commonsModelList;
	}
	
	public MdtAcOpsMndtCountEntity addSystemStatus (MandatesCountCommonsModel commonsModel)
	{
		MdtAcOpsMndtCountEntity entitySystemStatus = new AdminTranslator().translateCommonstoEntitySystemStatus(commonsModel);
		return entitySystemStatus;
	}
	
	public MandatesCountCommonsModel retrieveSystemStatusFile (MdtAcOpsMndtCountEntity systemStatusEntity)
	{
		MandatesCountCommonsModel commModel = new MandatesCountCommonsModel();
		commModel = new AdminTranslator().translateCommonsToSystemStatusEntity(systemStatusEntity);
		return commModel;
	}
	
}
