package com.bsva.businessLogic;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.entities.IncSotEotEntityModel;
import com.bsva.entities.MdtAcOpsSotEotCtrlEntity;
import com.bsva.entities.OutSotEotEntityModel;
import com.bsva.translator.AdminTranslator;

public class AcOpsSotEotLogic 
{
	public static Logger log = Logger.getLogger(AcOpsSotEotLogic.class);
	
	public AcOpsSotEotLogic()
	{
		
	}

	public List<AcOpsSotEotCntrlModel> retrieveAllMdtAcOpsSotEotCtrlEntityList(List<MdtAcOpsSotEotCtrlEntity> mdtAcOpsSotEotCtrlEntityList)
	{
      List<AcOpsSotEotCntrlModel> acOpsSotEotCntrlModelList = new ArrayList<AcOpsSotEotCntrlModel>();
      AcOpsSotEotCntrlModel acOpsSotEotCntrlModel;
      for (MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity : mdtAcOpsSotEotCtrlEntityList) 
      {	
			acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();
			acOpsSotEotCntrlModel = new AdminTranslator().translateAcOpsSotEotCntrlModelToEntity(mdtAcOpsSotEotCtrlEntity);
			acOpsSotEotCntrlModelList.add(acOpsSotEotCntrlModel);
      }
      return acOpsSotEotCntrlModelList;
	}
	
	public IncSotEotModel retrieveAllIncSotEotEntityList(IncSotEotEntityModel incEntityModel) 
	{	
		IncSotEotModel incSotEotModel= new IncSotEotModel();
		incSotEotModel = new AdminTranslator().translateIncCommonsToIncSotEotEntityModel(incEntityModel);
		return incSotEotModel;	
	}
	
	public MdtAcOpsSotEotCtrlEntity addMdtAcOpsSotEotCtrlEntity(AcOpsSotEotCntrlModel acOpsSotEotCntrlModel) 
	{
		MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity = new AdminTranslator().translateMdtAcOpsSotEotCtrlEntityToModel(acOpsSotEotCntrlModel);
		return mdtAcOpsSotEotCtrlEntity;
	}
	
	public AcOpsSotEotCntrlModel retrieveMdtAcOpsSotEotCtrlEntity(MdtAcOpsSotEotCtrlEntity mdtAcOpsSotEotCtrlEntity)
	{
		AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
		localModel = new AdminTranslator(). translateAcOpsSotEotCntrlModelToEntity(mdtAcOpsSotEotCtrlEntity);
		return localModel;
	}
	
	public OutSotEotModel retrieveAllOutSotEotEntityList(OutSotEotEntityModel outEntityModel) 
	{	
		OutSotEotModel outSotEotModel= new OutSotEotModel();
		outSotEotModel = new AdminTranslator().translateOutCommonsToIncSotEotEntityModel(outEntityModel);
		return outSotEotModel;	
	}
	
}
