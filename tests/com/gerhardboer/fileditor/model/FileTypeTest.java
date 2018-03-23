package com.gerhardboer.fileditor.model;

import com.gerhardboer.fileditor.FileType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileTypeTest {

  @Test()
  public void testFileTypeComponent() {
    assertThat(FileType.forFileName("some.css.file.ts").get(), equalTo(FileType.COMPONENT));
  }

  @Test()
  public void testFileTypeCss() {
    assertThat(FileType.forFileName("some.css.file.css").get(), equalTo(FileType.STYLE));
  }

  @Test()
  public void testFileTypeSpec() {
    assertThat(FileType.forFileName("some.css.spec.ts").get(), equalTo(FileType.SPEC));
  }

}
