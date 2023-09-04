package muni.eolida.sisifo.util;

import muni.eolida.sisifo.service.implementation.EstadoReclamoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    @Autowired EstadoReclamoServiceImpl estadoReclamoService;

    @Override
    public void run(String... args) throws Exception {
    }
}
