package com.satisfaccion.spring.service;

import com.satisfaccion.util.comun.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
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

		MimeMessage message = correoElectronico.createMimeMessage();
		boolean envioExitoso = false;

		try{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(Constantes.CORREO_FROM);
			helper.setTo(destinatario);

			if(evaluacion){
				helper.setSubject(Constantes.ENVIO_ASUNTO_EVALUACION);
				helper.setText(linkEncuesta);
			}else {
				helper.setSubject(Constantes.ENVIO_ASUNTO_ENCUESTA);
				helper.setText(linkEncuesta);
			}

			correoElectronico.send(message);

			envioExitoso = true;

		}catch (MessagingException e) {
			envioExitoso = false;

		}finally {

			return envioExitoso;
		}

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

