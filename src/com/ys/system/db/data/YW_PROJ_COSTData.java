package com.ys.system.db.data;

import java.sql.*;
import java.io.InputStream;

/**
* <p>Title: </p>
* <p>Description: </p>
* <p>Copyright: gentleman</p>
* <p>Company: gentleman</p>
* @author mengfanchang
* @version 1.0
*/
public class YW_PROJ_COSTData implements java.io.Serializable
{

	public YW_PROJ_COSTData()
	{
	}

	/**
	*
	*/
	private String id;
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id=id;
	}

	/**
	*
	*/
	private int proj_id;
	public int getProj_id()
	{
		return this.proj_id;
	}
	public void setProj_id(int proj_id)
	{
		this.proj_id=proj_id;
	}

	/**
	*
	*/
	private Float cost_mach;
	public Float getCost_mach()
	{
		return this.cost_mach;
	}
	public void setCost_mach(Float cost_mach)
	{
		this.cost_mach=cost_mach;
	}


	/**
	*
	*/
	private Float cost_mach_gear;
	public Float getCost_mach_gear()
	{
		return this.cost_mach_gear;
	}
	public void setCost_mach_gear(Float cost_mach_gear)
	{
		this.cost_mach_gear=cost_mach_gear;
	}


	/**
	*
	*/
	private Float cost_mach_bat_pkg;
	public Float getCost_mach_bat_pkg()
	{
		return this.cost_mach_bat_pkg;
	}
	public void setCost_mach_bat_pkg(Float cost_mach_bat_pkg)
	{
		this.cost_mach_bat_pkg=cost_mach_bat_pkg;
	}

	/**
	*
	*/
	private Float cost_mach_char;
	public Float getCost_mach_char()
	{
		return this.cost_mach_char;
	}
	public void setCost_mach_char(Float cost_mach_char)
	{
		this.cost_mach_char=cost_mach_char;
	}

	/**
	*
	*/
	private Float cost_mach_diec;
	public Float getCost_mach_diec()
	{
		return this.cost_mach_diec;
	}
	public void setCost_mach_diec(Float cost_mach_diec)
	{
		this.cost_mach_diec=cost_mach_diec;
	}

	/**
	*
	*/
	private Float cost_mach_hard;
	public Float getCost_mach_hard()
	{
		return this.cost_mach_hard;
	}
	public void setCost_mach_hard(Float cost_mach_hard)
	{
		this.cost_mach_hard=cost_mach_hard;
	}

	/**
	*
	*/
	private Float cost_pack_blow;
	public Float getCost_pack_blow()
	{
		return this.cost_pack_blow;
	}
	public void setCost_pack_blow(Float cost_pack_blow)
	{
		this.cost_pack_blow=cost_pack_blow;
	}

	/**
	*
	*/
	private Float cost_pack_inj;
	public Float getCost_pack_inj()
	{
		return this.cost_pack_inj;
	}
	public void setCost_pack_inj(Float cost_pack_inj)
	{
		this.cost_pack_inj=cost_pack_inj;
	}

	/**
	*
	*/
	private Float cost_pack_bli;
	public Float getCost_pack_bli()
	{
		return this.cost_pack_bli;
	}
	public void setCost_pack_bli(Float cost_pack_bli)
	{
		this.cost_pack_bli=cost_pack_bli;
	}

	/**
	*
	*/
	private Float cost_3d_hand;
	public Float getCost_3d_hand()
	{
		return this.cost_3d_hand;
	}
	public void setCost_3d_hand(Float cost_3d_hand)
	{
		this.cost_3d_hand=cost_3d_hand;
	}

	/**
	*
	*/
	private Float describes;
	public Float getDescribes()
	{
		return this.describes;
	}
	public void setDescribes(Float describes)
	{
		this.describes=describes;
	}

	/**
	*
	*/
	private Float cost_pat_ext;
	public Float getCost_pat_ext()
	{
		return this.cost_pat_ext;
	}
	public void setCost_pat_ext(Float cost_pat_ext)
	{
		this.cost_pat_ext=cost_pat_ext;
	}

	/**
	*
	*/
	private Float cost_pat_prac;
	public Float getCost_pat_prac()
	{
		return this.cost_pat_prac;
	}
	public void setCost_pat_prac(Float cost_pat_prac)
	{
		this.cost_pat_prac=cost_pat_prac;
	}

	/**
	*
	*/
	private Float cost_pat_inv;
	public Float getCost_pat_inv()
	{
		return this.cost_pat_inv;
	}
	public void setCost_pat_inv(Float cost_pat_inv)
	{
		this.cost_pat_inv=cost_pat_inv;
	}

	/**
	*
	*/
	private Float cost_pat_query;
	public Float getCost_pat_query()
	{
		return this.cost_pat_query;
	}
	public void setCost_pat_query(Float cost_pat_query)
	{
		this.cost_pat_query=cost_pat_query;
	}

	/**
	*
	*/
	private Float cost_design;
	public Float getCost_design()
	{
		return this.cost_design;
	}
	public void setCost_design(Float cost_design)
	{
		this.cost_design=cost_design;
	}
	/**
	*
	*/
	private int pilot_qty;
	public int getPilot_qty()
	{
		return this.pilot_qty;
	}
	public void setPilot_qty(int pilot_qty)
	{
		this.pilot_qty=pilot_qty;
	}

	/**
	*
	*/
	private String createtime;
	public String getCreatetime()
	{
		return this.createtime;
	}
	public void setCreatetime(String createtime)
	{
		this.createtime=createtime;
	}

	/**
	*
	*/
	private String createperson;
	public String getCreateperson()
	{
		return this.createperson;
	}
	public void setCreateperson(String createperson)
	{
		this.createperson=createperson;
	}

	/**
	*
	*/
	private String createunitid;
	public String getCreateunitid()
	{
		return this.createunitid;
	}
	public void setCreateunitid(String createunitid)
	{
		this.createunitid=createunitid;
	}

	/**
	*
	*/
	private String modifytime;
	public String getModifytime()
	{
		return this.modifytime;
	}
	public void setModifytime(String modifytime)
	{
		this.modifytime=modifytime;
	}

	/**
	*
	*/
	private String modifyperson;
	public String getModifyperson()
	{
		return this.modifyperson;
	}
	public void setModifyperson(String modifyperson)
	{
		this.modifyperson=modifyperson;
	}

	/**
	*
	*/
	private String deleteflag;
	public String getDeleteflag()
	{
		return this.deleteflag;
	}
	public void setDeleteflag(String deleteflag)
	{
		this.deleteflag=deleteflag;
	}

	/**
	*
	*/
	private String deptguid;
	public String getDeptguid()
	{
		return this.deptguid;
	}
	public void setDeptguid(String deptguid)
	{
		this.deptguid=deptguid;
	}

	/**
	*
	*/
	private String formid;
	public String getFormid()
	{
		return this.formid;
	}
	public void setFormid(String formid)
	{
		this.formid=formid;
	}

	/**
	*����ֵ
	*/
	private String returnvalue;
	public String getReturnvalue()
	{
		return this.returnvalue;
	}
	public void setReturnvalue(String returnvalue)
	{
		this.returnvalue=returnvalue;
	}

	/**
	*����ֵ
	*/
	private String returnsql;
	public String getReturnsql()
	{
		return this.returnsql;
	}
	public void setReturnsql(String returnsql)
	{
		this.returnsql=returnsql;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append("***** DataObject list begin *****\n");		sb.append("id = "+(id == null ? "null" : id)+"\n");		sb.append("proj_id = "+proj_id+"\n");		sb.append("cost_mach = "+(cost_mach == null ? "null" : cost_mach.toString())+"\n");		sb.append("cost_mach_gear = "+(cost_mach_gear == null ? "null" : cost_mach_gear.toString())+"\n");		sb.append("cost_mach_bat_pkg = "+(cost_mach_bat_pkg == null ? "null" : cost_mach_bat_pkg.toString())+"\n");		sb.append("cost_mach_char = "+(cost_mach_char == null ? "null" : cost_mach_char.toString())+"\n");		sb.append("cost_mach_diec = "+(cost_mach_diec == null ? "null" : cost_mach_diec.toString())+"\n");		sb.append("cost_mach_hard = "+(cost_mach_hard == null ? "null" : cost_mach_hard.toString())+"\n");		sb.append("cost_pack_blow = "+(cost_pack_blow == null ? "null" : cost_pack_blow.toString())+"\n");		sb.append("cost_pack_inj = "+(cost_pack_inj == null ? "null" : cost_pack_inj.toString())+"\n");		sb.append("cost_pack_bli = "+(cost_pack_bli == null ? "null" : cost_pack_bli.toString())+"\n");		sb.append("cost_3d_hand = "+(cost_3d_hand == null ? "null" : cost_3d_hand.toString())+"\n");		sb.append("describes = "+(describes == null ? "null" : describes.toString())+"\n");		sb.append("cost_pat_ext = "+(cost_pat_ext == null ? "null" : cost_pat_ext.toString())+"\n");		sb.append("cost_pat_prac = "+(cost_pat_prac == null ? "null" : cost_pat_prac.toString())+"\n");		sb.append("cost_pat_inv = "+(cost_pat_inv == null ? "null" : cost_pat_inv.toString())+"\n");		sb.append("cost_pat_query = "+(cost_pat_query == null ? "null" : cost_pat_query.toString())+"\n");		sb.append("cost_design = "+(cost_design == null ? "null" : cost_design.toString())+"\n");		sb.append("pilot_qty = "+pilot_qty+"\n");		sb.append("createtime = "+(createtime == null ? "null" : createtime)+"\n");		sb.append("createperson = "+(createperson == null ? "null" : createperson)+"\n");		sb.append("createunitid = "+(createunitid == null ? "null" : createunitid)+"\n");		sb.append("modifytime = "+(modifytime == null ? "null" : modifytime)+"\n");		sb.append("modifyperson = "+(modifyperson == null ? "null" : modifyperson)+"\n");		sb.append("deleteflag = "+(deleteflag == null ? "null" : deleteflag)+"\n");		sb.append("deptguid = "+(deptguid == null ? "null" : deptguid)+"\n");		sb.append("formid = "+(formid == null ? "null" : formid)+"\n");		sb.append("returnvalue = "+(returnvalue == null ? "null" : returnvalue)+"\n");		sb.append("returnsql = "+(returnsql == null ? "null" : returnsql)+"\n");		sb.append("***** DataObject list end *****\n");
		return sb.toString() ;
	}


	public void toTrim() {
		id= (id == null ?null : id.trim());		createtime= (createtime == null ?null : createtime.trim());		createperson= (createperson == null ?null : createperson.trim());		createunitid= (createunitid == null ?null : createunitid.trim());		modifytime= (modifytime == null ?null : modifytime.trim());		modifyperson= (modifyperson == null ?null : modifyperson.trim());		deleteflag= (deleteflag == null ?null : deleteflag.trim());		deptguid= (deptguid == null ?null : deptguid.trim());		formid= (formid == null ?null : formid.trim());		returnvalue= (returnvalue == null ?null : returnvalue.trim());		returnsql= (returnsql == null ?null : returnsql.trim());
	}

}
