
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="core.SML"%>
<%
        if ("POST".equalsIgnoreCase(request.getMethod())){
	String ubicacion="C:/files/";
	DiskFileItemFactory fac = new DiskFileItemFactory();
	fac.setSizeThreshold(1024);
	fac.setRepository(new File(ubicacion));
	
	ServletFileUpload upd = new ServletFileUpload(fac);
        String filePath = "";
	try{
		List<FileItem> partes = upd.parseRequest(request);
		for (FileItem item : partes){
			File file = new File(ubicacion,item.getName());
			item.write(file);
                        filePath = ubicacion + item.getName();
                        
                        <%--filePath: contiene la direccion del archivo a analizar--%>
		}
                SML s = new SML();
                String resul  = s.iniciar(filePath);
                
                request.setAttribute("resultado",resul);
		
               
	}catch(Exception ex){
		out.write("Error subiendo archivo" + ex.getMessage());
	}
        }
            
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Analizador SML</title>
        <link rel="stylesheet" href="style.css" type="text/css">
</head>
<body>
    <div id="pagina">
        <div id="contenido">
        <br/>
        <br/>
        <br/>
        <h2>Analizador de C&oacute;digo SML</h2>
        <br/>
        <br/>
	<form  id="form" action ="index.jsp" method="post" enctype="multipart/form-data">
		<input type="file" name="file"/>
		<input type="submit" value="Analizar" />
        </form>
        <%
            if (request.getAttribute("resultado") != null){
             String n = request.getAttribute("resultado").toString();
             out.write(n);
            }
        %>
        </div>
    </div>
</body>
</html>
