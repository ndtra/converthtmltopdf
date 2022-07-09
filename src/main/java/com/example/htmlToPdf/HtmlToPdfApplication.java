package com.example.htmlToPdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HtmlToPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmlToPdfApplication.class, args);
	}

	@GetMapping("/genpdf/{fileName}")
	HttpEntity<byte[]> createPdf(
            @PathVariable("fileName") String fileName) throws IOException {

		String source ="src/main/resources/templates/contract.html";
		String content = Files.readString(Path.of(source));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(content, baos);

		HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.APPLICATION_PDF);
	    header.set(HttpHeaders.CONTENT_DISPOSITION,
	                   "attachment; filename=" + fileName.replace(" ", "_"));
	    header.setContentLength(baos.toByteArray().length);

	    return new HttpEntity<byte[]>(baos.toByteArray(), header);

	}

}
