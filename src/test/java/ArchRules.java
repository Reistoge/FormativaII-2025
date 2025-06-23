
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;


@AnalyzeClasses(packages = {"cl.ucn"})
public class ArchRules {

    //"servicio solo puede depender de interfaces del paquete repositorio, no de clases concretas”.
    @ArchTest
    public static final ArchRule servicioNoDependeDeClasesConcretas = noClasses()
            .that().resideInAPackage("..servicio..")
            .should().dependOnClassesThat().resideInAPackage("..repositorio..").andShould().beInterfaces();


    @ArchTest
    public static final ArchRule domainShouldNotDependOnService = noClasses()
            .that().resideInAPackage("..dominio..")
            .should().onlyAccessClassesThat().resideInAnyPackage("..repositorio..");

}