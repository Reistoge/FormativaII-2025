package cl.ucn.casostest;

import cl.ucn.dominio.Tarea;
import cl.ucn.repositorio.RepositorioTareaImpl;
import cl.ucn.servicio.ServicioTarea;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class ServicioTareaTest{

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ServicioTarea servicio;

    @BeforeClass
    public static void initFactory() {
        emf = Persistence.createEntityManagerFactory("persistencia"); // usa tu unidad normal con SQLite
    }

    @AfterClass
    public static void closeFactory() {
        if (emf != null) emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin(); // 🔁 todo lo que hagas en el test será reversible
        servicio = new ServicioTarea(new RepositorioTareaImpl(em));
    }

    @After
    public void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); // ⬅️ descarta todos los cambios hechos en el test
        }
        em.close();
    }

    // 1. testCrearTareaValida
    @Test
    public void testCrearTareaValida() {
        Tarea t = servicio.crearTarea("Estudiar", "Cap. 2", LocalDate.now());
        assertNotNull(t.getId());
        assertEquals("Estudiar", t.getTitulo());
    }

    // 2. testCrearTareaSinFecha
    @Test
    public void testCrearTareaSinFecha() {
        Tarea t = servicio.crearTarea("Leer", null, null);
        assertNull(t.getFechaFinalizacion());
    }

    // 3. testListarTareasVacio
    @Test
    public void testListarTareasVacio() {
        List<Tarea> tareas = servicio.obtenerTodasLasTareas();
        assertNotNull(tareas);
    }

    // 4. testMarcarComoCompletada
    @Test
    public void testMarcarComoCompletada() {
        Tarea t = servicio.crearTarea("Practicar", null, null);
        servicio.marcarCompletada(t.getId());

        Tarea actualizada = servicio.obtenerTodasLasTareas()
                .stream()
                .filter(t2 -> t2.getId().equals(t.getId()))
                .findFirst()
                .orElseThrow();

        assertTrue(actualizada.isCompletada());
    }

    // 5. testEliminarTarea
    @Test
    public void testEliminarTarea() {
        Tarea t = servicio.crearTarea("Borrar", null, null);
        servicio.borrarTarea(t.getId());

        Tarea encontrada = servicio.buscarTareaPorId(t.getId());
        assertNull(encontrada);
    }


    // 6. testCrearTareaSinTitulo_lanzaExcepcion
    @Test(expected = IllegalArgumentException.class)
    public void testCrearTareaSinTitulo_lanzaExcepcion() {
        servicio.crearTarea("   ", "desc", LocalDate.now());
    }
}
