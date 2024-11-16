package net.binarypaper.anemic_api;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithTest {

  @Test
  void verifiesModularStructure() {
    ApplicationModules modules = ApplicationModules.of(Application.class);
    modules.verify();
  }

  @Test
  void createModuleDocumentation() {
    ApplicationModules modules = ApplicationModules.of(Application.class);
    new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
  }
}
