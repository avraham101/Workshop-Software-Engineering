package Server;

import EncoderDecoderConfig.Decoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"Server.RealTime"})
public class WorkshopApplication {

    public static void main(String[] args) {
		if(args.length>0 && args[0]!=null) {
			Decoder decoder = new Decoder(args[0]);
			decoder.excecute();
		}
    	SpringApplication.run(WorkshopApplication.class, args);
	}

}
