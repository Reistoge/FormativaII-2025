import cl.ucn.dominio.Tarea;
import cl.ucn.repositorio.RepositorioTarea;
import cl.ucn.repositorio.RepositorioTareaImpl;
import cl.ucn.servicio.ServicioTarea;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServicioTareasTest {
    static EntityManagerFactory emf;
    EntityManager em;
    ServicioTarea servicioTarea;
    RepositorioTarea repositorioTarea;


    @BeforeClass
    public static void setupClass() {
        // Aquí podrías configurar algo que se ejecute una vez antes de todos los tests
        emf = Persistence.createEntityManagerFactory("persistencia");
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        repositorioTarea = new RepositorioTareaImpl(em);
        servicioTarea = new ServicioTarea(repositorioTarea); // Aquí deberías inyectar un repositorio mock o real
    }
    @After
    public void tearDown() {
        if(em!=null){
            if( em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
    @AfterClass
    public static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testCrearTarea() {
        Tarea t = servicioTarea.crearTarea("TareaDePrueba", "Descripción", LocalDate.now().plusDays(1));
        Assert.assertNotNull(t);
        boolean result = servicioTarea.obtenerTareasPendientes().contains(t);
        assertTrue(result);


    }
//    @Test
//    public void archRule(){
//        JavaClasses javaClasses = new ClassFileImporter().importPackages("cl.ucn");
//        ArchRule rule = com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes()
//                .that().resideInAPackage("cl.ucn.servicio..")
//                .should().onlyBeAccessed().byAnyPackage("cl.ucn.servicio..", "cl.ucn.repositorio..");
//
//        rule.check(javaClasses);
//
//    }
    @Test
    public void testCrearTareaFechaVacia(){
        Tarea t = servicioTarea.crearTarea("TareaSinFecha", "Descripción", null);
        Assert.assertNotNull(t);
        boolean result = servicioTarea.obtenerTareasPendientes().contains(t);
        assertTrue(result);

    }
    @Test
    public void testListarTareasVacias() {
        // verificar que al principio no haya tareas.
        List<Tarea> tareas = servicioTarea.obtenerTareasPendientes();
        assertTrue("No debería haber tareas al inicio del test",tareas.isEmpty());

    }
    @Test
    public void testMarcarCompletada() {
        // Verifica que el método cambia el estado de la tarea a true
        Tarea t = servicioTarea.crearTarea("TareaParaCompletar", "Descripción", LocalDate.now().plusDays(1));

        servicioTarea.marcarCompletada(t.getId());
        boolean result = servicioTarea.obtenerTareasPendientes().contains(t);
        Assert.assertFalse(result);


    }
    @Test
    public void testEliminarTarea() {
        // Verifica que el metodo elimina la tarea correctamente
        Tarea t = servicioTarea.crearTarea("TareaParaEliminar", "Descripción", LocalDate.now().plusDays(1));
        servicioTarea.deleteTask(t.getId());
        boolean result = servicioTarea.obtenerTareasPendientes().contains(t) && servicioTarea.obtenerTareasCompletadas().contains(t);

        Assert.assertFalse(result);
    }
    @Test
    public void testCrearNulo(){
        // Verifica que el metodo no permite crear una tarea nula
        try{
            Tarea t = servicioTarea.crearTarea(null, null, null);

        }catch(Exception e){
            Assert.assertNotNull("no se puede crear una tarea nula",e.getMessage());
        }
    }


    @Test
    public void testCrearTareaMock() {

        RepositorioTarea repositorioMock = Mockito.mock(RepositorioTarea.class);
        // method returns null (which is Mockito's default behavior if you don't stub it).
        // so repositorio.guardar() will return null.

        ServicioTarea servicio = new ServicioTarea(repositorioMock);

        Tarea t = servicio.crearTarea("Titulo", "Descripcion", LocalDate.now().plusDays(1));
        verify(repositorioMock, times(1)).guardar(Mockito.any(Tarea.class));


    }

    @Test
    public void testMarcarTareaCompletadaMock() {

        // Verifica que se llama al metodo guardar() con la tarea marcada como completada

        Tarea tareaMock = new Tarea();
        tareaMock.setId(1L);
        tareaMock.setTitulo("Tarea Mock");
        tareaMock.setDescripcion("Descripción Mock");
        tareaMock.setFechaFinalizacion(LocalDate.now().plusDays(1));
        tareaMock.setCompletada(false);


        Tarea tareaMockSpy = Mockito.spy(tareaMock); // Creamos un spy de la tarea para verificar interacciones y cambios de estados

        assertFalse(tareaMockSpy.isCompletada()); // verificamos que la tarea no esta completada.

        // simulamos la dependencia del repositorio
        RepositorioTarea repositorioMock = Mockito.mock(RepositorioTarea.class);


        when(repositorioMock.encontrarTodas()).thenReturn(List.of(tareaMockSpy)); // al llamar la base de datos hacemos que retorne la tarea mock spy para verificar esos cambios de estado

        ServicioTarea servicio = new ServicioTarea(repositorioMock);
        servicio.marcarCompletada(tareaMock.getId());


        verify(tareaMockSpy, times(1)).marcarCompleta(); // Verifica que se llama al metodo marcarCompleta de la tarea mock
        assertTrue(tareaMockSpy.isCompletada()); // Verifica que la tarea se haya marcado como completada
        verify(repositorioMock, times(1)).guardar(Mockito.any(Tarea.class)); // verificamos que cuando se marca la tarea como completada, se llama al metodo guardar del repositorio.


    }


}
