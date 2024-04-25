package com.bsva.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.log4j.Logger;

import com.bsva.authcoll.singletable.validation.Validation_ST;
import com.bsva.entities.MdtAcOpsFileSizeLimitEntity;
import com.bsva.entities.SysCisBankEntity;
import com.bsva.interfaces.AdminBeanRemote;
import com.bsva.interfaces.FileProcessBeanRemote;
import com.bsva.interfaces.ServiceBeanRemote;
import com.bsva.interfaces.ValidationBeanRemote;

/**
 * 
 * @author DimakatsoN
 *
 */

public class ExtractUtil {

	public static Logger log = Logger.getLogger(ExtractUtil.class);
	public static AdminBeanRemote adminBeanRemote;
	public static ValidationBeanRemote valBeanRemote;
	public static ServiceBeanRemote beanRemote;
	public static FileProcessBeanRemote fileProcBeanRemote;
	List<SysCisBankEntity> sysCisBankList = new ArrayList<SysCisBankEntity>();
	List<MdtAcOpsFileSizeLimitEntity>  opsFileSizeLimitList = new ArrayList<MdtAcOpsFileSizeLimitEntity>();
	
	
	public ExtractUtil() {
		contextAdminBeanCheck();
		contextValidationBeanCheck();
		contextCheck();
		contextFileProcBeanCheck();
		
//		Retrieve STATIC Data from Tables	
		retriveOpsTableData();
	}

	public void retriveOpsTableData() 
	{
		log.debug("<<<<================Retrieving Ops Tables Data================>>>>");
		try {
	
			sysCisBankList = (List<SysCisBankEntity>) valBeanRemote.findAllCisBanks();
			opsFileSizeLimitList = (List<MdtAcOpsFileSizeLimitEntity>) valBeanRemote.findAllFileSizeLimit();
	
	
		}
		catch(Exception ex) {
			System.out.println("Error retrieving Ops tables for extract: "+ex.getMessage());
			ex.printStackTrace();
		}	
	}


		//Search for specific Creditor/Debtor Banks
		public SysCisBankEntity findCreditorCISBanks(final String memberNo)
		{
			if(memberNo == null || sysCisBankList == null || sysCisBankList.isEmpty())
			{
				return null;
			}
			SysCisBankEntity sysCisBankEntity = IterableUtils.find(sysCisBankList, new Predicate<SysCisBankEntity>() {

				@Override
				public boolean evaluate(SysCisBankEntity sysCisBankEntity) {
					// TODO Auto-generated method stub
					return 
					((memberNo.equalsIgnoreCase(sysCisBankEntity.getMemberNo()))	&& sysCisBankEntity.getAcCreditor().equalsIgnoreCase("Y"));
				}
				
			});
			return sysCisBankEntity;
		}
		
		
		public SysCisBankEntity findDebtorCISBanks(final String memberNo)
		{
			if(memberNo == null || sysCisBankList == null || sysCisBankList.isEmpty())
			{
				return null;
			}
			SysCisBankEntity sysCisBankEntity = IterableUtils.find(sysCisBankList, new Predicate<SysCisBankEntity>() {

				@Override
				public boolean evaluate(SysCisBankEntity sysCisBankEntity) {
					// TODO Auto-generated method stub
					return 
					((memberNo.equalsIgnoreCase(sysCisBankEntity.getMemberNo())) && sysCisBankEntity.getAcDebtor().equalsIgnoreCase("Y"));
				}
				
			});
			return sysCisBankEntity;
		}
		
		
		public MdtAcOpsFileSizeLimitEntity  findOutgoingService(final String serviceId)
		{
			if(serviceId == null || opsFileSizeLimitList == null || opsFileSizeLimitList.isEmpty())
			{
				return null;
			}
			
			MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity = IterableUtils.find(opsFileSizeLimitList, new Predicate<MdtAcOpsFileSizeLimitEntity>()
					{
				@Override
				public boolean evaluate(MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity) {
					
					return 
							((serviceId.equalsIgnoreCase(mdtAcOpsFileSizeLimitEntity.getMdtAcOpsFileSizeLimitPK().getSubService())) && mdtAcOpsFileSizeLimitEntity.getActiveId().equalsIgnoreCase("Y") &&
									mdtAcOpsFileSizeLimitEntity.getDirection().equalsIgnoreCase("O"));
				}
					});
			return mdtAcOpsFileSizeLimitEntity;
		}
		
		public MdtAcOpsFileSizeLimitEntity  findLimit(final String limit)
		{
			if(limit == null || opsFileSizeLimitList == null || opsFileSizeLimitList.isEmpty())
			{
				return null;
			}
			
			MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity = IterableUtils.find(opsFileSizeLimitList, new Predicate<MdtAcOpsFileSizeLimitEntity>()
					{
				@Override
				public boolean evaluate(MdtAcOpsFileSizeLimitEntity mdtAcOpsFileSizeLimitEntity) {
					
					return 
							((limit.equalsIgnoreCase(mdtAcOpsFileSizeLimitEntity.getLimit()) && mdtAcOpsFileSizeLimitEntity.getActiveId().equalsIgnoreCase("Y")));
				}
					});
			return mdtAcOpsFileSizeLimitEntity;
		}
		
		
		
		
		public static void contextAdminBeanCheck() {
			if (adminBeanRemote == null) {
				adminBeanRemote = Util.getAdminBean();
			}
		}

		public static void contextValidationBeanCheck() {
			if (valBeanRemote == null) {
				valBeanRemote = Util.getValidationBean();
			}

		}

		public static void contextCheck() {
			if (beanRemote == null) {
				beanRemote = Util.getServiceBean();
			}
		}

		public static void contextFileProcBeanCheck() {
			if (fileProcBeanRemote == null) {
				fileProcBeanRemote = Util.getFileProcessBean();
			}
		}

}
