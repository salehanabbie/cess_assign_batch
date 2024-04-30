package com.bsva.ws.mandates.impl;


import com.bsva.ws.mandates.Mandates;
import com.bsva.ws.mandates.models.EftMandateModel;
import com.bsva.ws.mandates.models.ResultModel;
import com.bsva.ws.mandates.models.SessionInfoModel;

//import java.util.List;
import java.util.Map;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService(endpointInterface = "com.bsva.ws.mandates.Mandates")
public class MandatesImpl implements Mandates {
	private final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(MandatesImpl.class);
	
	@Override
	public ResultModel checkMandate(SessionInfoModel sessionInfo, EftMandateModel eftMandate)
	{
		// TODO investigate Spring Webservice / proper SOA WS
		ResultModel result = new ResultModel();

		try
		{
			result.setCode("1000");
			result.setDescription("checkMandate Failed");
			
			Map<Boolean, String> cdvMap = null;
			try {
                //TODO check if session is valid in IAM via ws call
                if(sessionInfo.getSessionKey() == null || sessionInfo.getSessionKey().isEmpty()) {
                    result.setCode("1100");
                    result.setDescription("Session Invalid - Please login to continue");

                    return result;
                }
                //TODO check if session still active
			} catch (Exception e) {
				log.error("checkMandate : " + e);
				e.printStackTrace();
				
				result.setCode("9001");
				result.setDescription("System Error Occurred calling checkMandate.Authenticate");
				
				return result;
			}
			log.debug("checkMandate.authMap");
			try {
				log.debug("checkMandate.eftOneServiceBeanRemote.cdvAccount");
							
				//TODO put logic for checking mandates here
			} catch (Exception e) {
				log.error("checkMandate : " + e);
				e.printStackTrace();
							
				result.setCode("9002");
				result.setDescription("System Error Occurred calling checkMandate.cdvAccount");
							
				return result;
			}
			log.debug("checkMandate.result" + result.getCode());

			return result;
		} catch (Exception e) {
			log.error("checkMandate : " + e);
			e.printStackTrace();
			
			result.setCode("9000");
			result.setDescription("System Error Occurred in checkMandate routine");
			
			return result;
		}
	}

//	@Override
//    public List<ResultModel> checkBulkMandates(SessionInfoModel sessionInfo, List<EftMandateModel> eftMandates)
//    {
        // TODO investigate Spring Webservice / proper SOA WS
//        List<ResultModel> results = new ArrayList<ResultModel>();

//        try
//        {
//            Map<Boolean, String> cdvMap = null;
//            try {
                //TODO check if session is valid in IAM via ws call
//                if(sessionInfo.getSessionKey() == null || sessionInfo.getSessionKey().isEmpty()) {
//                    ResultModel result = new ResultModel();

//                    result.setCode("1100");
//                    result.setDescription("Session Invalid - Please login to continue");

//                    results.add(result);
//                    return results;
//                }
//            } catch (Exception e) {
//                log.error("checkBulkMandates : " + e);
//                e.printStackTrace();

//                ResultModel result = new ResultModel();
//                result.setCode("9001");
//                result.setDescription("System Error Occurred Calling checkBulkMandates.Authenticate");

//                results.add(result);
//                return results;
//            }
//            log.debug("checkBulkMandates.authMap");
//            try {
//                log.debug("checkBulkMandates.eftOneServiceBeanRemote.cdvAccount");

//                for(EftMandateModel eftMandate : eftMandates ) {
//                    ResultModel result = new ResultModel();
//                    result.setCode("1000");
//                    result.setDescription("Mandate Validation Failed");

                    //cdvMap = eftOneServiceBeanRemote.cdvAccount(cdv, sessionInfo.getUserName());
//                    if(cdvMap != null && cdvMap.size() > 0 ) {
//                        for (Entry<Boolean, String> cdvEntry : cdvMap.entrySet()) {
//                            log.debug("checkBulkMandates.cdvMap *");

//                            if (cdvEntry.getKey() == false) {
//                                result.setCode("1001");
//                                result.setDescription(cdvEntry.getValue());
//                            }else{
//                                result.setCode("0000");
//                                result.setDescription("Mandate Validation Passed");
//                            }
//                            results.add(result);
//                        }
//                    }
//                    else
//                    {
//                        result.setCode("8002");
//                        result.setDescription("MAndate Details Provided Incorrect");

//                        results.add(result);
//                    }
//                    log.debug("checkBulkMandates.result" + result.getCode());
//                }
//                return results;
//           } catch (Exception e) {
//                log.error("checkBulkMandates : " + e);
//                e.printStackTrace();

//                ResultModel result = new ResultModel();
//                result.setCode("9001");
//                result.setDescription("System Error Occurred calling checkBulkMandates.cdvAccount");

//                results.add(result);

//                return results;
//            }
//        } catch (Exception e) {
//            log.error("checkBulkMandates : " + e);
//            e.printStackTrace();

//            ResultModel result = new ResultModel();
//            result.setCode("9000");
//            result.setDescription("System Error Occurred in checkBulkMandates routine");

//            results.add(result);

//           return results;
//        }
//    }
}
