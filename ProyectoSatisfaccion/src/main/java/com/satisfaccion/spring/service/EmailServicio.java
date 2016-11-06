package com.satisfaccion.spring.service;

import com.satisfaccion.util.comun.Constantes;
import org.apache.lucene.util.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class EmailServicio {

	/*ATRIBUTOS*/

	@Autowired
	private JavaMailSender correoElectronico;


	/*METODOS*/

	public boolean validarSintaxisEmail(String email) {

		Pattern pattern = Pattern.compile(Constantes.FORMATO_EMAIL);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public boolean enviarCorreo(String destinatario, String linkEncuesta, Boolean evaluacion) {

		MimeMessage mimeMessage = correoElectronico.createMimeMessage();
		boolean envioExitoso = false;

		try{

			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			message.setTo(destinatario);
			message.setFrom(new InternetAddress(Constantes.CORREO_FROM, Constantes.NOMBRE_CORREO_FROM));

			if(evaluacion){
				message.setSubject(Constantes.ENVIO_ASUNTO_EVALUACION);
			}else {
				message.setSubject(Constantes.ENVIO_ASUNTO_ENCUESTA);
			}

			message.setSentDate(new Date());

			message.setText(cargarPlantilla(linkEncuesta, evaluacion), true);

			correoElectronico.send(mimeMessage);

			envioExitoso = true;

		}catch (Exception e) {
			envioExitoso = false;

		}finally {

			return envioExitoso;
		}

	}

	public String cargarPlantilla(String linkEncuesta, boolean evaluacion){

		VelocityEngine ve = new VelocityEngine();
		StringWriter stringWriter = new StringWriter();
		String plantilla;

		try {

			if(evaluacion){
				plantilla = "plantillaEmailEvaluacion.vm";
			}else{
				plantilla = "plantillaEmailEncuesta.vm";
			}

			//Crear plantilla cuerpo del correo
			ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, FacesContext.getCurrentInstance().getExternalContext().getRealPath("/plantilla") );
			ve.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
			ve.init();

			Template template = ve.getTemplate(plantilla, "UTF-8");
			template.setEncoding("UTF-8");

			//Inicializar variables de plantilla
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("linkEncuesta", linkEncuesta);

			template.merge(velocityContext, stringWriter);

		}catch (Exception e){
			return null;
		}

		return stringWriter.toString();

	}

	public boolean validarExistenciaServidor(String correo){

		try {

			Hashtable env = new Hashtable();
			env.put("java.naming.factory.initial",
					"com.sun.jndi.dns.DnsContextFactory");
			DirContext ictx =  new InitialDirContext( env );
			Attributes attrs =
					ictx.getAttributes( correo, new String[] { "MX" });
			Attribute attr = attrs.get( "MX" );

			if( attr == null ){
				return false;
			} else {
				return true;
			}

		} catch (NamingException e) {
			return false;
		}

	}




	/*GET & SET*/

	public JavaMailSender getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(JavaMailSender correoElectronico) {
		this.correoElectronico = correoElectronico;
	}


}

