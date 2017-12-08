/*
 * 业务相关的常量,函数等
 */

//自制品单价处理:单位换算用
var optionWeight =	"<option value=103>克</option>"+
					"<option value=102>千克</option>" +
					"<option value=101>吨</option>";

var optionSize = 	"<option value=1021>厘米</option>"  +
					"<option value=1011>米</option>";
				
var unitAaary = [
	['1','吨'],
	['1000','千克'],
	['1000000','克'],
	['1','米'],
	['10','分米'],
	['100','厘米']
	
];
	
function getUnitChange(unit){
	var fchgunit = 1;
	for(var i=0;i<unitAaary.length;i++){
		var val = unitAaary[i][0];//取得计算单位:100,1000...
		var key = unitAaary[i][1];//取得显示单位:克,吨...
		if(unit == key){
			fchgunit = val;//只有在需要换算的时候,才设置换算单位
			break;
		}
	}
	return fchgunit;
}