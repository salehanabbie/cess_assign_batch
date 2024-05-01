package com.bsva;

import com.bsva.beans.GenericDAO;
import com.bsva.businessLogic.AcOpsTransCtrlMsgLogic;
import com.bsva.businessLogic.CompanyParametersLogic;
import com.bsva.businessLogic.CustParamLogic;
import com.bsva.businessLogic.ErrorCodesLogic;
import com.bsva.businessLogic.MandatesCountLogic;
import com.bsva.businessLogic.OpsCustParamLogic;
import com.bsva.businessLogic.OpsFileRegLogic;
import com.bsva.businessLogic.SysCisBankLogic;
import com.bsva.businessLogic.SysCisBranchLogic;
import com.bsva.businessLogic.SysCtrlSysParamLogic;
import com.bsva.businessLogic.SysctrlCompParamLogic;
import com.bsva.businessLogic.SysctrlProcessStatusLogic;
import com.bsva.commons.model.AcOpsTransCtrlMsgModel;
import com.bsva.commons.model.BatchOustandingResponseModel;
import com.bsva.commons.model.ConfgErrorCodesModel;
import com.bsva.commons.model.EndOfDayReportCommonsModel;
import com.bsva.commons.model.FileStatusReportModel;
import com.bsva.commons.model.LocalInstrumentCodesModel;
import com.bsva.commons.model.MandateAmendModel;
import com.bsva.commons.model.MandateRejectionModel;
import com.bsva.commons.model.MandatesCountCommonsModel;
import com.bsva.commons.model.MandatesDebtorReportModel;
import com.bsva.commons.model.MandatesExtractReportModel;
import com.bsva.commons.model.MandatesModel;
import com.bsva.commons.model.MandatesRejectedReportModel;
import com.bsva.commons.model.MandatesSearchModel;
import com.bsva.commons.model.MandatesSummaryModel;
import com.bsva.commons.model.MdtSysCtrlSysParamModel;
import com.bsva.commons.model.OpsCustParamModel;
import com.bsva.commons.model.OpsFileRegModel;
import com.bsva.commons.model.OutBalanceCountReportModel;
import com.bsva.commons.model.OutofBalanceModel;
import com.bsva.commons.model.OutstandingRespSummaryCountModel;
import com.bsva.commons.model.ProcessStatusModel;
import com.bsva.commons.model.SchedulerModel;
import com.bsva.commons.model.SysCisBankModel;
import com.bsva.commons.model.SysCisBranchModel;
import com.bsva.commons.model.SysctrlCompParamModel;
import com.bsva.commons.model.SystemParameterReportModel;
import com.bsva.entities.BatchOustandingResponseEntityModel;
import com.bsva.entities.CasConfigDataTimeEntity;
import com.bsva.entities.CasOpsBillingCtrlsEntity;
import com.bsva.entities.CasOpsProcessControlsEntity;
import com.bsva.entities.CasSysctrlCompParamEntity;
import com.bsva.entities.CasSysctrlProcessStatusEntity;
import com.bsva.entities.CasSysctrlSysParamEntity;
import com.bsva.entities.EndOfDayReportsEntityModel;
import com.bsva.entities.FileStatusReportEntityModel;
import com.bsva.entities.InterchgBillingDataModel;
import com.bsva.entities.MandateAmendEntityModel;
import com.bsva.entities.MandateRejectionEntityModel;
import com.bsva.entities.MandatesExtractReportEntityModel;
import com.bsva.entities.MandatesRejectedReportEntityModel;
import com.bsva.entities.MandatesReportsEntityModel;
import com.bsva.entities.MandatesSearchEntityModel;
import com.bsva.entities.MandatesSummaryReportModel;
import com.bsva.entities.MdtAcArcConfDetailsEntity;
import com.bsva.entities.MdtAcArcConfHdrsEntity;
import com.bsva.entities.MdtAcArcStatusDetailsEntity;
import com.bsva.entities.MdtAcArcStatusHdrsEntity;
import com.bsva.entities.CasOpsConfDetailsEntity;
import com.bsva.entities.CasOpsConfHdrsEntity;
import com.bsva.entities.CasOpsDailyBillingEntity;
import com.bsva.entities.CasOpsGrpHdrEntity;
import com.bsva.entities.CasOpsCessionAssignEntity;
import com.bsva.entities.CasOpsMndtCountEntity;
import com.bsva.entities.CasOpsSotEotCtrlEntity;
import com.bsva.entities.CasOpsStatusDetailsEntity;
import com.bsva.entities.CasOpsStatusHdrsEntity;
import com.bsva.entities.CasOpsTransCtrlMsgEntity;
import com.bsva.entities.CasOpsTxnsBillReportEntity;
import com.bsva.entities.CasOpsTxnsBillingEntity;
import com.bsva.entities.CasCnfgErrorCodesEntity;
import com.bsva.entities.CasCnfgLocalInstrCodesEntity;
import com.bsva.entities.CasOpsCustParamEntity;
import com.bsva.entities.CasOpsFileRegEntity;
import com.bsva.entities.MonthlyVolumeCountEntityModel;
import com.bsva.entities.ObsBillingStagingEntity;
import com.bsva.entities.ObsTxnsBillStagingEntity;
import com.bsva.entities.OutBalanceCountReportEntityModel;
import com.bsva.entities.OutOfBalanceReportModel;
import com.bsva.entities.OutstandingRespSummaryCountEntityModel;
import com.bsva.entities.PasaMandateReportEntityModel;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.entities.SysCisBranchEntity;
import com.bsva.entities.SystemParamReportEntityModel;
import com.bsva.file.MonitorDirectory;
import com.bsva.interfaces.ServiceBeanLocal;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.translator.AdminTranslator;
import com.bsva.translator.ServiceTranslator;
import com.bsva.utils.DateUtil;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.osgi.application.ApplicationContext;

/**
 * Session Bean implementation class CurryBean
 */
@Stateless
@Remote(ServiceBeanRemote.class)
public class ServiceBean implements ServiceBeanRemote, ServiceBeanLocal {

	@EJB
	GenericDAO genericDAO;
	public static Logger log = Logger.getLogger(ServiceBean.class);
	String webProcess = "WEB";
	String readyForExtractStatus = "3";
	String extractedStatus = "4";
	String reportToBeProdStatus = "6";
	String respRecievedStatus ="9";
	String matchedStatus = "M";
	String rejectedStatus = "R";
	String reportProducedStatus = "7";
	String loadStatus = "L";
	String receviedFileStatus = "R";
	String validatingFileStatus = "V";
	String fileWaitingStatus = "W";
	Date currentDate = new Date();
	public static int jnlAcqLastRecordId = 0;

	/**
	 * Default constructor.
	 */
	public ServiceBean() {

	}

	private ApplicationContext appContext;

	@Override
	public void startFileListener() {
		MonitorDirectory.monitor();
	}

	@Override
	public boolean createCasOpsGrpHdr(Object obj) {
		try {

			if (obj instanceof CasOpsGrpHdrEntity) {
				CasOpsGrpHdrEntity casOpsGrpHdrEntity = (CasOpsGrpHdrEntity) obj;
				log.info("Service Bean: casOpsGrpHdrEntity: "+casOpsGrpHdrEntity);
				genericDAO.save(casOpsGrpHdrEntity);

				return true;
			} else {
				log.error("Unable to convert type to CasOpsGrpHdrEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on createCasOpsGrpHdr: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> getSysctrlCompParam() {

		List<SysctrlCompParamModel> sysctrlCompParamModel = new ArrayList<SysctrlCompParamModel>();

		List<CasSysctrlCompParamEntity> mdtSysctrlCompParamEntityList = new ArrayList<CasSysctrlCompParamEntity>();

		mdtSysctrlCompParamEntityList = genericDAO.findAll(CasSysctrlCompParamEntity.class);

		if (mdtSysctrlCompParamEntityList.size() > 0) {
			SysctrlCompParamLogic sysctrlCompParamLogic = new SysctrlCompParamLogic();
			sysctrlCompParamModel = sysctrlCompParamLogic.retrieveAllSysctrlCompParam(mdtSysctrlCompParamEntityList);
		}

		return sysctrlCompParamModel;
	}

	@Override
	public List<?> getOpsSysctrlCumParam() {

		List<OpsCustParamModel> opsSysctrlCumParaModel = new ArrayList<OpsCustParamModel>();

		List<CasOpsCustParamEntity> casOpsCustParamEntityList = new ArrayList<CasOpsCustParamEntity>();

		casOpsCustParamEntityList = genericDAO.findAll(CasOpsCustParamEntity.class);

		if (casOpsCustParamEntityList.size() > 0) {
			OpsCustParamLogic opsCustParamLogic = new OpsCustParamLogic();
			opsSysctrlCumParaModel = opsCustParamLogic.retrieveAllOpsCustParam(
					casOpsCustParamEntityList);
		}

		return opsSysctrlCumParaModel;
	}

	@Override
	public boolean updateOpsSysctrlCumPara(Object obj) {
		try {
			if (obj instanceof OpsCustParamModel) {

				OpsCustParamModel opsSysctrlCumParaModel = (OpsCustParamModel) obj;

				OpsCustParamLogic opsCustParamLogic = new OpsCustParamLogic();

				CasOpsCustParamEntity casOpsCustParamEntity = new CasOpsCustParamEntity();

				casOpsCustParamEntity = opsCustParamLogic.translateMdtOpsCustParam(opsSysctrlCumParaModel);
				genericDAO.saveOrUpdate(casOpsCustParamEntity);

			} else {
				log.error("Unable to convert type to opsSysctrlCumParaModel.");
				return false;

			}
		} catch (Exception e) {
			log.error("Error on updateOpsSysctrlCumPara: " + e.getMessage());
			e.printStackTrace();
			return false;

		}
		return true;
	}

	@Override
	public List<?> retrieveOpsFileRegByCriteria(String namedQuery, String property, String value) {
		List<OpsFileRegModel> opsFileRegList = new ArrayList<OpsFileRegModel>();
		try {
			List<CasOpsFileRegEntity> mdtOpsFileRegList = genericDAO.findAllByNamedQuery(namedQuery, property, value);

			if (mdtOpsFileRegList.size() > 0) {
				OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();
				for (CasOpsFileRegEntity opsFileRegEntity : mdtOpsFileRegList) {
					OpsFileRegModel localModel = opsFileRegLogic.retrieveOpsFileRegModel(opsFileRegEntity);
					opsFileRegList.add(localModel);
				}

			}

		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveOpsFileRegByCriteria: " + e.getMessage());
			e.printStackTrace();
		}
		return opsFileRegList;

	}

	public Object retrieveErrorCode(String errorCode) {
		ConfgErrorCodesModel errorCodesModel = new ConfgErrorCodesModel();

		try {
			CasCnfgErrorCodesEntity casCnfgErrorCodesEntity = (CasCnfgErrorCodesEntity) genericDAO
					.findByNamedQuery("CasCnfgErrorCodesEntity.findByErrorCode", "errorCode", errorCode);
			if (casCnfgErrorCodesEntity != null) {
				ErrorCodesLogic errorCodesLogic = new ErrorCodesLogic();
				errorCodesModel = errorCodesLogic.retrieveErrorCode(casCnfgErrorCodesEntity);
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveErrorCode: " + e.getMessage());
			e.printStackTrace();
		}
		return errorCodesModel;
	}

	@Override
	public List<?> getSysCtrlSysParam() {

		List<MdtSysCtrlSysParamModel> sysCtrlSysParamModel = new ArrayList<MdtSysCtrlSysParamModel>();

		List<CasSysctrlSysParamEntity> mdtSysctrlSysParamEntityList = new ArrayList<CasSysctrlSysParamEntity>();

		mdtSysctrlSysParamEntityList = genericDAO.findAll(CasSysctrlSysParamEntity.class);

		if (mdtSysctrlSysParamEntityList.size() > 0) {
			SysCtrlSysParamLogic sysCtrlSysParamLogic = new SysCtrlSysParamLogic();
			sysCtrlSysParamModel = sysCtrlSysParamLogic.retrieveAllSysCtrlSysParam(mdtSysctrlSysParamEntityList);
		}

		return sysCtrlSysParamModel;
	}

	@Override
	public List<?> getStatus() {

		List<ProcessStatusModel> processStatusModel = new ArrayList<ProcessStatusModel>();

		List<CasSysctrlProcessStatusEntity> mdtSysctrlProcessStatusEntityList = new ArrayList<CasSysctrlProcessStatusEntity>();

		mdtSysctrlProcessStatusEntityList = genericDAO.findAll(CasSysctrlProcessStatusEntity.class);

		if (mdtSysctrlProcessStatusEntityList.size() > 0) {
			SysctrlProcessStatusLogic sysctrlProcessStatusLogic = new SysctrlProcessStatusLogic();
			processStatusModel = sysctrlProcessStatusLogic.retreiveAllProcessStatus(mdtSysctrlProcessStatusEntityList);
		}

		return processStatusModel;
	}

	@Override
	public Object retrieveCisMemberByCriteria(String memberNo) {
		SysCisBankModel sysCisBankModel = new SysCisBankModel();
		try {
			SysCisBankEntity sysCisBankEntity = (SysCisBankEntity) genericDAO.findByNamedQuery("SysCisBankEntity.findByMemberNo", "memberNo", memberNo);
			if (sysCisBankEntity != null) {
				SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
				sysCisBankModel = sysCisBankLogic.retrieveSysCisBank(sysCisBankEntity);
			}
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.debug("Error on retrieveCisMemberByCriteria: " + e.getMessage());
			e.printStackTrace();
		}

		return sysCisBankModel;
	}

	public List<?> retrieveCreditorSummary() {

		// Entity Model List
		List<MandatesSummaryReportModel> mandatesSummaryReportEntityList = new ArrayList<MandatesSummaryReportModel>();

		// Commons Model List
		List<MandatesSummaryModel> credSummList = new ArrayList<MandatesSummaryModel>();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT SUM(a.COLL_AMOUNT) AS valofMandates, b.FI_NAME AS finInstName, COUNT(b.FI_NAME) AS NrofMandates ");
		sb.append("FROM CAMOWNER.MDT_OPS_MANDATE_REGISTER a, CAMOWNER.MDT_OPS_FIN_INST b ");
		sb.append(
				"WHERE a.MANDATE_REQ_ID = b.MANDATE_REQ_ID AND a.ACTIVE_IND = 'Y' AND (b.FIN_INST_TYPE_ID = 'FI03') ");
		sb.append("GROUP BY b.FI_NAME, b.FIN_INST_TYPE_ID ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("valofMandates");
		scalarList.add("finInstName");
		scalarList.add("NrofMandates");

		mandatesSummaryReportEntityList = (List<MandatesSummaryReportModel>) genericDAO.findBySql(sqlQuery, scalarList, MandatesSummaryReportModel.class);
		if (mandatesSummaryReportEntityList.size() > 0) {
			log.debug("mandatesSummaryReportEntityList: " + mandatesSummaryReportEntityList.toString());
			for (MandatesSummaryReportModel entModel : mandatesSummaryReportEntityList) {
				MandatesSummaryModel localModel = new ServiceTranslator()
						.translateMandatesSummaryModelEntityToCommonsModel(entModel);
				credSummList.add(localModel);
			}
		}
		return credSummList;

	}

	public List<?> retrieveReports(String bicfi) {

		List<MandatesReportsEntityModel> mandatesReportModelEntityList = new ArrayList<MandatesReportsEntityModel>();
		List<MandatesDebtorReportModel> reportsList = new ArrayList<MandatesDebtorReportModel>();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT a.MANDATE_ID AS mandateId, a.MANDATE_REQ_ID AS mandateReqId, a.FROM_DATE AS fromDate, a.TO_DATE AS toDate, A.COLL_AMOUNT AS collAmount,  b.FI_NAME AS finInstName");
		sb.append("FROM CAMOWNER.MDT_OPS_MANDATE_REGISTER a, MDT_OPS_FIN_INST b ");
		sb.append(
				"WHERE a.MANDATE_REQ_ID = b.MANDATE_REQ_ID AND a.ACTIVE_IND = 'Y' AND b.FIN_INST_TYPE_ID = 'FI04' AND b.BICFI = 'NEDSZAJJ'");
		// sb.append("WHERE a.MANDATE_REQ_ID = b.MANDATE_REQ_ID AND a.ACTIVE_IND
		// = 'Y' AND b.FIN_INST_TYPE_ID = 'FI04' AND b.BICFI = '"+bicfi+"'");

		String sqlQuery = sb.toString();

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("mandateId");
		scalarList.add("mandateReqId");
		scalarList.add("fromDate");
		scalarList.add("toDate");
		scalarList.add("collAmount");
		scalarList.add("finInstName");

		mandatesReportModelEntityList = (List<MandatesReportsEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				MandatesReportsEntityModel.class);

		if (mandatesReportModelEntityList.size() > 0) {
			log.debug("*******************mandatesReportModelEntityList: ****************************** "
					+ mandatesReportModelEntityList.toString());

			for (MandatesReportsEntityModel mandatesReportsModel : mandatesReportModelEntityList) {

				MandatesDebtorReportModel mandatesDebtorReportModel = new ServiceTranslator()
						.translateMandatesReportsModelEntityToCommonsModel(mandatesReportsModel);
				reportsList.add(mandatesDebtorReportModel);
			}
		}

		return reportsList;
	}

	public List<?> retrieveTotalBalanceBroughtForward()

	{
		List<EndOfDayReportsEntityModel> endofDayEntityModelList = new ArrayList<EndOfDayReportsEntityModel>();
		List<EndOfDayReportCommonsModel> endOfDayComonsModelList = new ArrayList<EndOfDayReportCommonsModel>();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) AS balanceBrghtForward ");
		sb.append("FROM CAMOWNER.CAS_OPS_CESS_ASSIGN_TXNS ");
		sb.append("WHERE (SERVICE_ID ='MANIN' OR SERVICE_ID='MANCN' OR SERVICE_ID='CARIN')");

		String sqlQuery = sb.toString();

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("balanceBrghtForward");

		endofDayEntityModelList = (List<EndOfDayReportsEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				EndOfDayReportsEntityModel.class);
		log.debug("The balance brought forward is as follows *&&&&&&&&&&&&&&" + endofDayEntityModelList);
		for (EndOfDayReportsEntityModel localModel : endofDayEntityModelList) {
			EndOfDayReportCommonsModel endOfDayReportCommonsModel = new ServiceTranslator()
					.translateEndOfDayEntityModelToCommonsModel(localModel);
			endOfDayComonsModelList.add(endOfDayReportCommonsModel);
		}

		log.debug("Model list is returning &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + endOfDayComonsModelList);
		return endOfDayComonsModelList;

	}

	public List<?> retrieveBalanceCarriedForward() {
		List<EndOfDayReportsEntityModel> endofDayEntityModelList = new ArrayList<EndOfDayReportsEntityModel>();
		List<EndOfDayReportCommonsModel> endOfDayComonsModelList = new ArrayList<EndOfDayReportCommonsModel>();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) AS balanceCarriedForward  ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG ");
		sb.append("WHERE (SERVICE_ID ='MANIN' OR SERVICE_ID='MANCN' OR SERVICE_ID='CARIN') AND PROCESS_STATUS='3'");

		String sqlQuery = sb.toString();

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("balanceCarriedForward");

		endofDayEntityModelList = (List<EndOfDayReportsEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				EndOfDayReportsEntityModel.class);
		log.debug("The balance carried has the following amount ******************" + endofDayEntityModelList);
		for (EndOfDayReportsEntityModel localModel : endofDayEntityModelList) {
			EndOfDayReportCommonsModel endOfDayReportCommonsModel = new ServiceTranslator()
					.translateEndOfDayEntityModelToCommonsModel(localModel);
			endOfDayComonsModelList.add(endOfDayReportCommonsModel);
		}
		return endOfDayComonsModelList;

	}

	public List<?> sumOutGoingMessagesPerServiceID() {
		List<EndOfDayReportsEntityModel> endofDayEntityModelList = new ArrayList<EndOfDayReportsEntityModel>();
		List<EndOfDayReportCommonsModel> endOfDayComonsModelList = new ArrayList<EndOfDayReportCommonsModel>();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUM(NR_OF_MSGS)as Outgoing  ");
		sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append(
				"WHERE OUTGOING ='Y' AND (SERVICE_ID ='MANOT' OR SERVICE_ID='MANOC' OR SERVICE_ID='MANCO'OR SERVICE_ID='MANRO' OR SERVICE_ID='MANRF' OR SERVICE_ID='ST200' OR SERVICE_ID='ST203')");

		String sqlQuery = sb.toString();

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("Outgoing");

		endofDayEntityModelList = (List<EndOfDayReportsEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				EndOfDayReportsEntityModel.class);
		log.debug("Amount of outgoing msgs******************" + endofDayEntityModelList);
		for (EndOfDayReportsEntityModel localModel : endofDayEntityModelList) {
			EndOfDayReportCommonsModel endOfDayReportCommonsModel = new ServiceTranslator()
					.translateEndOfDayEntityModelToCommonsModel(localModel);
			endOfDayComonsModelList.add(endOfDayReportCommonsModel);
		}
		return endOfDayComonsModelList;

	}

	public List<?> sumIncomingMessagesPerServiceId() {

		List<EndOfDayReportsEntityModel> endofDayEntityModelList = new ArrayList<EndOfDayReportsEntityModel>();
		List<EndOfDayReportCommonsModel> endOfDayComonsModelList = new ArrayList<EndOfDayReportCommonsModel>();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUM(NR_OF_MSGS) as Incoming ");
		sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append(
				"WHERE INCOMING ='Y' AND (SERVICE_ID ='MANIN' OR SERVICE_ID='MANCN' OR SERVICE_ID='CARIN' OR SERVICE_ID ='RCAIN' OR SERVICE_ID='MANRI' OR SERVICE_ID='MANRT'  OR SERVICE_ID='ST202' OR SERVICE_ID='ST201')");

		String sqlQuery = sb.toString();

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("Incoming");

		endofDayEntityModelList = (List<EndOfDayReportsEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				EndOfDayReportsEntityModel.class);
		log.debug("Amount of incoming msgs ******************" + endofDayEntityModelList);
		for (EndOfDayReportsEntityModel localModel : endofDayEntityModelList) {
			EndOfDayReportCommonsModel endOfDayReportCommonsModel = new ServiceTranslator()
					.translateEndOfDayEntityModelToCommonsModel(localModel);
			endOfDayComonsModelList.add(endOfDayReportCommonsModel);
		}
		return endOfDayComonsModelList;

	}

	public List<?> retrieveDebtorSummary() {

		List<MandatesSummaryReportModel> mandatesSummaryReportModelEntityList = new ArrayList<MandatesSummaryReportModel>();
		List<MandatesSummaryModel> debtorSummaryList = new ArrayList<MandatesSummaryModel>();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT SUM(a.COLL_AMOUNT) AS valofMandates, b.FI_NAME AS finInstName, COUNT(b.FI_NAME) AS NrofMandates ");
		sb.append("FROM CAMOWNER.MDT_OPS_MANDATE_REGISTER a, CAMOWNER.MDT_OPS_FIN_INST b ");
		sb.append(
				"WHERE a.MANDATE_REQ_ID = b.MANDATE_REQ_ID AND a.ACTIVE_IND = 'Y' AND (b.FIN_INST_TYPE_ID = 'FI04') ");
		sb.append("GROUP BY b.FI_NAME, b.FIN_INST_TYPE_ID ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("valofMandates");
		scalarList.add("finInstName");
		scalarList.add("NrofMandates");

		mandatesSummaryReportModelEntityList = (List<MandatesSummaryReportModel>) genericDAO.findBySql(sqlQuery,
				scalarList, MandatesSummaryReportModel.class);

		if (mandatesSummaryReportModelEntityList.size() > 0) {
			log.debug("mandatesSummaryReportModelEntityList: " + mandatesSummaryReportModelEntityList.toString());

			for (MandatesSummaryReportModel entModel : mandatesSummaryReportModelEntityList) {
				MandatesSummaryModel localModel = new ServiceTranslator()
						.translateMandatesSummaryModelEntityToCommonsModel(entModel);
				debtorSummaryList.add(localModel);
			}
		}
		return debtorSummaryList;
	}

	public List<?> retrieveOutGoingNrOfFiles()

	{

		List<OpsFileRegModel> fileRegList = new ArrayList<OpsFileRegModel>();
		List<CasOpsFileRegEntity> casOpsFileRegEntityList = null;

		try {
			casOpsFileRegEntityList = (List<CasOpsFileRegEntity>) genericDAO
					.findAllByNamedQuery("CasOpsFileRegEntity.findByInOutInd", "inOutInd", "O");

			if (casOpsFileRegEntityList.size() > 0) {
				OpsFileRegLogic opsFileRegLogic = new OpsFileRegLogic();

				for (CasOpsFileRegEntity localEntity : casOpsFileRegEntityList) {

					OpsFileRegModel opsFileRegModel = new OpsFileRegModel();
					opsFileRegModel = opsFileRegLogic.retrieveDelDelivery(localEntity);

					fileRegList.add(opsFileRegModel);
					// fileRegList =
					// opsFileRegLogic.retrieveAllDelDelivery(mdtOpsFileRegEntityList);
				}
			}
		} catch (Exception ex) {
			log.error("Error on retrieveOpsFileRegbyCriteria: " + ex.getMessage());
		}

		return fileRegList;
	}

	public List<?> retrieveIncomingNrOfFiles()
	{
		log.debug("retrieving incoming files using sql statement *************************");
		List<OutOfBalanceReportModel> OutOfBalanceReportModelList = new ArrayList<OutOfBalanceReportModel>();
		List<OutofBalanceModel> outofBalanceModelList = new ArrayList<OutofBalanceModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SERVICE_ID as serviceId,SUM(NR_MSGS_ACCEPTED) as nrMsgsAccepted ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append("WHERE INCOMING ='Y' AND  NR_MSGS_ACCEPTED > 0");
		sb.append("GROUP BY SERVICE_ID ");
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("serviceId");
		scalarList.add("nrMsgsAccepted");

		OutOfBalanceReportModelList = genericDAO.findBySql(sqlQuery, scalarList, OutOfBalanceReportModel.class);
		log.debug("Incoming files list has the following info ******************" + OutOfBalanceReportModelList);
		if (OutOfBalanceReportModelList.size() > 0) {

			for (OutOfBalanceReportModel localModelEntity : OutOfBalanceReportModelList) {
				OutofBalanceModel localModel = new OutofBalanceModel();
				localModel = new ServiceTranslator()
						.translateOutOfBalanceReportModelEntityToCommonsModel(localModelEntity);
				outofBalanceModelList.add(localModel);
				log.debug("The model list has this information #####################" + outofBalanceModelList);

			}
		}
		log.debug("The model list has this information #####################" + outofBalanceModelList);
		return outofBalanceModelList;
	}

	@Override
	public List<?> retriveExtractReportData() {

		List<MandatesExtractReportEntityModel> mandExtractReportEntityModelList = new ArrayList<MandatesExtractReportEntityModel>();
		List<MandatesExtractReportModel> mandatesExtractReportModelList = new ArrayList<MandatesExtractReportModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SERVICE_ID as serviceId, SUM(NR_OF_MSGS) as nrMsgsExtracted ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append("WHERE OUTGOING ='Y' AND NR_MSGS_EXTRACTED > 0 ");
		sb.append("GROUP BY SERVICE_ID ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);
		scalarList.add("serviceId");
		scalarList.add("nrMsgsExtracted");

		mandExtractReportEntityModelList = genericDAO.findBySql(sqlQuery, scalarList,
				MandatesExtractReportEntityModel.class);
		log.debug("Incoming files list has the following info ******************" + mandExtractReportEntityModelList);

		if (mandExtractReportEntityModelList.size() > 0) {
			for (MandatesExtractReportEntityModel localEntity : mandExtractReportEntityModelList) {
				MandatesExtractReportModel localModel = new MandatesExtractReportModel();
				localModel = new ServiceTranslator()
						.translateMandatesExtractReportEntityModelToCommonsModel(localEntity);
				mandatesExtractReportModelList.add(localModel);
				log.debug("The model list has this information #####################" + mandExtractReportEntityModelList);
			}
		}
		return mandatesExtractReportModelList;
	}

	public List<?> retrieveCaptureLocalInstCodes() {
		// Entity Model List
		List<CasCnfgLocalInstrCodesEntity> localInstrEntityList = new ArrayList<CasCnfgLocalInstrCodesEntity>();

		// Commons Model List
		List<LocalInstrumentCodesModel> localInstList = new ArrayList<LocalInstrumentCodesModel>();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT LOCAL_INSTRUMENT_CODE AS localInstrumentCode, LOCAL_INSTRUMENT_DESCRIPTION AS localInstrumentDescription, ACTIVE_IND AS activeInd ");
		sb.append("FROM CAMOWNER.CAS_CNFG_LOCAL_INSTR_CODES ");
		sb.append("WHERE LOCAL_INSTRUMENT_CODE = '0227' OR LOCAL_INSTRUMENT_CODE = '0228' ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("localInstrumentCode");
		scalarList.add("localInstrumentDescription");
		scalarList.add("activeInd");

		localInstrEntityList = (List<CasCnfgLocalInstrCodesEntity>) genericDAO.findBySql(sqlQuery, scalarList,
				CasCnfgLocalInstrCodesEntity.class);
		if (localInstrEntityList.size() > 0) {
			log.debug("localInstrEntityList: " + localInstrEntityList.toString());
			for (CasCnfgLocalInstrCodesEntity localEntity : localInstrEntityList) {
				LocalInstrumentCodesModel localModel = new AdminTranslator()
						.translateLocalInstrumentCodesEntityToCommonsModel(localEntity);
				localInstList.add(localModel);
			}
		}
		return localInstList;

	}

	@Override
	public List<?> retrieveCisBranchByCriteria(String memberNo) {
		List<SysCisBranchModel> SysCisBranchModelList = new ArrayList<SysCisBranchModel>();
		try {
			List<SysCisBranchEntity> sysCisBranchEntityList = genericDAO.findAllByNamedQuery("SysCisBranchEntity.findByMemberNo","memberNo", memberNo);
			if (sysCisBranchEntityList.size() > 0) {
				SysCisBranchLogic sysCisBranchLogic = new SysCisBranchLogic();
				SysCisBranchModelList = sysCisBranchLogic.retrieveAllSysCisBranchData(sysCisBranchEntityList);
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveCisBranchByCriteria: " + e.getMessage());
			e.printStackTrace();
		}

		return SysCisBranchModelList;
	}

	public List<?> retrieveMandateBySearchCriteria(String mandateId) {
		// Entity Model List
		List<CasCnfgLocalInstrCodesEntity> localInstrEntityList = new ArrayList<CasCnfgLocalInstrCodesEntity>();

		// Commons Model List
		List<LocalInstrumentCodesModel> localInstList = new ArrayList<LocalInstrumentCodesModel>();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT LOCAL_INSTRUMENT_CODE AS localInstrumentCode, LOCAL_INSTRUMENT_DESCRIPTION AS localInstrumentDescription, ACTIVE_IND AS activeInd ");
		sb.append("FROM CAMOWNER.CAS_CNFG_LOCAL_INSTR_CODES ");
		sb.append("WHERE LOCAL_INSTRUMENT_CODE = '0227' OR LOCAL_INSTRUMENT_CODE = '0228' ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("localInstrumentCode");
		scalarList.add("localInstrumentDescription");
		scalarList.add("activeInd");

		localInstrEntityList = (List<CasCnfgLocalInstrCodesEntity>) genericDAO.findBySql(sqlQuery, scalarList,
				CasCnfgLocalInstrCodesEntity.class);
		if (localInstrEntityList.size() > 0) {
			log.debug("localInstrEntityList: " + localInstrEntityList.toString());
			for (CasCnfgLocalInstrCodesEntity localEntity : localInstrEntityList) {
				LocalInstrumentCodesModel localModel = new AdminTranslator()
						.translateLocalInstrumentCodesEntityToCommonsModel(localEntity);
				localInstList.add(localModel);
			}
		}
		return localInstList;

	}

	@Override
	public Object retrieveCompanyParameters() {
		SysctrlCompParamModel sysctrlCompParamModel = new SysctrlCompParamModel();
		try {
			CasSysctrlCompParamEntity sysctrlCompParamEntity = (CasSysctrlCompParamEntity) genericDAO
					.findByNamedQuery("CasSysctrlCompParamEntity.findByCompAbbrevName", "compAbbrevName", "BSV");
			// find(MdtSysctrlCompParamEntity.class, new BigDecimal(3));
			if (sysctrlCompParamEntity != null) {

				CompanyParametersLogic companyParametersLogic = new CompanyParametersLogic();
				sysctrlCompParamModel = companyParametersLogic.retrieveCompanyParameters(sysctrlCompParamEntity);

			}
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveCompanyParameters: " + e.getMessage());
			e.printStackTrace();
		}

		return sysctrlCompParamModel;
	}

	@Override
	public Object retrieveOpsCustomerParameters(String bicCode) {
		OpsCustParamModel sysCustParamModel = new OpsCustParamModel();
		try {
			CasOpsCustParamEntity opsCustParamEntity = (CasOpsCustParamEntity) genericDAO
					.findByNamedQuery("CasOpsCustParamEntity.findByBicCode", "bicCode", bicCode);
			if (opsCustParamEntity != null) {
				CustParamLogic customerCustParamLogic = new CustParamLogic();
				sysCustParamModel = customerCustParamLogic.retrieveOpsCustomerParameter(opsCustParamEntity);

			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveOpsCustomerParameters: " + e.getMessage());
			e.printStackTrace();
		}

		return sysCustParamModel;
	}

	public List<?> retrieveMandatesSearchCriteria(String mandateId, String debtorName, String debtorBank,
			String creditorName, String creditorBank) {

		List<MandatesSearchEntityModel> mandatesSearchEntityList = new ArrayList<MandatesSearchEntityModel>();
		List<MandatesSearchModel> mandatesSearchList = new ArrayList<MandatesSearchModel>();
		MandatesModel mandateModel = new MandatesModel();
		mandateModel.setMandateId(mandateId);
		mandateModel.setDebtorName(debtorName);
		mandateModel.setDebtorBank(debtorBank);
		mandateModel.setCreditorName(creditorName);
		mandateModel.setCreditorBank(creditorBank);

		boolean mandateIdCrit = false, credBankCrit = false, credNameCrit = false, debtBankCrit = false,
				debtNameCrit = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer str = new StringBuffer();

		boolean whereClause = false;

		sb.append(
				"SELECT r.MANDATE_ID AS mandateId,p.NAME AS name , p.PARTY_IDENT_TYPE_ID AS partyId, f.FI_NAME AS fiName, f.FIN_INST_TYPE_ID AS finId");
		sb.append(" FROM CAMOWNER.MDT_OPS_MANDATE_REGISTER r INNER JOIN CAMOWNER.MDT_OPS_FIN_INST f");
		sb.append(" ON r.MANDATE_REQ_ID = f.MANDATE_REQ_ID");
		sb.append(" LEFT JOIN CAMOWNER.MDT_OPS_PARTY_IDENT p ON r.MANDATE_REQ_ID = p.MANDATE_REQ_ID");

		str.append("SELECT ");
		if (mandateModel.getMandateId() != null && !(mandateModel.getMandateId().isEmpty())) {
			mandateIdCrit = true;
			str.append("r.MANDATE_ID AS mandateId ");
		}

		if (mandateModel.getCreditorBank() != null && !(mandateModel.getCreditorBank().isEmpty())) {
			credBankCrit = true;
			str.append(", f.FI_NAME AS crFiName, f.FIN_INST_TYPE_ID AS crFiId ");
		}

		if (mandateModel.getCreditorName() != null && !(mandateModel.getCreditorName().isEmpty())) {
			credNameCrit = true;
			str.append(", p.NAME AS crName , p.PARTY_IDENT_TYPE_ID AS crPartyId ");
		}

		if (mandateModel.getDebtorBank() != null && !(mandateModel.getDebtorBank().isEmpty())) {
			debtBankCrit = true;
			str.append(", f.FI_NAME AS drFiName, f.FIN_INST_TYPE_ID AS drFiId ");
		}

		if (mandateModel.getDebtorName() != null && !(mandateModel.getDebtorName().isEmpty())) {
			debtNameCrit = true;
			str.append(", p.NAME AS drName , p.PARTY_IDENT_TYPE_ID AS drPartyId ");
		}

		// Build Up FROM clause
		str.append("FROM ");
		if (mandateIdCrit == true)
			str.append("MDT_OPS_MANDATE_REGISTER r ");

		if (credBankCrit == true || debtBankCrit == true)
			str.append(", MDT_OPS_FIN_INST f ");

		if (credNameCrit == true || debtNameCrit == true)
			str.append(", MDT_OPS_PARTY_IDENT p ");

		// Build up the WHERE clause
		str.append("WHERE ");
		if (mandateIdCrit == true)
			str.append("r.MANDATE_ID = '" + mandateModel.getMandateId() + "' ");

		if (credBankCrit == true || debtBankCrit == true)
			str.append("AND r.MANDATE_REQ_ID = f.MANDATE_REQ_ID ");

		if (credBankCrit == true)
			str.append("AND f.FIN_INST_TYPE_ID = 'FI03' AND  f.FI_NAME ='" + mandateModel.getCreditorBank() + "' ");

		if (debtBankCrit == true)
			str.append("AND f.FIN_INST_TYPE_ID = 'FI04' AND  f.FI_NAME ='" + mandateModel.getDebtorBank() + "' ");

		if (credNameCrit == true || debtNameCrit == true)
			str.append("AND r.MANDATE_REQ_ID = p.MANDATE_REQ_ID ");

		if (credNameCrit == true)
			str.append("AND p.PARTY_IDENT_TYPE_ID = 'PI02' AND  p.NAME ='" + mandateModel.getCreditorName() + "' ");

		if (debtNameCrit == true)
			str.append("AND p.PARTY_IDENT_TYPE_ID = 'PI04' AND  p.NAME ='" + mandateModel.getDebtorName() + "' ");

		log.debug("The new String====***************=>>>>>>> " + str.toString());

		if ((mandateModel.getMandateId() != null) || !(mandateModel.getMandateId().isEmpty())) {

			if (!whereClause) {
				whereClause = true;
				sb.append(" WHERE ");
			} else {
				sb.append(" OR ");
			}

			sb.append(" r.MANDATE_ID = " + "'" + mandateModel.getMandateId() + "'");

		}

		if ((mandateModel.getDebtorName() != null) || !(mandateModel.getDebtorName().isEmpty())) {

			if (!whereClause) {
				whereClause = true;
				sb.append(" WHERE ");
			} else {
				sb.append(" OR ");
			}
			sb.append(" p.NAME = " + "'" + mandateModel.getDebtorName() + "'");

		}

		if ((mandateModel.getCreditorName() != null) || !(mandateModel.getCreditorName().isEmpty())) {

			if (!whereClause) {
				whereClause = true;
				sb.append(" WHERE ");
			} else {
				sb.append(" OR ");
			}

			sb.append(" p.NAME= " + "'" + mandateModel.getCreditorName() + "'");

		}

		if ((mandateModel.getDebtorBank() != null) || !(mandateModel.getDebtorBank().isEmpty())) {

			if (!whereClause) {
				whereClause = true;
				sb.append(" WHERE ");
			} else {
				sb.append(" OR ");
			}

			sb.append("f.FI_NAME =" + "'" + mandateModel.getDebtorBank() + "'");
		}

		if ((mandateModel.getCreditorBank() != null) || !(mandateModel.getCreditorBank().isEmpty())) {

			if (!whereClause) {
				whereClause = true;
				sb.append(" WHERE ");
			} else {
				sb.append(" OR ");
			}
			sb.append("f.FI_NAME =" + "'" + mandateModel.getCreditorBank() + "'");
		}

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("mandateId");
		scalarList.add("name");
		scalarList.add("partyId");
		scalarList.add("finId");
		scalarList.add("fiName");

		mandatesSearchEntityList = (List<MandatesSearchEntityModel>) genericDAO.findBySql(sqlQuery, scalarList,
				MandatesSearchEntityModel.class);

		log.debug("size of list ==================: " + mandatesSearchEntityList.size());
		// log.debug("mandateSearchList ==================: "+
		// mandatesSearchEntityList.toString());

		if (mandatesSearchEntityList.size() > 0) {

			for (MandatesSearchEntityModel mandatesModel : mandatesSearchEntityList) {

				MandatesSearchModel localModel = new ServiceTranslator()
						.translateMandatesSearchModelEntityToCommonsModel(mandatesModel);
				mandatesSearchList.add(localModel);
			}

		}

		return mandatesSearchList;

	}

	@Override
	public List<?> retrieveAllMandatesCount() {
		List<MandatesCountCommonsModel> mandatesCountModelList = new ArrayList<MandatesCountCommonsModel>();
		try {

			List<CasOpsMndtCountEntity> mdtOpsMndtCountEntityList = genericDAO.findAll(
					CasOpsMndtCountEntity.class);

			if (mdtOpsMndtCountEntityList.size() > 0) {

				MandatesCountLogic mandatesCountLogic = new MandatesCountLogic();
				mandatesCountModelList = mandatesCountLogic.retrieveAllMandatesCountData(mdtOpsMndtCountEntityList);

			}

		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveAllMandatesCount :" + e.getMessage());
			e.printStackTrace();
		}

		return mandatesCountModelList;

	}

	@Override
	public List<?> retrieveCisMemberTableByCriteria(String memberNo) {

		List<SysCisBankModel> sysCisBankModelList = new ArrayList<SysCisBankModel>();

		try {
			List<SysCisBankEntity> sysCisBankEntityList = genericDAO.findAllByNamedQuery("SysCisBankEntity.findByMemberNo","memberNo", memberNo);
			if (sysCisBankEntityList.size() > 0) {
				SysCisBankLogic sysCisBankLogic = new SysCisBankLogic();
				sysCisBankModelList = sysCisBankLogic.retrieveAllSysCisBankData(sysCisBankEntityList);
			}
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.debug("Error on retrieveCisMemberTableByCriteria: " + e.getMessage());
			e.printStackTrace();
		}

		return sysCisBankModelList;
	}



	@Override
	public boolean createProcessStatus(Object obj) {

		try {

			if (obj instanceof CasSysctrlProcessStatusEntity) {
				CasSysctrlProcessStatusEntity casSysctrlProcessStatusEntity = (CasSysctrlProcessStatusEntity) obj;
				genericDAO.save(casSysctrlProcessStatusEntity);

				return true;
			} else {
				log.error("Unable to convert type to mdtSysctrlProcessStatusEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on createProcessStatus: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> retrieveActiveCustomerParameters() {
		List<CasOpsCustParamEntity> custParamsList = new ArrayList<CasOpsCustParamEntity>();

		try {
			custParamsList = genericDAO.findAllByNamedQuery("CasOpsCustParamEntity.findByActiveInd", "activeInd", "Y");
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveActiveCustomerParameters: " + e.getMessage());
			e.printStackTrace();
		}

		return custParamsList;
	}

	public Object retrieveErrorCodeDesc(String errorCode) {
		CasCnfgErrorCodesEntity casCnfgErrorCodesEntity = new CasCnfgErrorCodesEntity();
		try {
			casCnfgErrorCodesEntity = (CasCnfgErrorCodesEntity) genericDAO
					.findByNamedQuery("CasCnfgErrorCodesEntity.findByErrorCode", "errorCode", errorCode);
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveErrorCode: " + e.getMessage());
			e.printStackTrace();
		}
		return casCnfgErrorCodesEntity;
	}

	@Override
	public List<?> retrieveStatusHdrs() {
		List<CasOpsStatusHdrsEntity> statusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();

		try {
			log.debug("---------------In the retrieve status headers method------------------");
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", reportToBeProdStatus);
			// parameters.put("processStatus2", readyForExtractStatus);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			// statusHdrsList = (List<OpsStatusHdrsEntity>)
			// genericDAO.findAllByCriteria(OpsStatusHdrsEntity.class,
			// parameters, true);
			statusHdrsList = genericDAO.findAllByNQCriteria("CasOpsStatusHdrsEntity.findByIN", parameters);
			log.debug("---------------statusHdrsList after findAllByCriteria: ------------------" + statusHdrsList);
		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusHdrs: " + e.getMessage());
			e.printStackTrace();
		}

		return statusHdrsList;
	}

	@Override
	public List<?> retrieveStatusHdrsByStatus(String status, String pacs002Service) 
	{
		List<CasOpsStatusHdrsEntity> statusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();

		try {
			//SalehaR - 2016/02/20 - Scheduler not extracting by service
			//			statusHdrsList = genericDAO.findAllByNamedQuery("CasOpsStatusHdrsEntity.findByProcessStatus", "processStatus", status);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("service", pacs002Service);
			parameters.put("processStatus", status);

			statusHdrsList = (List<CasOpsStatusHdrsEntity>) genericDAO .findAllByCriteria(
					CasOpsStatusHdrsEntity.class, parameters);

		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusHdrsByStatus: " + e.getMessage());
			e.printStackTrace();
		}

		return statusHdrsList;
	}

	@Override
	public List<?> retrieveStatusDetailsByStatus(String instId) 
	{
		List<CasOpsStatusDetailsEntity> statusDtlsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("instId", instId);
			parameters.put("processStatus", readyForExtractStatus);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			statusDtlsList = (List<CasOpsStatusDetailsEntity>) genericDAO .findAllByCriteria(
					CasOpsStatusDetailsEntity.class, parameters);
			log.debug("---------------MdtAcOpsStatusDetailsEntity after findAllByCriteria: ------------------"
					+ statusDtlsList);
		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusDetailsByStatus: " + e.getMessage());
			e.printStackTrace();
		}

		return statusDtlsList;
	}

	@Override
	public boolean updateOpsStatusHdrs(Object obj) {
		try {

			if (obj instanceof CasOpsStatusHdrsEntity) {

				CasOpsStatusHdrsEntity statusGrpHdrEntity = (CasOpsStatusHdrsEntity) obj;
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<statusGrpHdrEntity before UPDATE>>>>>>>>>>>>>>>>>>>>>>>>>:"
						+ statusGrpHdrEntity);
				genericDAO.saveOrUpdate(statusGrpHdrEntity);

				return true;
			} else {
				log.error("Unable to convert type to CasOpsStatusHdrsEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on updateOpsStatusHdrs: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object retrieveSOTEOTCntrl(String memberNo, String serviceId) {
		List<CasOpsSotEotCtrlEntity> mdtAcOpsSotEotCtrlList = new ArrayList<CasOpsSotEotCtrlEntity>();

		CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = new CasOpsSotEotCtrlEntity();
		try {
			log.debug("memberNo: " + memberNo);
			log.debug("serviceId: " + serviceId);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("casOpsSotEotCtrlPK.instId", memberNo);
			parameters.put("casOpsSotEotCtrlPK.serviceId", serviceId);
			log.debug("---------------sparameters: ------------------" + parameters.toString());
			casOpsSotEotCtrlEntity = (CasOpsSotEotCtrlEntity) genericDAO
					.findByCriteria(CasOpsSotEotCtrlEntity.class, parameters);
			log.debug("---------------CasOpsSotEotCtrlEntity after findByCriteria: ------------------"
					+ casOpsSotEotCtrlEntity);
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveSOTEOTCntrl: " + e.getMessage());
			e.printStackTrace();
		}

		return casOpsSotEotCtrlEntity;
	}

	@Override
	public Object retrieveStatusErrors(BigDecimal hdrSeqNo, String errorType) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("statusHdrSeqNo", hdrSeqNo);
			parameters.put("errorType", errorType);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO
					.findAllByCriteria(CasOpsStatusDetailsEntity.class, parameters);
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ opsStatusDetailsList);
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusErrors: " + e.getMessage());
			e.printStackTrace();
		}

		return opsStatusDetailsList;
	}

	@Override
	public Object retrieveDistinctTxnErrors(BigDecimal hdrSeqNo, String errorType) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("statusHdrSeqNo", hdrSeqNo);
			parameters.put("errorType", errorType);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO.findDistinctByCriteria(
					CasOpsStatusDetailsEntity.class, parameters, "txnId");
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ opsStatusDetailsList);
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveDistinctTxnErrors: " + e.getMessage());
			e.printStackTrace();
		}

		return opsStatusDetailsList;
	}

	@Override
	public Object retrieveTransactionErrors(String txnId, BigDecimal hdrSeqNo) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("txnId", txnId);
			parameters.put("statusHdrSeqNo", hdrSeqNo);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO.findAllByCriteria(
					CasOpsStatusDetailsEntity.class, parameters);
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ opsStatusDetailsList);
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusErrors: " + e.getMessage());
			e.printStackTrace();
		}

		return opsStatusDetailsList;
	}

	public boolean updateSOTEOTCntrl(Object obj) {
		try {

			if (obj instanceof CasOpsSotEotCtrlEntity) {

				CasOpsSotEotCtrlEntity casOpsSotEotCtrlEntity = (CasOpsSotEotCtrlEntity) obj;
				genericDAO.saveOrUpdate(casOpsSotEotCtrlEntity);

				return true;
			} else {
				log.error("Unable to convert type to MdtAcOpsSotEotCtrlEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on updateSOTEOTCntrl: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> getSysctrlCustParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSysctrlCustParam(Object obj) {
		// TODO Auto-generated method stub

	}

	public Object retrievePacs002Count(String messageId) {
		CasOpsMndtCountEntity mdtOpsMndtCountEntity = new CasOpsMndtCountEntity();
		try {
			mdtOpsMndtCountEntity = (CasOpsMndtCountEntity) genericDAO
					.findByNamedQuery("CasOpsMndtCountEntity.findByMsgId", "msgId", messageId);
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveErrorCode: " + e.getMessage());
			e.printStackTrace();
		}
		return mdtOpsMndtCountEntity;
	}

	@Override
	public boolean createLoadEOTandSOT(Object obj) {

		try {
			if (obj instanceof CasOpsTransCtrlMsgEntity) {

				CasOpsTransCtrlMsgEntity casOpsTransCtrlMsgEntity = (CasOpsTransCtrlMsgEntity) obj;
				log.debug("in the createLoadEOTand SOT Entity before save " +
						casOpsTransCtrlMsgEntity);
				genericDAO.save(casOpsTransCtrlMsgEntity);

				return true;
			} else {
				log.error("Undable to convert  type to MdtAcOpsTransCtrlMsgEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on createLoadEOT:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Object retrieveStatusHdrsBySeqNo(BigDecimal seqNo) {
		CasOpsStatusHdrsEntity statusHdrsEntiy = new CasOpsStatusHdrsEntity();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("systemSeqNo", seqNo);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			statusHdrsEntiy = (CasOpsStatusHdrsEntity) genericDAO.findByCriteria(
					CasOpsStatusHdrsEntity.class,
					parameters);
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ statusHdrsEntiy);

		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusHdrsBySeqNo: " + e.getMessage());
			e.printStackTrace();
		}

		return statusHdrsEntiy;
	}

	@Override
	public Object retrieveDistinctStatusDetails(String instId) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("instId", instId);
			parameters.put("processStatus", readyForExtractStatus);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO.findDistinctByCriteria(
					CasOpsStatusDetailsEntity.class, parameters, "txnId");
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ opsStatusDetailsList);
		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveDistinctTxnErrors: " + e.getMessage());
			e.printStackTrace();
		}

		return opsStatusDetailsList;
	}

	@Override
	public List<?> retrieveStatusDetailsByCriteria(String txnId) {
		List<CasOpsStatusDetailsEntity> opsStatusDetailsList = new ArrayList<CasOpsStatusDetailsEntity>();

		try {
			// opsStatusDetailsList = (List<MdtAcOpsStatusDetailsEntity>)
			// genericDAO.findAllByNamedQuery("MdtAcOpsStatusDetailsEntity.findByTxnId",
			// "txnId", txnId);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("txnId", txnId);
			parameters.put("processStatus", readyForExtractStatus);
			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsStatusDetailsList = (List<CasOpsStatusDetailsEntity>) genericDAO
					.findAllByCriteria(CasOpsStatusDetailsEntity.class, parameters);
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"
					+ opsStatusDetailsList);

		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusErrors: " + e.getMessage());
			e.printStackTrace();
		}

		return opsStatusDetailsList;
	}

	@Override
	public boolean updateOpsStatusDetails(Object obj) {
		try {

			if (obj instanceof CasOpsStatusDetailsEntity) {

				CasOpsStatusDetailsEntity statusDetailsEntity = (CasOpsStatusDetailsEntity) obj;
				genericDAO.saveOrUpdate(statusDetailsEntity);

				return true;
			} else {
				log.error("Unable to convert type to MdtAcOpsStatusDetailsEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on updateOpsStatusDetails: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> retrieveAllSysCisBank() {
		List<SystemParamReportEntityModel> sysPramReportEntityList = new ArrayList<SystemParamReportEntityModel>();
		List<SystemParameterReportModel> sysParamReportModelList = new ArrayList<SystemParameterReportModel>();

		StringBuffer sb = new StringBuffer();

		/*
		 * select member_no, MEMBER_NAME,MAX_NR_RECORDS, case when AC_DEBTOR > 0
		 * then 'Y' else 'N' end as DEBTOR, case when AC_CREDITOR > 0 then 'Y'
		 * else 'N' end as CREDITOR from ( SELECT a.MEMBER_NO,a.MEMBER_NAME,
		 * a.MAX_NR_RECORDS, sum(case when nvl(b.AC_DEBTOR,'N') = 'Y' then 1
		 * else 0 end) as AC_DEBTOR, sum(case when nvl(b.AC_CREDITOR,'N') = 'Y'
		 * then 1 else 0 end) as AC_CREDITOR FROM sys_cis_bank a LEFT OUTER JOIN
		 * sys_cis_branch b ON a.member_no = b.member_no group by
		 * a.MEMBER_NO,a.MEMBER_NAME , a.MAX_NR_RECORDS) order by MEMBER_NO;
		 */

		sb.append(
				"select member_no as memberNo, MEMBER_NAME as memberName,MAX_NR_RECORDS as maxNrRecords, NR_OF_DAYS_PROC as nrOfDaysProc, ");
		sb.append("case when AC_DEBTOR > 0 then 'Y' else 'N' end as debtorBank, ");
		sb.append("case when AC_CREDITOR > 0 then 'Y' else 'N' end as creditorBank ");
		sb.append("from  (");
		sb.append("SELECT a.MEMBER_NO ,a.MEMBER_NAME , a.MAX_NR_RECORDS, a.NR_OF_DAYS_PROC, ");
		sb.append("sum(case when nvl(b.AC_DEBTOR,'N') = 'Y' then 1 else 0 end) as AC_DEBTOR, ");
		sb.append("sum(case when nvl(b.AC_CREDITOR,'N') = 'Y' then 1 else 0 end) as AC_CREDITOR ");
		sb.append("FROM CAMOWNER.sys_cis_bank a ");
		sb.append("LEFT OUTER  JOIN CAMOWNER.sys_cis_branch b ");
		sb.append("ON a.member_no = b.member_no ");
		sb.append("group by a.MEMBER_NO,a.MEMBER_NAME , a.MAX_NR_RECORDS,a.NR_OF_DAYS_PROC) ");
		sb.append("order by MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);
		scalarList.add("memberNo");
		scalarList.add("memberName");
		scalarList.add("maxNrRecords");
		scalarList.add("debtorBank");
		scalarList.add("creditorBank");
		scalarList.add("nrOfDaysProc");

		sysPramReportEntityList = genericDAO.findBySql(sqlQuery, scalarList, SystemParamReportEntityModel.class);
		log.debug("System report has the following info ******************" + sysPramReportEntityList.size());

		if (sysPramReportEntityList.size() > 0) {
			for (SystemParamReportEntityModel systemParamReportEntityModel : sysPramReportEntityList) {
				SystemParameterReportModel systemParameterReportModel = new SystemParameterReportModel();
				systemParameterReportModel = new ServiceTranslator()
						.translateSystemParamReportEntityModelToCommonsModel(systemParamReportEntityModel);
				sysParamReportModelList.add(systemParameterReportModel);
				log.debug("The model list has this information #####################" + sysParamReportModelList.size());
			}
		}

		return sysParamReportModelList;

	}

	@Override
	public List<?> retrieveIncomingMandatesCount() {

		List<MandatesCountCommonsModel> mandatesCountIncomingList = new ArrayList<MandatesCountCommonsModel>();
		try {
			List<CasOpsMndtCountEntity> mdtOpsMndtCountEntityList = genericDAO
					.findAllByNamedQuery("CasOpsMndtCountEntity.findByIncoming", "incoming", "Y");
			if (mdtOpsMndtCountEntityList.size() > 0) {
				mandatesCountIncomingList.clear();
				MandatesCountLogic mandatesCountLogic = new MandatesCountLogic();
				mandatesCountIncomingList = mandatesCountLogic.retrieveAllMandatesCountData(mdtOpsMndtCountEntityList);

			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveIncomingMandatesCount :" + e.getMessage());
			e.printStackTrace();
		}

		return mandatesCountIncomingList;

	}

	@Override
	public List<?> retrieveOutgoingMandatesCount() {
		List<MandatesCountCommonsModel> mandatesCountCommonsModelList = new ArrayList<MandatesCountCommonsModel>();
		try {
			List<CasOpsMndtCountEntity> mdtOpsMndtCountEntityList = genericDAO
					.findAllByNamedQuery("CasOpsMndtCountEntity.findByOutgoing", "outgoing", "Y");
			if (mdtOpsMndtCountEntityList.size() > 0) {
				mandatesCountCommonsModelList.clear();
				MandatesCountLogic mandatesCountLogic = new MandatesCountLogic();
				mandatesCountCommonsModelList = mandatesCountLogic
						.retrieveAllMandatesCountData(mdtOpsMndtCountEntityList);

			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveOutgoingMandatesCount :" + e.getMessage());
			e.printStackTrace();
		}

		return mandatesCountCommonsModelList;
	}

	@Override
	public List<?> retrieveRejectedReportData() {

		List<MandatesRejectedReportModel> mandatesRejectedModelList = new ArrayList<MandatesRejectedReportModel>();
		List<MandatesRejectedReportEntityModel> mandatesRejectedEntityModelList = new ArrayList<MandatesRejectedReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SERVICE_ID as serviceId,SUM(NR_MSGS_REJECTED) as nrMsgsRejected ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append("WHERE  INCOMING ='Y' AND NR_MSGS_REJECTED > 0 ");
		sb.append("GROUP BY SERVICE_ID");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);
		scalarList.add("serviceId");
		scalarList.add("nrMsgsRejected");

		mandatesRejectedEntityModelList = genericDAO.findBySql(sqlQuery, scalarList,
				MandatesRejectedReportEntityModel.class);
		log.debug("System report has the following info ******************" + mandatesRejectedEntityModelList);

		if (mandatesRejectedEntityModelList.size() > 0) {
			for (MandatesRejectedReportEntityModel mandatesRejectedReportEntityModel : mandatesRejectedEntityModelList) {
				MandatesRejectedReportModel mandatesRejectedReportModel = new MandatesRejectedReportModel();
				mandatesRejectedReportModel = new ServiceTranslator()
						.translateMandatesRejectedReportEntityModelToCommonsModel(mandatesRejectedReportEntityModel);
				mandatesRejectedModelList.add(mandatesRejectedReportModel);
				log.debug("The model list has this information  reject#####################" + mandatesRejectedModelList);
			}
		}

		return mandatesRejectedModelList;
	}

	@Override
	public List<?> viewSchedulerModel() {

		List<SchedulerModel> schedulerModellist = new ArrayList<SchedulerModel>();

		if (ModelClass.schedulerModelExtract != null) {

			if (ModelClass.schedulerModelExtract.size() != 0) {
				for (SchedulerModel schedulerModel : ModelClass.schedulerModelExtract) {

					schedulerModellist.add(schedulerModel);
				}
			}
		}

		if (ModelClass.schedulerModelEod != null) {

			if (ModelClass.schedulerModelEod.size() != 0) {
				for (SchedulerModel schedulerModel : ModelClass.schedulerModelEod) {

					schedulerModellist.add(schedulerModel);
				}
			}
		}

		if (ModelClass.schedulerModelSod != null) {

			if (ModelClass.schedulerModelSod.size() != 0) {
				for (SchedulerModel schedulerModel : ModelClass.schedulerModelSod) {

					schedulerModellist.add(schedulerModel);
				}
			}
		}

		return schedulerModellist;
	}

	@Override
	public List<?> retrieveAcceptedCountData() {

		List<OutBalanceCountReportEntityModel> outBalanceCountReportEntityList = new ArrayList<OutBalanceCountReportEntityModel>();
		List<OutBalanceCountReportModel> outBalanceCountReportList = new ArrayList<OutBalanceCountReportModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(NR_OF_MSGS) as nrMsgsAccepted ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		sb.append("WHERE INCOMING ='Y' ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);

		scalarList.add("nrMsgsAccepted");

		try {
			outBalanceCountReportEntityList = genericDAO.findBySql(sqlQuery, scalarList,
					OutBalanceCountReportEntityModel.class);

			if (outBalanceCountReportEntityList.size() > 0)

				log.debug("outBalanceCountReportEntityList for accepted: " + outBalanceCountReportEntityList);
			{
				for (OutBalanceCountReportEntityModel localEntity : outBalanceCountReportEntityList) {
					OutBalanceCountReportModel localModel = new OutBalanceCountReportModel();
					localModel = new ServiceTranslator()
							.translateOutBalanceCountReportEntityModelToCommonsModel(localEntity);
					outBalanceCountReportList.add(localModel);
				}
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveAcceptedCountData :" + e.getMessage());
			e.printStackTrace();
		}

		log.debug("outBalanceCountReportList for accepted: " + outBalanceCountReportList);
		return outBalanceCountReportList;
	}

	@Override
	public List<?> retrieveRejectedCountData() {
		List<OutBalanceCountReportEntityModel> outBalanceCountReportEntityList = new ArrayList<OutBalanceCountReportEntityModel>();
		List<OutBalanceCountReportModel> outBalanceCountReportList = new ArrayList<OutBalanceCountReportModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(NR_MSGS_REJECTED) as nrMsgsRejected ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		//sb.append("WHERE INCOMING ='Y' AND NR_MSGS_REJECTED > 0");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);

		scalarList.add("nrMsgsRejected");

		try {
			outBalanceCountReportEntityList = genericDAO.findBySql(sqlQuery, scalarList,
					OutBalanceCountReportEntityModel.class);

			if (outBalanceCountReportEntityList.size() > 0)
				log.debug("outBalanceCountReportEntityList for rejects: " + outBalanceCountReportEntityList);
			{
				for (OutBalanceCountReportEntityModel localEntity : outBalanceCountReportEntityList) {
					OutBalanceCountReportModel localModel = new OutBalanceCountReportModel();
					localModel = new ServiceTranslator()
							.translateOutBalanceCountReportEntityModelToCommonsModel(localEntity);
					outBalanceCountReportList.add(localModel);
				}
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveRejectedCountData :" + e.getMessage());
			e.printStackTrace();
		}

		log.debug("outBalanceCountReportList for rejects: " + outBalanceCountReportList);

		return outBalanceCountReportList;
	}

	@Override
	public List<?> retrieveExtractedCountData() {
		List<OutBalanceCountReportEntityModel> outBalanceCountReportEntityList = new ArrayList<OutBalanceCountReportEntityModel>();
		List<OutBalanceCountReportModel> outBalanceCountReportList = new ArrayList<OutBalanceCountReportModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(NR_MSGS_EXTRACTED) as nrMsgsExtracted ");
		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		//sb.append("WHERE OUTGOING ='Y' AND NR_MSGS_EXTRACTED > ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrMsgsExtracted");
		log.debug("scalarList: " + scalarList);

		try {
			outBalanceCountReportEntityList = genericDAO.findBySql(sqlQuery, scalarList,
					OutBalanceCountReportEntityModel.class);

			if (outBalanceCountReportEntityList.size() > 0) {

				log.debug("outBalanceCountReportEntityList for EXTRACTS: " + outBalanceCountReportEntityList);
				for (OutBalanceCountReportEntityModel localEntity : outBalanceCountReportEntityList) {
					OutBalanceCountReportModel localModel = new OutBalanceCountReportModel();
					localModel = new ServiceTranslator()
							.translateOutBalanceCountReportEntityModelToCommonsModel(localEntity);
					outBalanceCountReportList.add(localModel);
				}
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveExtractedCountData :" + e.getMessage());
			e.printStackTrace();
		}

		log.debug("outBalanceCountReportEntityList for EXTRACTS: " + outBalanceCountReportEntityList);
		return outBalanceCountReportList;
	}

	@Override
	public List<?> retrieveMndtFileStatus() {
		List<FileStatusReportModel> fileStatusReportList = new ArrayList<FileStatusReportModel>();
		List<FileStatusReportEntityModel> fileStatusReportEntityList = new ArrayList<FileStatusReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append(
				"SELECT a.FILE_NAME as fileName, a.INST_ID as instId, a.SERVICE_ID as serviceId, a.NR_OF_MSGS as nrOfMsgs, b.STATUS as status ");
		sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_FILE_REG b ");
		sb.append("ON a.FILE_NAME = b.FILE_NAME ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery:" + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("fileName");
		scalarList.add("instId");
		scalarList.add("serviceId");
		scalarList.add("nrOfMsgs");
		scalarList.add("status");

		log.debug("scalarList: " + scalarList);

		try {
			fileStatusReportEntityList = genericDAO.findBySql(sqlQuery, scalarList, FileStatusReportEntityModel.class);

			if (fileStatusReportEntityList.size() > 0) {
				for (FileStatusReportEntityModel localEntity : fileStatusReportEntityList) {
					FileStatusReportModel localModel = new FileStatusReportModel();
					localModel = new ServiceTranslator()
							.translateFileStatusReportEntityModelToCommonsModel(localEntity);
					fileStatusReportList.add(localModel);
				}
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveMndtFileStatus :" + e.getMessage());
			e.printStackTrace();
		}

		log.debug("fileStatusReportEntityList: " + fileStatusReportEntityList);
		return fileStatusReportList;

	}

	@Override
	public List<?> retrieveAllEotFiles() {

		List<AcOpsTransCtrlMsgModel> acOpsTransCtrlMsgModelList = new ArrayList<AcOpsTransCtrlMsgModel>();

		try{
			List<CasOpsTransCtrlMsgEntity>
					casOpsTransCtrlMsgEntityList = genericDAO.findAll(CasOpsTransCtrlMsgEntity.class);
			if(casOpsTransCtrlMsgEntityList.size() > 0)
			{
				AcOpsTransCtrlMsgLogic acOpsTransCtrlMsgLogic = new AcOpsTransCtrlMsgLogic();
				acOpsTransCtrlMsgModelList = acOpsTransCtrlMsgLogic.retrieveAllACOpsRecords(
						casOpsTransCtrlMsgEntityList);
			}
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveAllEotFiles :" + e.getMessage());
			e.printStackTrace();
		}

		return acOpsTransCtrlMsgModelList;
	}

	@Override
	public Object retrieveDistinctConfDetails(String instId, String extService) 
	{
		List<CasOpsConfDetailsEntity> opsConfDetailsList = new ArrayList<CasOpsConfDetailsEntity>();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("instId", instId);
			parameters.put("orgnlMsgType", extService);
			parameters.put("processStatus", readyForExtractStatus);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			opsConfDetailsList = (List<CasOpsConfDetailsEntity>) genericDAO.findDistinctByCriteria(
					CasOpsConfDetailsEntity.class, parameters, "txnId");
			log.debug("---------------opsConfDetailsList after findAllByCriteria: ------------------"+ opsConfDetailsList);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on retrieveDistinctConfDetails: " + e.getMessage());
			e.printStackTrace();
		}

		return opsConfDetailsList;
	}

	@Override
	public List<?> retrieveConfDetails(String txnId, String extService, String orgnlMsgType) 
	{
		List<CasOpsConfDetailsEntity> confDetailsList = new ArrayList<CasOpsConfDetailsEntity>();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("txnId", txnId);
			parameters.put("processStatus", readyForExtractStatus);
			parameters.put("extractService", extService);
			parameters.put("orgnlMsgType", orgnlMsgType);
			log.debug("---------------sparameters: ------------------" + parameters.toString());
			confDetailsList = (List<CasOpsConfDetailsEntity>) genericDAO.findAllByCriteria(
					CasOpsConfDetailsEntity.class, parameters);
			log.debug("---------------confDetailsList after findAllByCriteria: ------------------"+ confDetailsList);

		} catch (ObjectNotFoundException onfe) {
			log.debug("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveConfDetails: " + e.getMessage());
			e.printStackTrace();
		}

		return confDetailsList;
	}

	@Override
	public Object retrieveConfHdrsBySeqNo(BigDecimal seqNo) {
		CasOpsConfHdrsEntity confHdrsEntiy = new CasOpsConfHdrsEntity();

		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("systemSeqNo", seqNo);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			confHdrsEntiy = (CasOpsConfHdrsEntity) genericDAO.findByCriteria(CasOpsConfHdrsEntity.class,parameters);
			log.debug("---------------opsStatusDetailsList after findAllByCriteria: ------------------"+ confHdrsEntiy);

		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusHdrsBySeqNo: " + e.getMessage());
			e.printStackTrace();
		}

		return confHdrsEntiy;
	}


	@Override
	public boolean updateConfDetails(Object obj) {
		try {

			if (obj instanceof CasOpsConfDetailsEntity) {

				CasOpsConfDetailsEntity confDetailsEntity = (CasOpsConfDetailsEntity) obj;
				//				log.info("XXXXXX [confDetailsEntity before save] XXXXXXX "+confDetailsEntity);
				genericDAO.saveOrUpdate(confDetailsEntity);

				return true;
			} else {
				log.error("Unable to convert type to CasOpsConfDetailsEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on updateOpsStatusDetails: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateConfHdrs(Object obj) {
		try {

			if (obj instanceof CasOpsConfHdrsEntity) {

				CasOpsConfHdrsEntity confHdrEntity = (CasOpsConfHdrsEntity) obj;
				log.debug("<<<<<<<<<<<<<<<<<<<<<<<<confHdrEntity before UPDATE>>>>>>>>>>>>>>>>>>>>>>>>>:"+ confHdrEntity);
				genericDAO.saveOrUpdate(confHdrEntity);

				return true;
			} else {
				log.error("Unable to convert type to CasOpsConfHdrsEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on updateConfHdrs: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<?> retrieveConfStatusDetails(BigDecimal statusHdrSeqNo) {

		List<CasOpsStatusDetailsEntity> casOpsStatusDetailsEntityList = genericDAO.findAllByNamedQuery("CasOpsStatusDetailsEntity.findByStatusHdrSeqNo","statusHdrSeqNo",statusHdrSeqNo);
		return casOpsStatusDetailsEntityList;
	}

	@Override
	public List<?> retrieveConfDetailsByProcessStatus() 
	{
		List<CasOpsConfDetailsEntity> mdtAcOpsConfDetailsList = new ArrayList<CasOpsConfDetailsEntity>();

		try 
		{	
			mdtAcOpsConfDetailsList = genericDAO.findAllByNamedQuery("CasOpsConfDetailsEntity.findByProcessStatus", "processStatus", extractedStatus);

		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveConfDetailsByProcessStatus: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcOpsConfDetailsList;
	}

	@Override
	public List<?> retrieveStatusHdrsByProcessStatus() 
	{
		List<CasOpsStatusHdrsEntity> mdtAcOpsStatusHdrsList = new ArrayList<CasOpsStatusHdrsEntity>();

		try 
		{	
			mdtAcOpsStatusHdrsList = genericDAO.findAllByNamedQuery("CasOpsStatusHdrsEntity.findByProcessStatus", "processStatus", reportProducedStatus);
			log.debug(" mdtAcOpsStatusHdrsList from DB-->"+mdtAcOpsStatusHdrsList);
		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveStatusHdrsByProcessStatus: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcOpsStatusHdrsList;
	}

	public List<?> retrieveArcConfHdrsByArchiveDate(Date archiveDate) 
	{
		List<MdtAcArcConfHdrsEntity> mdtAcArcConfHdrsList= new ArrayList<MdtAcArcConfHdrsEntity>();

		try 
		{	
			mdtAcArcConfHdrsList = genericDAO.findAllByNamedQuery("CasArcConfHdrsEntity.findByArchiveDateCleanUp","archiveDate", archiveDate);
			log.debug("mdtAcArcConfHdrsList --> "+mdtAcArcConfHdrsList);
		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveArcConfHdrsByArchiveDate: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcArcConfHdrsList;
	}

	public List<?> retrieveArcConfDtlsByArchiveDate(Date archiveDate) 
	{
		List<MdtAcArcConfDetailsEntity> mdtAcArcConfDtlsList= new ArrayList<MdtAcArcConfDetailsEntity>();

		try 
		{	
			mdtAcArcConfDtlsList = genericDAO.findAllByNamedQuery("CasArcConfDetailsEntity.findByArchiveDateCleanUp","archiveDate", archiveDate);
			log.debug("mdtAcArcConfDtlsList --> "+mdtAcArcConfDtlsList);
		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveArcConfDtlsByArchiveDate: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcArcConfDtlsList;
	}

	public List<?> retrieveArcStatHdrsByArchiveDate(Date archiveDate) 
	{
		List<MdtAcArcStatusHdrsEntity> mdtAcArcStatHdrsList= new ArrayList<MdtAcArcStatusHdrsEntity>();

		try 
		{	
			mdtAcArcStatHdrsList = genericDAO.findAllByNamedQuery("CasArcStatusHdrsEntity.findByArchiveDateCleanUp","archiveDate", archiveDate);
			log.debug("mdtAcArcStatHdrsList --> "+mdtAcArcStatHdrsList);
		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveArcStatHdrsByArchiveDate: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcArcStatHdrsList;
	}

	public List<?> retrieveArcStatDtlsByArchiveDate(Date archiveDate) 
	{
		List<MdtAcArcStatusDetailsEntity> mdtAcArcStatDtlsList= new ArrayList<MdtAcArcStatusDetailsEntity>();

		try 
		{	
			mdtAcArcStatDtlsList = genericDAO.findAllByNamedQuery("CasArcStatusDetailsEntity.findByArchiveDateCleanUp","archiveDate", archiveDate);
			log.debug("mdtAcArcStatDtlsList --> "+mdtAcArcStatDtlsList);
		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveArcStatDtlsByArchiveDate: " + e.getMessage());
			e.printStackTrace();
		}

		return mdtAcArcStatDtlsList;
	}

	public boolean generateDuplicateError(String msgId, String txnId, String debtorBrNo, String crAbbShrtName, String mndtRefNo)
	{
		boolean saved = false;
		//Retrieve Status Hdrs Record
		log.debug("generateDuplicateError.msgId ---->"+msgId);
		log.debug("generateDuplicateError.txnId ---->"+txnId);
		log.debug("generateDuplicateError.debtorBrNo ---->"+debtorBrNo);
		log.debug("generateDuplicateError.crAbbShrtName ---->"+crAbbShrtName);
		log.debug("generateDuplicateError.mndtRefNo ---->"+mndtRefNo);

		CasOpsStatusHdrsEntity casOpsStatusHdrsEntity = (CasOpsStatusHdrsEntity) genericDAO.findByNamedQuery("CasOpsStatusHdrsEntity.findByOrgnlMsgId","orgnlMsgId", msgId);
		log.debug("mdtAcOpsStatusHdrsEntity from Duplicate Error ---->"+ casOpsStatusHdrsEntity);

		if(casOpsStatusHdrsEntity != null)
		{
			CasOpsStatusDetailsEntity opsStatusDetailsEntity=new CasOpsStatusDetailsEntity();

			opsStatusDetailsEntity.setSystemSeqNo(new BigDecimal(123));
			opsStatusDetailsEntity.setStatusHdrSeqNo(casOpsStatusHdrsEntity.getSystemSeqNo());
			opsStatusDetailsEntity.setErrorCode("902205");
			opsStatusDetailsEntity.setTxnId(txnId);
			opsStatusDetailsEntity.setTxnStatus("RJCT");
			opsStatusDetailsEntity.setErrorType("TXN");

			if(debtorBrNo != null)
				opsStatusDetailsEntity.setDebtorBranchNo(debtorBrNo);

			if(crAbbShrtName != null)
				opsStatusDetailsEntity.setCrAbbShortName(crAbbShrtName);

			if(mndtRefNo != null)
				opsStatusDetailsEntity.setMandateRefNumber(mndtRefNo);

			try 
			{
				genericDAO.save(opsStatusDetailsEntity);
				saved = true;
			} 
			catch (Exception e) 
			{
				log.error("Error on generateDuplicateError: " + e.getMessage());
				e.printStackTrace();
				saved = false;
			}
		}
		return saved;
	}

//	2020/08/25-SalehaR: Redundant Method-Use Flat Table
//	@Override
//	public boolean loadMdteRespMsg(Object obj/*, String debtorBrNo, String crAbbShrtName, String mndtRefNo*/) 
//	{
//		boolean isLoaded = true;
//		try 
//		{
//
//			if (obj instanceof MdtAcOpsMdteRespMsgEntity) 
//			{
//				MdtAcOpsMdteRespMsgEntity mdtAcOpsMdteRespMsgEntity = (MdtAcOpsMdteRespMsgEntity) obj;
//
//				String savedmsg = genericDAO.save(mdtAcOpsMdteRespMsgEntity);
//
//				log.debug("savedmsg ----> "+savedmsg);
//
//				//					if(savedmsg.equalsIgnoreCase("DUPL") || savedmsg.equalsIgnoreCase("ERROR"))
//				//					{
//				//						isLoaded = false;
//				//						log.error("Duplicated Detected");
//				//						//Create Duplicate Error
//				//						generateDuplicateError(mdtAcOpsMdteRespMsgEntity.getMdtAcOpsMdteRespMsgPK().getMsgId(), mdtAcOpsMdteRespMsgEntity.getMdtAcOpsMdteRespMsgPK().getMandateReqTranId(), debtorBrNo, crAbbShrtName, mndtRefNo);
//				//					}
//				//					else
//				//					{
//				//						isLoaded = true;
//				//					}
//			} 
//			else 
//			{
//				log.error("Unable to convert type to MdtAcOpsMdteRespMsgEntity.");
//				//				isLoaded = false;
//			}
//		} 
//		catch (Exception e) 
//		{
//			log.debug("Error on loadMdteResponse: " + e.getMessage());
//			e.printStackTrace();
//			//			isLoaded = false;
//		}
//		//		log.debug("~~~~~~~~~~~~~isLoaded-------> "+isLoaded);
//		return isLoaded;
//	}

//	2020/08/25-SalehaR: Redundant Method-Use Flat Table
//	public Object retrieveMdteResponse(String msgId, String mdtInfReqId) 
//	{
//		MdtAcOpsMdteRespMsgEntity mdtAcOpsMdteRespMsgEntity = new MdtAcOpsMdteRespMsgEntity();
//
//		HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//		if (msgId != null)
//			parameters.put("mdtAcOpsMdteRespMsgPK.msgId", msgId);
//		//		if (mandateReqTranId != null)
//		//			parameters.put("mdtAcOpsMdteRespMsgPK.mandateReqTranId", mandateReqTranId);
//		if (mdtInfReqId != null)
//			parameters.put("mdtAcOpsMdteRespMsgPK.mdtInfReqId", mdtInfReqId);
//
//		log.debug("---------------sparameters: ------------------" + parameters.toString());
//		mdtAcOpsMdteRespMsgEntity = (MdtAcOpsMdteRespMsgEntity) genericDAO.findByCriteria(MdtAcOpsMdteRespMsgEntity.class, parameters);
//		log.debug("---------------mdtAcOpsInitMndtMsgEntity after findAllByCriteria: ------------------"+ mdtAcOpsMdteRespMsgEntity);
//
//		return mdtAcOpsMdteRespMsgEntity;
//	}

//	2020/08/25-SalehaR: Redundant Method-Use Flat Table
//	@Override
//	public List<?> retrieveMdteMsgsResponsesByProcessStatus() 
//	{
//		List<MdtAcOpsMdteRespMsgEntity> mdtAcOpsMdteMsgRespList = new ArrayList<MdtAcOpsMdteRespMsgEntity>();
//
//		try 
//		{	
//			mdtAcOpsMdteMsgRespList = genericDAO.findAllByNamedQuery("MdtAcOpsMdteRespMsgEntity.findByProcessStatus", "processStatus", extractedStatus);
//
//		} catch (NullPointerException npe) {
//			log.error("NullPointer exception :" + npe.getMessage());
//		} catch (ObjectNotFoundException onfe) {
//			log.error("No Object Exists on DB");
//		} catch (Exception e) {
//			log.error("Error on retrieveMdteMsgsResponsesByProcessStatus: " + e.getMessage());
//			e.printStackTrace();
//		}
//
//		return mdtAcOpsMdteMsgRespList;
//	}

//	2020/08/25-SalehaR: Redundant Method-Use Flat Table
//	@Override
//	public Boolean updateMdteRespMsg(Object obj) 
//	{
//		Boolean updated = false;
//		try {
//			if (obj instanceof MdtAcOpsMdteRespMsgEntity) {
//				MdtAcOpsMdteRespMsgEntity mdtAcOpsMdteRespMsgEntity = (MdtAcOpsMdteRespMsgEntity) obj;
//
//				genericDAO.saveOrUpdate(mdtAcOpsMdteRespMsgEntity);
//				updated = true;
//			} else {
//				log.error("Unable to convert type to MdtAcOpsMdteRespMsgEntity.");
//				updated = false;
//			}
//		} catch (Exception e) {
//			log.error("Error on updateMdteRespMsg: " + e.getMessage());
//			e.printStackTrace();
//			updated = false;
//		}
//		return updated;
//	}

//	2020/08/25-SalehaR: Redundant Method-Use Flat Table
//	@Override
//	public boolean loadMdteResponse(Object obj, String debtorBrNo, String crAbbShrtName, String mndtRefNo) 
//	{
//		boolean isLoaded = false;
//		try 
//		{
//
//			if (obj instanceof MdtAcOpsMdteRespEntity) 
//			{
//				MdtAcOpsMdteRespEntity mdtAcOpsMdteRespEntity = (MdtAcOpsMdteRespEntity) obj;
//
//				String savedmsg = genericDAO.save(mdtAcOpsMdteRespEntity);
//
//				log.debug("savedmsg ----> "+savedmsg);
//
//				if(savedmsg.equalsIgnoreCase("DUPL") || savedmsg.equalsIgnoreCase("ERROR"))
//				{
//					isLoaded = false;
//					log.debug("Duplicated Detected");
//					//Create Duplicate Error
//					generateDuplicateError(mdtAcOpsMdteRespEntity.getMdteMsgId(), mdtAcOpsMdteRespEntity.getMdtInfReqId(), debtorBrNo, crAbbShrtName, mndtRefNo);
//				}
//				else
//				{
//					isLoaded = true;
//				}
//			} 
//			else 
//			{
//				log.debug("Unable to convert type to MdtAcOpsMndtMsgEntity.");
//				isLoaded = false;
//			}
//		} 
//		catch (Exception e) 
//		{
//			log.error("Error on createLoadMandateRegister: " + e.getMessage());
//			e.printStackTrace();
//			isLoaded = false;
//		}
//		log.debug("~~~~~~~~~~~~~isLoaded-------> "+isLoaded);
//		return isLoaded;
//	}

	@Override
	public List<?> retrieveMndtCount() {

		List<CasOpsMndtCountEntity> mndtCountList = new ArrayList<CasOpsMndtCountEntity>();

		try{
			mndtCountList=genericDAO.findAll(CasOpsMndtCountEntity.class);
			log.debug("mndtCountList---->"+mndtCountList);

		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} 
		catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) {
			log.error("Error on retrieveMndtCount: " + e.getMessage());
			e.printStackTrace();
		}

		return mndtCountList;
	}

	@Override
	public boolean saveOpsDailyBilling(Object obj) 
	{
		try 
		{
			if (obj instanceof CasOpsDailyBillingEntity)
			{
				CasOpsDailyBillingEntity opsDailyBillingEntity = (CasOpsDailyBillingEntity) obj;
				//				log.info("Entity in Save==> "+opsDailyBillingEntity);
				genericDAO.save(opsDailyBillingEntity);

				return true;
			} 
			else 
			{
				log.info("Unable to convert type to MdtAcOpsDailyBillingEntity.");
				return false;
			}
		} 
		catch (Exception e) 
		{
			log.error("Error on saveOpsDailyBilling: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	@Override
	//	public BigDecimal retrieveDailyBillingInterCngInfo()
	public List<?> retrieveDailyBillingInterCngInfo()
	{
		BigDecimal lastSeqNo= BigDecimal.ZERO;
		List<CasOpsDailyBillingEntity> dailyBillingInfoList = new ArrayList<CasOpsDailyBillingEntity>();

		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("billExpStatus", "N");

		dailyBillingInfoList = (List<CasOpsDailyBillingEntity>) genericDAO.findAllByOrderCriteria(
				CasOpsDailyBillingEntity.class, parameters, true, "systemSeqNo");
		log.info("Daily Billing Info List ==> "+dailyBillingInfoList);

		//		2017-07-10 SalehaR - Move this to the billing export code
		//		if(dailyBillingInfoList != null && dailyBillingInfoList.size() > 0)
		//		{
		//		//2017-07-10 SalehaR - Move this to the billing export code
		////			MdtAcOpsDailyBillingEntity acOpsDailyBillingEntity = dailyBillingInfoList.get(dailyBillingInfoList.size() - 1);
		////			lastSeqNo=acOpsDailyBillingEntity.getSystemSeqNo();
		//			
		//			//2017-07-10~SalehaR_Remove the update for Billing from here to after the data has been push
		//			//Update records picked for billing.
		////			for (MdtAcOpsDailyBillingEntity dailyBillEntity : dailyBillingInfoList) 
		////			{
		////				dailyBillEntity.setBillExpStatus("Y");
		////				 genericDAO.saveOrUpdate(dailyBillEntity);
		////			}
		//			
		//		}
		return dailyBillingInfoList;
	}


	public Object retrieveBillingCtrls(String processName) 
	{
		CasOpsBillingCtrlsEntity casOpsBillingCtrlsEntity = new CasOpsBillingCtrlsEntity();

		try 
		{
			casOpsBillingCtrlsEntity = (CasOpsBillingCtrlsEntity) genericDAO.findByNamedQuery("CasOpsBillingCtrlsEntity.findByProcessType", "processType", processName);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) {
			log.error("Error on retrieveBillingCtrls: " + e.getMessage());
			e.printStackTrace();
		}

		return casOpsBillingCtrlsEntity;
	}

	@Override
	public boolean saveBillingCntrl(Object obj) 
	{
		try 
		{
			if (obj instanceof CasOpsBillingCtrlsEntity)
			{
				CasOpsBillingCtrlsEntity casOpsBillingCtrlsEntity = (CasOpsBillingCtrlsEntity) obj;
				genericDAO.saveOrUpdate(casOpsBillingCtrlsEntity);

				return true;
			} 
			else 
			{
				log.info("Unable to convert type to MdtAcOpsBillingCtrlsEntity.");
				return false;
			}
		} 
		catch (Exception e) 
		{
			log.error("Error on saveBillingCntrl: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public List<?> retrieveInterchangeBillingData(String currentSeqNo, String lastSeqNo)
	{
		List<CasOpsDailyBillingEntity> intChngBillingList = new ArrayList<CasOpsDailyBillingEntity>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT CREDITOR_BANK as creditorBank, DEBTOR_BANK as debtorBank,SUB_SERVICE as subService,TXN_TYPE as txnType,TXN_STATUS as txnStatus, COUNT(*) AS volume,  ACTION_DATE as actionDate, AUTH_CODE as authCode, RESP_DATE as respDate ");
		sb.append("FROM CAMOWNER.CAS_OPS_DAILY_BILLING ");
		sb.append("WHERE BILL_EXP_STATUS = 'N' AND (SYSTEM_SEQ_NO > "+currentSeqNo+" AND SYSTEM_SEQ_NO <= "+lastSeqNo+") ");
		sb.append("GROUP BY CREDITOR_BANK, SUB_SERVICE,TXN_TYPE, DEBTOR_BANK, TXN_STATUS, ACTION_DATE, AUTH_CODE, RESP_DATE ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		log.debug("scalarList: " + scalarList);

		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("subService");
		scalarList.add("txnType");
		scalarList.add("txnStatus");
		scalarList.add("volume");
		scalarList.add("actionDate");
		scalarList.add("authCode");
		scalarList.add("respDate");

		intChngBillingList = genericDAO.findBySql(sqlQuery, scalarList, InterchgBillingDataModel.class);

		return intChngBillingList;
	}


	@Override
	public boolean createBillingRecords(Object obj) 
	{
		try {

			if (obj instanceof ObsBillingStagingEntity) {
				//				log.debug("In the billing staging entity<><><><><><><>");
				ObsBillingStagingEntity obsBillingStagingEntity = (ObsBillingStagingEntity) obj;
				log.info("<><><>obsBillingStagingEntity:<><><> "+ obsBillingStagingEntity);
				genericDAO.save(obsBillingStagingEntity);
				return true;
			} else {
				log.info("Unable to convert type to ObsBillingStagingEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on createBillingRecords: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}






	@Override	
	public Object retrieveAmend16Data(String memberNo,String amendmetReasonCode,String firstDate, String lastDate)
	{

		log.debug("retrieving incoming files using sql statement *************************");
		List<MandateAmendModel> mandateAmendModelList =  new ArrayList<MandateAmendModel>();
		List<MandateAmendEntityModel> mandateAmendEntityModelList = new ArrayList<MandateAmendEntityModel>();
		MandateAmendModel mandateAmendModel = new MandateAmendModel() ;

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT b.MEMBER_NAME ,SUBSTR(a.msg_id,13,6), a.AMEND_REASON_CODE,COUNT(*) AS amendReasonCodeCount "); 
		sb.append("FROM  CAMOWNER.MDT_AC_ARC_MNDT_MSG a  ");
		sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ON SUBSTR(a.msg_id,13,6)= b.MEMBER_NO ");
		sb.append("WHERE SUBSTR(a.msg_id,13,6) = '"+memberNo+"' AND a.AMEND_REASON_CODE = '"+amendmetReasonCode+"'  ");
		sb.append("AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+firstDate+"','DDMMYYYY') AND TO_DATE('"+lastDate+"','DDMMYYYY') AND a.SERVICE_ID ='CARIN' ");
		sb.append("GROUP BY AMEND_REASON_CODE, SUBSTR(a.msg_id,13,6), MEMBER_NAME ");



		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("amendReasonCodeCount");

		mandateAmendEntityModelList =  genericDAO.findBySql(sqlQuery, scalarList, MandateAmendEntityModel.class);

		if (mandateAmendEntityModelList.size() > 0) {

			for (MandateAmendEntityModel localModelEntity : mandateAmendEntityModelList) 
			{
				mandateAmendModel = new MandateAmendModel();
				mandateAmendModel = new ServiceTranslator().translateMandateAmend16EntityModelToCommonsModel(localModelEntity);
				mandateAmendModelList.add(mandateAmendModel);
				//localModel = mandateAmendModel;
				log.debug("The List list has this information #####################" + mandateAmendEntityModelList);

			}
		}

		log.debug("The  Amendmodel has this information #####################" + mandateAmendModel);
		return mandateAmendModel;
	}

	@Override
	public Object retrieveReasonCodeTotal(String amendmetReasonCode,String firstDate, String lastDate) {
		log.debug("retrieving incoming files using sql statement *************************");
		List<MandateAmendModel> mandateAmendModelList =  new ArrayList<MandateAmendModel>();
		List<MandateAmendEntityModel> mandateAmendEntityModelList = new ArrayList<MandateAmendEntityModel>();
		MandateAmendModel mandateAmendModel = new MandateAmendModel() ;

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(amendReasonCodeCount) AS amendReasonCodeCount FROM ");
		sb.append("(SELECT b.MEMBER_NAME ,SUBSTR(a.msg_id,13,6), a.AMEND_REASON_CODE,COUNT(*) AS amendReasonCodeCount "); 
		sb.append("FROM  CAMOWNER.MDT_AC_ARC_MNDT_MSG a  ");
		sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK b ON SUBSTR(a.msg_id,13,6)= b.MEMBER_NO ");
		sb.append("WHERE a.AMEND_REASON_CODE = '"+amendmetReasonCode+"'  ");
		sb.append("AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+firstDate+"','DDMMYYYY') AND TO_DATE('"+lastDate+"','DDMMYYYY') AND a.SERVICE_ID ='CARIN' ");
		sb.append("GROUP BY AMEND_REASON_CODE, SUBSTR(a.msg_id,13,6), MEMBER_NAME) ");


		String sqlQuery = sb.toString();
		log.info("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("amendReasonCodeCount");

		mandateAmendEntityModelList =  genericDAO.findBySql(sqlQuery, scalarList, MandateAmendEntityModel.class);

		if (mandateAmendEntityModelList.size() > 0) {

			for (MandateAmendEntityModel localModelEntity : mandateAmendEntityModelList) 
			{
				mandateAmendModel = new MandateAmendModel();
				mandateAmendModel = new ServiceTranslator().translateMandateAmend16EntityModelToCommonsModel(localModelEntity);
				mandateAmendModelList.add(mandateAmendModel);
				//localModel = mandateAmendModel;
				log.debug("The List list has this information #####################" + mandateAmendEntityModelList);

			}
		}

		log.debug("The  Amendmodel has this information #####################" + mandateAmendModel);
		return mandateAmendModel;
	}

	@Override
	public Object retrieveRejectReasonData(String rejectReasonCode,String memberNo) {

		List<MandateRejectionModel> mandateRejectionModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateRejectionEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateRejectionModel = new MandateRejectionModel();


		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addOneDay(currentDate);
			log.info("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		String dayOne = stringDate.substring(2,8);
		String firstDateOfMonth ="01"+dayOne;
		log.info("firsDateOfMonth**************"+ firstDateOfMonth);


		StringBuffer sb = new StringBuffer();

		/*	select DR_BANK_MEMBER_ID,REJECT_REASON, count(*) as Count
		from MDT_AC_ARC_MDTE_RESP
		where DR_BANK_MEMBER_ID = '210044' AND REJECT_REASON ='MDNF'
		GROUP BY DR_BANK_MEMBER_ID,REJECT_REASON*/

		sb.append("SELECT CR_BANK_MEMBER_ID, REJECT_REASON, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MDTE_RESP ");
		sb.append("WHERE CR_BANK_MEMBER_ID = '"+memberNo+"' AND REJECT_REASON = '"+rejectReasonCode+"' AND ");
		sb.append("TRUNC(CREATE_DATE_TIME) BETWEEN TO_DATE('"+firstDateOfMonth+"','ddMMYYYY') AND TO_DATE('"+stringDate+"','ddMMYYYY') ");
		sb.append("GROUP BY CR_BANK_MEMBER_ID , REJECT_REASON ");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateRejectionEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateRejectionEntityList != null && mandateRejectionEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel mandateRejectionEntityModel : mandateRejectionEntityList)
			{
				mandateRejectionModel = new MandateRejectionModel();
				mandateRejectionModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(mandateRejectionEntityModel);
				mandateRejectionModelList.add(mandateRejectionModel);
			}
		}

		return mandateRejectionModel;
	}

	@Override
	public Object retrieveSuspensionData(String suspensionCode, String memberNo) 
	{
		List<MandateRejectionModel> mandateSuspModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateSuspEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateSuspModel = new MandateRejectionModel();

		StringBuffer sb = new StringBuffer();

		/*		select b.ASSIGNER ,a.REASON_CODE, count(*) as Count
		from MDT_AC_OPS_SUSP_MSG a 
		LEFT JOIN MDT_AC_OPS_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.assignment_id
		where ASSIGNER = '210055' AND REASON_CODE ='CTCA'
		GROUP BY ASSIGNER,REASON_CODE*/


		sb.append("SELECT b.ASSIGNER, a.REASON_CODE, COUNT(*)AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_SUSP_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID = b.ASSIGNMENT_ID ");
		sb.append("WHERE ASSIGNER = '"+memberNo+"' AND REASON_CODE = '"+suspensionCode+"' ");
		sb.append("GROUP BY ASSIGNER,REASON_CODE ");



		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateSuspEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateSuspEntityList != null && mandateSuspEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel mandateSuspEntityModel : mandateSuspEntityList)
			{
				mandateSuspModel = new MandateRejectionModel();
				mandateSuspModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(mandateSuspEntityModel);
				mandateSuspModelList.add(mandateSuspModel);
			}
		}

		return mandateSuspModel;

	}

	@Override
	public Object retrieveReasonCodeData(String reasonCode, String memberNo) {
		List<MandateRejectionModel> mandateReasonCodeModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateReasonCodeEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateReasonCodeModel = new MandateRejectionModel();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");

		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addOneDay(currentDate);
			log.info("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		String dayOne = stringDate.substring(2,8);
		String firstDateOfMonth ="01"+dayOne;
		log.info("firsDateOfMonth**************"+ firstDateOfMonth);


		StringBuffer sb = new StringBuffer();


		/*  SELECT b.MEMBER_ID,a.REJECT_REASON_CODE, COUNT(*) AS COUNT
	    FROM MDT_AC_ARC_MNDT_MSG a
	    LEFT JOIN MDT_AC_ARC_FIN_INST b  ON a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID
	    WHERE FIN_INST_TYPE_ID = 'FI04' AND  MEMBER_ID = '210003' AND REJECT_REASON_CODE ='AC01'AND SERVICE_ID ='RCAIN' 
	    GROUP BY MEMBER_ID, REJECT_REASON_CODE*/


		sb.append("SELECT b.MEMBER_ID, a.REJECT_REASON_CODE,COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
		sb.append("WHERE FIN_INST_TYPE_ID = 'FI03' AND SERVICE_ID = 'RCAIN' AND MEMBER_ID ='"+memberNo+"' AND REJECT_REASON_CODE = '"+reasonCode+"' ");
		sb.append("AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+firstDateOfMonth+"','ddMMYYYY') AND TO_DATE('"+stringDate+"','ddMMYYYY') ");
		sb.append("GROUP BY MEMBER_ID, REJECT_REASON_CODE");

		String sqlQuery = sb.toString();
		log.info("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateReasonCodeEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateReasonCodeEntityList != null && mandateReasonCodeEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel mandateReasonCodeEntityModel : mandateReasonCodeEntityList)
			{
				mandateReasonCodeModel = new MandateRejectionModel();
				mandateReasonCodeModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(mandateReasonCodeEntityModel);
				mandateReasonCodeModelList.add(mandateReasonCodeModel);
			}
		}

		return mandateReasonCodeModel;
	}

	@Override
	public Object retrieveRejectReasonCount(String memberNo) {

		List<MandateRejectionModel> mandateRejectCountModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateRejectCountEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateRejectCountModel = new MandateRejectionModel();

		StringBuffer sb = new StringBuffer();

		/*select DR_BANK_MEMBER_ID,REJECT_REASON, count(*) as count
		from MDT_AC_ARC_MDTE_RESP
		where  DR_BANK_MEMBER_ID ='210044'and REJECT_REASON is not null
		GROUP BY REJECT_REASON,DR_BANK_MEMBER_ID*/

		sb.append("SELECT DR_BANK_MEMBER_ID,REJECT_REASON, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MDTE_RESP ");
		sb.append("WHERE DR_BANK_MEMBER_ID ='"+memberNo+"' AND REJECT_REASON is not null ");
		sb.append("GROUP BY REJECT_REASON,DR_BANK_MEMBER_ID ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateRejectCountEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateRejectCountEntityList != null && mandateRejectCountEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel mandateRejectReasonCountEntityModel : mandateRejectCountEntityList)
			{
				mandateRejectCountModel = new MandateRejectionModel();
				mandateRejectCountModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(mandateRejectReasonCountEntityModel);
				mandateRejectCountModelList.add(mandateRejectCountModel);
			}
		}
		return mandateRejectCountModel;

	}

	@Override
	public Object retrieveSuspensionCount(String memberNo) {
		List<MandateRejectionModel> mandateSuspensionCountModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateSuspensionCountEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateSuspensionCountModel = new MandateRejectionModel();

		StringBuffer sb = new StringBuffer();


		/*select b.ASSIGNER ,a.REASON_CODE, count(*) as Count
			from MDT_AC_OPS_SUSP_MSG a 
			LEFT JOIN MDT_AC_OPS_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.ASSIGNMENT_ID
			where ASSIGNER = '210055' and REASON_CODE is not null
			GROUP BY ASSIGNER,REASON_CODE*/

		sb.append("SELECT b.ASSIGNER, a.REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR b ON a.ASSIGNMENT_ID =b.ASSIGNMENT_ID ");
		sb.append("WHERE ASSIGNER ='"+memberNo+"' AND REASON_CODE is not null ");
		sb.append(" GROUP BY ASSIGNER,REASON_CODE");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateSuspensionCountEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateSuspensionCountEntityList != null && mandateSuspensionCountEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel suspCountEntityModel : mandateSuspensionCountEntityList)
			{
				mandateSuspensionCountModel = new MandateRejectionModel();
				mandateSuspensionCountModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(suspCountEntityModel);
				mandateSuspensionCountModelList.add(mandateSuspensionCountModel);
			}
		}
		return mandateSuspensionCountModel;
	}

	@Override
	public Object retrieveReasonCodeCount(String memberNo) {
		List<MandateRejectionModel> mandateReasonCodeCountModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateReasonCodeCountEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateReasonCodeCountModel = new MandateRejectionModel();

		StringBuffer sb = new StringBuffer();


		/*SELECT b.MEMBER_ID,a.REJECT_REASON_CODE, COUNT(*) AS COUNT
	    FROM MDT_AC_ARC_MNDT_MSG a
	    LEFT JOIN MDT_AC_ARC_FIN_INST b  ON a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID
	    WHERE FIN_INST_TYPE_ID = 'FI04' AND  MEMBER_ID = '210003' AND REJECT_REASON_CODE is not null AND SERVICE_ID ='RCAIN' 
	    GROUP BY MEMBER_ID, REJECT_REASON_CODE*/


		sb.append("SELECT b.MEMBER_ID,a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b  ON a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
		sb.append("WHERE FIN_INST_TYPE_ID = 'FI04' AND MEMBER_ID ='"+memberNo+"'AND REJECT_REASON_CODE is not null AND SERVICE_ID ='RCAIN' ");
		sb.append(" GROUP BY MEMBER_ID, REJECT_REASON_CODE ");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateReasonCodeCountEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateReasonCodeCountEntityList != null && mandateReasonCodeCountEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel reasonCountEntityModel : mandateReasonCodeCountEntityList)
			{
				mandateReasonCodeCountModel = new MandateRejectionModel();
				mandateReasonCodeCountModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(reasonCountEntityModel);
				mandateReasonCodeCountModelList.add(mandateReasonCodeCountModel);
			}
		}
		return mandateReasonCodeCountModel;
	}

	@Override
	public Object retrieverRespOutstandForOneDay(String debtorMember,String creditorMember,String serviceId) {

		List<BatchOustandingResponseModel> oustandingResponseSummaryModelList = new ArrayList<BatchOustandingResponseModel>();
		List<BatchOustandingResponseEntityModel> oustandingResponseSummaryEntityList = new ArrayList<BatchOustandingResponseEntityModel>();
		BatchOustandingResponseModel oustandingResponseSummaryModel = new BatchOustandingResponseModel();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		//		Date currentDate = new Date();
		Date  date = null;

		CasSysctrlSysParamEntity casSysctrlSysParamEntity = (CasSysctrlSysParamEntity) genericDAO.findByNamedQuery("CasSysctrlSysParamEntity.findByActiveInd", "activeInd","Y");
		Date currentDate;

		if(casSysctrlSysParamEntity != null && casSysctrlSysParamEntity.getProcessDate() != null)
		{
			currentDate = casSysctrlSysParamEntity.getProcessDate();
		}
		else
		{
			currentDate = new Date();
		}


		try {

			date= DateUtil.addOneDay(currentDate);
			log.debug("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		log.debug("stringDate*******:"+stringDate);


		StringBuffer sb = new StringBuffer();



		sb.append("SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS nrOfTxns ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS IN ('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6) = '"+debtorMember+"' AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND SUBSTR(a.MSG_ID,13,6)= '"+creditorMember+"' AND TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') ='"+stringDate+"' ");
		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("debtorBank");
		scalarList.add("creditorBank");
		scalarList.add("nrOfTxns");
		scalarList.add("creditorName");
		scalarList.add("serviceId");
		scalarList.add("debtorName");

		oustandingResponseSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, BatchOustandingResponseEntityModel.class);

		if(oustandingResponseSummaryEntityList != null && oustandingResponseSummaryEntityList.size()> 0)
		{
			for(BatchOustandingResponseEntityModel oustandingResponseEntityModel : oustandingResponseSummaryEntityList)
			{
				oustandingResponseSummaryModel = new BatchOustandingResponseModel();
				oustandingResponseSummaryModel = new ServiceTranslator().translateOustandingResponseSummaryEntityModelToCommonsModel(oustandingResponseEntityModel);
				oustandingResponseSummaryModelList.add(oustandingResponseSummaryModel);
			}
		}

		return oustandingResponseSummaryModel;


	}

	@Override
	public Object retrieverRespOutstandForTwoDays(String debtorMember,String creditorMember,String serviceId)
	{
		List<BatchOustandingResponseModel> oustandingResponseSummaryModelList = new ArrayList<BatchOustandingResponseModel>();
		List<BatchOustandingResponseEntityModel> oustandingResponseSummaryEntityList = new ArrayList<BatchOustandingResponseEntityModel>();
		BatchOustandingResponseModel oustandingResponseSummaryModel = new BatchOustandingResponseModel();


		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addNoOfDays(currentDate);
			log.debug("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		log.debug("stringDate*******:"+stringDate);


		StringBuffer sb = new StringBuffer();


		/*		//sb.append("LEFT JOIN CAMOWNER.SYS_CIS_BANK d ON c.MEMBER_ID = d.MEMBER_NO ");
		sb.append("SELECT b.MEMBER_ID As creditorBank,c.MEMBER_ID  AS debtorBank, COUNT(*) AS nrOfDays ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_OPS_FIN_INST b ON a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID AND b.FIN_INST_TYPE_ID = 'FI03' ");
		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_OPS_FIN_INST C ON a.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID AND b.FIN_INST_TYPE_ID = 'FI04' ");
		sb.append("WHERE  a.PROCESS_STATUS ='9' AND b.MEMBER_ID = '"+creditorMember+"' AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND  TO_CHAR(a.CREATED_DATE,'ddMMYYYY') = '"+stringDate+"' ");
		sb.append("GROUP BY b.MEMBER_ID,c.MEMBER_ID ");*/

		sb.append("SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS nrOfTxns ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS IN('4','9') AND SUBSTR(a.EXTRACT_MSG_ID,13,6) = '"+debtorMember+"' AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND SUBSTR(a.MSG_ID,13,6)= '"+creditorMember+"' AND  TO_CHAR(a.CREATED_DATE, 'ddMMYYYY') = '"+stringDate+"' ");
		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME ");




		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("debtorBank");
		scalarList.add("creditorBank");
		scalarList.add("nrOfTxns");
		scalarList.add("creditorName");
		scalarList.add("serviceId");
		scalarList.add("debtorName");


		oustandingResponseSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, BatchOustandingResponseEntityModel.class);

		if(oustandingResponseSummaryEntityList != null && oustandingResponseSummaryEntityList.size()> 0)
		{
			for(BatchOustandingResponseEntityModel oustandingResponseEntityModel : oustandingResponseSummaryEntityList)
			{
				oustandingResponseSummaryModel = new BatchOustandingResponseModel();
				oustandingResponseSummaryModel = new ServiceTranslator().translateOustandingResponseSummaryEntityModelToCommonsModel(oustandingResponseEntityModel);
				oustandingResponseSummaryModelList.add(oustandingResponseSummaryModel);
			}
		}
		return oustandingResponseSummaryModel;
	}

	public boolean eodCheckIfAllFilesAreExtracted(Date systemDate, String mdtLoadType)
	{
		boolean eodFilesCheck = false;
		boolean painMsgsCheck = false, confDtlsCheck = false, mdte001Check = false, mdte002Check = false, camt055Check = false, statusRptsCheck = false, mrkoffCheck = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strSysDate = sdf.format(systemDate);
		log.debug("strSysDate ====> "+strSysDate);

			log.info("<===Single Table Extract Check===> ");
			try
			{
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("processStatus", readyForExtractStatus);

				List<CasOpsCessionAssignEntity> opsMandateTxnsList = (List<CasOpsCessionAssignEntity>) genericDAO.findAllByCriteria(
						CasOpsCessionAssignEntity.class, parameters);
				if(opsMandateTxnsList != null && opsMandateTxnsList.size() > 0)
				{
					log.debug("opsMandateTxnsList.size(); =====> "+opsMandateTxnsList.size());
					painMsgsCheck = false;
				}
				else
					painMsgsCheck = true;
			}
			catch (ObjectNotFoundException onfe)
			{
				log.debug("No Object Exists on DB");
			}
			catch (Exception e)
			{
				log.error("Error on eodCheckIfAllFilesAreExtracted.opsMandateTxnsList: "+ e.getMessage());
				e.printStackTrace();
			}

		//2.Check ConfDtlsMsgs
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", readyForExtractStatus);

			List<CasOpsConfDetailsEntity> opsConfDetailsCheckList = (List<CasOpsConfDetailsEntity>) genericDAO.findAllByCriteria(
					CasOpsConfDetailsEntity.class, parameters);
			if(opsConfDetailsCheckList != null && opsConfDetailsCheckList.size() > 0)
			{
				log.debug("opsConfDetailsCheckList.size(); =====> "+opsConfDetailsCheckList.size());
				confDtlsCheck = false;
			}
			else
				confDtlsCheck = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on eodCheckIfAllFilesAreExtracted.opsConfDetailsCheckList: "+ e.getMessage());
			e.printStackTrace();
		}

		//6.Check Status Reports Status
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", reportToBeProdStatus);

			List<CasOpsStatusHdrsEntity> statusRptCheckList = (List<CasOpsStatusHdrsEntity>) genericDAO.findByCriteriaUsingTrunc(
					CasOpsStatusHdrsEntity.class, parameters, "CREATE_DATE_TIME", "YYYY-MM-DD", strSysDate);
			log.debug("statusRptCheckList.size(); =====> "+statusRptCheckList.size());
			if(statusRptCheckList != null && statusRptCheckList.size() > 0)
				statusRptsCheck = false;
			else
				statusRptsCheck = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on eodCheckIfAllFilesAreExtracted.statusRptCheckList: "+ e.getMessage());
			e.printStackTrace();
		}

		log.debug("painMsgsCheck ==> "+painMsgsCheck+ "\n"+
				"confDtlsCheck ==> "+confDtlsCheck + "\n"+
				"mdte001Check ==> "+mdte001Check + "\n"+ 
				"mdte002Check ==> "+mdte002Check + "\n"+ 
				"camt055Check ==> "+camt055Check + "\n"+ 
				"statusRptsCheck ==> "+statusRptsCheck + "\n"+
				"mrkoffCheck ==> "+mrkoffCheck);


		if(painMsgsCheck && confDtlsCheck && mdte001Check && mdte002Check && camt055Check && statusRptsCheck && mrkoffCheck)
			eodFilesCheck = true;
		else
			eodFilesCheck = false;

		return eodFilesCheck;
	}

	public List<?> calculateCountsReportInfo()
	{
		List<OutBalanceCountReportEntityModel> countsReportInfoEntityList = new ArrayList<OutBalanceCountReportEntityModel>();
		List<OutBalanceCountReportEntityModel> extractReportEntityList = new ArrayList<OutBalanceCountReportEntityModel>();
		List<OutBalanceCountReportModel> countsReportInfoList = new ArrayList<OutBalanceCountReportModel>();

		StringBuffer sb = new StringBuffer();


		//		sb.append("SELECT SERVICE_ID AS serviceId, SUM(NR_OF_MSGS) AS totalNrOfMsgs, SUM(NR_MSGS_ACCEPTED) AS nrMsgsAccepted, SUM(NR_MSGS_REJECTED) AS nrMsgsRejected ");
		//		sb.append("FROM  CAMOWNER.CAS_OPS_MNDT_COUNT ");
		//		sb.append(" WHERE INCOMING = 'Y' ");
		//		sb.append("GROUP BY SERVICE_ID ");
		//		sb.append("ORDER BY SERVICE_ID ASC ");

		sb.append("SELECT Q1.SERVICE_IN as inServiceId, Q1.TOTALMSGS as totalNrOfMsgs, Q1.TOTALACCP as nrMsgsAccepted, Q1.TOTALREJ as nrMsgsRejected, Q1.OPS_SERVICE_OUT as outServiceId, ");
		sb.append("Q2.SERVICE_OUT as extServiceId, NVL(Q2.TOTALEXT,0) as nrMsgsExtracted , NVL(Q1.TOTALACCP - NVL(Q2.TOTALEXT,0), 0) as difference ");
		sb.append("FROM (SELECT a.SERVICE_ID AS SERVICE_IN, SUM(NVL(a.NR_OF_MSGS, 0)) AS TOTALMSGS, SUM(NVL(a.NR_MSGS_ACCEPTED, 0)) AS TOTALACCP ");
		sb.append(",SUM(NVL(a.NR_MSGS_REJECTED, 0)) AS TOTALREJ, c.SERVICE_ID_OUT AS OPS_SERVICE_OUT ");
		sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.MDT_OPS_SERVICES c ON c.SERVICE_ID_IN = a.SERVICE_ID WHERE a.INCOMING = 'Y' ");
		sb.append("GROUP BY a.SERVICE_ID,c.SERVICE_ID_OUT) Q1 ");
		sb.append("LEFT JOIN ");
		sb.append("(SELECT b.SERVICE_ID AS SERVICE_OUT, SUM(NVL(b.NR_MSGS_EXTRACTED, 0)) AS TOTALEXT ");
		sb.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT b WHERE b.OUTGOING = 'Y' ");
		sb.append("GROUP BY b.SERVICE_ID) Q2 ");
		sb.append("ON Q1.OPS_SERVICE_OUT = Q2.SERVICE_OUT ");
		sb.append("GROUP BY Q1.SERVICE_IN, Q1.OPS_SERVICE_OUT, Q2.SERVICE_OUT, Q1.TOTALMSGS, Q1.TOTALACCP, Q1.TOTALREJ, Q2.TOTALEXT ");
		sb.append("ORDER BY SERVICE_IN ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("inServiceId");
		scalarList.add("totalNrOfMsgs");
		scalarList.add("nrMsgsAccepted");
		scalarList.add("nrMsgsRejected");
		scalarList.add("outServiceId");
		scalarList.add("extServiceId");
		scalarList.add("nrMsgsExtracted");
		scalarList.add("difference");

		countsReportInfoEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutBalanceCountReportEntityModel.class);

		if (countsReportInfoEntityList != null && countsReportInfoEntityList.size() > 0) 
		{
			for (OutBalanceCountReportEntityModel localModelEntity : countsReportInfoEntityList) 
			{
				log.debug("localModelEntity ==> "+ localModelEntity);
				OutBalanceCountReportModel localModel = new OutBalanceCountReportModel();
				localModel = new ServiceTranslator().translateOutBalanceCountReportEntityModelToCommonsModel(localModelEntity);
				countsReportInfoList.add(localModel);
			}
		}
		log.debug("The model list has this information #####################" + countsReportInfoList);
		return countsReportInfoList;
	}

	public List<?> retrieveDailyBillingForArchive() 
	{
		List<CasOpsDailyBillingEntity> dailyBillingList = new ArrayList<CasOpsDailyBillingEntity>();

		try 
		{
			dailyBillingList = genericDAO.findAllByNamedQuery("CasOpsDailyBillingEntity.findByBillExpStatus", "billExpStatus", "Y");
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on retrieveDailyBillingForArchive: " + e.getMessage());
			e.printStackTrace();
		}

		return dailyBillingList;
	}

	@Override
	public boolean updateDailyBillingInd(Object obj) 
	{
		try 
		{
			if (obj instanceof CasOpsDailyBillingEntity)
			{
				CasOpsDailyBillingEntity opsDailyBillingEntity = (CasOpsDailyBillingEntity) obj;
				log.debug("Entity in Save==> "+opsDailyBillingEntity);
				genericDAO.saveOrUpdate(opsDailyBillingEntity);

				return true;
			} 
			else 
			{
				log.info("Unable to convert type to MdtAcOpsDailyBillingEntity.");
				return false;
			}
		} 
		catch (Exception e) 
		{
			log.error("Error on updateDailyBillingInd: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object retrieveRejectReasonDataPerBankCount(String memberId,String memberNo) {

		List<MandateRejectionModel> mandateRejectCountModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateRejectCountEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateRejectCountModel = new MandateRejectionModel();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY");

		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addOneDay(currentDate);
			log.info("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");


		String dayOne = stringDate.substring(2,8);
		String firstDateOfMonth ="01"+dayOne;
		log.info("firsDateOfMonth**************"+ firstDateOfMonth);


		StringBuffer sb = new StringBuffer();

		/*select DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID,REJECT_REASON, count(*) as count
		from MDT_AC_ARC_MDTE_RESP
		where  DR_BANK_MEMBER_ID ='210044'and CR_BANK_MEMBER_ID= '210007' and  REJECT_REASON is not null
		GROUP BY REJECT_REASON,DR_BANK_MEMBER_ID, CR_BANK_MEMBER_ID*/

		sb.append("SELECT SUM(rejectReasonCodeCount) AS rejectReasonCodeCount FROM ");
		sb.append("(SELECT DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID, REJECT_REASON, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MDTE_RESP ");
		sb.append("WHERE DR_BANK_MEMBER_ID ='"+memberNo+"' AND CR_BANK_MEMBER_ID = '"+memberId+"' ");
		sb.append("AND REJECT_REASON is not null AND TRUNC(CREATE_DATE_TIME) BETWEEN TO_DATE('"+firstDateOfMonth+"','ddMMYYYY') AND TO_DATE('"+stringDate+"','ddMMYYYY') ");
		sb.append("GROUP BY REJECT_REASON,DR_BANK_MEMBER_ID,CR_BANK_MEMBER_ID) ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateRejectCountEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateRejectCountEntityList != null && mandateRejectCountEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel mandateRejectReasonCountEntityModel : mandateRejectCountEntityList)
			{
				mandateRejectCountModel = new MandateRejectionModel();
				mandateRejectCountModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(mandateRejectReasonCountEntityModel);
				mandateRejectCountModelList.add(mandateRejectCountModel);
			}
		}
		return mandateRejectCountModel;



	}

	@Override
	public Object retrieveReasonCodeDataPerBankCount(String memberId,String memberNo) {
		List<MandateRejectionModel> mandateReasonCodeCountModelList  = new ArrayList<MandateRejectionModel>();
		List<MandateRejectionEntityModel> mandateReasonCodeCountEntityList = new ArrayList<MandateRejectionEntityModel>();
		MandateRejectionModel mandateReasonCodeCountModel = new MandateRejectionModel();


		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addOneDay(currentDate);
			log.info("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");


		String dayOne = stringDate.substring(2,8);
		String firstDateOfMonth ="01"+dayOne;
		log.info("firsDateOfMonth**************"+ firstDateOfMonth);

		StringBuffer sb = new StringBuffer();


		/*SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS COUNT
		FROM MDT_AC_ARC_MNDT_MSG a
		LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID 
		LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID 
		WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04'AND  b.MEMBER_ID = '210003' AND  c.MEMBER_ID = '210044' AND  a.REJECT_REASON_CODE is not null AND a.SERVICE_ID ='RCAIN' 
		 GROUP BY  b.MEMBER_ID,  c.MEMBER_ID,a.REJECT_REASON_CODE*/

		sb.append("SELECT SUM(rejectReasonCodeCount) AS rejectReasonCodeCount FROM ");
		sb.append("(SELECT b.MEMBER_ID,c.MEMBER_ID, a.REJECT_REASON_CODE, COUNT(*) AS rejectReasonCodeCount ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID  ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_FIN_INST c ON a.MSG_ID = c.MSG_ID AND a.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID  ");
		sb.append("WHERE b.FIN_INST_TYPE_ID = 'FI03' AND  c.FIN_INST_TYPE_ID = 'FI04'AND  b.MEMBER_ID ='"+memberId+"'  AND  c.MEMBER_ID ='"+memberNo+"' ");
		sb.append("AND  a.REJECT_REASON_CODE is not null AND a.SERVICE_ID ='RCAIN'AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+firstDateOfMonth+"','ddMMYYYY') AND TO_DATE('"+stringDate+"','ddMMYYYY')  ");
		sb.append("GROUP BY b.MEMBER_ID,  c.MEMBER_ID,a.REJECT_REASON_CODE) ");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();

		scalarList.add("rejectReasonCodeCount");

		mandateReasonCodeCountEntityList = genericDAO.findBySql(sqlQuery, scalarList, MandateRejectionEntityModel.class);

		if(mandateReasonCodeCountEntityList != null && mandateReasonCodeCountEntityList.size()> 0)
		{
			for(MandateRejectionEntityModel reasonCountEntityModel : mandateReasonCodeCountEntityList)
			{
				mandateReasonCodeCountModel = new MandateRejectionModel();
				mandateReasonCodeCountModel = new ServiceTranslator().translateMandateRejectionEntityModelToCommonsModel(reasonCountEntityModel);
				mandateReasonCodeCountModelList.add(mandateReasonCodeCountModel);
			}
		}
		return mandateReasonCodeCountModel;
	}

	public Object retrieveDailyBillingByTxnId(String txnId, String service) 
	{
		CasOpsDailyBillingEntity dailyBill = new CasOpsDailyBillingEntity();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("txnId", txnId);
			parameters.put("subService", service);

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			dailyBill = (CasOpsDailyBillingEntity) genericDAO.findByCriteria(
					CasOpsDailyBillingEntity.class, parameters);
			log.debug("---------------mndtList after findAllByCriteria: ------------------"+ dailyBill);
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on retrieveDailyBillingByTxnId: " + e.getMessage());
			e.printStackTrace();
		}

		return dailyBill;
	}

	@Override
	public Object retrieverRespOutstandForOneDay(String serviceId) {
		List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList = new ArrayList<OutstandingRespSummaryCountModel>();
		List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList = new ArrayList<OutstandingRespSummaryCountEntityModel>();
		OutstandingRespSummaryCountModel outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();

		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addOneDay(currentDate);
			log.debug("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		log.debug("stringDate*******:"+stringDate);


		StringBuffer sb = new StringBuffer();


		sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
		sb.append("(SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS nrOfDays ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS ='9' AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND TO_CHAR(a.CREATED_DATE,'ddMMYYYY')= '"+stringDate+"' ");
		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME) ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrOfDays");


		outstandingRespSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

		if(outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size()> 0)
		{
			for(OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel : outstandingRespSummaryEntityList)
			{
				outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
				outstandingRespSummaryCountModel = new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(outstandingRespSummaryCountEntityModel);
				outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
			}
		}

		return outstandingRespSummaryCountModel;


	}

	@Override
	public Object retrieverRespOutstandForTwoDays(String serviceId) {

		List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList = new ArrayList<OutstandingRespSummaryCountModel>();
		List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList = new ArrayList<OutstandingRespSummaryCountEntityModel>();
		OutstandingRespSummaryCountModel outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		Date currentDate = new Date();
		Date  date = null;

		try {

			date= DateUtil.addNoOfDays(currentDate);
			log.debug("finalCurrentDate**************"+ currentDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String stringDate = DateUtil.convertDateToString(date, "ddMMYYYY");

		log.debug("stringDate*******:"+stringDate);

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(nrOfTxns) AS nrOfTxns FROM ");
		sb.append("(SELECT SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank,c.MEMBER_NAME  as debtorName,SUBSTR(a.MSG_ID,13,6) AS creditorBank,b.MEMBER_NAME AS creditorName,a.SERVICE_ID as serviceId, COUNT(*) AS nrOfTxns ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		sb.append("INNER JOIN CAMOWNER.SYS_CIS_BANK c ON SUBSTR(a.EXTRACT_MSG_ID,13,6) = c.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_STATUS ='9' AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND TO_CHAR(a.CREATED_DATE,'ddMMYYYY') ='"+stringDate+"' ");
		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,SUBSTR(a.EXTRACT_MSG_ID,13,6),b.MEMBER_NAME ,a.SERVICE_ID,c.MEMBER_NAME) ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrOfTxns");

		outstandingRespSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

		if(outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size()> 0)
		{
			for(OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel : outstandingRespSummaryEntityList)
			{
				outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
				outstandingRespSummaryCountModel = new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(outstandingRespSummaryCountEntityModel);
				outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
			}
		}
		return outstandingRespSummaryCountModel;
	}

	@Override
	public Object retrieverRespOutstandMANINTotal() {
		List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList = new ArrayList<OutstandingRespSummaryCountModel>();
		List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList = new ArrayList<OutstandingRespSummaryCountEntityModel>();
		OutstandingRespSummaryCountModel outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();

		StringBuffer sb = new StringBuffer();


		/*		sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
    	sb.append("(Select aa.member_name as debtorName,a.INSTRUCTEDAGENTAMS as debtorBank ,bb.member_name as creditorName ,a.INSTRUCTINGAGENTAMS as creditorBank,COUNT(*)  as nrOfDays ,a.SERVICEIDAMS as ServiceId ");
		sb.append("from manowner.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb ");
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("where EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',MANDATEBLOCKAMS),'</A>')), '/A/Tp/LclInstrm/Prtry') = '0227' and REASONCODEAMS = '900000' and msgtypeams = 'pain.009' and RESULTCODE = '0' and serviceidams = 'STMAN' ");
		sb.append("and TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("group by aa.member_name, bb.member_name, a.INSTRUCTEDAGENTAMS, a.INSTRUCTINGAGENTAMS, a.SERVICEIDAMS ");
		sb.append("minus ");
		sb.append("SELECT aa.member_name as debtorName ,a.INSTRUCTEDAGENTAMS as debtorBank,bb.member_name as creditorName,a.INSTRUCTINGAGENTAMS as creditorBank,count(*) as nrOfDays,b.SERVICEIDAMS as ServiceId ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb " );
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("INNER JOIN CAMOWNER.JNL_ACQ b ");
		sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS "); 
		sb.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0 AND b.MTI = 5501 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' and ");
		sb.append("TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("group by aa.member_name, bb.member_name, a.INSTRUCTEDAGENTAMS, a.INSTRUCTINGAGENTAMS, b.SERVICEIDAMS)") ;
		 */

		/*	sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
		sb.append("(WITH TEMPTBLA AS ");
		sb.append("(SELECT DISTINCT a.INSTRUCTINGAGENTAMS  AS  debtorBank, a.INSTRUCTINGAGENTAMS AS creditorBank,'TT1 Delayed Response' AS TransactionType, ");
		sb.append("CASE WHEN TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 THEN COUNT(*) else to_number('0',9) END AS nrOfDays, ");
		sb.append("a.SERVICEIDAMS AS service_Id,COUNT(*) AS volume,NVL(b.MSGTYPEAMS,'NOT') AS mes ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
		sb.append("ON a.TRANSACTIONIDENTIFIERAMS =b.TRANSACTIONIDENTIFIERAMS AND b.MSGTYPEAMS = 'pain.012' AND a.MSGTYPEAMS ='pain.009' ");
		sb.append("WHERE a.MSGTYPEAMS ='pain.009' AND a.SERVICEIDAMS <> 'MANIR' AND a.RESULTCODE ='0' ");
		sb.append("GROUP BY a.INSTRUCTEDAGENTAMS,a.INSTRUCTINGAGENTAMS,a.SERVICEIDAMS,b.MSGTYPEAMS,TO_DATE(SUBSTR(a.TRANSDATETIME,1,8),'YYYYMMDD')) ");
		sb.append("SELECT debtorBank,creditorBank,nrOfDays ");
		sb.append("FROM TEMPTBLA WHERE mes ='NOT') ");*/

		sb.append("SELECT SUM(nrOfTxns) AS nrOfDays FROM ");
		sb.append("(SELECT DISTINCT a.INSTRUCTEDAGENTAMS AS debtorBank,a.INSTRUCTINGAGENTAMS AS creditorBank,a.MSGTYPEAMS  AS msgType,a.PAYMENTSTATUSGROUPCODEAMS AS statusCode,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD')  AS processDate, ");
		sb.append("nvl(b.TRANSACTIONIDENTIFIERAMS,'not found') AS transactionId,COUNT(*) AS nrOfTxns  ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
		sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sb.append("AND a.MSGTYPEAMS = 'pain.009' AND b.msgtypeams = 'pain.012' ");
		sb.append("WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' AND a.msgtypeams = 'pain.009' AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found' ");
		sb.append("GROUP BY a.INSTRUCTEDAGENTAMS,a.INSTRUCTINGAGENTAMS,a.MSGTYPEAMS,a.PAYMENTSTATUSGROUPCODEAMS,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD'), b.TRANSACTIONIDENTIFIERAMS) ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrOfDays");



		outstandingRespSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

		if(outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size()> 0)
		{
			for(OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel : outstandingRespSummaryEntityList)
			{
				outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
				outstandingRespSummaryCountModel = new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(outstandingRespSummaryCountEntityModel);
				outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
			}
		}
		return outstandingRespSummaryCountModel;


	}

	@Override
	public Object retrieverRespOutstandMANAMTotal() {

		List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList = new ArrayList<OutstandingRespSummaryCountModel>();
		List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList = new ArrayList<OutstandingRespSummaryCountEntityModel>();
		OutstandingRespSummaryCountModel outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();

		StringBuffer sb = new StringBuffer();		
		/*		sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
		sb.append("(Select aa.member_name as debtorName ,a.INSTRUCTEDAGENTAMS as debtorBank,bb.member_name as creditorName,a.INSTRUCTINGAGENTAMS as creditorBank,count(*) as nrOfDays,a.SERVICEIDAMS as ServiceId ");
		sb.append("from manowner.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb ");
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("where SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) = '0227' ");
		sb.append("and REASONCODEAMS = '900000' and msgtypeams = 'pain.010' and RESULTCODE = '0' and serviceidams = 'STMAN' ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("group by aa.member_name, bb.member_name, a.INSTRUCTEDAGENTAMS, a.INSTRUCTINGAGENTAMS, a.SERVICEIDAMS ");
		sb.append("minus ");
		sb.append("SELECT aa.member_name as debtorName ,b.INSTRUCTEDAGENTAMS as debtorBank,bb.member_name as creditorName,b.INSTRUCTINGAGENTAMS as creditorBank,count(*) as nrOfDays,b.SERVICEIDAMS as ServiceId ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb ");
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("INNER JOIN CAMOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sb.append("WHERE a.SERVICEIDAMS = 'ST012' AND a.MTI = 5506 AND a.RESULTCODE = 0  ");
		sb.append("AND b.MTI = 5503 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("group by aa.member_name, bb.member_name, b.INSTRUCTEDAGENTAMS, b.INSTRUCTINGAGENTAMS, b.SERVICEIDAMS) ");
		 */

		sb.append("SELECT SUM(nrOfTxns) AS nrOfDays FROM ");
		sb.append("(SELECT DISTINCT a.INSTRUCTEDAGENTAMS AS debtorBank,a.INSTRUCTINGAGENTAMS AS creditorBank,a.MSGTYPEAMS  AS msgType,a.PAYMENTSTATUSGROUPCODEAMS AS statusCode,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD')  AS processDate, ");
		sb.append("nvl(b.TRANSACTIONIDENTIFIERAMS,'not found') AS transactionId,COUNT(*) AS nrOfTxns  ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
		sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sb.append("AND a.MSGTYPEAMS = 'pain.010' AND b.msgtypeams = 'pain.012' ");
		sb.append("WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' AND a.msgtypeams = 'pain.010' AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found' ");
		sb.append("GROUP BY a.INSTRUCTEDAGENTAMS,a.INSTRUCTINGAGENTAMS,a.MSGTYPEAMS,a.PAYMENTSTATUSGROUPCODEAMS,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD'), b.TRANSACTIONIDENTIFIERAMS) ");



		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrOfDays");



		outstandingRespSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

		if(outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size()> 0)
		{
			for(OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel : outstandingRespSummaryEntityList)
			{
				outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
				outstandingRespSummaryCountModel = new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(outstandingRespSummaryCountEntityModel);
				outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
			}
		}
		return outstandingRespSummaryCountModel;
	}

	@Override
	public Object retrieverRespOutstandMANCNTotal() {
		List<OutstandingRespSummaryCountModel> outstandingRespSummaryCountModelList = new ArrayList<OutstandingRespSummaryCountModel>();
		List<OutstandingRespSummaryCountEntityModel> outstandingRespSummaryEntityList = new ArrayList<OutstandingRespSummaryCountEntityModel>();
		OutstandingRespSummaryCountModel outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();

		StringBuffer sb = new StringBuffer();

		/*		sb.append("SELECT SUM(nrOfDays) AS nrOfDays FROM ");
		sb.append("(Select debtorBank,debtorName,creditorBank,creditorName,nrOfDays,ServiceId ");
		sb.append("from ");
		sb.append("(Select aa.member_name as debtorName ,a.INSTRUCTEDAGENTAMS as debtorBank,bb.member_name as creditorName,a.INSTRUCTINGAGENTAMS as creditorBank, ");
		sb.append("a.SERVICEIDAMS as ServiceId,count(*) as nrOfDays ");
		sb.append("from manowner.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb ");
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("where msgtypeams = 'pain.011' and RESULTCODE = '0' and serviceidams IN('MANIR', 'STMDF') ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1  ");
		sb.append("group by aa.member_name, bb.member_name, a.INSTRUCTEDAGENTAMS, a.INSTRUCTINGAGENTAMS, a.SERVICEIDAMS ");
		sb.append("minus ");
		sb.append("SELECT aa.member_name as debtorName ,b.INSTRUCTEDAGENTAMS as debtorBank,bb.member_name as creditorName,b.INSTRUCTINGAGENTAMS as creditorBank, ");
		sb.append("a.SERVICEIDAMS as ServiceId ,count(*) as nrOfDays ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("left join manowner.SYS_CIS_BANK aa ");
		sb.append("on a.INSTRUCTEDAGENTAMS = aa.member_no ");
		sb.append("left join manowner.sys_cis_bank bb ");
		sb.append("on a.INSTRUCTINGAGENTAMS = bb.member_no ");
		sb.append("INNER JOIN CAMOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sb.append("WHERE a.SERVICEIDAMS = 'MANIR' AND a.MTI = 5506 AND a.RESULTCODE = 0 ");
		sb.append("AND b.MTI = 5505 AND b.RESULTCODE = 0 AND a.REASONCODEAMS = '900000' AND b.REASONCODEAMS = '900000' ");
		sb.append("AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8), 'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("group by aa.member_name, bb.member_name, b.INSTRUCTEDAGENTAMS, b.INSTRUCTINGAGENTAMS, a.SERVICEIDAMS)) ");*/


		sb.append("SELECT SUM(nrOfTxns) AS nrOfDays FROM ");
		sb.append("(SELECT DISTINCT a.INSTRUCTEDAGENTAMS AS debtorBank,a.INSTRUCTINGAGENTAMS AS creditorBank,a.MSGTYPEAMS  AS msgType,a.PAYMENTSTATUSGROUPCODEAMS AS statusCode,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD')  AS processDate, ");
		sb.append("nvl(b.TRANSACTIONIDENTIFIERAMS,'not found') AS transactionId,COUNT(*) AS nrOfTxns  ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.JNL_ACQ b ");
		sb.append("ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
		sb.append("AND a.MSGTYPEAMS = 'pain.011' AND b.msgtypeams = 'pain.012' ");
		sb.append("WHERE a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' AND a.msgtypeams = 'pain.011' AND TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD') = TRUNC(current_date) -1 ");
		sb.append("AND NVL(b.TRANSACTIONIDENTIFIERAMS,'not found') = 'not found' ");
		sb.append("GROUP BY a.INSTRUCTEDAGENTAMS,a.INSTRUCTINGAGENTAMS,a.MSGTYPEAMS,a.PAYMENTSTATUSGROUPCODEAMS,TO_DATE(SUBSTR(a.TRANSDATETIME, 1, 8),'YYYYMMDD'), b.TRANSACTIONIDENTIFIERAMS) ");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();
		scalarList.add("nrOfDays");



		outstandingRespSummaryEntityList = genericDAO.findBySql(sqlQuery, scalarList, OutstandingRespSummaryCountEntityModel.class);

		if(outstandingRespSummaryEntityList != null && outstandingRespSummaryEntityList.size()> 0)
		{
			for(OutstandingRespSummaryCountEntityModel outstandingRespSummaryCountEntityModel : outstandingRespSummaryEntityList)
			{
				outstandingRespSummaryCountModel = new OutstandingRespSummaryCountModel();
				outstandingRespSummaryCountModel = new ServiceTranslator().translateOutstandingRespSummaryCountEntityModelToCommonsModel(outstandingRespSummaryCountEntityModel);
				outstandingRespSummaryCountModelList.add(outstandingRespSummaryCountModel);
			}
		}
		return outstandingRespSummaryCountModel;

	}

	@Override
	public boolean checkIfAllFilesLoaded(Date processDate, String service) {
		boolean fileStatus = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strSysDate = sdf.format(processDate);
		log.debug("strSysDate ====> "+strSysDate);

		//1.Check Pain Msgs
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processDate", strSysDate);
			parameters.put("status1", receviedFileStatus);
			parameters.put("status2", validatingFileStatus);
			parameters.put("status3", fileWaitingStatus);
			parameters.put("serviceName", service);
//			parameters.put("memberId", memberId);

			//			log.info("parameters =====> "+parameters);
			//			List<MdtAcOpsMndtMsgEntity> painMsgsCheckList = (List<MdtAcOpsMndtMsgEntity>) genericDAO.findByCriteriaUsingTrunc(MdtAcOpsMndtMsgEntity.class, parameters, "CREATED_DATE", "YYYY-MM-DD", strSysDate);
			List<CasOpsFileRegEntity> fileStatusCheckList = genericDAO.findAllByNQCriteria("CasOpsFileRegEntity.findByProcessDateTruncService", parameters);

			log.debug("fileStatusCheckList =====> "+fileStatusCheckList);
			log.debug("fileStatusCheckList.size(); =====> "+fileStatusCheckList.size());

			if(fileStatusCheckList != null && fileStatusCheckList.size() > 0)
				fileStatus = false;
			else
				fileStatus = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on checkIfAllFilesLoaded.painMsgsCheckList: "+ e.getMessage());
			e.printStackTrace();
		}
		return fileStatus;

	}

	public List<?> retrievePasaHIRMandateInfo(String fromDate, String toDate, String service, boolean expiredTxns)
	{
		log.debug("fromDate ==> "+fromDate);
		log.debug("toDate ==> "+toDate);
		List<PasaMandateReportEntityModel> pasaInitInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		if(expiredTxns)
		{
			sb.append("SELECT a.MANDATE_REQ_TRAN_ID AS mrti, a.MSG_ID AS msgId, SUBSTR(a.MSG_ID,13,6) AS creditorBank, SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank, TO_CHAR(TRUNC(a.ARCHIVE_DATE), 'YYYY-MM-DD') AS creationDate, ");
		}
		else
		{
			sb.append("SELECT a.MANDATE_REQ_TRAN_ID AS mrti, a.MSG_ID AS msgId, SUBSTR(a.MSG_ID,13,6) AS creditorBank, SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank, TO_CHAR(TRUNC(a.CREATED_DATE), 'YYYY-MM-DD') AS creationDate, ");
		}

		sb.append("c.AUTH_TYPE AS authType, a.LOCAL_INSTR_CD AS dbtrAuthReq, a.SEQUENCE_TYPE AS instOcc, b.ID as cdtrAbbShtNm, a.PROCESS_STATUS as processStatus,'ACH' AS dataSource ");

		if(service.equalsIgnoreCase("CARIN"))
		{
			sb.append(",a.AMEND_REASON_CODE as reason, e.MANDATE_REQ_ID AS contRefNum ");
		}
		else
		{
			sb.append(",a.MANDATE_REQ_ID AS contRefNum ");
		}

		if(service.equalsIgnoreCase("MANCN"))
		{
			sb.append(", a.REJECT_REASON_CODE as reason ");
		}

		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
		//		sb.append("JOIN CAMOWNER.MDT_AC_ARC_GRP_HDR d on a.MSG_ID = d.MSG_ID ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_PARTY_IDENT b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_SUPPL_DATA c ON a.MSG_ID = c.MSG_ID AND a.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID ");

		if(service.equalsIgnoreCase("CARIN"))
		{
			sb.append("LEFT JOIN CAMOWNER.MDT_AC_ARC_ORGNL_MNDT e ON a.MSG_ID = e.MSG_ID AND a.MANDATE_REQ_TRAN_ID = e.MANDATE_REQ_TRAN_ID "); 
		}

		if(expiredTxns)
		{
			sb.append("WHERE a.SERVICE_ID = '"+service+"' AND a.PROCESS_STATUS = '8' AND TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') and b.PARTY_IDENT_TYPE_ID = 'PI03' ");
		}
		else
		{
			sb.append("WHERE a.SERVICE_ID = '"+service+"' AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') and b.PARTY_IDENT_TYPE_ID = 'PI03' ");	
		}

		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");

		String sqlQuery = sb.toString();
		log.info(service+ " Mandates sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("msgId");
		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("processStatus");
		scalarList.add("dataSource");
		if(service.equalsIgnoreCase("CARIN") || service.equalsIgnoreCase("MANCN"))
			scalarList.add("reason");	
		log.debug("scalarList: " + scalarList);

		pasaInitInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaInitInfoList;
	}

	public List<?> retrieveNotRespondedMandates(String fromDate, String toDate, String service)
	{
		List<PasaMandateReportEntityModel> pasaInitInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT a.MANDATE_REQ_TRAN_ID AS mrti, a.MSG_ID AS msgId, SUBSTR(a.MSG_ID,13,6) AS creditorBank, SUBSTR(a.EXTRACT_MSG_ID,13,6) AS debtorBank, TO_CHAR(a.CREATED_DATE, 'YYYY-MM-DD') AS creationDate, ");
		sb.append("c.AUTH_TYPE AS authType, a.MANDATE_REQ_ID AS contRefNum,a.LOCAL_INSTR_CD AS dbtrAuthReq, a.SEQUENCE_TYPE AS instOcc, b.ID as cdtrAbbShtNm, a.PROCESS_STATUS as processStatus,'ACH' AS dataSource  ");
		if(service.equalsIgnoreCase("CARIN"))
		{
			sb.append(", a.AMEND_REASON_CODE as reason ");
		}

		if(service.equalsIgnoreCase("MANCN"))
		{
			sb.append(", a.REJECT_REASON_CODE as reason ");
		}

		sb.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_OPS_PARTY_IDENT b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
		sb.append("LEFT JOIN CAMOWNER.MDT_AC_OPS_SUPPL_DATA c ON a.MSG_ID = c.MSG_ID AND a.MANDATE_REQ_TRAN_ID = c.MANDATE_REQ_TRAN_ID ");
		sb.append("WHERE a.PROCESS_STATUS IN ('4','9')  AND a.SERVICE_ID = '"+service+"' AND TRUNC(a.CREATED_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') and b.PARTY_IDENT_TYPE_ID = 'PI03' ");
		sb.append("ORDER BY creationDate, mrti, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("msgId");
		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("processStatus");
		scalarList.add("dataSource");
		if(service.equalsIgnoreCase("CARIN") || service.equalsIgnoreCase("MANCN"))
			scalarList.add("reason");	

		log.debug("scalarList: " + scalarList);

		pasaInitInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaInitInfoList;
	}

	public List<?> retrieveAchValidationErrors(String fromDate, String toDate, String serviceNmId)
	{
		List<PasaMandateReportEntityModel> pasaInitInfoList = new ArrayList<PasaMandateReportEntityModel>();



		StringBuffer sb = new StringBuffer();

		if(serviceNmId.equalsIgnoreCase("camt.055"))
		{
			sb.append("SELECT a.TXN_ID AS mrti, b.INSTG_AGT as debtorBank, NULL as creditorBank, a.DEBTOR_BRANCH_NO as debtorBranch, TO_CHAR(a.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,'BATCH' as authType, NULL as contRefNum, NULL as dbtrAuthReq, NULL as instOcc, ");
		}
		else
		{
			sb.append("SELECT a.TXN_ID AS mrti, b.INSTG_AGT as creditorBank, a.DEBTOR_BRANCH_NO as debtorBranch, TO_CHAR(a.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,'BATCH' as authType, NULL as contRefNum, NULL as dbtrAuthReq, NULL as instOcc, ");
		}

		sb.append("a.CR_ABB_SHORT_NAME as cdtrAbbShtNm, 'VALIDATION_FAILURE' AS status, a.ERROR_CODE AS errorCode, 'ACH' AS dataSource ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_STATUS_DETAILS a ");
		sb.append("JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS b ON a.STATUS_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
		sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') AND ");
		if(serviceNmId.equalsIgnoreCase("camt.055"))
		{
			sb.append("b.SERVICE = 'ST007' AND a.ERROR_TYPE = 'TXN' AND a.TXN_STATUS = 'RJCT' ");
		}
		else
		{
			sb.append("b.ORGNL_MSG_NAME = '"+serviceNmId+"' AND a.ERROR_TYPE = 'TXN' AND a.TXN_STATUS = 'RJCT' ");
		}

		sb.append("ORDER BY creationDate, mrti, creditorBank, debtorBranch, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm, errorCode ");

		String sqlQuery = sb.toString();
		log.debug(serviceNmId+" ACH Errors sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("creditorBank");
		scalarList.add("debtorBranch");
		scalarList.add("creationDate");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("status");
		scalarList.add("errorCode");
		scalarList.add("dataSource");
		if(serviceNmId.equalsIgnoreCase("camt.055"))
			scalarList.add("debtorBank");
		log.debug("scalarList: " + scalarList);

		pasaInitInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaInitInfoList;
	}

	public List<?> retrieveDebtorValidationErrors(String fromDate, String toDate,String service, String serviceNmId)
	{
		List<PasaMandateReportEntityModel> pasaInitInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		log.debug("service ===> "+service);
		log.debug("serviceNmId ===> "+serviceNmId);

		//		sb.append("SELECT a.INST_ID as creditorBank, b.INSTG_AGT as debtorBank, TO_CHAR(b.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,'BATCH' as authType, 'N/A' as contRefNum, 'N/A' as dbtrAuthReq, 'N/A' as instOcc, "); 
		//		sb.append("'N/A' as cdtrAbbShtNm, 'VALIDATION FAILURE' AS status, a.ERROR_CODE AS errorCode, 'ACH' AS dataSource ");  
		//		sb.append("FROM CAMOWNER.MDT_AC_ARC_CONF_DETAILS a "); 
		//		sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS b ON a.CONF_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
		//		sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') AND a.ORGNL_MSG_TYPE = '"+serviceNmId+"' AND a.ERROR_TYPE = 'TXN'AND a.TXN_STATUS = 'RJCT' ");
		//		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm,errorCode ");

		if(service.equalsIgnoreCase("CARIN"))
		{
			sb.append("SELECT a.TXN_ID AS mrti, a.INST_ID as creditorBank, b.INSTG_AGT as debtorBank, TO_CHAR(a.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,e.AUTH_TYPE AS authType, ");
			sb.append("'VALIDATION_FAILURE' AS status, a.ERROR_CODE AS errorCode, 'ACH' AS dataSource,f.MANDATE_REQ_ID AS contRefNum,c.LOCAL_INSTR_CD AS dbtrAuthReq, c.SEQUENCE_TYPE AS instOcc, d.ID as cdtrAbbShtNm ");
			sb.append("FROM CAMOWNER.MDT_AC_ARC_CONF_DETAILS a ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS b ON a.CONF_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_MNDT_MSG c ON c.MANDATE_REQ_TRAN_ID = a.TXN_ID ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_PARTY_IDENT d ON c.MSG_ID = d.MSG_ID AND c.MANDATE_REQ_TRAN_ID = d.MANDATE_REQ_TRAN_ID ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_SUPPL_DATA e ON c.MSG_ID = e.MSG_ID AND c.MANDATE_REQ_TRAN_ID = e.MANDATE_REQ_TRAN_ID ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_ORGNL_MNDT f ON c.MSG_ID = f.MSG_ID AND c.MANDATE_REQ_TRAN_ID = f.MANDATE_REQ_TRAN_ID ");  
			sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') AND a.ORGNL_MSG_TYPE = '"+serviceNmId+"' AND a.ERROR_TYPE = 'TXN'AND a.TXN_STATUS = 'RJCT' ");
			sb.append("and c.SERVICE_ID = '"+service+"' and d.PARTY_IDENT_TYPE_ID = 'PI03' ");
			sb.append("ORDER BY creationDate, mrti, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm,errorCode ");
		}
		else
		{
			sb.append("SELECT a.TXN_ID AS mrti, a.INST_ID as creditorBank, b.INSTG_AGT as debtorBank, TO_CHAR(a.ARCHIVE_DATE, 'YYYY-MM-DD') AS creationDate,e.AUTH_TYPE AS authType, ");
			sb.append("'VALIDATION_FAILURE' AS status, a.ERROR_CODE AS errorCode, 'ACH' AS dataSource,c.MANDATE_REQ_ID AS contRefNum,c.LOCAL_INSTR_CD AS dbtrAuthReq, c.SEQUENCE_TYPE AS instOcc, d.ID as cdtrAbbShtNm ");
			sb.append("FROM CAMOWNER.MDT_AC_ARC_CONF_DETAILS a ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS b ON a.CONF_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_MNDT_MSG c ON c.MANDATE_REQ_TRAN_ID = a.TXN_ID ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_PARTY_IDENT d ON c.MSG_ID = d.MSG_ID AND c.MANDATE_REQ_TRAN_ID = d.MANDATE_REQ_TRAN_ID ");
			sb.append("JOIN CAMOWNER.MDT_AC_ARC_SUPPL_DATA e ON c.MSG_ID = e.MSG_ID AND c.MANDATE_REQ_TRAN_ID = e.MANDATE_REQ_TRAN_ID ");
			sb.append("WHERE TRUNC(a.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') AND a.ORGNL_MSG_TYPE = '"+serviceNmId+"' AND a.ERROR_TYPE = 'TXN'AND a.TXN_STATUS = 'RJCT' ");
			sb.append("and c.SERVICE_ID = '"+service+"' and d.PARTY_IDENT_TYPE_ID = 'PI03' ");
			sb.append("ORDER BY creationDate, mrti, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm,errorCode ");
		}


		String sqlQuery = sb.toString();
		log.info(service+" Debtor Validations sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("status");
		scalarList.add("errorCode");
		scalarList.add("dataSource");
		log.debug("scalarList: " + scalarList);

		pasaInitInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);
		log.debug("pasaInitInfoList ==> "+pasaInitInfoList);

		return pasaInitInfoList;
	}

	public List<?> retrieveAuthStatus(String mrti,String service)
	{
		List<PasaMandateReportEntityModel> matchedManacList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		if(service.equalsIgnoreCase("CARIN"))
		{
			sb.append("SELECT a.REJECT_REASON_CODE as reason,a.LOCAL_INSTR_CD as authType,b.CODE as authStatus, TO_CHAR(a.CREATED_DATE, 'YYYY-MM-DD') as creationDate "); 
		}
		else 
		{
			sb.append("SELECT a.REJECT_REASON_CODE as reason, b.CODE as authStatus, TO_CHAR(a.CREATED_DATE, 'YYYY-MM-DD') as creationDate "); 
		}
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG a ");
		sb.append("JOIN CAMOWNER.MDT_AC_ARC_REF_DOC b ON a.MSG_ID = b.MSG_ID AND a.MANDATE_REQ_TRAN_ID = b.MANDATE_REQ_TRAN_ID ");
		sb.append("WHERE a.MANDATE_REQ_TRAN_ID = '"+mrti+"' AND a.SERVICE_ID = 'RCAIN' ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("reason");
		scalarList.add("authStatus");
		scalarList.add("creationDate");
		if(service.equalsIgnoreCase("CARIN"))
			scalarList.add("authType");


		log.debug("scalarList: " + scalarList);

		matchedManacList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return matchedManacList;
	}	

	public List<?> retrievePHIRMndtSuspInfo(String fromDate, String toDate, boolean expiredTxns)
	{
		List<PasaMandateReportEntityModel> matchedManacList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		if(expiredTxns)
		{
			sb.append("SELECT b.MANDATE_SUSP_ID as mrti, a.ASSIGNER as debtorBank, b.CREDITOR_BANK as creditorBank, TO_CHAR(TRUNC(b.ARCHIVE_DATE), 'YYYY-MM-DD') AS creationDate, b.REASON_CODE as reason, 'ACH' AS dataSource, b.PROCESS_STATUS AS processStatus ");
		}
		else
		{
			sb.append("SELECT b.MANDATE_SUSP_ID as mrti, a.ASSIGNER as debtorBank, b.CREDITOR_BANK as creditorBank, TO_CHAR(TRUNC(b.CREATED_DATE), 'YYYY-MM-DD') AS creationDate, b.REASON_CODE as reason, 'ACH' AS dataSource, b.PROCESS_STATUS AS processStatus ");
		}

		sb.append("FROM CAMOWNER.MDT_AC_ARC_SUSP_GRP_HDR a ");
		sb.append("JOIN CAMOWNER.MDT_AC_ARC_SUSP_MSG b ON a.ASSIGNMENT_ID = b.ASSIGNMENT_ID ");

		if(expiredTxns)
		{
			sb.append("WHERE b.PROCESS_STATUS = '8' AND TRUNC(b.ARCHIVE_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') ");
		}
		else
		{
			//SalehaR - 2019-02-20 - No longer require information from CONF tables
			//			sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_DETAILS c ON b.MANDATE_SUSP_ID = c.TXN_ID ");
			//			sb.append("JOIN CAMOWNER.MDT_AC_ARC_CONF_HDRS d ON c.CONF_HDR_SEQ_NO = d.SYSTEM_SEQ_NO ");
			sb.append("WHERE TRUNC(b.CREATED_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') ");
		}
		sb.append("ORDER BY creationDate, debtorBank, creditorBank, reason ");

		String sqlQuery = sb.toString();
		log.info("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("debtorBank");
		scalarList.add("creditorBank");
		scalarList.add("creationDate");
		scalarList.add("reason");
		scalarList.add("dataSource");
		scalarList.add("processStatus");

		log.debug("scalarList: " + scalarList);

		matchedManacList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return matchedManacList;
	}	

	public List<?> retrieveSuspRejections(String txnId)
	{
		List<MdtAcArcConfDetailsEntity> confDtlsList = new ArrayList<MdtAcArcConfDetailsEntity>();

		try 
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("txnId", txnId);
			parameters.put("orgnlMsgType", "camt.055");
			parameters.put("txnStatus", "RJCT");

			log.debug("---------------sparameters: ------------------" + parameters.toString());
			confDtlsList =  (List<MdtAcArcConfDetailsEntity>) genericDAO.findAllByCriteria(MdtAcArcConfDetailsEntity.class,parameters);
		} catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} catch (Exception e) {
			log.error("Error on retrieveSuspRejections: " + e.getMessage());
			e.printStackTrace();
		}

		return confDtlsList;
	}	

	public List<?> retrieveNotRespondedMndtSusp(String fromDate, String toDate)
	{
		List<PasaMandateReportEntityModel> matchedManacList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT b.MANDATE_SUSP_ID as mrti, a.ASSIGNER as debtorBank, b.CREDITOR_BANK as creditorBank, TO_CHAR(TRUNC(a.CREATE_DATE_TIME), 'YYYY-MM-DD') AS creationDate, b.REASON_CODE as reason, 'ACH' AS dataSource, b.PROCESS_STATUS AS processStatus ");
		sb.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR a ");
		sb.append("JOIN CAMOWNER.MDT_AC_OPS_SUSP_MSG b ON a.ASSIGNMENT_ID = b.ASSIGNMENT_ID ");
		sb.append("WHERE TRUNC(b.CREATED_DATE) BETWEEN TO_DATE('"+fromDate+"', 'YYYY-MM-DD') AND TO_DATE('"+toDate+"', 'YYYY-MM-DD') AND PROCESS_STATUS = '4' ");
		sb.append("ORDER BY creationDate, debtorBank, creditorBank, reason ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("mrti");
		scalarList.add("debtorBank");
		scalarList.add("creditorBank");
		scalarList.add("creationDate");
		scalarList.add("reason");
		scalarList.add("dataSource");
		scalarList.add("processStatus");

		log.debug("scalarList: " + scalarList);

		matchedManacList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return matchedManacList;
	}	

	public List<?> retrieveRealTimePHIRInitInfo(String fromDate, String toDate, String service)
	{
		List<PasaMandateReportEntityModel> pasaRealTimeInitInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();
//		SalehaR-2019/11/03 Replace with optimised SQL
//		sb.append("WITH temptbla AS "); 
//		sb.append("(SELECT distinct  a.INSTRUCTINGAGENTAMS AS CREDITOR_BANK ,a.INSTRUCTEDAGENTAMS AS DEBTOR_BANK ,TO_CHAR(TO_DATE(SUBSTR(a.CREATIONDATETIMEAMS,1,10), 'YYYY-MM-DD'), 'YYYY-MM-DD')AS CREATION_DATE ");
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.SPLMTRYDATABLOCKREQAMS, chr(10)), chr(13)), chr(9)), ' '), instr(REPLACE(REPLACE(REPLACE(REPLACE(a.SPLMTRYDATABLOCKREQAMS, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'</AthntctnTp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>')) AS AthTp "); 
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<MndtReqId>') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'</MndtReqId>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'<MndtReqId>')) AS CntrcRefNum ");
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</Prtry></LclInstrm>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<LclInstrm><Prtry>')) AS LclInstrm "); 
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<SeqTp>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</SeqTp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<SeqTp>')) AS SqTp "); 
//		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.MANDATEBLOCKAMS),'</A>')), '/A/UltmtCdtr/Id/OrgId/Othr/Id') as XMLString ");
//		sb.append(",a.TRANSACTIONIDENTIFIERAMS AS TXNID ,a.MSGTYPEAMS AS msg ,a.TRANSMISSIONNUMBERAMS AS trsnnr, a.SERVICEIDAMS AS SID  ");
//		sb.append(",NVL(b.TRANSACTIONIDENTIFIERAMS, 'NotFound') AS RESPFILENOTREC ");
//		sb.append(",CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'AUTHENTICATED' ");  
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'AUTHENTICATED' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'NO_RESPONSE' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'NO_RESPONSE' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - instr(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING' ");
//		sb.append("WHEN a.RESULTCODE in ('2003', '8603') THEN 'RESPONSE_FILE_NOT_RECEIVED' ");
//		sb.append("WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN 'VALIDATION_FAILURE' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'STMAN' AND b.TRANSACTIONIDENTIFIERAMS = 'NotFound' THEN 'RESPONSE_FILE_NOT_RECEIVED' ");
//		sb.append("END AS STATUS ");
//		sb.append(",CASE WHEN b.MSGTYPEAMS = 'pain.012' AND b.ACCEPTEDINDICATORAMS = 'false' THEN b.REJECTEDREASONCODEAMS ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND a.ACCEPTEDINDICATORAMS = 'false' THEN a.REJECTEDREASONCODEAMS ");
//		sb.append("WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN a.REASONCODEAMS ");
//		sb.append("END AS ERROR_CODE ");
//		sb.append(",a.PAYMENTSTATUSGROUPCODEAMS AS sts ,TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYY/MM/DD') AS dte ");
//		sb.append("FROM CAMOWNER.JNL_ACQ a ");
//		sb.append("LEFT JOIN CAMOWNER.JNL_ACQ b ON a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
//		sb.append("AND a. MSGTYPEAMS = 'pain.009' AND b.MSGTYPEAMS = 'pain.012' AND  a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' ");
//		sb.append("WHERE a.MSGTYPEAMS = 'pain.009' AND TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') between '"+fromDate+"' and '"+toDate+"') ");
//
//		sb.append("SELECT TXNID AS mrti, DEBTOR_BANK AS debtorBank ,CREDITOR_BANK AS creditorBank ,CREATION_DATE AS creationDate ");
//		sb.append(",SUBSTR(AthTp,instr(AthTp,'<AthntctnTp>') + 12) AS authType ,SUBSTR(CntrcRefNum,instr(CntrcRefNum,'<MndtReqId>')+ 11) AS contRefNum ");
//		sb.append(",SUBSTR(LclInstrm,instr(LclInstrm,'<LclInstrm><Prtry>') + 18) AS dbtrAuthReq ,SUBSTR(SqTp,instr(SqTp,'<SeqTP>') + 8) AS instOcc ");
//		sb.append(",NVL(XMLString,' ') as  cdtrAbbShtNm ");
//		sb.append(",CASE WHEN SID = 'STMAN' AND RESPFILENOTREC = 'NotFound' THEN 'RESPONSE_FILE_NOT_RECEIVED' else STATUS END AS status "); 
//		sb.append(",ERROR_CODE as errorCode,'ACH' AS dataSource ");
//		sb.append("FROM temptbla ");
//		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");
		
		sb.append("with tmptbl as ");
		sb.append("(select INSTRUCTINGAGENTAMS AS CREDITORBANK ");
		sb.append(",INSTRUCTEDAGENTAMS as DEBTORBANK ");
		sb.append(",to_char(to_date(substr(Transdatetime,1,8),'YYYY-MM-DD'), 'YYYY-MM-DD') as SYSTEM_DATE ");
		sb.append(",splmtrydatablockreqams "); 
		sb.append(",MANDATEBLOCKAMS ");
		sb.append(",trim(UNDRLYGACCPTNCDTLSBLOCKAMS) as undaccptdtls ");
		sb.append(",transactionidentifierams as TXNID ");
		sb.append(",msgtypeams as msgtpa ");
		sb.append(",transmissionnumberams as trsnnr ");
		sb.append(",serviceidams as SID ");
		sb.append(",paymentstatusgroupcodeams as pmtstatusa ");
		sb.append(",resultcode as rsltcd ");
		sb.append(",acceptedindicatorams as accptinda ");
		sb.append(",REJECTEDREASONCODEAMS as rjctrsncda ");
		sb.append(",reasoncodeams as rsncda ");       
		sb.append("from manowner.jnl_acq "); 
		sb.append("WHERE MSGTYPEAMS = 'pain.009' and substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"') ");
		sb.append(",tmptbl2 as ");
		sb.append("(Select distinct transactionidentifierams as TXNID ");
		sb.append(",reasoncodeams as rsncdb ");
		sb.append(",REJECTEDREASONCODEAMS as rjctrsncdb ");
		sb.append(",acceptedindicatorams as accptindb ");
		sb.append(",paymentstatusgroupcodeams as pmtstatusb ");
		sb.append(",msgtypeams as msgtpb ");
		sb.append(",trim(UNDRLYGACCPTNCDTLSBLOCKAMS) as undaccptdtls ");
		sb.append(",substr(TRANSDATETIME,1,8) as TRANSDATETIME ");
		sb.append("from manowner.jnl_acq ");
		sb.append("WHERE MSGTYPEAMS = 'pain.012' and substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"' and paymentstatusgroupcodeams = 'ACCP' and trim(UNDRLYGACCPTNCDTLSBLOCKAMS) like '%<RqstTrnsnbr>1</RqstTrnsnbr>%') ");
        sb.append("select aa.TXNID as mrti ");
		sb.append(",aa.creditorbank as creditorBank ");
		sb.append(",aa.debtorbank as debtorBank ");
		sb.append(",aa.system_date as creationDate ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.splmtrydatablockreqams),'</A>')), '/A//AthntctnTp') as authType ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.MANDATEBLOCKAMS),'</A>')), '/A//MndtReqId') as contRefNum ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.MANDATEBLOCKAMS),'</A>')), '/A//LclInstrm/Prtry') as dbtrAuthReq ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.MANDATEBLOCKAMS),'</A>')), '/A//SeqTp') as instOcc ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.MANDATEBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') as cdtrAbbShtNm ");
		sb.append(",case when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' and bb.pmtstatusb = 'RJCT' then 'VALIDATION_FAILURE' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' and aa.pmtstatusa = 'ACCP' then 'AUTHENTICATED' ");
		sb.append("when aa.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' then 'AUTHENTICATED' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT' and bb.pmtstatusb = 'RJCT' then 'VALIDATION_FAILURE' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT' and aa.pmtstatusa = 'ACCP' then 'DECLINED' ");
		sb.append("when aa.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT' then 'DECLINED' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP' and bb.pmtstatusb = 'RJCT' then 'VALIDATION_FAILURE' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP' and aa.pmtstatusa = 'ACCP' then 'NO_RESPONSE' ");
		sb.append("when aa.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',aa.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP' then 'NO_RESPONSE' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',bb.undaccptdtls),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' and bb.pmtstatusb = 'PEND' then 'PENDING' ");
		sb.append("when (aa.rsltcd in ('2003', '8603')) or (aa.SID = 'STMAN' and NVL(bb.TXNID, 'NF') = 'NF') then 'RESPONSE_FILE_NOT_RECEIVED' ");
		sb.append("when aa.pmtstatusa = 'RJCT' then 'VALIDATION_FAILURE' end as status ");
		sb.append(",case when bb.msgtpb = 'pain.012' and bb.accptindb = 'false' then bb.rjctrsncdb ");
		sb.append("when aa.SID = 'MANIR' and aa.accptinda = 'false' then aa.rjctrsncda ");
		sb.append("when aa.pmtstatusa = 'RJCT' then aa.rsncda ");
		sb.append("when bb.pmtstatusb = 'RJCT' then bb.rsncdb end as errorCode ,'ACH' as dataSource ");
		sb.append("from tmptbl aa left outer join tmptbl2 bb on aa.txnid = bb.txnid ");
		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");
		
		String sqlQuery = sb.toString();
		log.debug("PHIR01 REALTIME sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		

		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("status");
		scalarList.add("errorCode");
		scalarList.add("dataSource");
		scalarList.add("mrti");
		log.debug("scalarList: " + scalarList);

		pasaRealTimeInitInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaRealTimeInitInfoList;
	}


	public List<?> retrieveRealTimePHIRAmendmentInfo(String fromDate, String toDate)
	{
		List<PasaMandateReportEntityModel> pasaRealTimeAmendInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		//2019-11-04 SalehaR - Optimised SQL Query
//		sb.append("WITH temptbla AS");
//		sb.append(" (SELECT distinct a.INSTRUCTINGAGENTAMS AS CREDITOR_BANK ,a.INSTRUCTEDAGENTAMS AS DEBTOR_BANK ,TO_CHAR(TO_DATE(SUBSTR(a.CREATIONDATETIMEAMS,1,10), 'YYYY-MM-DD'), 'YYYY-MM-DD')AS CREATION_DATE ,a.AMENDMENTREASONAMS AS AMENDMENT_REASON ");
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.SPLMTRYDATABLOCKREQAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.SPLMTRYDATABLOCKREQAMS, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'</AthntctnTp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>')) AS AthTp ");
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<MndtReqId>') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'</MndtReqId>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'<MndtReqId>')) AS CntrcRefNum ");
//		sb.append(",substr(replace(replace(replace(replace(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), instr(replace(replace(replace(replace(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>'),instr(replace(replace(replace(replace(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</Prtry></LclInstrm>') - instr(replace(replace(replace(replace(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<LclInstrm><Prtry>')) as LclInstrm ");
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<SeqTp>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</SeqTp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGAMDMNTDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<SeqTp>')) AS SqTp ");
//		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') as XMLString ");
//		sb.append(",a.TRANSACTIONIDENTIFIERAMS AS TXNID ,a.MSGTYPEAMS AS msg ,a.transmissionnumberams AS trsnnr ,a.SERVICEIDAMS AS SID ,NVL(B.TRANSACTIONIDENTIFIERAMS, 'NotFound') AS OUTRESP ");
//		sb.append(",CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'AUTHENTICATED' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR'AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'AUTHENTICATED' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR'AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' "); 
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'NO_RESPONSE' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'NO_RESPONSE' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING' ");
//		sb.append("WHEN a.RESULTCODE in ('2003', '8603') THEN 'RESPONSE_FILE_NOT_RECEIVED' ");
//		sb.append("WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN 'VALIDATION_FAILURE' ");
//		sb.append("end AS STATUS, ");
//		sb.append("CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN a.reasoncodeams ");
//		sb.append("end AS ERROR_CODE ");
//		sb.append(",a.PAYMENTSTATUSGROUPCODEAMS AS sts ,TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYY/MM/DD') AS dte ");
//		sb.append("FROM CAMOWNER.JNL_ACQ a ");
//		sb.append("LEFT JOIN CAMOWNER.JNL_ACQ b on a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS ");
//		sb.append("AND a. MSGTYPEAMS = 'pain.010' AND b.MSGTYPEAMS = 'pain.012' AND  a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' ");
//		sb.append("where a.MSGTYPEAMS = 'pain.010' AND TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') between '"+fromDate+"' and '"+toDate+"') ");
//		sb.append("SELECT TXNID AS mrti, DEBTOR_BANK AS debtorBank ,CREDITOR_BANK AS creditorBank ,CREATION_DATE AS creationDate, AMENDMENT_REASON AS reason ");
//		sb.append(",SUBSTR(AthTp,INSTR(AthTp,'<AthntctnTp>') + 12) AS authType ,SUBSTR(CntrcRefNum,INSTR(CntrcRefNum,'<MndtReqId>')+ 11) AS contRefNum ");
//		sb.append(",SUBSTR(LclInstrm,INSTR(LclInstrm,'<LclInstrm><Prtry>') + 18) AS dbtrAuthReq ,SUBSTR(SqTp,INSTR(SqTp,'<SeqTP>') + 8) AS instOcc ");
//		sb.append(",NVL(XMLString,' ') as  cdtrAbbShtNm ");
//		sb.append(",CASE WHEN SID = 'STMAN' AND OUTRESP = 'NotFound' and dte < trunc(sysdate) THEN 'RESPONSE_FILE_NOT_RECEIVED' ");
//		sb.append(" WHEN SUBSTR(LclInstrm,INSTR(LclInstrm,'<LclInstrm><Prtry>') + 18) = '0226' and STATUS = 'AUTHENTICATED'  then 'NOTIFICATION' ");
//		sb.append("ELSE STATUS end as STATUS ");     
//		sb.append(",ERROR_CODE AS errorCode,'ACH' AS dataSource ");
//		sb.append("FROM temptbla ");
//		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");
		
		sb.append("with temptbl as ");
		sb.append("(SELECT distinct ID ");
		sb.append(",INSTRUCTINGAGENTAMS AS CREDITOR_BANK ");
		sb.append(",INSTRUCTEDAGENTAMS as DEBTOR_BANK ");
		sb.append(",to_char(to_date(substr(transdatetime,1,8), 'YYYY-MM-DD'), 'YYYY-MM-DD') as SYSTEM_DATE ");
		sb.append(",AMENDMENTREASONAMS as AMENDMENT_REASON ");
		sb.append(",splmtrydatablockreqams ");
		sb.append(",UNDRLYGAMDMNTDTLSBLOCKAMS ");
		sb.append(",transactionidentifierams as TXNID ");
		sb.append(",msgtypeams as msgtpa ");
		sb.append(",transmissionnumberams as trsnnr ");
		sb.append(",serviceidams as SID ");
		sb.append(",resultcode as ResCd ");
		sb.append(",REJECTEDREASONCODEAMS as RejRsnCda ");
		sb.append(",reasoncodeams as RsnCda ");
		sb.append(",PAYMENTSTATUSGROUPCODEAMS as PmtStGrpCda ");
		sb.append(",UNDRLYGACCPTNCDTLSBLOCKAMS as accptblocka ");
		sb.append("from manowner.jnl_acq where msgtypeams = 'pain.010' and substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"') ");
		sb.append(",tmptbl2 as ");
		sb.append("(Select transactionidentifierams as TXNID ");
		sb.append(",msgtypeams as msgtpb ");
		sb.append(",REJECTEDREASONCODEAMS as RejRsnCdb ");
		sb.append(",reasoncodeams as RsnCdb ");
		sb.append(",paymentstatusgroupcodeams as pmtstatusb ");
		sb.append(",UNDRLYGACCPTNCDTLSBLOCKAMS as accptblockb ");
		sb.append("from manowner.jnl_acq b ");
		sb.append("WHERE MSGTYPEAMS = 'pain.012' and substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"' and paymentstatusgroupcodeams = 'ACCP' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//SplmtryData/Envlp/Cnts/RqstTrnsnbr') = '1') ");

		sb.append("Select distinct a.ID ");
		sb.append(",a.TXNID as mrti ");
		sb.append(",a.CREDITOR_BANK as creditorBank ");
		sb.append(",a.DEBTOR_BANK as debtorBank ");
		sb.append(",a.SYSTEM_DATE as creationDate ");
		sb.append(",a.AMENDMENT_REASON as reason ");         
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.splmtrydatablockreqams),'</A>')), '/A//AthntctnTp') as authType ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//OrgnlMndt/MndtReqId') as contRefNum ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//LclInstrm/Prtry') as dbtrAuthReq ");
	    sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//SeqTp') as instOcc ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGAMDMNTDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') as cdtrAbbShtNm ");
		sb.append(",case when (EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry')) = 'AAUT' ");
		sb.append("or (a.SID  = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT') then 'AUTHENTICATED' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT' ");
		sb.append("or (a.SID  = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT') then 'DECLINED' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP' ");
		sb.append("or (a.SID  = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP') then 'NO_RESPONSE' ");
		sb.append("when (a.ResCd in ('2003', '8603')) or (a.SID = 'STMAN' and NVL(b.TXNID,'NF') = 'NF') then 'RESPONSE_FILE_NOT_RECEIVED' ");
		sb.append("when a.PmtStGrpCda = 'RJCT' then 'VALIDATION_FAILURE' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//LclInstrm/Prtry') = '0226' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' then 'NOTIFICATION' ");
		sb.append("end as status ");
		sb.append(",case when a.PmtStGrpCda = 'RJCT' then a.RsnCda else '' end as errorCode ,'ACH' as dataSource ");
		sb.append("from temptbl a left join tmptbl2 b on a.TXNID = b.TXNID ");
		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, contRefNum, dbtrAuthReq, instOcc, cdtrAbbShtNm ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("reason");
		scalarList.add("authType");
		scalarList.add("contRefNum");
		scalarList.add("dbtrAuthReq");
		scalarList.add("instOcc");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("status");
		scalarList.add("errorCode");
		scalarList.add("dataSource");
		scalarList.add("mrti");
		log.debug("scalarList: " + scalarList);

		pasaRealTimeAmendInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaRealTimeAmendInfoList;
	}

	public List<?> retrieveRealTimePHIRCancellationInfo(String fromDate, String toDate)
	{
		List<PasaMandateReportEntityModel> pasaRealTimeCancInfoList = new ArrayList<PasaMandateReportEntityModel>();

		StringBuffer sb = new StringBuffer();

		//SalehaR 2019/11/03 - Optimised SQL Query
//		sb.append("WITH temptbla AS  ");
//		sb.append("(SELECT DISTINCT a.INSTRUCTINGAGENTAMS AS CREDITOR_BANK ,a.INSTRUCTEDAGENTAMS AS DEBTOR_BANK ,TO_CHAR(TO_DATE(SUBSTR(a.CREATIONDATETIMEAMS,1,10), 'YYYY-MM-DD'), 'YYYY-MM-DD') AS CREATION_DATE ");   
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'</AthntctnTp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.splmtrydatablockreqams, chr(10)), chr(13)), chr(9)), ' '),'<AthntctnTp>')) AS AthTp "); 
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<MndtReqId>') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'</MndtReqId>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS,chr(10)),chr(13)),chr(9)),' '),'<MndtReqId>')) AS CntrcRefNum ");  
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclINSTRm><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</Prtry></LclINSTRm>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<LclINSTRm><Prtry>')) AS LclINSTRm ");  
//		sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<SeqTp>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</SeqTp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<SeqTp>')) AS SqTp ");
//		//sb.append(",SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<UltmtCdtr>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'</UltmtCdtr>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGCXLDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '),'<UltmtCdtr>')) AS XMLString ");  
//		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGCXLDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') as XMLString ");
//		sb.append(",a.TRANSACTIONIDENTIFIERAMS AS TXNID ,a.MSGTYPEAMS AS msg, a.transmissionnumberams AS trsnnr, a.CANCELLATIONREASONAMS AS CANCELLATION_REASON, a.SERVICEIDAMS AS SID ");
//		sb.append(",NVL(B.TRANSACTIONIDENTIFIERAMS, 'NotFound') AS OUTRESP ");
//		sb.append(",CASE WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'SUCCESSFUL' ");   
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(9)), chr(13)), ' '), '</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<RfrdDoc><Tp><CdOrPrtry><Prtry>'))  = '<RfrdDoc><Tp><CdOrPrtry><Prtry>AAUT' THEN 'SUCCESSFUL' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NAUT' THEN 'DECLINED' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'SUCCESSFUL'  ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>NRSP' THEN 'SUCCESSFUL' ");
//		sb.append("WHEN SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(b.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING'  ");
//		sb.append("WHEN a.SERVICEIDAMS = 'MANIR' AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' ') ,INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>'),INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'</Prtry></CdOrPrtry></Tp>') - INSTR(REPLACE(REPLACE(REPLACE(REPLACE(a.UNDRLYGACCPTNCDTLSBLOCKAMS, chr(10)), chr(13)), Chr(9)), ' '),'<RfrdDoc><Tp><CdOrPrtry><Prtry>')) = '<RfrdDoc><Tp><CdOrPrtry><Prtry>PEND' THEN 'PENDING' ");
//		sb.append("WHEN a.RESULTCODE in ('2003', '8603') THEN 'RESPONSE_FILE_NOT_RECEIVED' ");
//		sb.append("WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN 'VALIDATION_FAILURE' "); 
//		sb.append("WHEN a.ACCEPTEDINDICATORAMS = 'true' THEN 'SUCCESSFUL' ");
//		sb.append("END AS STATUS ");
//		sb.append(",CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN NVL(b.REJECTEDREASONCODEAMS,a.reasoncodeams) "); 
//		sb.append("END AS ERROR_CODE ");
//		sb.append(",a.PAYMENTSTATUSGROUPCODEAMS AS sts ");
//		sb.append(",TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYY/MM/DD') AS dte "); 
//		sb.append("FROM CAMOWNER.JNL_ACQ a ");
//		sb.append("left join CAMOWNER.JNL_ACQ b on a.TRANSACTIONIDENTIFIERAMS = b.TRANSACTIONIDENTIFIERAMS "); 
//		sb.append("AND a. MSGTYPEAMS = 'pain.011' AND b.MSGTYPEAMS = 'pain.012' AND a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' "); 
//		sb.append("where a.MSGTYPEAMS = 'pain.011' AND TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') BETWEEN '"+fromDate+"' and '"+toDate+"') ");
//		sb.append("SELECT TXNID AS mrti, DEBTOR_BANK AS debtorBank ,CREDITOR_BANK AS creditorBank ,CREATION_DATE AS creationDate ,SUBSTR(AthTp,INSTR(AthTp,'<AthntctnTp>') + 12) AS authType  ");
//		//sb.append(",CANCELLATION_REASON AS reason ,NVL(SUBSTR(XMLString,INSTR(XMLString,'<Id>') + 21,INSTR(XMLString,'</Id>') - INSTR(XMLString,'<Id>') - 21),' ') AS cdtrAbbShtNm  ");
//		sb.append(",CANCELLATION_REASON AS reason ,NVL(XMLString,' ') as cdtrAbbShtNm ");
//		sb.append(", CASE WHEN SID = 'STMAN' AND OUTRESP = 'NotFound' and dte < trunc(sysdate) THEN 'RESPONSE_FILE_NOT_RECEIVED' else STATUS END AS status ");
//		sb.append(",ERROR_CODE AS errorCode ,'ACH' AS dataSource ");
//		sb.append("FROM temptbla ");
//		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, cdtrAbbShtNm ");
		
		sb.append("with tmptbl as ");
		sb.append("(select distinct INSTRUCTINGAGENTAMS AS CREDITOR_BANK ");
		sb.append(",INSTRUCTEDAGENTAMS as DEBTOR_BANK ");
		sb.append(",to_char(to_date(substr(transdatetime,1,8), 'YYYY-MM-DD'), 'YYYY-MM-DD') as SYSTEM_DATE ");
		sb.append(",transactionidentifierams as TXNID ");
		sb.append(",msgtypeams as msga ");
		sb.append(",transmissionnumberams as trsnnr ");
		sb.append(",CANCELLATIONREASONAMS as CANCELLATION_REASON ");
		sb.append(",serviceidams as SID ");
		sb.append(",paymentstatusgroupcodeams as PmtStGrpCda ");
		sb.append(",splmtrydatablockreqams ");
		sb.append(",UNDRLYGCXLDTLSBLOCKAMS ");
		sb.append(",REJECTEDREASONCODEAMS as RejRsnCda ");
		sb.append(",reasoncodeams as RsnCda ");
		sb.append(",UNDRLYGACCPTNCDTLSBLOCKAMS as accptblocka ");
		sb.append(",CANCELLATIONREASONAMS AS reason ");
		sb.append(",resultcode as ResCd ");
		sb.append(",acceptedindicatorams as AcpInd ");
		sb.append(",TRANSMISSIONNUMBERAMS as trnsmnr ");
		sb.append("from manowner.jnl_acq ");
		sb.append("where msgtypeams = 'pain.011' and substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"') ");
		sb.append(",tmptbl2 as ");
		sb.append("(select transactionidentifierams as TXNID ");
		sb.append(",msgtypeams as msgb ");
		sb.append(",paymentstatusgroupcodeams as PmtStGrpCdb ");
		sb.append(",REJECTEDREASONCODEAMS as RejRsnCdb ");
		sb.append(",reasoncodeams as RsnCdb ");
		sb.append(",UNDRLYGACCPTNCDTLSBLOCKAMS as accptblockb ");
		sb.append("from manowner.jnl_acq ");
		sb.append("where msgtypeams = 'pain.011' and to_substr(TRANSDATETIME,1,8) between '"+fromDate+"' and '"+toDate+"' and paymentstatusgroupcodeams = 'ACCP' ");
		sb.append("and UNDRLYGACCPTNCDTLSBLOCKAMS like '%<RqstTrnsnbr>1</RqstTrnsnbr>%') ");
		sb.append("Select a.TXNID as mrti ");
		sb.append(",a.DEBTOR_BANK as debtorBank ");
		sb.append(",a.CREDITOR_BANK AS creditorBank "); 
		sb.append(",a.SYSTEM_DATE AS creationDate ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.splmtrydatablockreqams),'</A>')), '/A//AthntctnTp') as authType ");
		sb.append(",a.reason as reason ");
		sb.append(",EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.UNDRLYGCXLDTLSBLOCKAMS),'</A>')), '/A//UltmtCdtr/Id/OrgId/Othr/Id') as cdtrAbbShtNm ");
		sb.append(",case when (EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT' and PmtStGrpCdb = 'ACCP') ");
		sb.append("or (a.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'AAUT') then 'SUCCESSFUL' ");
		sb.append("when (EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT' and PmtStGrpCdb = 'ACCP') ");
		sb.append("or (a.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NAUT') then 'DECLINED' ");
		sb.append("when (EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP' and PmtStGrpCdb = 'ACCP') ");
		sb.append("or (a.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',a.accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'NRSP') then 'SUCCESSFUL' ");
		sb.append("when EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',b.accptblockb),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'PEND' ");
		sb.append("or (a.SID = 'MANIR' and EXTRACTVALUE(XMLTYPE(CONCAT(CONCAT('<A>',accptblocka),'</A>')), '/A//RfrdDoc/Tp/CdOrPrtry/Prtry') = 'PEND') then 'PENDING' ");
		sb.append("when (a.ResCd in ('2003', '8603')) OR  (a.SID = 'STMAN' and NVL(b.TXNID,'NF') = 'NF') then 'RESPONSE_FILE_NOT_RECEIVED' ");
		sb.append("when a.PmtStGrpCda = 'RJCT' then 'VALIDATION_FAILURE' ");
		sb.append("when a.AcpInd = 'true' then 'SUCCESSFUL' ");                
		sb.append("end as status ");
		sb.append(",case when a.PmtStGrpCda = 'RJCT' then nvl(b.RejRsnCdb,a.RsnCda) ");
		sb.append("end as errorCode ,'ACH' as dataSource ");
		sb.append("from tmptbl a ");
		sb.append("left join tmptbl2 b on a.TXNID = b.TXNID ");
		sb.append("ORDER BY creationDate, creditorBank, debtorBank, authType, cdtrAbbShtNm ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("creditorBank");
		scalarList.add("debtorBank");
		scalarList.add("creationDate");
		scalarList.add("reason");
		scalarList.add("authType");
		scalarList.add("cdtrAbbShtNm");
		scalarList.add("status");
		scalarList.add("errorCode");
		scalarList.add("dataSource");
		scalarList.add("mrti");
		log.debug("scalarList: " + scalarList);

		pasaRealTimeCancInfoList = genericDAO.findBySql(sqlQuery, scalarList, PasaMandateReportEntityModel.class);

		return pasaRealTimeCancInfoList;
	}

	public List<?> retrieveMndtCountByCreditorBanks(String fromDate, String toDate, String serviceId)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		//      sb.append("SELECT INST_ID AS instId, SUM(NR_OF_MSGS) as nrOfMsgs, SUM(NR_MSGS_ACCEPTED) as nrOfAccpMsgs, SUM(NR_MSGS_REJECTED) as nrOfRjctMsgs ");
		//      sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_COUNT ");
		//      sb.append("WHERE PROCESS_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') AND SERVICE_ID = '"+serviceId+"' ");
		//      sb.append("GROUP BY INST_ID ");
		//      sb.append("ORDER BY INST_ID ");

		sb.append("SELECT a.INST_ID AS instId, SUM(NVL(a.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(a.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs, SUM(NVL(a.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.INST_ID = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND b.AC_CREDITOR = 'Y' ");
		sb.append("GROUP BY INST_ID ");
		sb.append("UNION ");
		sb.append("SELECT b.MEMBER_NO AS instId, 0 as nrOfMsgs, 0 as nrOfAccpMsgs, 0 as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.SYS_CIS_BANK b ");
		sb.append("WHERE b.MEMBER_NO NOT IN (SELECT a.INST_ID AS instId FROM CAMOWNER.MDT_AC_ARC_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.INST_ID = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') AND a.SERVICE_ID = '"+serviceId+"' AND b.AC_CREDITOR = 'Y' ");
		sb.append("GROUP BY INST_ID) AND b.AC_CREDITOR = 'Y' ");
		sb.append("ORDER BY instId ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();          
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;

	}	


	public List<?> retrieveMndtCountByDebtorBanks(String fromDate, String toDate, String serviceId)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT a.INST_ID AS instId, SUM(NVL(a.NR_OF_MSGS, 0)) as nrOfMsgs, SUM(NVL(a.NR_MSGS_ACCEPTED,0)) as nrOfAccpMsgs, SUM(NVL(a.NR_MSGS_REJECTED,0)) as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.INST_ID = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') AND a.SERVICE_ID = '"+serviceId+"' ");
		sb.append("AND b.AC_DEBTOR = 'Y' ");
		sb.append("GROUP BY INST_ID ");
		sb.append("UNION ");
		sb.append("SELECT b.MEMBER_NO AS instId, 0 as nrOfMsgs, 0 as nrOfAccpMsgs, 0 as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.SYS_CIS_BANK b ");
		sb.append("WHERE b.MEMBER_NO NOT IN (SELECT a.INST_ID AS instId FROM CAMOWNER.MDT_AC_ARC_MNDT_COUNT a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON a.INST_ID = b.MEMBER_NO ");
		sb.append("WHERE a.PROCESS_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') AND a.SERVICE_ID = '"+serviceId+"' AND b.AC_DEBTOR = 'Y' ");
		sb.append("GROUP BY INST_ID) AND b.AC_DEBTOR = 'Y' ");
		sb.append("ORDER BY instId ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	@Override
	public List<?> retrieveOnlineCountByCreditor(String strFromDate, String strToDate, String serviceId) {
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL AS ");
		sb.append("(SELECT a.INSTRUCTINGAGENTAMS AS instId,COUNT(*) as nrOfMsgs,CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' THEN COUNT(*) END as nrOfAccpMsgs, ");
		sb.append("CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS IS NULL OR a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN COUNT(*) END as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("WHERE a.MSGTYPEAMS = '"+serviceId+"' ");
		sb.append("AND TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') BETWEEN '"+strFromDate+"'  AND '"+strToDate+"' ");
		sb.append("GROUP BY a.INSTRUCTINGAGENTAMS, a.PAYMENTSTATUSGROUPCODEAMS, a.RESULTCODE ");
		sb.append("UNION ALL ");
		sb.append("SELECT b.MEMBER_NO AS instId, 0 as nrOfMsg, 0 as nrOfAccpMsgs, 0 as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.SYS_CIS_BANK b ");
		sb.append("WHERE b.MEMBER_NO NOT IN (SELECT a.INSTRUCTINGAGENTAMS as instId ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ");
		sb.append("ON a.INSTRUCTINGAGENTAMS = b.MEMBER_NO ");
		sb.append("WHERE a.MSGTYPEAMS ='"+serviceId+"' AND b.AC_CREDITOR ='Y')) ");
		sb.append("SELECT instId, sum(nvl(nrOfMsgs,0)) AS nrOfMsgs, sum(nvl(nrOfAccpMsgs,0)) as nrOfAccpMsgs, sum(nvl(nrOfRjctMsgs,0)) as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.TMPTBL ");
		sb.append("GROUP BY instId ");
		sb.append("ORDER BY instId ");	


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	@Override
	public List<?> retrieveOnlineCountByDebtor(String strFromDate, String strToDate, String serviceId) {

		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();		

		sb.append("WITH TMPTBL AS ");
		sb.append("(SELECT a.INSTRUCTINGAGENTAMS AS instId,COUNT(*) as nrOfMsgs,CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' THEN COUNT(*) END as nrOfAccpMsgs, ");
		sb.append("CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS IS NULL OR a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN COUNT(*) END as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("WHERE a.SERVICEIDAMS = '"+serviceId+"' ");
		sb.append("AND TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') BETWEEN '"+strFromDate+"'  AND '"+strToDate+"' ");
		sb.append("GROUP BY a.INSTRUCTINGAGENTAMS, a.RESULTCODE, a.PAYMENTSTATUSGROUPCODEAMS ");
		sb.append("UNION ");
		sb.append("SELECT b.MEMBER_NO AS instId, 0 AS nrOfMsgs, 0 AS nrOfAccpMsgs, 0 AS nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.SYS_CIS_BANK b ");
		sb.append("WHERE b.MEMBER_NO NOT IN (SELECT a.INSTRUCTINGAGENTAMS AS instId ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ");
		sb.append("ON a.INSTRUCTINGAGENTAMS = b. MEMBER_NO ");
		sb.append("WHERE a.SERVICEIDAMS ='"+serviceId+"' AND b.AC_DEBTOR = 'Y' AND  TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') BETWEEN '"+strFromDate+"'  AND '"+strToDate+"')) ");
		sb.append("SELECT instId, SUM(NVL(nrOfMsgs,0)) AS nrOfMsgs, SUM(NVL(nrOfAccpMsgs,0)) AS nrOfAccpMsgs, SUM(NVL(nrOfRjctMsgs,0)) AS nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.TMPTBL ");
		sb.append("GROUP BY instId ");
		sb.append("ORDER BY instId ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;



	}

	@Override
	public List<?> retrieveST012MndtCountByCreditorBanks(String strFromDate, String strToDate, String serviceId) {

		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL AS ");
		sb.append("(SELECT a.INSTRUCTINGAGENTAMS AS instId,COUNT(*) AS nrOfMsgs,CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS = 'ACCP' THEN ");
		sb.append("COUNT(*) END AS nrOfAccpMsgs,CASE WHEN a.PAYMENTSTATUSGROUPCODEAMS IS NULL OR a.PAYMENTSTATUSGROUPCODEAMS = 'RJCT' THEN COUNT(*) END as nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("WHERE a.SERVICEIDAMS ='"+serviceId+"' AND ");
		sb.append("TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'),'YYYY-MM-DD') BETWEEN '"+strFromDate+"'  AND '"+strToDate+"' ");
		sb.append("GROUP BY a.INSTRUCTINGAGENTAMS, a.RESULTCODE, a.PAYMENTSTATUSGROUPCODEAMS ");
		sb.append("UNION ");
		sb.append("SELECT b.MEMBER_NO AS instId, 0 AS nrOfMsgs, 0 AS nrOfAccpMsgs, 0 AS nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.SYS_CIS_BANK b ");
		sb.append("WHERE b.MEMBER_NO NOT IN (SELECT a.INSTRUCTINGAGENTAMS AS instId ");
		sb.append("FROM CAMOWNER.JNL_ACQ a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ");
		sb.append("ON a.INSTRUCTINGAGENTAMS = b.MEMBER_NO ");
		sb.append("WHERE a.SERVICEIDAMS ='"+serviceId+"' AND b.AC_CREDITOR ='Y' AND ");
		sb.append("TO_CHAR(TO_DATE(SUBSTR(a.TRANSDATETIME,1,8), 'YYYYMMDD'), 'YYYY-MM-DD') BETWEEN '"+strFromDate+"'  AND '"+strToDate+"')) ");
		sb.append("SELECT instId, SUM(NVL(nrOfMsgs,0)) AS nrOfMsgs, SUM(NVL(nrOfAccpMsgs,0)) AS nrOfAccpMsgs, SUM(NVL(nrOfRjctMsgs,0)) AS nrOfRjctMsgs ");
		sb.append("FROM CAMOWNER.TMPTBL ");
		sb.append("GROUP BY instId ");
		sb.append("ORDER BY instId ");


		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}			

	@Override
	public List<?> retrieveOpsFileReg() {
		List<CasOpsFileRegEntity> opsFileRegList = new ArrayList<CasOpsFileRegEntity>();

		try{
			opsFileRegList=genericDAO.findAll(CasOpsFileRegEntity.class);
			log.debug("opsFileRegList---->"+opsFileRegList);

		} 
		catch (NullPointerException npe) {
			log.error("NullPointer exception :" + npe.getMessage());
		} 
		catch (ObjectNotFoundException onfe) {
			log.error("No Object Exists on DB");
		} 
		catch (Exception e) {
			log.error("Error on retrieveMndtCount: " + e.getMessage());
			e.printStackTrace();
		}

		return opsFileRegList;
	}	

	public List<?> retrieveStatusReportsSentToCreditorsBanks(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TEMPTBL AS ( ");
		sb.append("SELECT a.MEMBER_NO AS cr_memno "); 
		sb.append(",a.MEMBER_NAME AS cr_memname  ");
		sb.append(",c.SERVICE_ID_OUT AS outService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a "); 
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		//2020-04-07-SalehaR: Remove Debtor Ind from Reoprt
//		sb.append("WHERE a.AC_CREDITOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
		sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
		sb.append("SELECT aa.outService as service, aa.cr_memno AS instId, ");
		sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
		sb.append("FROM TEMPTBL aa ");
		if(reportNr.equalsIgnoreCase("MR022")) {
			sb.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_STATUS_HDRS bb ON aa.cr_memno = bb.INSTG_AGT ");
		}
		else
		{
			sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS bb ON aa.cr_memno = bb.INSTG_AGT ");
			sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");  
		}
		sb.append("AND aa.outService = bb.SERVICE ");
		//		sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('2018-06-01','YYYY-MM-DD') AND TO_DATE('2018-06-30','YYYY-MM-DD') ");  
		sb.append("WHERE aa.outService IN ('ST200', 'ST105', 'ST007') ");
		sb.append("GROUP BY aa.outService, aa.cr_memno ");
		sb.append("ORDER BY aa.outService, aa.cr_memno ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}


	public List<?> retrieveStatusReportsSentToDebtorBanks(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TEMPTBL AS ( ");
		sb.append("SELECT a.MEMBER_NO AS dr_memno "); 
		sb.append(",a.MEMBER_NAME AS dr_memname  ");
		sb.append(",c.SERVICE_ID_OUT AS outService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a "); 
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c "); 
		//2020-04-07-SalehaR: Remove Debtor Ind from Reoprt
//		sb.append("WHERE a.AC_DEBTOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
		sb.append("WHERE c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN IS NULL) ");
		sb.append("SELECT aa.outService as service, aa.dr_memno AS instId, ");
		sb.append("COUNT(bb.SERVICE) AS nrOfMsgs ");
		sb.append("FROM TEMPTBL aa ");
		if(reportNr.equalsIgnoreCase("MR022")) {
			sb.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_STATUS_HDRS bb ON aa.dr_memno = bb.INSTG_AGT ");
		}
		else
		{
			sb.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_ARC_STATUS_HDRS bb ON aa.dr_memno = bb.INSTG_AGT ");
			//			sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('2018-06-01','YYYY-MM-DD') AND TO_DATE('2018-06-30','YYYY-MM-DD') ");
			sb.append("AND bb.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");
		}
		sb.append("AND aa.outService = bb.SERVICE ");

		sb.append("WHERE aa.outService IN ('ST202', 'ST204', 'ST106', 'ST008') ");
		sb.append("GROUP BY aa.outService, aa.dr_memno ");
		sb.append("ORDER BY aa.outService, aa.dr_memno ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsManinTT3(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' OR SERVICEIDAMS = 'STMVF' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE MSGTYPEAMS = 'pain.009' "); 
		sb.append("AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) <> '0227' ");

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTINGAGENTAMS, RESULTCODE, SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId ,SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsExtractManinTT3(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND MSGTYPEAMS = 'pain.009' "); 
		sb.append("AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) <> '0227' "); 

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTEDAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsManinTT1(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' OR SERVICEIDAMS = 'STMVF' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) = '0227' ");

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTINGAGENTAMS, RESULTCODE, SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId, SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsExtractManinTT1(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND MSGTYPEAMS = 'pain.009' "); 
		sb.append("AND SUBSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), INSTR(REPLACE(REPLACE(REPLACE(REPLACE(MANDATEBLOCKAMS, chr(10)), chr(13)), chr(9)), ' '), '<LclInstrm><Prtry>',1,1)+18,4) = '0227' "); 

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsManam(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' OR SERVICEIDAMS = 'STMVF' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE MSGTYPEAMS = 'pain.010' ");

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTINGAGENTAMS, RESULTCODE, SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId, SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");		

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsExtractManam(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND MSGTYPEAMS = 'pain.010' ");  

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTEDAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsMancn(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' OR SERVICEIDAMS = 'STMVF' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE MSGTYPEAMS = 'pain.011' ");

		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}

		sb.append("GROUP BY INSTRUCTINGAGENTAMS, RESULTCODE, SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId, SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsExtractMancn(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND MSGTYPEAMS = 'pain.011' ");  
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsST012(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE SERVICEIDAMS = 'ST012' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTINGAGENTAMS, RESULTCODE, SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId, SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}	

	public List<?> retrieveRTimeCountsManir(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0'THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ WHERE SERVICEIDAMS = 'MANIR' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS,RESULTCODE,SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId ,SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsExtractManir(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND SERVICEIDAMS = 'MANIR' ");  
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTINGAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsStman(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0'THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE SERVICEIDAMS = 'STMAN' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS,RESULTCODE,SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId ,SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 AA ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 BB ON AA.MEMBER_NO = BB.INSTRUCTEDAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsExtractStman(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND SERVICEIDAMS = 'STMAN' ");  
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTINGAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsStmdf(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0'THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE SERVICEIDAMS = 'STMDF' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS,RESULTCODE,SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId ,SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsExtractStmdf(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND SERVICEIDAMS = 'STMDF' ");  
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTINGAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsMandr(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS NOM ");
		sb.append(",CASE WHEN RESULTCODE = '0' THEN COUNT(1) END AS NOMA ");
		sb.append(",CASE WHEN RESULTCODE <> '0' OR SERVICEIDAMS = 'STAVF' THEN COUNT(1) END AS NOMR ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE MSGTYPEAMS = 'pain.012' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS,RESULTCODE,SERVICEIDAMS) ");
		sb.append("SELECT aa.MEMBER_NO AS instId ,SUM(NVL(bb.NOM,0)) AS nrOfMsgs ,SUM(NVL(bb.NOMA,0)) AS nrOfAccpMsgs ,SUM(NVL(bb.NOMR,0)) AS nrOfRjctMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("GROUP BY aa.MEMBER_NO ");
		sb.append("ORDER BY aa.MEMBER_NO ");	

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");
		scalarList.add("nrOfRjctMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	public List<?> retrieveRTimeCountsExtractMandr(String fromDate, String toDate, String reportNr)
	{
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();

		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTINGAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND MSGTYPEAMS = 'pain.012' ");  
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTINGAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTINGAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}

	@Override
	public List<?> retrieveRTimeCountsExtractST012(String fromDate, String toDate, String reportNr) {

		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();
		sb.append("WITH TMPTBL1 AS ");
		sb.append("(SELECT MEMBER_NO FROM CAMOWNER.SYS_CIS_BANK) ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT INSTRUCTEDAGENTAMS ,COUNT(1) AS VOL ");
		sb.append("FROM CAMOWNER.JNL_ACQ ");
		sb.append("WHERE RESULTCODE = '0' AND SERVICEIDAMS = 'ST012' ");
		if(reportNr.equalsIgnoreCase("MR021"))
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) BETWEEN '"+fromDate+"' AND '"+toDate+"' ");
		}
		else
		{
			sb.append("AND SUBSTR(TRANSDATETIME, 1, 8) = '"+fromDate+"' ");
		}
		sb.append("GROUP BY INSTRUCTEDAGENTAMS) ");
		sb.append("SELECT aa.MEMBER_NO as instId, NVL(bb.VOL,0) AS nrOfExtMsgs ");
		sb.append("FROM TMPTBL1 aa ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 bb ON aa.MEMBER_NO = bb.INSTRUCTEDAGENTAMS ");
		sb.append("ORDER BY aa.MEMBER_NO ");

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;


	}

	@Override
	public List<?> retrieveMigrationCountSubmittedByCreditorBanks(String fromDate, String toDate) {
		List<MonthlyVolumeCountEntityModel> migrationCountsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();
		
		sb.append("WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno  ");
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_IN AS inService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_CREDITOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN ='MANIN') ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT SUBSTR(MSG_ID,13,6) AS instId,SERVICE_ID as service,COUNT(*) AS nrOfMsgs,COUNT(*) AS nrOfAccpMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS ");   
		sb.append("WHERE LOCAL_INSTR_CD IN('0999','0998')AND SERVICE_ID = 'MANIN' AND ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");
		sb.append("GROUP BY SUBSTR(MSG_ID,13,6) ,SERVICE_ID ");
		sb.append("ORDER BY SUBSTR(MSG_ID,13,6) ,SERVICE_ID) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfMsgs,0)) AS nrOfMsgs,SUM(NVL(nrOfAccpMsgs,0)) AS nrOfAccpMsgs ");
		sb.append("FROM TEMPTBL1 d ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 b ");
		sb.append("ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService ");
		sb.append("ORDER BY d.cr_memno,d.inService "); 

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("instId");
		scalarList.add("service");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");

		log.debug("scalarList: " + scalarList);

		migrationCountsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return migrationCountsEntityList;
	}

	@Override
	public List<?> retrieveMigrationCountExtractedToDebtorBanks(String fromDate, String toDate) {
		List<MonthlyVolumeCountEntityModel> countsEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();
		
		sb.append("WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno "); 
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_OUT AS inService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_DEBTOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_OUT ='MANOT') ");
		sb.append(",TEMPTBL2 AS ");
		sb.append("(SELECT SUBSTR( a.EXTRACT_MSG_ID,13,6) AS instId,SUBSTR(a.EXTRACT_MSG_ID,05,05) as service,COUNT(*) AS nrOfExtMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.EXTRACT_MSG_ID,13,6) = b.MEMBER_NO "); 
		//sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998')AND  SUBSTR(a.EXTRACT_MSG_ID,05,05) = 'MANOT' AND b.AC_DEBTOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('2019-06-01','YYYY-MM-DD') AND TO_DATE('2019-06-30','YYYY-MM-DD') ");
		sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998')AND  SUBSTR(a.EXTRACT_MSG_ID,05,05) = 'MANOT' AND b.AC_DEBTOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");
		sb.append("GROUP BY SUBSTR(a.EXTRACT_MSG_ID,13,6),SUBSTR(a.EXTRACT_MSG_ID,05,05) ");
		sb.append("ORDER BY SUBSTR(a.EXTRACT_MSG_ID,13,6),SUBSTR(a.EXTRACT_MSG_ID,05,05)) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfExtMsgs,0)) as nrOfExtMsgs  ");
		sb.append("FROM TEMPTBL1 d ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 b ");
		sb.append("ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService ");
		sb.append("ORDER BY d.cr_memno,d.inService "); 

		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);        

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countsEntityList;
	}



	@Override
	public List<?> retrieveMigrationCountST101SubmittedByDebtorBanks(String fromDate, String toDate,String service) {

		List<MonthlyVolumeCountEntityModel> countST101EntityList = new ArrayList<MonthlyVolumeCountEntityModel>();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno "); 
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_IN AS inService "); 
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_DEBTOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN ='ST201'), ");
		sb.append("TEMPTBL2 AS (SELECT aa.INSTG_AGT as instId , aa.SERVICE as service ,COUNT(*) AS nrOfMsgs,COUNT(*) AS nrOfAccpMsgs ");
		sb.append("FROM ");         
		sb.append("CAMOWNER.MDT_AC_ARC_CONF_HDRS aa , ");
		sb.append("CAMOWNER.MDT_AC_ARC_CONF_DETAILS bb, ");
		sb.append("CAMOWNER.SYS_CIS_BANK cc  ");
		sb.append("WHERE  aa.SYSTEM_SEQ_NO = bb.CONF_HDR_SEQ_NO ");
		sb.append("AND aa.ARCHIVE_DATE = bb.ARCHIVE_DATE ");
		sb.append("AND aa.INSTG_AGT = cc.MEMBER_NO ");
		//sb.append("AND bb.LOCAL_INSTR_CD IN ('0999','0998') and aa.SERVICE = 'ST201' AND  cc.AC_DEBTOR = 'Y' AND bb.ARCHIVE_DATE BETWEEN TO_DATE('2019-06-01','YYYY-MM-DD') AND TO_DATE('2019-06-30','YYYY-MM-DD') ");
		sb.append("AND bb.LOCAL_INSTR_CD IN ('0999','0998') and aa.SERVICE = 'ST201' AND  cc.AC_DEBTOR = 'Y' AND bb.ARCHIVE_DATE  BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");  
		sb.append("GROUP BY aa.INSTG_AGT, aa.SERVICE ");
		sb.append("ORDER BY aa.INSTG_AGT, aa.SERVICE) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfMsgs,0)) AS nrOfMsgs,SUM(NVL(nrOfAccpMsgs,0)) AS nrOfAccpMsgs ");
		sb.append("FROM TEMPTBL1 d  LEFT OUTER JOIN TEMPTBL2 b ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService ");
		sb.append("ORDER BY d.cr_memno,d.inService ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");

		log.debug("scalarList: " + scalarList);

		countST101EntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);

		return countST101EntityList;


	}

	@Override
	public List<?> retrieveMigrationCountMANACSubmittedByDebtorBanks(String fromDate, String toDate,String service) 
	{
		List<MonthlyVolumeCountEntityModel> countMANACEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno "); 
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_IN AS inService "); 
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_DEBTOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_IN ='RCAIN'), ");
		sb.append("TEMPTBL2 AS (SELECT SUBSTR(a.MSG_ID,13,6) AS instId,a.SERVICE_ID as service,COUNT(*) AS nrOfMsgs,COUNT(*) AS nrOfAccpMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.MSG_ID,13,6) = b.MEMBER_NO ");
		//sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998') AND a.SERVICE_ID = 'RCAIN' AND b.AC_DEBTOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('2019-06-01','YYYY-MM-DD') AND TO_DATE('2019-06-30','YYYY-MM-DD') ");
		sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998') AND a.SERVICE_ID = 'RCAIN' AND b.AC_DEBTOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");
		sb.append("GROUP BY SUBSTR(a.MSG_ID,13,6) ,a.SERVICE_ID ");
		sb.append("ORDER BY SUBSTR(a.MSG_ID,13,6) ,a.SERVICE_ID) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfMsgs,0)) AS nrOfMsgs,SUM(NVL(nrOfAccpMsgs,0)) AS nrOfAccpMsgs ");
		sb.append("FROM TEMPTBL1 d  LEFT OUTER JOIN TEMPTBL2 b ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService ");
		sb.append("ORDER BY d.cr_memno,d.inService ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfMsgs");
		scalarList.add("nrOfAccpMsgs");

		log.debug("scalarList: " + scalarList);

		countMANACEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);


		return countMANACEntityList;
	}

	@Override
	public List<?> retrieveMigratonCountST103ExtractedToCreditorBanks(String fromDate, String toDate,String service) {
		List<MonthlyVolumeCountEntityModel> countsST103EntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();
		
		sb.append("WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno ");  
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_OUT AS inService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_CREDITOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_OUT ='ST203'), ");
		sb.append("TEMPTBL2 AS (SELECT aa.INST_ID as instId , aa.EXTRACT_SERVICE as service ,COUNT(*) as nrOfExtMsgs "); 
		sb.append("FROM "); 
		sb.append("CAMOWNER.MDT_AC_ARC_CONF_DETAILS aa, ");
		sb.append("CAMOWNER.SYS_CIS_BANK bb ");
		sb.append("WHERE  aa.INST_ID = bb.MEMBER_NO ");
		//sb.append("AND aa.LOCAL_INSTR_CD IN ('0999','0998') and aa.EXTRACT_SERVICE = 'ST203' AND bb.AC_CREDITOR = 'Y'AND aa.ARCHIVE_DATE BETWEEN TO_DATE('2019-06-01','YYYY-MM-DD') AND TO_DATE('2019-06-30','YYYY-MM-DD') ");
		sb.append("AND aa.LOCAL_INSTR_CD IN ('0999','0998') and aa.EXTRACT_SERVICE = 'ST203' AND bb.AC_CREDITOR = 'Y'AND aa.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD') ");
		sb.append("GROUP BY aa.INST_ID , aa.EXTRACT_SERVICE "); 
		sb.append("ORDER BY aa.INST_ID , aa.EXTRACT_SERVICE) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfExtMsgs,0)) as nrOfExtMsgs ");
		sb.append("FROM TEMPTBL1 d ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 b ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService "); 
		sb.append("ORDER BY d.cr_memno,d.inService ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsST103EntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);



		return countsST103EntityList;
	}

	@Override
	public List<?> retrieveMigratonCountMANOCExtractedToCreditorBanks(String fromDate, String toDate,String service) {
		List<MonthlyVolumeCountEntityModel> countsMANACEntityList = new ArrayList<MonthlyVolumeCountEntityModel>();

		StringBuffer sb = new StringBuffer();
		
		sb.append("WITH TEMPTBL1 AS ");
		sb.append("(SELECT a.MEMBER_NO AS cr_memno ");  
		sb.append(",a.MEMBER_NAME AS cr_memname ");
		sb.append(",c.SERVICE_ID_OUT AS inService ");
		sb.append("FROM  CAMOWNER.SYS_CIS_BANK a  ");
		sb.append(",CAMOWNER.MDT_SYSCTRL_SERVICES c ");
		sb.append("WHERE a.AC_CREDITOR = 'Y' AND c.ACTIVE_IND = 'Y' AND c.SERVICE_ID_OUT ='MANOC'), ");
		sb.append("TEMPTBL2 AS (SELECT SUBSTR( a.EXTRACT_MSG_ID,13,6) AS instId,SUBSTR(a.EXTRACT_MSG_ID,05,05) as service,COUNT(*) AS nrOfExtMsgs ");
		sb.append("FROM CAMOWNER.MDT_AC_ARC_MANDATE_TXNS a ");
		sb.append("LEFT OUTER JOIN CAMOWNER.SYS_CIS_BANK b ON  SUBSTR(a.EXTRACT_MSG_ID,13,6) = b.MEMBER_NO ");
		//sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998')AND  SUBSTR(a.EXTRACT_MSG_ID,05,05) = 'MANOC' AND b.AC_CREDITOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('2019-06-01','YYYY-MM-DD') AND TO_DATE('2019-06-30','YYYY-MM-DD') "); 
		sb.append("WHERE a.LOCAL_INSTR_CD IN('0999','0998')AND  SUBSTR(a.EXTRACT_MSG_ID,05,05) = 'MANOC' AND b.AC_CREDITOR = 'Y' AND a.ARCHIVE_DATE BETWEEN TO_DATE('"+fromDate+"','YYYY-MM-DD') AND TO_DATE('"+toDate+"','YYYY-MM-DD')"); 
		sb.append("GROUP BY SUBSTR(a.EXTRACT_MSG_ID,13,6),SUBSTR(a.EXTRACT_MSG_ID,05,05) ");
		sb.append("ORDER BY SUBSTR(a.EXTRACT_MSG_ID,13,6),SUBSTR(a.EXTRACT_MSG_ID,05,05)) ");
		sb.append("SELECT d.cr_memno AS instId,d.inService as service,SUM(NVL(nrOfExtMsgs,0)) as nrOfExtMsgs ");
		sb.append("FROM TEMPTBL1 d ");
		sb.append("LEFT OUTER JOIN TEMPTBL2 b ON d.cr_memno  = b.instId ");
		sb.append("GROUP BY d.cr_memno,d.inService "); 
		sb.append("ORDER BY d.cr_memno,d.inService ");
		
		String sqlQuery = sb.toString();
		log.debug("sqlQuery: " + sqlQuery);

		List<String> scalarList = new ArrayList<String>();		
		scalarList.add("service");
		scalarList.add("instId");
		scalarList.add("nrOfExtMsgs");

		log.debug("scalarList: " + scalarList);

		countsMANACEntityList = genericDAO.findBySql(sqlQuery, scalarList, MonthlyVolumeCountEntityModel.class);


		return countsMANACEntityList;
	}

	public boolean archiveMandatesBySQL(String archiveType, String expiredDate, String archDate)
	{
		log.info("Archive Type in ServiceBean ==> "+archiveType);
		boolean grpHdrBool = false, cashBool= false, finInstBool= false, partyIdBool= false, refDocBool= false, suppBool= false, orgMntBool= false, mndtMsgBool= false;

		log.info("===============ARCHIVING "+archiveType+" GROUP HEADER===============");
		StringBuffer sbGrpHdr = new StringBuffer();
		//GROUP HEADER
		sbGrpHdr.append("INSERT INTO CAMOWNER.MDT_AC_ARC_GRP_HDR ");
		sbGrpHdr.append("(MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,ARCHIVE_DATE) ");
		sbGrpHdr.append("SELECT distinct nvl(b.MSG_ID,'NF') as msgid ,b.CREATE_DATE_TIME ,b.AUTH_CODE ,b.CREATED_BY ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbGrpHdr.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbGrpHdr.append("left join CAMOWNER.MDT_AC_OPS_GRP_HDR b on a.msg_id = b.msg_id ");

		switch(archiveType)
		{
		case "MATCH":  sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF' ");
		break;
		case "ACCEPT": sbGrpHdr.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	
		break;
		case "EXPIRE": sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
		break;
		}

		try
		{
			String gpHdrSql = sbGrpHdr.toString();
			log.debug("gpHdrSql: " + gpHdrSql);
			genericDAO.executeNativeSQL(gpHdrSql);
			grpHdrBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Group Hdr:- "+ex.getMessage());
			ex.printStackTrace();
			grpHdrBool = false;
		}

		//CASH ACCOUNT
		log.info("===============ARCHIVING "+archiveType+" CASH ACCOUNT===============");
		StringBuffer sbCashAcc = new StringBuffer();

		sbCashAcc.append("INSERT INTO CAMOWNER.MDT_AC_ARC_CASH_ACCOUNT ");
		sbCashAcc.append("(ACCOUNT_NAME ,ACCOUNT_NUMBER ,ACCOUNT_TYPE ,CREATED_BY ,CREATED_DATE ,CURRENCY ,MODIFIED_BY ,MODIFIED_DATE ,PARTY_IDENT_TYPE_ID ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbCashAcc.append("SELECT b.ACCOUNT_NAME ,b.ACCOUNT_NUMBER ,b.ACCOUNT_TYPE ,b.CREATED_BY ,b.CREATED_DATE ,b.CURRENCY ,b.MODIFIED_BY ,b.MODIFIED_DATE ,NVL(b.PARTY_IDENT_TYPE_ID,'NF') ,b.MSG_ID ");
		sbCashAcc.append(" ,b.MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbCashAcc.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbCashAcc.append("left join CAMOWNER.MDT_AC_OPS_CASH_ACCOUNT b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

		switch(archiveType)
		{
		case "MATCH"	: sbCashAcc.append("WHERE a.PROCESS_STATUS IN ('M','R') AND NVL(b.PARTY_IDENT_TYPE_ID,'NF')  <> 'NF' ");  
		break;
		case "ACCEPT"	: sbCashAcc.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	  
		break;
		case "EXPIRE"	: sbCashAcc.append("WHERE a.PROCESS_STATUS IN ('4','9') AND NVL(b.PARTY_IDENT_TYPE_ID,'NF')  <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");  
		break;
		}

		try
		{
			String cashAccSql = sbCashAcc.toString();
			log.debug("cashAccSql: " + cashAccSql);
			genericDAO.executeNativeSQL(cashAccSql);
			cashBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Cash Account:- "+ex.getMessage());
			ex.printStackTrace();
			cashBool = false;
		}

		//FIN INST
		log.info("===============ARCHIVING "+archiveType+" FIN INST===============");
		StringBuffer sbFinInst = new StringBuffer();								

		sbFinInst.append("INSERT INTO CAMOWNER.MDT_AC_ARC_FIN_INST ");
		sbFinInst.append("(INST_ID ,BR_ADDR_LINE ,BR_ADDR_TYPE ,BR_BUILD_NUMBER ,BR_COUNTRY ,BR_COUNTRY_SUB_DIV ,BR_DEPT ,BR_POST_CODE ,BR_STREET_NAME ,BR_SUB_DEPT ,BR_TOWN_NAME ,BRANCH_ID ,BRANCH_NAME "); 
		sbFinInst.append(",CREATED_BY ,CREATED_DATE ,FI_ADDR_LINE ,FI_ADDR_TYPE ,FI_BUILD_NUMBER ,FI_COUNTRY ,FI_COUNTRY_SUB_DIV ,FI_DEPT ,FI_ID ,FI_NAME ,FI_POST_CODE ,FI_STREET_NAME ,FI_SUB_DEPT ");
		sbFinInst.append(",FI_TOWN_NAME ,FIN_INST_TYPE_ID ,ISSUER ,MEMBER_ID ,MODIFIED_BY ,MODIFIED_DATE ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbFinInst.append("SELECT b.INST_ID ,b.BR_ADDR_LINE ,b.BR_ADDR_TYPE ,b.BR_BUILD_NUMBER ,b.BR_COUNTRY ,b.BR_COUNTRY_SUB_DIV ,b.BR_DEPT ,b.BR_POST_CODE ,b.BR_STREET_NAME ,b.BR_SUB_DEPT ,b.BR_TOWN_NAME "); 
		sbFinInst.append(",b.BRANCH_ID ,b.BRANCH_NAME ,b.CREATED_BY ,b.CREATED_DATE ,b.FI_ADDR_LINE ,b.FI_ADDR_TYPE ,b.FI_BUILD_NUMBER ,b.FI_COUNTRY ,b.FI_COUNTRY_SUB_DIV ,b.FI_DEPT ,b.FI_ID ,b.FI_NAME ");
		sbFinInst.append(",b.FI_POST_CODE ,b.FI_STREET_NAME ,b.FI_SUB_DEPT ,b.FI_TOWN_NAME ,NVL(b.FIN_INST_TYPE_ID,'NF') ,b.ISSUER ,b.MEMBER_ID ,b.MODIFIED_BY ,b.MODIFIED_DATE ,b.MSG_ID ,b.MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbFinInst.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbFinInst.append("left join CAMOWNER.MDT_AC_OPS_FIN_INST b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

		switch(archiveType)
		{
		case "MATCH"	: sbFinInst.append("WHERE a.PROCESS_STATUS IN ('M','R') AND NVL(b.FIN_INST_TYPE_ID,'NF')  <> 'NF' ");   
		break;
		case "ACCEPT"	: sbFinInst.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' AND NVL(b.FIN_INST_TYPE_ID,'NF')  <> 'NF' ");	  
		break;
		case "EXPIRE"	: sbFinInst.append("WHERE a.PROCESS_STATUS IN ('4','9') AND NVL(b.FIN_INST_TYPE_ID,'NF')  <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");  
		break;
		}

		try
		{
			String finInstSql = sbFinInst.toString();
			log.debug("finInstSql: " + finInstSql);
			genericDAO.executeNativeSQL(finInstSql);
			finInstBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Fin Inst:- "+ex.getMessage());
			ex.printStackTrace();
			finInstBool = false;
		}

		//PARTY IDENTIFICATION
		log.info("===============ARCHIVING "+archiveType+" PARTY IDENT===============");
		StringBuffer sbPartyIdent = new StringBuffer();

		sbPartyIdent.append("INSERT INTO CAMOWNER.MDT_AC_ARC_PARTY_IDENT ");
		sbPartyIdent.append("(ADDR_LINE ,ADDR_TYPE ,BUILD_NUMBER ,CITY_OF_BIRTH ,CONTACT_NAME ,COUNTRY ,COUNTRY_SUB_DIV ,CREATED_BY ,CREATED_DATE ,CTRY_OF_BIRTH ,CTRY_OF_RESIDENCE ,DATE_OF_BIRTH ,DEPT ,EMAIL ,FAX_NR "); 
		sbPartyIdent.append(",ID ,MOB_NR ,MODIFIED_BY ,MODIFIED_DATE ,NAME ,NAME_PREFIX ,PARTY_IDENT_TYPE_ID ,PHONE_NR ,POST_CODE ,PROVINCE_OF_BIRTH ,STREET_NAME ,SUB_DEPT ,TOWN_NAME ,MSG_ID ,ENTRY_CLASS ");
		sbPartyIdent.append(",MANDATE_REQ_TRAN_ID, ARCHIVE_DATE) ");
		sbPartyIdent.append("SELECT b.ADDR_LINE ,b.ADDR_TYPE ,b.BUILD_NUMBER ,b.CITY_OF_BIRTH ,b.CONTACT_NAME ,b.COUNTRY ,b.COUNTRY_SUB_DIV ,b.CREATED_BY ,b.CREATED_DATE ,b.CTRY_OF_BIRTH ,b.CTRY_OF_RESIDENCE "); 
		sbPartyIdent.append(",b.DATE_OF_BIRTH ,b.DEPT ,b.EMAIL ,b.FAX_NR ,b.ID ,b.MOB_NR ,b.MODIFIED_BY ,b.MODIFIED_DATE ,b.NAME ,b.NAME_PREFIX ,NVL(b.PARTY_IDENT_TYPE_ID,'NF'),b.PHONE_NR ,b.POST_CODE ,b.PROVINCE_OF_BIRTH "); 
		sbPartyIdent.append(",b.STREET_NAME ,b.SUB_DEPT ,b.TOWN_NAME ,b.MSG_ID ,b.ENTRY_CLASS ,b.MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbPartyIdent.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbPartyIdent.append("left join CAMOWNER.MDT_AC_OPS_PARTY_IDENT b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

		switch(archiveType)
		{
		case "MATCH"	: sbPartyIdent.append("WHERE a.PROCESS_STATUS IN ('M','R') AND NVL(b.PARTY_IDENT_TYPE_ID,'NF')  <> 'NF' ");  
		break;
		case "ACCEPT"	: sbPartyIdent.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	  
		break;
		case "EXPIRE"	: sbPartyIdent.append("WHERE a.PROCESS_STATUS IN ('4','9') AND NVL(b.PARTY_IDENT_TYPE_ID,'NF')  <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");  
		break;
		}

		try
		{
			String partyIdentSql = sbPartyIdent.toString();
			log.debug("partyIdentSql: " + partyIdentSql);
			genericDAO.executeNativeSQL(partyIdentSql);
			partyIdBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Party Ident:- "+ex.getMessage());
			ex.printStackTrace();
			partyIdBool = false;
		}

		//REF DOC
		log.info("===============ARCHIVING "+archiveType+" REF DOC===============");
		StringBuffer sbRefDoc = new StringBuffer();

		sbRefDoc.append("INSERT INTO CAMOWNER.MDT_AC_ARC_REF_DOC ");
		sbRefDoc.append("(CODE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,REF_DOC_NUMBER ,RELATED_DATE ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbRefDoc.append("SELECT b.CODE ,b.CREATED_BY ,b.CREATED_DATE ,b.MODIFIED_BY ,b.MODIFIED_DATE ,b.REF_DOC_NUMBER ,b.RELATED_DATE ,nvl(b.MSG_ID,'NF') as msgid ,b.MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbRefDoc.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbRefDoc.append("left join CAMOWNER.MDT_AC_OPS_REF_DOC b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

		switch(archiveType)
		{
		case "MATCH"	: sbRefDoc.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF' ");
		break;
		case "ACCEPT"	: sbRefDoc.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");
		break;
		case "EXPIRE"	: sbRefDoc.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");
		break;
		}

		try
		{
			String refDocSql = sbRefDoc.toString();
			log.debug("refDocSql: " + refDocSql);
			genericDAO.executeNativeSQL(refDocSql);
			refDocBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Ref Doc:- "+ex.getMessage());
			ex.printStackTrace();
			refDocBool = false;
		}

		//SUPPLEMENTARY DATA
		log.info("===============ARCHIVING "+archiveType+" SUPPL DATA===============");
		StringBuffer sbSupplData = new StringBuffer();
		sbSupplData.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUPPL_DATA ");
		sbSupplData.append("(ADJUST_AMT ,ADJUST_AMT_CURR ,ADJUST_CAT ,ADJUST_RATE ,DEBIT_VALUE_TYPE ,DTE_ADJUST_RULE_IND ,FIRST_COLL_AMT ,FIRST_COLL_AMT_CURR ,PLACE_AND_NAME "); 
		sbSupplData.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,MANDATE_REF_NR ,AUTH_CHANNEL ,AUTH_TYPE ,COLL_DAY ,MSG_ID ,MANDATE_AUTH_DATE ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbSupplData.append("SELECT b.ADJUST_AMT ,b.ADJUST_AMT_CURR ,b.ADJUST_CAT ,b.ADJUST_RATE ,b.DEBIT_VALUE_TYPE ,b.DTE_ADJUST_RULE_IND ,b.FIRST_COLL_AMT ,b.FIRST_COLL_AMT_CURR ,b.PLACE_AND_NAME ,b.CREATED_BY ");
		sbSupplData.append(" ,b.CREATED_DATE ,b.MODIFIED_BY ,b.MODIFIED_DATE ,b.MANDATE_REF_NR ,b.AUTH_CHANNEL ,b.AUTH_TYPE ,b.COLL_DAY ,b.MSG_ID ,b.MANDATE_AUTH_DATE ,b.MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbSupplData.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		sbSupplData.append("left join CAMOWNER.MDT_AC_OPS_SUPPL_DATA b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

		switch(archiveType)
		{
		case "MATCH"	: sbSupplData.append("WHERE a.PROCESS_STATUS IN ('M','R') ");
		break;
		case "ACCEPT"	: sbSupplData.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	
		break;
		case "EXPIRE"	: sbSupplData.append("WHERE a.PROCESS_STATUS IN ('4','9') AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");
		break;
		}

		try
		{
			String suppDataSql = sbSupplData.toString();
			log.debug("suppDataSql: " + suppDataSql);
			genericDAO.executeNativeSQL(suppDataSql);
			suppBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Suppl Data:- "+ex.getMessage());
			ex.printStackTrace();
			suppBool = false;
		}

		if(!archiveType.equalsIgnoreCase("ACCEPT")) {

			//ORIGINAL MANDATE
			log.info("===============ARCHIVING "+archiveType+" ORGNL MANDATE===============");
			StringBuffer sbOrigMndt = new StringBuffer();
			sbOrigMndt.append("INSERT INTO CAMOWNER.MDT_AC_ARC_ORGNL_MNDT ");
			sbOrigMndt.append("(MANDATE_ID ,MANDATE_REQ_TRAN_ID ,MSG_ID ,MANDATE_REQ_ID ,CREDITOR_NAME ,DEBTOR_NAME ,DEBTOR_BRANCH_NO ,ORGNL_MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
			sbOrigMndt.append("SELECT b.MANDATE_ID ,nvl(b.MANDATE_REQ_TRAN_ID,'NF') as mrti ,b.MSG_ID ,b.MANDATE_REQ_ID ,b.CREDITOR_NAME ,b.DEBTOR_NAME ,b.DEBTOR_BRANCH_NO ");
			sbOrigMndt.append(",b.ORGNL_MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
			sbOrigMndt.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
			sbOrigMndt.append("left join CAMOWNER.MDT_AC_OPS_ORGNL_MNDT b on a.msg_id = b.msg_id and A.MANDATE_REQ_TRAN_ID = B.MANDATE_REQ_TRAN_ID ");

			switch(archiveType)
			{
			case "MATCH"	: sbOrigMndt.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MANDATE_REQ_TRAN_ID,'NF') <> 'NF' ");
			break;
			case "EXPIRE"	: sbOrigMndt.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MANDATE_REQ_TRAN_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");
			break;
			}

			try
			{
				String origMndtSql = sbOrigMndt.toString();
				log.debug("origMndtSql: " + origMndtSql);
				genericDAO.executeNativeSQL(origMndtSql);
				orgMntBool = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Matched Mandates Archive--Orig Mandate:- "+ex.getMessage());
				ex.printStackTrace();
				orgMntBool = false;
			}
		}
		else {
			orgMntBool = true;//NO ORGMNDT for RCAIN. To pass check below
		}

		//MANDATE MESSAGE
		log.info("===============ARCHIVING "+archiveType+" MANDATE===============");
		StringBuffer sbMandate = new StringBuffer();
		sbMandate.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MNDT_MSG ");
		sbMandate.append("(MSG_ID ,MANDATE_ID ,MANDATE_REQ_ID ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,TO_DATE ,FIRST_COLL_DATE ,FINAL_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,LOCAL_INSTR_CD "); 
		sbMandate.append(",SERVICE_LEVEL ,STATUS ,ACTIVE_IND ,ACTIVE_IND_CHANGE_DATE ,FILE_NAME ,FILE_DATE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,MOD_REASON ,ORGNL_MSG_ID ,ORGNL_MSG_NAME_ID "); 
		sbMandate.append(",ORGNL_MSG_CREATE_DATE_TIME ,AMEND_REASON_CODE ,AMEND_REASON_DESC ,ORGNL_MDT_REQ_ID ,TRACKING_IND ,ACCEPTED ,REJECT_REASON_CODE ,ADD_REJECT_RSN_INF ,ORIG_MANDATE_ID ,PROCESS_IND ,CONTENTS ");                 
		sbMandate.append(",SYS_GEN_SEQ_NR ,MDT_INF_REQ_ID ,RECORD_TYPE ,EXTRACT_MSG_ID ,SERVICE_ID ,ONLINE_IND ,MANDATE_REQ_TRAN_ID ,MANDATE_REF_NR ,ARCHIVE_DATE) ");
		sbMandate.append("SELECT MSG_ID ,MANDATE_ID ,MANDATE_REQ_ID ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,TO_DATE ,FIRST_COLL_DATE ,FINAL_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ");
		if(archiveType.equalsIgnoreCase("EXPIRE") )
		{
			//Expire Txn-Change Process Status to 8
			sbMandate.append(",LOCAL_INSTR_CD ,SERVICE_LEVEL ,STATUS ,ACTIVE_IND ,ACTIVE_IND_CHANGE_DATE ,FILE_NAME ,FILE_DATE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,'8' ,MOD_REASON ,ORGNL_MSG_ID ");
		}
		else
		{
			sbMandate.append(",LOCAL_INSTR_CD ,SERVICE_LEVEL ,STATUS ,ACTIVE_IND ,ACTIVE_IND_CHANGE_DATE ,FILE_NAME ,FILE_DATE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,MOD_REASON ,ORGNL_MSG_ID ");
		}

		sbMandate.append(",ORGNL_MSG_NAME_ID ,ORGNL_MSG_CREATE_DATE_TIME ,AMEND_REASON_CODE ,AMEND_REASON_DESC ,ORGNL_MDT_REQ_ID ,TRACKING_IND ,ACCEPTED ,REJECT_REASON_CODE ,ADD_REJECT_RSN_INF ,ORIG_MANDATE_ID,PROCESS_IND ");              
		sbMandate.append(",CONTENTS ,SYS_GEN_SEQ_NR ,MDT_INF_REQ_ID ,RECORD_TYPE ,EXTRACT_MSG_ID ,SERVICE_ID ,ONLINE_IND ,MANDATE_REQ_TRAN_ID ,MANDATE_REF_NR ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbMandate.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");


		switch(archiveType)
		{
		case "MATCH"	: sbMandate.append("WHERE a.PROCESS_STATUS IN ('M','R') ");
		break;
		case "ACCEPT"	: sbMandate.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");
		break;
		case "EXPIRE"	: sbMandate.append("WHERE a.PROCESS_STATUS IN ('4','9') AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");
		break;
		}

		try
		{
			String mandateSql = sbMandate.toString();
			log.debug("mandateSql: " + mandateSql);
			genericDAO.executeNativeSQL(mandateSql);
			mndtMsgBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Matched Mandates Archive--Mandate Mess:- "+ex.getMessage());
			ex.printStackTrace();
			mndtMsgBool = false;
		}

		if(grpHdrBool && cashBool && finInstBool && partyIdBool && refDocBool && suppBool && orgMntBool && mndtMsgBool)
			return true;
		else
			return false;
	}

	public boolean deleteMatchedMandates(String archiveType, String expiredDate)
	{
		boolean grpHdrBool = false, cashBool= false, finInstBool= false, partyIdBool= false, refDocBool= false, suppBool= false, orgMntBool= false, mndtMsgBool= false;

		log.info("===============DELETING "+archiveType+" MANDATE TRANSACTIONS===============");
		StringBuffer sbGrpHdr = new StringBuffer();
		sbGrpHdr.append("delete from CAMOWNER.MDT_AC_OPS_GRP_HDR b "); 
		sbGrpHdr.append("where (b.msg_id) IN ");
		sbGrpHdr.append("(select a.msg_id from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbGrpHdr.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbGrpHdr.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String grpHdrSql = sbGrpHdr.toString();
			log.debug("delete grpHdrSql: " + grpHdrSql);
			genericDAO.executeNativeSQL(grpHdrSql);
			grpHdrBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Group Header:- "+ex.getMessage());
			ex.printStackTrace();
			grpHdrBool = false;
		}

		StringBuffer sbCashAcc = new StringBuffer();
		sbCashAcc.append("delete from CAMOWNER.MDT_AC_OPS_CASH_ACCOUNT b "); 
		sbCashAcc.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
		sbCashAcc.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbCashAcc.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbCashAcc.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbCashAcc.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String cashAccSql = sbCashAcc.toString();
			log.debug("delete cashAccSql: " + cashAccSql);
			genericDAO.executeNativeSQL(cashAccSql);
			cashBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Cash Acc:- "+ex.getMessage());
			ex.printStackTrace();
			cashBool = false;
		}

		StringBuffer sbFinInst = new StringBuffer();
		sbFinInst.append("delete from CAMOWNER.MDT_AC_OPS_FIN_INST b "); 
		sbFinInst.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
		sbFinInst.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbFinInst.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbFinInst.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbFinInst.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String finInstSql = sbFinInst.toString();
			log.debug("delete finInstSql: " + finInstSql);
			genericDAO.executeNativeSQL(finInstSql);
			finInstBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Fin Inst:- "+ex.getMessage());
			ex.printStackTrace();
			finInstBool = false;
		}

		StringBuffer sbPartyId = new StringBuffer();
		sbPartyId.append("delete from CAMOWNER.MDT_AC_OPS_PARTY_IDENT b "); 
		sbPartyId.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
		sbPartyId.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbPartyId.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbPartyId.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbPartyId.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}
		try
		{
			String partyIdSql = sbPartyId.toString();
			log.debug("delete partyIdSql: " + partyIdSql);
			genericDAO.executeNativeSQL(partyIdSql);
			partyIdBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Party id:- "+ex.getMessage());
			ex.printStackTrace();
			partyIdBool = false;
		}

		StringBuffer sbRefDoc = new StringBuffer();
		sbRefDoc.append("delete from CAMOWNER.MDT_AC_OPS_REF_DOC b "); 
		sbRefDoc.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
		sbRefDoc.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbRefDoc.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbRefDoc.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbRefDoc.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String refDocSql = sbRefDoc.toString();
			log.debug("delete refDocSql: " + refDocSql);
			genericDAO.executeNativeSQL(refDocSql);
			refDocBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Ref Doc:- "+ex.getMessage());
			ex.printStackTrace();
			refDocBool = false;
		}

		StringBuffer sbSuppData = new StringBuffer();
		sbSuppData.append("delete from CAMOWNER.MDT_AC_OPS_SUPPL_DATA b "); 
		sbSuppData.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
		sbSuppData.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbSuppData.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
		break;
		case "ACCEPT": sbSuppData.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN') ");	
		break;
		case "EXPIRE": sbSuppData.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
		break;
		}

		try
		{
			String suppDataSql = sbSuppData.toString();
			log.debug("delete suppDataSql: " + suppDataSql);
			genericDAO.executeNativeSQL(suppDataSql);
			suppBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--Suppl Data:- "+ex.getMessage());
			ex.printStackTrace();
			suppBool = false;
		}

		if(!archiveType.equals("ACCEPT"))
		{
			StringBuffer sbOrgMndt = new StringBuffer();
			sbOrgMndt.append("delete from CAMOWNER.MDT_AC_OPS_ORGNL_MNDT b "); 
			sbOrgMndt.append("where (b.msg_id, b.MANDATE_REQ_TRAN_ID) IN ");
			sbOrgMndt.append("(select a.msg_id,a.MANDATE_REQ_TRAN_ID from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
			switch(archiveType)
			{
			case "MATCH":  sbOrgMndt.append("WHERE a.PROCESS_STATUS IN ('M','R') and nvl(b.MSG_ID,'NF') <> 'NF') ");
			break;
			case "EXPIRE": sbOrgMndt.append("WHERE a.PROCESS_STATUS IN ('4','9') and nvl(b.MSG_ID,'NF') <> 'NF' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
			break;
			}


			try
			{
				String origMndtSql = sbOrgMndt.toString();
				log.debug("delete origMndtSql: " + origMndtSql);
				genericDAO.executeNativeSQL(origMndtSql);
				orgMntBool = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Delete Matched Mandates--Orig Mandate:- "+ex.getMessage());
				ex.printStackTrace();
				orgMntBool = false;
			}
		}
		else
		{
			orgMntBool = true;//No OrgnMndt for RCAIN. To pass check at bottom
		}


		StringBuffer sbMndMsg = new StringBuffer();
		sbMndMsg.append("delete from CAMOWNER.MDT_AC_OPS_MNDT_MSG a ");
		switch(archiveType)
		{
		case "MATCH":  sbMndMsg.append("WHERE a.PROCESS_STATUS IN ('M','R') ");
		break;
		case "ACCEPT": sbMndMsg.append("WHERE a.PROCESS_STATUS = '4' AND a.SERVICE_ID = 'RCAIN' ");	
		break;
		case "EXPIRE": sbMndMsg.append("WHERE a.PROCESS_STATUS IN ('4','9') AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
		break;
		}
		try
		{
			String mndMsgSql = sbMndMsg.toString();
			log.debug("delete mndMsgSql: " + mndMsgSql);
			genericDAO.executeNativeSQL(mndMsgSql);
			mndtMsgBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Delete Matched Mandates--MandateMsg:- "+ex.getMessage());
			ex.printStackTrace();
			mndtMsgBool = false;
		}

		if(grpHdrBool && cashBool && finInstBool && partyIdBool && refDocBool && suppBool && orgMntBool && mndtMsgBool)
			return true;
		else
			return false;
	}

	public boolean archiveDailyBillingBySQL(String archDate)
	{
		boolean dailyBillBool = false;

		StringBuffer sbDlyBill = new StringBuffer();

		sbDlyBill.append("INSERT INTO CAMOWNER.CAS_ARC_DAILY_BILLING ");
		sbDlyBill.append("(SYSTEM_SEQ_NO ,CREDITOR_BANK ,DEBTOR_BANK ,SUB_SERVICE ,TXN_TYPE ,TXN_STATUS ,CREATED_BY ,CREATED_DATE ,BILL_EXP_STATUS ,ACTION_DATE ");
		sbDlyBill.append(",AUTH_CODE ,TXN_ID ,MNDT_REF_NUM ,EXT_MSG_ID ,RESP_DATE ,ARCHIVE_DATE) ");
		sbDlyBill.append("SELECT SYSTEM_SEQ_NO ,CREDITOR_BANK ,DEBTOR_BANK ,SUB_SERVICE ,TXN_TYPE ,TXN_STATUS ,CREATED_BY ,CREATED_DATE ,BILL_EXP_STATUS ,ACTION_DATE ");
		sbDlyBill.append(",AUTH_CODE ,TXN_ID ,MNDT_REF_NUM ,EXT_MSG_ID ,RESP_DATE ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbDlyBill.append("FROM CAMOWNER.CAS_OPS_DAILY_BILLING WHERE BILL_EXP_STATUS = 'Y' ");

		try
		{
			String dailyBillSQL = sbDlyBill.toString();
			log.debug("dailyBillSQL: " + dailyBillSQL);
			genericDAO.executeNativeSQL(dailyBillSQL);
			dailyBillBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Daily Billing:- "+ex.getMessage());
			ex.printStackTrace();
			dailyBillBool = false;
		}
		return dailyBillBool;
	}

	public boolean deleteDailyBilling()
	{
		boolean dailyBillBool = false;

		try
		{
			String dlyBillDel = "DELETE FROM CAMOWNER.CAS_OPS_DAILY_BILLING WHERE BILL_EXP_STATUS = 'Y' ";
			log.debug("dlyBillDel: " + dlyBillDel);
			genericDAO.executeNativeSQL(dlyBillDel);
			dailyBillBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS Daily Billing:- "+ex.getMessage());
			ex.printStackTrace();
			dailyBillBool = false;
		}
		return dailyBillBool;
	}

	public boolean archiveConfHdrs(String archDate)
	{
		boolean confHdrsBool = false, delConfHdrs = false;

		log.info("===============ARCHIVING CAMOWNER.CAS_ARC_CONF_HDRS ===============");
		StringBuffer sbConfHdrs = new StringBuffer();

		sbConfHdrs.append("INSERT INTO CAMOWNER.CAS_ARC_CONF_HDRS ");
		sbConfHdrs.append("(SYSTEM_SEQ_NO ,HDR_MSG_ID ,CREATE_DATE_TIME ,INSTG_AGT ,INSTD_AGT ,ORGNL_MSG_ID ,ORGNL_MSG_NAME ,ORGNL_CREATE_DATE_TIME ,PROCESS_STATUS ");
		sbConfHdrs.append(",GROUP_STATUS ,SERVICE ,GROUP_ERROR ,ARCHIVE_DATE) ");
//		2020/03/26-SALEHAR- REMOVE JOIN TO CONF DETAILS
//		sbConfHdrs.append("SELECT a.SYSTEM_SEQ_NO ,a.HDR_MSG_ID ,a.CREATE_DATE_TIME ,a.INSTG_AGT ,a.INSTD_AGT ,a.ORGNL_MSG_ID ,a.ORGNL_MSG_NAME ,a.ORGNL_CREATE_DATE_TIME ,a.PROCESS_STATUS ");
//		sbConfHdrs.append(",a.GROUP_STATUS ,a.SERVICE ,a.GROUP_ERROR ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
//		sbConfHdrs.append("FROM CAMOWNER.CAS_OPS_CONF_HDRS a ");
//		sbConfHdrs.append("LEFT JOIN CAMOWNER.CAS_OPS_CONF_DETAILS b ON a.SYSTEM_SEQ_NO = b.CONF_HDR_SEQ_NO ");
//		sbConfHdrs.append("WHERE b.PROCESS_STATUS IN ('4') ");
		sbConfHdrs.append("SELECT SYSTEM_SEQ_NO ,HDR_MSG_ID ,CREATE_DATE_TIME ,INSTG_AGT ,INSTD_AGT ,ORGNL_MSG_ID ,ORGNL_MSG_NAME ,ORGNL_CREATE_DATE_TIME ,PROCESS_STATUS ");
		sbConfHdrs.append(",GROUP_STATUS ,SERVICE ,GROUP_ERROR ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbConfHdrs.append("FROM CAMOWNER.CAS_OPS_CONF_HDRS ");
		sbConfHdrs.append("WHERE PROCESS_STATUS = 'C' ");

		try
		{
			String confHdrsSql = sbConfHdrs.toString();
			log.debug("confHdrsSql: " + confHdrsSql);
			genericDAO.executeNativeSQL(confHdrsSql);
			confHdrsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Conf Hdrs:- "+ex.getMessage());
			ex.printStackTrace();
			confHdrsBool = false;
		}

		if(confHdrsBool)
		{
			log.info("===============DELETING CAMOWNER.CAS_ARC_CONF_HDRS===============");
			try
			{
				StringBuffer sbDelCHdrs = new StringBuffer();
//				2020/03/26-SALEHAR- REMOVE JOIN TO CONF DETAILS
//				sbDelCHdrs.append("delete FROM CAMOWNER.CAS_OPS_CONF_HDRS a ");
//				sbDelCHdrs.append("where (a.SYSTEM_SEQ_NO) in ");
//				sbDelCHdrs.append("(select b.CONF_HDR_SEQ_NO FROM CAMOWNER.CAS_OPS_CONF_DETAILS b WHERE b.PROCESS_STATUS = '4') ");

				sbDelCHdrs.append("DELETE FROM CAMOWNER.CAS_OPS_CONF_HDRS WHERE PROCESS_STATUS = 'C' ");

				String delConHdsSQL = sbDelCHdrs.toString();
				log.debug("delConHdsSQL: " + delConHdsSQL);
				genericDAO.executeNativeSQL(delConHdsSQL);
				delConfHdrs = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Deleting OPS CONF HDRS:- "+ex.getMessage());
				ex.printStackTrace();
				delConfHdrs = false;
			}
		}
		return delConfHdrs;
	}

	public boolean archiveConfDtls(String archDate)
	{
		boolean confDtlsBool = false, delConfDtls = false;

		log.info("===============ARCHIVING CAMOWNER.CAS_ARC_CONF_DETAILS DETAILS===============");
		StringBuffer sbConfDtls = new StringBuffer();
		
		sbConfDtls.append("INSERT INTO CAMOWNER.CAS_ARC_CONF_DETAILS ");
		sbConfDtls.append("(SYSTEM_SEQ_NO ,CONF_HDR_SEQ_NO ,ERROR_CODE ,TXN_ID ,TXN_STATUS ,ERROR_TYPE ,RECORD_ID ,MANDATE_REF_NUMBER ,INST_ID ,PROCESS_STATUS ");
		sbConfDtls.append(",EXTRACT_SERVICE ,ORGNL_MSG_TYPE ,EXTRACT_MSG_ID ,LOCAL_INSTR_CD ,ARCHIVE_DATE,MSG_ID,IN_FILE_NAME,EXTRACT_FILE_NAME) ");
		sbConfDtls.append("SELECT SYSTEM_SEQ_NO ,CONF_HDR_SEQ_NO ,ERROR_CODE ,TXN_ID ,TXN_STATUS ,ERROR_TYPE ,RECORD_ID ,MANDATE_REF_NUMBER ,INST_ID ,PROCESS_STATUS ");
		sbConfDtls.append(",EXTRACT_SERVICE ,ORGNL_MSG_TYPE ,EXTRACT_MSG_ID ,LOCAL_INSTR_CD ,TO_DATE('"+archDate+"','YYYY-MM-DD'),MSG_ID,IN_FILE_NAME,EXTRACT_FILE_NAME ");
		sbConfDtls.append("FROM CAMOWNER.CAS_OPS_CONF_DETAILS a WHERE a.PROCESS_STATUS = '4' ");

		try
		{
			String confDtlsSQL = sbConfDtls.toString();
			log.debug("confDtlsSQL: " + confDtlsSQL);
			genericDAO.executeNativeSQL(confDtlsSQL);
			confDtlsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Conf Details:- "+ex.getMessage());
			ex.printStackTrace();
			confDtlsBool = false;
		}

		if(confDtlsBool) {
			log.info("===============DELETING CAMOWNER.CAS_ARC_CONF_DETAILS===============");
			try
			{
				String delCDtlStr = "DELETE FROM CAMOWNER.CAS_OPS_CONF_DETAILS b WHERE b.PROCESS_STATUS = '4' ";
				log.debug("delCDtlStr: " + delCDtlStr);
				genericDAO.executeNativeSQL(delCDtlStr);
				delConfDtls = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Deleting OPS CONF DETAILS:- "+ex.getMessage());
				ex.printStackTrace();
				delConfDtls = false;
			}
		}
		return delConfDtls;
	}

	public boolean archiveOpsErrorCodesRpt(String archDate)
	{
		boolean opsErrBool = false;

		StringBuffer sbErrorRep = new StringBuffer();

		sbErrorRep.append("INSERT INTO CAMOWNER.MDT_AC_ARC_ERROR_CODES_REPORT ");
		sbErrorRep.append("(SYSTEM_SEQ_NO ,PROCESSING_DATE ,PROCESSING_MONTH ,DEBTOR_BANK ,CREDITOR_BANK ,ULTIMATE_CREDITOR ,ABBREV_SHORT_NAME ,TXN_ID ");
		sbErrorRep.append(",ERROR_CODE ,ERROR_CODE_DESC ,EXTRACTED_IND ,ONLINE_IND ,ARCHIVE_DATE ,SERVICE_ID) ");
		sbErrorRep.append("SELECT SYSTEM_SEQ_NO ,PROCESSING_DATE ,PROCESSING_MONTH ,DEBTOR_BANK ,CREDITOR_BANK ,ULTIMATE_CREDITOR ,ABBREV_SHORT_NAME ,TXN_ID ");
		sbErrorRep.append(",ERROR_CODE ,ERROR_CODE_DESC ,EXTRACTED_IND ,ONLINE_IND ,TO_DATE('"+archDate+"','YYYY-MM-DD') ,SERVICE_ID ");
		sbErrorRep.append("FROM CAMOWNER.MDT_AC_OPS_ERROR_CODES_REPORT ");

		try
		{
			String errCdsSQL = sbErrorRep.toString();
			log.debug("errCdsSQL: " + errCdsSQL);
			genericDAO.executeNativeSQL(errCdsSQL);
			opsErrBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Ops Error Codes:- "+ex.getMessage());
			ex.printStackTrace();
			opsErrBool = false;
		}
		return opsErrBool;
	}

	public boolean deleteOpsErrorCodesRpt()
	{
		boolean errCodeDel = false;

		try
		{
			String errCodeDelSQL = "DELETE FROM CAMOWNER.MDT_AC_OPS_ERROR_CODES_REPORT ";
			genericDAO.executeNativeSQL(errCodeDelSQL);
			errCodeDel = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS Error Codes:- "+ex.getMessage());
			ex.printStackTrace();
			errCodeDel = false;
		}
		return errCodeDel;

	}

	public boolean archiveMarkOffBySQL()
	{
		boolean mrkoffHdrsBool = false, mrkoffDtlsBool = false;

		log.info("===============ARCHIVING MarkOff HDRS===============");
		StringBuffer sbMrkOffHdrs = new StringBuffer();

		sbMrkOffHdrs.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MRKOFF_GRP_HDR ");
		sbMrkOffHdrs.append("(MSG_ID ,CREATE_DATE_TIME ,INITIATING_PARTY ,INSTRUCTING_AGENT ,INSTRUCTED_AGENT ,CREATED_BY ");
		sbMrkOffHdrs.append(",CREATED_DATE,ARCHIVE_DATE) ");
		sbMrkOffHdrs.append("SELECT DISTINCT a.MSG_ID ,a.CREATE_DATE_TIME ,a.INITIATING_PARTY ,a.INSTRUCTING_AGENT ,a.INSTRUCTED_AGENT ,a.CREATED_BY ");
		sbMrkOffHdrs.append(",a.CREATED_DATE ,TRUNC(SYSDATE) ");
		sbMrkOffHdrs.append("FROM CAMOWNER.MDT_AC_OPS_MRKOFF_GRP_HDR a ");
		sbMrkOffHdrs.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_OPS_MRKOFF_ACCEPT_DET b ON a.MSG_ID = b.MSG_ID ");
		sbMrkOffHdrs.append("WHERE b.TRAN_STATUS = '4' ");

		try
		{
			String mrkoffHdrsSql = sbMrkOffHdrs.toString();
			log.debug("confHdrsSql: " + mrkoffHdrsSql);
			genericDAO.executeNativeSQL(mrkoffHdrsSql);
			mrkoffHdrsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive MarkOff Hdrs:- "+ex.getMessage());
			ex.printStackTrace();
			mrkoffHdrsBool = false;
		}

		if(mrkoffHdrsBool)
		{
			log.info("===============ARCHIVING MarkOff DETAILS===============");
			StringBuffer sbMrkoffDtls = new StringBuffer();

			sbMrkoffDtls.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MRKOFF_ACCEPT_DET ");
			sbMrkoffDtls.append("(MSG_ID ,ACCEPTED ,REJECT_REASON_CODE ,MANDATE_ID ,MANDATE_REQ_ID ,TRACKING_IND ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ");
			sbMrkoffDtls.append(",FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_COLL_AMT_CURR ,MAX_COLL_AMT ,CRED_SCHEME_ID ");
			sbMrkoffDtls.append(",CRED_NAME ,MANDATE_REQ_TRAN_ID ,CRED_PHONE_NR ,CRED_EMAIL ,CRED_ACCOUNT_NO ,CRED_BRANCH_NO ,ULT_CREDITOR_NAME ");
			sbMrkoffDtls.append(",CRED_ABBREV_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL ,DEBT_ACCOUNT_NO ,DEBT_ACCOUNT_TYPE ");
			sbMrkoffDtls.append(",DEBT_BRANCH_NO ,ULT_DEBTOR_NAME ,AUTH_STATUS_IND ,AUTH_TYPE ,COLL_DAY ,DATE_ADJ_RULE_IND ,ADJUSTMENT_CAT ");
			sbMrkoffDtls.append(",ADJUSTMENT_RATE ,ADJ_AMT_CURR ,ADJUSTMENT_AMT ,AUTHENT_CHANNEL ,MANDATE_REF_NR ,SUPPL_COLL_CURR ,SUPPL_FRST_COLL_AMT ");
			sbMrkoffDtls.append(",DEBIT_VALUE_TYPE ,MANDATE_AUTH_DATE ,TRAN_STATUS ,MAC ,REQ_TRAN_NO ,DEBT_BANK_MEMBER_NO ,CRED_BANK_MEMBER_NO ,CREATED_BY,CREATED_DATE,ARCHIVE_DATE ,MODIFIED_BY ,MODIFIED_DATE,EXTRACT_FILE_NAME,INCOMING_FILE_NAME,EXTRACT_MSG_ID) ");
			sbMrkoffDtls.append("SELECT MSG_ID ,ACCEPTED ,REJECT_REASON_CODE ,MANDATE_ID ,MANDATE_REQ_ID ,TRACKING_IND ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ");
			sbMrkoffDtls.append(",FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_COLL_AMT_CURR ,MAX_COLL_AMT ,CRED_SCHEME_ID ");
			sbMrkoffDtls.append(",CRED_NAME ,MANDATE_REQ_TRAN_ID ,CRED_PHONE_NR ,CRED_EMAIL ,CRED_ACCOUNT_NO ,CRED_BRANCH_NO ,ULT_CREDITOR_NAME ");
			sbMrkoffDtls.append(",CRED_ABBREV_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL ,DEBT_ACCOUNT_NO ,DEBT_ACCOUNT_TYPE  ");
			sbMrkoffDtls.append(",DEBT_BRANCH_NO ,ULT_DEBTOR_NAME ,AUTH_STATUS_IND ,AUTH_TYPE ,COLL_DAY ,DATE_ADJ_RULE_IND ,ADJUSTMENT_CAT ");
			sbMrkoffDtls.append(",ADJUSTMENT_RATE ,ADJ_AMT_CURR ,ADJUSTMENT_AMT ,AUTHENT_CHANNEL ,MANDATE_REF_NR ,SUPPL_COLL_CURR ,SUPPL_FRST_COLL_AMT ");
			sbMrkoffDtls.append(",DEBIT_VALUE_TYPE ,MANDATE_AUTH_DATE ,TRAN_STATUS ,MAC ,REQ_TRAN_NO ,DEBT_BANK_MEMBER_NO ,CRED_BANK_MEMBER_NO ,CREATED_BY ,CREATED_DATE ,TRUNC(SYSDATE) ,MODIFIED_BY ,MODIFIED_DATE,EXTRACT_FILE_NAME,INCOMING_FILE_NAME,EXTRACT_MSG_ID ");
			sbMrkoffDtls.append("FROM CAMOWNER.MDT_AC_OPS_MRKOFF_ACCEPT_DET ");
			sbMrkoffDtls.append("WHERE TRAN_STATUS = '4' ");
			try
			{
				String mrkoffDtlsSQL = sbMrkoffDtls.toString();
				log.debug("confDtlsSQL: " + mrkoffDtlsSQL);
				genericDAO.executeNativeSQL(mrkoffDtlsSQL);
				mrkoffDtlsBool = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Archive MarkOff Details:- "+ex.getMessage());
				ex.printStackTrace();
				mrkoffDtlsBool = false;
			}
		}

		if(mrkoffHdrsBool && mrkoffDtlsBool)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean deleteMarkOff()
	{
		boolean delMrkOffHdrs = false, delMrkOffDtls = false;

		log.info("===============DELETING OPS MarkOff HDRS===============");

		try
		{
			StringBuffer sbDelMrkHdrs = new StringBuffer();

			sbDelMrkHdrs.append("DELETE FROM CAMOWNER.MDT_AC_OPS_MRKOFF_GRP_HDR a ");
			sbDelMrkHdrs.append("where (a.MSG_ID) in ");
			sbDelMrkHdrs.append("(select b.MSG_ID FROM CAMOWNER.MDT_AC_OPS_MRKOFF_ACCEPT_DET b WHERE b.TRAN_STATUS = '4') ");

			String delMrkOffHdsSQL = sbDelMrkHdrs.toString();
			log.debug("delConHdsSQL: " + delMrkOffHdsSQL);
			genericDAO.executeNativeSQL(delMrkOffHdsSQL);
			delMrkOffHdrs = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS MarkOff HDRS:- "+ex.getMessage());
			ex.printStackTrace();
			delMrkOffHdrs = false;
		}

		if(delMrkOffHdrs) {
			log.info("===============DELETING OPS MarkOff DETAILS===============");
			try
			{
				String delMrkDtlStr = "DELETE FROM CAMOWNER.MDT_AC_OPS_MRKOFF_ACCEPT_DET b WHERE b.TRAN_STATUS = '4' ";
				log.debug("delCDtlStr: " + delMrkDtlStr);
				genericDAO.executeNativeSQL(delMrkDtlStr);
				delMrkOffDtls = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Deleting OPS MarkOff DETAILS:- "+ex.getMessage());
				ex.printStackTrace();
				delMrkOffDtls = false;
			}
		}

		if(delMrkOffHdrs && delMrkOffDtls)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean archiveSuspensionsBySQL(String archiveType, String expiredDate, String archDate)
	{
		log.info("Archive Type===> "+archiveType);
		boolean suspHdrsBool = false, suspDtlsBool = false;

		log.info("===============ARCHIVING SUSPENSION HDRS===============");
		StringBuffer sbSuspHdrs = new StringBuffer();

		sbSuspHdrs.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUSP_GRP_HDR ");
		sbSuspHdrs.append("(ASSIGNMENT_ID ,ASSIGNER ,ASSIGNEE ,CREATE_DATE_TIME ,ARCHIVE_DATE) ");
		sbSuspHdrs.append("SELECT DISTINCT nvl(b.ASSIGNMENT_ID,'NF') as assgnid ,b.ASSIGNER ,b.ASSIGNEE ,b.CREATE_DATE_TIME ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbSuspHdrs.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG a ");
		sbSuspHdrs.append("LEFT OUTER JOIN CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR b ON A.ASSIGNMENT_ID = B.ASSIGNMENT_ID ");
		switch(archiveType)
		{
		case "MATCH":  sbSuspHdrs.append("WHERE a.PROCESS_STATUS IN ('R', 'M') and nvl(b.ASSIGNMENT_ID,'NF') <> 'NF' ");
		break;
		case "EXPIRE": sbSuspHdrs.append("WHERE a.PROCESS_STATUS = '4' and nvl(b.ASSIGNMENT_ID,'NF') <> 'NF'  AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
		break;
		}

		try
		{
			String suspHdrsSQL = sbSuspHdrs.toString();
			log.debug("suspHdrsSQL: " + suspHdrsSQL);
			genericDAO.executeNativeSQL(suspHdrsSQL);
			suspHdrsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Suspensions Hdrs:- "+ex.getMessage());
			ex.printStackTrace();
			suspHdrsBool = false;
		}

		if(suspHdrsBool)
		{
			log.info("===============ARCHIVING SUSPENSIONS DETAILS===============");
			StringBuffer sbSuspDtls = new StringBuffer();

			sbSuspDtls.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUSP_MSG ");
			sbSuspDtls.append("(MANDATE_SUSP_ID ,ORIGINAL_PMT_ID ,ASSIGNMENT_ID ,REASON_CODE ,MANDATE_REF_NR ,CREDITOR_BANK ,MANDATE_REQ_TRAN_ID "); 
			sbSuspDtls.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,IN_FILE_NAME ,EXTRACT_FILE_NAME ,ARCHIVE_DATE) ");
			sbSuspDtls.append("SELECT MANDATE_SUSP_ID ,ORIGINAL_PMT_ID ,ASSIGNMENT_ID ,REASON_CODE ,MANDATE_REF_NR ,CREDITOR_BANK ,MANDATE_REQ_TRAN_ID "); 
			sbSuspDtls.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,IN_FILE_NAME ,EXTRACT_FILE_NAME ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");

			switch(archiveType)
			{
			case "MATCH":  sbSuspDtls.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG WHERE PROCESS_STATUS IN ('R', 'M') ");
			break;
			case "EXPIRE": sbSuspDtls.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG WHERE PROCESS_STATUS = '4' AND TRUNC(CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
			break;
			}

			try
			{
				String suspDtlsSQL = sbSuspDtls.toString();
				log.debug("suspDtlsSQL: " + suspDtlsSQL);
				genericDAO.executeNativeSQL(suspDtlsSQL);
				suspDtlsBool = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Archive Suspension Details:- "+ex.getMessage());
				ex.printStackTrace();
				suspDtlsBool = false;
			}
		}

		if(suspHdrsBool && suspDtlsBool)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean deleteSuspensions(String archiveType, String expiredDate)
	{
		boolean delSuspHdrs = false, delSuspDtls = false;

		log.info("===============DELETING OPS SUSPENSIONS HDRS===============");

		try
		{
			StringBuffer sbDelSuspHdrs = new StringBuffer();

			sbDelSuspHdrs.append("delete from CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR b ");
			sbDelSuspHdrs.append("WHERE (B.ASSIGNMENT_ID) IN ");
			sbDelSuspHdrs.append("(SELECT A.ASSIGNMENT_ID FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG a ");
			switch(archiveType)
			{
			case "MATCH":  sbDelSuspHdrs.append("WHERE a.PROCESS_STATUS IN ('R', 'M')) ");
			break;
			case "EXPIRE": sbDelSuspHdrs.append("WHERE a.PROCESS_STATUS = '4' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD')) "); 
			break;
			}

			String delSuspHdsSQL = sbDelSuspHdrs.toString();
			log.debug("delSuspHdsSQL: " + delSuspHdsSQL);
			genericDAO.executeNativeSQL(delSuspHdsSQL);
			delSuspHdrs = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS SUSP HDRS:- "+ex.getMessage());
			ex.printStackTrace();
			delSuspHdrs = false;
		}

		if(delSuspHdrs) {
			log.info("===============DELETING OPS SUSP DETAILS===============");
			try
			{
				StringBuffer sbDelSuspDtls = new StringBuffer();
				sbDelSuspDtls.append("delete from CAMOWNER.MDT_AC_OPS_SUSP_MSG a ");
				switch(archiveType)
				{
				case "MATCH":  sbDelSuspDtls.append("WHERE a.PROCESS_STATUS IN ('R', 'M') ");
				break;
				case "EXPIRE": sbDelSuspDtls.append("WHERE a.PROCESS_STATUS = '4' AND TRUNC(a.CREATED_DATE) = TO_DATE('"+expiredDate+"','YYYY-MM-DD') "); 
				break;
				}
				String delSuspDtlsSQL = sbDelSuspDtls.toString();
				log.debug("delSuspDtlsSQL: " + delSuspDtlsSQL.toString());
				genericDAO.executeNativeSQL(delSuspDtlsSQL);
				delSuspDtls = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Deleting OPS SUSP DETAILS:- "+ex.getMessage());
				ex.printStackTrace();
				delSuspDtls = false;
			}
		}

		if(delSuspHdrs && delSuspDtls)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean archiveStatusReportsBySQL(String archDate)
	{
		boolean statusHdrsBool = false, statusDtlsBool = false;

		log.info("===============ARCHIVING STATUS DETAILS===============");
		StringBuffer sbStatusDtls = new StringBuffer();

		sbStatusDtls.append("INSERT INTO CAMOWNER.CAS_ARC_STATUS_DETAILS ");
		sbStatusDtls.append("(SYSTEM_SEQ_NO ,STATUS_HDR_SEQ_NO ,ERROR_CODE ,TXN_ID ,END_TO_END_ID ,TXN_STATUS ,ERROR_TYPE ,RECORD_ID ,ORGNL_TXN_SEQ_NO "); 
		sbStatusDtls.append(",MANDATE_REF_NUMBER ,INST_ID ,PROCESS_STATUS ,DEBTOR_BRANCH_NO ,CR_ABB_SHORT_NAME ,ARCHIVE_DATE)");
		sbStatusDtls.append("SELECT a.SYSTEM_SEQ_NO ,a.STATUS_HDR_SEQ_NO ,a.ERROR_CODE ,a.TXN_ID ,a.END_TO_END_ID ,a.TXN_STATUS ,a.ERROR_TYPE ,a.RECORD_ID ,a.ORGNL_TXN_SEQ_NO ");
		sbStatusDtls.append(",a.MANDATE_REF_NUMBER ,a.INST_ID ,a.PROCESS_STATUS ,a.DEBTOR_BRANCH_NO ,a.CR_ABB_SHORT_NAME ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbStatusDtls.append("FROM CAMOWNER.CAS_OPS_STATUS_DETAILS a ");
		sbStatusDtls.append("LEFT OUTER JOIN CAMOWNER.CAS_OPS_STATUS_HDRS b ON a.STATUS_HDR_SEQ_NO = b.SYSTEM_SEQ_NO ");
		sbStatusDtls.append("WHERE b.PROCESS_STATUS IN ('7') ");


		try
		{
			String statusDtlsSQL = sbStatusDtls.toString();
			log.debug("statusDtlsSQL: " + statusDtlsSQL);
			genericDAO.executeNativeSQL(statusDtlsSQL);
			statusDtlsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Status Details:- "+ex.getMessage());
			ex.printStackTrace();
			statusDtlsBool = false;
		}

		if(statusDtlsBool)
		{
			log.info("===============ARCHIVING STATUS HEADERS===============");
			StringBuffer sbStatusHdrs = new StringBuffer();

			sbStatusHdrs.append("INSERT INTO CAMOWNER.CAS_ARC_STATUS_HDRS ");
			sbStatusHdrs.append("(SYSTEM_SEQ_NO ,HDR_MSG_ID ,CREATE_DATE_TIME ,INSTG_AGT ,INSTD_AGT ,ORGNL_MSG_ID ,ORGNL_MSG_NAME ,ORGNL_CREATE_DATE_TIME "); 
			sbStatusHdrs.append(",ORGNL_NO_OF_TXNS ,ORGNL_CNTL_SUM ,PROCESS_STATUS ,GROUP_STATUS ,SERVICE ,VET_RUN_NUMBER ,WORKUNIT_REF_NO ,ORGNL_FILE_NAME ,EXTRACT_FILE_NAME ,ARCHIVE_DATE) ");
			sbStatusHdrs.append("SELECT SYSTEM_SEQ_NO ,HDR_MSG_ID ,CREATE_DATE_TIME ,INSTG_AGT ,INSTD_AGT ,ORGNL_MSG_ID ,ORGNL_MSG_NAME ,ORGNL_CREATE_DATE_TIME "); 
			sbStatusHdrs.append(",ORGNL_NO_OF_TXNS ,ORGNL_CNTL_SUM ,PROCESS_STATUS ,GROUP_STATUS ,SERVICE ,VET_RUN_NUMBER ,WORKUNIT_REF_NO ,ORGNL_FILE_NAME ,EXTRACT_FILE_NAME ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
			sbStatusHdrs.append("FROM CAMOWNER.CAS_OPS_STATUS_HDRS WHERE PROCESS_STATUS = '7' ");

			try
			{
				String statusHdrsSQL = sbStatusHdrs.toString();
				log.debug("statusHdrsSQL: " + statusHdrsSQL);
				genericDAO.executeNativeSQL(statusHdrsSQL);
				statusHdrsBool = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Archive Status Headers:- "+ex.getMessage());
				ex.printStackTrace();
				statusHdrsBool = false;
			}
		}

		if(statusDtlsBool && statusHdrsBool)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean deleteStatusReports()
	{
		boolean delStatusHdrs = false, delStatusDtls = false;

		log.info("===============DELETING OPS STATUS DTLS===============");

		try
		{
			StringBuffer sbDelStatusDtls = new StringBuffer();

			sbDelStatusDtls.append("delete FROM CAMOWNER.CAS_OPS_STATUS_DETAILS a ");
			sbDelStatusDtls.append("where (a.STATUS_HDR_SEQ_NO) in ");
			sbDelStatusDtls.append("(select b.SYSTEM_SEQ_NO FROM CAMOWNER.CAS_OPS_STATUS_HDRS b WHERE b.PROCESS_STATUS = '7') ");

			String delStatusDtlsSQL = sbDelStatusDtls.toString();
			log.debug("delStatusDtlsSQL: " + delStatusDtlsSQL);
			genericDAO.executeNativeSQL(delStatusDtlsSQL);
			delStatusDtls = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS STATUS DETAILS:- "+ex.getMessage());
			ex.printStackTrace();
			delStatusDtls = false;
		}

		if(delStatusDtls) {
			log.info("===============DELETING OPS STATUS HDRS===============");
			try
			{
				String delStatusHdrsSQL = "DELETE FROM CAMOWNER.CAS_OPS_STATUS_HDRS WHERE PROCESS_STATUS = '7'";
				log.debug("delStatusHdrsSQL: " + delStatusHdrsSQL);
				genericDAO.executeNativeSQL(delStatusHdrsSQL);
				delStatusHdrs = true;
			}
			catch(Exception ex)
			{
				log.error("Error on Deleting OPS STATUS HDRS:- "+ex.getMessage());
				ex.printStackTrace();
				delStatusHdrs = false;
			}
		}

		if(delStatusDtls && delStatusHdrs)
		{
			return true;
		}
		else {
			return false;
		}
	}

	public boolean archiveMandateCountsBySQL()
	{
		boolean mndtCountsBool = false;

		StringBuffer sbMndtCounts = new StringBuffer();

		sbMndtCounts.append("INSERT INTO CAMOWNER.CAS_ARC_MNDT_COUNT ");
		sbMndtCounts.append("(INST_ID ,SERVICE_ID ,NR_OF_FILES ,NR_OF_MSGS ,PROCESS_DATE ,INCOMING ,OUTGOING ,MSG_ID ,NR_MSGS_REJECTED ");
		sbMndtCounts.append(",FILE_NAME ,NR_MSGS_ACCEPTED ,NR_MSGS_EXTRACTED) ");
		sbMndtCounts.append("SELECT INST_ID ,SERVICE_ID ,NR_OF_FILES ,NR_OF_MSGS ,PROCESS_DATE ,INCOMING ,OUTGOING ,MSG_ID ,NR_MSGS_REJECTED ");
		sbMndtCounts.append(",FILE_NAME ,NR_MSGS_ACCEPTED ,NR_MSGS_EXTRACTED ");
		sbMndtCounts.append("FROM CAMOWNER.CAS_OPS_MNDT_COUNT ");

		try
		{
			String mndtCountSQL = sbMndtCounts.toString();
			log.debug("mndtCountSQL: " + mndtCountSQL);
			genericDAO.executeNativeSQL(mndtCountSQL);
			mndtCountsBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Mandate Counts:- "+ex.getMessage());
			ex.printStackTrace();
			mndtCountsBool = false;
		}
		return mndtCountsBool;
	}

	public boolean deleteMandateCounts()
	{
		boolean mndtCountBool = false;

		try
		{
			String mndtCountDel = "DELETE FROM CAMOWNER.CAS_OPS_MNDT_COUNT ";
			log.debug("mndtCountDel: " + mndtCountDel);
			genericDAO.executeNativeSQL(mndtCountDel);
			mndtCountBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Mandate Counts:- "+ex.getMessage());
			ex.printStackTrace();
			mndtCountBool = false;
		}
		return mndtCountBool;
	}

	public boolean archiveFileRegBySQL(String archDate)
	{
		boolean fileRegBool = false;

		StringBuffer sbFileReg = new StringBuffer();

		sbFileReg.append("INSERT INTO CAMOWNER.CAS_ARC_FILE_REG ");
		sbFileReg.append("(FILE_NAME ,FILEPATH ,STATUS ,REASON ,PROCESS_DATE ,NAME_SPACE ,GRP_HDR_MSG_ID ,ONLINE_IND ,IN_OUT_IND ,EXTRACT_MSG_ID ,ARCHIVE_DATE) ");
		sbFileReg.append("SELECT FILE_NAME ,FILEPATH ,STATUS ,REASON ,PROCESS_DATE ,NAME_SPACE ,GRP_HDR_MSG_ID ,ONLINE_IND ,IN_OUT_IND ,EXTRACT_MSG_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbFileReg.append("FROM CAMOWNER.CAS_OPS_FILE_REG ");

		try
		{
			String fileRegSQL = sbFileReg.toString();
			log.debug("fileRegSQL: " + fileRegSQL);
			genericDAO.executeNativeSQL(fileRegSQL);
			fileRegBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive File Register:- "+ex.getMessage());
			ex.printStackTrace();
			fileRegBool = false;
		}
		return fileRegBool;
	}

	public boolean deleteFileReg()
	{
		boolean fileRegBool = false;

		try
		{
			String fileRegDel = "DELETE FROM CAMOWNER.CAS_OPS_FILE_REG ";
			log.debug("fileRegDel: " + fileRegDel);
			genericDAO.executeNativeSQL(fileRegDel);
			fileRegBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting File Reg:- "+ex.getMessage());
			ex.printStackTrace();
			fileRegBool = false;
		}
		return fileRegBool;
	}

	public boolean archiveMdteRequestsBySQL(String archDate)
	{
		boolean mdteReqBool = false;

		StringBuffer sbMdteReq = new StringBuffer();
		sbMdteReq.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MDTE_REQUEST ");
		sbMdteReq.append("(MDT_INF_REQ_ID ,INSTR_AGENT ,INSTD_AGENT ,REQUEST_TYPE ,CREDITOR_BANK ,CR_ABB_SHORT_NAME ,DEBTOR_BANK ,MANDATE_REF_NR ,MDTE_MSG_ID "); 
		sbMdteReq.append(",MDTE_RESP_IND ,PROCESS_STATUS ,ARCHIVE_DATE ,EXTRACT_MSG_ID ,IN_FILE_NAME ,EXTRACT_FILE_NAME) ");
		sbMdteReq.append("SELECT MDT_INF_REQ_ID ,INSTR_AGENT ,INSTD_AGENT ,REQUEST_TYPE ,CREDITOR_BANK ,CR_ABB_SHORT_NAME ,DEBTOR_BANK ,MANDATE_REF_NR ,MDTE_MSG_ID "); 
		sbMdteReq.append(",MDTE_RESP_IND ,PROCESS_STATUS ,TO_DATE('"+archDate+"','YYYY-MM-DD') ,EXTRACT_MSG_ID ,IN_FILE_NAME ,EXTRACT_FILE_NAME ");
		sbMdteReq.append("FROM CAMOWNER.MDT_AC_OPS_MDTE_REQUEST WHERE PROCESS_STATUS = '4' ");

		try
		{
			String mdtReqSQL = sbMdteReq.toString();
			log.debug("mdtReqSQL: " + mdtReqSQL);
			genericDAO.executeNativeSQL(mdtReqSQL);
			mdteReqBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Mdte Requests:- "+ex.getMessage());
			ex.printStackTrace();
			mdteReqBool = false;
		}
		return mdteReqBool;
	}

	public boolean deleteMdteRequests()
	{
		boolean mdteReqBool = false;

		try
		{
			String mdteReqDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_MDTE_REQUEST WHERE PROCESS_STATUS = '4' ";
			log.debug("mdteReqDel: " + mdteReqDel);
			genericDAO.executeNativeSQL(mdteReqDel);
			mdteReqBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Mdte Req:- "+ex.getMessage());
			ex.printStackTrace();
			mdteReqBool = false;
		}
		return mdteReqBool;
	}

	public boolean archiveMdteResponsesBySQL(String archDate)
	{
		boolean mdteRespBool = false;

		StringBuffer sbMdteResp = new StringBuffer();
		//2020/08/25: SalehaR - Replace SQL with FLAT table SQL
		//		sbMdteResp.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MDTE_RESP ");
		//		sbMdteResp.append("(MDT_INF_REQ_ID ,INSTR_AGENT ,INSTD_AGENT ,REJECT_REASON ,CREATE_DATE_TIME ,MDTE_MSG_ID ,PROCESS_STATUS ,ACCPT_IND ");
		//		sbMdteResp.append(",MDT_STATUS ,CR_BANK_MEMBER_ID ,DR_BANK_MEMBER_ID ,EXTRACT_MSG_ID ,ARCHIVE_DATE) ");
		//		sbMdteResp.append("SELECT MDT_INF_REQ_ID ,INSTR_AGENT ,INSTD_AGENT ,REJECT_REASON ,CREATE_DATE_TIME ,MDTE_MSG_ID ,PROCESS_STATUS ,ACCPT_IND "); 
		//		sbMdteResp.append(",MDT_STATUS ,CR_BANK_MEMBER_ID ,DR_BANK_MEMBER_ID ,EXTRACT_MSG_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		//		sbMdteResp.append("FROM CAMOWNER.MDT_AC_OPS_MDTE_RESP WHERE PROCESS_STATUS = '9' ");

		sbMdteResp.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MDTE_RSP_TXNS ");
		sbMdteResp.append("(MSG_ID ,MDT_INF_REQ_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,ACCEPTED_IND ");       
		sbMdteResp.append(",RESP_REASON_CODE ,MANDATE_STATUS ,MANDATE_ID ,CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ");   
		sbMdteResp.append(",COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,CRED_SCHEME_ID ,CREDITOR_NAME ,MANDATE_REQ_TRAN_ID ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ");      
		sbMdteResp.append(",CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ,DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ");
		sbMdteResp.append(",DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,AUTH_CHANNEL ,MANDATE_REF_NR ,FIRST_COLL_AMT_CURR ,FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,MANDATE_AUTH_DATE ");  
		sbMdteResp.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,ARCHIVE_DATE) ");
		sbMdteResp.append("SELECT MSG_ID ,MDT_INF_REQ_ID ,CREDITOR_BANK ,DEBTOR_BANK ,SERVICE_ID ,PROCESS_STATUS ,IN_FILE_NAME ,IN_FILE_DATE ,EXTRACT_MSG_ID ,EXTRACT_FILE_NAME ,ACCEPTED_IND ");       
		sbMdteResp.append(",RESP_REASON_CODE ,MANDATE_STATUS ,MANDATE_ID ,CONTRACT_REF ,SERVICE_LEVEL ,LOCAL_INSTR_CD ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLL_AMOUNT_CURR ");
		sbMdteResp.append(",COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,CRED_SCHEME_ID ,CREDITOR_NAME ,MANDATE_REQ_TRAN_ID ,CRED_PHONE_NR ,CRED_EMAIL_ADDR ,CRED_ACC_NUM ,CRED_BRANCH_NR ,ULT_CRED_NAME ");      
		sbMdteResp.append(",CRED_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DEBT_PHONE_NR ,DEBT_EMAIL_ADDR ,DEBT_ACC_NUM ,DEBT_ACC_TYPE ,DEBT_BRANCH_NR ,ULT_DEBT_NAME ,AUTH_TYPE ,COLLECTION_DAY ");
		sbMdteResp.append(",DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJ_RATE ,ADJ_AMOUNT_CURR ,ADJ_AMOUNT ,AUTH_CHANNEL ,MANDATE_REF_NR ,FIRST_COLL_AMT_CURR ,FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,MANDATE_AUTH_DATE ");  
		sbMdteResp.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbMdteResp.append("FROM CAMOWNER.MDT_AC_OPS_MDTE_RSP_TXNS WHERE PROCESS_STATUS = '4' ");

		try
		{
			String mdtRespSQL = sbMdteResp.toString();
			log.info("mdtRespSQL: " + mdtRespSQL);
			genericDAO.executeNativeSQL(mdtRespSQL);
			mdteRespBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Mdte Response:- "+ex.getMessage());
			ex.printStackTrace();
			mdteRespBool = false;
		}
		return mdteRespBool;
	}

	public boolean deleteMdteResponses()
	{
		boolean mdteRespBool = false;

		try
		{
//			String mdteRespDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_MDTE_RESP WHERE PROCESS_STATUS = '9' ";
			String mdteRespDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_MDTE_RSP_TXNS WHERE PROCESS_STATUS = '4' ";
			log.debug("mdteRespDel: " + mdteRespDel);
			genericDAO.executeNativeSQL(mdteRespDel);
			mdteRespBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Mdte Resp:- "+ex.getMessage());
			ex.printStackTrace();
			mdteRespBool = false;
		}
		return mdteRespBool;
	}

	public boolean archiveMdteRespMsgsBySQL(String archDate)
	{
		boolean mdteRespMsgBool = false;

		StringBuffer sbMdteRespMsg = new StringBuffer();

		sbMdteRespMsg.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MDTE_RESP_MSG ");
		sbMdteRespMsg.append("(MSG_ID ,MANDATE_REQ_TRAN_ID ,MDT_INF_REQ_ID ,CREDITOR_BANK ,DEBTOR_BANK ,ACCEPTED_IND ,RESP_REASON ,MANDATE_STATUS ,MANDATE_ID ,MANDATE_REQ_ID "); 
		sbMdteRespMsg.append(",TRACKING_IND ,LOCAL_INST_CODE ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLLECTION_CURR ,COLLECTION_AMT ,MAX_AMT_CURR ,MAX_AMT ");
		sbMdteRespMsg.append(",CR_SCHEME_ID ,CR_NAME ,CR_TEL_NO ,CR_EMAIL ,CR_ACC_NO ,CR_BRANCH_NO ,ULT_CR_NAME ,CR_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DR_TEL_NO ");
		sbMdteRespMsg.append(",DR_EMAIL ,DR_ACC_NO ,DR_ACC_TYPE ,DR_BRANCH_NO ,ULT_DR_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJUSTMENT_RATE ");
		sbMdteRespMsg.append(",ADJ_AMT_CURR ,ADJ_AMT ,AUTH_CHANNEL ,MNDT_REF_NUM ,FIRST_COLL_AMT_CURR ,FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ");
		sbMdteRespMsg.append(",MANDATE_AUTH_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,ARCHIVE_DATE) ");
		sbMdteRespMsg.append("SELECT MSG_ID ,MANDATE_REQ_TRAN_ID ,MDT_INF_REQ_ID ,CREDITOR_BANK ,DEBTOR_BANK ,ACCEPTED_IND ,RESP_REASON ,MANDATE_STATUS ,MANDATE_ID ,MANDATE_REQ_ID "); 
		sbMdteRespMsg.append(",TRACKING_IND ,LOCAL_INST_CODE ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,FIRST_COLL_DATE ,COLLECTION_CURR ,COLLECTION_AMT ,MAX_AMT_CURR ,MAX_AMT ");
		sbMdteRespMsg.append(",CR_SCHEME_ID ,CR_NAME ,CR_TEL_NO ,CR_EMAIL ,CR_ACC_NO ,CR_BRANCH_NO ,ULT_CR_NAME ,CR_ABB_SHORT_NAME ,DEBTOR_NAME ,DEBTOR_ID ,DR_TEL_NO ,DR_EMAIL ");
		sbMdteRespMsg.append(",DR_ACC_NO ,DR_ACC_TYPE ,DR_BRANCH_NO ,ULT_DR_NAME ,AUTH_TYPE ,COLLECTION_DAY ,DATE_ADJ_RULE_IND ,ADJ_CATEGORY ,ADJUSTMENT_RATE ,ADJ_AMT_CURR ");
		sbMdteRespMsg.append(",ADJ_AMT ,AUTH_CHANNEL ,MNDT_REF_NUM ,FIRST_COLL_AMT_CURR ,FIRST_COLL_AMT ,DEBIT_VALUE_TYPE ,MANDATE_AUTH_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbMdteRespMsg.append("FROM CAMOWNER.MDT_AC_OPS_MDTE_RESP_MSG WHERE PROCESS_STATUS = '4' ");

		try
		{
			String mdtRespMsgSQL = sbMdteRespMsg.toString();
			log.debug("mdtRespMsgSQL: " + mdtRespMsgSQL);
			genericDAO.executeNativeSQL(mdtRespMsgSQL);
			mdteRespMsgBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Mdte Response Msg:- "+ex.getMessage());
			ex.printStackTrace();
			mdteRespMsgBool = false;
		}
		return mdteRespMsgBool;
	}

	public boolean deleteMdteRespMsg()
	{
		boolean mdteRespMsgBool = false;

		try
		{
			String mdteRespMsgDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_MDTE_RESP_MSG WHERE PROCESS_STATUS = '4' ";
			log.debug("mdteRespMsgDel: " + mdteRespMsgDel);
			genericDAO.executeNativeSQL(mdteRespMsgDel);
			mdteRespMsgBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Mdte Resp Msg:- "+ex.getMessage());
			ex.printStackTrace();
			mdteRespMsgBool = false;
		}
		return mdteRespMsgBool;
	}


	public boolean archiveLeftOverTxnsBySQL(String expiredDate, String archDate)
	{
		boolean leftOverBool = false;

		log.info("===============ARCHIVING GROUP HEADER EXCESS TXNS===============");
		StringBuffer leftGHMsg = new StringBuffer();
		leftGHMsg.append("INSERT INTO CAMOWNER.MDT_AC_ARC_GRP_HDR ");
		leftGHMsg.append("(MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,ARCHIVE_DATE) ");
		leftGHMsg.append("SELECT MSG_ID ,CREATE_DATE_TIME ,AUTH_CODE ,CREATED_BY ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		leftGHMsg.append("FROM CAMOWNER.MDT_AC_OPS_GRP_HDR ");
		leftGHMsg.append("WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String gHleftSQL = leftGHMsg.toString();
			log.debug("gHleftSQL: " + gHleftSQL);
			genericDAO.executeNativeSQL(gHleftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Group Hdr Excess:- "+ex.getMessage());
			ex.printStackTrace();
		}

		//Mandate Message
		log.info("===============ARCHIVING MANDATES EXCESS TXNS===============");
		StringBuffer sbMandate = new StringBuffer();
		sbMandate.append("INSERT INTO CAMOWNER.MDT_AC_ARC_MNDT_MSG ");
		sbMandate.append("(MSG_ID ,MANDATE_ID ,MANDATE_REQ_ID ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,TO_DATE ,FIRST_COLL_DATE ,FINAL_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ,LOCAL_INSTR_CD "); 
		sbMandate.append(",SERVICE_LEVEL ,STATUS ,ACTIVE_IND ,ACTIVE_IND_CHANGE_DATE ,FILE_NAME ,FILE_DATE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,MOD_REASON ,ORGNL_MSG_ID ,ORGNL_MSG_NAME_ID "); 
		sbMandate.append(",ORGNL_MSG_CREATE_DATE_TIME ,AMEND_REASON_CODE ,AMEND_REASON_DESC ,ORGNL_MDT_REQ_ID ,TRACKING_IND ,ACCEPTED ,REJECT_REASON_CODE ,ADD_REJECT_RSN_INF ,ORIG_MANDATE_ID ,PROCESS_IND ,CONTENTS ");                 
		sbMandate.append(",SYS_GEN_SEQ_NR ,MDT_INF_REQ_ID ,RECORD_TYPE ,EXTRACT_MSG_ID ,SERVICE_ID ,ONLINE_IND ,MANDATE_REQ_TRAN_ID ,MANDATE_REF_NR ,ARCHIVE_DATE) ");
		sbMandate.append("SELECT MSG_ID ,MANDATE_ID ,MANDATE_REQ_ID ,SEQUENCE_TYPE ,FREQUENCY ,FROM_DATE ,TO_DATE ,FIRST_COLL_DATE ,FINAL_COLL_DATE ,COLL_CURRENCY ,COLL_AMOUNT ,MAX_AMOUNT_CURR ,MAX_AMOUNT ");
		sbMandate.append(",LOCAL_INSTR_CD ,SERVICE_LEVEL ,STATUS ,ACTIVE_IND ,ACTIVE_IND_CHANGE_DATE ,FILE_NAME ,FILE_DATE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,MOD_REASON ,ORGNL_MSG_ID ");
		sbMandate.append(",ORGNL_MSG_NAME_ID ,ORGNL_MSG_CREATE_DATE_TIME ,AMEND_REASON_CODE ,AMEND_REASON_DESC ,ORGNL_MDT_REQ_ID ,TRACKING_IND ,ACCEPTED ,REJECT_REASON_CODE ,ADD_REJECT_RSN_INF ,ORIG_MANDATE_ID,PROCESS_IND ");              
		sbMandate.append(",CONTENTS ,SYS_GEN_SEQ_NR ,MDT_INF_REQ_ID ,RECORD_TYPE ,EXTRACT_MSG_ID ,SERVICE_ID ,ONLINE_IND ,MANDATE_REQ_TRAN_ID ,MANDATE_REF_NR ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbMandate.append("FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG ");
		sbMandate.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String mandLeftSQL = sbMandate.toString();
			log.debug("mandLeftSQL: " + mandLeftSQL);
			genericDAO.executeNativeSQL(mandLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Mandates LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING CASH ACCOUNT EXCESS TXNS===============");
		StringBuffer sbCashAcc = new StringBuffer();
		sbCashAcc.append("INSERT INTO CAMOWNER.MDT_AC_ARC_CASH_ACCOUNT ");
		sbCashAcc.append("(ACCOUNT_NAME ,ACCOUNT_NUMBER ,ACCOUNT_TYPE ,CREATED_BY ,CREATED_DATE ,CURRENCY ,MODIFIED_BY ,MODIFIED_DATE ,PARTY_IDENT_TYPE_ID ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbCashAcc.append("SELECT ACCOUNT_NAME ,ACCOUNT_NUMBER ,ACCOUNT_TYPE ,CREATED_BY ,CREATED_DATE ,CURRENCY ,MODIFIED_BY ,MODIFIED_DATE ,PARTY_IDENT_TYPE_ID ,MSG_ID ");
		sbCashAcc.append(" ,MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbCashAcc.append("FROM CAMOWNER.MDT_AC_OPS_CASH_ACCOUNT ");
		sbCashAcc.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String cashAccLeftSQL = sbCashAcc.toString();
			log.debug("cashAccLeftSQL: " + cashAccLeftSQL);
			genericDAO.executeNativeSQL(cashAccLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Cash Acc LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING FIN INST EXCESS TXNS===============");
		StringBuffer sbFinInst = new StringBuffer();
		sbFinInst.append("INSERT INTO CAMOWNER.MDT_AC_ARC_FIN_INST ");
		sbFinInst.append("(INST_ID ,BR_ADDR_LINE ,BR_ADDR_TYPE ,BR_BUILD_NUMBER ,BR_COUNTRY ,BR_COUNTRY_SUB_DIV ,BR_DEPT ,BR_POST_CODE ,BR_STREET_NAME ,BR_SUB_DEPT ,BR_TOWN_NAME ,BRANCH_ID ,BRANCH_NAME "); 
		sbFinInst.append(",CREATED_BY ,CREATED_DATE ,FI_ADDR_LINE ,FI_ADDR_TYPE ,FI_BUILD_NUMBER ,FI_COUNTRY ,FI_COUNTRY_SUB_DIV ,FI_DEPT ,FI_ID ,FI_NAME ,FI_POST_CODE ,FI_STREET_NAME ,FI_SUB_DEPT ");
		sbFinInst.append(",FI_TOWN_NAME ,FIN_INST_TYPE_ID ,ISSUER ,MEMBER_ID ,MODIFIED_BY ,MODIFIED_DATE ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbFinInst.append("SELECT INST_ID ,BR_ADDR_LINE ,BR_ADDR_TYPE ,BR_BUILD_NUMBER ,BR_COUNTRY ,BR_COUNTRY_SUB_DIV ,BR_DEPT ,BR_POST_CODE ,BR_STREET_NAME ,BR_SUB_DEPT ,BR_TOWN_NAME "); 
		sbFinInst.append(",BRANCH_ID ,BRANCH_NAME ,CREATED_BY ,CREATED_DATE ,FI_ADDR_LINE ,FI_ADDR_TYPE ,FI_BUILD_NUMBER ,FI_COUNTRY ,FI_COUNTRY_SUB_DIV ,FI_DEPT ,FI_ID ,FI_NAME ");
		sbFinInst.append(",FI_POST_CODE ,FI_STREET_NAME ,FI_SUB_DEPT ,FI_TOWN_NAME ,NVL(FIN_INST_TYPE_ID,'NF') ,ISSUER ,MEMBER_ID ,MODIFIED_BY ,MODIFIED_DATE ,MSG_ID ,MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbFinInst.append("FROM CAMOWNER.MDT_AC_OPS_FIN_INST ");
		sbFinInst.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String finInstLeftSQL = sbFinInst.toString();
			log.debug("finInstLeftSQL: " + finInstLeftSQL);
			genericDAO.executeNativeSQL(finInstLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Fin Inst LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING PARTY IDENT EXCESS TXNS===============");
		StringBuffer sbPartyIdent = new StringBuffer();
		sbPartyIdent.append("INSERT INTO CAMOWNER.MDT_AC_ARC_PARTY_IDENT ");
		sbPartyIdent.append("(ADDR_LINE ,ADDR_TYPE ,BUILD_NUMBER ,CITY_OF_BIRTH ,CONTACT_NAME ,COUNTRY ,COUNTRY_SUB_DIV ,CREATED_BY ,CREATED_DATE ,CTRY_OF_BIRTH ,CTRY_OF_RESIDENCE ,DATE_OF_BIRTH ,DEPT ,EMAIL ,FAX_NR "); 
		sbPartyIdent.append(",ID ,MOB_NR ,MODIFIED_BY ,MODIFIED_DATE ,NAME ,NAME_PREFIX ,PARTY_IDENT_TYPE_ID ,PHONE_NR ,POST_CODE ,PROVINCE_OF_BIRTH ,STREET_NAME ,SUB_DEPT ,TOWN_NAME ,MSG_ID ,ENTRY_CLASS ");
		sbPartyIdent.append(",MANDATE_REQ_TRAN_ID, ARCHIVE_DATE) ");
		sbPartyIdent.append("SELECT ADDR_LINE ,ADDR_TYPE ,BUILD_NUMBER ,CITY_OF_BIRTH ,CONTACT_NAME ,COUNTRY ,COUNTRY_SUB_DIV ,CREATED_BY ,CREATED_DATE ,CTRY_OF_BIRTH ,CTRY_OF_RESIDENCE "); 
		sbPartyIdent.append(",DATE_OF_BIRTH ,DEPT ,EMAIL ,FAX_NR ,ID ,MOB_NR ,MODIFIED_BY ,MODIFIED_DATE ,NAME ,NAME_PREFIX ,NVL(PARTY_IDENT_TYPE_ID,'NF'),PHONE_NR ,POST_CODE ,PROVINCE_OF_BIRTH "); 
		sbPartyIdent.append(",STREET_NAME ,SUB_DEPT ,TOWN_NAME ,MSG_ID ,ENTRY_CLASS ,MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbPartyIdent.append("FROM CAMOWNER.MDT_AC_OPS_PARTY_IDENT ");
		sbPartyIdent.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String partyIdLeftSQL = sbPartyIdent.toString();
			log.debug("partyIdLeftSQL: " + partyIdLeftSQL);
			genericDAO.executeNativeSQL(partyIdLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive PartyIdent LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING REF DOC EXCESS TXNS===============");
		StringBuffer sbRefDoc = new StringBuffer();
		sbRefDoc.append("INSERT INTO CAMOWNER.MDT_AC_ARC_REF_DOC ");
		sbRefDoc.append("(CODE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,REF_DOC_NUMBER ,RELATED_DATE ,MSG_ID ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbRefDoc.append("SELECT CODE ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,REF_DOC_NUMBER ,RELATED_DATE ,nvl(MSG_ID,'NF') as msgid ,MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbRefDoc.append("FROM CAMOWNER.MDT_AC_OPS_REF_DOC ");
		sbRefDoc.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String refDocLeftSQL = sbRefDoc.toString();
			log.debug("refDocLeftSQL: " + refDocLeftSQL);
			genericDAO.executeNativeSQL(refDocLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive RefDoc LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING SUPPL DATA EXCESS TXNS===============");
		StringBuffer sbSupplData = new StringBuffer();
		sbSupplData.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUPPL_DATA ");
		sbSupplData.append("(ADJUST_AMT ,ADJUST_AMT_CURR ,ADJUST_CAT ,ADJUST_RATE ,DEBIT_VALUE_TYPE ,DTE_ADJUST_RULE_IND ,FIRST_COLL_AMT ,FIRST_COLL_AMT_CURR ,PLACE_AND_NAME "); 
		sbSupplData.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,MANDATE_REF_NR ,AUTH_CHANNEL ,AUTH_TYPE ,COLL_DAY ,MSG_ID ,MANDATE_AUTH_DATE ,MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbSupplData.append("SELECT ADJUST_AMT ,ADJUST_AMT_CURR ,ADJUST_CAT ,ADJUST_RATE ,DEBIT_VALUE_TYPE ,DTE_ADJUST_RULE_IND ,FIRST_COLL_AMT ,FIRST_COLL_AMT_CURR ,PLACE_AND_NAME ,CREATED_BY ");
		sbSupplData.append(" ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,MANDATE_REF_NR ,AUTH_CHANNEL ,AUTH_TYPE ,COLL_DAY ,MSG_ID ,MANDATE_AUTH_DATE ,MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbSupplData.append("FROM CAMOWNER.MDT_AC_OPS_SUPPL_DATA ");
		sbSupplData.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String suppDataLeftSQL = sbSupplData.toString();
			log.debug("suppDataLeftSQL: " + suppDataLeftSQL);
			genericDAO.executeNativeSQL(suppDataLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive SupplData LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING ORGNL MANDATE EXCESS TXNS===============");
		StringBuffer sbOrigMndt = new StringBuffer();
		sbOrigMndt.append("INSERT INTO CAMOWNER.MDT_AC_ARC_ORGNL_MNDT ");
		sbOrigMndt.append("(MANDATE_ID ,MANDATE_REQ_TRAN_ID ,MSG_ID ,MANDATE_REQ_ID ,CREDITOR_NAME ,DEBTOR_NAME ,DEBTOR_BRANCH_NO ,ORGNL_MANDATE_REQ_TRAN_ID ,ARCHIVE_DATE) ");
		sbOrigMndt.append("SELECT MANDATE_ID ,nvl(MANDATE_REQ_TRAN_ID,'NF') as mrti ,MSG_ID ,MANDATE_REQ_ID ,CREDITOR_NAME ,DEBTOR_NAME ,DEBTOR_BRANCH_NO ");
		sbOrigMndt.append(",ORGNL_MANDATE_REQ_TRAN_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbOrigMndt.append("FROM CAMOWNER.MDT_AC_OPS_ORGNL_MNDT ");
		sbOrigMndt.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String orgMndtLeftSQL = sbOrigMndt.toString();
			log.debug("orgMndtLeftSQL: " + orgMndtLeftSQL);
			genericDAO.executeNativeSQL(orgMndtLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Orig Mandate LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING SUSPENSION GRPHDR EXCESS TXNS===============");
		StringBuffer sbSuspHdrs = new StringBuffer();
		sbSuspHdrs.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUSP_GRP_HDR ");
		sbSuspHdrs.append("(ASSIGNMENT_ID ,ASSIGNER ,ASSIGNEE ,CREATE_DATE_TIME ,ARCHIVE_DATE) ");
		sbSuspHdrs.append("SELECT DISTINCT nvl(ASSIGNMENT_ID,'NF') as assgnid ,ASSIGNER ,ASSIGNEE ,CREATE_DATE_TIME ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbSuspHdrs.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR ");
		sbSuspHdrs.append("WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String suspHdrsLeftSQL = sbSuspHdrs.toString();
			log.debug("suspHdrsLeftSQL: " + suspHdrsLeftSQL);
			genericDAO.executeNativeSQL(suspHdrsLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive SuspHdrs LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		log.info("===============ARCHIVING SUSPENSION MSG EXCESS TXNS===============");
		StringBuffer sbSuspDtls = new StringBuffer();
		sbSuspDtls.append("INSERT INTO CAMOWNER.MDT_AC_ARC_SUSP_MSG ");
		sbSuspDtls.append("(MANDATE_SUSP_ID ,ORIGINAL_PMT_ID ,ASSIGNMENT_ID ,REASON_CODE ,MANDATE_REF_NR ,CREDITOR_BANK ,MANDATE_REQ_TRAN_ID "); 
		sbSuspDtls.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,ARCHIVE_DATE) ");
		sbSuspDtls.append("SELECT MANDATE_SUSP_ID ,ORIGINAL_PMT_ID ,ASSIGNMENT_ID ,REASON_CODE ,MANDATE_REF_NR ,CREDITOR_BANK ,MANDATE_REQ_TRAN_ID "); 
		sbSuspDtls.append(",CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,MODIFIED_DATE ,PROCESS_STATUS ,EXTRACT_MSG_ID ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbSuspDtls.append("FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG ");
		sbSuspDtls.append("WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ");

		try
		{
			String suspDtlsLeftSQL = sbSuspDtls.toString();
			log.debug("suspDtlsLeftSQL: " + suspDtlsLeftSQL);
			genericDAO.executeNativeSQL(suspDtlsLeftSQL);
		}
		catch(Exception ex)
		{
			log.error("Error on Archive SuspDtls LeftOver:- "+ex.getMessage());
			ex.printStackTrace();
		}

		return true;
	}

	public boolean deleteLeftOverTxnsBySQL(String expiredDate)
	{
		try
		{
			log.info("===============DELETING GROUP HEADER EXCESS TXNS===============");
			String ghLeftDel = "DELETE FROM  CAMOWNER.MDT_AC_OPS_GRP_HDR WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("ghLeftDel: " + ghLeftDel);
			genericDAO.executeNativeSQL(ghLeftDel);

			log.info("===============DELETING MANDATES EXCESS TXNS===============");
			String mndtLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_MNDT_MSG WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("mndtLeftDel: " + mndtLeftDel);
			genericDAO.executeNativeSQL(mndtLeftDel);

			log.info("===============DELETING CASH ACCOUNT EXCESS TXNS===============");
			String cashAccLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_CASH_ACCOUNT WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("cashAccLeftDel: " + cashAccLeftDel);
			genericDAO.executeNativeSQL(cashAccLeftDel);

			log.info("===============DELETING FIN INST EXCESS TXNS===============");
			String finInstLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_FIN_INST WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("finInstLeftDel: " + finInstLeftDel);
			genericDAO.executeNativeSQL(finInstLeftDel);

			log.info("===============DELETING PARTY IDENT EXCESS TXNS===============");
			String partyIdLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_PARTY_IDENT WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("partyIdLeftDel: " + partyIdLeftDel);
			genericDAO.executeNativeSQL(partyIdLeftDel);

			log.info("===============DELETING REF DOC EXCESS TXNS===============");
			String refDocLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_REF_DOC WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("refDocLeftDel: " + refDocLeftDel);
			genericDAO.executeNativeSQL(refDocLeftDel);

			log.info("===============DELETING SUPPLEMENTARY DATA EXCESS TXNS===============");
			String suppDataLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_SUPPL_DATA WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suppDataLeftDel: " + suppDataLeftDel);
			genericDAO.executeNativeSQL(suppDataLeftDel);

			log.info("===============DELETING ORIGINAL MANDATE EXCESS TXNS===============");
			String origMndtLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_ORGNL_MNDT WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("origMndtLeftDel: " + origMndtLeftDel);
			genericDAO.executeNativeSQL(origMndtLeftDel);

			log.info("===============DELETING SUSPENSION GRPHDR EXCESS TXNS===============");
			String suspGHLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_SUSP_GRP_HDR WHERE CREATE_DATE_TIME <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suspGHLeftDel: " + suspGHLeftDel);
			genericDAO.executeNativeSQL(suspGHLeftDel);

			log.info("===============DELETING SUSPENSION MSG EXCESS TXNS===============");
			String suspMsgLeftDel = "DELETE FROM CAMOWNER.MDT_AC_OPS_SUSP_MSG WHERE CREATED_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suspMsgLeftDel: " + suspMsgLeftDel);
			genericDAO.executeNativeSQL(suspMsgLeftDel);

			return true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Excess Txns:- "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}

	}

	public boolean cleanUpArchivedTxnsBySQL(String expiredDate)
	{
		try
		{
			log.info("===============CLEANING UP ARC GROUP HEADER TXNS===============");
			String ghLeftDel = "DELETE FROM  CAMOWNER.MDT_AC_ARC_GRP_HDR WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("ghLeftDel: " + ghLeftDel);
			genericDAO.executeNativeSQL(ghLeftDel);

			log.info("===============CLEANING UP ARC MANDATES TXNS===============");
			String mndtLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_MNDT_MSG WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("mndtLeftDel: " + mndtLeftDel);
			genericDAO.executeNativeSQL(mndtLeftDel);

			log.info("===============CLEANING UP ARC CASH ACCOUNT TXNS===============");
			String cashAccLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_CASH_ACCOUNT WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("cashAccLeftDel: " + cashAccLeftDel);
			genericDAO.executeNativeSQL(cashAccLeftDel);

			log.info("===============CLEANING UP ARC FIN INST TXNS===============");
			String finInstLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_FIN_INST WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("finInstLeftDel: " + finInstLeftDel);
			genericDAO.executeNativeSQL(finInstLeftDel);

			log.info("===============CLEANING UP ARC PARTY IDENT TXNS===============");
			String partyIdLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_PARTY_IDENT WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("partyIdLeftDel: " + partyIdLeftDel);
			genericDAO.executeNativeSQL(partyIdLeftDel);

			log.info("===============CLEANING UP ARC REF DOC TXNS===============");
			String refDocLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_REF_DOC WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("refDocLeftDel: " + refDocLeftDel);
			genericDAO.executeNativeSQL(refDocLeftDel);

			log.info("===============CLEANING UP ARC SUPPLEMENTARY DATA TXNS===============");
			String suppDataLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_SUPPL_DATA WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suppDataLeftDel: " + suppDataLeftDel);
			genericDAO.executeNativeSQL(suppDataLeftDel);

			log.info("===============CLEANING UP ARC ORIGINAL MANDATE TXNS===============");
			String origMndtLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_ORGNL_MNDT WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("origMndtLeftDel: " + origMndtLeftDel);
			genericDAO.executeNativeSQL(origMndtLeftDel);

			log.info("===============CLEANING UP ARC SUSPENSION GRPHDR TXNS===============");
			String suspGHLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_SUSP_GRP_HDR WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suspGHLeftDel: " + suspGHLeftDel);
			genericDAO.executeNativeSQL(suspGHLeftDel);

			log.info("===============CLEANING UP ARC SUSPENSION MSG TXNS===============");
			String suspMsgLeftDel = "DELETE FROM CAMOWNER.MDT_AC_ARC_SUSP_MSG WHERE ARCHIVE_DATE <= TO_DATE('"+expiredDate+"','YYYY-MM-DD') ";
			log.debug("suspMsgLeftDel: " + suspMsgLeftDel);
			genericDAO.executeNativeSQL(suspMsgLeftDel);

			return true;
		}
		catch(Exception ex)
		{
			log.error("Error on clean up archive Txns:- "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}


	public boolean eodCheckSt203SroutExtracted(String service, String memberId) {

		boolean confDtlsCheck = false;

		//2.Check ConfDtlsMsgs
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", readyForExtractStatus);
			parameters.put("extractService", service);
			parameters.put("memberId", memberId);

			List<CasOpsConfDetailsEntity> opsConfDetailsCheckList = (List<CasOpsConfDetailsEntity>) genericDAO.findAllByNQCriteria("CasOpsConfDetailsEntity.findByExtractProcessStatusOutService", parameters);
			if(opsConfDetailsCheckList != null && opsConfDetailsCheckList.size() > 0)
			{
				log.debug("opsConfDetailsCheckList.size(); =====> "+opsConfDetailsCheckList.size());
				confDtlsCheck = false;
			}
			else
				confDtlsCheck = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on eodCheckIfAllFilesAreExtracted.opsConfDetailsCheckList: "+ e.getMessage());
			e.printStackTrace();
		}

		return confDtlsCheck;
	}

	public boolean eodCheckIfStReportExtracted(Date systemDate, String service, String memberId) {

		boolean statusRptsCheck = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strSysDate = sdf.format(systemDate);
		log.debug("strSysDate ====> "+strSysDate);

		//6.Check Status Reports Status
		try
		{
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("processStatus", reportToBeProdStatus);
			parameters.put("createDateTime", strSysDate);
			parameters.put("service", service);
			parameters.put("memberId", memberId);

			List<CasOpsStatusHdrsEntity> statusRptCheckList = (List<CasOpsStatusHdrsEntity>) genericDAO.findAllByNQCriteria("CasOpsStatusHdrsEntity.findByProStatusAndService",parameters);
			log.debug("statusRptCheckList.size(); =====> "+statusRptCheckList.size());
			if(statusRptCheckList != null && statusRptCheckList.size() > 0)
				statusRptsCheck = false;
			else
				statusRptsCheck = true;
		} 
		catch (ObjectNotFoundException onfe) 
		{
			log.debug("No Object Exists on DB");
		} 
		catch (Exception e) 
		{
			log.error("Error on eodCheckIfAllFilesAreExtracted.statusRptCheckList: "+ e.getMessage());
			e.printStackTrace();
		}

		return statusRptsCheck;
	}

	@Override
	public boolean saveAcOpsTxnBilling(Object obj) {
		try 
		{
			if (obj instanceof CasOpsTxnsBillingEntity)
			{
				CasOpsTxnsBillingEntity acOpsTxnsBillingEntity = (CasOpsTxnsBillingEntity) obj;
				//				log.info("Entity in Save==> "+opsDailyBillingEntity);
				genericDAO.save(acOpsTxnsBillingEntity);

				return true;
			} 
			else 
			{
				log.info("Unable to convert type to MdtAcOpsTxnsBillingEntity.");
				return false;
			}
		} 
		catch (Exception e) 
		{
			log.error("Error on saveAcOpsTxnBilling: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<?> retrievetxnsBilingToExport(String nonActInd) {
	
		List<CasOpsTxnsBillingEntity> mdtAcOpsTxnsBillingList = genericDAO.findAllByNamedQuery("CasOpsTxnsBillingEntity.findByBillExpStatus","billExpStatus",nonActInd);
		return mdtAcOpsTxnsBillingList;
	}

	@Override
	public boolean createOpsTxnBillingRecords(Object obj) {
		try {

			if (obj instanceof ObsTxnsBillStagingEntity) {
				//				log.debug("In the billing staging entity<><><><><><><>");
				ObsTxnsBillStagingEntity obsTxnsBillStagingEntity = (ObsTxnsBillStagingEntity) obj;
//				log.info("<><><>obsTxnsBillStagingEntity:<><><> "+obsTxnsBillStagingEntity);
				genericDAO.save(obsTxnsBillStagingEntity);
				return true;
			} else {
				log.info("Unable to convert type to ObsTxnsBillStagingEntity.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error on createOpsTxnBillingRecords: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateOpsTxnBillingInd(Object obj) {
		try 
		{
			if (obj instanceof CasOpsTxnsBillingEntity)
			{
				CasOpsTxnsBillingEntity opsTxnsBillingEntity = (CasOpsTxnsBillingEntity) obj;
				log.info("Entity in Save==> "+opsTxnsBillingEntity);
				genericDAO.saveOrUpdate(opsTxnsBillingEntity);

				return true;
			} 
			else 
			{
				log.info("Unable to convert type to MdtAcOpsTxnsBillingEntity.");
				return false;
			}
		} 
		catch (Exception e) 
		{
			log.error("Error on updateOpsTxnBillingInd: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean archiveOpsTxnBillingBySQL(String archDate)
	{
		boolean txnsBillBool = false;

		StringBuffer sbTnxsBill = new StringBuffer();

		sbTnxsBill.append("INSERT INTO CAMOWNER.CAS_ARC_TXNS_BILLING ");
		sbTnxsBill.append("(SYSTEM_SEQ_NO ,ORIGINATOR ,SERVICE ,SUB_SERVICE ,TXN_TYPE ,TXN_STATUS ,FILE_NAME ,STATUS ,VOLUME ,BILL_EXP_STATUS ");
		sbTnxsBill.append(",SYSTEM_NAME ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,ACTION_DATE ,RESP_DATE ,ARCHIVE_DATE) ");
		sbTnxsBill.append("SELECT SYSTEM_SEQ_NO ,ORIGINATOR ,SERVICE ,SUB_SERVICE ,TXN_TYPE ,TXN_STATUS ,FILE_NAME ,STATUS ,VOLUME ,BILL_EXP_STATUS ");
		sbTnxsBill.append(",SYSTEM_NAME ,CREATED_BY ,CREATED_DATE ,MODIFIED_BY ,ACTION_DATE ,RESP_DATE ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbTnxsBill.append("FROM CAMOWNER.CAS_OPS_TXNS_BILLING WHERE BILL_EXP_STATUS = 'Y' ");

		try
		{
			String txnsBillSQL = sbTnxsBill.toString();
			log.debug("txnsBillSQL: " + txnsBillSQL);
			genericDAO.executeNativeSQL(txnsBillSQL);
			txnsBillBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Txn Billing:- "+ex.getMessage());
			ex.printStackTrace();
			txnsBillBool = false;
		}
		return txnsBillBool;
	}

	public boolean deleteOpsTxnBillingBySQL()
	{
		boolean opsTxnBillBool = false;

		try
		{
			String opsTxnsDelete = "DELETE FROM CAMOWNER.CAS_OPS_TXNS_BILLING WHERE BILL_EXP_STATUS = 'Y' ";
			log.debug("dlyBillDel: " + opsTxnsDelete);
			genericDAO.executeNativeSQL(opsTxnsDelete);
			opsTxnBillBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting OPS Txns Billing:- "+ex.getMessage());
			ex.printStackTrace();
			opsTxnBillBool = false;
		}
		return opsTxnBillBool;
	}

	@Override
	public Object retrievePastTimeForConfData() {
		
		List<CasConfigDataTimeEntity> casOpsConfigDataTimeList = new ArrayList<CasConfigDataTimeEntity>();
		CasConfigDataTimeEntity casOpsConfigDataTime = new CasConfigDataTimeEntity();
		
		try {
			casOpsConfigDataTimeList = genericDAO.findAll(CasConfigDataTimeEntity.class);
			
			if(casOpsConfigDataTimeList != null && casOpsConfigDataTimeList.size() > 0) {
				for(CasConfigDataTimeEntity mdtAcOpsConfigDataTimeLocal : casOpsConfigDataTimeList) {
					casOpsConfigDataTime = mdtAcOpsConfigDataTimeLocal;
				}
			}
		}
		catch (Exception e) 
		{
			log.error("Error on retrieveMdtAcOpsConfigDataTime: "+ e.getMessage());
			e.printStackTrace();
		}
		return casOpsConfigDataTime;
	}
	
	@Override
	public Boolean createOrUpdatePastTimeForConfData(Object obj) {
		Boolean updated = false;
		try {
			if (obj instanceof CasConfigDataTimeEntity) {
				CasConfigDataTimeEntity casConfigDataTimeEntity = (CasConfigDataTimeEntity) obj;

				genericDAO.saveOrUpdate(casConfigDataTimeEntity);
				updated = true;
			} else {
				log.error("Unable to convert type to MdtAcConfigDataTimeEntity.");
				updated = false;
			}
		} catch (Exception e) {
			log.error("Error on updateMandate: " + e.getMessage());
			e.printStackTrace();
			updated = false;
		}
		return updated;
	}

	@Override
	public boolean saveOpsTxnBillInfo(List<?> opsTxnsBillReportList) {
		{
			boolean saved = false;
			try
			{
				if(opsTxnsBillReportList.size() > 0)
				{
					for (Object obj : opsTxnsBillReportList) 
					{
						if(obj instanceof CasOpsTxnsBillReportEntity)
						{

							CasOpsTxnsBillReportEntity localEntity = (CasOpsTxnsBillReportEntity) obj;
							log.info("Writing to OpsTxnsBillReportTable table.... "+localEntity);
							genericDAO.save(localEntity);
							saved = true;
						}
					}			
				}
				else
					saved = false;
			}
			catch(Exception e)
			{
				log.error("Error on saveOpsErrorCodeInfo"+e.getMessage());
				e.printStackTrace();
				saved = false;
			}

			return saved;
		}

	}


	public boolean archiveOpsTxnBillRptDataBySQL(String archDate)
	{
		boolean opsTxnDataBool = false;

		StringBuffer sbTxnDataBool = new StringBuffer();

		sbTxnDataBool.append("INSERT INTO CAMOWNER.CAS_ARC_TXNS_BILL_REPORT ");
		sbTxnDataBool.append("(SYSTEM_SEQ_NO ,ORIGINATOR ,SUB_SERVICE ,TXN_TYPE ,FILE_NAME ,PROCESS_DATE ,DELIVERY_TIME ,MANDATE_REQ_TRAN_ID ,TXN_STATUS ,ARCHIVE_DATE) ");
		sbTxnDataBool.append("SELECT SYSTEM_SEQ_NO ,ORIGINATOR ,SUB_SERVICE ,TXN_TYPE ,FILE_NAME ,PROCESS_DATE ,DELIVERY_TIME ,MANDATE_REQ_TRAN_ID ,TXN_STATUS ,TO_DATE('"+archDate+"','YYYY-MM-DD') ");
		sbTxnDataBool.append("FROM CAMOWNER.CAS_OPS_TXNS_BILL_REPORT ");

		try
		{
			String txnBilSQL = sbTxnDataBool.toString();
			log.debug("txnBilSQL: " + txnBilSQL);
			genericDAO.executeNativeSQL(txnBilSQL);
			opsTxnDataBool = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Archive Ops Txns Bill Report:- "+ex.getMessage());
			ex.printStackTrace();
			opsTxnDataBool = false;
		}
		return opsTxnDataBool;
	}

	public boolean deleteOpsTxnBillRptDataBySQL()
	{
		boolean txnBilDel = false;

		try
		{
			String txnBilDelSQL = "DELETE FROM CAMOWNER.CAS_OPS_TXNS_BILL_REPORT";
			genericDAO.executeNativeSQL(txnBilDelSQL);
			txnBilDel = true;
		}
		catch(Exception ex)
		{
			log.error("Error on Deleting Ops Txns Bill Report:- "+ex.getMessage());
			ex.printStackTrace();
			txnBilDel = false;
		}
		return txnBilDel;

	}
}

