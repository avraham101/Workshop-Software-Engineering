package Server;

import EncoderDecoderConfig.Decoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;

@SpringBootApplication
public class WorkshopApplication {

    public static void main(String[] args) {
		if(args.length>0 && args[0]!=null) {
			Decoder decoder = new Decoder(args[0]);
			decoder.excecute();
		}
    	SpringApplication.run(WorkshopApplication.class, args);
	}

}
