package hwr.oop.library.persistence;

import hwr.oop.library.domain.*;

public interface Persistence {

  Library loadLibrary();

  void saveLibrary(Library library);
}
