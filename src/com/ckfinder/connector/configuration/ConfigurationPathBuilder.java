/*
 * CKFinder
 * ========
 * http://ckfinder.com
 * Copyright (C) 2007-2013, CKSource - Frederico Knabben. All rights reserved.
 *
 * The software, this file and its contents are subject to the CKFinder
 * License. Please read the license.txt file before using, installing, copying,
 * modifying or distribute this file or part of its contents. The contents of
 * this file is part of the Source Code of CKFinder.
 */
package com.ckfinder.connector.configuration;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.ckfinder.connector.utils.PathUtils;
import com.ys.system.common.BusinessConstants;

/**
 * Path builder that creates default values of baseDir and baseURL.
 */
public class ConfigurationPathBuilder extends DefaultPathBuilder {

	/**
	 * Gets configuration value of baseUrl. When config value is not set, then
	 * return default value.
	 *
	 * @param request request
	 * @return default baseDir value
	 */
	@Override
	public String getBaseUrl(final HttpServletRequest request) {
		String baseURL = null;
		try {
			IConfiguration conf = ConfigurationFactory.getInstace().getConfiguration();
			//TODO
			//可以通过id传递一些信息
			String id = request.getParameter("id");
			if (id == null) {
				id = "";
			} else {
				id = id.split("-")[1];
			}
			String userURL = "";
			Object tempURL = request.getSession().getAttribute(BusinessConstants.FILESYSTEMBROWSERBASEFOLDER + id);
			
			if (tempURL != null) {
				userURL = String.valueOf(tempURL);
			}
			baseURL = conf.getBaseURL();
			baseURL = PathUtils.addSlashToEnd(baseURL) + userURL;
		} catch (Exception e) {
			baseURL = null;
		}
		if (baseURL == null || baseURL.equals("")) {
			baseURL = super.getBaseUrl(request);
		}

		return PathUtils.addSlashToBeginning(PathUtils.addSlashToEnd(baseURL));
	}

	/**
	 * Gets configuration value of baseDir. When config value is not set, then
	 * return default value.
	 *
	 * @param request request
	 * @return default baseDir value
	 */
	@Override
	public String getBaseDir(final HttpServletRequest request) {
		String baseDir = null;
		try {
			IConfiguration conf = ConfigurationFactory.getInstace().getConfiguration();
			baseDir = conf.getBaseDir();
		} catch (Exception e) {
			baseDir = null;
		}
		if (baseDir == null || baseDir.equals("")) {
			return super.getBaseDir(request);
		} else {
			return baseDir;
		}
	}
}
