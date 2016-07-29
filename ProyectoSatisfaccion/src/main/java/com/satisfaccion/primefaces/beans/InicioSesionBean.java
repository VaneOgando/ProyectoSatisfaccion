package com.satisfaccion.primefaces.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.satisfaccion.spring.service.LdapServicio;
import com.satisfaccion.util.comun.Constantes;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import java.io.IOException;

@ManagedBean
@ViewScoped
public class InicioSesionBean implements PhaseListener{

	/*ATRIBUTOS*/
	@ManagedProperty("#{ldapServicio}")
	private LdapServicio ldapServicio;

    private String usuario;
	private String contrasenia;

	private boolean respuesta;

    private LdapTemplate ldapTemplate;

	/*METODOS*/

	public String bt_ingresar_action() throws IOException, ServletException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		return null;

	}

	@Override
	public void beforePhase(final PhaseEvent arg0) {
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException) {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage("mensajesError", new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.ERR_LOGIN_INVALIDO, null));
		}
	}

	@Override
	public void afterPhase(final PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

//		public String authenticateUser() throws NamingException {
//
//		Hashtable<String, String> env = new Hashtable<String, String>();
//
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.PROVIDER_URL, "ldap://infra.tcs.local/DC=tcs,DC=local" );
//
//		// Needed for the Bind (User Authorized to Query the LDAP server)
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
//		env.put(Context.SECURITY_PRINCIPAL, "incidencias");
//		env.put(Context.SECURITY_CREDENTIALS, "sDuZv5TfCMOgQ5");
//
//		DirContext ctx;
//
//		try {
//			ctx = new InitialDirContext(env);
//		} catch (NamingException e) {
//			throw new RuntimeException(e);
//		}
//
//		NamingEnumeration<SearchResult> results = null;
//
//		try {
//
//			SearchControls controls = new SearchControls();
//			controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
//			controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
//			controls.setTimeLimit(5000); // Sets the time limit of these SearchControls in milliseconds
//
//			String searchString = "(&(objectCategory=user)(sAMAccountName=" + usuario + "))";
//
//			results = ctx.search("", searchString, controls);
//
//			if (results.hasMore()) {
//
//				SearchResult result = results.next();
//				Attributes attrs = result.getAttributes();
//				javax.naming.directory.Attribute dnAttr = attrs.get("distinguishedName");
//				String dn = dnAttr.get().toString();
//
//				// User Exists, Validate the Password
//
//				env.put(Context.SECURITY_PRINCIPAL, dn);
//				env.put(Context.SECURITY_CREDENTIALS, contrasenia);
//
//				new InitialDirContext(env); // Exception will be thrown on Invalid case
//				return "consultarPregunta.xhtml";
//			}
//			else
//				return "";
//
//		} catch (AuthenticationException e) { // Invalid Login
//			return "";
//		} catch (NameNotFoundException e) { // The base context was not found.
//
//			return "";
//		} catch (NamingException e) {
//			throw new RuntimeException(e);
//		} finally {
//
//			if (results != null) {
//				try { results.close(); } catch (Exception e) {    }
//			}
//
//			if (ctx != null) {
//				try { ctx.close(); } catch (Exception e) {   }
//			}
//		}
//	}


	/*GET & SET*/
	public LdapServicio getLdapServicio() {
		return ldapServicio;
	}

	public void setLdapServicio(LdapServicio ldapServicio) {
		this.ldapServicio = ldapServicio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }


}