package com.ipvision.domain;

public class ChannelObj {
	private boolean status;
	private String channelName;
	private String channelURL;
	private boolean domain;
	private int channelId;
	private String image;
	private String sourceLink;
	
	public ChannelObj(boolean status, String channelName, String channelURL,boolean domain,int channelId,String image,String sourceLink) {
		super();
		this.status = status;
		this.channelName = channelName;
		this.channelURL = channelURL;
		this.domain=domain;
		this.channelId = channelId;
		this.image = image;
		this.sourceLink = sourceLink;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isDomain() {
		return domain;
	}

	public void setDomain(boolean domain) {
		this.domain = domain;
	}

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelURL() {
		return channelURL;
	}
	public void setChannelURL(String channelURL) {
		this.channelURL = channelURL;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	
	
	
}