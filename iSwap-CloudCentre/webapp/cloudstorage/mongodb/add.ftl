<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/common/taglibs.ftl">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据源注册</title>
<link href="${path}/css/main.css" rel="stylesheet" type="text/css" />
<link href="${path}/css/tree.css" rel="stylesheet" type="text/css" />
<link href="${path}/css/pop.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="pop_01"  style="width:622px;height:500px;overflow-x:hidden;overflow-y:scroll;">
<div class="frameset_w" style="height:500px;background-color:#FFFFFF;">
  <div class="main"><div>
   <form name="save" action="${path}/cloudstorage/mongodb/mongodb!add.action" method="post" id="saveForm">
   <input type="hidden" id="id" value="0"/>
	<@s.token name="token"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center" valign="middle" class=""  height="100%" ><table width="100%" border="0" cellspacing="0" cellpadding="0"  height="100%">
			<tr>
			  <td  height="100%" valign="top" ><div class="">
				  <ul class="item1_c">
					<li>
					  <p> <b>*</b>数据源名称：</p>
					  <span>
					  <input type="text" size="30" name="sourceName" id="sourceName" maxLenght="30" />
					  </span> 
					  <span> <div id="sourceNameTip"></div></span>
					  </li>
					  <li  class="item2_bg">
					  <p> <b>*</b>数据源编码：</p>
					  <span>
					  <input type="text" size="30" name="sourceCode" id="sourceCode" maxLength="20"/>
					  </span> 
					  <span> <div id="sourceCodeTip"></div></span>
					  </li>
					<li class="item2_bg">
					  <p> <b>*</b>连接地址：</p>
					  <span>
					  <input type="text" size="30" name="ip" id="ip"/>
					  </span>
					   <span> <div id="ipTip"></div></span>
					</li>
					<li>
					  <p> <b>*</b>端口：</p>
					  <span>
					  <input type="text" size="30" name="port" id="port"/>
					  </span>
					   <span> <div id="portTip"></div></span>
					</li>
					<li class="item2_bg">
					  <p> <b>*</b>数据库名称：</p>
					  <span>
					  <input type="text" size="30" name="dbName" id="dbName"/>
					  </span>
					   <span> <div id="dbNameTip"></div></span>
					</li>
					<li >
					  <p>用户名：</p>
					  <span>
					   <input type="text" size="30" name="userName" id="userName"/>
					  </span>
					  <span> <div id="userNameTip"></div></span>
					</li>
					<li  class="item2_bg">
					  <p> 密码：</p>
					  <span>
					  <input type="text" size="30" name="passWord" id="passWord"/>
					  </span>
					   <span> <div id="passWordTip"></div></span>
					</li>
				  </ul>
				</div></td>
			</tr>
		  </table></td>
	  </tr>
	</table>
</form>
</div>
</div>
<div class="clear"></div>
</div>
<script type="text/javascript" src="${path}/js/jquery-1.5.1.js"></script>
<!--增删查改-->
<#include "/common/commonUd.ftl">
 <!--验证js-->
<#include "/common/commonValidator.ftl">
<script type='text/javascript' src='${path}/js/validator/cloudnode/mongodbValidator.js'></script> 
<script type="text/javascript">
	//大写转换
	$('#sourceCode').keypress(function(e) {     
    var keyCode= event.keyCode;  
    var realkey = String.fromCharCode(keyCode).toUpperCase();  
    $(this).val($(this).val()+realkey);  
  	  event.returnValue =false;  
    });
</script>
</body>
</html>
