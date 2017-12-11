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

function addPhotoRow(id,tdTable, index,serPath) {
	
	for (var i = 0; i < 1; i++) {

		var path = serPath + "/images/blankDemo.png";
		var pathDel = '';
		var trHtml = '';

		trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
		trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
		trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
		trHtml += '<td><div id="uploadFile'+tdTable+index+'" ><input type="file"  id="photoFile" name="photoFile" '+
				'onchange="uploadPhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\'' + ',' + index + ');" accept="image/*" style="max-width: 250px;" /></div></td>';
		trHtml += '<td width="50px"><div id="deleteFile'+tdTable+index+'" ><a href="###" '+
				'onclick=\"deletePhoto(' + '\'' + id + '\'' + ','+ '\''+ tdTable + '\'' + ',' + '\'' + pathDel+ '\''+ ')\">删除</a></div></td>';
		trHtml += "</tr>";
		trHtml += '<tr><td colspan="2"  style="height:300px;text-align: center;""><img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  /></td>';
		trHtml += '</tr>';
		trHtml += '</table>';
		trHtml += '</td>';
		
	}//for		

	return trHtml;
}

function showPhotoRow(id,tdTable,path,pathDel,index) {
	var trHtml='';
	trHtml += '<td class="photo" style="text-align:center;padding: 10px;">';
	trHtml += '<table style="width:400px;margin: auto;" class="form" id="tb'+index+'">';
	trHtml += '<tr style="background: #d4d0d0;height: 35px;">';
	trHtml += '<td></td>';
	trHtml += '<td width="50px"><a id="uploadFile' + index + '" href="###" '+
			'onclick="deletePhoto(' + '\'' + id + '\'' + ',' + '\'' + tdTable + '\''+ ',' + '\'' + pathDel + '\'' + ');">删除</a></td>';
	trHtml += "</tr>";
	trHtml += '<tr><td colspan="2"  style="height:300px;">';
	trHtml += '<a id=linkFile'+tdTable+index+'" href="'+path+'" target="_blank">';
	trHtml += '<img id="imgFile'+tdTable+index+'" src="'+path+'" style="max-width: 400px;max-height:300px"  />';
	trHtml += '</a>';
	trHtml += '</td>';
	trHtml += '</tr>';
	trHtml += '</table>';
	trHtml += '</td>';

	return trHtml;
}