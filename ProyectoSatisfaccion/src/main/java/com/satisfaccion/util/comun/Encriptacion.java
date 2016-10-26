package com.satisfaccion.util.comun;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;

@ManagedBean
@ViewScoped
public class Encriptacion {

	/*ATRIBUTOS*/
	byte[] sharedvector = {
			0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
	};

	byte[] keyArray = new byte[24];

	/*METODOS*/

	public byte[] encodeDecode( Cipher c, String texto, boolean encode) throws Exception {

		try {

			byte[] encodeDecode;

			if (encode) {

				c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
				encodeDecode = c.doFinal(texto.getBytes("UTF-8"));

			} else {

				c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
				encodeDecode = c.doFinal(Base64.decodeBase64(texto.getBytes()));

			}

			return encodeDecode;

		}catch (Exception e) {
			throw e;
		}

	}

	public String encriptarEnvio(String texto, boolean encriptar) throws Exception {

		try
		{

			MessageDigest m = MessageDigest.getInstance("MD5");
			byte[] temporaryKey = m.digest(Constantes.KEY_ENCRIPT.getBytes("UTF-8"));

			if(temporaryKey.length < 24) // DESede require 24 byte length key
			{
				int index = 0;
				for(int i=temporaryKey.length;i< 24;i++)
				{
					keyArray[i] =  temporaryKey[index];
				}
			}

			Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");

			byte[] resultado = encodeDecode(c, texto, encriptar);

			if(encriptar){
				return new String(Base64.encodeBase64(resultado));
			}else{
				return new String(resultado, "UTF-8");
			}

		}
		catch(Exception e)
		{
			throw e;
		}
	}



	/*GET & SET*/

	public byte[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(byte[] keyArray) {
		this.keyArray = keyArray;
	}
}

