package org.dtos;

import java.io.Serializable;

import org.model.DeadNeed;

public class RemainingNeedsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DeadNeed deadNeed;
	private int distributedValue;
	private int restValue;

	public RemainingNeedsDto() {
		// TODO Auto-generated constructor stub
	}

	public int getDistributedValue() {
		return distributedValue;
	}

	public void setDistributedValue(int distributedValue) {
		this.distributedValue = distributedValue;
	}

	public DeadNeed getDeadNeed() {
		return deadNeed;
	}

	public void setDeadNeed(DeadNeed deadNeed) {
		this.deadNeed = deadNeed;
	}

	public int getRestValue() {
		return restValue;
	}

	public void setRestValue(int restValue) {
		this.restValue = restValue;
	}

}
