<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@include file="/common/common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<script type="text/javascript">
function selectPagesize(field){
	document.location.href = document.all.firstpageurl.href + "&pagesize="+field.value;
}
function selectOrg(oid,field){
	window.opener.document.getElementById("orgNameId").value = field.v;
	window.opener.document.getElementById("orgIdId").value = oid;
	window.close();
}
</script>
<title>��������</title>
</head>
<BODY bgColor=#dee7ff leftMargin=0 background="" topMargin=0 marginheight="0" marginwidth="0">
<center>
      <TABLE width="778" border=0 cellPadding=0 cellSpacing=0 borderColor=#ffffff bgColor=#dee7ff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR height=35>
            <TD align=middle width=20 background=images/title_left.gif 
          bgColor=#dee7ff></TD>
            <TD align=middle width=120 background=images/title_left.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>��������<font color="#FFFFFF">&nbsp;</font></FONT> </TD>
            <TD align=middle width=11 background=images/title_middle.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
            <TD align=middle background=images/title_right.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
          </TR>
        </TBODY>
      </TABLE>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD width="82%" height=14 align=right vAlign=center noWrap>
            </TD>
            <TD width="18%" align=right vAlign=center noWrap>��</TD>
          </TR>
          <TR>
            <TD height=14 align=right vAlign=center noWrap>&nbsp;<!-- ����������ѯ���� -->
            </TD>
            <TD height=14 align="left" vAlign=center noWrap>
            
            <a href="org.do?parentId=${ppid }&select=true">����</a>
            </TD>
          </TR>
          <TR>
            <TD height=28 colspan="2" align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- ��������������ҳ������ -->
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <table width="778" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- �б������� -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
	      	  <td width="5%" height="37" align="center"><b>ѡ��</b></td>
		      <td width="5%" height="37" align="center"><b>���</b></td>
		      <td width="18%" height="37" align="center"><B>��������</B></td>
		      <td width="18%" height="37" align="center"><b>�������</b></td>
		      <td width="18%" height="37" align="center"><b>����������</b></td>
          </tr>
          <!-- �б������� -->
          <c:if test="${!empty pm.datas}">
          <c:forEach items="${pm.datas }" var="org">
	      <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
	      	  <td align="center" vAlign="center">
	      	  	<input type="radio" id="selectorgid" v="${org.name }" onclick=selectOrg("${org.id }",this)>
	      	  </td>
		      <td align="center" vAlign="center">${org.id }</td>
	          <td align="center" vAlign="center"><a href="org.do?parentId=${org.id }&select=true">${org.name }</a></td>
	          <td align="center" vAlign="center">${org.sn }</td>
	          <td align="center" vAlign="center">${org.parent.name }</td>
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
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- ��������������ҳ������ -->
<pg:pager items="${pm.total }" url="org.do" maxPageItems="${ps}"  export="currentPageNumber=pageNumber">
	<pg:param name="parentId"/>
	<pg:param name="select" value="true"/>
	<pg:first>
		<a href="${pageUrl }" id="firstpageurl">��ҳ</a>
	</pg:first>
	<pg:prev>
		<a href="${pageUrl }">ǰҳ</a>
	</pg:prev>
	<pg:pages>
		<c:choose>
			<c:when test="${currentPageNumber eq pageNumber}">
				<font color="red">${pageNumber }</font>
			</c:when>
			<c:otherwise>
				<a href="${pageUrl }">${pageNumber }</a>
			</c:otherwise>
		</c:choose>
	</pg:pages>
	<pg:next>
		<a href="${pageUrl }">��ҳ</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl }">βҳ</a>
	</pg:last>
</pg:pager>   
<select name="pagesize" onchange="selectPagesize(this)" >
<c:forEach begin="5" end="50" step="5" var="i">
	<option value="${i}"
	<c:if test="${ps eq i }">selected</c:if> 
	>${i}</option>
</c:forEach>
</select>       
    		</TD>
          </TR>
        </TBODY>
      </TABLE>
</center>

</body>

</html>