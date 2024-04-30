package com.bsva.businessLogic;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import com.bsva.ModelClass;
import com.bsva.commons.model.SchedulerModel;

public class SchedulerLogic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SchedulerModel> schedulerModelExtract = new ArrayList<SchedulerModel>();
	private List<SchedulerModel> schedulerModelSod = new ArrayList<SchedulerModel>();
	private List<SchedulerModel> schedulerModelEod = new ArrayList<SchedulerModel>();

	public void addSchedulerExtract(SchedulerModel schedulerModel) {
		if (!schedulerModel.equals(null)) {

			if (schedulerModelExtract.size() != 0) {

				for (SchedulerModel schedulerModeltemp : schedulerModelExtract) {

					schedulerModeltemp.setCron(schedulerModel.getCron());
					schedulerModeltemp.setLastExecutTime(schedulerModel
							.getLastExecutTime());
					schedulerModeltemp.setNextExecutTime(schedulerModel
							.getNextExecutTime());
					schedulerModeltemp.setStatus(schedulerModel.getStatus());
				}

			} else {
				schedulerModelExtract.add(schedulerModel);
			}

		}

		ModelClass.schedulerModelExtract = schedulerModelExtract;

	}

	public void addSchedulerEod(SchedulerModel schedulerModel) {

		if (!schedulerModel.equals(null)) {

			if (schedulerModelEod.size() != 0) {

				for (SchedulerModel schedulerModeltemp : schedulerModelEod) {

					schedulerModeltemp.setCron(schedulerModel.getCron());
					schedulerModeltemp.setLastExecutTime(schedulerModel
							.getLastExecutTime());
					schedulerModeltemp.setNextExecutTime(schedulerModel
							.getNextExecutTime());
					schedulerModeltemp.setStatus(schedulerModel.getStatus());
				}

			} else {
				schedulerModelEod.add(schedulerModel);
			}

		}

		ModelClass.schedulerModelEod = schedulerModelEod;

	}

	public void addSchedulerSod(SchedulerModel schedulerModel) {

		if (!schedulerModel.equals(null)) {

			if (schedulerModelSod.size() != 0) {

				for (SchedulerModel schedulerModeltemp : schedulerModelSod) {

					schedulerModeltemp.setCron(schedulerModel.getCron());
					schedulerModeltemp.setLastExecutTime(schedulerModel
							.getLastExecutTime());
					schedulerModeltemp.setNextExecutTime(schedulerModel
							.getNextExecutTime());
					schedulerModeltemp.setStatus(schedulerModel.getStatus());
				}

			} else {
				schedulerModelSod.add(schedulerModel);
			}

		}

		ModelClass.schedulerModelSod = schedulerModelSod;

	}

}
