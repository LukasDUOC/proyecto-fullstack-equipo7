package cl.duoc.api_filtros.service;

import cl.duoc.api_filtros.client.ContenidoClient;
import cl.duoc.api_filtros.dto.ContenidoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiltroService {

    @Autowired
    private ContenidoClient contenidoClient;

    public List<ContenidoDTO> filtrar(
            String genero,
            String tipo,
            String visualizar) {

        List<ContenidoDTO> contenidos =
                contenidoClient.getAll();

        return contenidos.stream()

                .filter(c -> genero == null ||
                        c.getGenero().equalsIgnoreCase(genero))

                .filter(c -> tipo == null ||
                        c.getTipo().equalsIgnoreCase(tipo))

                .filter(c -> visualizar == null ||
                        c.getVisualizar().equalsIgnoreCase(visualizar))

                .collect(Collectors.toList());
    }
}