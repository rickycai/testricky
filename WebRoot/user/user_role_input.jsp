<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<title>���û������ɫ</title>
</head>
<body>
<center>
<form action="user.do" method="post">
<input type="hidden" name="method" value="addUserRole">
<input type="hidden" name="id" value="${userForm.id}">
<TABLE class="tableEdit" border="0" cellspacing="1" cellpadding="0" style="width:580px;">
	<TBODY>
		<TR>
			<!-- ��������ӡ��༭����ı��� -->
			<td align="center" class="tdEditTitle">��ѡ��Ҫ������û��Ľ�ɫ</TD>
		</TR>
		<TR>
			<td>
			<!-- ��������ʼ -->
      <table width="100%" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- �б������ -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
		      <td width="20%" height="37" align="center"><b>ѡ��</b></td>
		      <td width="80%" height="37" align="center"><b>��ɫ����</b></td>
          </tr>
          <!-- �б������� -->
          <c:if test="${!empty pm.datas}">
          <c:forEach items="${pm.datas }" var="role">
	      <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
		      <td align="center" vAlign="center"><input type="radio" name="roleId" value="${role.id }"></td>
	          <td align="center" vAlign="center">${role.name }</td>
        </tr>
        </c:forEach>
		</c:if>
        <!-- ���б�����Ϊ�յ�ʱ��Ҫ��ʾ����ʾ��Ϣ -->
	    <c:if test="${empty pm.datas}">
	    <tr>
	    	<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
	    	û���ҵ���Ӧ�ļ�¼
	    	</td>
	    </tr>
	    </c:if>
      </table>
<TABLE width="100%" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 align=left vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- ��������������ҳ������ -->
			���ȼ���<input type="text" name="orderNo">
    		</TD>
          </TR>
        </TBODY>
      </TABLE>
			<!-- ����������� -->
			</td>
		</TR>
	</TBODY>
</TABLE>

<TABLE>
		<TR align="center">
			<TD colspan="3" bgcolor="#EFF3F7">
			<input type="submit" name="saveButton"
				class="MyButton" value="�����ɫ"> 
			<input type="button" class="MyButton"
				value="�رմ���" onclick="window.close()">
			</TD>
		</TR>
</TABLE>
</form>
</center>
</body>
</html>