/*******************************************************************************
 * Copyright (c) 2017 QNX Software Systems and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     QNX Software Systems - initial version
 *     Espressif Systems Ltd — Kondal Kolipaka <kondal.kolipaka@espressif.com>

 *******************************************************************************/
package com.espressif.launch.serial.ui.internal;

import org.eclipse.launchbar.core.target.ILaunchTarget;
import org.eclipse.launchbar.core.target.ILaunchTargetManager;
import org.eclipse.launchbar.core.target.ILaunchTargetWorkingCopy;
import org.eclipse.launchbar.ui.target.LaunchTargetWizard;

import com.espressif.launch.serial.SerialFlashLaunchTargetProvider;

public class NewSerialFlashTargetWizard extends LaunchTargetWizard {

	private NewSerialFlashTargetWizardPage page;

	public NewSerialFlashTargetWizard() {
		setWindowTitle(Messages.NewSerialFlashTargetWizard_Title);
	}

	@Override
	public void addPages() {
		super.addPages();

		page = new NewSerialFlashTargetWizardPage(getLaunchTarget());
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		ILaunchTargetManager manager = Activator.getService(ILaunchTargetManager.class);
		String typeId = SerialFlashLaunchTargetProvider.TYPE_ID;
		String id = page.getTargetName();

		ILaunchTarget target = getLaunchTarget();
		if (target == null) {
			target = manager.addLaunchTarget(typeId, id);
		}

		ILaunchTargetWorkingCopy wc = target.getWorkingCopy();
		wc.setId(id);
		wc.setAttribute(ILaunchTarget.ATTR_OS, page.getOS());
		wc.setAttribute(ILaunchTarget.ATTR_ARCH, page.getArch());
		wc.setAttribute(SerialFlashLaunchTargetProvider.ATTR_SERIAL_PORT, page.getSerialPortName());
		wc.save();

		return true;
	}

}
