package com.ys.system.service.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ys.system.db.dao.S_DICDao;
import com.ys.system.db.data.S_DICData;
import com.ys.system.service.common.BaseService;
import com.ys.util.basedao.BaseTransaction;
import com.ys.util.basequery.BaseQuery;
import com.ys.util.basequery.common.BaseModel;


@Service
public class TestService extends BaseService {
 
	//@EJB(beanName="DbUpdateEjb") com.ys.system.ejb.DbUpdateEjbLocal DbUpdateEjb;
	Context ctx;
	
	public void getDB() {
		ArrayList<Integer> iNum = new ArrayList<Integer>();
		ArrayList<Integer> sNum = new ArrayList<Integer>();
		ArrayList<Integer> tNum = new ArrayList<Integer>();
		long counter = 0;
		long sumTotal = 0;
		PrintWriter out = null;
		
		resetiNum(iNum);
		resettNum(tNum);
		try {
			File x = new File("e:/temp/sOut.txt");
			x.createNewFile();
			out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(x), "utf-8"));
			
		}
		catch(Exception e) {
			
		}
		for (int i = 1; i <= iNum.size() + 1; i++) {
			boolean isContinue = false;
			if (tNum.get(5).intValue() == 33) {
				tNum.set(5, Integer.valueOf(0));
				for(int j = 4; j >= 0; j--) {
					int temp = tNum.get(j).intValue();
					if (temp < (33 - (5 - j))) {
						tNum.set(j, temp + 1);
						i = temp + 1;
						isContinue = true;
						break;
					} else {
						tNum.set(j, Integer.valueOf(0));
					}
				}
			}
			if (isContinue) {
				continue;
			}

			int tIndex = istNumFull(tNum);
			if (tIndex == -1) {
				if (i >= 34) {
					break;
				}
				tNum.set(5, Integer.valueOf(i));
				//TODO
				sumTotal = 0;
				String sStr = "";
				boolean isSeries = false;
				int i9count, i10count, i20count, i30count;
				i9count = 0;
				i10count = 0;
				i20count = 0;
				i30count = 0;
				for(int c = 0; c < tNum.size(); c++) {
					sumTotal += tNum.get(c).intValue();
					sStr += tNum.get(c).intValue() + ",";
					if (c != tNum.size() - 1) {
						if (tNum.get(c + 1).intValue() - tNum.get(c).intValue() == 1) {
							isSeries = true;
						}
					}
					if (tNum.get(c).intValue() <= 9) {
						i9count++;
					}
					if (tNum.get(c).intValue() >= 10 && tNum.get(c).intValue() < 20) {
						i10count++;
					}	
					if (tNum.get(c).intValue() >= 20 && tNum.get(c).intValue() < 30) {
						i20count++;
					}
					if (tNum.get(c).intValue() >= 30) {
						i30count++;
					}
				}
				
				if ((sumTotal >= 80) && (sumTotal <= 130)) {
					if (isSeries == false) {
						if (i9count < 3) {
							if (i10count != 0 && i10count != 4 && i10count != 5) {
								if (i20count != 0 && i20count != 4 && i20count != 5) {
									if (i30count < 2) {
										counter++;
										out.println(sStr);
									}
								}
							}
						}

					}
				}
				
			} else {
				tNum.set(tIndex, Integer.valueOf(i));
			}
		}
		out.close();
		System.out.println(counter);

	}
	
	private void resetiNum(ArrayList<Integer> iNum) {
		for (int i = 1; i <= 33; i++) {
			iNum.add(Integer.valueOf(i));
		}
	}

	private void resettNum(ArrayList<Integer> tNum) {
		for (int i = 1; i <= 6; i++) {
			tNum.add(Integer.valueOf(0));
		}
	}
	
	private int istNumFull(ArrayList<Integer> tNum) {
		int val = -1;
		for (int i = 1; i <= 5; i++) {
			if (tNum.get(i - 1).intValue() == 0) {
				val = i - 1;
				break;
			}
		}
		
		return val;
	}
}
