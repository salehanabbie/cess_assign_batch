package com.bsva.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WebOpsFileRegModel implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	    private String filepath;
	    private String status;
	    private String reason;
	    private Date processDate;
	    private String nameSpace;
	    private String grpHdrMsgId;
	    private String inOutInd;
	    private String extractMsgId;
	    private String onlineInd;
	    
	    public WebOpsFileRegModel(String fileName) {
			super();
			this.fileName = fileName;
		}
	    
	    
		public WebOpsFileRegModel() {
			// TODO Auto-generated constructor stub
		}


		public String getFileName() {
			return fileName;
		}


		public void setFileName(String fileName) {
			this.fileName = fileName;
		}


		public String getFilepath() {
			return filepath;
		}


		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public String getReason() {
			return reason;
		}


		public void setReason(String reason) {
			this.reason = reason;
		}


		public Date getProcessDate() {
			return processDate;
		}


		public void setProcessDate(Date processDate) {
			this.processDate = processDate;
		}


		public String getNameSpace() {
			return nameSpace;
		}


		public void setNameSpace(String nameSpace) {
			this.nameSpace = nameSpace;
		}


		public String getGrpHdrMsgId() {
			return grpHdrMsgId;
		}


		public void setGrpHdrMsgId(String grpHdrMsgId) {
			this.grpHdrMsgId = grpHdrMsgId;
		}


		public String getInOutInd() {
			return inOutInd;
		}


		public void setInOutInd(String inOutInd) {
			this.inOutInd = inOutInd;
		}


		public String getExtractMsgId() {
			return extractMsgId;
		}


		public void setExtractMsgId(String extractMsgId) {
			this.extractMsgId = extractMsgId;
		}


		public String getOnlineInd() {
			return onlineInd;
		}


		public void setOnlineInd(String onlineInd) {
			this.onlineInd = onlineInd;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((extractMsgId == null) ? 0 : extractMsgId.hashCode());
			result = prime * result
					+ ((fileName == null) ? 0 : fileName.hashCode());
			result = prime * result
					+ ((filepath == null) ? 0 : filepath.hashCode());
			result = prime * result
					+ ((grpHdrMsgId == null) ? 0 : grpHdrMsgId.hashCode());
			result = prime * result
					+ ((inOutInd == null) ? 0 : inOutInd.hashCode());
			result = prime * result
					+ ((nameSpace == null) ? 0 : nameSpace.hashCode());
			result = prime * result
					+ ((onlineInd == null) ? 0 : onlineInd.hashCode());
			result = prime * result
					+ ((processDate == null) ? 0 : processDate.hashCode());
			result = prime * result
					+ ((reason == null) ? 0 : reason.hashCode());
			result = prime * result
					+ ((status == null) ? 0 : status.hashCode());
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
			WebOpsFileRegModel other = (WebOpsFileRegModel) obj;
			if (extractMsgId == null) {
				if (other.extractMsgId != null)
					return false;
			} else if (!extractMsgId.equals(other.extractMsgId))
				return false;
			if (fileName == null) {
				if (other.fileName != null)
					return false;
			} else if (!fileName.equals(other.fileName))
				return false;
			if (filepath == null) {
				if (other.filepath != null)
					return false;
			} else if (!filepath.equals(other.filepath))
				return false;
			if (grpHdrMsgId == null) {
				if (other.grpHdrMsgId != null)
					return false;
			} else if (!grpHdrMsgId.equals(other.grpHdrMsgId))
				return false;
			if (inOutInd == null) {
				if (other.inOutInd != null)
					return false;
			} else if (!inOutInd.equals(other.inOutInd))
				return false;
			if (nameSpace == null) {
				if (other.nameSpace != null)
					return false;
			} else if (!nameSpace.equals(other.nameSpace))
				return false;
			if (onlineInd == null) {
				if (other.onlineInd != null)
					return false;
			} else if (!onlineInd.equals(other.onlineInd))
				return false;
			if (processDate == null) {
				if (other.processDate != null)
					return false;
			} else if (!processDate.equals(other.processDate))
				return false;
			if (reason == null) {
				if (other.reason != null)
					return false;
			} else if (!reason.equals(other.reason))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			return true;
		}


		@Override
		public String toString() {
			return "WebOpsFileRegModel [fileName=" + fileName + ", filepath="
					+ filepath + ", status=" + status + ", reason=" + reason
					+ ", processDate=" + processDate + ", nameSpace="
					+ nameSpace + ", grpHdrMsgId=" + grpHdrMsgId
					+ ", inOutInd=" + inOutInd + ", extractMsgId="
					+ extractMsgId + ", onlineInd=" + onlineInd + "]";
		}


		
		
	    

}
