
package com.bsva.models;

import java.io.Serializable;
import java.util.Date;

public class WebMandateCountModel implements Serializable

{
                
                /**
                * @author ElelwaniR
                */
	private static final long serialVersionUID = 1L;
    private String instId;
    private String serviceId;
    private Integer nrOfFiles;
    private Integer nrOfMsgs;
    private String incoming;
    private String outgoing;
    private Date processDate;
    private Integer nrMsgsRejected;
    private String fileName;
    private String msgId;
    private Integer nrMsgsAccepted;
    private Integer nrMsgsExtracted;
    
                public WebMandateCountModel() 
                {
                                super();
                }


                public WebMandateCountModel(String instId) 
                {
                                super();
                                this.instId = instId;
                }


                public String getInstId() {
                                return instId;
                }


                public void setInstId(String instId) {
                                this.instId = instId;
                }


                public String getServiceId() {
                                return serviceId;
                }


                public void setServiceId(String serviceId) {
                                this.serviceId = serviceId;
                }


                public Integer getNrOfFiles() {
                                return nrOfFiles;
                }


                public void setNrOfFiles(Integer nrOfFiles) {
                                this.nrOfFiles = nrOfFiles;
                }


                public Integer getNrOfMsgs() {
                                return nrOfMsgs;
                }


                public void setNrOfMsgs(Integer nrOfMsgs) {
                                this.nrOfMsgs = nrOfMsgs;
                }


                public String getIncoming() {
                                return incoming;
                }


                public void setIncoming(String incoming) {
                                this.incoming = incoming;
                }


                public String getOutgoing() {
                                return outgoing;
                }


                public void setOutgoing(String outgoing) {
                                this.outgoing = outgoing;
                }


                public Date getProcessDate() {
                                return processDate;
                }


                public void setProcessDate(Date processDate) {
                                this.processDate = processDate;
                }


                public Integer getNrMsgsRejected() {
                                return nrMsgsRejected;
                }


	public void setNrMsgsRejected(Integer nrMsgsRejected) {
		this.nrMsgsRejected = nrMsgsRejected;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}


	public Integer getNrMsgsAccepted() {
		return nrMsgsAccepted;
	}


	public void setNrMsgsAccepted(Integer nrMsgsAccepted) {
		this.nrMsgsAccepted = nrMsgsAccepted;
	}


	public Integer getNrMsgsExtracted() {
		return nrMsgsExtracted;
	}


	public void setNrMsgsExtracted(Integer nrMsgsExtracted) {
		this.nrMsgsExtracted = nrMsgsExtracted;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((incoming == null) ? 0 : incoming.hashCode());
		result = prime * result + ((instId == null) ? 0 : instId.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result
				+ ((nrMsgsAccepted == null) ? 0 : nrMsgsAccepted.hashCode());
		result = prime * result
				+ ((nrMsgsExtracted == null) ? 0 : nrMsgsExtracted.hashCode());
		result = prime * result
				+ ((nrMsgsRejected == null) ? 0 : nrMsgsRejected.hashCode());
		result = prime * result
				+ ((nrOfFiles == null) ? 0 : nrOfFiles.hashCode());
		result = prime * result
				+ ((nrOfMsgs == null) ? 0 : nrOfMsgs.hashCode());
		result = prime * result
				+ ((outgoing == null) ? 0 : outgoing.hashCode());
		result = prime * result
				+ ((processDate == null) ? 0 : processDate.hashCode());
		result = prime * result
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		WebMandateCountModel other = (WebMandateCountModel) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (incoming == null) {
			if (other.incoming != null)
				return false;
		} else if (!incoming.equals(other.incoming))
			return false;
		if (instId == null) {
			if (other.instId != null)
				return false;
		} else if (!instId.equals(other.instId))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (nrMsgsAccepted == null) {
			if (other.nrMsgsAccepted != null)
				return false;
		} else if (!nrMsgsAccepted.equals(other.nrMsgsAccepted))
			return false;
		if (nrMsgsExtracted == null) {
			if (other.nrMsgsExtracted != null)
				return false;
		} else if (!nrMsgsExtracted.equals(other.nrMsgsExtracted))
			return false;
		if (nrMsgsRejected == null) {
			if (other.nrMsgsRejected != null)
				return false;
		} else if (!nrMsgsRejected.equals(other.nrMsgsRejected))
			return false;
		if (nrOfFiles == null) {
			if (other.nrOfFiles != null)
				return false;
		} else if (!nrOfFiles.equals(other.nrOfFiles))
			return false;
		if (nrOfMsgs == null) {
			if (other.nrOfMsgs != null)
				return false;
		} else if (!nrOfMsgs.equals(other.nrOfMsgs))
			return false;
		if (outgoing == null) {
			if (other.outgoing != null)
				return false;
		} else if (!outgoing.equals(other.outgoing))
			return false;
		if (processDate == null) {
			if (other.processDate != null)
				return false;
		} else if (!processDate.equals(other.processDate))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "WebMandateCountModel [instId=" + instId + ", serviceId="
				+ serviceId + ", nrOfFiles=" + nrOfFiles + ", nrOfMsgs="
				+ nrOfMsgs + ", incoming=" + incoming + ", outgoing="
				+ outgoing + ", processDate=" + processDate
				+ ", nrMsgsRejected=" + nrMsgsRejected + ", fileName="
				+ fileName + ", msgId=" + msgId + ", nrMsgsAccepted="
				+ nrMsgsAccepted + ", nrMsgsExtracted=" + nrMsgsExtracted + "]";
	}


}
