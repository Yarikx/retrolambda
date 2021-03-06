// Copyright © 2013-2015 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.retrolambda.lambdas;

import net.orfjackal.retrolambda.Transformers;
import net.orfjackal.retrolambda.files.OutputDirectory;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class LambdaClassSaver {

    private final OutputDirectory saver;
    private final Transformers transformers;

    public LambdaClassSaver(OutputDirectory saver, Transformers transformers) {
        this.saver = saver;
        this.transformers = transformers;
    }

    public void saveIfLambda(String className, byte[] bytecode) {
        if (LambdaReifier.isLambdaClassToReify(className)) {
            reifyLambdaClass(className, bytecode);
        }
    }

    private void reifyLambdaClass(String className, byte[] bytecode) {
        System.out.println("Saving lambda class: " + className);
        bytecode = transformers.backportLambdaClass(new ClassReader(bytecode));
        try {
            saver.writeClass(bytecode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
