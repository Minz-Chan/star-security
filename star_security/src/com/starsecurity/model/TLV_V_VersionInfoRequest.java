package com.starsecurity.model;

/* version info: remote -> streaming server.  No response need */
//TLV_T: TLV_T_VERSION_INFO_ANSWER
//TLV_L: sizeof(TLV_V_VersionInfoRequest)
public class TLV_V_VersionInfoRequest {
	private int versionMajor;		// major version
	private int versionMinor;		// minor version
	public int getVersionMajor() {
		return versionMajor;
	}
	public void setVersionMajor(int versionMajor) {
		this.versionMajor = versionMajor;
	}
	public int getVersionMinor() {
		return versionMinor;
	}
	public void setVersionMinor(int versionMinor) {
		this.versionMinor = versionMinor;
	}
}
