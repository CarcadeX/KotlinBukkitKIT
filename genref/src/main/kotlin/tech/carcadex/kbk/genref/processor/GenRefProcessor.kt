package tech.carcadex.kbk.genref.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import tech.carcadex.kbk.genref.plugin.PluginVisitor
import tech.carcadex.kbk.genref.plugin.annotations.Plugin

class GenRefProcessor(private val codeGenerator: CodeGenerator,
                      private val logger: KSPLogger) : SymbolProcessor {
   // private var invoked = false

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
     //  if(invoked) return emptyList()
        val visitor = PluginVisitor(codeGenerator, logger, resolver)
        resolver.getAllFiles()
            .filter { it.validate() }
            .filter {
                it.declarations.any { it is KSFunctionDeclaration && it.validate() && it.isAnnotationPresent(Plugin::class) }
            }.firstOrNull()?.accept(visitor, Unit)

       // invoked = true
        return emptyList()
    }

}