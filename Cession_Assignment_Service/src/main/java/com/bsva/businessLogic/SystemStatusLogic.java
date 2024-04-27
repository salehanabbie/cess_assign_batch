package com.bsva.businessLogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.translator.AdminTranslator;

public class SystemStatusLogic implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(SystemStatusLogic.class);
	
	public SystemStatusLogic()
	{
		
	}
	
	public List<MandatesCountCommonsModel> retrieveAllSystemStatus(List<CasOpsMndtCountEntity> countEntity)
	{
			List<MandatesCountCommonsModel> commonsModelList = new ArrayList<MandatesCountCommonsModel >();
			for(CasOpsMndtCountEntity systemStatusEntity : countEntity)
			{
				MandatesCountCommonsModel countCommonsModel = new MandatesCountCommonsModel();
				countCommonsModel = new AdminTranslator().translateCommonsToSystemStatusEntity(systemStatusEntity);
				commonsModelList.add(countCommonsModel);
			}
			log.debug("commonsModelList : " + commonsModelList.toString());
			return commonsModelList;
	}
	
	public CasOpsMndtCountEntity addSystemStatus (MandatesCountCommonsModel commonsModel)
	{
		CasOpsMndtCountEntity entitySystemStatus = new AdminTranslator().translateCommonstoEntitySystemStatus(commonsModel);
		return entitySystemStatus;
	}
	
	public MandatesCountCommonsModel retrieveSystemStatusFile (
        CasOpsMndtCountEntity systemStatusEntity)
	{
		MandatesCountCommonsModel commModel = new MandatesCountCommonsModel();
		commModel = new AdminTranslator().translateCommonsToSystemStatusEntity(systemStatusEntity);
		return commModel;
	}
	
}
