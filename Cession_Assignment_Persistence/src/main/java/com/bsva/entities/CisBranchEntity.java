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
@Table(name = "CIS_BRANCH",schema = "MANOWNER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CisBranchEntity.findAll", query = "SELECT c FROM CisBranchEntity c"),
    @NamedQuery(name = "CisBranchEntity.findByBranchNo", query = "SELECT c FROM CisBranchEntity c WHERE c.branchNo = :branchNo"),
    @NamedQuery(name = "CisBranchEntity.findByBranchName", query = "SELECT c FROM CisBranchEntity c WHERE c.branchName = :branchName"),
    @NamedQuery(name = "CisBranchEntity.findByDivision", query = "SELECT c FROM CisBranchEntity c WHERE c.division = :division"),
    @NamedQuery(name = "CisBranchEntity.findByAbbrDivisionName", query = "SELECT c FROM CisBranchEntity c WHERE c.abbrDivisionName = :abbrDivisionName"),
    @NamedQuery(name = "CisBranchEntity.findByAgencyBranchNo", query = "SELECT c FROM CisBranchEntity c WHERE c.agencyBranchNo = :agencyBranchNo"),
    @NamedQuery(name = "CisBranchEntity.findByMemberNo", query = "SELECT c FROM CisBranchEntity c WHERE c.memberNo = :memberNo"),
    @NamedQuery(name = "CisBranchEntity.findByBaMemberNo", query = "SELECT c FROM CisBranchEntity c WHERE c.baMemberNo = :baMemberNo"),
    @NamedQuery(name = "CisBranchEntity.findByInstitutionType", query = "SELECT c FROM CisBranchEntity c WHERE c.institutionType = :institutionType"),
    @NamedQuery(name = "CisBranchEntity.findByAddressLine1", query = "SELECT c FROM CisBranchEntity c WHERE c.addressLine1 = :addressLine1"),
    @NamedQuery(name = "CisBranchEntity.findByAddressLine2", query = "SELECT c FROM CisBranchEntity c WHERE c.addressLine2 = :addressLine2"),
    @NamedQuery(name = "CisBranchEntity.findByAddressLine3", query = "SELECT c FROM CisBranchEntity c WHERE c.addressLine3 = :addressLine3"),
    @NamedQuery(name = "CisBranchEntity.findByCity", query = "SELECT c FROM CisBranchEntity c WHERE c.city = :city"),
    @NamedQuery(name = "CisBranchEntity.findByPostCode", query = "SELECT c FROM CisBranchEntity c WHERE c.postCode = :postCode"),
    @NamedQuery(name = "CisBranchEntity.findByCountry", query = "SELECT c FROM CisBranchEntity c WHERE c.country = :country"),
    @NamedQuery(name = "CisBranchEntity.findByPoBoxNo", query = "SELECT c FROM CisBranchEntity c WHERE c.poBoxNo = :poBoxNo"),
    @NamedQuery(name = "CisBranchEntity.findByPostOffice", query = "SELECT c FROM CisBranchEntity c WHERE c.postOffice = :postOffice"),
    @NamedQuery(name = "CisBranchEntity.findByPostalCity", query = "SELECT c FROM CisBranchEntity c WHERE c.postalCity = :postalCity"),
    @NamedQuery(name = "CisBranchEntity.findByPostalPostCode", query = "SELECT c FROM CisBranchEntity c WHERE c.postalPostCode = :postalPostCode"),
    @NamedQuery(name = "CisBranchEntity.findByPostalProvince", query = "SELECT c FROM CisBranchEntity c WHERE c.postalProvince = :postalProvince"),
    @NamedQuery(name = "CisBranchEntity.findByPostalCountry", query = "SELECT c FROM CisBranchEntity c WHERE c.postalCountry = :postalCountry"),
    @NamedQuery(name = "CisBranchEntity.findByAreaCode", query = "SELECT c FROM CisBranchEntity c WHERE c.areaCode = :areaCode"),
    @NamedQuery(name = "CisBranchEntity.findByTelNo", query = "SELECT c FROM CisBranchEntity c WHERE c.telNo = :telNo"),
    @NamedQuery(name = "CisBranchEntity.findByFaxNo", query = "SELECT c FROM CisBranchEntity c WHERE c.faxNo = :faxNo"),
    @NamedQuery(name = "CisBranchEntity.findByEmailAddress", query = "SELECT c FROM CisBranchEntity c WHERE c.emailAddress = :emailAddress"),
    @NamedQuery(name = "CisBranchEntity.findByTelexNo1", query = "SELECT c FROM CisBranchEntity c WHERE c.telexNo1 = :telexNo1"),
    @NamedQuery(name = "CisBranchEntity.findByTelexNo2", query = "SELECT c FROM CisBranchEntity c WHERE c.telexNo2 = :telexNo2"),
    @NamedQuery(name = "CisBranchEntity.findByTelegraphicAddress", query = "SELECT c FROM CisBranchEntity c WHERE c.telegraphicAddress = :telegraphicAddress"),
    @NamedQuery(name = "CisBranchEntity.findByLanguage", query = "SELECT c FROM CisBranchEntity c WHERE c.language = :language"),
    @NamedQuery(name = "CisBranchEntity.findByEft2dayAllowToNoncomp", query = "SELECT c FROM CisBranchEntity c WHERE c.eft2dayAllowToNoncomp = :eft2dayAllowToNoncomp"),
    @NamedQuery(name = "CisBranchEntity.findByEftBranchInd", query = "SELECT c FROM CisBranchEntity c WHERE c.eftBranchInd = :eftBranchInd"),
    @NamedQuery(name = "CisBranchEntity.findByInAreaIndicator", query = "SELECT c FROM CisBranchEntity c WHERE c.inAreaIndicator = :inAreaIndicator"),
    @NamedQuery(name = "CisBranchEntity.findByPaperToFollow", query = "SELECT c FROM CisBranchEntity c WHERE c.paperToFollow = :paperToFollow"),
    @NamedQuery(name = "CisBranchEntity.findByCrossBorderFlag", query = "SELECT c FROM CisBranchEntity c WHERE c.crossBorderFlag = :crossBorderFlag"),
    @NamedQuery(name = "CisBranchEntity.findByCrossBorderCurrency", query = "SELECT c FROM CisBranchEntity c WHERE c.crossBorderCurrency = :crossBorderCurrency"),
    @NamedQuery(name = "CisBranchEntity.findByCdvNo", query = "SELECT c FROM CisBranchEntity c WHERE c.cdvNo = :cdvNo"),
    @NamedQuery(name = "CisBranchEntity.findByIntPocket", query = "SELECT c FROM CisBranchEntity c WHERE c.intPocket = :intPocket"),
    @NamedQuery(name = "CisBranchEntity.findByClcRemitter", query = "SELECT c FROM CisBranchEntity c WHERE c.clcRemitter = :clcRemitter"),
    @NamedQuery(name = "CisBranchEntity.findByClcRemitDateLive", query = "SELECT c FROM CisBranchEntity c WHERE c.clcRemitDateLive = :clcRemitDateLive"),
    @NamedQuery(name = "CisBranchEntity.findByClcBranchInd", query = "SELECT c FROM CisBranchEntity c WHERE c.clcBranchInd = :clcBranchInd"),
    @NamedQuery(name = "CisBranchEntity.findByBamBranchInd", query = "SELECT c FROM CisBranchEntity c WHERE c.bamBranchInd = :bamBranchInd"),
    @NamedQuery(name = "CisBranchEntity.findByFineSortTypeJhb", query = "SELECT c FROM CisBranchEntity c WHERE c.fineSortTypeJhb = :fineSortTypeJhb"),
    @NamedQuery(name = "CisBranchEntity.findByFineSortTypeCtn", query = "SELECT c FROM CisBranchEntity c WHERE c.fineSortTypeCtn = :fineSortTypeCtn"),
    @NamedQuery(name = "CisBranchEntity.findByFineSortTypeDbn", query = "SELECT c FROM CisBranchEntity c WHERE c.fineSortTypeDbn = :fineSortTypeDbn"),
    @NamedQuery(name = "CisBranchEntity.findByFineSortTypePe", query = "SELECT c FROM CisBranchEntity c WHERE c.fineSortTypePe = :fineSortTypePe"),
    @NamedQuery(name = "CisBranchEntity.findByClrcl6ReportReqJhb", query = "SELECT c FROM CisBranchEntity c WHERE c.clrcl6ReportReqJhb = :clrcl6ReportReqJhb"),
    @NamedQuery(name = "CisBranchEntity.findByClrcl6ReportReqCtn", query = "SELECT c FROM CisBranchEntity c WHERE c.clrcl6ReportReqCtn = :clrcl6ReportReqCtn"),
    @NamedQuery(name = "CisBranchEntity.findByClrcl6ReportReqDbn", query = "SELECT c FROM CisBranchEntity c WHERE c.clrcl6ReportReqDbn = :clrcl6ReportReqDbn"),
    @NamedQuery(name = "CisBranchEntity.findByClrcl6ReportReqPe", query = "SELECT c FROM CisBranchEntity c WHERE c.clrcl6ReportReqPe = :clrcl6ReportReqPe"),
    @NamedQuery(name = "CisBranchEntity.findByClcChequesToJhb", query = "SELECT c FROM CisBranchEntity c WHERE c.clcChequesToJhb = :clcChequesToJhb"),
    @NamedQuery(name = "CisBranchEntity.findByClcChequesToCtn", query = "SELECT c FROM CisBranchEntity c WHERE c.clcChequesToCtn = :clcChequesToCtn"),
    @NamedQuery(name = "CisBranchEntity.findByClcChequesToDbn", query = "SELECT c FROM CisBranchEntity c WHERE c.clcChequesToDbn = :clcChequesToDbn"),
    @NamedQuery(name = "CisBranchEntity.findByClcChequesToPe", query = "SELECT c FROM CisBranchEntity c WHERE c.clcChequesToPe = :clcChequesToPe"),
    @NamedQuery(name = "CisBranchEntity.findByCourierRouteJhb", query = "SELECT c FROM CisBranchEntity c WHERE c.courierRouteJhb = :courierRouteJhb"),
    @NamedQuery(name = "CisBranchEntity.findByCourierRouteCtn", query = "SELECT c FROM CisBranchEntity c WHERE c.courierRouteCtn = :courierRouteCtn"),
    @NamedQuery(name = "CisBranchEntity.findByCourierRouteDbn", query = "SELECT c FROM CisBranchEntity c WHERE c.courierRouteDbn = :courierRouteDbn"),
    @NamedQuery(name = "CisBranchEntity.findByCourierRoutePe", query = "SELECT c FROM CisBranchEntity c WHERE c.courierRoutePe = :courierRoutePe"),
    @NamedQuery(name = "CisBranchEntity.findByPocketNoJhb", query = "SELECT c FROM CisBranchEntity c WHERE c.pocketNoJhb = :pocketNoJhb"),
    @NamedQuery(name = "CisBranchEntity.findByPocketNoCtn", query = "SELECT c FROM CisBranchEntity c WHERE c.pocketNoCtn = :pocketNoCtn"),
    @NamedQuery(name = "CisBranchEntity.findByPocketNoDbn", query = "SELECT c FROM CisBranchEntity c WHERE c.pocketNoDbn = :pocketNoDbn"),
    @NamedQuery(name = "CisBranchEntity.findByPocketNoPe", query = "SELECT c FROM CisBranchEntity c WHERE c.pocketNoPe = :pocketNoPe"),
    @NamedQuery(name = "CisBranchEntity.findByEftCtrlBreakInd", query = "SELECT c FROM CisBranchEntity c WHERE c.eftCtrlBreakInd = :eftCtrlBreakInd"),
    @NamedQuery(name = "CisBranchEntity.findByEftDebitsNalToBrn", query = "SELECT c FROM CisBranchEntity c WHERE c.eftDebitsNalToBrn = :eftDebitsNalToBrn"),
    @NamedQuery(name = "CisBranchEntity.findByEftExceptionAccType", query = "SELECT c FROM CisBranchEntity c WHERE c.eftExceptionAccType = :eftExceptionAccType"),
    @NamedQuery(name = "CisBranchEntity.findByEftStreamCode", query = "SELECT c FROM CisBranchEntity c WHERE c.eftStreamCode = :eftStreamCode"),
    @NamedQuery(name = "CisBranchEntity.findByEftStreamDataXmit", query = "SELECT c FROM CisBranchEntity c WHERE c.eftStreamDataXmit = :eftStreamDataXmit"),
    @NamedQuery(name = "CisBranchEntity.findByEftXmitInd", query = "SELECT c FROM CisBranchEntity c WHERE c.eftXmitInd = :eftXmitInd"),
    @NamedQuery(name = "CisBranchEntity.findByDeletionRequestDateClc", query = "SELECT c FROM CisBranchEntity c WHERE c.deletionRequestDateClc = :deletionRequestDateClc"),
    @NamedQuery(name = "CisBranchEntity.findByDeletionRequestDateEft", query = "SELECT c FROM CisBranchEntity c WHERE c.deletionRequestDateEft = :deletionRequestDateEft"),
    @NamedQuery(name = "CisBranchEntity.findByDeletionRequestDateBam", query = "SELECT c FROM CisBranchEntity c WHERE c.deletionRequestDateBam = :deletionRequestDateBam"),
    @NamedQuery(name = "CisBranchEntity.findByDateLastChanged", query = "SELECT c FROM CisBranchEntity c WHERE c.dateLastChanged = :dateLastChanged"),
    @NamedQuery(name = "CisBranchEntity.findByNaedossBranchInd", query = "SELECT c FROM CisBranchEntity c WHERE c.naedossBranchInd = :naedossBranchInd"),
    @NamedQuery(name = "CisBranchEntity.findByAedossBranchInd", query = "SELECT c FROM CisBranchEntity c WHERE c.aedossBranchInd = :aedossBranchInd")})
