package com.bsva.businessLogic;

/**
* @author ElelwaniR
*/

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.bsva.commons.model.SystemParameterModel;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.translator.AdminTranslator;

public class SystemsParametersLogic {
	public static Logger log = Logger.getLogger(SystemsParametersLogic.class);
                
                
                public SystemParameterModel   retrieveSystemParameters(CasSysctrlSysParamEntity mdtsystemParameterEntity)
                {
                                SystemParameterModel  localModel = new SystemParameterModel();
                                localModel = new AdminTranslator().translateSystemParameterEntityToCommonsModel(mdtsystemParameterEntity);
                   return localModel;
                }

                public List<SystemParameterModel> retrieveAllSystemsParameters(List<CasSysctrlSysParamEntity> mdtSystemParametersEntity){
                                
                                List<SystemParameterModel> systemsparametersList = new ArrayList<SystemParameterModel>();
                                                
                                                
                                                SystemParameterModel localModel;

                                                for (CasSysctrlSysParamEntity localEntity:  mdtSystemParametersEntity) 
                                                {
                                              

                                                              localModel = new SystemParameterModel();
                                                                localModel = new AdminTranslator().translateSystemParameterEntityToCommonsModel(localEntity);
                                                                systemsparametersList.add(localModel);
                                                }
                                                return systemsparametersList;
                                }

                public CasSysctrlSysParamEntity addSystemsParameters(SystemParameterModel systemParametersModel) {
                                
                                
                    CasSysctrlSysParamEntity mdtsysctrlsysparamEntity = new  AdminTranslator().translateCommonsSystemParameterModelToEntity(systemParametersModel);
                

                                return mdtsysctrlsysparamEntity;
                }

								
                                
}

                
                
                
                
                
                
                
                
                


