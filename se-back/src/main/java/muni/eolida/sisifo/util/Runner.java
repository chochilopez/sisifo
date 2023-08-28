package muni.eolida.sisifo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.io.File;

@Component
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        File fi = new File("/home/repara/Descargas/descarga.jpeg");
        System.out.println(Files.readAllBytes(fi.toPath()));
    }
}