public class CisBranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "BRANCH_NO")
    private String branchNo;
    @Size(max = 30)
    @Column(name = "BRANCH_NAME")
    private String branchName;
    @Size(max = 30)
    @Column(name = "DIVISION")
    private String division;
    @Size(max = 6)
    @Column(name = "ABBR_DIVISION_NAME")
    private String abbrDivisionName;
    @Size(max = 2)
    @Column(name = "AGENCY_BRANCH_NO")
    private String agencyBranchNo;
    @Size(max = 6)
    @Column(name = "MEMBER_NO")
    private String memberNo;
    @Size(max = 6)
    @Column(name = "BA_MEMBER_NO")
    private String baMemberNo;
    @Size(max = 4)
    @Column(name = "INSTITUTION_TYPE")
    private String institutionType;
    @Size(max = 48)
    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;
    @Size(max = 48)
    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;
    @Size(max = 48)
    @Column(name = "ADDRESS_LINE_3")
    private String addressLine3;
    @Size(max = 30)
    @Column(name = "CITY")
    private String city;
    @Size(max = 6)
    @Column(name = "POST_CODE")
    private String postCode;
    @Size(max = 35)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 35)
    @Column(name = "PO_BOX_NO")
    private String poBoxNo;
    @Size(max = 20)
    @Column(name = "POST_OFFICE")
    private String postOffice;
    @Size(max = 35)
    @Column(name = "POSTAL_CITY")
    private String postalCity;
    @Size(max = 6)
    @Column(name = "POSTAL_POST_CODE")
    private String postalPostCode;
    @Size(max = 35)
    @Column(name = "POSTAL_PROVINCE")
    private String postalProvince;
    @Size(max = 35)
    @Column(name = "POSTAL_COUNTRY")
    private String postalCountry;
    @Size(max = 20)
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Size(max = 14)
    @Column(name = "TEL_NO")
    private String telNo;
    @Size(max = 14)
    @Column(name = "FAX_NO")
    private String faxNo;
    @Size(max = 40)
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Size(max = 20)
    @Column(name = "TELEX_NO_1")
    private String telexNo1;
    @Size(max = 20)
    @Column(name = "TELEX_NO_2")
    private String telexNo2;
    @Size(max = 24)
    @Column(name = "TELEGRAPHIC_ADDRESS")
    private String telegraphicAddress;
    @Size(max = 3)
    @Column(name = "LANGUAGE")
    private String language;
    @Size(max = 1)
    @Column(name = "EFT_2DAY_ALLOW_TO_NONCOMP")
    private String eft2dayAllowToNoncomp;
    @Size(max = 1)
    @Column(name = "EFT_BRANCH_IND")
    private String eftBranchInd;
    @Size(max = 1)
    @Column(name = "IN_AREA_INDICATOR")
    private String inAreaIndicator;
    @Size(max = 1)
    @Column(name = "PAPER_TO_FOLLOW")
    private String paperToFollow;
    @Size(max = 1)
    @Column(name = "CROSS_BORDER_FLAG")
    private String crossBorderFlag;
    @Size(max = 3)
    @Column(name = "CROSS_BORDER_CURRENCY")
    private String crossBorderCurrency;
    @Size(max = 2)
    @Column(name = "CDV_NO")
    private String cdvNo;
    @Size(max = 2)
    @Column(name = "INT_POCKET")
    private String intPocket;
    @Size(max = 1)
    @Column(name = "CLC_REMITTER")
    private String clcRemitter;
    @Column(name = "CLC_REMIT_DATE_LIVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date clcRemitDateLive;
    @Size(max = 1)
    @Column(name = "CLC_BRANCH_IND")
    private String clcBranchInd;
    @Size(max = 1)
    @Column(name = "BAM_BRANCH_IND")
    private String bamBranchInd;
    @Size(max = 1)
    @Column(name = "FINE_SORT_TYPE_JHB")
    private String fineSortTypeJhb;
    @Size(max = 1)
    @Column(name = "FINE_SORT_TYPE_CTN")
    private String fineSortTypeCtn;
    @Size(max = 1)
    @Column(name = "FINE_SORT_TYPE_DBN")
    private String fineSortTypeDbn;
    @Size(max = 1)
    @Column(name = "FINE_SORT_TYPE_PE")
    private String fineSortTypePe;
    @Size(max = 1)
    @Column(name = "CLRCL6_REPORT_REQ_JHB")
    private String clrcl6ReportReqJhb;
    @Size(max = 1)
    @Column(name = "CLRCL6_REPORT_REQ_CTN")
    private String clrcl6ReportReqCtn;
    @Size(max = 1)
    @Column(name = "CLRCL6_REPORT_REQ_DBN")
    private String clrcl6ReportReqDbn;
    @Size(max = 1)
    @Column(name = "CLRCL6_REPORT_REQ_PE")
    private String clrcl6ReportReqPe;
    @Size(max = 3)
    @Column(name = "CLC_CHEQUES_TO_JHB")
    private String clcChequesToJhb;
    @Size(max = 3)
    @Column(name = "CLC_CHEQUES_TO_CTN")
    private String clcChequesToCtn;
    @Size(max = 3)
    @Column(name = "CLC_CHEQUES_TO_DBN")
    private String clcChequesToDbn;
    @Size(max = 3)
    @Column(name = "CLC_CHEQUES_TO_PE")
    private String clcChequesToPe;
    @Size(max = 2)
    @Column(name = "COURIER_ROUTE_JHB")
    private String courierRouteJhb;
    @Size(max = 2)
    @Column(name = "COURIER_ROUTE_CTN")
    private String courierRouteCtn;
    @Size(max = 2)
    @Column(name = "COURIER_ROUTE_DBN")
    private String courierRouteDbn;
    @Size(max = 2)
    @Column(name = "COURIER_ROUTE_PE")
    private String courierRoutePe;
    @Column(name = "POCKET_NO_JHB")
    private Short pocketNoJhb;
    @Column(name = "POCKET_NO_CTN")
    private Short pocketNoCtn;
    @Column(name = "POCKET_NO_DBN")
    private Short pocketNoDbn;
    @Column(name = "POCKET_NO_PE")
    private Short pocketNoPe;
    @Size(max = 1)
    @Column(name = "EFT_CTRL_BREAK_IND")
    private String eftCtrlBreakInd;
    @Size(max = 2)
    @Column(name = "EFT_DEBITS_NAL_TO_BRN")
    private String eftDebitsNalToBrn;
    @Size(max = 1)
    @Column(name = "EFT_EXCEPTION_ACC_TYPE")
    private String eftExceptionAccType;
    @Size(max = 2)
    @Column(name = "EFT_STREAM_CODE")
    private String eftStreamCode;
    @Size(max = 1)
    @Column(name = "EFT_STREAM_DATA_XMIT")
    private String eftStreamDataXmit;
    @Size(max = 1)
    @Column(name = "EFT_XMIT_IND")
    private String eftXmitInd;
    @Column(name = "DELETION_REQUEST_DATE_CLC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateClc;
    @Column(name = "DELETION_REQUEST_DATE_EFT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateEft;
    @Column(name = "DELETION_REQUEST_DATE_BAM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionRequestDateBam;
    @Column(name = "DATE_LAST_CHANGED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastChanged;
    @Size(max = 1)
    @Column(name = "NAEDOSS_BRANCH_IND")
    private String naedossBranchInd;
    @Size(max = 1)
    @Column(name = "AEDOSS_BRANCH_IND")
    private String aedossBranchInd;

    public CisBranchEntity() {
    }

    public CisBranchEntity(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getAbbrDivisionName() {
        return abbrDivisionName;
    }

    public void setAbbrDivisionName(String abbrDivisionName) {
        this.abbrDivisionName = abbrDivisionName;
    }

    public String getAgencyBranchNo() {
        return agencyBranchNo;
    }

    public void setAgencyBranchNo(String agencyBranchNo) {
        this.agencyBranchNo = agencyBranchNo;
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

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPoBoxNo() {
        return poBoxNo;
    }

    public void setPoBoxNo(String poBoxNo) {
        this.poBoxNo = poBoxNo;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getPostalCity() {
        return postalCity;
    }

    public void setPostalCity(String postalCity) {
        this.postalCity = postalCity;
    }

    public String getPostalPostCode() {
        return postalPostCode;
    }

    public void setPostalPostCode(String postalPostCode) {
        this.postalPostCode = postalPostCode;
    }

    public String getPostalProvince() {
        return postalProvince;
    }

    public void setPostalProvince(String postalProvince) {
        this.postalProvince = postalProvince;
    }

    public String getPostalCountry() {
        return postalCountry;
    }

    public void setPostalCountry(String postalCountry) {
        this.postalCountry = postalCountry;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelexNo1() {
        return telexNo1;
    }

    public void setTelexNo1(String telexNo1) {
        this.telexNo1 = telexNo1;
    }

    public String getTelexNo2() {
        return telexNo2;
    }

    public void setTelexNo2(String telexNo2) {
        this.telexNo2 = telexNo2;
    }

    public String getTelegraphicAddress() {
        return telegraphicAddress;
    }

    public void setTelegraphicAddress(String telegraphicAddress) {
        this.telegraphicAddress = telegraphicAddress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEft2dayAllowToNoncomp() {
        return eft2dayAllowToNoncomp;
    }

    public void setEft2dayAllowToNoncomp(String eft2dayAllowToNoncomp) {
        this.eft2dayAllowToNoncomp = eft2dayAllowToNoncomp;
    }

    public String getEftBranchInd() {
        return eftBranchInd;
    }

    public void setEftBranchInd(String eftBranchInd) {
        this.eftBranchInd = eftBranchInd;
    }

    public String getInAreaIndicator() {
        return inAreaIndicator;
    }

    public void setInAreaIndicator(String inAreaIndicator) {
        this.inAreaIndicator = inAreaIndicator;
    }

    public String getPaperToFollow() {
        return paperToFollow;
    }

    public void setPaperToFollow(String paperToFollow) {
        this.paperToFollow = paperToFollow;
    }

    public String getCrossBorderFlag() {
        return crossBorderFlag;
    }

    public void setCrossBorderFlag(String crossBorderFlag) {
        this.crossBorderFlag = crossBorderFlag;
    }

    public String getCrossBorderCurrency() {
        return crossBorderCurrency;
    }

    public void setCrossBorderCurrency(String crossBorderCurrency) {
        this.crossBorderCurrency = crossBorderCurrency;
    }

    public String getCdvNo() {
        return cdvNo;
    }

    public void setCdvNo(String cdvNo) {
        this.cdvNo = cdvNo;
    }

    public String getIntPocket() {
        return intPocket;
    }

    public void setIntPocket(String intPocket) {
        this.intPocket = intPocket;
    }

    public String getClcRemitter() {
        return clcRemitter;
    }

    public void setClcRemitter(String clcRemitter) {
        this.clcRemitter = clcRemitter;
    }

    public Date getClcRemitDateLive() {
        return clcRemitDateLive;
    }

    public void setClcRemitDateLive(Date clcRemitDateLive) {
        this.clcRemitDateLive = clcRemitDateLive;
    }

    public String getClcBranchInd() {
        return clcBranchInd;
    }

    public void setClcBranchInd(String clcBranchInd) {
        this.clcBranchInd = clcBranchInd;
    }

    public String getBamBranchInd() {
        return bamBranchInd;
    }

    public void setBamBranchInd(String bamBranchInd) {
        this.bamBranchInd = bamBranchInd;
    }

    public String getFineSortTypeJhb() {
        return fineSortTypeJhb;
    }

    public void setFineSortTypeJhb(String fineSortTypeJhb) {
        this.fineSortTypeJhb = fineSortTypeJhb;
    }

    public String getFineSortTypeCtn() {
        return fineSortTypeCtn;
    }

    public void setFineSortTypeCtn(String fineSortTypeCtn) {
        this.fineSortTypeCtn = fineSortTypeCtn;
    }

    public String getFineSortTypeDbn() {
        return fineSortTypeDbn;
    }

    public void setFineSortTypeDbn(String fineSortTypeDbn) {
        this.fineSortTypeDbn = fineSortTypeDbn;
    }

    public String getFineSortTypePe() {
        return fineSortTypePe;
    }

    public void setFineSortTypePe(String fineSortTypePe) {
        this.fineSortTypePe = fineSortTypePe;
    }

    public String getClrcl6ReportReqJhb() {
        return clrcl6ReportReqJhb;
    }

    public void setClrcl6ReportReqJhb(String clrcl6ReportReqJhb) {
        this.clrcl6ReportReqJhb = clrcl6ReportReqJhb;
    }

    public String getClrcl6ReportReqCtn() {
        return clrcl6ReportReqCtn;
    }

    public void setClrcl6ReportReqCtn(String clrcl6ReportReqCtn) {
        this.clrcl6ReportReqCtn = clrcl6ReportReqCtn;
    }

    public String getClrcl6ReportReqDbn() {
        return clrcl6ReportReqDbn;
    }

    public void setClrcl6ReportReqDbn(String clrcl6ReportReqDbn) {
        this.clrcl6ReportReqDbn = clrcl6ReportReqDbn;
    }

    public String getClrcl6ReportReqPe() {
        return clrcl6ReportReqPe;
    }

    public void setClrcl6ReportReqPe(String clrcl6ReportReqPe) {
        this.clrcl6ReportReqPe = clrcl6ReportReqPe;
    }

    public String getClcChequesToJhb() {
        return clcChequesToJhb;
    }

    public void setClcChequesToJhb(String clcChequesToJhb) {
        this.clcChequesToJhb = clcChequesToJhb;
    }

    public String getClcChequesToCtn() {
        return clcChequesToCtn;
    }

    public void setClcChequesToCtn(String clcChequesToCtn) {
        this.clcChequesToCtn = clcChequesToCtn;
    }

    public String getClcChequesToDbn() {
        return clcChequesToDbn;
    }

    public void setClcChequesToDbn(String clcChequesToDbn) {
        this.clcChequesToDbn = clcChequesToDbn;
    }

    public String getClcChequesToPe() {
        return clcChequesToPe;
    }

    public void setClcChequesToPe(String clcChequesToPe) {
        this.clcChequesToPe = clcChequesToPe;
    }

    public String getCourierRouteJhb() {
        return courierRouteJhb;
    }

    public void setCourierRouteJhb(String courierRouteJhb) {
        this.courierRouteJhb = courierRouteJhb;
    }

    public String getCourierRouteCtn() {
        return courierRouteCtn;
    }

    public void setCourierRouteCtn(String courierRouteCtn) {
        this.courierRouteCtn = courierRouteCtn;
    }

    public String getCourierRouteDbn() {
        return courierRouteDbn;
    }

    public void setCourierRouteDbn(String courierRouteDbn) {
        this.courierRouteDbn = courierRouteDbn;
    }

    public String getCourierRoutePe() {
        return courierRoutePe;
    }

    public void setCourierRoutePe(String courierRoutePe) {
        this.courierRoutePe = courierRoutePe;
    }

    public Short getPocketNoJhb() {
        return pocketNoJhb;
    }

    public void setPocketNoJhb(Short pocketNoJhb) {
        this.pocketNoJhb = pocketNoJhb;
    }

    public Short getPocketNoCtn() {
        return pocketNoCtn;
    }

    public void setPocketNoCtn(Short pocketNoCtn) {
        this.pocketNoCtn = pocketNoCtn;
    }

    public Short getPocketNoDbn() {
        return pocketNoDbn;
    }

    public void setPocketNoDbn(Short pocketNoDbn) {
        this.pocketNoDbn = pocketNoDbn;
    }

    public Short getPocketNoPe() {
        return pocketNoPe;
    }

    public void setPocketNoPe(Short pocketNoPe) {
        this.pocketNoPe = pocketNoPe;
    }

    public String getEftCtrlBreakInd() {
        return eftCtrlBreakInd;
    }

    public void setEftCtrlBreakInd(String eftCtrlBreakInd) {
        this.eftCtrlBreakInd = eftCtrlBreakInd;
    }

    public String getEftDebitsNalToBrn() {
        return eftDebitsNalToBrn;
    }

    public void setEftDebitsNalToBrn(String eftDebitsNalToBrn) {
        this.eftDebitsNalToBrn = eftDebitsNalToBrn;
    }

    public String getEftExceptionAccType() {
        return eftExceptionAccType;
    }

    public void setEftExceptionAccType(String eftExceptionAccType) {
        this.eftExceptionAccType = eftExceptionAccType;
    }

    public String getEftStreamCode() {
        return eftStreamCode;
    }

    public void setEftStreamCode(String eftStreamCode) {
        this.eftStreamCode = eftStreamCode;
    }

    public String getEftStreamDataXmit() {
        return eftStreamDataXmit;
    }

    public void setEftStreamDataXmit(String eftStreamDataXmit) {
        this.eftStreamDataXmit = eftStreamDataXmit;
    }

    public String getEftXmitInd() {
        return eftXmitInd;
    }

    public void setEftXmitInd(String eftXmitInd) {
        this.eftXmitInd = eftXmitInd;
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

    public Date getDeletionRequestDateBam() {
        return deletionRequestDateBam;
    }

    public void setDeletionRequestDateBam(Date deletionRequestDateBam) {
        this.deletionRequestDateBam = deletionRequestDateBam;
    }

    public Date getDateLastChanged() {
        return dateLastChanged;
    }

    public void setDateLastChanged(Date dateLastChanged) {
        this.dateLastChanged = dateLastChanged;
    }

    public String getNaedossBranchInd() {
        return naedossBranchInd;
    }

    public void setNaedossBranchInd(String naedossBranchInd) {
        this.naedossBranchInd = naedossBranchInd;
    }

    public String getAedossBranchInd() {
        return aedossBranchInd;
    }

    public void setAedossBranchInd(String aedossBranchInd) {
        this.aedossBranchInd = aedossBranchInd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchNo != null ? branchNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CisBranchEntity)) {
            return false;
        }
        CisBranchEntity other = (CisBranchEntity) object;
        if ((this.branchNo == null && other.branchNo != null) || (this.branchNo != null && !this.branchNo.equals(other.branchNo))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "CisBranchEntity [branchNo=" + branchNo + ", branchName="
				+ branchName + ", division=" + division + ", abbrDivisionName="
				+ abbrDivisionName + ", agencyBranchNo=" + agencyBranchNo
				+ ", memberNo=" + memberNo + ", baMemberNo=" + baMemberNo
				+ ", institutionType=" + institutionType + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2
				+ ", addressLine3=" + addressLine3 + ", city=" + city
				+ ", postCode=" + postCode + ", country=" + country
				+ ", poBoxNo=" + poBoxNo + ", postOffice=" + postOffice
				+ ", postalCity=" + postalCity + ", postalPostCode="
				+ postalPostCode + ", postalProvince=" + postalProvince
				+ ", postalCountry=" + postalCountry + ", areaCode=" + areaCode
				+ ", telNo=" + telNo + ", faxNo=" + faxNo + ", emailAddress="
				+ emailAddress + ", telexNo1=" + telexNo1 + ", telexNo2="
				+ telexNo2 + ", telegraphicAddress=" + telegraphicAddress
				+ ", language=" + language + ", eft2dayAllowToNoncomp="
				+ eft2dayAllowToNoncomp + ", eftBranchInd=" + eftBranchInd
				+ ", inAreaIndicator=" + inAreaIndicator + ", paperToFollow="
				+ paperToFollow + ", crossBorderFlag=" + crossBorderFlag
				+ ", crossBorderCurrency=" + crossBorderCurrency + ", cdvNo="
				+ cdvNo + ", intPocket=" + intPocket + ", clcRemitter="
				+ clcRemitter + ", clcRemitDateLive=" + clcRemitDateLive
				+ ", clcBranchInd=" + clcBranchInd + ", bamBranchInd="
				+ bamBranchInd + ", fineSortTypeJhb=" + fineSortTypeJhb
				+ ", fineSortTypeCtn=" + fineSortTypeCtn + ", fineSortTypeDbn="
				+ fineSortTypeDbn + ", fineSortTypePe=" + fineSortTypePe
				+ ", clrcl6ReportReqJhb=" + clrcl6ReportReqJhb
				+ ", clrcl6ReportReqCtn=" + clrcl6ReportReqCtn
				+ ", clrcl6ReportReqDbn=" + clrcl6ReportReqDbn
				+ ", clrcl6ReportReqPe=" + clrcl6ReportReqPe
				+ ", clcChequesToJhb=" + clcChequesToJhb + ", clcChequesToCtn="
				+ clcChequesToCtn + ", clcChequesToDbn=" + clcChequesToDbn
				+ ", clcChequesToPe=" + clcChequesToPe + ", courierRouteJhb="
				+ courierRouteJhb + ", courierRouteCtn=" + courierRouteCtn
				+ ", courierRouteDbn=" + courierRouteDbn + ", courierRoutePe="
				+ courierRoutePe + ", pocketNoJhb=" + pocketNoJhb
				+ ", pocketNoCtn=" + pocketNoCtn + ", pocketNoDbn="
				+ pocketNoDbn + ", pocketNoPe=" + pocketNoPe
				+ ", eftCtrlBreakInd=" + eftCtrlBreakInd
				+ ", eftDebitsNalToBrn=" + eftDebitsNalToBrn
				+ ", eftExceptionAccType=" + eftExceptionAccType
				+ ", eftStreamCode=" + eftStreamCode + ", eftStreamDataXmit="
				+ eftStreamDataXmit + ", eftXmitInd=" + eftXmitInd
				+ ", deletionRequestDateClc=" + deletionRequestDateClc
				+ ", deletionRequestDateEft=" + deletionRequestDateEft
				+ ", deletionRequestDateBam=" + deletionRequestDateBam
				+ ", dateLastChanged=" + dateLastChanged
				+ ", naedossBranchInd=" + naedossBranchInd
				+ ", aedossBranchInd=" + aedossBranchInd + "]";
	}

  
    
}
