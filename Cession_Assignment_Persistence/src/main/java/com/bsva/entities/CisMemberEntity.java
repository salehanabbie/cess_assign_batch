package com.bsva.entities;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SalehaR
 */
@Entity
@Table(name = "CIS_MEMBER",schema = "CAMOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CisMemberEntity.findAll", query = "SELECT c FROM CisMemberEntity c"),
    @NamedQuery(name = "CisMemberEntity.findByMemberNo", query = "SELECT c FROM CisMemberEntity c WHERE c.memberNo = :memberNo"),
    @NamedQuery(name = "CisMemberEntity.findByBaMemberNo", query = "SELECT c FROM CisMemberEntity c WHERE c.baMemberNo = :baMemberNo"),
    @NamedQuery(name = "CisMemberEntity.findByMemberName", query = "SELECT c FROM CisMemberEntity c WHERE c.memberName = :memberName"),
    @NamedQuery(name = "CisMemberEntity.findByAbbrevMemberName", query = "SELECT c FROM CisMemberEntity c WHERE c.abbrevMemberName = :abbrevMemberName"),
    @NamedQuery(name = "CisMemberEntity.findByMnemonicMemberName", query = "SELECT c FROM CisMemberEntity c WHERE c.mnemonicMemberName = :mnemonicMemberName"),
    @NamedQuery(name = "CisMemberEntity.findByMemberBranchStartRange", query = "SELECT c FROM CisMemberEntity c WHERE c.memberBranchStartRange = :memberBranchStartRange"),
    @NamedQuery(name = "CisMemberEntity.findByMemberBranchEndRange", query = "SELECT c FROM CisMemberEntity c WHERE c.memberBranchEndRange = :memberBranchEndRange"),
    @NamedQuery(name = "CisMemberEntity.findByMemberAddress1", query = "SELECT c FROM CisMemberEntity c WHERE c.memberAddress1 = :memberAddress1"),
    @NamedQuery(name = "CisMemberEntity.findByMemberAddress2", query = "SELECT c FROM CisMemberEntity c WHERE c.memberAddress2 = :memberAddress2"),
    @NamedQuery(name = "CisMemberEntity.findByMemberAddress3", query = "SELECT c FROM CisMemberEntity c WHERE c.memberAddress3 = :memberAddress3"),
    @NamedQuery(name = "CisMemberEntity.findByMemberAddress4", query = "SELECT c FROM CisMemberEntity c WHERE c.memberAddress4 = :memberAddress4"),
    @NamedQuery(name = "CisMemberEntity.findByCountry", query = "SELECT c FROM CisMemberEntity c WHERE c.country = :country"),
    @NamedQuery(name = "CisMemberEntity.findByCurrency", query = "SELECT c FROM CisMemberEntity c WHERE c.currency = :currency"),
    @NamedQuery(name = "CisMemberEntity.findByAbbrevCurrency", query = "SELECT c FROM CisMemberEntity c WHERE c.abbrevCurrency = :abbrevCurrency"),
    @NamedQuery(name = "CisMemberEntity.findByMemberLanguage", query = "SELECT c FROM CisMemberEntity c WHERE c.memberLanguage = :memberLanguage"),
    @NamedQuery(name = "CisMemberEntity.findByMemberStatus", query = "SELECT c FROM CisMemberEntity c WHERE c.memberStatus = :memberStatus"),
    @NamedQuery(name = "CisMemberEntity.findByMemberSetLive", query = "SELECT c FROM CisMemberEntity c WHERE c.memberSetLive = :memberSetLive"),
    @NamedQuery(name = "CisMemberEntity.findByMemberSponsoredBy", query = "SELECT c FROM CisMemberEntity c WHERE c.memberSponsoredBy = :memberSponsoredBy"),
    @NamedQuery(name = "CisMemberEntity.findByMemberSarbAccnt", query = "SELECT c FROM CisMemberEntity c WHERE c.memberSarbAccnt = :memberSarbAccnt"),
    @NamedQuery(name = "CisMemberEntity.findByMemberTapeId", query = "SELECT c FROM CisMemberEntity c WHERE c.memberTapeId = :memberTapeId"),
    @NamedQuery(name = "CisMemberEntity.findByMemberSortSeqNo", query = "SELECT c FROM CisMemberEntity c WHERE c.memberSortSeqNo = :memberSortSeqNo"),
    @NamedQuery(name = "CisMemberEntity.findByAgencyAddress1", query = "SELECT c FROM CisMemberEntity c WHERE c.agencyAddress1 = :agencyAddress1"),
    @NamedQuery(name = "CisMemberEntity.findByAgencyAddress2", query = "SELECT c FROM CisMemberEntity c WHERE c.agencyAddress2 = :agencyAddress2"),
    @NamedQuery(name = "CisMemberEntity.findByAgencyAddress3", query = "SELECT c FROM CisMemberEntity c WHERE c.agencyAddress3 = :agencyAddress3"),
    @NamedQuery(name = "CisMemberEntity.findByClcMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.clcMemberInd = :clcMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByClcChequesTo", query = "SELECT c FROM CisMemberEntity c WHERE c.clcChequesTo = :clcChequesTo"),
    @NamedQuery(name = "CisMemberEntity.findByClcRemitter", query = "SELECT c FROM CisMemberEntity c WHERE c.clcRemitter = :clcRemitter"),
    @NamedQuery(name = "CisMemberEntity.findByClcOutputContent", query = "SELECT c FROM CisMemberEntity c WHERE c.clcOutputContent = :clcOutputContent"),
    @NamedQuery(name = "CisMemberEntity.findByClcOutputFormat", query = "SELECT c FROM CisMemberEntity c WHERE c.clcOutputFormat = :clcOutputFormat"),
    @NamedQuery(name = "CisMemberEntity.findByClcDestBranch", query = "SELECT c FROM CisMemberEntity c WHERE c.clcDestBranch = :clcDestBranch"),
    @NamedQuery(name = "CisMemberEntity.findByClcCl6ReportReq", query = "SELECT c FROM CisMemberEntity c WHERE c.clcCl6ReportReq = :clcCl6ReportReq"),
    @NamedQuery(name = "CisMemberEntity.findByCcardMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.ccardMemberInd = :ccardMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDcardMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.dcardMemberInd = :dcardMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftMemberInd = :eftMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftComputerisedInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftComputerisedInd = :eftComputerisedInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftCtrlBreakCount", query = "SELECT c FROM CisMemberEntity c WHERE c.eftCtrlBreakCount = :eftCtrlBreakCount"),
    @NamedQuery(name = "CisMemberEntity.findByEftDebitAllowToSavings", query = "SELECT c FROM CisMemberEntity c WHERE c.eftDebitAllowToSavings = :eftDebitAllowToSavings"),
    @NamedQuery(name = "CisMemberEntity.findByEftElecChgFile", query = "SELECT c FROM CisMemberEntity c WHERE c.eftElecChgFile = :eftElecChgFile"),
    @NamedQuery(name = "CisMemberEntity.findByEftFormat", query = "SELECT c FROM CisMemberEntity c WHERE c.eftFormat = :eftFormat"),
    @NamedQuery(name = "CisMemberEntity.findByEftNonCompElecOut", query = "SELECT c FROM CisMemberEntity c WHERE c.eftNonCompElecOut = :eftNonCompElecOut"),
    @NamedQuery(name = "CisMemberEntity.findByEftOutputCentre", query = "SELECT c FROM CisMemberEntity c WHERE c.eftOutputCentre = :eftOutputCentre"),
    @NamedQuery(name = "CisMemberEntity.findByEftSsvTestLiveInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftSsvTestLiveInd = :eftSsvTestLiveInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftSsvOutputInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftSsvOutputInd = :eftSsvOutputInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftSsvMaxSizeTransFile", query = "SELECT c FROM CisMemberEntity c WHERE c.eftSsvMaxSizeTransFile = :eftSsvMaxSizeTransFile"),
    @NamedQuery(name = "CisMemberEntity.findByEftSsvMaxSizeTapeFile", query = "SELECT c FROM CisMemberEntity c WHERE c.eftSsvMaxSizeTapeFile = :eftSsvMaxSizeTapeFile"),
    @NamedQuery(name = "CisMemberEntity.findByEftDatedTestLiveInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftDatedTestLiveInd = :eftDatedTestLiveInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftDatedOutputInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftDatedOutputInd = :eftDatedOutputInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftDatedMaxSizeTransFile", query = "SELECT c FROM CisMemberEntity c WHERE c.eftDatedMaxSizeTransFile = :eftDatedMaxSizeTransFile"),
    @NamedQuery(name = "CisMemberEntity.findByEftDatedMaxSizeTapeFile", query = "SELECT c FROM CisMemberEntity c WHERE c.eftDatedMaxSizeTapeFile = :eftDatedMaxSizeTapeFile"),
    @NamedQuery(name = "CisMemberEntity.findByEftRecallsTestLiveInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftRecallsTestLiveInd = :eftRecallsTestLiveInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftRecallOutputInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftRecallOutputInd = :eftRecallOutputInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftUnpaidsTestLiveInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftUnpaidsTestLiveInd = :eftUnpaidsTestLiveInd"),
    @NamedQuery(name = "CisMemberEntity.findByEftUnpaidsOutputInd", query = "SELECT c FROM CisMemberEntity c WHERE c.eftUnpaidsOutputInd = :eftUnpaidsOutputInd"),
    @NamedQuery(name = "CisMemberEntity.findBySamosMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.samosMemberInd = :samosMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findBySamosBicCodeLive", query = "SELECT c FROM CisMemberEntity c WHERE c.samosBicCodeLive = :samosBicCodeLive"),
    @NamedQuery(name = "CisMemberEntity.findByLikeSamosBicCodeLive", query = "SELECT c FROM CisMemberEntity c WHERE c.samosBicCodeLive like :samosBicCodeLive"),
    @NamedQuery(name = "CisMemberEntity.findBySamosBicCodeTest", query = "SELECT c FROM CisMemberEntity c WHERE c.samosBicCodeTest = :samosBicCodeTest"),
    @NamedQuery(name = "CisMemberEntity.findBySamosAccNoLive", query = "SELECT c FROM CisMemberEntity c WHERE c.samosAccNoLive = :samosAccNoLive"),
    @NamedQuery(name = "CisMemberEntity.findBySamosAccNoTest", query = "SELECT c FROM CisMemberEntity c WHERE c.samosAccNoTest = :samosAccNoTest"),
    @NamedQuery(name = "CisMemberEntity.findBySamosSponsoredBy", query = "SELECT c FROM CisMemberEntity c WHERE c.samosSponsoredBy = :samosSponsoredBy"),
    @NamedQuery(name = "CisMemberEntity.findBySamosSponsorOthers", query = "SELECT c FROM CisMemberEntity c WHERE c.samosSponsorOthers = :samosSponsorOthers"),
    @NamedQuery(name = "CisMemberEntity.findBySamosReceiveMt298", query = "SELECT c FROM CisMemberEntity c WHERE c.samosReceiveMt298 = :samosReceiveMt298"),
    @NamedQuery(name = "CisMemberEntity.findBySaswMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.saswMemberInd = :saswMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByZapsMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsMemberInd = :zapsMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByZapsBicCodeLive", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsBicCodeLive = :zapsBicCodeLive"),
    @NamedQuery(name = "CisMemberEntity.findByZapsBicCodeTest", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsBicCodeTest = :zapsBicCodeTest"),
    @NamedQuery(name = "CisMemberEntity.findByZapsItemLimit", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsItemLimit = :zapsItemLimit"),
    @NamedQuery(name = "CisMemberEntity.findByZapsReceiveMt950", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsReceiveMt950 = :zapsReceiveMt950"),
    @NamedQuery(name = "CisMemberEntity.findByZapsMt950AccNo", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsMt950AccNo = :zapsMt950AccNo"),
    @NamedQuery(name = "CisMemberEntity.findByZapsOutputType", query = "SELECT c FROM CisMemberEntity c WHERE c.zapsOutputType = :zapsOutputType"),
    @NamedQuery(name = "CisMemberEntity.findByBamMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.bamMemberInd = :bamMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateBam", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateBam = :deletionRequestDateBam"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateClc", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateClc = :deletionRequestDateClc"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateEft", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateEft = :deletionRequestDateEft"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateCcard", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateCcard = :deletionRequestDateCcard"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateDcard", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateDcard = :deletionRequestDateDcard"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateSamos", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateSamos = :deletionRequestDateSamos"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateSasw", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateSasw = :deletionRequestDateSasw"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateZaps", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateZaps = :deletionRequestDateZaps"),
    @NamedQuery(name = "CisMemberEntity.findByDateLastChanged", query = "SELECT c FROM CisMemberEntity c WHERE c.dateLastChanged = :dateLastChanged"),
    @NamedQuery(name = "CisMemberEntity.findByVisaMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.visaMemberInd = :visaMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateVisa", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateVisa = :deletionRequestDateVisa"),
    @NamedQuery(name = "CisMemberEntity.findByNupayMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.nupayMemberInd = :nupayMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateNupay", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateNupay = :deletionRequestDateNupay"),
    @NamedQuery(name = "CisMemberEntity.findByAdossMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.adossMemberInd = :adossMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByAdossAcq", query = "SELECT c FROM CisMemberEntity c WHERE c.adossAcq = :adossAcq"),
    @NamedQuery(name = "CisMemberEntity.findByAdossIss", query = "SELECT c FROM CisMemberEntity c WHERE c.adossIss = :adossIss"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateAdoss", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateAdoss = :deletionRequestDateAdoss"),
    @NamedQuery(name = "CisMemberEntity.findByMmtMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.mmtMemberInd = :mmtMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateMmt", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateMmt = :deletionRequestDateMmt"),
    @NamedQuery(name = "CisMemberEntity.findByRtcMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcMemberInd = :rtcMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByRtcMemberNo", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcMemberNo = :rtcMemberNo"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateRtc", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateRtc = :deletionRequestDateRtc"),
    @NamedQuery(name = "CisMemberEntity.findByIcmsMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.icmsMemberInd = :icmsMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateIcms", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateIcms = :deletionRequestDateIcms"),
    @NamedQuery(name = "CisMemberEntity.findByNaedossMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.naedossMemberInd = :naedossMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByNaedossAcq", query = "SELECT c FROM CisMemberEntity c WHERE c.naedossAcq = :naedossAcq"),
    @NamedQuery(name = "CisMemberEntity.findByNaedossIss", query = "SELECT c FROM CisMemberEntity c WHERE c.naedossIss = :naedossIss"),
    @NamedQuery(name = "CisMemberEntity.findByNaedossSevenDayService", query = "SELECT c FROM CisMemberEntity c WHERE c.naedossSevenDayService = :naedossSevenDayService"),
    @NamedQuery(name = "CisMemberEntity.findByDeletionRequestDateNaedoss", query = "SELECT c FROM CisMemberEntity c WHERE c.deletionRequestDateNaedoss = :deletionRequestDateNaedoss"),
    @NamedQuery(name = "CisMemberEntity.findByEdosMaxNoRecs", query = "SELECT c FROM CisMemberEntity c WHERE c.edosMaxNoRecs = :edosMaxNoRecs"),
    @NamedQuery(name = "CisMemberEntity.findByAedosEdoFormatInd", query = "SELECT c FROM CisMemberEntity c WHERE c.aedosEdoFormatInd = :aedosEdoFormatInd"),
    @NamedQuery(name = "CisMemberEntity.findByEdosIssuerTrackingInd", query = "SELECT c FROM CisMemberEntity c WHERE c.edosIssuerTrackingInd = :edosIssuerTrackingInd"),
    @NamedQuery(name = "CisMemberEntity.findByNaedosReceiveDisputesInd", query = "SELECT c FROM CisMemberEntity c WHERE c.naedosReceiveDisputesInd = :naedosReceiveDisputesInd"),
    @NamedQuery(name = "CisMemberEntity.findByRtcFiid", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcFiid = :rtcFiid"),
    @NamedQuery(name = "CisMemberEntity.findByEdosPresentOnUsInd", query = "SELECT c FROM CisMemberEntity c WHERE c.edosPresentOnUsInd = :edosPresentOnUsInd"),
    @NamedQuery(name = "CisMemberEntity.findByEdoMemberInd", query = "SELECT c FROM CisMemberEntity c WHERE c.edoMemberInd = :edoMemberInd"),
    @NamedQuery(name = "CisMemberEntity.findByRtcVatNumber", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcVatNumber = :rtcVatNumber"),
    @NamedQuery(name = "CisMemberEntity.findByRtcNoOfIlfFiles", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcNoOfIlfFiles = :rtcNoOfIlfFiles"),
    @NamedQuery(name = "CisMemberEntity.findByRtcBillingAddress1", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcBillingAddress1 = :rtcBillingAddress1"),
    @NamedQuery(name = "CisMemberEntity.findByRtcBillingAddress2", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcBillingAddress2 = :rtcBillingAddress2"),
    @NamedQuery(name = "CisMemberEntity.findByRtcBillingAddress3", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcBillingAddress3 = :rtcBillingAddress3"),
    @NamedQuery(name = "CisMemberEntity.findByRtcBillingAddress4", query = "SELECT c FROM CisMemberEntity c WHERE c.rtcBillingAddress4 = :rtcBillingAddress4"),
    @NamedQuery(name = "CisMemberEntity.findByCim900XcomInd", query = "SELECT c FROM CisMemberEntity c WHERE c.cim900XcomInd = :cim900XcomInd"),
    @NamedQuery(name = "CisMemberEntity.findByAcMdtMaxNrRecords", query = "SELECT c FROM CisMemberEntity c WHERE c.acMdtMaxNrRecords = :acMdtMaxNrRecords"),
    @NamedQuery(name = "CisMemberEntity.findByAcMdtNrOfDaysProc", query = "SELECT c FROM CisMemberEntity c WHERE c.acMdtNrOfDaysProc = :acMdtNrOfDaysProc"),
    @NamedQuery(name = "CisMemberEntity.findByAcMdtPubHolProc", query = "SELECT c FROM CisMemberEntity c WHERE c.acMdtPubHolProc = :acMdtPubHolProc"),
    @NamedQuery(name = "CisMemberEntity.findByAcMdtInd", query = "SELECT c FROM CisMemberEntity c WHERE c.acMdtInd = :acMdtInd")})
public class CisMemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "MEMBER_NO")
    private String memberNo;
    @Size(max = 6)
    @Column(name = "BA_MEMBER_NO")
    private String baMemberNo;
    @Size(max = 30)
    @Column(name = "MEMBER_NAME")
    private String memberName;
    @Size(max = 12)
    @Column(name = "ABBREV_MEMBER_NAME")
    private String abbrevMemberName;
    @Size(max = 3)
    @Column(name = "MNEMONIC_MEMBER_NAME")
    private String mnemonicMemberName;
    @Size(max = 3)
    @Column(name = "MEMBER_BRANCH_START_RANGE")
    private String memberBranchStartRange;
    @Size(max = 3)
    @Column(name = "MEMBER_BRANCH_END_RANGE")
    private String memberBranchEndRange;
    @Size(max = 30)
    @Column(name = "MEMBER_ADDRESS_1")
    private String memberAddress1;
    @Size(max = 30)
    @Column(name = "MEMBER_ADDRESS_2")
    private String memberAddress2;
    @Size(max = 30)
    @Column(name = "MEMBER_ADDRESS_3")
    private String memberAddress3;
    @Size(max = 30)
    @Column(name = "MEMBER_ADDRESS_4")
    private String memberAddress4;
    @Size(max = 35)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 30)
    @Column(name = "CURRENCY")
    private String currency;
    @Size(max = 3)
    @Column(name = "ABBREV_CURRENCY")
    private String abbrevCurrency;
    @Size(max = 3)
    @Column(name = "MEMBER_LANGUAGE")
    private String memberLanguage;
    @Size(max = 1)
    @Column(name = "MEMBER_STATUS")
    private String memberStatus;
    @Column(name = "MEMBER_SET_LIVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memberSetLive;
    @Size(max = 6)
    @Column(name = "MEMBER_SPONSORED_BY")
    private String memberSponsoredBy;
    @Size(max = 8)
    @Column(name = "MEMBER_SARB_ACCNT")
    private String memberSarbAccnt;
    @Size(max = 2)
    @Column(name = "MEMBER_TAPE_ID")
    private String memberTapeId;
    @Column(name = "MEMBER_SORT_SEQ_NO")
    private Short memberSortSeqNo;
    @Size(max = 30)
    @Column(name = "AGENCY_ADDRESS_1")
    private String agencyAddress1;
    @Size(max = 30)
    @Column(name = "AGENCY_ADDRESS_2")
    private String agencyAddress2;
    @Size(max = 30)
    @Column(name = "AGENCY_ADDRESS_3")
    private String agencyAddress3;
    @Size(max = 1)
    @Column(name = "CLC_MEMBER_IND")
    private String clcMemberInd;
    @Size(max = 3)
    @Column(name = "CLC_CHEQUES_TO")
    private String clcChequesTo;
    @Size(max = 1)
    @Column(name = "CLC_REMITTER")
    private String clcRemitter;
    @Size(max = 4)
    @Column(name = "CLC_OUTPUT_CONTENT")
    private String clcOutputContent;
    @Size(max = 4)
    @Column(name = "CLC_OUTPUT_FORMAT")
    private String clcOutputFormat;
    @Size(max = 8)
    @Column(name = "CLC_DEST_BRANCH")
    private String clcDestBranch;
    @Size(max = 1)
    @Column(name = "CLC_CL6_REPORT_REQ")
    private String clcCl6ReportReq;
    @Size(max = 1)
    @Column(name = "CCARD_MEMBER_IND")
    private String ccardMemberInd;
    @Size(max = 1)
    @Column(name = "DCARD_MEMBER_IND")
    private String dcardMemberInd;
    @Size(max = 1)
    @Column(name = "EFT_MEMBER_IND")
    private String eftMemberInd;
    @Size(max = 1)
    @Column(name = "EFT_COMPUTERISED_IND")
    private String eftComputerisedInd;
    @Size(max = 8)
    @Column(name = "EFT_CTRL_BREAK_COUNT")
    private String eftCtrlBreakCount;
    @Size(max = 1)
    @Column(name = "EFT_DEBIT_ALLOW_TO_SAVINGS")
    private String eftDebitAllowToSavings;
    @Size(max = 2)
    @Column(name = "EFT_ELEC_CHG_FILE")
    private String eftElecChgFile;
    @Size(max = 2)
    @Column(name = "EFT_FORMAT")
    private String eftFormat;
    @Size(max = 1)
    @Column(name = "EFT_NON_COMP_ELEC_OUT")
    private String eftNonCompElecOut;
    @Size(max = 3)
    @Column(name = "EFT_OUTPUT_CENTRE")
    private String eftOutputCentre;
    @Size(max = 2)
    @Column(name = "EFT_SSV_TEST_LIVE_IND")
    private String eftSsvTestLiveInd;
    @Size(max = 2)
    @Column(name = "EFT_SSV_OUTPUT_IND")
    private String eftSsvOutputInd;
    @Size(max = 8)
    @Column(name = "EFT_SSV_MAX_SIZE_TRANS_FILE")
    private String eftSsvMaxSizeTransFile;
    @Size(max = 8)
    @Column(name = "EFT_SSV_MAX_SIZE_TAPE_FILE")
    private String eftSsvMaxSizeTapeFile;
    @Size(max = 2)
    @Column(name = "EFT_DATED_TEST_LIVE_IND")
    private String eftDatedTestLiveInd;
    @Size(max = 2)
    @Column(name = "EFT_DATED_OUTPUT_IND")
    private String eftDatedOutputInd;
    @Size(max = 8)
    @Column(name = "EFT_DATED_MAX_SIZE_TRANS_FILE")
    private String eftDatedMaxSizeTransFile;
    @Size(max = 8)
    @Column(name = "EFT_DATED_MAX_SIZE_TAPE_FILE")
    private String eftDatedMaxSizeTapeFile;
    @Size(max = 2)
    @Column(name = "EFT_RECALLS_TEST_LIVE_IND")
    private String eftRecallsTestLiveInd;
    @Size(max = 2)
    @Column(name = "EFT_RECALL_OUTPUT_IND")
    private String eftRecallOutputInd;
    @Size(max = 2)
    @Column(name = "EFT_UNPAIDS_TEST_LIVE_IND")
    private String eftUnpaidsTestLiveInd;
    @Size(max = 2)
    @Column(name = "EFT_UNPAIDS_OUTPUT_IND")
    private String eftUnpaidsOutputInd;
    @Size(max = 1)
    @Column(name = "SAMOS_MEMBER_IND")
    private String samosMemberInd;
    @Size(max = 12)
    @Column(name = "SAMOS_BIC_CODE_LIVE")
    private String samosBicCodeLive;
    @Size(max = 12)
    @Column(name = "SAMOS_BIC_CODE_TEST")
    private String samosBicCodeTest;
    @Size(max = 12)
    @Column(name = "SAMOS_ACC_NO_LIVE")
    private String samosAccNoLive;
    @Size(max = 12)
    @Column(name = "SAMOS_ACC_NO_TEST")
    private String samosAccNoTest;
    @Size(max = 6)
    @Column(name = "SAMOS_SPONSORED_BY")
    private String samosSponsoredBy;
    @Size(max = 1)
    @Column(name = "SAMOS_SPONSOR_OTHERS")
    private String samosSponsorOthers;
    @Size(max = 1)
    @Column(name = "SAMOS_RECEIVE_MT298")
    private String samosReceiveMt298;
    @Size(max = 1)
    @Column(name = "SASW_MEMBER_IND")
    private String saswMemberInd;
    @Size(max = 1)
    @Column(name = "ZAPS_MEMBER_IND")
    private String zapsMemberInd;
    @Size(max = 12)
    @Column(name = "ZAPS_BIC_CODE_LIVE")
    private String zapsBicCodeLive;
    @Size(max = 12)
    @Column(name = "ZAPS_BIC_CODE_TEST")
    private String zapsBicCodeTest;
    @Size(max = 12)
    @Column(name = "ZAPS_ITEM_LIMIT")
    private String zapsItemLimit;
    @Size(max = 1)
    @Column(name = "ZAPS_RECEIVE_MT950")
    private String zapsReceiveMt950;
    @Size(max = 11)
    @Column(name = "ZAPS_MT950_ACC_NO")
    private String zapsMt950AccNo;
    @Size(max = 1)
    @Column(name = "ZAPS_OUTPUT_TYPE")
    private String zapsOutputType;
    @Size(max = 1)
    @Column(name = "BAM_MEMBER_IND")
    private String bamMemberInd;
    @Column(name = "DELETION_REQUEST_DATE_BAM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateBam;
    @Column(name = "DELETION_REQUEST_DATE_CLC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateClc;
    @Column(name = "DELETION_REQUEST_DATE_EFT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateEft;
    @Column(name = "DELETION_REQUEST_DATE_CCARD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateCcard;
    @Column(name = "DELETION_REQUEST_DATE_DCARD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateDcard;
    @Column(name = "DELETION_REQUEST_DATE_SAMOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateSamos;
    @Column(name = "DELETION_REQUEST_DATE_SASW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateSasw;
    @Column(name = "DELETION_REQUEST_DATE_ZAPS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateZaps;
    @Column(name = "DATE_LAST_CHANGED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastChanged;
    @Size(max = 1)
    @Column(name = "VISA_MEMBER_IND")
    private String visaMemberInd;
    @Column(name = "DELETION_REQUEST_DATE_VISA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateVisa;
    @Size(max = 1)
    @Column(name = "NUPAY_MEMBER_IND")
    private String nupayMemberInd;
    @Column(name = "DELETION_REQUEST_DATE_NUPAY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateNupay;
    @Size(max = 1)
    @Column(name = "ADOSS_MEMBER_IND")
    private String adossMemberInd;
    @Size(max = 1)
    @Column(name = "ADOSS_ACQ")
    private String adossAcq;
    @Size(max = 1)
    @Column(name = "ADOSS_ISS")
    private String adossIss;
    @Column(name = "DELETION_REQUEST_DATE_ADOSS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateAdoss;
    @Size(max = 1)
    @Column(name = "MMT_MEMBER_IND")
    private String mmtMemberInd;
    @Column(name = "DELETION_REQUEST_DATE_MMT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateMmt;
    @Size(max = 1)
    @Column(name = "RTC_MEMBER_IND")
    private String rtcMemberInd;
    @Size(max = 2)
    @Column(name = "RTC_MEMBER_NO")
    private String rtcMemberNo;
    @Column(name = "DELETION_REQUEST_DATE_RTC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateRtc;
    @Size(max = 1)
    @Column(name = "ICMS_MEMBER_IND")
    private String icmsMemberInd;
    @Column(name = "DELETION_REQUEST_DATE_ICMS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateIcms;
    @Size(max = 1)
    @Column(name = "NAEDOSS_MEMBER_IND")
    private String naedossMemberInd;
    @Size(max = 1)
    @Column(name = "NAEDOSS_ACQ")
    private String naedossAcq;
    @Size(max = 1)
    @Column(name = "NAEDOSS_ISS")
    private String naedossIss;
    @Size(max = 1)
    @Column(name = "NAEDOSS_SEVEN_DAY_SERVICE")
    private String naedossSevenDayService;
    @Column(name = "DELETION_REQUEST_DATE_NAEDOSS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateNaedoss;
    @Column(name = "EDOS_MAX_NO_RECS")
    private Long edosMaxNoRecs;
    @Size(max = 1)
    @Column(name = "AEDOS_EDO_FORMAT_IND")
    private String aedosEdoFormatInd;
    @Size(max = 1)
    @Column(name = "EDOS_ISSUER_TRACKING_IND")
    private String edosIssuerTrackingInd;
    @Size(max = 1)
    @Column(name = "NAEDOS_RECEIVE_DISPUTES_IND")
    private String naedosReceiveDisputesInd;
    @Column(name = "RTC_FIID")
    private Short rtcFiid;
    @Size(max = 1)
    @Column(name = "EDOS_PRESENT_ON_US_IND")
    private String edosPresentOnUsInd;
    @Size(max = 1)
    @Column(name = "EDO_MEMBER_IND")
    private String edoMemberInd;
    @Size(max = 15)
    @Column(name = "RTC_VAT_NUMBER")
    private String rtcVatNumber;
    @Column(name = "RTC_NO_OF_ILF_FILES")
    private Short rtcNoOfIlfFiles;
    @Size(max = 30)
    @Column(name = "RTC_BILLING_ADDRESS_1")
    private String rtcBillingAddress1;
    @Size(max = 30)
    @Column(name = "RTC_BILLING_ADDRESS_2")
    private String rtcBillingAddress2;
    @Size(max = 30)
    @Column(name = "RTC_BILLING_ADDRESS_3")
    private String rtcBillingAddress3;
    @Size(max = 30)
    @Column(name = "RTC_BILLING_ADDRESS_4")
    private String rtcBillingAddress4;
    @Size(max = 1)
    @Column(name = "CIM900_XCOM_IND")
    private String cim900XcomInd;
    @Column(name = "AC_MDT_MAX_NR_RECORDS")
    private Integer acMdtMaxNrRecords;
    @Column(name = "AC_MDT_NR_OF_DAYS_PROC")
    private Short acMdtNrOfDaysProc;
    @Size(max = 1)
    @Column(name = "AC_MDT_PUB_HOL_PROC")
    private String acMdtPubHolProc;
    @Size(max = 1)
    @Column(name = "AC_MDT_IND")
    private String acMdtInd;
    
    

    public CisMemberEntity() {
    }

    public CisMemberEntity(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getBaMemberNo() {
        return baMemberNo;
    }

    public void setBaMemberNo(String baMemberNo) {
        this.baMemberNo = baMemberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAbbrevMemberName() {
        return abbrevMemberName;
    }

    public void setAbbrevMemberName(String abbrevMemberName) {
        this.abbrevMemberName = abbrevMemberName;
    }

    public String getMnemonicMemberName() {
        return mnemonicMemberName;
    }

    public void setMnemonicMemberName(String mnemonicMemberName) {
        this.mnemonicMemberName = mnemonicMemberName;
    }

    public String getMemberBranchStartRange() {
        return memberBranchStartRange;
    }

    public void setMemberBranchStartRange(String memberBranchStartRange) {
        this.memberBranchStartRange = memberBranchStartRange;
    }

    public String getMemberBranchEndRange() {
        return memberBranchEndRange;
    }

    public void setMemberBranchEndRange(String memberBranchEndRange) {
        this.memberBranchEndRange = memberBranchEndRange;
    }

    public String getMemberAddress1() {
        return memberAddress1;
    }

    public void setMemberAddress1(String memberAddress1) {
        this.memberAddress1 = memberAddress1;
    }

    public String getMemberAddress2() {
        return memberAddress2;
    }

    public void setMemberAddress2(String memberAddress2) {
        this.memberAddress2 = memberAddress2;
    }

    public String getMemberAddress3() {
        return memberAddress3;
    }

    public void setMemberAddress3(String memberAddress3) {
        this.memberAddress3 = memberAddress3;
    }

    public String getMemberAddress4() {
        return memberAddress4;
    }

    public void setMemberAddress4(String memberAddress4) {
        this.memberAddress4 = memberAddress4;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAbbrevCurrency() {
        return abbrevCurrency;
    }

    public void setAbbrevCurrency(String abbrevCurrency) {
        this.abbrevCurrency = abbrevCurrency;
    }

    public String getMemberLanguage() {
        return memberLanguage;
    }

    public void setMemberLanguage(String memberLanguage) {
        this.memberLanguage = memberLanguage;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getMemberSetLive() {
        return memberSetLive;
    }

    public void setMemberSetLive(Date memberSetLive) {
        this.memberSetLive = memberSetLive;
    }

    public String getMemberSponsoredBy() {
        return memberSponsoredBy;
    }

    public void setMemberSponsoredBy(String memberSponsoredBy) {
        this.memberSponsoredBy = memberSponsoredBy;
    }

    public String getMemberSarbAccnt() {
        return memberSarbAccnt;
    }

    public void setMemberSarbAccnt(String memberSarbAccnt) {
        this.memberSarbAccnt = memberSarbAccnt;
    }

    public String getMemberTapeId() {
        return memberTapeId;
    }

    public void setMemberTapeId(String memberTapeId) {
        this.memberTapeId = memberTapeId;
    }

    public Short getMemberSortSeqNo() {
        return memberSortSeqNo;
    }

    public void setMemberSortSeqNo(Short memberSortSeqNo) {
        this.memberSortSeqNo = memberSortSeqNo;
    }

    public String getAgencyAddress1() {
        return agencyAddress1;
    }

    public void setAgencyAddress1(String agencyAddress1) {
        this.agencyAddress1 = agencyAddress1;
    }

    public String getAgencyAddress2() {
        return agencyAddress2;
    }

    public void setAgencyAddress2(String agencyAddress2) {
        this.agencyAddress2 = agencyAddress2;
    }

    public String getAgencyAddress3() {
        return agencyAddress3;
    }

    public void setAgencyAddress3(String agencyAddress3) {
        this.agencyAddress3 = agencyAddress3;
    }

    public String getClcMemberInd() {
        return clcMemberInd;
    }

    public void setClcMemberInd(String clcMemberInd) {
        this.clcMemberInd = clcMemberInd;
    }

    public String getClcChequesTo() {
        return clcChequesTo;
    }

    public void setClcChequesTo(String clcChequesTo) {
        this.clcChequesTo = clcChequesTo;
    }

    public String getClcRemitter() {
        return clcRemitter;
    }

    public void setClcRemitter(String clcRemitter) {
        this.clcRemitter = clcRemitter;
    }

    public String getClcOutputContent() {
        return clcOutputContent;
    }

    public void setClcOutputContent(String clcOutputContent) {
        this.clcOutputContent = clcOutputContent;
    }

    public String getClcOutputFormat() {
        return clcOutputFormat;
    }

    public void setClcOutputFormat(String clcOutputFormat) {
        this.clcOutputFormat = clcOutputFormat;
    }

    public String getClcDestBranch() {
        return clcDestBranch;
    }

    public void setClcDestBranch(String clcDestBranch) {
        this.clcDestBranch = clcDestBranch;
    }

    public String getClcCl6ReportReq() {
        return clcCl6ReportReq;
    }

    public void setClcCl6ReportReq(String clcCl6ReportReq) {
        this.clcCl6ReportReq = clcCl6ReportReq;
    }

    public String getCcardMemberInd() {
        return ccardMemberInd;
    }

    public void setCcardMemberInd(String ccardMemberInd) {
        this.ccardMemberInd = ccardMemberInd;
    }

    public String getDcardMemberInd() {
        return dcardMemberInd;
    }

    public void setDcardMemberInd(String dcardMemberInd) {
        this.dcardMemberInd = dcardMemberInd;
    }

    public String getEftMemberInd() {
        return eftMemberInd;
    }

    public void setEftMemberInd(String eftMemberInd) {
        this.eftMemberInd = eftMemberInd;
    }

    public String getEftComputerisedInd() {
        return eftComputerisedInd;
    }

    public void setEftComputerisedInd(String eftComputerisedInd) {
        this.eftComputerisedInd = eftComputerisedInd;
    }

    public String getEftCtrlBreakCount() {
        return eftCtrlBreakCount;
    }

    public void setEftCtrlBreakCount(String eftCtrlBreakCount) {
        this.eftCtrlBreakCount = eftCtrlBreakCount;
    }

    public String getEftDebitAllowToSavings() {
        return eftDebitAllowToSavings;
    }

    public void setEftDebitAllowToSavings(String eftDebitAllowToSavings) {
        this.eftDebitAllowToSavings = eftDebitAllowToSavings;
    }

    public String getEftElecChgFile() {
        return eftElecChgFile;
    }

    public void setEftElecChgFile(String eftElecChgFile) {
        this.eftElecChgFile = eftElecChgFile;
    }

    public String getEftFormat() {
        return eftFormat;
    }

    public void setEftFormat(String eftFormat) {
        this.eftFormat = eftFormat;
    }

    public String getEftNonCompElecOut() {
        return eftNonCompElecOut;
    }

    public void setEftNonCompElecOut(String eftNonCompElecOut) {
        this.eftNonCompElecOut = eftNonCompElecOut;
    }

    public String getEftOutputCentre() {
        return eftOutputCentre;
    }

    public void setEftOutputCentre(String eftOutputCentre) {
        this.eftOutputCentre = eftOutputCentre;
    }

    public String getEftSsvTestLiveInd() {
        return eftSsvTestLiveInd;
    }

    public void setEftSsvTestLiveInd(String eftSsvTestLiveInd) {
        this.eftSsvTestLiveInd = eftSsvTestLiveInd;
    }

    public String getEftSsvOutputInd() {
        return eftSsvOutputInd;
    }

    public void setEftSsvOutputInd(String eftSsvOutputInd) {
        this.eftSsvOutputInd = eftSsvOutputInd;
    }

    public String getEftSsvMaxSizeTransFile() {
        return eftSsvMaxSizeTransFile;
    }

    public void setEftSsvMaxSizeTransFile(String eftSsvMaxSizeTransFile) {
        this.eftSsvMaxSizeTransFile = eftSsvMaxSizeTransFile;
    }

    public String getEftSsvMaxSizeTapeFile() {
        return eftSsvMaxSizeTapeFile;
    }

    public void setEftSsvMaxSizeTapeFile(String eftSsvMaxSizeTapeFile) {
        this.eftSsvMaxSizeTapeFile = eftSsvMaxSizeTapeFile;
    }

    public String getEftDatedTestLiveInd() {
        return eftDatedTestLiveInd;
    }

    public void setEftDatedTestLiveInd(String eftDatedTestLiveInd) {
        this.eftDatedTestLiveInd = eftDatedTestLiveInd;
    }

    public String getEftDatedOutputInd() {
        return eftDatedOutputInd;
    }

    public void setEftDatedOutputInd(String eftDatedOutputInd) {
        this.eftDatedOutputInd = eftDatedOutputInd;
    }

    public String getEftDatedMaxSizeTransFile() {
        return eftDatedMaxSizeTransFile;
    }

    public void setEftDatedMaxSizeTransFile(String eftDatedMaxSizeTransFile) {
        this.eftDatedMaxSizeTransFile = eftDatedMaxSizeTransFile;
    }

    public String getEftDatedMaxSizeTapeFile() {
        return eftDatedMaxSizeTapeFile;
    }

    public void setEftDatedMaxSizeTapeFile(String eftDatedMaxSizeTapeFile) {
        this.eftDatedMaxSizeTapeFile = eftDatedMaxSizeTapeFile;
    }

    public String getEftRecallsTestLiveInd() {
        return eftRecallsTestLiveInd;
    }

    public void setEftRecallsTestLiveInd(String eftRecallsTestLiveInd) {
        this.eftRecallsTestLiveInd = eftRecallsTestLiveInd;
    }

    public String getEftRecallOutputInd() {
        return eftRecallOutputInd;
    }

    public void setEftRecallOutputInd(String eftRecallOutputInd) {
        this.eftRecallOutputInd = eftRecallOutputInd;
    }

    public String getEftUnpaidsTestLiveInd() {
        return eftUnpaidsTestLiveInd;
    }

    public void setEftUnpaidsTestLiveInd(String eftUnpaidsTestLiveInd) {
        this.eftUnpaidsTestLiveInd = eftUnpaidsTestLiveInd;
    }

    public String getEftUnpaidsOutputInd() {
        return eftUnpaidsOutputInd;
    }

    public void setEftUnpaidsOutputInd(String eftUnpaidsOutputInd) {
        this.eftUnpaidsOutputInd = eftUnpaidsOutputInd;
    }

    public String getSamosMemberInd() {
        return samosMemberInd;
    }

    public void setSamosMemberInd(String samosMemberInd) {
        this.samosMemberInd = samosMemberInd;
    }

    public String getSamosBicCodeLive() {
        return samosBicCodeLive;
    }

    public void setSamosBicCodeLive(String samosBicCodeLive) {
        this.samosBicCodeLive = samosBicCodeLive;
    }

    public String getSamosBicCodeTest() {
        return samosBicCodeTest;
    }

    public void setSamosBicCodeTest(String samosBicCodeTest) {
        this.samosBicCodeTest = samosBicCodeTest;
    }

    public String getSamosAccNoLive() {
        return samosAccNoLive;
    }

    public void setSamosAccNoLive(String samosAccNoLive) {
        this.samosAccNoLive = samosAccNoLive;
    }

    public String getSamosAccNoTest() {
        return samosAccNoTest;
    }

    public void setSamosAccNoTest(String samosAccNoTest) {
        this.samosAccNoTest = samosAccNoTest;
    }

    public String getSamosSponsoredBy() {
        return samosSponsoredBy;
    }

    public void setSamosSponsoredBy(String samosSponsoredBy) {
        this.samosSponsoredBy = samosSponsoredBy;
    }

    public String getSamosSponsorOthers() {
        return samosSponsorOthers;
    }

    public void setSamosSponsorOthers(String samosSponsorOthers) {
        this.samosSponsorOthers = samosSponsorOthers;
    }

    public String getSamosReceiveMt298() {
        return samosReceiveMt298;
    }

    public void setSamosReceiveMt298(String samosReceiveMt298) {
        this.samosReceiveMt298 = samosReceiveMt298;
    }

    public String getSaswMemberInd() {
        return saswMemberInd;
    }

    public void setSaswMemberInd(String saswMemberInd) {
        this.saswMemberInd = saswMemberInd;
    }

    public String getZapsMemberInd() {
        return zapsMemberInd;
    }

    public void setZapsMemberInd(String zapsMemberInd) {
        this.zapsMemberInd = zapsMemberInd;
    }

    public String getZapsBicCodeLive() {
        return zapsBicCodeLive;
    }

    public void setZapsBicCodeLive(String zapsBicCodeLive) {
        this.zapsBicCodeLive = zapsBicCodeLive;
    }

    public String getZapsBicCodeTest() {
        return zapsBicCodeTest;
    }

    public void setZapsBicCodeTest(String zapsBicCodeTest) {
        this.zapsBicCodeTest = zapsBicCodeTest;
    }

    public String getZapsItemLimit() {
        return zapsItemLimit;
    }

    public void setZapsItemLimit(String zapsItemLimit) {
        this.zapsItemLimit = zapsItemLimit;
    }

    public String getZapsReceiveMt950() {
        return zapsReceiveMt950;
    }

    public void setZapsReceiveMt950(String zapsReceiveMt950) {
        this.zapsReceiveMt950 = zapsReceiveMt950;
    }

    public String getZapsMt950AccNo() {
        return zapsMt950AccNo;
    }

    public void setZapsMt950AccNo(String zapsMt950AccNo) {
        this.zapsMt950AccNo = zapsMt950AccNo;
    }

    public String getZapsOutputType() {
        return zapsOutputType;
    }

    public void setZapsOutputType(String zapsOutputType) {
        this.zapsOutputType = zapsOutputType;
    }

    public String getBamMemberInd() {
        return bamMemberInd;
    }

    public void setBamMemberInd(String bamMemberInd) {
        this.bamMemberInd = bamMemberInd;
    }

    public Date getDeletionRequestDateBam() {
        return deletionRequestDateBam;
    }

    public void setDeletionRequestDateBam(Date deletionRequestDateBam) {
        this.deletionRequestDateBam = deletionRequestDateBam;
    }

    public Date getDeletionRequestDateClc() {
        return deletionRequestDateClc;
    }

    public void setDeletionRequestDateClc(Date deletionRequestDateClc) {
        this.deletionRequestDateClc = deletionRequestDateClc;
    }

    public Date getDeletionRequestDateEft() {
        return deletionRequestDateEft;
    }

    public void setDeletionRequestDateEft(Date deletionRequestDateEft) {
        this.deletionRequestDateEft = deletionRequestDateEft;
    }

    public Date getDeletionRequestDateCcard() {
        return deletionRequestDateCcard;
    }

    public void setDeletionRequestDateCcard(Date deletionRequestDateCcard) {
        this.deletionRequestDateCcard = deletionRequestDateCcard;
    }

    public Date getDeletionRequestDateDcard() {
        return deletionRequestDateDcard;
    }

    public void setDeletionRequestDateDcard(Date deletionRequestDateDcard) {
        this.deletionRequestDateDcard = deletionRequestDateDcard;
    }

    public Date getDeletionRequestDateSamos() {
        return deletionRequestDateSamos;
    }

    public void setDeletionRequestDateSamos(Date deletionRequestDateSamos) {
        this.deletionRequestDateSamos = deletionRequestDateSamos;
    }

    public Date getDeletionRequestDateSasw() {
        return deletionRequestDateSasw;
    }

    public void setDeletionRequestDateSasw(Date deletionRequestDateSasw) {
        this.deletionRequestDateSasw = deletionRequestDateSasw;
    }

    public Date getDeletionRequestDateZaps() {
        return deletionRequestDateZaps;
    }

    public void setDeletionRequestDateZaps(Date deletionRequestDateZaps) {
        this.deletionRequestDateZaps = deletionRequestDateZaps;
    }

    public Date getDateLastChanged() {
        return dateLastChanged;
    }

    public void setDateLastChanged(Date dateLastChanged) {
        this.dateLastChanged = dateLastChanged;
    }

    public String getVisaMemberInd() {
        return visaMemberInd;
    }

    public void setVisaMemberInd(String visaMemberInd) {
        this.visaMemberInd = visaMemberInd;
    }

    public Date getDeletionRequestDateVisa() {
        return deletionRequestDateVisa;
    }

    public void setDeletionRequestDateVisa(Date deletionRequestDateVisa) {
        this.deletionRequestDateVisa = deletionRequestDateVisa;
    }

    public String getNupayMemberInd() {
        return nupayMemberInd;
    }

    public void setNupayMemberInd(String nupayMemberInd) {
        this.nupayMemberInd = nupayMemberInd;
    }

    public Date getDeletionRequestDateNupay() {
        return deletionRequestDateNupay;
    }

    public void setDeletionRequestDateNupay(Date deletionRequestDateNupay) {
        this.deletionRequestDateNupay = deletionRequestDateNupay;
    }

    public String getAdossMemberInd() {
        return adossMemberInd;
    }

    public void setAdossMemberInd(String adossMemberInd) {
        this.adossMemberInd = adossMemberInd;
    }

    public String getAdossAcq() {
        return adossAcq;
    }

    public void setAdossAcq(String adossAcq) {
        this.adossAcq = adossAcq;
    }

    public String getAdossIss() {
        return adossIss;
    }

    public void setAdossIss(String adossIss) {
        this.adossIss = adossIss;
    }

    public Date getDeletionRequestDateAdoss() {
        return deletionRequestDateAdoss;
    }

    public void setDeletionRequestDateAdoss(Date deletionRequestDateAdoss) {
        this.deletionRequestDateAdoss = deletionRequestDateAdoss;
    }

    public String getMmtMemberInd() {
        return mmtMemberInd;
    }

    public void setMmtMemberInd(String mmtMemberInd) {
        this.mmtMemberInd = mmtMemberInd;
    }

    public Date getDeletionRequestDateMmt() {
        return deletionRequestDateMmt;
    }

    public void setDeletionRequestDateMmt(Date deletionRequestDateMmt) {
        this.deletionRequestDateMmt = deletionRequestDateMmt;
    }

    public String getRtcMemberInd() {
        return rtcMemberInd;
    }

    public void setRtcMemberInd(String rtcMemberInd) {
        this.rtcMemberInd = rtcMemberInd;
    }

    public String getRtcMemberNo() {
        return rtcMemberNo;
    }

    public void setRtcMemberNo(String rtcMemberNo) {
        this.rtcMemberNo = rtcMemberNo;
    }

    public Date getDeletionRequestDateRtc() {
        return deletionRequestDateRtc;
    }

    public void setDeletionRequestDateRtc(Date deletionRequestDateRtc) {
        this.deletionRequestDateRtc = deletionRequestDateRtc;
    }

    public String getIcmsMemberInd() {
        return icmsMemberInd;
    }

    public void setIcmsMemberInd(String icmsMemberInd) {
        this.icmsMemberInd = icmsMemberInd;
    }

    public Date getDeletionRequestDateIcms() {
        return deletionRequestDateIcms;
    }

    public void setDeletionRequestDateIcms(Date deletionRequestDateIcms) {
        this.deletionRequestDateIcms = deletionRequestDateIcms;
    }

    public String getNaedossMemberInd() {
        return naedossMemberInd;
    }

    public void setNaedossMemberInd(String naedossMemberInd) {
        this.naedossMemberInd = naedossMemberInd;
    }

    public String getNaedossAcq() {
        return naedossAcq;
    }

    public void setNaedossAcq(String naedossAcq) {
        this.naedossAcq = naedossAcq;
    }

    public String getNaedossIss() {
        return naedossIss;
    }

    public void setNaedossIss(String naedossIss) {
        this.naedossIss = naedossIss;
    }

    public String getNaedossSevenDayService() {
        return naedossSevenDayService;
    }

    public void setNaedossSevenDayService(String naedossSevenDayService) {
        this.naedossSevenDayService = naedossSevenDayService;
    }

    public Date getDeletionRequestDateNaedoss() {
        return deletionRequestDateNaedoss;
    }

    public void setDeletionRequestDateNaedoss(Date deletionRequestDateNaedoss) {
        this.deletionRequestDateNaedoss = deletionRequestDateNaedoss;
    }

    public Long getEdosMaxNoRecs() {
        return edosMaxNoRecs;
    }

    public void setEdosMaxNoRecs(Long edosMaxNoRecs) {
        this.edosMaxNoRecs = edosMaxNoRecs;
    }

    public String getAedosEdoFormatInd() {
        return aedosEdoFormatInd;
    }

    public void setAedosEdoFormatInd(String aedosEdoFormatInd) {
        this.aedosEdoFormatInd = aedosEdoFormatInd;
    }

    public String getEdosIssuerTrackingInd() {
        return edosIssuerTrackingInd;
    }

    public void setEdosIssuerTrackingInd(String edosIssuerTrackingInd) {
        this.edosIssuerTrackingInd = edosIssuerTrackingInd;
    }

    public String getNaedosReceiveDisputesInd() {
        return naedosReceiveDisputesInd;
    }

    public void setNaedosReceiveDisputesInd(String naedosReceiveDisputesInd) {
        this.naedosReceiveDisputesInd = naedosReceiveDisputesInd;
    }

    public Short getRtcFiid() {
        return rtcFiid;
    }

    public void setRtcFiid(Short rtcFiid) {
        this.rtcFiid = rtcFiid;
    }

    public String getEdosPresentOnUsInd() {
        return edosPresentOnUsInd;
    }

    public void setEdosPresentOnUsInd(String edosPresentOnUsInd) {
        this.edosPresentOnUsInd = edosPresentOnUsInd;
    }

    public String getEdoMemberInd() {
        return edoMemberInd;
    }

    public void setEdoMemberInd(String edoMemberInd) {
        this.edoMemberInd = edoMemberInd;
    }

    public String getRtcVatNumber() {
        return rtcVatNumber;
    }

    public void setRtcVatNumber(String rtcVatNumber) {
        this.rtcVatNumber = rtcVatNumber;
    }

    public Short getRtcNoOfIlfFiles() {
        return rtcNoOfIlfFiles;
    }

    public void setRtcNoOfIlfFiles(Short rtcNoOfIlfFiles) {
        this.rtcNoOfIlfFiles = rtcNoOfIlfFiles;
    }

    public String getRtcBillingAddress1() {
        return rtcBillingAddress1;
    }

    public void setRtcBillingAddress1(String rtcBillingAddress1) {
        this.rtcBillingAddress1 = rtcBillingAddress1;
    }

    public String getRtcBillingAddress2() {
        return rtcBillingAddress2;
    }

    public void setRtcBillingAddress2(String rtcBillingAddress2) {
        this.rtcBillingAddress2 = rtcBillingAddress2;
    }

    public String getRtcBillingAddress3() {
        return rtcBillingAddress3;
    }

    public void setRtcBillingAddress3(String rtcBillingAddress3) {
        this.rtcBillingAddress3 = rtcBillingAddress3;
    }

    public String getRtcBillingAddress4() {
        return rtcBillingAddress4;
    }

    public void setRtcBillingAddress4(String rtcBillingAddress4) {
        this.rtcBillingAddress4 = rtcBillingAddress4;
    }

    public String getCim900XcomInd() {
        return cim900XcomInd;
    }

    public void setCim900XcomInd(String cim900XcomInd) {
        this.cim900XcomInd = cim900XcomInd;
    }
    
    
    public Integer getAcMdtMaxNrRecords() {
        return acMdtMaxNrRecords;
    }

    public void setAcMdtMaxNrRecords(Integer acMdtMaxNrRecords) {
        this.acMdtMaxNrRecords = acMdtMaxNrRecords;
    }

    public Short getAcMdtNrOfDaysProc() {
        return acMdtNrOfDaysProc;
    }

    public void setAcMdtNrOfDaysProc(Short acMdtNrOfDaysProc) {
        this.acMdtNrOfDaysProc = acMdtNrOfDaysProc;
    }

    public String getAcMdtPubHolProc() {
        return acMdtPubHolProc;
    }

    public void setAcMdtPubHolProc(String acMdtPubHolProc) {
        this.acMdtPubHolProc = acMdtPubHolProc;
    }

    public String getAcMdtInd() {
        return acMdtInd;
    }

    public void setAcMdtInd(String acMdtInd) {
        this.acMdtInd = acMdtInd;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((abbrevCurrency == null) ? 0 : abbrevCurrency.hashCode());
		result = prime
				* result
				+ ((abbrevMemberName == null) ? 0 : abbrevMemberName.hashCode());
		result = prime * result
				+ ((acMdtInd == null) ? 0 : acMdtInd.hashCode());
		result = prime
				* result
				+ ((acMdtMaxNrRecords == null) ? 0 : acMdtMaxNrRecords
						.hashCode());
		result = prime
				* result
				+ ((acMdtNrOfDaysProc == null) ? 0 : acMdtNrOfDaysProc
						.hashCode());
		result = prime * result
				+ ((acMdtPubHolProc == null) ? 0 : acMdtPubHolProc.hashCode());
		result = prime * result
				+ ((adossAcq == null) ? 0 : adossAcq.hashCode());
		result = prime * result
				+ ((adossIss == null) ? 0 : adossIss.hashCode());
		result = prime * result
				+ ((adossMemberInd == null) ? 0 : adossMemberInd.hashCode());
		result = prime
				* result
				+ ((aedosEdoFormatInd == null) ? 0 : aedosEdoFormatInd
						.hashCode());
		result = prime * result
				+ ((agencyAddress1 == null) ? 0 : agencyAddress1.hashCode());
		result = prime * result
				+ ((agencyAddress2 == null) ? 0 : agencyAddress2.hashCode());
		result = prime * result
				+ ((agencyAddress3 == null) ? 0 : agencyAddress3.hashCode());
		result = prime * result
				+ ((baMemberNo == null) ? 0 : baMemberNo.hashCode());
		result = prime * result
				+ ((bamMemberInd == null) ? 0 : bamMemberInd.hashCode());
		result = prime * result
				+ ((ccardMemberInd == null) ? 0 : ccardMemberInd.hashCode());
		result = prime * result
				+ ((cim900XcomInd == null) ? 0 : cim900XcomInd.hashCode());
		result = prime * result
				+ ((clcChequesTo == null) ? 0 : clcChequesTo.hashCode());
		result = prime * result
				+ ((clcCl6ReportReq == null) ? 0 : clcCl6ReportReq.hashCode());
		result = prime * result
				+ ((clcDestBranch == null) ? 0 : clcDestBranch.hashCode());
		result = prime * result
				+ ((clcMemberInd == null) ? 0 : clcMemberInd.hashCode());
		result = prime
				* result
				+ ((clcOutputContent == null) ? 0 : clcOutputContent.hashCode());
		result = prime * result
				+ ((clcOutputFormat == null) ? 0 : clcOutputFormat.hashCode());
		result = prime * result
				+ ((clcRemitter == null) ? 0 : clcRemitter.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result
				+ ((dateLastChanged == null) ? 0 : dateLastChanged.hashCode());
		result = prime * result
				+ ((dcardMemberInd == null) ? 0 : dcardMemberInd.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateAdoss == null) ? 0
						: deletionRequestDateAdoss.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateBam == null) ? 0
						: deletionRequestDateBam.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateCcard == null) ? 0
						: deletionRequestDateCcard.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateClc == null) ? 0
						: deletionRequestDateClc.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateDcard == null) ? 0
						: deletionRequestDateDcard.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateEft == null) ? 0
						: deletionRequestDateEft.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateIcms == null) ? 0
						: deletionRequestDateIcms.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateMmt == null) ? 0
						: deletionRequestDateMmt.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateNaedoss == null) ? 0
						: deletionRequestDateNaedoss.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateNupay == null) ? 0
						: deletionRequestDateNupay.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateRtc == null) ? 0
						: deletionRequestDateRtc.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateSamos == null) ? 0
						: deletionRequestDateSamos.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateSasw == null) ? 0
						: deletionRequestDateSasw.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateVisa == null) ? 0
						: deletionRequestDateVisa.hashCode());
		result = prime
				* result
				+ ((deletionRequestDateZaps == null) ? 0
						: deletionRequestDateZaps.hashCode());
		result = prime * result
				+ ((edoMemberInd == null) ? 0 : edoMemberInd.hashCode());
		result = prime
				* result
				+ ((edosIssuerTrackingInd == null) ? 0 : edosIssuerTrackingInd
						.hashCode());
		result = prime * result
				+ ((edosMaxNoRecs == null) ? 0 : edosMaxNoRecs.hashCode());
		result = prime
				* result
				+ ((edosPresentOnUsInd == null) ? 0 : edosPresentOnUsInd
						.hashCode());
		result = prime
				* result
				+ ((eftComputerisedInd == null) ? 0 : eftComputerisedInd
						.hashCode());
		result = prime
				* result
				+ ((eftCtrlBreakCount == null) ? 0 : eftCtrlBreakCount
						.hashCode());
		result = prime
				* result
				+ ((eftDatedMaxSizeTapeFile == null) ? 0
						: eftDatedMaxSizeTapeFile.hashCode());
		result = prime
				* result
				+ ((eftDatedMaxSizeTransFile == null) ? 0
						: eftDatedMaxSizeTransFile.hashCode());
		result = prime
				* result
				+ ((eftDatedOutputInd == null) ? 0 : eftDatedOutputInd
						.hashCode());
		result = prime
				* result
				+ ((eftDatedTestLiveInd == null) ? 0 : eftDatedTestLiveInd
						.hashCode());
		result = prime
				* result
				+ ((eftDebitAllowToSavings == null) ? 0
						: eftDebitAllowToSavings.hashCode());
		result = prime * result
				+ ((eftElecChgFile == null) ? 0 : eftElecChgFile.hashCode());
		result = prime * result
				+ ((eftFormat == null) ? 0 : eftFormat.hashCode());
		result = prime * result
				+ ((eftMemberInd == null) ? 0 : eftMemberInd.hashCode());
		result = prime
				* result
				+ ((eftNonCompElecOut == null) ? 0 : eftNonCompElecOut
						.hashCode());
		result = prime * result
				+ ((eftOutputCentre == null) ? 0 : eftOutputCentre.hashCode());
		result = prime
				* result
				+ ((eftRecallOutputInd == null) ? 0 : eftRecallOutputInd
						.hashCode());
		result = prime
				* result
				+ ((eftRecallsTestLiveInd == null) ? 0 : eftRecallsTestLiveInd
						.hashCode());
		result = prime
				* result
				+ ((eftSsvMaxSizeTapeFile == null) ? 0 : eftSsvMaxSizeTapeFile
						.hashCode());
		result = prime
				* result
				+ ((eftSsvMaxSizeTransFile == null) ? 0
						: eftSsvMaxSizeTransFile.hashCode());
		result = prime * result
				+ ((eftSsvOutputInd == null) ? 0 : eftSsvOutputInd.hashCode());
		result = prime
				* result
				+ ((eftSsvTestLiveInd == null) ? 0 : eftSsvTestLiveInd
						.hashCode());
		result = prime
				* result
				+ ((eftUnpaidsOutputInd == null) ? 0 : eftUnpaidsOutputInd
						.hashCode());
		result = prime
				* result
				+ ((eftUnpaidsTestLiveInd == null) ? 0 : eftUnpaidsTestLiveInd
						.hashCode());
		result = prime * result
				+ ((icmsMemberInd == null) ? 0 : icmsMemberInd.hashCode());
		result = prime * result
				+ ((memberAddress1 == null) ? 0 : memberAddress1.hashCode());
		result = prime * result
				+ ((memberAddress2 == null) ? 0 : memberAddress2.hashCode());
		result = prime * result
				+ ((memberAddress3 == null) ? 0 : memberAddress3.hashCode());
		result = prime * result
				+ ((memberAddress4 == null) ? 0 : memberAddress4.hashCode());
		result = prime
				* result
				+ ((memberBranchEndRange == null) ? 0 : memberBranchEndRange
						.hashCode());
		result = prime
				* result
				+ ((memberBranchStartRange == null) ? 0
						: memberBranchStartRange.hashCode());
		result = prime * result
				+ ((memberLanguage == null) ? 0 : memberLanguage.hashCode());
		result = prime * result
				+ ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result
				+ ((memberNo == null) ? 0 : memberNo.hashCode());
		result = prime * result
				+ ((memberSarbAccnt == null) ? 0 : memberSarbAccnt.hashCode());
		result = prime * result
				+ ((memberSetLive == null) ? 0 : memberSetLive.hashCode());
		result = prime * result
				+ ((memberSortSeqNo == null) ? 0 : memberSortSeqNo.hashCode());
		result = prime
				* result
				+ ((memberSponsoredBy == null) ? 0 : memberSponsoredBy
						.hashCode());
		result = prime * result
				+ ((memberStatus == null) ? 0 : memberStatus.hashCode());
		result = prime * result
				+ ((memberTapeId == null) ? 0 : memberTapeId.hashCode());
		result = prime * result
				+ ((mmtMemberInd == null) ? 0 : mmtMemberInd.hashCode());
		result = prime
				* result
				+ ((mnemonicMemberName == null) ? 0 : mnemonicMemberName
						.hashCode());
		result = prime
				* result
				+ ((naedosReceiveDisputesInd == null) ? 0
						: naedosReceiveDisputesInd.hashCode());
		result = prime * result
				+ ((naedossAcq == null) ? 0 : naedossAcq.hashCode());
		result = prime * result
				+ ((naedossIss == null) ? 0 : naedossIss.hashCode());
		result = prime
				* result
				+ ((naedossMemberInd == null) ? 0 : naedossMemberInd.hashCode());
		result = prime
				* result
				+ ((naedossSevenDayService == null) ? 0
						: naedossSevenDayService.hashCode());
		result = prime * result
				+ ((nupayMemberInd == null) ? 0 : nupayMemberInd.hashCode());
		result = prime
				* result
				+ ((rtcBillingAddress1 == null) ? 0 : rtcBillingAddress1
						.hashCode());
		result = prime
				* result
				+ ((rtcBillingAddress2 == null) ? 0 : rtcBillingAddress2
						.hashCode());
		result = prime
				* result
				+ ((rtcBillingAddress3 == null) ? 0 : rtcBillingAddress3
						.hashCode());
		result = prime
				* result
				+ ((rtcBillingAddress4 == null) ? 0 : rtcBillingAddress4
						.hashCode());
		result = prime * result + ((rtcFiid == null) ? 0 : rtcFiid.hashCode());
		result = prime * result
				+ ((rtcMemberInd == null) ? 0 : rtcMemberInd.hashCode());
		result = prime * result
				+ ((rtcMemberNo == null) ? 0 : rtcMemberNo.hashCode());
		result = prime * result
				+ ((rtcNoOfIlfFiles == null) ? 0 : rtcNoOfIlfFiles.hashCode());
		result = prime * result
				+ ((rtcVatNumber == null) ? 0 : rtcVatNumber.hashCode());
		result = prime * result
				+ ((samosAccNoLive == null) ? 0 : samosAccNoLive.hashCode());
		result = prime * result
				+ ((samosAccNoTest == null) ? 0 : samosAccNoTest.hashCode());
		result = prime
				* result
				+ ((samosBicCodeLive == null) ? 0 : samosBicCodeLive.hashCode());
		result = prime
				* result
				+ ((samosBicCodeTest == null) ? 0 : samosBicCodeTest.hashCode());
		result = prime * result
				+ ((samosMemberInd == null) ? 0 : samosMemberInd.hashCode());
		result = prime
				* result
				+ ((samosReceiveMt298 == null) ? 0 : samosReceiveMt298
						.hashCode());
		result = prime
				* result
				+ ((samosSponsorOthers == null) ? 0 : samosSponsorOthers
						.hashCode());
		result = prime
				* result
				+ ((samosSponsoredBy == null) ? 0 : samosSponsoredBy.hashCode());
		result = prime * result
				+ ((saswMemberInd == null) ? 0 : saswMemberInd.hashCode());
		result = prime * result
				+ ((visaMemberInd == null) ? 0 : visaMemberInd.hashCode());
		result = prime * result
				+ ((zapsBicCodeLive == null) ? 0 : zapsBicCodeLive.hashCode());
		result = prime * result
				+ ((zapsBicCodeTest == null) ? 0 : zapsBicCodeTest.hashCode());
		result = prime * result
				+ ((zapsItemLimit == null) ? 0 : zapsItemLimit.hashCode());
		result = prime * result
				+ ((zapsMemberInd == null) ? 0 : zapsMemberInd.hashCode());
		result = prime * result
				+ ((zapsMt950AccNo == null) ? 0 : zapsMt950AccNo.hashCode());
		result = prime * result
				+ ((zapsOutputType == null) ? 0 : zapsOutputType.hashCode());
		result = prime
				* result
				+ ((zapsReceiveMt950 == null) ? 0 : zapsReceiveMt950.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CisMemberEntity other = (CisMemberEntity) obj;
		if (abbrevCurrency == null) {
			if (other.abbrevCurrency != null)
				return false;
		} else if (!abbrevCurrency.equals(other.abbrevCurrency))
			return false;
		if (abbrevMemberName == null) {
			if (other.abbrevMemberName != null)
				return false;
		} else if (!abbrevMemberName.equals(other.abbrevMemberName))
			return false;
		if (acMdtInd == null) {
			if (other.acMdtInd != null)
				return false;
		} else if (!acMdtInd.equals(other.acMdtInd))
			return false;
		if (acMdtMaxNrRecords == null) {
			if (other.acMdtMaxNrRecords != null)
				return false;
		} else if (!acMdtMaxNrRecords.equals(other.acMdtMaxNrRecords))
			return false;
		if (acMdtNrOfDaysProc == null) {
			if (other.acMdtNrOfDaysProc != null)
				return false;
		} else if (!acMdtNrOfDaysProc.equals(other.acMdtNrOfDaysProc))
			return false;
		if (acMdtPubHolProc == null) {
			if (other.acMdtPubHolProc != null)
				return false;
		} else if (!acMdtPubHolProc.equals(other.acMdtPubHolProc))
			return false;
		if (adossAcq == null) {
			if (other.adossAcq != null)
				return false;
		} else if (!adossAcq.equals(other.adossAcq))
			return false;
		if (adossIss == null) {
			if (other.adossIss != null)
				return false;
		} else if (!adossIss.equals(other.adossIss))
			return false;
		if (adossMemberInd == null) {
			if (other.adossMemberInd != null)
				return false;
		} else if (!adossMemberInd.equals(other.adossMemberInd))
			return false;
		if (aedosEdoFormatInd == null) {
			if (other.aedosEdoFormatInd != null)
				return false;
		} else if (!aedosEdoFormatInd.equals(other.aedosEdoFormatInd))
			return false;
		if (agencyAddress1 == null) {
			if (other.agencyAddress1 != null)
				return false;
		} else if (!agencyAddress1.equals(other.agencyAddress1))
			return false;
		if (agencyAddress2 == null) {
			if (other.agencyAddress2 != null)
				return false;
		} else if (!agencyAddress2.equals(other.agencyAddress2))
			return false;
		if (agencyAddress3 == null) {
			if (other.agencyAddress3 != null)
				return false;
		} else if (!agencyAddress3.equals(other.agencyAddress3))
			return false;
		if (baMemberNo == null) {
			if (other.baMemberNo != null)
				return false;
		} else if (!baMemberNo.equals(other.baMemberNo))
			return false;
		if (bamMemberInd == null) {
			if (other.bamMemberInd != null)
				return false;
		} else if (!bamMemberInd.equals(other.bamMemberInd))
			return false;
		if (ccardMemberInd == null) {
			if (other.ccardMemberInd != null)
				return false;
		} else if (!ccardMemberInd.equals(other.ccardMemberInd))
			return false;
		if (cim900XcomInd == null) {
			if (other.cim900XcomInd != null)
				return false;
		} else if (!cim900XcomInd.equals(other.cim900XcomInd))
			return false;
		if (clcChequesTo == null) {
			if (other.clcChequesTo != null)
				return false;
		} else if (!clcChequesTo.equals(other.clcChequesTo))
			return false;
		if (clcCl6ReportReq == null) {
			if (other.clcCl6ReportReq != null)
				return false;
		} else if (!clcCl6ReportReq.equals(other.clcCl6ReportReq))
			return false;
		if (clcDestBranch == null) {
			if (other.clcDestBranch != null)
				return false;
		} else if (!clcDestBranch.equals(other.clcDestBranch))
			return false;
		if (clcMemberInd == null) {
			if (other.clcMemberInd != null)
				return false;
		} else if (!clcMemberInd.equals(other.clcMemberInd))
			return false;
		if (clcOutputContent == null) {
			if (other.clcOutputContent != null)
				return false;
		} else if (!clcOutputContent.equals(other.clcOutputContent))
			return false;
		if (clcOutputFormat == null) {
			if (other.clcOutputFormat != null)
				return false;
		} else if (!clcOutputFormat.equals(other.clcOutputFormat))
			return false;
		if (clcRemitter == null) {
			if (other.clcRemitter != null)
				return false;
		} else if (!clcRemitter.equals(other.clcRemitter))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (dateLastChanged == null) {
			if (other.dateLastChanged != null)
				return false;
		} else if (!dateLastChanged.equals(other.dateLastChanged))
			return false;
		if (dcardMemberInd == null) {
			if (other.dcardMemberInd != null)
				return false;
		} else if (!dcardMemberInd.equals(other.dcardMemberInd))
			return false;
		if (deletionRequestDateAdoss == null) {
			if (other.deletionRequestDateAdoss != null)
				return false;
		} else if (!deletionRequestDateAdoss
				.equals(other.deletionRequestDateAdoss))
			return false;
		if (deletionRequestDateBam == null) {
			if (other.deletionRequestDateBam != null)
				return false;
		} else if (!deletionRequestDateBam.equals(other.deletionRequestDateBam))
			return false;
		if (deletionRequestDateCcard == null) {
			if (other.deletionRequestDateCcard != null)
				return false;
		} else if (!deletionRequestDateCcard
				.equals(other.deletionRequestDateCcard))
			return false;
		if (deletionRequestDateClc == null) {
			if (other.deletionRequestDateClc != null)
				return false;
		} else if (!deletionRequestDateClc.equals(other.deletionRequestDateClc))
			return false;
		if (deletionRequestDateDcard == null) {
			if (other.deletionRequestDateDcard != null)
				return false;
		} else if (!deletionRequestDateDcard
				.equals(other.deletionRequestDateDcard))
			return false;
		if (deletionRequestDateEft == null) {
			if (other.deletionRequestDateEft != null)
				return false;
		} else if (!deletionRequestDateEft.equals(other.deletionRequestDateEft))
			return false;
		if (deletionRequestDateIcms == null) {
			if (other.deletionRequestDateIcms != null)
				return false;
		} else if (!deletionRequestDateIcms
				.equals(other.deletionRequestDateIcms))
			return false;
		if (deletionRequestDateMmt == null) {
			if (other.deletionRequestDateMmt != null)
				return false;
		} else if (!deletionRequestDateMmt.equals(other.deletionRequestDateMmt))
			return false;
		if (deletionRequestDateNaedoss == null) {
			if (other.deletionRequestDateNaedoss != null)
				return false;
		} else if (!deletionRequestDateNaedoss
				.equals(other.deletionRequestDateNaedoss))
			return false;
		if (deletionRequestDateNupay == null) {
			if (other.deletionRequestDateNupay != null)
				return false;
		} else if (!deletionRequestDateNupay
				.equals(other.deletionRequestDateNupay))
			return false;
		if (deletionRequestDateRtc == null) {
			if (other.deletionRequestDateRtc != null)
				return false;
		} else if (!deletionRequestDateRtc.equals(other.deletionRequestDateRtc))
			return false;
		if (deletionRequestDateSamos == null) {
			if (other.deletionRequestDateSamos != null)
				return false;
		} else if (!deletionRequestDateSamos
				.equals(other.deletionRequestDateSamos))
			return false;
		if (deletionRequestDateSasw == null) {
			if (other.deletionRequestDateSasw != null)
				return false;
		} else if (!deletionRequestDateSasw
				.equals(other.deletionRequestDateSasw))
			return false;
		if (deletionRequestDateVisa == null) {
			if (other.deletionRequestDateVisa != null)
				return false;
		} else if (!deletionRequestDateVisa
				.equals(other.deletionRequestDateVisa))
			return false;
		if (deletionRequestDateZaps == null) {
			if (other.deletionRequestDateZaps != null)
				return false;
		} else if (!deletionRequestDateZaps
				.equals(other.deletionRequestDateZaps))
			return false;
		if (edoMemberInd == null) {
			if (other.edoMemberInd != null)
				return false;
		} else if (!edoMemberInd.equals(other.edoMemberInd))
			return false;
		if (edosIssuerTrackingInd == null) {
			if (other.edosIssuerTrackingInd != null)
				return false;
		} else if (!edosIssuerTrackingInd.equals(other.edosIssuerTrackingInd))
			return false;
		if (edosMaxNoRecs == null) {
			if (other.edosMaxNoRecs != null)
				return false;
		} else if (!edosMaxNoRecs.equals(other.edosMaxNoRecs))
			return false;
		if (edosPresentOnUsInd == null) {
			if (other.edosPresentOnUsInd != null)
				return false;
		} else if (!edosPresentOnUsInd.equals(other.edosPresentOnUsInd))
			return false;
		if (eftComputerisedInd == null) {
			if (other.eftComputerisedInd != null)
				return false;
		} else if (!eftComputerisedInd.equals(other.eftComputerisedInd))
			return false;
		if (eftCtrlBreakCount == null) {
			if (other.eftCtrlBreakCount != null)
				return false;
		} else if (!eftCtrlBreakCount.equals(other.eftCtrlBreakCount))
			return false;
		if (eftDatedMaxSizeTapeFile == null) {
			if (other.eftDatedMaxSizeTapeFile != null)
				return false;
		} else if (!eftDatedMaxSizeTapeFile
				.equals(other.eftDatedMaxSizeTapeFile))
			return false;
		if (eftDatedMaxSizeTransFile == null) {
			if (other.eftDatedMaxSizeTransFile != null)
				return false;
		} else if (!eftDatedMaxSizeTransFile
				.equals(other.eftDatedMaxSizeTransFile))
			return false;
		if (eftDatedOutputInd == null) {
			if (other.eftDatedOutputInd != null)
				return false;
		} else if (!eftDatedOutputInd.equals(other.eftDatedOutputInd))
			return false;
		if (eftDatedTestLiveInd == null) {
			if (other.eftDatedTestLiveInd != null)
				return false;
		} else if (!eftDatedTestLiveInd.equals(other.eftDatedTestLiveInd))
			return false;
		if (eftDebitAllowToSavings == null) {
			if (other.eftDebitAllowToSavings != null)
				return false;
		} else if (!eftDebitAllowToSavings.equals(other.eftDebitAllowToSavings))
			return false;
		if (eftElecChgFile == null) {
			if (other.eftElecChgFile != null)
				return false;
		} else if (!eftElecChgFile.equals(other.eftElecChgFile))
			return false;
		if (eftFormat == null) {
			if (other.eftFormat != null)
				return false;
		} else if (!eftFormat.equals(other.eftFormat))
			return false;
		if (eftMemberInd == null) {
			if (other.eftMemberInd != null)
				return false;
		} else if (!eftMemberInd.equals(other.eftMemberInd))
			return false;
		if (eftNonCompElecOut == null) {
			if (other.eftNonCompElecOut != null)
				return false;
		} else if (!eftNonCompElecOut.equals(other.eftNonCompElecOut))
			return false;
		if (eftOutputCentre == null) {
			if (other.eftOutputCentre != null)
				return false;
		} else if (!eftOutputCentre.equals(other.eftOutputCentre))
			return false;
		if (eftRecallOutputInd == null) {
			if (other.eftRecallOutputInd != null)
				return false;
		} else if (!eftRecallOutputInd.equals(other.eftRecallOutputInd))
			return false;
		if (eftRecallsTestLiveInd == null) {
			if (other.eftRecallsTestLiveInd != null)
				return false;
		} else if (!eftRecallsTestLiveInd.equals(other.eftRecallsTestLiveInd))
			return false;
		if (eftSsvMaxSizeTapeFile == null) {
			if (other.eftSsvMaxSizeTapeFile != null)
				return false;
		} else if (!eftSsvMaxSizeTapeFile.equals(other.eftSsvMaxSizeTapeFile))
			return false;
		if (eftSsvMaxSizeTransFile == null) {
			if (other.eftSsvMaxSizeTransFile != null)
				return false;
		} else if (!eftSsvMaxSizeTransFile.equals(other.eftSsvMaxSizeTransFile))
			return false;
		if (eftSsvOutputInd == null) {
			if (other.eftSsvOutputInd != null)
				return false;
		} else if (!eftSsvOutputInd.equals(other.eftSsvOutputInd))
			return false;
		if (eftSsvTestLiveInd == null) {
			if (other.eftSsvTestLiveInd != null)
				return false;
		} else if (!eftSsvTestLiveInd.equals(other.eftSsvTestLiveInd))
			return false;
		if (eftUnpaidsOutputInd == null) {
			if (other.eftUnpaidsOutputInd != null)
				return false;
		} else if (!eftUnpaidsOutputInd.equals(other.eftUnpaidsOutputInd))
			return false;
		if (eftUnpaidsTestLiveInd == null) {
			if (other.eftUnpaidsTestLiveInd != null)
				return false;
		} else if (!eftUnpaidsTestLiveInd.equals(other.eftUnpaidsTestLiveInd))
			return false;
		if (icmsMemberInd == null) {
			if (other.icmsMemberInd != null)
				return false;
		} else if (!icmsMemberInd.equals(other.icmsMemberInd))
			return false;
		if (memberAddress1 == null) {
			if (other.memberAddress1 != null)
				return false;
		} else if (!memberAddress1.equals(other.memberAddress1))
			return false;
		if (memberAddress2 == null) {
			if (other.memberAddress2 != null)
				return false;
		} else if (!memberAddress2.equals(other.memberAddress2))
			return false;
		if (memberAddress3 == null) {
			if (other.memberAddress3 != null)
				return false;
		} else if (!memberAddress3.equals(other.memberAddress3))
			return false;
		if (memberAddress4 == null) {
			if (other.memberAddress4 != null)
				return false;
		} else if (!memberAddress4.equals(other.memberAddress4))
			return false;
		if (memberBranchEndRange == null) {
			if (other.memberBranchEndRange != null)
				return false;
		} else if (!memberBranchEndRange.equals(other.memberBranchEndRange))
			return false;
		if (memberBranchStartRange == null) {
			if (other.memberBranchStartRange != null)
				return false;
		} else if (!memberBranchStartRange.equals(other.memberBranchStartRange))
			return false;
		if (memberLanguage == null) {
			if (other.memberLanguage != null)
				return false;
		} else if (!memberLanguage.equals(other.memberLanguage))
			return false;
		if (memberName == null) {
			if (other.memberName != null)
				return false;
		} else if (!memberName.equals(other.memberName))
			return false;
		if (memberNo == null) {
			if (other.memberNo != null)
				return false;
		} else if (!memberNo.equals(other.memberNo))
			return false;
		if (memberSarbAccnt == null) {
			if (other.memberSarbAccnt != null)
				return false;
		} else if (!memberSarbAccnt.equals(other.memberSarbAccnt))
			return false;
		if (memberSetLive == null) {
			if (other.memberSetLive != null)
				return false;
		} else if (!memberSetLive.equals(other.memberSetLive))
			return false;
		if (memberSortSeqNo == null) {
			if (other.memberSortSeqNo != null)
				return false;
		} else if (!memberSortSeqNo.equals(other.memberSortSeqNo))
			return false;
		if (memberSponsoredBy == null) {
			if (other.memberSponsoredBy != null)
				return false;
		} else if (!memberSponsoredBy.equals(other.memberSponsoredBy))
			return false;
		if (memberStatus == null) {
			if (other.memberStatus != null)
				return false;
		} else if (!memberStatus.equals(other.memberStatus))
			return false;
		if (memberTapeId == null) {
			if (other.memberTapeId != null)
				return false;
		} else if (!memberTapeId.equals(other.memberTapeId))
			return false;
		if (mmtMemberInd == null) {
			if (other.mmtMemberInd != null)
				return false;
		} else if (!mmtMemberInd.equals(other.mmtMemberInd))
			return false;
		if (mnemonicMemberName == null) {
			if (other.mnemonicMemberName != null)
				return false;
		} else if (!mnemonicMemberName.equals(other.mnemonicMemberName))
			return false;
		if (naedosReceiveDisputesInd == null) {
			if (other.naedosReceiveDisputesInd != null)
				return false;
		} else if (!naedosReceiveDisputesInd
				.equals(other.naedosReceiveDisputesInd))
			return false;
		if (naedossAcq == null) {
			if (other.naedossAcq != null)
				return false;
		} else if (!naedossAcq.equals(other.naedossAcq))
			return false;
		if (naedossIss == null) {
			if (other.naedossIss != null)
				return false;
		} else if (!naedossIss.equals(other.naedossIss))
			return false;
		if (naedossMemberInd == null) {
			if (other.naedossMemberInd != null)
				return false;
		} else if (!naedossMemberInd.equals(other.naedossMemberInd))
			return false;
		if (naedossSevenDayService == null) {
			if (other.naedossSevenDayService != null)
				return false;
		} else if (!naedossSevenDayService.equals(other.naedossSevenDayService))
			return false;
		if (nupayMemberInd == null) {
			if (other.nupayMemberInd != null)
				return false;
		} else if (!nupayMemberInd.equals(other.nupayMemberInd))
			return false;
		if (rtcBillingAddress1 == null) {
			if (other.rtcBillingAddress1 != null)
				return false;
		} else if (!rtcBillingAddress1.equals(other.rtcBillingAddress1))
			return false;
		if (rtcBillingAddress2 == null) {
			if (other.rtcBillingAddress2 != null)
				return false;
		} else if (!rtcBillingAddress2.equals(other.rtcBillingAddress2))
			return false;
		if (rtcBillingAddress3 == null) {
			if (other.rtcBillingAddress3 != null)
				return false;
		} else if (!rtcBillingAddress3.equals(other.rtcBillingAddress3))
			return false;
		if (rtcBillingAddress4 == null) {
			if (other.rtcBillingAddress4 != null)
				return false;
		} else if (!rtcBillingAddress4.equals(other.rtcBillingAddress4))
			return false;
		if (rtcFiid == null) {
			if (other.rtcFiid != null)
				return false;
		} else if (!rtcFiid.equals(other.rtcFiid))
			return false;
		if (rtcMemberInd == null) {
			if (other.rtcMemberInd != null)
				return false;
		} else if (!rtcMemberInd.equals(other.rtcMemberInd))
			return false;
		if (rtcMemberNo == null) {
			if (other.rtcMemberNo != null)
				return false;
		} else if (!rtcMemberNo.equals(other.rtcMemberNo))
			return false;
		if (rtcNoOfIlfFiles == null) {
			if (other.rtcNoOfIlfFiles != null)
				return false;
		} else if (!rtcNoOfIlfFiles.equals(other.rtcNoOfIlfFiles))
			return false;
		if (rtcVatNumber == null) {
			if (other.rtcVatNumber != null)
				return false;
		} else if (!rtcVatNumber.equals(other.rtcVatNumber))
			return false;
		if (samosAccNoLive == null) {
			if (other.samosAccNoLive != null)
				return false;
		} else if (!samosAccNoLive.equals(other.samosAccNoLive))
			return false;
		if (samosAccNoTest == null) {
			if (other.samosAccNoTest != null)
				return false;
		} else if (!samosAccNoTest.equals(other.samosAccNoTest))
			return false;
		if (samosBicCodeLive == null) {
			if (other.samosBicCodeLive != null)
				return false;
		} else if (!samosBicCodeLive.equals(other.samosBicCodeLive))
			return false;
		if (samosBicCodeTest == null) {
			if (other.samosBicCodeTest != null)
				return false;
		} else if (!samosBicCodeTest.equals(other.samosBicCodeTest))
			return false;
		if (samosMemberInd == null) {
			if (other.samosMemberInd != null)
				return false;
		} else if (!samosMemberInd.equals(other.samosMemberInd))
			return false;
		if (samosReceiveMt298 == null) {
			if (other.samosReceiveMt298 != null)
				return false;
		} else if (!samosReceiveMt298.equals(other.samosReceiveMt298))
			return false;
		if (samosSponsorOthers == null) {
			if (other.samosSponsorOthers != null)
				return false;
		} else if (!samosSponsorOthers.equals(other.samosSponsorOthers))
			return false;
		if (samosSponsoredBy == null) {
			if (other.samosSponsoredBy != null)
				return false;
		} else if (!samosSponsoredBy.equals(other.samosSponsoredBy))
			return false;
		if (saswMemberInd == null) {
			if (other.saswMemberInd != null)
				return false;
		} else if (!saswMemberInd.equals(other.saswMemberInd))
			return false;
		if (visaMemberInd == null) {
			if (other.visaMemberInd != null)
				return false;
		} else if (!visaMemberInd.equals(other.visaMemberInd))
			return false;
		if (zapsBicCodeLive == null) {
			if (other.zapsBicCodeLive != null)
				return false;
		} else if (!zapsBicCodeLive.equals(other.zapsBicCodeLive))
			return false;
		if (zapsBicCodeTest == null) {
			if (other.zapsBicCodeTest != null)
				return false;
		} else if (!zapsBicCodeTest.equals(other.zapsBicCodeTest))
			return false;
		if (zapsItemLimit == null) {
			if (other.zapsItemLimit != null)
				return false;
		} else if (!zapsItemLimit.equals(other.zapsItemLimit))
			return false;
		if (zapsMemberInd == null) {
			if (other.zapsMemberInd != null)
				return false;
		} else if (!zapsMemberInd.equals(other.zapsMemberInd))
			return false;
		if (zapsMt950AccNo == null) {
			if (other.zapsMt950AccNo != null)
				return false;
		} else if (!zapsMt950AccNo.equals(other.zapsMt950AccNo))
			return false;
		if (zapsOutputType == null) {
			if (other.zapsOutputType != null)
				return false;
		} else if (!zapsOutputType.equals(other.zapsOutputType))
			return false;
		if (zapsReceiveMt950 == null) {
			if (other.zapsReceiveMt950 != null)
				return false;
		} else if (!zapsReceiveMt950.equals(other.zapsReceiveMt950))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CisMemberEntity [memberNo=" + memberNo + ", baMemberNo="
				+ baMemberNo + ", memberName=" + memberName
				+ ", abbrevMemberName=" + abbrevMemberName
				+ ", mnemonicMemberName=" + mnemonicMemberName
				+ ", memberBranchStartRange=" + memberBranchStartRange
				+ ", memberBranchEndRange=" + memberBranchEndRange
				+ ", memberAddress1=" + memberAddress1 + ", memberAddress2="
				+ memberAddress2 + ", memberAddress3=" + memberAddress3
				+ ", memberAddress4=" + memberAddress4 + ", country=" + country
				+ ", currency=" + currency + ", abbrevCurrency="
				+ abbrevCurrency + ", memberLanguage=" + memberLanguage
				+ ", memberStatus=" + memberStatus + ", memberSetLive="
				+ memberSetLive + ", memberSponsoredBy=" + memberSponsoredBy
				+ ", memberSarbAccnt=" + memberSarbAccnt + ", memberTapeId="
				+ memberTapeId + ", memberSortSeqNo=" + memberSortSeqNo
				+ ", agencyAddress1=" + agencyAddress1 + ", agencyAddress2="
				+ agencyAddress2 + ", agencyAddress3=" + agencyAddress3
				+ ", clcMemberInd=" + clcMemberInd + ", clcChequesTo="
				+ clcChequesTo + ", clcRemitter=" + clcRemitter
				+ ", clcOutputContent=" + clcOutputContent
				+ ", clcOutputFormat=" + clcOutputFormat + ", clcDestBranch="
				+ clcDestBranch + ", clcCl6ReportReq=" + clcCl6ReportReq
				+ ", ccardMemberInd=" + ccardMemberInd + ", dcardMemberInd="
				+ dcardMemberInd + ", eftMemberInd=" + eftMemberInd
				+ ", eftComputerisedInd=" + eftComputerisedInd
				+ ", eftCtrlBreakCount=" + eftCtrlBreakCount
				+ ", eftDebitAllowToSavings=" + eftDebitAllowToSavings
				+ ", eftElecChgFile=" + eftElecChgFile + ", eftFormat="
				+ eftFormat + ", eftNonCompElecOut=" + eftNonCompElecOut
				+ ", eftOutputCentre=" + eftOutputCentre
				+ ", eftSsvTestLiveInd=" + eftSsvTestLiveInd
				+ ", eftSsvOutputInd=" + eftSsvOutputInd
				+ ", eftSsvMaxSizeTransFile=" + eftSsvMaxSizeTransFile
				+ ", eftSsvMaxSizeTapeFile=" + eftSsvMaxSizeTapeFile
				+ ", eftDatedTestLiveInd=" + eftDatedTestLiveInd
				+ ", eftDatedOutputInd=" + eftDatedOutputInd
				+ ", eftDatedMaxSizeTransFile=" + eftDatedMaxSizeTransFile
				+ ", eftDatedMaxSizeTapeFile=" + eftDatedMaxSizeTapeFile
				+ ", eftRecallsTestLiveInd=" + eftRecallsTestLiveInd
				+ ", eftRecallOutputInd=" + eftRecallOutputInd
				+ ", eftUnpaidsTestLiveInd=" + eftUnpaidsTestLiveInd
				+ ", eftUnpaidsOutputInd=" + eftUnpaidsOutputInd
				+ ", samosMemberInd=" + samosMemberInd + ", samosBicCodeLive="
				+ samosBicCodeLive + ", samosBicCodeTest=" + samosBicCodeTest
				+ ", samosAccNoLive=" + samosAccNoLive + ", samosAccNoTest="
				+ samosAccNoTest + ", samosSponsoredBy=" + samosSponsoredBy
				+ ", samosSponsorOthers=" + samosSponsorOthers
				+ ", samosReceiveMt298=" + samosReceiveMt298
				+ ", saswMemberInd=" + saswMemberInd + ", zapsMemberInd="
				+ zapsMemberInd + ", zapsBicCodeLive=" + zapsBicCodeLive
				+ ", zapsBicCodeTest=" + zapsBicCodeTest + ", zapsItemLimit="
				+ zapsItemLimit + ", zapsReceiveMt950=" + zapsReceiveMt950
				+ ", zapsMt950AccNo=" + zapsMt950AccNo + ", zapsOutputType="
				+ zapsOutputType + ", bamMemberInd=" + bamMemberInd
				+ ", deletionRequestDateBam=" + deletionRequestDateBam
				+ ", deletionRequestDateClc=" + deletionRequestDateClc
				+ ", deletionRequestDateEft=" + deletionRequestDateEft
				+ ", deletionRequestDateCcard=" + deletionRequestDateCcard
				+ ", deletionRequestDateDcard=" + deletionRequestDateDcard
				+ ", deletionRequestDateSamos=" + deletionRequestDateSamos
				+ ", deletionRequestDateSasw=" + deletionRequestDateSasw
				+ ", deletionRequestDateZaps=" + deletionRequestDateZaps
				+ ", dateLastChanged=" + dateLastChanged + ", visaMemberInd="
				+ visaMemberInd + ", deletionRequestDateVisa="
				+ deletionRequestDateVisa + ", nupayMemberInd="
				+ nupayMemberInd + ", deletionRequestDateNupay="
				+ deletionRequestDateNupay + ", adossMemberInd="
				+ adossMemberInd + ", adossAcq=" + adossAcq + ", adossIss="
				+ adossIss + ", deletionRequestDateAdoss="
				+ deletionRequestDateAdoss + ", mmtMemberInd=" + mmtMemberInd
				+ ", deletionRequestDateMmt=" + deletionRequestDateMmt
				+ ", rtcMemberInd=" + rtcMemberInd + ", rtcMemberNo="
				+ rtcMemberNo + ", deletionRequestDateRtc="
				+ deletionRequestDateRtc + ", icmsMemberInd=" + icmsMemberInd
				+ ", deletionRequestDateIcms=" + deletionRequestDateIcms
				+ ", naedossMemberInd=" + naedossMemberInd + ", naedossAcq="
				+ naedossAcq + ", naedossIss=" + naedossIss
				+ ", naedossSevenDayService=" + naedossSevenDayService
				+ ", deletionRequestDateNaedoss=" + deletionRequestDateNaedoss
				+ ", edosMaxNoRecs=" + edosMaxNoRecs + ", aedosEdoFormatInd="
				+ aedosEdoFormatInd + ", edosIssuerTrackingInd="
				+ edosIssuerTrackingInd + ", naedosReceiveDisputesInd="
				+ naedosReceiveDisputesInd + ", rtcFiid=" + rtcFiid
				+ ", edosPresentOnUsInd=" + edosPresentOnUsInd
				+ ", edoMemberInd=" + edoMemberInd + ", rtcVatNumber="
				+ rtcVatNumber + ", rtcNoOfIlfFiles=" + rtcNoOfIlfFiles
				+ ", rtcBillingAddress1=" + rtcBillingAddress1
				+ ", rtcBillingAddress2=" + rtcBillingAddress2
				+ ", rtcBillingAddress3=" + rtcBillingAddress3
				+ ", rtcBillingAddress4=" + rtcBillingAddress4
				+ ", cim900XcomInd=" + cim900XcomInd + ", acMdtMaxNrRecords="
				+ acMdtMaxNrRecords + ", acMdtNrOfDaysProc="
				+ acMdtNrOfDaysProc + ", acMdtPubHolProc=" + acMdtPubHolProc
				+ ", acMdtInd=" + acMdtInd + "]";
	}

    
    
}
