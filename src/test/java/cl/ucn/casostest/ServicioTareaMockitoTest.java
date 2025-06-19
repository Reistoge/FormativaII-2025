package cl.ucn.casostest;

import cl.ucn.dominio.Tarea;
import cl.ucn.repositorio.RepositorioTarea;
import cl.ucn.servicio.ServicioTarea;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


public class ServicioTareaMockitoTest {

    private RepositorioTarea repoMock;
    private ServicioTarea servicio;

    @Before
    public void setUp() {
        repoMock = mock(RepositorioTarea.class);
        servicio = new ServicioTarea(repoMock);

        // Simula que guardar devuelve la misma tarea que recibe
        when(repoMock.guardar(any(Tarea.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

    }

    /**
     * Verifica que al crear una tarea, el objeto resultante contiene los datos correctos.
     * No se verifica la interacción con el mock, solo el estado del objeto retornado.
     */
    @Test
    public void testCrearTarea() {
        LocalDate fecha = LocalDate.now();
        Tarea tarea = servicio.crearTarea("Título", "Descripción", fecha);

        assertNotNull(tarea);
        assertEquals("Título", tarea.getTitulo());
        assertEquals("Descripción", tarea.getDescripcion());
        assertEquals(fecha, tarea.getFechaFinalizacion());
        assertFalse(tarea.isCompletada());
    }

    /**
     * Verifica que una tarea se marca como completada en memoria al llamar a marcarCompletada.
     * No se verifica la llamada a guardar, solo que el estado cambió.
     */
    @Test
    public void testMarcarCompletada() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Practicar");
        tarea.setDescripcion(null);
        tarea.setFechaFinalizacion(null);
        tarea.setCompletada(false);

        // Simula que el repositorio devuelve la tarea al buscarla
        when(repoMock.encontrarPorId(1L)).thenReturn(tarea);

        servicio.marcarCompletada(1L);
        assertTrue(tarea.isCompletada());
    }
}
