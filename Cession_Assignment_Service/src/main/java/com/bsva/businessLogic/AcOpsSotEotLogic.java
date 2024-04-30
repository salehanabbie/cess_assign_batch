package com.bsva.businessLogic;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.bsva.commons.model.AcOpsSotEotCntrlModel;
import com.bsva.commons.model.IncSotEotModel;
import com.bsva.commons.model.OutSotEotModel;
import com.bsva.entities.IncSotEotEntityModel;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.OutSotEotEntityModel;
import com.bsva.translator.AdminTranslator;

public class AcOpsSotEotLogic 
{
	public static Logger log = Logger.getLogger(AcOpsSotEotLogic.class);
	
	public AcOpsSotEotLogic()
	{
		
	}

	public List<AcOpsSotEotCntrlModel> retrieveAllMdtAcOpsSotEotCtrlEntityList(List<CasOpsSotEotCtrlEntity> casOpsSotEotCtrlEntityList)
	{
      List<AcOpsSotEotCntrlModel> acOpsSotEotCntrlModelList = new ArrayList<AcOpsSotEotCntrlModel>();
      AcOpsSotEotCntrlModel acOpsSotEotCntrlModel;
      for (CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity : casOpsSotEotCtrlEntityList)
      {	
			acOpsSotEotCntrlModel = new AcOpsSotEotCntrlModel();
			acOpsSotEotCntrlModel = new AdminTranslator().translateAcOpsSotEotCntrlModelToEntity(
                casOpsSotEotCtrlEntity);
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
	
	public CasOpsSotEotCtrlEntity addMdtAcOpsSotEotCtrlEntity(AcOpsSotEotCntrlModel acOpsSotEotCntrlModel)
	{
		CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new AdminTranslator().translateMdtAcOpsSotEotCtrlEntityToModel(acOpsSotEotCntrlModel);
		return casOpsSotEotCtrlEntity;
	}
	
	public AcOpsSotEotCntrlModel retrieveMdtAcOpsSotEotCtrlEntity(
        CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity)
	{
		AcOpsSotEotCntrlModel localModel = new AcOpsSotEotCntrlModel();
		localModel = new AdminTranslator(). translateAcOpsSotEotCntrlModelToEntity(
            casOpsSotEotCtrlEntity);
		return localModel;
	}
	
	public OutSotEotModel retrieveAllOutSotEotEntityList(OutSotEotEntityModel outEntityModel) 
	{	
		OutSotEotModel outSotEotModel= new OutSotEotModel();
		outSotEotModel = new AdminTranslator().translateOutCommonsToIncSotEotEntityModel(outEntityModel);
		return outSotEotModel;	
	}
	
}
