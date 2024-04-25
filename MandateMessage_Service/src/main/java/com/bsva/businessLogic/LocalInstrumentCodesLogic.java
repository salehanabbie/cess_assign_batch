package com.bsva.businessLogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.entities.MdtCnfgLocalInstrCodesEntity;
import com.bsva.translator.AdminTranslator;

/**
 * @author salehas
 * 
 */
public class LocalInstrumentCodesLogic {
	public static Logger log = Logger.getLogger(LocalInstrumentCodesLogic.class);

                public LocalInstrumentCodesLogic() {

                }

                public List<LocalInstrumentCodesModel> retrieveAllLocalInstrumentCodes(
                                                List<MdtCnfgLocalInstrCodesEntity> mdtLocalInstrCodesList) {

                                

                                List<LocalInstrumentCodesModel> localInstrCodeList = new ArrayList<LocalInstrumentCodesModel>();
                                LocalInstrumentCodesModel localModel;
                             

                                for (MdtCnfgLocalInstrCodesEntity localEntity : mdtLocalInstrCodesList) {
                                               
                                                 localModel = new LocalInstrumentCodesModel();
                                                localModel = new AdminTranslator()
                                                                                .translateLocalInstrumentCodesEntityToCommonsModel(localEntity);
                                                localInstrCodeList.add(localModel);
                                }

                                return localInstrCodeList;
                }

                public MdtCnfgLocalInstrCodesEntity addLocalInstrumentCode(LocalInstrumentCodesModel localInstrumentCodesModel) 
                {
                                MdtCnfgLocalInstrCodesEntity mdtLocalInstrumentCodesEntity = new AdminTranslator().translateCommonsInstrumentModelToEntity(localInstrumentCodesModel);

                                return mdtLocalInstrumentCodesEntity;
                }

                
                public LocalInstrumentCodesModel retrieveLocalInstrumentCode(MdtCnfgLocalInstrCodesEntity mdtLocalInstrumentCodesEntity)
                {
                                LocalInstrumentCodesModel localModel = new LocalInstrumentCodesModel();
                                localModel = new AdminTranslator().translateLocalInstrumentCodesEntityToCommonsModel(mdtLocalInstrumentCodesEntity);
                                
                                return localModel;
                }

}
