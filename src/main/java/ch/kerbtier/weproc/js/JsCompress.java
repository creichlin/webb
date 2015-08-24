package ch.kerbtier.weproc.js;

import ch.kerbtier.weproc.Processor;
import ch.kerbtier.weproc.Source;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.CompilerOptions.LanguageMode;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;

public class JsCompress implements Processor {

  @Override
  public String process(Source source) {
    Compiler compiler = new Compiler();
    compiler.disableThreads();
    CompilerOptions options = new CompilerOptions();

    CompilationLevel.SIMPLE_OPTIMIZATIONS
        .setOptionsForCompilationLevel(options);
    options.setLanguageIn(LanguageMode.ECMASCRIPT5);

    SourceFile input = SourceFile.fromCode("input.js", source.get());
    SourceFile extern = SourceFile.fromCode("extern.js", "function alert(){}");
    Result result = compiler.compile(extern, input, options);
    
    if(result.success) {
      return compiler.toSource();
    }
    return source.get();
  }
}
