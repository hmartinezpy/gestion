<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
/*Para evitar error #{..} is not allowed in template text  */
%><%@page deferredSyntaxAllowedAsLiteral="true"%>
<html>
<head>

	<!-- include primefaces stylesheets -->
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/themes/sam/theme.css.jsf?ln=primefaces&amp;amp;v=2.2.1" />
	<script type="text/javascript" src="/perfil/javax.faces.resource/jquery/jquery.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/core/core.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/themeswitcher/themeswitcher.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/jquery/ui/jquery-ui.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/wijmo/wijmo.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/perfil/javax.faces.resource/jquery/ui/jquery-ui.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/wijmo/wijmo.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/menu/menu.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/toolbar/toolbar.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/perfil/javax.faces.resource/button/button.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/confirmdialog/confirmdialog.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/fieldset/fieldset.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/perfil/javax.faces.resource/fieldset/fieldset.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/messages/messages.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<link type="text/css" rel="stylesheet" href="/perfil/javax.faces.resource/growl/assets/growl.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/perfil/javax.faces.resource/growl/growl.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/perfil/javax.faces.resource/ajaxstatus/ajaxstatus.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<!-- End include primefaces stylesheets  -->
	

	<title>Sistema Gestión de Documentos</title>
</head>
	<body  onload="document.j_security_check.username.focus();">
	

	<br></br>
	<br></br>
	
	<center><img src="images/logo-paraguayparatodos.png"/></center>
	<center><b><span class="wijmo-wijmenu-text"> <font face="Arial">SISTEMA DE GESTIÓN DE DOCUMENTOS</font></span></b></center>
	<br></br>		
	<table style="width: 100%">
		
		<!-- Header Text  -->
		
		<tr>
			<td height="10" width="100%" align="left"><div id="j_idt6"></div></td>
		</tr>
		<tr>
			<td>
			     <table cellspacing="1" cellpadding="1" border="0" align="center" width="550"> 
			      
					<!-- End Header Text  -->
					 
					<tr valign="top">
						<td colspan="2" align="center">
							<div id="layout" >  
								
								<!-- JAAS Login Form  -->
								<form method="post" action="login" name="j_security_check"  >  
																		
									<div class="ui-widget ui-fieldset-content">
									<table id="loginTable" align="center">
										<tbody>
											<tr>
												<td><label for="username" class="text-input"><b><font face="courier">Usuario:</font></b> </label></td>
												<td>
													<input id="username" type="text" name="j_username" title="Username" />										
												</td>	
											</tr>
											<tr>
												<td><label for="password" class="text-input"><b><font face="courier">Contraseña:</font></b> </label></td>
												<td><input id="password" type="password" name="j_password" title="Password" /></td>
											</tr>								
											<tr><td></td>
											
												<td align="right" >
													<div class="ui-toolbar-group-right">
														<button id="j_idt21" name="j_idt21"  onclick=";"  type="submit"  style="padding: 5px 10px;  "><b><font face="courier">Ingresar</font></b></button>									
														<script type="text/javascript">widget_j_idt21 = new PrimeFaces.widget.CommandButton('j_idt21', {});</script>
													</div>
												</td>
											</tr>															
										</tbody>  
									</table>
									</div>
								</form>  
								<!-- End JAAS Login Form  -->
							</div>
						</td>
					</tr> 
			     </table> 
			</td>
		</tr>
	</table>
	</body>
</html>