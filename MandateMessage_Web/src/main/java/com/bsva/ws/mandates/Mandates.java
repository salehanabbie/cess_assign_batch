package com.bsva.ws.mandates;

//import java.util.List;

import com.bsva.ws.mandates.models.EftMandateModel;
import com.bsva.ws.mandates.models.ResultModel;
import com.bsva.ws.mandates.models.SessionInfoModel;

import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
import javax.jws.WebMethod;
import javax.jws.WebService;
 
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface Mandates {

	@WebMethod ResultModel checkMandate(SessionInfoModel sessionInfo, EftMandateModel eftMandate);
	//@WebMethod List<ResultModel> checkBulkMandates(SessionInfoModel sessionInfo, List<EftMandateModel> eftMandates);
}