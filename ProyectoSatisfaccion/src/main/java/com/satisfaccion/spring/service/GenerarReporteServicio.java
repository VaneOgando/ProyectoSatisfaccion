package com.satisfaccion.spring.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

@Component
public class GenerarReporteServicio {

	/*ATRIBUTOS*/


	/*METODOS*/

	public GenerarReporteServicio() {}

	@Transactional
	public JasperPrint generarReporte(String reporte, Map<String, Object> parametros) throws Exception {

		try {
			//Buscar reporte
			String  jasperReport=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reportes/"+ reporte );

			//Crear el reporte
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			return jasperPrint;

		} catch (JRException e) {
			throw e;
		} catch (Exception e){
			throw e;
		}
	}

	public void exportarPDF(JasperPrint jasperPrint, String nombreArchivo) throws IOException {

		try{
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + nombreArchivo);

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			servletOutputStream.close();

			FacesContext.getCurrentInstance().responseComplete();

		}catch (JRException e) {
			e.printStackTrace();
		}
	}

	/*GET & SET*/

}
