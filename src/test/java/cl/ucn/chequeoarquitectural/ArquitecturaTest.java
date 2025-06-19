package cl.ucn.chequeoarquitectural;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArquitecturaTest {

    @Test
    public void servicioNoUsaImpl() {
        JavaClasses clases = new ClassFileImporter().importPackages("cl.ucn");

        ArchRule regla = noClasses()
                .that().resideInAPackage("cl.ucn.servicio")
                .should().dependOnClassesThat()
                .haveSimpleName("RepositorioTareaImpl");

        regla.check(clases);
    }

    @Test
    public void dominioEsIndependiente() {
        JavaClasses clases = new ClassFileImporter().importPackages("cl.ucn");

        ArchRule regla = noClasses()
                .that().resideInAPackage("cl.ucn.dominio")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        "cl.ucn.servicio",
                        "cl.ucn.repositorio",
                        "cl.ucn.consola",
                        "cl.ucn.vertx"
                );

        regla.check(clases);
    }
}

